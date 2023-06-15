package com.mindlease.fa.test.repository;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.mindlease.fa.model.Status;
import com.mindlease.fa.repository.StatusRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class StatusRepositoryTest {

	@Autowired
	StatusRepository statusRepository;

	@Test
	public void findAll() {
		assertNotNull(statusRepository.findAll());
	}
	
	@Test
	public void create() {
		Status newobj = new Status();
		
		newobj.setName("Test name");
		Status saved = statusRepository.save(newobj);
		
		assertNotNull(saved.getId());
		
	}

	@Test
	public void update() {
		Optional<Status> savedOps = statusRepository.findById(1l);
		Status saved =  savedOps.isPresent() ? savedOps.get(): new Status();
		assertNotNull(saved.getId());
	}
	
	@Test
	public void deleteById() {
		statusRepository.deleteById(1l);
		Optional<Status> deleteArchive = statusRepository.findById(1l);
		assertFalse(deleteArchive.isPresent());
	}

}