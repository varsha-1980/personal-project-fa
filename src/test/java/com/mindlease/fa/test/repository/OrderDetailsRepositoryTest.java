package com.mindlease.fa.test.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import com.mindlease.fa.model.OrderDetails;
import com.mindlease.fa.repository.LocationRepository;
import com.mindlease.fa.repository.MaterialRepository;
import com.mindlease.fa.repository.OrderDetailsRepository;
import com.mindlease.fa.repository.PersonalRepository;
import com.mindlease.fa.repository.PriorityRepository;
import com.mindlease.fa.util.FailureAnalysisConstants;

@RunWith(SpringRunner.class)
@DataJpaTest
public class OrderDetailsRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	OrderDetailsRepository orderDetailsRepository;

	@Autowired
	PriorityRepository priorityRepository;
	@Autowired
	MaterialRepository materialRepository;
	@Autowired
	LocationRepository locationRepository;
	@Autowired
	PersonalRepository personalRepository;

	@Test
	public void testSaveOrderDetails() {
		OrderDetails orderDetails = getFirstNewOrderDetails();
		OrderDetails savedInDb = entityManager.persist(orderDetails);
		OrderDetails getFromDb = orderDetailsRepository.findById(savedInDb.getId()).get();

		assertThat(getFromDb).isEqualTo(orderDetails);

	}

	@Test
	public void testGetAllOrderDetails() {
		long initialCount = orderDetailsRepository.count();
		// Save both tickets in DB
		OrderDetails orderDetails1 = getFirstNewOrderDetails();
		OrderDetails orderDetails2 = getSecondNewOrderDetails();

		entityManager.persist(orderDetails1);
		entityManager.persist(orderDetails2);

		long endCount = orderDetailsRepository.count();
		assertThat(endCount).isEqualTo(initialCount+2);
	}

	@Test
	public void testFindByOrderNumber() {
		OrderDetails orderDetails = getFirstNewOrderDetails();
		// Ticket in DB
		entityManager.persist(orderDetails);

		// Get ticket info from DB for specified email
		OrderDetails getFromDb = orderDetailsRepository.findById(orderDetails.getId()).get();
		assertThat(getFromDb.getDbs_lotid()).isEqualTo("Test lotid");
	}

	@Test
	public void testDeleteTicketById() {
		long initialCount = orderDetailsRepository.count();
		// Save both tickets in DB
		OrderDetails orderDetails1 = getFirstNewOrderDetails();
		OrderDetails orderDetails2 = getSecondNewOrderDetails();

		OrderDetails persist = entityManager.persist(orderDetails1);
		entityManager.persist(orderDetails2);

		// delete one ticket DB
		entityManager.remove(persist);
		entityManager.flush();
		long endCount = orderDetailsRepository.count();
		assertThat(endCount).isEqualTo(initialCount+1);
	}

	@Test
	public void testUpdateTicket() {
		OrderDetails orderDetails = getFirstNewOrderDetails();
		// Ticket in DB
		OrderDetails getFromDb = entityManager.persist(orderDetails);
		getFromDb.setDbs_car("Updated CarOrNovOrMRBNumber");

		assertThat(getFromDb.getDbs_car()).isEqualTo("Updated CarOrNovOrMRBNumber");
	}

	@Test
	public void fetchOrderDetailsInnerJoinTest() {
		List<OrderDetails> orderDetailsList = new ArrayList<OrderDetails>();
		orderDetailsRepository.findAll().forEach(orderDetailsList::add);
		Assert.assertNotNull(orderDetailsRepository.fetchOrderDetailsInnerJoin());

	}

	@Test
	public void fetchOrderDetailsInnerJoinPaginationTest() {
		int evalPageSize = FailureAnalysisConstants.INITIAL_PAGE_SIZE;
		int evalPage = FailureAnalysisConstants.INITIAL_PAGE;
		PageRequest pageRequest = PageRequest.of(evalPage, evalPageSize);
		Page<OrderDetails> clientlist = orderDetailsRepository.findAll(pageRequest);
		Assert.assertNotNull(clientlist);
	}

	private OrderDetails getFirstNewOrderDetails() {
		OrderDetails orderDetails = new OrderDetails();
		/*
		 * orderDetails.setPriority(priorityRepository.getOne(1l));
		 * orderDetails.setMaterial(materialRepository.getOne(1l));
		 * orderDetails.setLocation(locationRepository.getOne(1l));
		 * orderDetails.setCustomer(personalRepository.getOne(1l));
		 * orderDetails.setOrderNumber("Test order1"); orderDetails.setOrderDate(new
		 * Date()); orderDetails.setLotId("Test lot id1");
		 * orderDetails.setWaferNo("Test Wafer Number1");
		 * orderDetails.setCarOrNovOrMRBNumber("Test CarOrNovOrMRBNumber1");
		 * orderDetails.setCurrentProcessingStep("Test Current Proccessing step1");
		 * //orderDetails.setProcessingStatus("Test Processing Status1");
		 * orderDetails.setCarOrNovOrMRBNumber("Test CarOrNovOrMRBNumber1");
		 */
		
			orderDetails.setDbs_fa_date(new Date());
			orderDetails.setDbs_ag_name("Test Ag Name");
			orderDetails.setDbs_material("Test material");
			orderDetails.setDbs_lotid("Test lotid");
			orderDetails.setDbs_wfr("Test wafer");
			orderDetails.setDbs_part("Test part");
			orderDetails.setDbs_prio("Test Priority");
			orderDetails.setDbs_location("Test location");
			orderDetails.setDbs_step("Test step");
			orderDetails.setDbs_status("Test Status");
			orderDetails.setDbs_car("Test Car");
			orderDetails.setDbs_part("Test part");
		
			orderDetails.setDbs_elee("Test Dbs_elee");
			orderDetails.setDbs_famo("Test Dbs_famo");
			orderDetails.setDbs_fa_reason("Test Dbs_fa_reason");
			orderDetails.setDbs_fa_descr("Test Dbs_fa_descr");
			orderDetails.setDbs_pos_text("Test Dbs_pos_text");
			orderDetails.setDbs_pos_xcmap(false);
			orderDetails.setDbs_remain("Test Dbs_remain");
			orderDetails.setDbs_fa_start(new Date());
			orderDetails.setDbs_fa_stop(new Date());
			orderDetails.setDbs_fa_name("Test Dbs_fa_name");
			orderDetails.setDbs_fa_text("Test Dbs_fa_text");
			orderDetails.setDbs_status("Test Dbs_status");
			orderDetails.setDbs_fa_archiv_wf("Test Dbs_fa_archiv_wf");
			orderDetails.setDbs_fa_archiv_ps("Test Dbs_fa_archiv_ps");
			
			orderDetails.setDbs_cost(BigDecimal.ONE);
			orderDetails.setDbs_res_name("Test Dbs_res_name");
			orderDetails.setDbs_status("Test Dbs_status");
			orderDetails.setDbs_res_text("Test Dbs_res_text");
			orderDetails.setDbs_res_start(new Date());
			orderDetails.setDbs_res_stop(new Date());
			orderDetails.setDbs_wait_time1(23f);
			orderDetails.setDbs_fa_time(24f);
			orderDetails.setDbs_wait_time2(21f);
			orderDetails.setDbs_res_time(63f);
			orderDetails.setDbs_cpl_time(90f);
			orderDetails.setDbs_cost(BigDecimal.TEN);

		return orderDetails;
	}

	private OrderDetails getSecondNewOrderDetails() {
		OrderDetails orderDetails = new OrderDetails();
		orderDetails.setDbs_fa_date(new Date());
		orderDetails.setDbs_ag_name("Test Ag Name");
		orderDetails.setDbs_material("Test material");
		orderDetails.setDbs_lotid("Test lotid");
		orderDetails.setDbs_wfr("Test wafer");
		orderDetails.setDbs_part("Test part");
		orderDetails.setDbs_prio("Test Priority");
		orderDetails.setDbs_location("Test location");
		orderDetails.setDbs_step("Test step");
		orderDetails.setDbs_status("Test Status");
		orderDetails.setDbs_car("Test Car");
		orderDetails.setDbs_part("Test part");
	
		orderDetails.setDbs_elee("Test Dbs_elee");
		orderDetails.setDbs_famo("Test Dbs_famo");
		orderDetails.setDbs_fa_reason("Test Dbs_fa_reason");
		orderDetails.setDbs_fa_descr("Test Dbs_fa_descr");
		orderDetails.setDbs_pos_text("Test Dbs_pos_text");
		orderDetails.setDbs_pos_xcmap(false);
		orderDetails.setDbs_remain("Test Dbs_remain");
		orderDetails.setDbs_fa_start(new Date());
		orderDetails.setDbs_fa_stop(new Date());
		orderDetails.setDbs_fa_name("Test Dbs_fa_name");
		orderDetails.setDbs_fa_text("Test Dbs_fa_text");
		orderDetails.setDbs_status("Test Dbs_status");
		orderDetails.setDbs_fa_archiv_wf("Test Dbs_fa_archiv_wf");
		orderDetails.setDbs_fa_archiv_ps("Test Dbs_fa_archiv_ps");
		
		orderDetails.setDbs_cost(BigDecimal.ONE);
		orderDetails.setDbs_res_name("Test Dbs_res_name");
		orderDetails.setDbs_status("Test Dbs_status");
		orderDetails.setDbs_res_text("Test Dbs_res_text");
		orderDetails.setDbs_res_start(new Date());
		orderDetails.setDbs_res_stop(new Date());
		orderDetails.setDbs_wait_time1(23f);
		orderDetails.setDbs_fa_time(24f);
		orderDetails.setDbs_wait_time2(21f);
		orderDetails.setDbs_res_time(63f);
		orderDetails.setDbs_cpl_time(90f);
		orderDetails.setDbs_cost(BigDecimal.TEN);
		return orderDetails;
	}
}
