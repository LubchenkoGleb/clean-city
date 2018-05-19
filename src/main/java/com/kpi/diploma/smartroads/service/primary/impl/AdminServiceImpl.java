package com.kpi.diploma.smartroads.service.primary.impl;

import com.kpi.diploma.smartroads.model.document.map.Container;
import com.kpi.diploma.smartroads.model.document.user.*;
import com.kpi.diploma.smartroads.model.util.title.value.RoleValues;
import com.kpi.diploma.smartroads.repository.map.ContainerRepository;
import com.kpi.diploma.smartroads.repository.map.MapObjectRepository;
import com.kpi.diploma.smartroads.repository.map.RouteRepository;
import com.kpi.diploma.smartroads.repository.map.TaskRepository;
import com.kpi.diploma.smartroads.repository.user.*;
import com.kpi.diploma.smartroads.service.primary.AdminService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final RouteRepository routeRepository;
    private final TaskRepository taskRepository;
    private final MapObjectRepository mapObjectRepository;
    private final ContainerRepository containerRepository;

    public AdminServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,
                            RoleRepository roleRepository, RouteRepository routeRepository,
                            TaskRepository taskRepository, MapObjectRepository mapObjectRepository,
                            ContainerRepository containerRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.routeRepository = routeRepository;
        this.taskRepository = taskRepository;
        this.mapObjectRepository = mapObjectRepository;
        this.containerRepository = containerRepository;
    }

    @Override
    public void fillDbWithInitData() {
        roleRepository.deleteAll();
        mapObjectRepository.deleteAll();
        userRepository.deleteAll();
        routeRepository.deleteAll();
        taskRepository.deleteAll();

        initRoles();
        initUsers();
    }

    @Override
    public void setAllContainersToPending() {
        List<Container> containers = containerRepository.findAll();
        containers.forEach(c -> c.getDetails().forEach(d -> d.setPending(false)));
        containerRepository.save(containers);
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
            Company savedCompany = userRepository.save(company);

            drivers.forEach(dr -> dr.setBoss(savedCompany));
            userRepository.save(drivers);

            manager.forEach(mn -> mn.setBoss(savedCompany));
            userRepository.save(manager);
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
            driver.setPassword(passwordEncoder.encode("1234"));
            driver.setEnable(true);
            driver.getRoles().add(driverRole);
            driver = userRepository.save(driver);
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
            manager.setPassword(passwordEncoder.encode("1234"));
            manager.setEnable(true);
            manager.getRoles().add(managerRole);
            manager = userRepository.save(manager);
            managers.add(manager);
        }
        return managers;
    }
}
