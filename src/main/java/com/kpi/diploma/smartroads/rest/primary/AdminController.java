package com.kpi.diploma.smartroads.rest.primary;

import com.fasterxml.jackson.databind.JsonNode;
import com.kpi.diploma.smartroads.model.dto.map.ContainerDto;
import com.kpi.diploma.smartroads.model.dto.map.MapObjectDto;
import com.kpi.diploma.smartroads.model.dto.user.CompanyDto;
import com.kpi.diploma.smartroads.model.util.security.MongoUserDetails;
import com.kpi.diploma.smartroads.model.util.title.Fields;
import com.kpi.diploma.smartroads.service.primary.CompanyService;
import com.kpi.diploma.smartroads.service.primary.MapObjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/admin")
public class AdminController {

    private final CompanyService companyService;

    private final MapObjectService mapObjectService;

    public AdminController(CompanyService companyService, MapObjectService mapObjectService) {
        this.companyService = companyService;
        this.mapObjectService = mapObjectService;
    }

    @GetMapping(value = "/companies-list")
    private ResponseEntity<List<CompanyDto>> getList() {
        log.info("'getList' invoked");

        List<CompanyDto> companies = companyService.getCompanies();

        return ResponseEntity.ok(companies);
    }

    @PostMapping(value = "/create-container/{companyId}")
    private ResponseEntity<MapObjectDto> createContainer(@Valid @RequestBody ContainerDto containerDto, @PathVariable String companyId) {
        log.info("'create' invoked with params'{}'", containerDto);

        MapObjectDto response = mapObjectService.createMapObject(companyId, containerDto);
        log.info("'response={}'", response);

        return ResponseEntity.ok(response);
    }

    /**
     * @param principal
     * @param jsonNode  {"company_id":string, "map_object_id":string}
     */
    @PutMapping(value = "/set-company")
    private ResponseEntity<MapObjectDto> setCompany(@AuthenticationPrincipal MongoUserDetails principal,
                                                    @RequestBody JsonNode jsonNode) {
        log.info("'setCompany' invoked with params'{}, {}'", principal.getUserId(), jsonNode);

        String companyId = jsonNode.get(Fields.COMPANY_ID).asText();
        String mapObjectId = jsonNode.get(Fields.MAP_OBJECT_ID).asText();

        MapObjectDto mapObjectDto = mapObjectService.setCompany(companyId, mapObjectId);
        log.info("'mapObjectDto={}'", mapObjectDto);

        return ResponseEntity.ok(mapObjectDto);
    }

    /**
     * @param principal
     * @param jsonNode  {"map_object_id":string}
     */
    @DeleteMapping(value = "/unset-company")
    private ResponseEntity<MapObjectDto> unsetCompany(@AuthenticationPrincipal MongoUserDetails principal,
                                                      @RequestBody JsonNode jsonNode) {
        log.info("'unsetCompany' invoked with params'{}, {}'", principal.getUserId(), jsonNode);

        String mapObjectId = jsonNode.get(Fields.MAP_OBJECT_ID).asText();

        MapObjectDto mapObjectDto = mapObjectService.unsetCompany(mapObjectId);
        log.info("'mapObjectDto={}'", mapObjectDto);

        return ResponseEntity.ok(mapObjectDto);
    }
}
