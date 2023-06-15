package com.mindlease.fa.test.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.mindlease.fa.model.OrderDetails;
import com.mindlease.fa.repository.LocationRepository;
import com.mindlease.fa.repository.MaterialRepository;
import com.mindlease.fa.repository.PartRepository;
import com.mindlease.fa.repository.PersonalRepository;
import com.mindlease.fa.repository.PriorityRepository;
import com.mindlease.fa.repository.StatusRepository;
import com.mindlease.fa.service.OrderDetailsService;
import com.mindlease.fa.util.TabValues;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
@Slf4j
@ActiveProfiles("test")
//@WebMvcTest(controllers = OrderDetailsController.class)
public class OrderDetailsControllerTest {
	@Autowired
	private ApplicationContext context;
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private OrderDetailsService serviceMock;

	@Autowired
	PriorityRepository priorityRepository;
	@Autowired
	MaterialRepository materialRepository;
	@Autowired
	LocationRepository locationRepository;
	@Autowired
	PersonalRepository personalRepository;
	@Autowired
	PartRepository partRepository;
	@Autowired
	StatusRepository statusRepository;

	public OrderDetailsControllerTest() {
		// TODO Auto-generated constructor stub
	}

	OrderDetails orderDetails1;
	Date testDate = new Date();
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

	@Before
	public void setUpProduct() throws Exception {
		// mockMvc = MockMvcBuilders.standaloneSetup(webApplicationContext).build();
		List<OrderDetails> orderDetailsList = serviceMock.findAll();
		orderDetails1 = orderDetailsList.size() > 0 ? orderDetailsList.get(0) : new OrderDetails();
		/*
		 * orderDetails1.setPriority(priorityRepository.getOne(1l));
		 * orderDetails1.setMaterial(materialRepository.getOne(1l));
		 * orderDetails1.setLocation(locationRepository.getOne(1l));
		 * orderDetails1.setCustomer(personalRepository.getOne(1l));
		 * orderDetails1.setOrderNumber("Test order");
		 * orderDetails1.setOrderDate(testDate); orderDetails1.setLotId("Test lot id");
		 * orderDetails1.setWaferNo("Test Wafer Number");
		 * orderDetails1.setCarOrNovOrMRBNumber("Test CarOrNovOrMRBNumber");
		 * orderDetails1.setCurrentProcessingStep("Test Current Proccessing step");
		 * orderDetails1.setProcessingStatus(statusRepository.getOne(1l));
		 * orderDetails1.setCarOrNovOrMRBNumber("Test CarOrNovOrMRBNumber");
		 * orderDetails1.setFActive("Y"); orderDetails1.setFCancel("N");
		 * orderDetails1.setModifiedBy(1); orderDetails1.setModifiedDate(testDate);
		 */
		
		
		orderDetails1.setDbs_fa_date(new Date());
		orderDetails1.setDbs_ag_name("Test Ag Name");
		orderDetails1.setDbs_material("Test material");
		orderDetails1.setDbs_lotid("Test lotid");
		orderDetails1.setDbs_wfr("Test wafer");
		orderDetails1.setDbs_part("Test part");
		orderDetails1.setDbs_prio("Test Priority");
		orderDetails1.setDbs_location("Test location");
		orderDetails1.setDbs_step("Test step");
		orderDetails1.setDbs_status("Test Status");
		orderDetails1.setDbs_car("Test Car");
		orderDetails1.setDbs_part("Test part");
	
		orderDetails1.setDbs_elee("Test Dbs_elee");
		orderDetails1.setDbs_famo("Test Dbs_famo");
		orderDetails1.setDbs_fa_reason("Test Dbs_fa_reason");
		orderDetails1.setDbs_fa_descr("Test Dbs_fa_descr");
		orderDetails1.setDbs_pos_text("Test Dbs_pos_text");
		orderDetails1.setDbs_pos_xcmap(false);
		orderDetails1.setDbs_remain("Test Dbs_remain");
		orderDetails1.setDbs_fa_start(new Date());
		orderDetails1.setDbs_fa_stop(new Date());
		orderDetails1.setDbs_fa_name("Test Dbs_fa_name");
		orderDetails1.setDbs_fa_text("Test Dbs_fa_text");
		orderDetails1.setDbs_status("Test Dbs_status");
		orderDetails1.setDbs_fa_archiv_wf("Test Dbs_fa_archiv_wf");
		orderDetails1.setDbs_fa_archiv_ps("Test Dbs_fa_archiv_ps");
		
		orderDetails1.setDbs_cost(BigDecimal.ONE);
		orderDetails1.setDbs_res_name("Test Dbs_res_name");
		orderDetails1.setDbs_status("Test Dbs_status");
		orderDetails1.setDbs_res_text("Test Dbs_res_text");
		orderDetails1.setDbs_res_start(new Date());
		orderDetails1.setDbs_res_stop(new Date());
		orderDetails1.setDbs_wait_time1(23f);
		orderDetails1.setDbs_fa_time(24f);
		orderDetails1.setDbs_wait_time2(21f);
		orderDetails1.setDbs_res_time(63f);
		orderDetails1.setDbs_cpl_time(90f);
		orderDetails1.setDbs_cost(BigDecimal.TEN);
		
		
		serviceMock.update(orderDetails1);
	}

