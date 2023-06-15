package com.mindlease.fa.test.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.mindlease.fa.model.OrderDetails;
import com.mindlease.fa.repository.LocationRepository;
import com.mindlease.fa.repository.MaterialRepository;
import com.mindlease.fa.repository.PersonalRepository;
import com.mindlease.fa.repository.PriorityRepository;
import com.mindlease.fa.service.OrderDetailsService;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
@ActiveProfiles("test")
public class OrderDetailsServiceTest {

	@Autowired
	OrderDetailsService orderDetailsService;

	@Autowired
	PriorityRepository priorityRepository;

	@Autowired
	MaterialRepository materialRepository;
	@Autowired
	LocationRepository locationRepository;
	@Autowired
	PersonalRepository personalRepository;

	OrderDetails orderDetails1;
	Date testDate = new Date();
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");


	@Test
	void getAllIssueLogsTest() {
		assertThat(orderDetailsService.getAllOrderDetails());
	}

	@Test
	void findById() {
		assertThat(orderDetailsService.findById(1l));
	}

	@Test
	public void create() {
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

		assertNotNull(orderDetailsService.create(orderDetails).getId());
	}

	/*
	 * @Test public void update() { OrderDetails orderDetails =
	 * orderDetailsService.findById(1l).get();
	 * 
	 * orderDetails.setDbs_fa_date(new Date());
	 * orderDetails.setDbs_ag_name("Test Ag Name");
	 * orderDetails.setDbs_material("Test material");
	 * orderDetails.setDbs_lotid("Test lotid");
	 * orderDetails.setDbs_wfr("Test wafer"); orderDetails.setDbs_part("Test part");
	 * orderDetails.setDbs_prio("Test Priority");
	 * orderDetails.setDbs_location("Test location");
	 * orderDetails.setDbs_step("Test step");
	 * orderDetails.setDbs_status("Test Status");
	 * orderDetails.setDbs_car("Test Car"); orderDetails.setDbs_part("Test part");
	 * 
	 * orderDetails.setDbs_elee("Test Dbs_elee");
	 * orderDetails.setDbs_famo("Test Dbs_famo");
	 * orderDetails.setDbs_fa_reason("Test Dbs_fa_reason");
	 * orderDetails.setDbs_fa_descr("Test Dbs_fa_descr");
	 * orderDetails.setDbs_pos_text("Test Dbs_pos_text");
	 * orderDetails.setDbs_pos_xcmap(false);
	 * orderDetails.setDbs_remain("Test Dbs_remain");
	 * orderDetails.setDbs_fa_start(new Date()); orderDetails.setDbs_fa_stop(new
	 * Date()); orderDetails.setDbs_fa_name("Test Dbs_fa_name");
	 * orderDetails.setDbs_fa_text("Test Dbs_fa_text");
	 * orderDetails.setDbs_status("Test Dbs_status");
	 * orderDetails.setDbs_fa_archiv_wf("Test Dbs_fa_archiv_wf");
	 * orderDetails.setDbs_fa_archiv_ps("Test Dbs_fa_archiv_ps");
	 * 
	 * orderDetails.setDbs_cost(BigDecimal.ONE);
	 * orderDetails.setDbs_res_name("Test Dbs_res_name");
	 * orderDetails.setDbs_status("Test Dbs_status");
	 * orderDetails.setDbs_res_text("Test Dbs_res_text");
	 * orderDetails.setDbs_res_start(new Date()); orderDetails.setDbs_res_stop(new
	 * Date()); orderDetails.setDbs_wait_time1(23f);
	 * orderDetails.setDbs_fa_time(24f); orderDetails.setDbs_wait_time2(21f);
	 * orderDetails.setDbs_res_time(63f); orderDetails.setDbs_cpl_time(90f);
	 * orderDetails.setDbs_cost(BigDecimal.TEN);
	 * 
	 * assertEquals("Current Processing Step Updated with Junit",
	 * orderDetailsService.update(orderDetails).getDbs_status()); }
	 */

	/*
	 * @Test public void deleteById() { orderDetailsService.deleteById(1l);
	 * Optional<OrderDetails> deletedOrderDetails =
	 * orderDetailsService.findById(1l);
	 * assertFalse(deletedOrderDetails.isPresent()); }
	 */
}