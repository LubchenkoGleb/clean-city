package com.kpi.diploma.smartroads.service.primary.impl;

import com.kpi.diploma.smartroads.model.document.user.Role;
import com.kpi.diploma.smartroads.model.document.map.MapObject;
import com.kpi.diploma.smartroads.model.document.user.Company;
import com.kpi.diploma.smartroads.model.document.user.Driver;
import com.kpi.diploma.smartroads.model.document.user.Manager;
import com.kpi.diploma.smartroads.model.dto.map.MapObjectDto;
import com.kpi.diploma.smartroads.model.dto.user.*;
import com.kpi.diploma.smartroads.model.util.data.EmailMessage;
import com.kpi.diploma.smartroads.model.util.exception.IncorrectInputDataException;
import com.kpi.diploma.smartroads.model.util.exception.ResourceNotFoundException;
import com.kpi.diploma.smartroads.model.util.exception.ResourseDoesntBelongToUserException;
import com.kpi.diploma.smartroads.model.util.title.value.MapObjectDescriptionValues;
import com.kpi.diploma.smartroads.model.util.title.value.MapObjectRequestValues;
import com.kpi.diploma.smartroads.model.util.title.value.RoleValues;
import com.kpi.diploma.smartroads.repository.*;
import com.kpi.diploma.smartroads.service.primary.CompanyService;
import com.kpi.diploma.smartroads.service.primary.MapObjectService;
import com.kpi.diploma.smartroads.service.util.email.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class CompanyServiceImpl implements CompanyService {

    private final MapObjectRepository mapObjectRepository;
    private final CompanyRepository companyRepository;
    private final ManagerRepository managerRepository;
    private final MapObjectService mapObjectService;
    private final DriverRepository driverRepository;
    private final RoleRepository roleRepository;
    private final EmailService emailService;
    private final Environment environment;
    private final String clientUrl;

    @Autowired
    public CompanyServiceImpl(MapObjectRepository mapObjectRepository,
                              CompanyRepository companyRepository,
                              ManagerRepository managerRepository,
                              MapObjectService mapObjectService,
                              DriverRepository driverRepository,
                              RoleRepository roleRepository,
                              EmailService emailService,
                              Environment environment) {
        this.mapObjectRepository = mapObjectRepository;
        this.companyRepository = companyRepository;
        this.managerRepository = managerRepository;
        this.mapObjectService = mapObjectService;
        this.driverRepository = driverRepository;
        this.roleRepository = roleRepository;
        this.emailService = emailService;
        this.environment = environment;
        this.clientUrl = environment.getProperty("client.url");
    }

    @Override
    public RegistrationDriverDto createDriver(
            String companyId, RegistrationDriverDto driver) {
        log.info("'createDriver' invoked with params'{}, {}'", companyId, driver);

        String encodedEmail;
        try {
            encodedEmail = URLEncoder.encode(driver.getEmail(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new IncorrectInputDataException("impossible to encode email");
        }

        String inviteKey = RandomStringUtils.randomAlphabetic(50);
        String message = clientUrl + "?email=" + encodedEmail + "&key=" + inviteKey + "&role=DRIVER";
        EmailMessage emailMessage = new EmailMessage(driver.getEmail(), "Confirm driver account",
                message);
        emailService.send(emailMessage);
        log.info("email sent successfully");

        Role driverRole = roleRepository.findByRole(RoleValues.DRIVER);
        Company company = companyRepository.findOne(companyId);

        Driver driverEntity = new Driver();
        driverEntity.setEmail(driver.getEmail());
        driverEntity.getRoles().add(driverRole);
        driverEntity.setInviteKey(inviteKey);
        driverEntity.setBoss(company);
        driverEntity = driverRepository.save(driverEntity);
        log.info("'driverEntity={}'", driverEntity);

        company.getDrivers().add(driverEntity);
        companyRepository.save(company);

        return driver;
    }

    @Override
    public RegistrationManagerDto createManager(
            String companyId, RegistrationManagerDto manager) {
        log.info("'createManager' invoked with params'{}, {}'", companyId, manager);

        String encodedEmail;
        try {
            encodedEmail = URLEncoder.encode(manager.getEmail(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new IncorrectInputDataException("impossible to encode email");
        }

        String inviteKey = RandomStringUtils.randomAlphabetic(50);
        String message = clientUrl + "?email=" + encodedEmail + "&key=" + inviteKey + "&role=MANAGER";
        EmailMessage emailMessage = new EmailMessage(manager.getEmail(), "Confirm manager account", message);
        emailService.send(emailMessage);
        log.info("email was sent");

        Role managerRole = roleRepository.findByRole(RoleValues.MANAGER);
        Company company = companyRepository.findOne(companyId);

        Manager managerEntity = new Manager();
        managerEntity.setEmail(manager.getEmail());
        managerEntity.getRoles().add(managerRole);
        managerEntity.setInviteKey(inviteKey);
        managerEntity.setBoss(company);
        managerEntity = managerRepository.save(managerEntity);
        log.info("'managerEntity={}'", managerEntity);

        company.getManagers().add(managerEntity);
        companyRepository.save(company);

        return manager;
    }

    @Override
    public void deleteDriver(String driverId, String companyId) {
        log.info("'deleteDriver' invoked with params'{}, {}'", driverId, companyId);

        Driver driver = driverRepository.findByIdAndBossId(driverId, companyId);

        if (driver == null) {

            String errorMessage = "driver not found or doesn't belong to company";
            log.error(errorMessage);
            throw new ResourceNotFoundException(errorMessage);

        }

        driverRepository.delete(driverId);
        log.info("deleted successfully");
    }

    @Override
    public void deleteManager(String managerId, String companyId) {
        log.info("'deleteManager' invoked with params'{}, {}'", managerId, companyId);

        Manager manager = managerRepository.findByIdAndBossId(managerId, companyId);

        if (manager == null) {

            String errorMessage = "manager not found or doesn't belong to company";
            log.error(errorMessage);
            throw new ResourceNotFoundException(errorMessage);

        }

        managerRepository.delete(managerId);
        log.info("deleted successfully");
    }

    @Override
    public List<DriverDto> getDrivers(String companyId) {
        log.info("'getDrivers' invoked with params'{}'", companyId);

        Company company = companyRepository.findOne(companyId);

        List<DriverDto> driverDtos = company.getDrivers()
                .stream().map(DriverDto::convert)
                .collect(Collectors.toList());
        log.info("'driverDtos={}'", driverDtos);

        return driverDtos;
    }

    @Override
    public List<ManagerDto> getManagers(String companyId) {
        log.info("'getManagers' invoked with params'{}'", companyId);

        Company company = companyRepository.findOne(companyId);
        List<ManagerDto> managers = company.getManagers()
                .stream().map(ManagerDto::convert)
                .collect(Collectors.toList());
        log.info("'managers={}'", managers);

        return managers;
    }

    @Override
    public MapObjectDto setEndpoint(String marker, MapObjectDto mapObjectDto, String companyId) {
        log.info("'setEndpoint' invoked with params '{}, {}, {}'");

        Company company = companyRepository.findOne(companyId);

        MapObject mapObject = MapObjectDto.convert(mapObjectDto);
        mapObject.setOwner(company);
        log.info("'saved mapObject={}'", mapObject);

        if (marker.equals(MapObjectRequestValues.START)) {

            mapObject.setDescription(MapObjectDescriptionValues.START.toString());
            mapObject = mapObjectRepository.save(mapObject);

            MapObject start = company.getStart();

            if (start != null) {
                log.info("delete old start={}", start);
                mapObjectRepository.delete(start.getId());
            }

            company.setStart(mapObject);

        } else {

            mapObject.setDescription(MapObjectDescriptionValues.FINISH.toString());
            mapObject = mapObjectRepository.save(mapObject);

            MapObject finish = company.getFinish();
            log.info("'old finish={}'", finish);

            if (finish != null) {
                mapObjectRepository.delete(finish.getId());
            }

            company.setFinish(mapObject);
        }

        company = companyRepository.save(company);
        log.info("saved company{}", company);

        mapObjectService.processRoutes(companyId, mapObject);

        return MapObjectDto.convert(mapObject);
    }

    @Override
    public void deleteEndpoint(String mapObjectId, String companyId) {
        log.info("'deleteEndpoint' invoked with params'{}, {}'", mapObjectId, companyId);

        MapObject endpoint = mapObjectRepository.findOne(mapObjectId);
        log.info("'endpoint={}'", endpoint);

        if (endpoint == null) {

            String errorMessage = "endpoint with id'" + mapObjectId + "' not found";
            log.error(errorMessage);
            throw new ResourceNotFoundException(errorMessage);

        } else if (!endpoint.getOwner().getId().equals(companyId)) {

            String errorMessage = "map object doesn't belong to user";
            log.error(errorMessage);
            throw new ResourseDoesntBelongToUserException(errorMessage);

        }

        mapObjectRepository.delete(mapObjectId);
        log.info("'deleted successfully");
    }

    @Override
    public List<CompanyDto> getCompanies() {
        log.info("'getCompanies' invoked");

        Role role = roleRepository.findByRole(RoleValues.COMPANY);

        List<Company> all = companyRepository.findAllByRolesContains(role);

        List<CompanyDto> driverDtos = all
                .stream().map(CompanyDto::convert)
                .collect(Collectors.toList());
        log.info("'driverDtos={}'", driverDtos);

        return driverDtos;
    }
}
