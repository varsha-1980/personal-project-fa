package com.mindlease.fa.test.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

import com.mindlease.fa.model.Role;
import com.mindlease.fa.model.User;
import com.mindlease.fa.repository.RoleRepository;
import com.mindlease.fa.repository.UserRepository;
import com.mindlease.fa.security.CustomUserDetailsService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@Slf4j
@ActiveProfiles("test")
public class CustomUserDetailsServiceTest {

	@Autowired
	CustomUserDetailsService customUserDetailsService;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;

	@Test
	void getAllloadUserByUsernameTest() {
		UserDetails userDetails = customUserDetailsService.loadUserByUsername("admin@gmail.com");
		assertEquals("admin@gmail.com", userDetails.getUsername());
	}
	
	@Test
	void testSave() {
		User newUser = new User();
		newUser.setFirstName("Test Name");
		newUser.setLastName("Test Initial");
		newUser.setEmail("test@gmail.com");
		newUser.setPassword("test123");
		newUser.setRole(roleRepository.findAll().get(0));
		User savedUser = customUserDetailsService.save(newUser);
		assertNotNull(savedUser);
		assertNotNull(savedUser.getId());
		assertEquals("test@gmail.com", savedUser.getEmail());
	}
	
	@Test
	void testUpdate() {
		User newUser = userRepository.findByEmail("test@gmail.com").get();
		newUser.setFirstName("Updated Test Name");
		Role role = newUser.getRoles().iterator().next();
		assertNotNull(role);
		newUser.setRole(role );
		assertNotNull(newUser.getRole());
		System.out.println("----------:::::"+newUser.getRole());
		log.info("Befor update - {}", newUser.getPassword());
		User savedUser = customUserDetailsService.save(newUser);
		assertNotNull(savedUser);
		assertNotNull(savedUser.getId());
		assertEquals("Updated Test Name", savedUser.getFirstName());
	}
	
	@Test
	void testGetUsers() {
		List<User> dblist = userRepository.findAll();
		List<User> fromservice = customUserDetailsService.getUsers();
		assertEquals(dblist.size(), fromservice.size());
	}
	
	@Test
	void testFindByEmail() {
		User newUser = userRepository.findByEmail("test@gmail.com").get();
		assertEquals("test@gmail.com", newUser.getEmail());
	}
	
	@Test
	void testFindById() {
		User newUser = userRepository.findByEmail("test@gmail.com").get();
		User fromservice = customUserDetailsService.findById(Optional.of(newUser.getId())).get();
		assertEquals(newUser.getId(), fromservice.getId());
	}
	
	@Test
	void testDdeleteById() {
		User newUser = userRepository.findByEmail("test@gmail.com").get();
		customUserDetailsService.deleteById(newUser.getId());
		assertFalse(Optional.of(userRepository.findByEmail("test@gmail.com").isPresent()).get());
	}

}
