package org.kp.poc.demo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kp.poc.demo.model.Role;
import org.kp.poc.demo.model.User;
import org.kp.poc.demo.repository.RoleRepository;
import org.kp.poc.demo.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KaiserPocApplicationTests {
	private String testUserName = "user3";
	private Role userRole;
	private Role adminRole;
	private User testUser = null;
	
	@Autowired
	private UserServiceImpl userService;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@BeforeClass
	public static void setUp() {
		System.out.println("----- Setup Test here -----");
	}
	
	@Before
	public void preTest() {
		System.out.println("*********************Initialize User Test Objects");
		Optional<User> optUser = userService.getUserByUsername(testUserName);
		if(optUser.isPresent()) {
			// If this Record was not cleaned up from the last test, then delete it
			System.out.println("*********************Found Test User. Delete");
			userService.deleteUser(optUser.get().getId());
		}
		// Load/Init Roles
		this.userRole = getRole("USER");
		this.adminRole = getRole("ADMIN");
	}
	
	@Test
	public void contextLoads() {
		assertNotNull("User Role is not null", userRole);
		System.out.println("*********************Found User Role: " + userRole.getRoleName());
		assertNotNull("User Role is not null", adminRole);
		System.out.println("*********************Found Admin Role: " + adminRole.getRoleName());
				
		System.out.println("*********************Creating Test User");

		testUser = createUser(testUserName);
		
		Collection<Role> roles = new ArrayList<Role>();
        roles.add(userRole);
        roles.add(adminRole);
        testUser.setRole(roles);
        testUser.setEnabled(true);
        String plainTextPassword = testUser.getPassword();  // For testing after Save
        
        User savedTestUser = userService.create(testUser);
        System.out.println("*********************Test User ID: " + savedTestUser.getId());
		System.out.println("*********************Test User Name: " + savedTestUser.getUsername());
		System.out.println("*********************Test User Password: " + savedTestUser.getPassword());
		
		assertNotNull("User is not null", savedTestUser);
		
		// Check Encrypted Password
		String encryptedPassword = savedTestUser.getPassword();
		assertTrue(BCrypt.checkpw(plainTextPassword, encryptedPassword));
	}
	
	protected User createUser(String username) {
        User user = new User();
        //all the user fields
        user.setUsername(username);
        user.setPassword("testing");
        user.setFirst_name("TestFirst");
        user.setLast_name("TestLast");
        user.setEmail("test.user@mycompany.com");
        return user;
    }
	
	private Role getRole(String name) {
		return roleRepository.findRoleByRoleName(name);
	}
	
	@After
	public void cleanUp() {
		System.out.println("*********************Cleanup/Delete Test User");
		if(testUser != null) {
			userService.deleteUser(testUser.getId());
		}
	}
	
	@AfterClass
	public static void afterTest() {
		System.out.println("----- End Kaiser User Creation Tests -----");
	}

}
