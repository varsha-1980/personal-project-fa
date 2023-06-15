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

import com.mindlease.fa.model.ElectricError;
import com.mindlease.fa.repository.ElectricErrorRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ElectricErrorRepositoryTest {

	@Autowired
	ElectricErrorRepository electricErrorRepository;

	@Test
	public void findAll() {
		assertNotNull(electricErrorRepository.findAll());
	}
	
	@Test
	public void create() {
		ElectricError newobj = new ElectricError();
		
		newobj.setName("Test name");
		ElectricError saved = electricErrorRepository.save(newobj);
		
		assertNotNull(saved.getId());
		
	}

	@Test
	public void update() {
		Optional<ElectricError> savedOps = electricErrorRepository.findById(1l);
		ElectricError saved =  savedOps.isPresent() ? savedOps.get(): new ElectricError();
		assertNotNull(saved.getId());
		
	}
	
	@Test
	public void deleteById() {
		electricErrorRepository.deleteById(1l);
		Optional<ElectricError> deleteArchive = electricErrorRepository.findById(1l);
		assertFalse(deleteArchive.isPresent());
	}

}