package com.kpi.diploma.smartroads.rest.primary;

import com.kpi.diploma.smartroads.model.dto.RegistrationManagerDto;
import com.kpi.diploma.smartroads.service.main.ManagerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/manger-requests")
public class ManagerController {

    private final ManagerService managerService;

    @Autowired
    public ManagerController(ManagerService driverService) {
        this.managerService = driverService;
    }

    @PostMapping(value = "/confirm")
    private ResponseEntity<RegistrationManagerDto> confirmProfile(@RequestBody RegistrationManagerDto registrationManagerDto) {
        log.info("'confirmProfile' invoked with params'{}'", registrationManagerDto);

        registrationManagerDto = managerService.registerManager(registrationManagerDto);
        log.info("'registrationManagerDto={}'", registrationManagerDto);

        return ResponseEntity.ok(registrationManagerDto);
    }
}