	@Test
	@WithMockUser(username = "admin@gmail.com", password = "admin", roles = "SUPER-ADMIN")
	public void testOrderDetailsList() throws Exception {
		assertThat(this.serviceMock).isNotNull();

		mockMvc.perform(MockMvcRequestBuilders.get("/orderdetails")).andExpect(status().isOk())
				.andExpect(content().contentType("text/html;charset=UTF-8"))
				.andExpect(view().name("order_details/orders_list"))
				.andExpect(MockMvcResultMatchers.view().name("order_details/orders_list")).andDo(print());

	}

	/*
	 * @Test
	 * 
	 * @WithMockUser(username = "admin@gmail.com", password = "admin", roles =
	 * "SUPER-ADMIN") public void testShowOrderDetails() throws Exception { //
	 * when(this.serviceMock.getById(1l)).thenReturn(orderDetails); //
	 * Mockito.when(serviceMock.getById(Mockito.anyLong())).thenReturn(orderDetails1
	 * );
	 * 
	 * MvcResult result = mockMvc.perform(post("/orderdetails/edit").param("id",
	 * "1")).andExpect(status().isOk())
	 * .andExpect(view().name("order_details/order_edit"))
	 * .andExpect(MockMvcResultMatchers.model().attributeExists("orderDetails"))
	 * .andExpect(model().attribute("orderDetails", hasProperty("id", is(1l))))
	 * .andExpect(model().attribute("orderDetails", hasProperty("orderNumber",
	 * is("Test order")))) .andExpect(model().attribute("orderDetails",
	 * hasProperty("lotId", is("Test lot id"))))
	 * .andExpect(model().attribute("orderDetails", hasProperty("waferNo",
	 * is("Test Wafer Number")))) .andExpect(model().attribute("orderDetails",
	 * hasProperty("carOrNovOrMRBNumber", is("Test CarOrNovOrMRBNumber"))))
	 * .andExpect(model().attribute("orderDetails",
	 * hasProperty("currentProcessingStep", is("Test Current Proccessing step"))))
	 * //.andExpect(model().attribute("orderDetails",
	 * hasProperty("processingStatus", is("Test Processing Status"))))
	 * .andExpect(model().attribute("orderDetails", hasProperty("FCancel",
	 * is("N")))) .andExpect(model().attribute("orderDetails",
	 * hasProperty("FActive", is("Y"))))
	 * .andExpect(model().attribute("orderDetails", hasProperty("createdBy",
	 * is(1)))) .andExpect(model().attribute("orderDetails",
	 * hasProperty("modifiedBy", is(1)))).andReturn();
	 * 
	 * MockHttpServletResponse mockResponse = result.getResponse();
	 * assertThat(mockResponse.getContentType()).isEqualTo("text/html;charset=UTF-8"
	 * ); Collection<String> responseHeaders = mockResponse.getHeaderNames();
	 * assertNotNull(responseHeaders); }
	 */
	@Test
	@WithMockUser(username = "admin@gmail.com", password = "admin", roles = "SUPER-ADMIN")
	public void testUpdateTab1() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/orderdetails")).andExpect(status().isOk());

		OrderDetails orderDetails = serviceMock.getAllOrderDetails().get(0);

		orderDetails1.setDbs_fa_date(new Date());
		orderDetails1.setDbs_ag_name("Test Ag Name");
		orderDetails1.setDbs_material("Test material");
		orderDetails1.setDbs_lotid("Test lotid");
		orderDetails1.setDbs_wfr("Test wafer");
		orderDetails1.setDbs_part("Test part");
		orderDetails1.setDbs_prio("Test Priority");
		orderDetails1.setDbs_location("Test location");
		orderDetails1.setDbs_step("Test step");
		orderDetails1.setDbs_status("Test Status");
		orderDetails1.setDbs_car("Test Car");
		orderDetails1.setDbs_part("Test part");

