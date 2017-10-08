package com.kpi.diploma.smartroads.rest.primary;

import com.kpi.diploma.smartroads.model.dto.RegistrationDriverDto;
import com.kpi.diploma.smartroads.model.dto.RegistrationManagerDto;
import com.kpi.diploma.smartroads.model.security.MongoUserDetails;
import com.kpi.diploma.smartroads.service.main.CompanyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.security.Principal;

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
            @AuthenticationPrincipal MongoUserDetails principal) throws MessagingException {
        log.info("'createDriver' invoked with params'{}, {}'", driverDto, principal);

        RegistrationDriverDto driver = companyService.createDriver(principal.getUserId(), driverDto);
        log.info("'driver={}'", driver);

        return ResponseEntity.ok(driver);
    }

    @PostMapping(value = "/create-manager")
    private ResponseEntity<RegistrationManagerDto> createManager(
            @RequestBody RegistrationManagerDto managerDto,
            @AuthenticationPrincipal MongoUserDetails principal) throws MessagingException {
        log.info("'createManager' invoked with params'{}, {}'", managerDto, principal);

        RegistrationManagerDto manager = companyService.createManager(principal.getUserId(), managerDto);
        log.info("'driver={}'", manager);

        return ResponseEntity.ok(manager);
    }
}
