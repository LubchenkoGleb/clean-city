package com.kpi.diploma.smartroads.service.primary.impl;

import com.kpi.diploma.smartroads.model.document.MapObject;
import com.kpi.diploma.smartroads.model.document.user.Manager;
import com.kpi.diploma.smartroads.model.document.user.User;
import com.kpi.diploma.smartroads.model.dto.MapObjectDto;
import com.kpi.diploma.smartroads.model.dto.user.UserDto;
import com.kpi.diploma.smartroads.model.util.exception.ResourceNotFoundException;
import com.kpi.diploma.smartroads.model.util.exception.UserRoleException;
import com.kpi.diploma.smartroads.model.util.title.value.RoleValues;
import com.kpi.diploma.smartroads.repository.ManagerRepository;
import com.kpi.diploma.smartroads.repository.MapObjectRepository;
import com.kpi.diploma.smartroads.repository.UserRepository;
import com.kpi.diploma.smartroads.service.primary.MapObjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class MapObjectServiceImpl implements MapObjectService {

    private final MapObjectRepository mapObjectRepository;

    private final ManagerRepository managerRepository;

    private final UserRepository userRepository;

    @Autowired
    public MapObjectServiceImpl(MapObjectRepository mapObjectRepository,
                                ManagerRepository managerRepository,
                                UserRepository userRepository) {
        this.mapObjectRepository = mapObjectRepository;
        this.managerRepository = managerRepository;
        this.userRepository = userRepository;
    }

    @Override
    public MapObjectDto createMapObject(String userId, MapObjectDto mapObjectDto) {
        log.info("'createMapObject' invoked with params'{}, {}'", userId, mapObjectDto);

        User user = userRepository.findOne(userId);
        User owner = null;
        if (user.hasRole(RoleValues.COMPANY)) {
            owner = user;
        } else if (user.hasRole(RoleValues.MANAGER)) {
            Manager manager = managerRepository.findOne(userId);
            owner = manager.getBoss();
        }
        log.info("'owner={}'", owner);

        MapObject mapObject = MapObjectDto.convert(mapObjectDto);
        mapObject.setOwner(owner);
        mapObject = mapObjectRepository.save(mapObject);
        log.info("'mapObject={}'", mapObject);

        return mapObjectDto;
    }

    @Override
    public MapObjectDto setCompany(String companyId, String mapObjectId) {
        log.info("'setCompany' invoked with params'{}, {}'", companyId, mapObjectId);

        User company = userRepository.findOne(companyId);
        if(company == null) {
            String errorMessage = "company with id'" + companyId + "'not found";
            log.error(errorMessage);
            throw new ResourceNotFoundException(errorMessage);
        } else if (!company.hasRole(RoleValues.COMPANY)) {
            String errorMessage = "user with id'" + companyId + "' doesn't has company role";
            log.error(errorMessage);
            throw new UserRoleException(errorMessage);
        }

        MapObject mapObject = mapObjectRepository.findOne(mapObjectId);
        if(mapObject == null) {
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
        if(mapObject == null) {
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
}
