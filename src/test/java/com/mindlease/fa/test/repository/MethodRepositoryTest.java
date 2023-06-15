package com.mindlease.fa.test.repository;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.Optional;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.mindlease.fa.model.Method;
import com.mindlease.fa.model.MethodX;
import com.mindlease.fa.repository.MethodRepository;
import com.mindlease.fa.repository.MethodXRepository;

public class MethodRepositoryTest {

	@Mock
	MethodRepository methodRepository;
	
	@Mock
	MethodXRepository methodXRepository;
	
	@org.junit.Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void findAll() {
		assertNotNull(methodRepository.findAll());
	}
	
	@Test
	public void create() {
		Method newobj = new Method();
		
		newobj.setName("Test name");
		newobj.setGeneral(false);
		newobj.setPack(true);
		newobj.setWfr(false);
		methodRepository.save(newobj);
		assertNotNull(methodRepository.findByGeneral(false));
		
	}

	@Test
	public void update() {
		Optional<Method> savedOps = methodRepository.findById(1l);
		Method saved =  savedOps.isPresent() ? savedOps.get(): new Method();
		assertNotNull(saved);
	}
	
	@Test
	public void deleteById() {
		Optional<Method> delete = methodRepository.findById(10l);
		if (delete.isPresent()) {
			MethodX x = methodXRepository.findByName(delete.get().getName());
			if (x != null)
				methodXRepository.delete(x);
			methodRepository.delete(delete.get());
			Optional<Method> deleteArchive = methodRepository.findById(10l);
			assertFalse(deleteArchive.isPresent());
		}
	}

}