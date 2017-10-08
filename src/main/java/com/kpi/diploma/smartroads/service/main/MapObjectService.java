package com.kpi.diploma.smartroads.service.main;

import com.kpi.diploma.smartroads.model.dto.MapObjectDto;

public interface MapObjectService {

    MapObjectDto createMapObject(String userId, MapObjectDto mapObjectDto);

    MapObjectDto deleteMapObject(String userId, String mapObjectId);
}
