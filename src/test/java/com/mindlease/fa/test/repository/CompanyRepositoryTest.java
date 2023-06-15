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

import com.mindlease.fa.model.Archive;
import com.mindlease.fa.model.Company;
import com.mindlease.fa.repository.CompanyRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CompanyRepositoryTest {

	@Autowired
	CompanyRepository companyRepository;

	@Test
	public void findAll() {
		assertNotNull(companyRepository.findAll());
	}
	
	@Test
	public void create() {
		Company newobj = new Company();
		
		newobj.setName("Test name");
		newobj.setVersion(1);
		
		newobj.setCreatedBy(1);
		newobj.setCreatedDate(new Date());
		newobj.setFActive("Y");
		newobj.setFCancel("Y");
		newobj.setModifiedBy(1);
		newobj.setModifiedDate(new Date());
		Company saved = companyRepository.save(newobj);
		
		assertNotNull(saved.getId());
		
	}

	@Test
	public void update() {
		Optional<Company> savedOps = companyRepository.findById(1l);
		Company saved =  savedOps.isPresent() ? savedOps.get(): new Company();
		assertNotNull(saved.getId());
		
	}

	@Test
	public void deleteById() {
		companyRepository.deleteById(1l);
		Optional<Company> deleteArchive = companyRepository.findById(1l);
		assertFalse(deleteArchive.isPresent());
	}
}