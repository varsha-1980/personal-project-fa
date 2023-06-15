package com.mindlease.fa.test.repository;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.mindlease.fa.model.Archive;
import com.mindlease.fa.repository.ArchiveRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles(profiles = "test")
public class ArchiveRepositoryTest {

	@Autowired
	ArchiveRepository archiveRepository;

	@Test
	public void findAll() {
		assertNotNull(archiveRepository.findAll());
	}
	
	@Test
	public void create() {
		Archive newobj = new Archive();
		
		newobj.setName("Test name");
		Archive saved = archiveRepository.save(newobj);
		assertNotNull(saved.getId());
		
	}

	@Test
	public void update() {
		Optional<Archive> savedOps = archiveRepository.findById(1l);
		Archive saved =  savedOps.isPresent() ? savedOps.get(): new Archive();
		assertNotNull(saved.getId());
	}
	
	@Test
	public void deleteById() {
		archiveRepository.deleteById(1l);
		Optional<Archive> deleteArchive = archiveRepository.findById(1l);
		assertFalse(deleteArchive.isPresent());
	}

}