		orderDetails.setCurrentTab(TabValues.TAB1);
		orderDetails.setNextTab(TabValues.TAB2);

		TypeReference<HashMap<String, Object>> typeRef = new TypeReference<HashMap<String, Object>>() {
		};

		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		// object to Map
		ObjectMapper objectMapper = new ObjectMapper();
		// objectMapper.enableDefaultTyping();
		// objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		objectMapper.configure(SerializationFeature.USE_EQUALITY_FOR_OBJECT_ID, true);
		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		objectMapper.setDateFormat(df);

		Map<String, Object> map = objectMapper.convertValue(orderDetails, typeRef);
		LinkedMultiValueMap<String, String> linkedMultiValueMap = new LinkedMultiValueMap<>();
		map.entrySet().forEach(e -> {
			String key = e.getKey();
			String value = null;
			if (e.getValue() instanceof Map) {
				Map<String, Object> obj = (Map<String, Object>) e.getValue();
				value = obj.get("id") != null ? (obj.get("id") + "") : null; // Long.toString((Long) obj.get("id"));
			} else if (e.getValue() instanceof List) {
				List<Long> list = new ArrayList<>();
				List<Map<String, Object>> objs = (List<Map<String, Object>>) e.getValue();
				objs.forEach(o -> {
					Long v = o.get("id") != null ? Long.valueOf(o.get("id") + "") : null; // Long.toString((Long)
					if (v != null)
						list.add(v);
				});
				// value = list;
			} else {
				log.info(key + "---------tag::::: {} " + e.getValue());
				value = e.getValue() + "";
			}
			if (e.getValue() != null)
				linkedMultiValueMap.add(e.getKey(), value);
		});
		linkedMultiValueMap.add("action", "save");
		linkedMultiValueMap.add("ln", "en");
		log.info("------------map ffsadfass:::{}", map);

