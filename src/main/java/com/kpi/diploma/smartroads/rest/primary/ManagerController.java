package com.kpi.diploma.smartroads.rest.primary;

import com.fasterxml.jackson.databind.JsonNode;
import com.kpi.diploma.smartroads.model.dto.user.ManagerDto;
import com.kpi.diploma.smartroads.model.dto.user.RegistrationManagerDto;
import com.kpi.diploma.smartroads.model.util.security.MongoUserDetails;
import com.kpi.diploma.smartroads.model.util.title.Fields;
import com.kpi.diploma.smartroads.model.util.title.value.ContainerValues;
import com.kpi.diploma.smartroads.service.primary.ManagerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/manager-requests")
public class ManagerController {

    private final ManagerService managerService;

    @Autowired
    public ManagerController(ManagerService driverService) {
        this.managerService = driverService;
    }

    @PostMapping(value = "/confirm")
    private ResponseEntity<ManagerDto> confirmProfile(@RequestBody RegistrationManagerDto registrationManagerDto) {
        log.info("'confirmProfile' invoked with params'{}'", registrationManagerDto);

        ManagerDto managerDto = managerService.registerManager(registrationManagerDto);
        log.info("'managerDto={}'", managerDto);

        return ResponseEntity.ok(managerDto);
    }

    @PostMapping(value = "/create-job-request")
    private ResponseEntity createJobRequest(@RequestBody JsonNode body,
                                            @AuthenticationPrincipal MongoUserDetails principal) {

        String mapObjectId = body.get(Fields.MAP_OBJECT_ID).asText();
        ContainerValues containerValues = ContainerValues.valueOf(body.get(Fields.CONTAINER_TYPE).asText());

        managerService.createJobRequest(principal.getUserId(), mapObjectId, containerValues);
        log.info("job request created successfully");

        return new ResponseEntity(HttpStatus.OK);
    }
}
