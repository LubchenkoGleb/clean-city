package com.kpi.diploma.smartroads.service.primary.impl;

import com.kpi.diploma.smartroads.model.document.MapObject;
import com.kpi.diploma.smartroads.model.document.user.User;
import com.kpi.diploma.smartroads.model.dto.MapObjectDto;
import com.kpi.diploma.smartroads.repository.MapObjectRepository;
import com.kpi.diploma.smartroads.repository.UserRepository;
import com.kpi.diploma.smartroads.service.primary.MapObjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MapObjectServiceImpl implements MapObjectService {

    private final MapObjectRepository mapObjectRepository;

    private final UserRepository userRepository;

    @Autowired
    public MapObjectServiceImpl(MapObjectRepository mapObjectRepository, UserRepository userRepository) {
        this.mapObjectRepository = mapObjectRepository;
        this.userRepository = userRepository;
    }

    @Override
    public MapObjectDto createMapObject(String userId, MapObjectDto mapObjectDto) {
        log.info("'createMapObject' invoked with params'{}, {}'", userId, mapObjectDto);

        User user = userRepository.findOne(userId);

        MapObject mapObjectEntity = mapObjectDto.convert();
        mapObjectEntity.setCreator(user);
        mapObjectEntity = mapObjectRepository.save(mapObjectEntity);
        log.info("'mapObjectEntity={}'", mapObjectEntity);

        return mapObjectDto;
    }

    @Override
    public MapObjectDto deleteMapObject(String userId, String mapObjectId) {
        log.info("'deleteMapObject' invoked with params'{}, {}'", userId, mapObjectId);

        MapObject mapObjectEntity = mapObjectRepository.findOne(mapObjectId);

        return null;
    }
}
