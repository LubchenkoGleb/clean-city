package com.kpi.diploma.smartroads.service.main.impl;

import com.kpi.diploma.smartroads.model.document.Role;
import com.kpi.diploma.smartroads.model.document.user.Company;
import com.kpi.diploma.smartroads.model.document.user.Driver;
import com.kpi.diploma.smartroads.model.document.user.Manager;
import com.kpi.diploma.smartroads.model.dto.DriverDto;
import com.kpi.diploma.smartroads.model.dto.ManagerDto;
import com.kpi.diploma.smartroads.model.dto.RegistrationDriverDto;
import com.kpi.diploma.smartroads.model.dto.RegistrationManagerDto;
import com.kpi.diploma.smartroads.model.title.Constants;
import com.kpi.diploma.smartroads.model.util.EmailMessage;
import com.kpi.diploma.smartroads.repository.CompanyRepository;
import com.kpi.diploma.smartroads.repository.DriverRepository;
import com.kpi.diploma.smartroads.repository.ManagerRepository;
import com.kpi.diploma.smartroads.repository.RoleRepository;
import com.kpi.diploma.smartroads.service.main.CompanyService;
import com.kpi.diploma.smartroads.service.util.email.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
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

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository,
                              ManagerRepository managerRepository,
                              DriverRepository driverRepository,
                              RoleRepository roleRepository,
                              EmailService emailService) {
        this.companyRepository = companyRepository;
        this.managerRepository = managerRepository;
        this.driverRepository = driverRepository;
        this.roleRepository = roleRepository;
        this.emailService = emailService;
    }

    @Override
    public RegistrationDriverDto createDriver(
            String companyId, RegistrationDriverDto driver) throws MessagingException {
        log.info("'createDriver' invoked with params'{}, {}'", companyId, driver);

        Role driverRole = roleRepository.findByRole(Constants.ROLE_DRIVER);

        Driver driverEntity = new Driver();
        driverEntity.setEmail(driver.getEmail());
        driverEntity.getRoles().add(driverRole);
        driverEntity.setInviteKey(RandomStringUtils.randomAlphabetic(50));
        driverEntity = driverRepository.save(driverEntity);
        log.info("'driverEntity={}'", driverEntity);

        EmailMessage emailMessage = new EmailMessage(driver.getEmail(), "Confirm driver account",
                "http://tempurl.com?email=" + driver.getEmail() + "&key=" + driverEntity.getInviteKey());
        emailService.send(emailMessage);
        log.info("email is sent");

        Company company = companyRepository.findOne(companyId);
        company.getDrivers().add(driverEntity);
        companyRepository.save(company);

        return driver;
    }

    @Override
    public RegistrationManagerDto createManager(
            String companyId, RegistrationManagerDto manager) throws MessagingException {
        log.info("'createManager' invoked with params'{}, {}'", companyId, manager);

        Role managerRole = roleRepository.findByRole(Constants.ROLE_MANGER);

        Manager managerEntity = new Manager();
        managerEntity.setEmail(manager.getEmail());
        managerEntity.getRoles().add(managerRole);
        managerEntity.setInviteKey(RandomStringUtils.randomAlphabetic(50));
        managerEntity = managerRepository.save(managerEntity);
        log.info("'managerEntity={}'", managerEntity);

        EmailMessage emailMessage = new EmailMessage(manager.getEmail(), "Confirm manager account",
                "http://tempurl.com?email=" + manager.getEmail() + "&key=" + managerEntity.getInviteKey());
        emailService.send(emailMessage);
        log.info("email is sent");

        Company company = companyRepository.findOne(companyId);
        company.getManagers().add(managerEntity);
        companyRepository.save(company);

        return manager;
    }

    @Override
    public List<DriverDto> getDrivers(String companyId) {
        log.info("'getDrivers' invoked with params'{}'", companyId);

        Company company = companyRepository.findOne(companyId);
        List<DriverDto> drivers = company.getDrivers()
                .stream().map(DriverDto::convert).collect(Collectors.toList());
        log.info("'drivers={}'", drivers);

        return drivers;
    }

    @Override
    public List<ManagerDto> getManagers(String companyId) {
        log.info("'getManagers' invoked with params'{}'", companyId);

        Company company = companyRepository.findOne(companyId);
        List<ManagerDto> managers = company.getManagers()
                .stream().map(ManagerDto::convert).collect(Collectors.toList());
        log.info("'managers={}'", managers);

        return managers;
    }
}
