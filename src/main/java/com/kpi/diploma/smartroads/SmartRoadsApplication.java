package com.kpi.diploma.smartroads;

import com.kpi.diploma.smartroads.model.document.map.Container;
import com.kpi.diploma.smartroads.model.document.user.Role;
import com.kpi.diploma.smartroads.model.document.map.MapObject;
import com.kpi.diploma.smartroads.model.document.user.Company;
import com.kpi.diploma.smartroads.model.document.user.Driver;
import com.kpi.diploma.smartroads.model.document.user.Manager;
import com.kpi.diploma.smartroads.model.document.user.User;
import com.kpi.diploma.smartroads.model.util.data.MapObjectDetail;
import com.kpi.diploma.smartroads.model.util.title.value.MapObjectDescriptionValues;
import com.kpi.diploma.smartroads.model.util.title.value.ContainerValues;
import com.kpi.diploma.smartroads.model.util.title.value.RoleValues;
import com.kpi.diploma.smartroads.repository.map.MapObjectRepository;
import com.kpi.diploma.smartroads.repository.map.RouteRepository;
import com.kpi.diploma.smartroads.repository.user.*;
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
    private final RouteRepository routeRepository;
    private final CompanyRepository companyRepository;
    private final DriverRepository driverRepository;
    private final ManagerRepository managerRepository;
    private final MapObjectRepository mapObjectRepository;

    @Autowired
    public SmartRoadsApplication(UserRepository userRepository,
                                 PasswordEncoder passwordEncoder,
                                 RoleRepository roleRepository,
                                 RouteRepository routeRepository,
                                 CompanyRepository companyRepository,
                                 DriverRepository driverRepository,
                                 ManagerRepository managerRepository,
                                 MapObjectRepository mapObjectRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.routeRepository = routeRepository;
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
        roleRepository.deleteAll();
        mapObjectRepository.deleteAll();
        userRepository.deleteAll();
        routeRepository.deleteAll();

        initRoles();
        initUsers();

        log.info("test data was init");
    }

    private void initRoles() {
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
        initAdmin();
        initCompanies();
        driverForConfirmation();
        managerForConfirmation();
    }

    private void initAdmin() {
        Role adminRole = roleRepository.findByRole(RoleValues.ADMIN);

        User admin = new User();
        admin.setId("a1");
        admin.setEmail("admin");
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setEnable(true);
        admin.getRoles().add(adminRole);
        userRepository.save(admin);
    }


    private void initCompanies() {
        Role companyRole = roleRepository.findByRole(RoleValues.COMPANY);

        for (int i = 0; i < 3; i++) {

            List<Driver> drivers = testDrivers("c" + i);
            List<Manager> manager = testManagers("c" + i);

            Company company = new Company();
            company.setId("c" + i);
            company.setEmail("company" + i);
            company.setTitle("cp" + i);
            company.setPassword(passwordEncoder.encode("1234"));
            company.setEnable(true);
            company.getRoles().add(companyRole);
            company.getDrivers().addAll(drivers);
            company.getManagers().addAll(manager);
            Company savedCompany = companyRepository.save(company);

            drivers.forEach(dr -> dr.setBoss(savedCompany));
            driverRepository.save(drivers);

            manager.forEach(mn -> mn.setBoss(savedCompany));
            managerRepository.save(manager);

            if(i != 2) {
                initContainers(company);
            }
        }
    }

    private List<Driver> testDrivers(String companyName) {
        Role driverRole = roleRepository.findByRole(RoleValues.DRIVER);

        ArrayList<Driver> drivers = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            Driver driver = new Driver();
            driver.setId("d" + i + "_" + companyName);
            driver.setEmail("driver" + i + "_" + companyName + "@email.com");
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

    private List<Manager> testManagers(String companyName) {
        Role managerRole = roleRepository.findByRole(RoleValues.MANAGER);

        ArrayList<Manager> managers = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            Manager manager = new Manager();
            manager.setId("m" + i + "_" + companyName);
            manager.setEmail("manager" + i + "_" + companyName + "@email.com");
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

    private void initContainers(User owner) {

        MapObject start = new MapObject();
        start.setLon((Math.random() * 0.2) + 30.4);
        start.setLat((Math.random() * 0.2) + 50.3);
        start.setOwner(owner);
        start.setDescription(MapObjectDescriptionValues.START.toString());

        List<Container> mapObjects = new ArrayList<>();
        for (int i = 0; i < 5; i++) {

            Container container = new Container();
            container.setId("m" + i + "_" + owner.getId());
            container.setOwner(owner);
            container.setLon((Math.random() * 0.2) + 30.4);
            container.setLat((Math.random() * 0.2) + 50.3);
            container.setDescription(MapObjectDescriptionValues.CONTAINER.toString());

            MapObjectDetail mapObjectDetailGlass = new MapObjectDetail();
            mapObjectDetailGlass.setType(ContainerValues.GLASS);
            mapObjectDetailGlass.setAmount(((Double) (Math.random() * 5)).intValue());

            MapObjectDetail mapObjectDetailPlastic = new MapObjectDetail();
            mapObjectDetailPlastic.setType(ContainerValues.PLASTIC);
            mapObjectDetailPlastic.setAmount(((Double) (Math.random() * 5)).intValue());

            MapObjectDetail mapObjectDetailPaper = new MapObjectDetail();
            mapObjectDetailPaper.setType(ContainerValues.PAPER);
            mapObjectDetailPaper.setAmount(((Double) (Math.random() * 5)).intValue());

            container.getDetails()
                    .addAll(Arrays.asList(mapObjectDetailGlass, mapObjectDetailPlastic, mapObjectDetailPaper));
            mapObjects.add(container);
        }
        mapObjectRepository.save(mapObjects);
    }
}