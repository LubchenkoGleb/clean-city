package com.kpi.diploma.smartroads.rest.primary;

import com.kpi.diploma.smartroads.model.dto.user.DriverDto;
import com.kpi.diploma.smartroads.model.dto.user.RegistrationDriverDto;
import com.kpi.diploma.smartroads.service.primary.DriverService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/driver-requests")
public class DriverController {

    private final DriverService driverService;

    @Autowired
    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @PostMapping(value = "/confirm")
    private ResponseEntity<DriverDto> confirmProfile(@RequestBody RegistrationDriverDto registrationDriverDto) {
        log.info("'confirmProfile' invoked with params'{}'", registrationDriverDto);

        DriverDto driverDto = driverService.registerDriver(registrationDriverDto);
        log.info("'driverDto={}'", driverDto);

        return ResponseEntity.ok(driverDto);
    }
}
