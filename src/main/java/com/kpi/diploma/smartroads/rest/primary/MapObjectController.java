package com.kpi.diploma.smartroads.rest.primary;

import com.kpi.diploma.smartroads.model.dto.map.MapObjectDto;
import com.kpi.diploma.smartroads.service.primary.MapObjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "map-object-requests")
public class MapObjectController {

    private final MapObjectService mapObjectService;

    @Autowired
    public MapObjectController(MapObjectService mapObjectService) {
        this.mapObjectService = mapObjectService;
    }

    @GetMapping(value = "/get-details/{mapObjectId}")
    private ResponseEntity<MapObjectDto> getMapObjectDatails(@PathVariable String mapObjectId) {
        log.info("'getMapObjectDatails' invoked with params'{}'", mapObjectId);

        MapObjectDto details = mapObjectService.getDetails(mapObjectId);

        return ResponseEntity.ok(details);
    }
}
