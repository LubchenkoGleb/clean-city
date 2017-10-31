package com.kpi.diploma.smartroads.service.primary.impl;

import com.kpi.diploma.smartroads.model.document.Role;
import com.kpi.diploma.smartroads.model.document.user.Company;
import com.kpi.diploma.smartroads.model.document.user.Driver;
import com.kpi.diploma.smartroads.model.document.user.Manager;
import com.kpi.diploma.smartroads.model.dto.DriverDto;
import com.kpi.diploma.smartroads.model.dto.ManagerDto;
import com.kpi.diploma.smartroads.model.dto.RegistrationDriverDto;
import com.kpi.diploma.smartroads.model.dto.RegistrationManagerDto;
import com.kpi.diploma.smartroads.model.util.data.EmailMessage;
import com.kpi.diploma.smartroads.model.util.title.value.RoleValues;
import com.kpi.diploma.smartroads.repository.CompanyRepository;
import com.kpi.diploma.smartroads.repository.DriverRepository;
import com.kpi.diploma.smartroads.repository.ManagerRepository;
import com.kpi.diploma.smartroads.repository.RoleRepository;
import com.kpi.diploma.smartroads.service.primary.CompanyService;
import com.kpi.diploma.smartroads.service.util.email.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final ManagerRepository managerRepository;
    private final DriverRepository driverRepository;
    private final RoleRepository roleRepository;
    private final EmailService emailService;
    private final Environment environment;
    private final String clientUrl;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository,
                              ManagerRepository managerRepository,
                              DriverRepository driverRepository,
                              RoleRepository roleRepository,
                              EmailService emailService,
                              Environment environment) {
        this.companyRepository = companyRepository;
        this.managerRepository = managerRepository;
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

        String inviteKey = RandomStringUtils.randomAlphabetic(50);
        String message = clientUrl + "?email=" + driver.getEmail() + "&key=" + inviteKey + "&role=DRIVER";
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

        String inviteKey = RandomStringUtils.randomAlphabetic(50);
        String message = clientUrl + "?email=" + manager.getEmail() + "&key=" + inviteKey + "&role=MANAGER";
        EmailMessage emailMessage = new EmailMessage(manager.getEmail(), "Confirm manager account", message);
        emailService.send(emailMessage);
        log.info("email is sent");

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
}
