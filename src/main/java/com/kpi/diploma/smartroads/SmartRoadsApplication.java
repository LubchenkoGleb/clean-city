package com.kpi.diploma.smartroads;

import com.kpi.diploma.smartroads.model.document.MapObject;
import com.kpi.diploma.smartroads.model.document.Role;
import com.kpi.diploma.smartroads.model.document.user.Company;
import com.kpi.diploma.smartroads.model.document.user.Driver;
import com.kpi.diploma.smartroads.model.document.user.Manager;
import com.kpi.diploma.smartroads.model.document.user.User;
import com.kpi.diploma.smartroads.model.util.data.MapObjectDetail;
import com.kpi.diploma.smartroads.model.util.title.value.MapObjectValues;
import com.kpi.diploma.smartroads.model.util.title.value.RoleValues;
import com.kpi.diploma.smartroads.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@Slf4j
public class SmartRoadsApplication implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final CompanyRepository companyRepository;
    private final DriverRepository driverRepository;
    private final ManagerRepository managerRepository;
    private final MapObjectRepository mapObjectRepository;

    @Autowired
    public SmartRoadsApplication(UserRepository userRepository,
                                 PasswordEncoder passwordEncoder,
                                 RoleRepository roleRepository,
                                 CompanyRepository companyRepository,
                                 DriverRepository driverRepository,
                                 ManagerRepository managerRepository,
                                 MapObjectRepository mapObjectRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.companyRepository = companyRepository;
        this.driverRepository = driverRepository;
        this.managerRepository = managerRepository;
        this.mapObjectRepository = mapObjectRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(SmartRoadsApplication.class, args);
    }

    @Override
    public void run(String... strings) {
        initRoles();
        initUsers();
        log.info("test data was init");
    }

    private void initRoles() {
        roleRepository.deleteAll();
        Role roleCompany = new Role(RoleValues.COMPANY);
        Role roleDriver = new Role(RoleValues.DRIVER);
        Role roleManager = new Role(RoleValues.MANAGER);
        Role roleAdmin = new Role(RoleValues.ADMIN);
        roleRepository.save(roleCompany);
        roleRepository.save(roleDriver);
        roleRepository.save(roleManager);
        roleRepository.save(roleAdmin);
    }

    private void initUsers() {
        userRepository.deleteAll();

        initAdmin();
        initCompanies();
        driverForConfirmation();
        managerForConfirmation();
    }

    private void initAdmin() {
        Role adminRole = roleRepository.findByRole(RoleValues.ADMIN);

        User admin = new User();
        admin.setEmail("admin");
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setEnable(true);
        admin.getRoles().add(adminRole);
        userRepository.save(admin);
    }


    private void initCompanies() {
        Role companyRole = roleRepository.findByRole(RoleValues.COMPANY);

        Company company = new Company();
        company.setEmail("company1");
        company.setFirstName("cp1");
        company.setLastName("cp1");
        company.setPassword(passwordEncoder.encode("1234"));
        company.setEnable(true);
        company.getRoles().add(companyRole);
        company.getDrivers().addAll(testDrivers());
        company.getManagers().addAll(testManagers());
        companyRepository.save(company);

        initMapObjects(company);
    }

    private List<Driver> testDrivers() {
        Role driverRole = roleRepository.findByRole(RoleValues.DRIVER);

        ArrayList<Driver> drivers = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            Driver driver = new Driver();
            driver.setEmail("driver" + i + "@email.com");
            driver.setFirstName("driver" + i + "_firstName");
            driver.setLastName("driver" + i + "_lastName");
            driver.setPassword(passwordEncoder.encode("driver" + i));
            driver.setEnable(true);
            driver.getRoles().add(driverRole);
            driver = driverRepository.save(driver);
            drivers.add(driver);
        }
        return drivers;
    }

    private List<Manager> testManagers() {
        Role managerRole = roleRepository.findByRole(RoleValues.MANAGER);

        ArrayList<Manager> managers = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            Manager manager = new Manager();
            manager.setEmail("manager" + i + "@email.com");
            manager.setFirstName("manager" + i + "_firstName");
            manager.setLastName("manager" + i + "_lastName");
            manager.setPassword(passwordEncoder.encode("manager" + i));
            manager.setEnable(true);
            manager.getRoles().add(managerRole);
            manager = managerRepository.save(manager);
            managers.add(manager);
        }
        return managers;
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

    private void initMapObjects(User owner) {
        mapObjectRepository.deleteAll();
        for (int i = 0; i < 10; i++) {
            MapObject mapObject = new MapObject();
            mapObject.setOwner(owner);
            mapObject.setLon((Math.random() * 5) + 30);
            mapObject.setLat((Math.random() * 5) + 50);

            MapObjectDetail mapObjectDetailGlass = new MapObjectDetail();
            mapObjectDetailGlass.setType(MapObjectValues.GLASS);
            mapObjectDetailGlass.setAmount(((Double) (Math.random() * 5)).intValue());

            MapObjectDetail mapObjectDetailPlastic = new MapObjectDetail();
            mapObjectDetailPlastic.setType(MapObjectValues.PLASTIC);
            mapObjectDetailPlastic.setAmount(((Double) (Math.random() * 5)).intValue());

            MapObjectDetail mapObjectDetailPaper = new MapObjectDetail();
            mapObjectDetailPaper.setType(MapObjectValues.PAPER);
            mapObjectDetailPaper.setAmount(((Double) (Math.random() * 5)).intValue());

            mapObject.getDetails()
                    .addAll(Arrays.asList(mapObjectDetailGlass, mapObjectDetailPlastic, mapObjectDetailPaper));
            mapObjectRepository.save(mapObject);
        }
    }
}