package com.kpi.diploma.smartroads.rest.primary;

import com.kpi.diploma.smartroads.model.dto.user.ManagerDto;
import com.kpi.diploma.smartroads.model.dto.user.RegistrationManagerDto;
import com.kpi.diploma.smartroads.service.primary.ManagerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
}
