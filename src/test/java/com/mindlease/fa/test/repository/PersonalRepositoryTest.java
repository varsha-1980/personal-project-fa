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

import com.mindlease.fa.model.Personal;
import com.mindlease.fa.repository.PersonalRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PersonalRepositoryTest {

	@Autowired
	PersonalRepository personalRepository;

	@Test
	public void findAll() {
		assertNotNull(personalRepository.findAll());
	}
	
	@Test
	public void create() {
		Personal newobj = new Personal();
		newobj.setPers_active(true);
		newobj.setPers_admin(true);
		newobj.setPers_company("Test Company");
		newobj.setPers_editor(true);
		newobj.setPers_firstname("Test name");
		newobj.setPers_mail("Test@mail.com");
		newobj.setPers_order(1);
		newobj.setPers_short("Test name");
		newobj.setPers_phone(54354353l);
		newobj.setPers_speech("Test speech name");
		newobj.setPers_surname("Test surname name");
		Personal saved = personalRepository.save(newobj);
		
		assertNotNull(saved.getId());
		
	}

	@Test
	public void update() {
		Optional<Personal> savedOps = personalRepository.findById(1l);
		Personal saved =  savedOps.isPresent() ? savedOps.get(): new Personal();
		assertNotNull(saved.getId());
	}
	
	@Test
	public void deleteById() {
		personalRepository.deleteById(1l);
		Optional<Personal> deleteArchive = personalRepository.findById(1l);
		assertFalse(deleteArchive.isPresent());
	}

}