package com.kpi.diploma.smartroads;

import com.kpi.diploma.smartroads.model.document.Role;
import com.kpi.diploma.smartroads.model.document.user.Company;
import com.kpi.diploma.smartroads.model.title.Constants;
import com.kpi.diploma.smartroads.repository.CompanyRepository;
import com.kpi.diploma.smartroads.repository.RoleRepository;
import com.kpi.diploma.smartroads.repository.UserRepository;
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

    @Autowired
    public SmartRoadsApplication(UserRepository userRepository,
                                 PasswordEncoder passwordEncoder,
                                 RoleRepository roleRepository,
                                 CompanyRepository companyRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.companyRepository = companyRepository;
    }


    public static void main(String[] args) {
        SpringApplication.run(SmartRoadsApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        initRoles();
        initCompanies();
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
}
