package com.kpi.diploma.smartroads.rest.primary;

import com.kpi.diploma.smartroads.model.dto.DriverDto;
import com.kpi.diploma.smartroads.model.dto.ManagerDto;
import com.kpi.diploma.smartroads.model.dto.RegistrationDriverDto;
import com.kpi.diploma.smartroads.model.dto.RegistrationManagerDto;
import com.kpi.diploma.smartroads.model.security.MongoUserDetails;
import com.kpi.diploma.smartroads.service.primary.CompanyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/company-requests")
public class CompanyController {

    final CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping(value = "/create-driver")
    private ResponseEntity<RegistrationDriverDto> createDriver(
            @RequestBody RegistrationDriverDto driverDto,
            @AuthenticationPrincipal MongoUserDetails principal) {
        log.info("'createDriver' invoked with params'{}, {}'", driverDto, principal);

        RegistrationDriverDto driver = companyService.createDriver(principal.getUserId(), driverDto);
        log.info("'driver={}'", driver);

        return ResponseEntity.ok(driver);
    }

    @PostMapping(value = "/create-manager")
    private ResponseEntity<RegistrationManagerDto> createManager(
            @RequestBody RegistrationManagerDto managerDto,
            @AuthenticationPrincipal MongoUserDetails principal) {
        log.info("'createManager' invoked with params'{}, {}'", managerDto, principal);

        RegistrationManagerDto manager = companyService.createManager(principal.getUserId(), managerDto);
        log.info("'driver={}'", manager);

        return ResponseEntity.ok(manager);
    }

    @GetMapping(value = "/get-drivers")
    private ResponseEntity<List<DriverDto>> getDrivers(@AuthenticationPrincipal MongoUserDetails principal) {
        log.info("'getDrivers' invoked for user'{}'", principal.getUserId());

        List<DriverDto> drivers = companyService.getDrivers(principal.getUserId());
        log.info("'drivers={}'", drivers);

        return ResponseEntity.ok(drivers);
    }

    @GetMapping(value = "/get-managers")
    private ResponseEntity<List<ManagerDto>> getManagers(@AuthenticationPrincipal MongoUserDetails principal) {
        log.info("'getManagers' invoked for user'{}'", principal.getUserId());

        List<ManagerDto> managers = companyService.getManagers(principal.getUserId());
        log.info("'managers={}'", managers);

        return ResponseEntity.ok(managers);
    }
}
