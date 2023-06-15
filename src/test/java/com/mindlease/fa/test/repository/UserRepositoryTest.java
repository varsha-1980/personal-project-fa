package com.mindlease.fa.test.repository;

import static org.junit.Assert.assertNotNull;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.mindlease.fa.model.User;
import com.mindlease.fa.repository.UserRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

	@Autowired
	UserRepository userRepository;

	@Test
	public void findByEmail() {
		assertNotNull(userRepository.findByEmail("admin@gmail.com"));
	}

	@Test
	public void create() {
		User newobj = new User();

		newobj.setConfirmPassword("Test confirmPassword");
		newobj.setEmail("test@gmail.com");
		newobj.setFirstName("Test firstName");
		newobj.setLanguage("Test language");
		newobj.setLastName("Test lastName");
		newobj.setMode("Test mode");
		newobj.setNewPassword("Test newPassword");
		newobj.setPassword("Test password");

		User saved = userRepository.save(newobj);

		assertNotNull(saved.getId());

	}

	@Test
	public void update() {
		Optional<User> savedOps = userRepository.findById(Optional.of(1));
		User saved = savedOps.isPresent() ? savedOps.get() : new User();
		assertNotNull(saved.getId());

	}

}
