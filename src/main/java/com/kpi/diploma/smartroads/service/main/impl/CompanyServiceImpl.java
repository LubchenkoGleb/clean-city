package com.kpi.diploma.smartroads.service.main.impl;

import com.kpi.diploma.smartroads.model.document.Role;
import com.kpi.diploma.smartroads.model.document.user.Company;
import com.kpi.diploma.smartroads.model.document.user.Driver;
import com.kpi.diploma.smartroads.model.document.user.Manager;
import com.kpi.diploma.smartroads.model.dto.RegistrationDriverDto;
import com.kpi.diploma.smartroads.model.dto.RegistrationManagerDto;
import com.kpi.diploma.smartroads.model.title.Constants;
import com.kpi.diploma.smartroads.repository.CompanyRepository;
import com.kpi.diploma.smartroads.repository.DriverRepository;
import com.kpi.diploma.smartroads.repository.ManagerRepository;
import com.kpi.diploma.smartroads.repository.RoleRepository;
import com.kpi.diploma.smartroads.service.main.CompanyService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final ManagerRepository managerRepository;
    private final DriverRepository driverRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository,
                              ManagerRepository managerRepository,
                              DriverRepository driverRepository,
                              RoleRepository roleRepository) {
        this.companyRepository = companyRepository;
        this.managerRepository = managerRepository;
        this.driverRepository = driverRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public RegistrationDriverDto createDriver(String companyId, RegistrationDriverDto driver) {
        log.info("'createDriver' invoked with params'{}, {}'", companyId, driver);

        Role driverRole = roleRepository.findByRole(Constants.ROLE_DRIVER);

        Driver driverEntity = driver.convert();
        driverEntity.getRoles().add(driverRole);
        driverEntity.setInviteUrl(RandomStringUtils.randomAlphabetic(50));
        driverEntity = driverRepository.save(driverEntity);
        log.info("'driverEntity={}'", driverEntity);

        Company company = companyRepository.findOne(companyId);
        company.getDrivers().add(driverEntity);
        companyRepository.save(company);

        return driver;
    }

    @Override
    public RegistrationManagerDto createManager(String companyId, RegistrationManagerDto manager) {
        log.info("'createManager' invoked with params'{}, {}'", companyId, manager);

        Role managerRole = roleRepository.findByRole(Constants.ROLE_MANGER);

        Manager managerEntity = manager.convert();
        managerEntity.getRoles().add(managerRole);
        managerEntity.setInviteUrl(RandomStringUtils.randomAlphabetic(50));
        managerEntity = managerRepository.save(managerEntity);
        log.info("'managerEntity={}'", managerEntity);

        Company company = companyRepository.findOne(companyId);
        company.getManagers().add(managerEntity);
        companyRepository.save(company);

        return manager;
    }
}
