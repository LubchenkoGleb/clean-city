package com.kpi.diploma.smartroads.service.primary.impl;

import com.google.maps.model.LatLng;
import com.kpi.diploma.smartroads.model.document.map.Container;
import com.kpi.diploma.smartroads.model.document.map.MapObject;
import com.kpi.diploma.smartroads.model.document.map.Route;
import com.kpi.diploma.smartroads.model.document.user.User;
import com.kpi.diploma.smartroads.model.dto.map.ContainerDto;
import com.kpi.diploma.smartroads.model.dto.map.MapObjectDto;
import com.kpi.diploma.smartroads.model.dto.user.UserDto;
import com.kpi.diploma.smartroads.model.util.data.GoogleRoute;
import com.kpi.diploma.smartroads.model.util.exception.ResourceNotFoundException;
import com.kpi.diploma.smartroads.model.util.exception.UserRoleException;
import com.kpi.diploma.smartroads.model.util.title.value.MapObjectDescriptionValues;
import com.kpi.diploma.smartroads.model.util.title.value.RoleValues;
import com.kpi.diploma.smartroads.repository.ContainerRepository;
import com.kpi.diploma.smartroads.repository.MapObjectRepository;
import com.kpi.diploma.smartroads.repository.RouteRepository;
import com.kpi.diploma.smartroads.repository.UserRepository;
import com.kpi.diploma.smartroads.service.primary.MapObjectService;
import com.kpi.diploma.smartroads.service.util.google.GoogleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MapObjectServiceImpl implements MapObjectService {

    private final MapObjectRepository mapObjectRepository;

    private final ContainerRepository containerRepository;

    private final GoogleService googleService;

    private final UserRepository userRepository;

    private final RouteRepository routeRepository;

    @Autowired
    public MapObjectServiceImpl(MapObjectRepository mapObjectRepository,
                                ContainerRepository containerRepository, GoogleService googleService, UserRepository userRepository,
                                RouteRepository routeRepository) {
        this.mapObjectRepository = mapObjectRepository;
        this.containerRepository = containerRepository;
        this.googleService = googleService;
        this.userRepository = userRepository;
        this.routeRepository = routeRepository;
    }

    @Override
    public MapObjectDto createMapObject(String companyId, ContainerDto containerDto) {
        log.info("'createMapObject' invoked with params'{}, {}'", companyId, containerDto);

        User owner = userRepository.findOne(companyId);
        log.info("'owner={}'", owner);

        if (!owner.hasRole(RoleValues.COMPANY)) {

            String errorMessage = "user with id '" + companyId + "' doesn't have company role";
            log.error(errorMessage);
            throw new UserRoleException(errorMessage);

        }

        Container container = ContainerDto.convertContainer(containerDto);
        container.setOwner(owner);
        container.setDescription(MapObjectDescriptionValues.CONTAINER.toString());
        Container savedContainer = mapObjectRepository.save(container);

        processRoutes(companyId, container);

        ContainerDto response = ContainerDto.convertContainer(savedContainer);
        response.setOwner(UserDto.convert(container.getOwner()));
        log.info("'response {}'", response);

        return response;
    }

    @Override
    public void processRoutes(String companyId, MapObject mapObject) {
        log.info("'processRoutes' invoked with params'{}, {}'", companyId, mapObject);

        List<MapObject> allCompanyMapObjects = mapObjectRepository.findAllByOwnerId(companyId)
                .stream().filter(mo -> !mo.getId().equals(mapObject.getId())).collect(Collectors.toList());
        log.info("'allCompanyMapObjects={}'", allCompanyMapObjects);
        List<Route> startRoutes = new ArrayList<>();
        List<Route> endRoutes = new ArrayList<>();

        ExecutorService executor = Executors.newWorkStealingPool();

        allCompanyMapObjects.forEach(mo -> executor.submit(() -> {

            GoogleRoute googleRoute = googleService.buildRoute(
                    new LatLng(mo.getLat(), mo.getLon()),
                    new LatLng(mo.getLat(), mo.getLon()));
            Route startRoute = new Route(mapObject, mo, googleRoute.getLength(), googleRoute.getEncodedRoute());
            log.info("'startRoute={}'", startRoute);
            startRoutes.add(startRoute);

            googleRoute = googleService.buildRoute(
                    new LatLng(mo.getLat(), mo.getLon()),
                    new LatLng(mo.getLat(), mo.getLon()));
            Route endRoute = new Route(mo, mapObject, googleRoute.getLength(), googleRoute.getEncodedRoute());
            log.info("'endRoute={}'", endRoute);
            endRoutes.add(endRoute);
        }));

        executor.shutdown();
        try {
            executor.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            return;
        }

        List<Route> savedStartRoutes = routeRepository.save(startRoutes);
        List<Route> savedEndRoutes = routeRepository.save(endRoutes);

        mapObject.setStartRoutes(savedStartRoutes);
        mapObjectRepository.save(mapObject);

        for (int i = 0; i < allCompanyMapObjects.size(); i++) {
            allCompanyMapObjects.get(i).getStartRoutes().add(savedEndRoutes.get(i));
        }
        mapObjectRepository.save(allCompanyMapObjects);

    }

    @Override
    public MapObjectDto setCompany(String companyId, String mapObjectId) {
        log.info("'setCompany' invoked with params'{}, {}'", companyId, mapObjectId);

        User company = userRepository.findOne(companyId);
        if (company == null) {
            String errorMessage = "company with id'" + companyId + "'not found";
            log.error(errorMessage);
            throw new ResourceNotFoundException(errorMessage);
        } else if (!company.hasRole(RoleValues.COMPANY)) {
            String errorMessage = "user with id'" + companyId + "' doesn't has company role";
            log.error(errorMessage);
            throw new UserRoleException(errorMessage);
        }

        MapObject mapObject = mapObjectRepository.findOne(mapObjectId);
        if (mapObject == null) {
            String errorMessage = "map object with id'" + mapObjectId + "'not found";
            log.error(errorMessage);
            throw new ResourceNotFoundException(errorMessage);
        }

        mapObject.setOwner(company);
        mapObjectRepository.save(mapObject);

        MapObjectDto converted = MapObjectDto.convert(mapObject);
        converted.setOwner(UserDto.convert(company));
        log.info("'converted={}'", converted);

        return converted;
    }

    @Override
    public MapObjectDto unsetCompany(String mapObjectId) {
        log.info("'unsetCompany' invoked with params'{}, {}'", mapObjectId);

        MapObject mapObject = mapObjectRepository.findOne(mapObjectId);
        if (mapObject == null) {
            String errorMessage = "map object with id'" + mapObjectId + "'not found";
            log.error(errorMessage);
            throw new ResourceNotFoundException(errorMessage);
        }

        mapObject.setOwner(null);
        mapObjectRepository.save(mapObject);

        MapObjectDto converted = MapObjectDto.convert(mapObject);
        log.info("'converted={}'", converted);

        return converted;
    }

    @Override
    public MapObjectDto deleteMapObject(String userId, String mapObjectId) {
        log.info("'deleteMapObject' invoked with params'{}, {}'", userId, mapObjectId);

        MapObject mapObjectEntity = mapObjectRepository.findOne(mapObjectId);

        return null;
    }

    @Override
    public List<MapObjectDto> getByOwner(String userId) {
        log.info("'getByOwner' invoked with params'{}'", userId);
        return null;
    }

    @Override
    public List<MapObjectDto> getAll() {
        return null;
    }

    @Override
    public MapObjectDto getDetails(String mapObjectId) {
        log.info("'getDetails' invoked with params'{}'", mapObjectId);

        MapObject mapObject = mapObjectRepository.findOne(mapObjectId);

        MapObjectDto mapObjectDto;

        if (mapObject instanceof Container) {
            mapObjectDto = ContainerDto.convertContainer((Container) mapObject);
        } else {
            mapObjectDto = MapObjectDto.convert(mapObject);
        }

        mapObjectDto.setOwner(UserDto.convert(mapObject.getOwner()));
        log.info("'mapObjectDto={}'", mapObject);

        return mapObjectDto;
    }
}
