package com.kpi.diploma.smartroads.rest.primary;

import com.kpi.diploma.smartroads.model.dto.MapObjectDto;
import com.kpi.diploma.smartroads.model.util.security.MongoUserDetails;
import com.kpi.diploma.smartroads.service.primary.MapObjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "map-object-controllers")
public class MapObjectController {

    private final MapObjectService mapObjectService;

    @Autowired
    public MapObjectController(MapObjectService mapObjectService) {
        this.mapObjectService = mapObjectService;
    }

    @PostMapping(value = "/create")
    private ResponseEntity<MapObjectDto> create(@AuthenticationPrincipal MongoUserDetails principal,
                                                @RequestBody MapObjectDto mapObjectDto) {
        log.info("'create' invoked with params'{}, {}'", principal.getUserId(), mapObjectDto);

        MapObjectDto response = mapObjectService.createMapObject(principal.getUserId(), mapObjectDto);
        log.info("'response={}'", response);

        return ResponseEntity.ok(response);
    }
}
