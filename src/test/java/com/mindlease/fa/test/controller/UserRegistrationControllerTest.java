package com.mindlease.fa.test.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.mindlease.fa.model.Role;
import com.mindlease.fa.model.User;
import com.mindlease.fa.repository.CompanyRepository;
import com.mindlease.fa.repository.RoleRepository;
import com.mindlease.fa.security.CustomUserDetailsService;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
@Slf4j
@ActiveProfiles("test")
public class UserRegistrationControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private CustomUserDetailsService userService;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private CompanyRepository companyRepository;

	@Test
	@WithMockUser(username = "admin@gmail.com", password = "admin", roles = "SUPER-ADMIN")
	public void testGetAllUsers() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/registration/userManagement")).andExpect(status().isOk())
				.andExpect(content().contentType("text/html;charset=UTF-8")).andExpect(view().name("admin/user_list"))
				.andExpect(MockMvcResultMatchers.view().name("admin/user_list"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("usersList"));

	}

	@Test
	@WithMockUser(username = "admin@gmail.com", password = "admin", roles = "SUPER-ADMIN")
	public void testUserCreate() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/registration/edit")).andExpect(status().isOk())
				.andExpect(content().contentType("text/html;charset=UTF-8"))
				.andExpect(view().name("admin/user_register"))
				.andExpect(MockMvcResultMatchers.view().name("admin/user_register"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("rolesList", "user", "mode"));// .andDo(print());
	}

	@Test
	@WithMockUser(username = "admin@gmail.com", password = "admin", roles = "SUPER-ADMIN")
	public void testUserSave() throws Exception {
		User newUser = new User();
		newUser.setFirstName("Test Name");
		newUser.setLastName("Test Initial");
		newUser.setEmail("testnew@gmail.com");
		newUser.setPassword("testnew123");
		newUser.setRole(roleRepository.findAll().get(0));
		newUser.setCompany(companyRepository.getOne(1l));
		newUser.setLanguage("en");
		TypeReference<HashMap<String, Object>> typeRef = new TypeReference<HashMap<String, Object>>() {
		};

		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		// object to Map
		ObjectMapper objectMapper = new ObjectMapper();
		// objectMapper.enableDefaultTyping();
		// objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		objectMapper.configure(SerializationFeature.USE_EQUALITY_FOR_OBJECT_ID, true);
		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		objectMapper.setDateFormat(df);

		Map<String, Object> map = objectMapper.convertValue(newUser, typeRef);
		LinkedMultiValueMap<String, String> linkedMultiValueMap = new LinkedMultiValueMap<>();
		map.entrySet().forEach(e -> {
			String key = e.getKey();
			String value = null;
			if (key != "roles" && key != "id") {
				if (e.getValue() instanceof Map) {
					Map<String, Object> obj = (Map<String, Object>) e.getValue();
					value = obj.get("id") + ""; // Long.toString((Long) obj.get("id"));
				} else {
					value = e.getValue() + "";
				}
				linkedMultiValueMap.add(e.getKey(), value);
			}
		});

		log.info("------------map testUserSave:::{}", map);
		mockMvc.perform(MockMvcRequestBuilders.post("/registration").params(linkedMultiValueMap))
				.andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/registration/userManagement"))
				.andExpect(flash().attributeCount(1))
				.andExpect(flash().attribute("flash_usercreat", "User Suceesfully Created"));
	}

	@Test
	@WithMockUser(username = "admin@gmail.com", password = "admin", roles = "SUPER-ADMIN")
	public void testUserSaveEmailValidate() throws Exception {
		User newUser = new User();
		newUser.setFirstName("Test Name");
		newUser.setLastName("Test Initial");
		newUser.setEmail("admin@gmail.com");
		newUser.setPassword("testnew123");
		newUser.setRole(roleRepository.findAll().get(0));
		newUser.setCompany(companyRepository.getOne(1l));
		newUser.setLanguage("en");
		TypeReference<HashMap<String, Object>> typeRef = new TypeReference<HashMap<String, Object>>() {
		};

		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		// object to Map
		ObjectMapper objectMapper = new ObjectMapper();
		// objectMapper.enableDefaultTyping();
		// objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		objectMapper.configure(SerializationFeature.USE_EQUALITY_FOR_OBJECT_ID, true);
		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		objectMapper.setDateFormat(df);

		Map<String, Object> map = objectMapper.convertValue(newUser, typeRef);
		LinkedMultiValueMap<String, String> linkedMultiValueMap = new LinkedMultiValueMap<>();
		map.entrySet().forEach(e -> {
			String key = e.getKey();
			String value = null;
			if (key != "roles" && key != "id") {
				if (e.getValue() instanceof Map) {
					Map<String, Object> obj = (Map<String, Object>) e.getValue();
					value = obj.get("id") + ""; // Long.toString((Long) obj.get("id"));
				} else {
					value = e.getValue() + "";
				}
				linkedMultiValueMap.add(e.getKey(), value);
			}
		});

		log.info("------------map testUserSaveEmailValidate:::{}", map);
		mockMvc.perform(MockMvcRequestBuilders.post("/registration").params(linkedMultiValueMap))
				.andExpect(view().name("admin/user_register")).andExpect(model().hasErrors());
	}

	@Test
	@WithMockUser(username = "admin@gmail.com", password = "admin", roles = "SUPER-ADMIN")
	public void testUserEditPage() throws Exception {
		User newUser = new User();
		newUser.setFirstName("Test Name");
		newUser.setLastName("Test Initial");
		newUser.setEmail("test@gmail.com");
		newUser.setPassword("test123");
		newUser.setRole(roleRepository.findAll().get(0));
		User savedUser = userService.save(newUser);
		log.info("------------testUserEditPage:::{}", savedUser);

		mockMvc.perform(MockMvcRequestBuilders.post("/registration/edit").param("id", savedUser.getId() + "")
				.param("mode", "edit")).andExpect(status().isOk())
				.andExpect(content().contentType("text/html;charset=UTF-8"))
				.andExpect(view().name("admin/user_register"))
				.andExpect(MockMvcResultMatchers.view().name("admin/user_register"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("rolesList", "user", "mode"));// .andDo(print());
	}

	@Test
	@WithMockUser(username = "admin@gmail.com", password = "admin", roles = "SUPER-ADMIN")
	public void testUserUpdate() throws Exception {
		User newUser = new User();
		newUser.setFirstName("Test Name");
		newUser.setLastName("Test Initial");
		newUser.setEmail("test@gmail.com");
		newUser.setPassword("test123");
		newUser.setRole(roleRepository.findAll().get(0));
		newUser.setCompany(companyRepository.getOne(1l));
		newUser.setLanguage("en");
		User savedUser = userService.save(newUser);
		log.info("------------testUserEditPage:::{}", savedUser);

		savedUser.setFirstName("Updated Test Name");
		savedUser.setLastName("Updated Test Initial");
		log.info("------------testUserUpdate:::{}", savedUser);
		Role role = savedUser.getRoles().iterator().next();
		savedUser.setRole(role);
		TypeReference<HashMap<String, Object>> typeRef = new TypeReference<HashMap<String, Object>>() {
		};

		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		// object to Map
		ObjectMapper objectMapper = new ObjectMapper();
		// objectMapper.enableDefaultTyping();
		// objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		objectMapper.configure(SerializationFeature.USE_EQUALITY_FOR_OBJECT_ID, true);
		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		objectMapper.setDateFormat(df);

		Map<String, Object> map = objectMapper.convertValue(savedUser, typeRef);
		LinkedMultiValueMap<String, String> linkedMultiValueMap = new LinkedMultiValueMap<>();
		map.entrySet().forEach(e -> {
			String key = e.getKey();
			String value = null;
			if (key != "roles") {
				if (e.getValue() instanceof Map) {
					Map<String, Object> obj = (Map<String, Object>) e.getValue();
					value = obj.get("id") + ""; // Long.toString((Long) obj.get("id"));
				} else {
					value = e.getValue() + "";
				}
				linkedMultiValueMap.add(e.getKey(), value);
			}
		});
		log.info("------------linkedMultiValueMap:::{}", linkedMultiValueMap);
		mockMvc.perform(MockMvcRequestBuilders.post("/registration").params(linkedMultiValueMap))
				.andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/registration/userManagement"))
				.andExpect(flash().attributeCount(1))
				.andExpect(flash().attribute("flash_usercreat", "User Suceesfully updated"));
	}

	@Test
	@WithMockUser(username = "admin@gmail.com", password = "admin", roles = "SUPER-ADMIN")
	public void testUserUpdateValidateErrors() throws Exception {
		User newUser = new User();
		newUser.setFirstName("Test Name");
		newUser.setLastName("Test Initial");
		newUser.setEmail("test@gmail.com");
		newUser.setPassword("test123");
		newUser.setRole(roleRepository.findAll().get(0));
		newUser.setCompany(companyRepository.getOne(1l));
		newUser.setLanguage("en");
		User savedUser = userService.save(newUser);
		log.info("------------testUserEditPage:::{}", savedUser);

		savedUser.setFirstName("Updated Test Name");
		savedUser.setLastName("Updated Test Initial");
		log.info("------------testUserUpdate:::{}", savedUser);
		Role role = savedUser.getRoles().iterator().next();
		savedUser.setRole(role);
		TypeReference<HashMap<String, Object>> typeRef = new TypeReference<HashMap<String, Object>>() {
		};

		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		// object to Map
		ObjectMapper objectMapper = new ObjectMapper();
		// objectMapper.enableDefaultTyping();
		// objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		objectMapper.configure(SerializationFeature.USE_EQUALITY_FOR_OBJECT_ID, true);
		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		objectMapper.setDateFormat(df);

		Map<String, Object> map = objectMapper.convertValue(savedUser, typeRef);
		LinkedMultiValueMap<String, String> linkedMultiValueMap = new LinkedMultiValueMap<>();
		map.entrySet().forEach(e -> {
			String key = e.getKey();
			String value = null;
			if (key != "roles") {
				if (e.getValue() instanceof Map) {
					Map<String, Object> obj = (Map<String, Object>) e.getValue();
					value = obj.get("id") + ""; // Long.toString((Long) obj.get("id"));
				} else {
					value = e.getValue() + "";
				}
				linkedMultiValueMap.add(e.getKey(), value);
			}
		});
		log.info("------------linkedMultiValueMap:::{}", linkedMultiValueMap);
		mockMvc.perform(MockMvcRequestBuilders.post("/registration").params(linkedMultiValueMap))
				.andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/registration/userManagement"))
				.andExpect(flash().attributeCount(1))
				.andExpect(flash().attribute("flash_usercreat", "User Suceesfully updated"));
	}

	@Test
	@WithMockUser(username = "admin@gmail.com", password = "admin", roles = "SUPER-ADMIN")
	public void testChangePassword() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/registration/changePassword")).andExpect(status().isOk())
				.andExpect(content().contentType("text/html;charset=UTF-8"))
				.andExpect(view().name("admin/change_password"))
				.andExpect(MockMvcResultMatchers.view().name("admin/change_password"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("user"));// .andDo(print());
	}

	@Test
	@WithMockUser(username = "admin@gmail.com", password = "admin", roles = "SUPER-ADMIN")
	public void testSaveChangePassword() throws Exception {
		User newUser = new User();
		newUser.setFirstName("Test Name");
		newUser.setLastName("Test Initial");
		newUser.setEmail("test@gmail.com");
		newUser.setPassword("test123");
		newUser.setRole(roleRepository.findAll().get(0));
		User saved = userService.save(newUser);
		log.info("------------testUserEditPage:::{}", saved.getPassword());
		User savedUser = new User();
		savedUser.setId(saved.getId());
		savedUser.setPassword("test123");
		savedUser.setNewPassword("updatedadmin");
		savedUser.setConfirmPassword("updatedadmin");

		TypeReference<HashMap<String, Object>> typeRef = new TypeReference<HashMap<String, Object>>() {
		};

		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		// object to Map
		ObjectMapper objectMapper = new ObjectMapper();
		// objectMapper.enableDefaultTyping();
		// objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		objectMapper.configure(SerializationFeature.USE_EQUALITY_FOR_OBJECT_ID, true);
		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		objectMapper.setDateFormat(df);

		Map<String, Object> map = objectMapper.convertValue(savedUser, typeRef);
		LinkedMultiValueMap<String, String> linkedMultiValueMap = new LinkedMultiValueMap<>();
		map.entrySet().forEach(e -> {
			String key = e.getKey();
			String value = null;
			if (key == "id" || key == "password" || key == "newPassword" || key == "confirmPassword") {
				if (e.getValue() instanceof Map) {
					Map<String, Object> obj = (Map<String, Object>) e.getValue();
					value = obj.get("id") + ""; // Long.toString((Long) obj.get("id"));
				} else {
					value = e.getValue() + "";
				}
				linkedMultiValueMap.add(e.getKey(), value);
			}
		});
		log.info("------------linkedMultiValueMap:::{}", linkedMultiValueMap);
		mockMvc.perform(MockMvcRequestBuilders.post("/registration/saveChangePassword").params(linkedMultiValueMap))
				.andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/registration/userManagement"))
				.andExpect(flash().attributeCount(1))
				.andExpect(flash().attribute("flash_password", "Password updated!"));
	}

	@Test
	@WithMockUser(username = "admin@gmail.com", password = "admin", roles = "SUPER-ADMIN")
	public void testSaveChangePasswordWithErro() throws Exception {
		User newUser = new User();
		newUser.setFirstName("Test Name");
		newUser.setLastName("Test Initial");
		newUser.setEmail("test@gmail.com");
		newUser.setPassword("test123");
		newUser.setRole(roleRepository.findAll().get(0));
		User savedUser = userService.save(newUser);
		log.info("------------testUserEditPage:::{}", savedUser);

		savedUser.setFirstName("Updated Test Name");
		savedUser.setLastName("Updated Test Initial");
		log.info("------------testUserUpdate:::{}", savedUser);
		Role role = savedUser.getRoles().iterator().next();
		savedUser.setRole(role);

		savedUser.setPassword("asfdfasdf");
		savedUser.setNewPassword("updatedadmin");
		savedUser.setConfirmPassword("updatedadmin");

		TypeReference<HashMap<String, Object>> typeRef = new TypeReference<HashMap<String, Object>>() {
		};

		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		// object to Map
		ObjectMapper objectMapper = new ObjectMapper();
		// objectMapper.enableDefaultTyping();
		// objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		objectMapper.configure(SerializationFeature.USE_EQUALITY_FOR_OBJECT_ID, true);
		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		objectMapper.setDateFormat(df);

		Map<String, Object> map = objectMapper.convertValue(savedUser, typeRef);
		LinkedMultiValueMap<String, String> linkedMultiValueMap = new LinkedMultiValueMap<>();
		map.entrySet().forEach(e -> {
			String key = e.getKey();
			String value = null;
			if (key != "roles") {
				if (e.getValue() instanceof Map) {
					Map<String, Object> obj = (Map<String, Object>) e.getValue();
					value = obj.get("id") + ""; // Long.toString((Long) obj.get("id"));
				} else {
					value = e.getValue() + "";
				}
				linkedMultiValueMap.add(e.getKey(), value);
			}
		});
		log.info("------------linkedMultiValueMap:::{}", linkedMultiValueMap);
		mockMvc.perform(MockMvcRequestBuilders.post("/registration/saveChangePassword").params(linkedMultiValueMap))
				.andExpect(MockMvcResultMatchers.view().name("admin/change_password")).andExpect(model().hasErrors());
	}

	@Test
	@WithMockUser(username = "admin@gmail.com", password = "admin", roles = "SUPER-ADMIN")
	public void testDeleteUser() throws Exception {
		User newUser = new User();
		newUser.setFirstName("Test Name");
		newUser.setLastName("Test Initial");
		newUser.setEmail("test@gmail.com");
		newUser.setPassword("test123");
		newUser.setRole(roleRepository.findAll().get(0));
		User savedUser = userService.save(newUser);
		log.info("------------testUserEditPage:::{}", savedUser);
		mockMvc.perform(MockMvcRequestBuilders.post("/registration/delete").param("id", savedUser.getId() + ""))
				.andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/registration/userManagement"))
				.andExpect(flash().attributeCount(1))
				.andExpect(flash().attribute("flash_delete", "User Deleted Successfully!"));
	}

}
