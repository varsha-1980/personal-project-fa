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

import com.mindlease.fa.model.Location;
import com.mindlease.fa.repository.LocationRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class LocationRepositoryTest {

	@Autowired
	LocationRepository locationRepository;

	@Test
	public void findAll() {
		assertNotNull(locationRepository.findAll());
	}
	
	@Test
	public void create() {
		Location newobj = new Location();
		
		newobj.setName("Test name");
		Location saved = locationRepository.save(newobj);
		
		assertNotNull(saved.getId());
		
	}

	@Test
	public void update() {
		Optional<Location> savedOps = locationRepository.findById(1l);
		Location saved =  savedOps.isPresent() ? savedOps.get(): new Location();
		assertNotNull(saved.getId());
	}
	
	@Test
	public void deleteById() {
		locationRepository.deleteById(1l);
		Optional<Location> deleteArchive = locationRepository.findById(1l);
		assertFalse(deleteArchive.isPresent());
	}

}
