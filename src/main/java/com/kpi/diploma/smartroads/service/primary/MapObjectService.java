package com.kpi.diploma.smartroads.service.primary;

import com.kpi.diploma.smartroads.model.document.map.MapObject;
import com.kpi.diploma.smartroads.model.dto.map.ContainerDto;
import com.kpi.diploma.smartroads.model.dto.map.MapObjectDto;

import java.util.List;

public interface MapObjectService {

    MapObjectDto createMapObject(String userId, ContainerDto containerDto);

    MapObjectDto setCompany(String ownerId, String mapObjectId);

    MapObjectDto unsetCompany(String mapObjectId);

    MapObjectDto deleteMapObject(String userId, String mapObjectId);

    List<MapObjectDto> getByOwner(String userId);

    List<MapObjectDto> getAll();

    void processRoutes(String companyId, MapObject mapObject);

    MapObjectDto getDetails(String mapObjectId);
}
