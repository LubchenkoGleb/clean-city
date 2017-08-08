package com.kpi.diploma.smartroads;

import com.kpi.diploma.smartroads.model.Role;
import com.kpi.diploma.smartroads.model.User;
import com.kpi.diploma.smartroads.repository.RoleRepository;
import com.kpi.diploma.smartroads.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.HashSet;

@SpringBootApplication
@Slf4j
//@ComponentScan("com.kpi.diploma.smartroads.*")
public class SmartRoadsApplication implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(SmartRoadsApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {

		User user1 = new User("email1", "pass1");
		Role test1 = new Role("test1");
		Role test2 = new Role("test2");
//		roleRepository.save(test1);
//		roleRepository.save(test2);
		user1.getRoles().add(test1);
		user1.getRoles().add(test2);
		userRepository.save(user1);

		for (User user : userRepository.findAll()) {
			log.info(user.toString());
		}
	}
}