		mockMvc.perform(post("/orderdetails/update").params(linkedMultiValueMap)).andExpect(status().is2xxSuccessful());
		/*
		 * .andExpect(status().is3xxRedirection()).andExpect(redirectedUrl(
		 * "/orderdetails")) .andExpect(flash().attributeCount(1))
		 * .andExpect(flash().attribute("flash", "Order update!"));
		 */

	}

	@Test
	@WithMockUser(username = "admin@gmail.com", password = "admin", roles = "SUPER-ADMIN")
	public void testUpdateTab2() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/orderdetails")).andExpect(status().isOk());

		OrderDetails orderDetails = serviceMock.getAllOrderDetails().get(0);

		orderDetails.setPart(partRepository.findById(1l)
				.orElseThrow(() -> new EntityNotFoundException("Part with id " + 1l + "was not found")));
		orderDetails.setCurrentTab(TabValues.TAB2);
		orderDetails.setNextTab(TabValues.TAB3);
		orderDetails.setPreviousTab(TabValues.TAB1);

		TypeReference<HashMap<String, Object>> typeRef = new TypeReference<HashMap<String, Object>>() {
		};

		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		// object to Map
		ObjectMapper objectMapper = new ObjectMapper();
		// objectMapper.enableDefaultTyping();
		// objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		objectMapper.configure(SerializationFeature.USE_EQUALITY_FOR_OBJECT_ID, true);
		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		objectMapper.setDateFormat(df);

		Map<String, Object> map = objectMapper.convertValue(orderDetails, typeRef);
		LinkedMultiValueMap<String, String> linkedMultiValueMap = new LinkedMultiValueMap<>();
		map.entrySet().forEach(e -> {
			String key = e.getKey();
			String value = null;
			if (e.getValue() instanceof Map) {
				Map<String, Object> obj = (Map<String, Object>) e.getValue();
				value = obj.get("id") != null ? (obj.get("id") + "") : null; // Long.toString((Long) obj.get("id"));
			} else if (e.getValue() instanceof List) {
				List<Long> list = new ArrayList<>();
				List<Map<String, Object>> objs = (List<Map<String, Object>>) e.getValue();
				objs.forEach(o -> {
					Long v = o.get("id") != null ? Long.valueOf(o.get("id") + "") : null; // Long.toString((Long)
					if (v != null)
						list.add(v);
				});
				// value = list;
			} else {
				log.info(key + "---------tag::::: {} " + e.getValue());
				value = e.getValue() + "";
			}
			if (e.getValue() != null)
				linkedMultiValueMap.add(e.getKey(), value);
		});
		linkedMultiValueMap.add("action", "save");
		linkedMultiValueMap.add("ln", "en");
		log.info("------------map ffsadfass:::{}", map);

		mockMvc.perform(post("/orderdetails/update").params(linkedMultiValueMap)).andExpect(status().is2xxSuccessful());
		/*
		 * .andExpect(status().is3xxRedirection()).andExpect(redirectedUrl(
		 * "/orderdetails")) .andExpect(flash().attributeCount(1))
		 * .andExpect(flash().attribute("flash", "Order update!"));
		 */

	}

	public OrderDetails setData(OrderDetails orderDetails, OrderDetails orderDetailsDto, TabValues currentTab) {
		if (currentTab.getValue().equalsIgnoreCase("order")) {
			orderDetails.setDbs_fa_date(orderDetailsDto.getDbs_fa_date());
			orderDetails.setDbs_ag_name(orderDetailsDto.getDbs_ag_name());
			orderDetails.setDbs_material(orderDetailsDto.getDbs_material());
			orderDetails.setDbs_lotid(orderDetailsDto.getDbs_lotid());
			orderDetails.setDbs_wfr(orderDetailsDto.getDbs_wfr());
			orderDetails.setDbs_prio(orderDetailsDto.getDbs_prio());
			orderDetails.setDbs_location(orderDetailsDto.getDbs_location());
			orderDetails.setDbs_step(orderDetailsDto.getDbs_step());
			orderDetails.setDbs_status(orderDetailsDto.getDbs_status());
			orderDetails.setDbs_car(orderDetailsDto.getDbs_car());
		}
		if (currentTab.getValue().equalsIgnoreCase("geometry")) {
			orderDetails.setDbs_part(orderDetailsDto.getDbs_part());
		}
		
		if (currentTab.getValue().equalsIgnoreCase("reason")) {
			orderDetails.setDbs_elee(orderDetailsDto.getDbs_elee());
			orderDetails.setDbs_famo(orderDetailsDto.getDbs_famo());
			orderDetails.setDbs_fa_reason(orderDetailsDto.getDbs_fa_reason());
			orderDetails.setDbs_fa_descr(orderDetailsDto.getDbs_fa_descr());
		}
		if (currentTab.getValue().equalsIgnoreCase("analysis")) {

		}
		if (currentTab.getValue().equalsIgnoreCase("position")) {
			orderDetails.setDbs_pos_text(orderDetailsDto.getDbs_pos_text());
			orderDetails.setDbs_pos_xcmap(orderDetailsDto.isDbs_pos_xcmap());
		}
		if (currentTab.getValue().equalsIgnoreCase("location")) {
			orderDetails.setDbs_remain(orderDetailsDto.getDbs_remain());
		}
		if (currentTab.getValue().equalsIgnoreCase("processing")) {
			orderDetails.setDbs_fa_start(orderDetailsDto.getDbs_fa_start());
			orderDetails.setDbs_fa_stop(orderDetailsDto.getDbs_fa_stop());
			orderDetails.setDbs_fa_name(orderDetailsDto.getDbs_fa_name());
			orderDetails.setDbs_fa_text(orderDetailsDto.getDbs_fa_text());
			orderDetails.setDbs_status(orderDetailsDto.getDbs_status());
			orderDetails.setDbs_fa_archiv_wf(orderDetailsDto.getDbs_fa_archiv_wf());
			orderDetails.setDbs_fa_archiv_ps(orderDetailsDto.getDbs_fa_archiv_ps());
			
		}
		if (currentTab.getValue().equalsIgnoreCase("costs")) {
			orderDetails.setDbs_cost(orderDetailsDto.getDbs_cost());
		}
		if (currentTab.getValue().equalsIgnoreCase("result")) {
			orderDetails.setDbs_res_name(orderDetailsDto.getDbs_res_name());
			orderDetails.setDbs_status(orderDetailsDto.getDbs_status());
			orderDetails.setDbs_res_text(orderDetailsDto.getDbs_res_text());
			orderDetails.setDbs_res_start(orderDetailsDto.getDbs_res_start());
			orderDetails.setDbs_res_stop(orderDetailsDto.getDbs_res_stop());
		}
		if (currentTab.getValue().equalsIgnoreCase("statistics")) {
			orderDetails.setDbs_wait_time1(orderDetailsDto.getDbs_wait_time1());
			orderDetails.setDbs_fa_time(orderDetailsDto.getDbs_fa_time());
			orderDetails.setDbs_wait_time2(orderDetailsDto.getDbs_wait_time2());
			orderDetails.setDbs_res_time(orderDetailsDto.getDbs_res_time());
			orderDetails.setDbs_cpl_time(orderDetailsDto.getDbs_cpl_time());
			orderDetails.setDbs_cost(orderDetailsDto.getDbs_cost());
		}
		return orderDetails;
	}
}
