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

import com.mindlease.fa.model.FailureMode;
import com.mindlease.fa.repository.FailureModeRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class FailureModeRepositoryTest {

	@Autowired
	FailureModeRepository failureModeRepository;

	@Test
	public void findAll() {
		assertNotNull(failureModeRepository.findAll());
	}
	
	@Test
	public void create() {
		FailureMode newobj = new FailureMode();
		
		newobj.setName("Test name");
		FailureMode saved = failureModeRepository.save(newobj);
		
		assertNotNull(saved.getId());
		
	}

	@Test
	public void update() {
		Optional<FailureMode> savedOps = failureModeRepository.findById(1l);
		FailureMode saved =  savedOps.isPresent() ? savedOps.get(): new FailureMode();
		assertNotNull(saved.getId());
	}
	
	@Test
	public void deleteById() {
		failureModeRepository.deleteById(1l);
		Optional<FailureMode> deleteArchive = failureModeRepository.findById(1l);
		assertFalse(deleteArchive.isPresent());
	}

}