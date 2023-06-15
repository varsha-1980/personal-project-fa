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

import com.mindlease.fa.model.Priority;
import com.mindlease.fa.repository.PriorityRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PriorityRepositoryTest {


	@Autowired
	PriorityRepository priorityRepository;

	@Test
	public void findAll() {
		assertNotNull(priorityRepository.findAll());
	}
	
	@Test
	public void create() {
		Priority newobj = new Priority();
		
		newobj.setName("Test name");
		Priority saved = priorityRepository.save(newobj);
		
		assertNotNull(saved.getId());
		
	}

	@Test
	public void update() {
		Optional<Priority> savedOps = priorityRepository.findById(1l);
		Priority saved =  savedOps.isPresent() ? savedOps.get(): new Priority();
		assertNotNull(saved.getId());
	}
	
	@Test
	public void deleteById() {
		priorityRepository.deleteById(1l);
		Optional<Priority> deleteArchive = priorityRepository.findById(1l);
		assertFalse(deleteArchive.isPresent());
	}

}
