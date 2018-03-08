package com.kpi.diploma.smartroads.rest.primary;

import com.kpi.diploma.smartroads.model.document.map.MapObject;
import com.kpi.diploma.smartroads.model.dto.map.MapObjectDto;
import com.kpi.diploma.smartroads.model.dto.user.DriverDto;
import com.kpi.diploma.smartroads.model.dto.user.ManagerDto;
import com.kpi.diploma.smartroads.model.dto.user.RegistrationDriverDto;
import com.kpi.diploma.smartroads.model.dto.user.RegistrationManagerDto;
import com.kpi.diploma.smartroads.model.util.exception.IncorrectInputDataException;
import com.kpi.diploma.smartroads.model.util.security.MongoUserDetails;
import com.kpi.diploma.smartroads.model.util.title.value.MapObjectRequestValues;
import com.kpi.diploma.smartroads.service.primary.CompanyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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

        if (driverDto.getEmail() == null) {
            throw new IncorrectInputDataException("email isn't set");
        }

        RegistrationDriverDto driver = companyService.createDriver(principal.getUserId(), driverDto);
        log.info("'driver={}'", driver);

        return ResponseEntity.ok(driver);
    }

    @PostMapping(value = "/create-manager")
    private ResponseEntity<RegistrationManagerDto> createManager(
            @RequestBody RegistrationManagerDto managerDto,
            @AuthenticationPrincipal MongoUserDetails principal) {
        log.info("'createManager' invoked with params'{}, {}'", managerDto, principal);

        if (managerDto.getEmail() == null) {
            throw new IncorrectInputDataException("email isn't set");
        }

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

    @DeleteMapping(value = "/delete-driver/{driverId}")
    private ResponseEntity deleteDriver(@AuthenticationPrincipal MongoUserDetails principal,
                                        @PathVariable String driverId) {
        log.info("'deleteDriver' invoked with params'{}, {}'", principal.getUsername(), driverId);

        companyService.deleteDriver(driverId, principal.getUserId());
        log.info("deleted successfully");

        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete-manager/{managerId}")
    private ResponseEntity deleteManager(@AuthenticationPrincipal MongoUserDetails principal,
                                        @PathVariable String managerId) {
        log.info("'deleteManager' invoked with params'{}, {}'", principal.getUsername(), managerId);

        companyService.deleteManager(managerId, principal.getUserId());
        log.info("deleted successfully");

        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping(value = "/set-endpoint/{marker}")
    private ResponseEntity<MapObjectDto> setEndpoint(@PathVariable String marker,
                                       @RequestBody MapObjectDto mapObjectDto,
                                       @AuthenticationPrincipal MongoUserDetails principal) {
        log.info("'setEndpoint' invoked with params'{}, {}, {}'", marker, mapObjectDto, principal.getUserId());

        if(!marker.equals(MapObjectRequestValues.START) && !marker.equals(MapObjectRequestValues.FINISH)) {

            String errorMessage = "Incorrect endpoint marker";
            log.error(errorMessage);
            throw new IncorrectInputDataException(errorMessage);

        }

        MapObjectDto mapObject = companyService.setEndpoint(marker, mapObjectDto, principal.getUserId());
        log.info("'mapObject={}'", mapObject);

        return ResponseEntity.ok(mapObject);
    }

    @DeleteMapping(value = "/delete-endpoint/{mapObjectId}")
    private ResponseEntity deleteEndpoint(@AuthenticationPrincipal MongoUserDetails principal,
                                          @PathVariable String mapObjectId) {
        log.info("'deleteEndpoint' invoked with params'{}, {}'", principal.getUserId(), mapObjectId);

        companyService.deleteEndpoint(mapObjectId, principal.getUserId());
        log.info("deleted successfully");

        return new ResponseEntity(HttpStatus.OK);
    }
}
