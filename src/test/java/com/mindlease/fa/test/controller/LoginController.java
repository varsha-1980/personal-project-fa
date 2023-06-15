package com.mindlease.fa.test.controller;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.FormLoginRequestBuilder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.mindlease.fa.model.User;
import com.mindlease.fa.repository.RoleRepository;
import com.mindlease.fa.security.CustomUserDetailsService;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
@Slf4j
@ActiveProfiles("test")
public class LoginController {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private CustomUserDetailsService userService;

	@Autowired
	private RoleRepository roleRepository;

	@Before
	public void setUp() {
		User newUser = new User();
		newUser.setFirstName("Test Name");
		newUser.setLastName("Test Initial");
		newUser.setEmail("test@gmail.com");
		newUser.setPassword("test123");
		newUser.setRole(roleRepository.findAll().get(0));
		User savedUser = userService.save(newUser);
	}

	static FormLoginRequestBuilder login() {
		return SecurityMockMvcRequestBuilders.formLogin("/login").userParameter("username").passwordParam("password");
	}

	@Test
	public void authenticationSuccess() throws Exception {
		this.mockMvc.perform(login().user("test@gmail.com").password("test123")).andExpect(status().isFound())
				.andExpect(redirectedUrl("/home")).andExpect(authenticated().withUsername("test@gmail.com"));
	}

	@Test
	public void authenticationFailed() throws Exception {
		mockMvc.perform(login().user("notfound").password("invalid")).andExpect(status().isFound())
				.andExpect(redirectedUrl("/login?error")).andExpect(unauthenticated());
	}

	@Test
	public void authenticatedRedirection() throws Exception {
		mockMvc.perform(login().user("test@gmail.com").password("test123")).andExpect(status().isFound()).andExpect(redirectedUrl("/home"))
				.andExpect(authenticated().withUsername("test@gmail.com"));
		log.info("login successful------------");
		mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(view().name("login"))
				.andExpect(MockMvcResultMatchers.view().name("login"));
	}

}
