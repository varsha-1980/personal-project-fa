package com.mindlease.fa.test.repository;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.mindlease.fa.model.Role;
import com.mindlease.fa.model.User;
import com.mindlease.fa.repository.RoleRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RoleRepositoryTest {

	@Autowired
	RoleRepository roleRepository;

	@Test
	public void findAll() {
		assertNotNull(roleRepository.findAll());
	}

	@Test
	public void create() {
		Role newobj = new Role();

		newobj.setName("Test name");
		Role saved = roleRepository.save(newobj);

		assertNotNull(saved.getId());

	}
	
	


}