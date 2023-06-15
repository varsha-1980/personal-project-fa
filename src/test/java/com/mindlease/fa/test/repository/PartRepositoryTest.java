package com.mindlease.fa.test.repository;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.mindlease.fa.model.Part;
import com.mindlease.fa.repository.PartRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PartRepositoryTest {

	@Autowired
	PartRepository partRepository;

	@Test
	public void findAll() {
		assertNotNull(partRepository.findAll());
	}
	
	@Test
	public void create() {
		Part newobj = new Part();
		
		newobj.setName("Test name");
		newobj.setPart_bdv("1");
		newobj.setPart_calc("1.0");
		newobj.setPart_cells("2");
		newobj.setPart_channel("TEST CHANNEL");
		newobj.setPart_class("Test Class");
		newobj.setPart_esd("TEST ESD");
		newobj.setPart_gateoxide("32423l");
		newobj.setPart_metal("Test Metal");
		newobj.setPart_note("Test Note");
		newobj.setPart_pack("Test Pack");
		newobj.setPart_pass("Test pass");
		newobj.setPart_pitch("Test Pitch");
		
		newobj.setPart_rel("Test rel");
		newobj.setPart_substr("Test Substr");
		newobj.setPart_trench("2342342l");
		newobj.setPart_version("0.90");
		newobj.setPart_wfr_no("232l");
		newobj.setPart_wfr_thick("32");
		Part saved = partRepository.save(newobj);
		
		assertNotNull(saved.getId());
		
	}

	@Test
	public void update() {
		Optional<Part> savedOps = partRepository.findById(1l);
		Part saved =  savedOps.isPresent() ? savedOps.get(): new Part();
		assertNotNull(saved.getId());
	}
	
	@Test
	public void deleteById() {
		partRepository.deleteById(1l);
		Optional<Part> deleteArchive = partRepository.findById(1l);
		assertFalse(deleteArchive.isPresent());
	}

}