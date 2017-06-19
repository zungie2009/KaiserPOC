package org.kp.poc.demo;

import java.util.ArrayList;
import java.util.Collection;

import org.kp.poc.demo.model.Role;
import org.kp.poc.demo.model.User;
import org.kp.poc.demo.repository.RoleRepository;
import org.kp.poc.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;


@ServletComponentScan
@EnableScheduling
@SpringBootApplication
public class KaiserPocApplication implements CommandLineRunner {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserService userService;
	
	public static void main(String[] args) {
		SpringApplication.run(KaiserPocApplication.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		initRoles();
		Role userRole = roleRepository.findRoleByRoleName("USER");
		
		createUsers(userRole);
	}
	
	public void createUsers(Role role) throws Exception {
		logger.info("************************Initialize First Time Users");
		User firstUser = getFirstUser();

		Collection<Role> collectionRole = new ArrayList<Role>();
		Role roletwo = roleRepository.findRoleByRoleName("ADMIN");

		collectionRole.add(role);
		collectionRole.add(roletwo);
		firstUser.setRole(collectionRole);
		userService.create(firstUser);

		User secondUser = getSecondUser();
		secondUser.setRole(collectionRole);
		userService.create(secondUser);
	}
	
	public void initRoles() {
		roleRepository.save(new Role("ADMIN", "Administrator role (can edit Users)"));
		roleRepository.save(new Role("USER", "Default role for all Users"));
	}
	
	public static User getFirstUser() {
		User user = new User();
		user.setFirst_name("Robert");
		user.setLast_name("Tu");
		user.setUsername("user1");
		user.setPassword("test");
		user.setEmail("robert.tu@mycompany.com");
		user.setDisplayEmailFlag(true);
        user.setMember_type("Application Architect");
		return user;
	}
	
	public static User getSecondUser() {
		User user = new User();
		user.setFirst_name("John");
		user.setLast_name("Smith");
		user.setUsername("user2");
		user.setPassword("test");
		user.setEmail("john.smith@mycompany.com");
		user.setDisplayEmailFlag(true);
        user.setMember_type("Web Developer");
		return user;
	}
}
