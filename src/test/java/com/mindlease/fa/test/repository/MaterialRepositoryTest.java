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

import com.mindlease.fa.model.Material;
import com.mindlease.fa.repository.MaterialRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class MaterialRepositoryTest {
	@Autowired
	MaterialRepository materialRepository;
	
	@Test
	public void findAll() {
		assertNotNull(materialRepository.findAll());
	}
	
	@Test
	public void create() {
		Material newobj = new Material();
		
		newobj.setName("Test name");
		Material saved = materialRepository.save(newobj);
		
		assertNotNull(saved.getId());
		
	}

	@Test
	public void update() {
		Optional<Material> savedOps = materialRepository.findById(1l);
		Material saved =  savedOps.isPresent() ? savedOps.get(): new Material();
		assertNotNull(saved.getId());
	}
	
	@Test
	public void deleteById() {
		materialRepository.deleteById(1l);
		Optional<Material> deleteArchive = materialRepository.findById(1l);
		assertFalse(deleteArchive.isPresent());
	}
}
