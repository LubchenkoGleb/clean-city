package com.kpi.diploma.smartroads;

import com.kpi.diploma.smartroads.model.document.Role;
import com.kpi.diploma.smartroads.model.document.user.Company;
import com.kpi.diploma.smartroads.model.document.user.Driver;
import com.kpi.diploma.smartroads.model.document.user.Manager;
import com.kpi.diploma.smartroads.model.title.Constants;
import com.kpi.diploma.smartroads.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@Slf4j
public class SmartRoadsApplication implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final CompanyRepository companyRepository;
    private final DriverRepository driverRepository;
    private final ManagerRepository managerRepository;

    @Autowired
    public SmartRoadsApplication(UserRepository userRepository,
                                 PasswordEncoder passwordEncoder,
                                 RoleRepository roleRepository,
                                 CompanyRepository companyRepository,
                                 DriverRepository driverRepository,
                                 ManagerRepository managerRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.companyRepository = companyRepository;
        this.driverRepository = driverRepository;
        this.managerRepository = managerRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(SmartRoadsApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        initRoles();
        initCompanies();
        driverForConfirmation();
        managerForConfirmation();
        log.info("test data was init");
    }

    private void initRoles() {
        roleRepository.deleteAll();
        Role roleCompany = new Role("COMPANY");
        Role roleDriver = new Role("DRIVER");
        Role roleManager = new Role("MANAGER");
        roleRepository.save(roleCompany);
        roleRepository.save(roleDriver);
        roleRepository.save(roleManager);
    }

    private void initCompanies() {
        Role companyRole = roleRepository.findByRole(Constants.ROLE_COMPANY);

        companyRepository.deleteAll();
        Company company = new Company();
        company.setEmail("company1");
        company.setFirstName("cp1");
        company.setLastName("cp1");
        company.setPassword(passwordEncoder.encode("1234"));
        company.setEnable(true);
        company.getRoles().add(companyRole);
        companyRepository.save(company);
    }

    private void driverForConfirmation() {
        Driver driver = new Driver();
        driver.setEmail("test2");
        driver.setInviteKey("test2");
        driverRepository.save(driver);
    }

    private void managerForConfirmation() {
        Manager manager = new Manager();
        manager.setEmail("test1");
        manager.setInviteKey("key1");
        managerRepository.save(manager);
    }
}