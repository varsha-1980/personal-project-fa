package com.mindlease.fa.test.repository;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.mindlease.fa.model.MethodX;
import com.mindlease.fa.model.OrderDetails;
import com.mindlease.fa.repository.MethodRepository;
import com.mindlease.fa.repository.MethodXRepository;
import com.mindlease.fa.repository.OrderDetailsRepository;


public class MethodXRepositoryTest {

	@Mock
	MethodXRepository methodXRepository;
	@Mock
	MethodRepository methodRepository;
	@Mock
	OrderDetailsRepository orderDetailsRepository;
	
	private static final String NAME = "Test";
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	
	@Test
	public void findAll() {
		assertNotNull(methodXRepository.findAll());
	}
	
	@Test
	public void create() {
		MethodX newobj = new MethodX();
		Iterable<OrderDetails> list =orderDetailsRepository.findAll();
		if(list.iterator().hasNext())
		newobj.setOrder_id(orderDetailsRepository.findAll().iterator().next().getId());
		newobj.setOrder_id(null);
		newobj.setName(NAME);
		newobj.setGeneral(false);
		newobj.setPack(true);
		newobj.setWfr(false);
		methodXRepository.save(newobj);
	}

	@Test
	public void update() {
		Optional<MethodX> savedOps = methodXRepository.findById(1l);
		MethodX saved =  savedOps.isPresent() ? savedOps.get(): new MethodX();
		assertNotNull(saved);
	}
	
	@Test
	public void deleteById() {
		Optional<MethodX> delete = methodXRepository.findById(10l);
		if (delete.isPresent()) {
			MethodX x = methodXRepository.findByName(delete.get().getName());
			if (x != null)
				methodXRepository.delete(x);
			methodXRepository.delete(delete.get());
			Optional<MethodX> deleteArchive = methodXRepository.findById(10l);
			assertFalse(deleteArchive.isPresent());
		}
	}

}