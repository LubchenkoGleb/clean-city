package com.kpi.diploma.smartroads.service.primary.impl;

import com.kpi.diploma.smartroads.model.document.MapObject;
import com.kpi.diploma.smartroads.model.document.user.Company;
import com.kpi.diploma.smartroads.model.document.user.Manager;
import com.kpi.diploma.smartroads.model.document.user.User;
import com.kpi.diploma.smartroads.model.dto.MapObjectDto;
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
        if(user.hasRole(RoleValues.COMPANY)) {
            owner = user;
        } else if(user.hasRole(RoleValues.MANAGER)) {
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
    public MapObjectDto deleteMapObject(String userId, String mapObjectId) {
        log.info("'deleteMapObject' invoked with params'{}, {}'", userId, mapObjectId);

        MapObject mapObjectEntity = mapObjectRepository.findOne(mapObjectId);

        return null;
    }

    @Override
    public List<MapObjectDto> getByOwner(String userId) {
        return null;
    }

    @Override
    public List<MapObjectDto> getAll() {
        return null;
    }
}
