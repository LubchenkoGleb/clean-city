package com.kpi.diploma.smartroads.service.primary;

import com.kpi.diploma.smartroads.model.dto.MapObjectDto;

import java.util.List;

public interface MapObjectService {

    MapObjectDto createMapObject(String userId, MapObjectDto mapObjectDto);

    MapObjectDto setCompany(String ownerId, String mapObjectId);

    MapObjectDto unsetCompany(String mapObjectId);

    MapObjectDto deleteMapObject(String userId, String mapObjectId);

    List<MapObjectDto> getByOwner(String userId);

    List<MapObjectDto> getAll();
}
