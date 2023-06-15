package com.mindlease.fa.test.repository;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.mindlease.fa.model.Costs;
import com.mindlease.fa.repository.CostsRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CostsRepositoryTest {

	@Autowired
	CostsRepository costsRepository;

	@Before
	public void before() {
		Costs newobj = new Costs();
		
		newobj.setCst_analysis("Test Analysis");
		newobj.setCst_company("Test Company");
		newobj.setCst_price(new BigDecimal(10.05));
		Costs saved = costsRepository.save(newobj);
	}
	
	@Test
	public void findAll() {
		assertNotNull(costsRepository.findAll());
	}
	
	@Test
	public void create() {
		Costs newobj = new Costs();
		
		newobj.setCst_analysis("Test Analysis");
		newobj.setCst_company("Test Company");
		newobj.setCst_price(new BigDecimal(10.05));
		Costs saved = costsRepository.save(newobj);
		
		assertNotNull(saved.getId());
		
	}

	@Test
	public void update() {
		Optional<Costs> savedOps = costsRepository.findById(1l);
		Costs saved =  savedOps.isPresent() ? savedOps.get(): new Costs();
		assertNotNull(saved.getId());
		
	}

	@Test
	public void deleteById() {
		costsRepository.deleteById(1l);
		Optional<Costs> deleteArchive = costsRepository.findById(1l);
		assertFalse(deleteArchive.isPresent());
	}
}