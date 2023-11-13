package com.mindlease.fa.web;

import java.io.*;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.mindlease.fa.config.LocaleConfig;
import com.mindlease.fa.dto.EmailTemplate;
import com.mindlease.fa.model.*;
import com.mindlease.fa.repository.MethodXRepository;
import com.mindlease.fa.repository.OrderDetailsRepository;
import com.mindlease.fa.repository.PartRepository;
import com.mindlease.fa.repository.UserRepository;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.mindlease.fa.datatable.DataTableRequest;
import com.mindlease.fa.datatable.DataTableResult;
import com.mindlease.fa.dto.OrderDetailsDto;
import com.mindlease.fa.exception.ResourceNotFoundException;
import com.mindlease.fa.mapstruct.mapper.OrderDetailsMapper;
import com.mindlease.fa.security.CustomUserDetailsService;
import com.mindlease.fa.service.OrderDetailsService;
import com.mindlease.fa.util.FailureAnalysisConstants;
import com.mindlease.fa.util.TabValues;

import java.io.File;
import java.awt.Desktop;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/orderdetails")
@Slf4j
public class OrderDetailsController {

	@Autowired
	OrderDetailsService service;
	@Autowired
	OrderDetailsMapper orderDetailsMapper;
	@Autowired
	private CustomUserDetailsService userService;

	@Autowired
	private LocaleResolver localeResolver;

	@Value("${sharedFolderPath}")
	private String sharedFolderPath;

	@Value("${sharedFolderPathURL}")
	private String sharedFolderPathURl;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PartRepository partRepository;

	@Autowired
	OrderDetailsRepository orderDetailsRepository;

	@Autowired
	MethodXRepository methodXRepository;

	SearchParameters searchParameters = new SearchParameters();

	// @GetMapping
	@RequestMapping
	public ModelAndView homepage(Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, RedirectAttributes redirectAttributes,
								 @RequestParam("pageSize") Optional<Integer> pageSize, @RequestParam("page") Optional<Integer> page,
								 @RequestParam("fromLink") Optional<String> fromLink) {
		User user = null;
		Optional<User> userOps = service.findByEmail(principal.getName());
		log.info("{}------------id:::{}", principal.getName(), userOps.isPresent());
		log.info("------------tab--{}:::{}", pageSize, page);
		if (userOps.isPresent()) /* && principal.equals("admin@gmail.com")) */ {
			user = userOps.get();
			//service.findAllPrioritiesById();
			/**   ------------------------------------------ **/
			ModelAndView modelAndView = new ModelAndView("order_details/orders_list");

			modelAndView.addObject(FailureAnalysisConstants.PRIORITY_LIST, service.findAllPriorities());
			modelAndView.addObject(FailureAnalysisConstants.LOCATIONS_LIST, service.findAllLocations());
			modelAndView.addObject(FailureAnalysisConstants.CUSTOMERS_LIST, service.findAllPersonals());
			modelAndView.addObject(FailureAnalysisConstants.MATERIALS_LIST, service.findAllMaterials());
			modelAndView.addObject(FailureAnalysisConstants.PROCESS_STATUS_LIST, service.findAllStatuses());
			modelAndView.addObject("electricErrorList", service.findAllElectricErrors());
			modelAndView.addObject("failureModeList", service.findAllFailureModes());
			modelAndView.addObject("archiveList", service.findAllArchives());
			
			modelAndView.addObject("searchParameters", searchParameters);
			modelAndView.addObject("pageSizes", FailureAnalysisConstants.PAGE_SIZES);
			modelAndView.addObject("defaultPageSize", FailureAnalysisConstants.INITIAL_PAGE_SIZE);
			if (fromLink.isPresent())
				modelAndView.addObject("fromLink", fromLink.get());
			else
				modelAndView.addObject("fromLink", null);
			return modelAndView;
		} else {
			redirectAttributes.addFlashAttribute(FailureAnalysisConstants.FLASH, "Please login and try");
			ModelAndView modelAndView = new ModelAndView("/home");
			return modelAndView;
		}
	}

	// @GetMapping
	@RequestMapping(path = "/search", method = RequestMethod.POST)
	public ModelAndView search(Model model, @ModelAttribute SearchParameters searchParameters,
			BindingResult bindingResult, RedirectAttributes redirectAttributes, Principal principal,
			@RequestParam("pageSize") Optional<Integer> pageSize, @RequestParam("page") Optional<Integer> page,
			@RequestParam("fromLink") Optional<String> fromLink)
	// (Principal principal, RedirectAttributes redirectAttributes,
	// @RequestParam("pageSize") Optional<Integer> pageSize, @RequestParam("page")
	// Optional<Integer> page,@RequestParam("fromLink") Optional<String> fromLink)

	{
		log.info("{}------search------searchParameters:::{}", principal.getName(), searchParameters);
		User user = null;
		Optional<User> userOps = service.findByEmail(principal.getName());
		log.info("{}------search------id:::{}", principal.getName(), userOps.isPresent());
		log.info("-------search-----tab--{}:::{}", pageSize, page);
		if (userOps.isPresent()) {
			user = userOps.get();
			ModelAndView modelAndView = new ModelAndView("order_details/orders_list");
			int evalPageSize = pageSize.orElse(FailureAnalysisConstants.INITIAL_PAGE_SIZE);
			int evalPage = (page.orElse(0) < 1) ? FailureAnalysisConstants.INITIAL_PAGE : page.get() - 1;
			// log.info("here is client repo {}", service.findAll());
			PageRequest pageRequest = PageRequest.of(evalPage, evalPageSize);
			Page<OrderDetails> clientlist = null;// service.findAll(pageRequest);\
			if (searchParameters != null && ((searchParameters.getSCustomername() != null
					&& searchParameters.getSCustomername().length() > 0)
					|| (searchParameters.getSElectricalError() != null
							&& searchParameters.getSElectricalError().length() > 0)
					|| (searchParameters.getSFailureMode() != null && searchParameters.getSFailureMode().length() > 0)
					|| (searchParameters.getSArchWaferBox() != null && searchParameters.getSArchWaferBox().length() > 0)
					|| (searchParameters.getSArchPolyBox() != null && searchParameters.getSArchPolyBox().length() > 0)
					|| (searchParameters.getSMaterial() != null && searchParameters.getSMaterial().length() > 0)
					|| (searchParameters.getSPriority() != null && searchParameters.getSPriority().length() > 0)
					|| (searchParameters.getSStatus() != null && searchParameters.getSStatus().length() > 0))) {
				
						clientlist = service.searchBy(searchParameters.getSMaterial(), searchParameters.getSCustomername(),
						searchParameters.getSPriority(), searchParameters.getSStatus(),
						searchParameters.getSElectricalError(), searchParameters.getSFailureMode(), 
						searchParameters.getSArchWaferBox(), searchParameters.getSArchPolyBox(), pageRequest);
			} else {
				clientlist = new PageImpl<>(new ArrayList<OrderDetails>(0));
				redirectAttributes.addFlashAttribute("flash_searchresults", "Please atleast one parameter to search!");
			}
			log.info("----search------clientlist::{}", clientlist.getSize());
			log.info("----search------searchParameters::{}", searchParameters);
			if (clientlist.getSize() == 0) {
				redirectAttributes.addFlashAttribute("flash_searchresults", "Search return no result!");
			}
			log.info("Order Details List get total pages {}",
					clientlist.getTotalPages() + " \n Order DetailsL ist get number {}", clientlist.getNumber());
			PagerModel pager = new PagerModel(clientlist.getTotalPages(), clientlist.getNumber(),
					FailureAnalysisConstants.BUTTONS_TO_SHOW);
			log.info("----search------clientlist::{}", clientlist.getSize());
			log.info("----search------searchParameters::{}", searchParameters);
			//service.findAllPrioritiesById();
			modelAndView.addObject(FailureAnalysisConstants.PRIORITY_LIST, service.findAllPriorities());
			modelAndView.addObject(FailureAnalysisConstants.LOCATIONS_LIST, service.findAllLocations());
			modelAndView.addObject(FailureAnalysisConstants.CUSTOMERS_LIST, service.findAllPersonals());
			modelAndView.addObject(FailureAnalysisConstants.MATERIALS_LIST, service.findAllMaterials());
			modelAndView.addObject(FailureAnalysisConstants.PROCESS_STATUS_LIST, service.findAllStatuses());
			modelAndView.addObject("archiveList", service.findAllArchives());
			model.addAttribute("archiveList", service.findAllArchives());
			modelAndView.addObject("electricErrorList", service.findAllElectricErrors());
			modelAndView.addObject("failureModeList", service.findAllFailureModes());

			modelAndView.addObject("orderDetailsList", clientlist);
			modelAndView.addObject("searchParameters", searchParameters);
			modelAndView.addObject("selectedPageSize", evalPageSize);
			modelAndView.addObject("pageSizes", FailureAnalysisConstants.PAGE_SIZES);
			modelAndView.addObject("pager", pager);
			modelAndView.addObject("fromLink", "search");
			modelAndView.addObject("language", clientlist);
			return modelAndView;
		} else {
			redirectAttributes.addFlashAttribute(FailureAnalysisConstants.FLASH, "Please login and try");
			ModelAndView modelAndView = new ModelAndView("/home");
			return modelAndView;
		}
	}

	// @GetMapping
	public ModelAndView homepageWithoutPagination(Principal principal, RedirectAttributes redirectAttributes) {
		User user = null;
		Optional<User> userOps = service.findByEmail(principal.getName());
		if (userOps.isPresent()) {
			user = userOps.get();
			ModelAndView modelAndView = new ModelAndView("order_details/orders_list");
			List<OrderDetails> clientlist = service.getAllByCompany(user.getCompany());// getAllOrderDetails();
			modelAndView.addObject("orderDetailsList", clientlist);
			return modelAndView;
		} else {
			redirectAttributes.addFlashAttribute(FailureAnalysisConstants.FLASH, "Please login and try");
			ModelAndView modelAndView = new ModelAndView("/home");
			return modelAndView;
		}
	}

	@RequestMapping(path = { "/orderSuccess" })
	public String orderSuccess(Model modell, RedirectAttributes redirectAttributes) throws ResourceNotFoundException {
		return "order_details/order_success";
	}

	@RequestMapping(path = { "/create", "/edit" })
	public String editById(Model model, @ModelAttribute OrderDetails orderDetailsDto,HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) throws ResourceNotFoundException {
		Locale currentLocale = localeResolver.resolveLocale(httpServletRequest);
		String language = localeResolver.resolveLocale(httpServletRequest).getLanguage();
		System.out.println("----------------------------------Locale-------------------------------------");
		System.out.println(currentLocale);
		System.out.println(language
		);

		log.info("------------id:::{}", orderDetailsDto.getId());
		log.info("------------tab:::{}", orderDetailsDto.getTab());

		edit(model, Optional.ofNullable(orderDetailsDto.getId()), Optional.ofNullable(orderDetailsDto.getTab()));
		return "order_details/order_edit";
	}

	private void edit(Model model, Optional<Long> id, Optional<TabValues> tab) {


		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		System.out.println("User has authorities: " + userDetails.getAuthorities());
		User user = userService.findByEmail(currentPrincipalName);
		String firstName=user.getFirstName();
		String lastName=user.getLastName();
		String clientName=(firstName.substring(0, 1)+lastName).toLowerCase();


		model.addAttribute("user", user);
	//	model.addAttribute("clientName", clientName);
		OrderDetails entity = null;
		if (id.isPresent()) {
			entity = service.findById(id.get()).get();
            // If Order exist display personal phone no from Personal based on Client name
			Optional<Personal> personal = service.findPersonByShort(entity.getDbs_ag_name());
			model.addAttribute("personal_phone", personal.isPresent()?personal.get().getPers_phone():"no phone");
			model.addAttribute("clientName", entity.getDbs_ag_name());
		} else {
			entity = new OrderDetails();
			entity.setDbs_fa_date(new Date());
			// If Order doesnt exist then  personal phone no will be 'phone no'
			model.addAttribute("personal_phone", "no phone");
			model.addAttribute("clientName", clientName);
		}
		boolean isFA = AuthorityUtils.authorityListToSet(userDetails.getAuthorities()).contains("FA");
		boolean isFARequester = AuthorityUtils.authorityListToSet(userDetails.getAuthorities())
				.contains("FA-REQUESTOR");
		if (!tab.isPresent()) {
			//TabValues currentTab = isFA ? TabValues.values()[6] : TabValues.values()[0];
			TabValues currentTab = TabValues.values()[0];

			if (currentTab != null) {
				model.addAttribute(FailureAnalysisConstants.CURRENT_TAB, currentTab);
				model.addAttribute(FailureAnalysisConstants.PREVIOUS_TAB,
						(isFA && currentTab.ordinal() == TabValues.TAB7.ordinal()) ? null : currentTab.getPrevious());
				model.addAttribute(FailureAnalysisConstants.NEXT_TAB, currentTab.getNext());
				entity.setCurrentTab(currentTab);
				entity.setPreviousTab(currentTab.getPrevious());
				entity.setNextTab((isFARequester && currentTab.ordinal() == TabValues.TAB6.ordinal()) ? null
						: currentTab.getNext());
			}
			model.addAttribute("userName", currentPrincipalName);
			model.addAttribute("userDetails", userDetails);
			model.addAttribute(FailureAnalysisConstants.ORDER_DETAILS, entity);
			model.addAttribute(FailureAnalysisConstants.PRIORITY_LIST, service.findAllPriorities());
			model.addAttribute(FailureAnalysisConstants.LOCATIONS_LIST, service.findAllLocations());
			model.addAttribute(FailureAnalysisConstants.CUSTOMERS_LIST, service.findAllPersonals());
			model.addAttribute(FailureAnalysisConstants.MATERIALS_LIST, service.findAllMaterials());
			model.addAttribute(FailureAnalysisConstants.PROCESS_STATUS_LIST, service.findAllStatuses());

		}
		else {
			TabValues currentTab = tab.get();
			model.addAttribute(FailureAnalysisConstants.CURRENT_TAB, currentTab);
			model.addAttribute(FailureAnalysisConstants.PREVIOUS_TAB,
					(isFA && currentTab.ordinal() == TabValues.TAB7.ordinal()) ? null : currentTab.getPrevious());
			model.addAttribute(FailureAnalysisConstants.NEXT_TAB,
					(isFARequester && currentTab.ordinal() == TabValues.TAB6.ordinal()) ? null : currentTab.getNext());
			entity.setCurrentTab(currentTab);
			entity.setPreviousTab(currentTab.getPrevious());
			entity.setNextTab(currentTab.getNext());
			model.addAttribute("userName", currentPrincipalName);
			model.addAttribute("userDetails", userDetails);
			model.addAttribute(FailureAnalysisConstants.ORDER_DETAILS, entity);
			if (currentTab.getValue().equalsIgnoreCase(FailureAnalysisConstants.ORDER)) {
				model.addAttribute(FailureAnalysisConstants.PRIORITY_LIST, service.findAllPriorities());
				model.addAttribute(FailureAnalysisConstants.LOCATIONS_LIST, service.findAllLocations());
				model.addAttribute(FailureAnalysisConstants.CUSTOMERS_LIST, service.findAllPersonals());
				model.addAttribute(FailureAnalysisConstants.MATERIALS_LIST, service.findAllMaterials());
				model.addAttribute(FailureAnalysisConstants.PROCESS_STATUS_LIST, service.findAllStatuses());
			}
			if (currentTab.getValue().equalsIgnoreCase(FailureAnalysisConstants.GEOMETRY)) {
				model.addAttribute("partList", service.findAllParts());
				log.info("------geometry---partName:::{}", entity.getDbs_part());
				Part part = service.findPartByName(entity.getDbs_part());
				log.info("-----geometry----onChangePartPost---partId:::{}", part);
				entity.setPart(part);
				model.addAttribute("part", part);
			}
			if (currentTab.getValue().equalsIgnoreCase(FailureAnalysisConstants.REASON)) {
				model.addAttribute("electricErrorList", service.findAllElectricErrors());
				model.addAttribute("failureModeList", service.findAllFailureModes());
			}
			if (currentTab.getValue().equalsIgnoreCase(FailureAnalysisConstants.ANALYSIS)) {
				model.addAttribute("generalMethodsList", service.findAllGeneralMethods());
				model.addAttribute("housedMethodsList", service.findAllHouseMethods());
				model.addAttribute("wafersMethodsList", service.findAllWaferMethods());
				// model.addAttribute("methodList", service.findAllMethods());
				// model.addAttribute("methodXList", service.findAllMethodXs());

				entity.setWafersExaminationMethods(service.findSelectedWaferMethods(entity));
				entity.setGeneralInvestigationMethods(service.findSelectedGeneralMethods(entity));
				entity.setHousedExaminationMethods(service.findSelectedHouseMethods(entity));

			}
			if (currentTab.getValue().equalsIgnoreCase(FailureAnalysisConstants.LOCATION)) {
				//model.addAttribute("archiveList", service.findAllArchives());
				model.addAttribute(FailureAnalysisConstants.SAMPLES_REMAIN_LIST, service.findAllSampleRemains());
			}
			if (currentTab.getValue().equalsIgnoreCase(FailureAnalysisConstants.PROCESSING)) {
				model.addAttribute("processorList", service.findAllPersonals());
				model.addAttribute("processStatusList", service.findAllStatuses());
				model.addAttribute("archiveList", service.findAllArchives());
			}
			if (currentTab.getValue().equalsIgnoreCase(FailureAnalysisConstants.COSTS)) {
				model.addAttribute("costsList", service.findAllCosts());
			}
			if (currentTab.getValue().equalsIgnoreCase(FailureAnalysisConstants.RESULT)) {
				model.addAttribute("evaluatorList", service.findAllPersonals());
				model.addAttribute("processStatusList", service.findAllStatuses());
			}

	}

	}

	@RequestMapping(path = "/delete")
	public String deleteById(Model model, @RequestParam(value = "id") Long id) throws ResourceNotFoundException {
		service.deleteById(id);
		return "redirect:/orderdetails";
	}

	@RequestMapping(path = "/update", method = RequestMethod.POST)
	public String createOrUpdate(Model model, @ModelAttribute OrderDetails orderDetailsDto, BindingResult bindingResult,
			RedirectAttributes redirectAttributes, Principal principal) {
		log.info("--------update----id:::{}", orderDetailsDto.getId());
		log.info("--------update----tab:::{}", orderDetailsDto.getTab());
		log.info("--------update----tab:::{}", orderDetailsDto.getCurrentTab());	

		log.info(String.valueOf(orderDetailsDto));
		TabValues currentTab = orderDetailsDto.getCurrentTab();
		if (orderDetailsDto.getAction().equalsIgnoreCase("previous")) {
			if (currentTab != null) {
				currentTab = currentTab.getPrevious();
			}
			if (currentTab == null) {
				currentTab = TabValues.values()[0];
			}
			edit(model, Optional.of(orderDetailsDto.getId()), Optional.of(currentTab));
			return "order_details/order_edit";
		}

		User user = null;
		Optional<User> userOps = service.findByEmail(principal.getName());
		if (userOps.isPresent())
			user = userOps.get();
		else {
			redirectAttributes.addFlashAttribute(FailureAnalysisConstants.FLASH, "Please login and try");
			edit(model, Optional.of(orderDetailsDto.getId()), Optional.of(currentTab));
			return "order_details/order_edit";
		}
		if (bindingResult.hasErrors()) {
			log.info("------------/bindingResult.hasErrors():::{}", bindingResult.getAllErrors());
			redirectAttributes.addFlashAttribute(FailureAnalysisConstants.FLASH, "Check Errors!");
			edit(model, Optional.of(orderDetailsDto.getId()), Optional.of(currentTab));
			return "order_details/order_edit";
		} else {
			OrderDetails orderDetails = orderDetailsDto.getId() != null
					? service.findById(orderDetailsDto.getId()).orElse(new OrderDetails())
					: new OrderDetails();

			User oldUser=null;
			boolean isUpdate=false;
			if(orderDetails.getId() != null) {
				isUpdate=true;
				oldUser=orderDetails.getUser();
				
			}
			
			orderDetails = service.setData(orderDetails, orderDetailsDto, orderDetailsDto.getCurrentTab());
			if(!isUpdate)//means creating not updating
				orderDetails.setUser(user);
			else
				orderDetails.setUser(oldUser);
			
			String firstName=user.getFirstName();
			String lastName=user.getLastName();
			
			String clientNameToSave=(firstName.substring(0, 1)+lastName).toLowerCase();
			
			String clientName=orderDetails.getDbs_ag_name();
			if(clientName==null||clientName.isEmpty() || clientName.trim().isEmpty()) {
				orderDetails.setDbs_ag_name(clientNameToSave);
			}
			
			
			orderDetails = service.create(orderDetails);
			
			if (!orderDetailsDto.getAction().equalsIgnoreCase("continue"))
				redirectAttributes.addFlashAttribute(FailureAnalysisConstants.FLASH,
						FailureAnalysisConstants.ORDER_UPDATE);

			OrderDetails entity = service.findById(orderDetails.getId()).get();
			if (currentTab.ordinal() == TabValues.TAB10.ordinal()) {
				log.info("------------bindingResult.hasErrors():::{}", bindingResult.getAllErrors());
				redirectAttributes.addFlashAttribute(FailureAnalysisConstants.FLASH,
						FailureAnalysisConstants.ORDER_SUCCESSFULLY_COMPLETED);
				return "redirect:/orderdetails/orderSuccess";
			}
			if (currentTab != null && orderDetailsDto.getAction().equalsIgnoreCase("continue")) {
				currentTab = currentTab.getNext();
			}
			if (currentTab == null) {
				currentTab = TabValues.values()[0];
			}
			if (!orderDetailsDto.getAction().equalsIgnoreCase("continue")) {
				redirectAttributes.addFlashAttribute(FailureAnalysisConstants.FLASH,
						FailureAnalysisConstants.ORDER_UPDATE);
				model.addAttribute(FailureAnalysisConstants.FLASH, FailureAnalysisConstants.ORDER_UPDATE);
			}

			edit(model, Optional.of(entity.getId()), Optional.of(currentTab));
			return "order_details/order_edit";
		}
	}

	@RequestMapping(path = "/orderdetails/autosave", method = RequestMethod.POST)
	public void autoSaveForm(Model model, @ModelAttribute OrderDetails orderDetailsDto, Principal principal) {

		log.info("------------------------------Auto save--------------------------");
		log.info("--------autosave----id:::{}", orderDetailsDto.getId());
		log.info("--------autosave----tab:::{}", orderDetailsDto.getTab());
		log.info("--------autosave----tab:::{}", orderDetailsDto.getCurrentTab());

		log.info(String.valueOf(orderDetailsDto));

		User user = null;
		Optional<User> userOps = service.findByEmail(principal.getName());
		if (userOps.isPresent())
			user = userOps.get();


		OrderDetails orderDetails = orderDetailsDto.getId() != null
				? service.findById(orderDetailsDto.getId()).orElse(new OrderDetails())
				: new OrderDetails();

		log.info(String.valueOf(orderDetails));

		User oldUser=null;
		boolean isUpdate=false;
		if(orderDetails.getId() != null) {
			isUpdate=true;
			oldUser=orderDetails.getUser();

		}

		orderDetails = service.setData(orderDetails, orderDetailsDto, orderDetailsDto.getCurrentTab());
		if(!isUpdate)//means creating not updating
			orderDetails.setUser(user);
		else
			orderDetails.setUser(oldUser);

		String firstName=user.getFirstName();
		String lastName=user.getLastName();

		String clientNameToSave=(firstName.substring(0, 1)+lastName).toLowerCase();

		String clientName=orderDetails.getDbs_ag_name();
		if(clientName==null||clientName.isEmpty() || clientName.trim().isEmpty()) {
			orderDetails.setDbs_ag_name(clientNameToSave);
		}
        log.info("--------------------Model---------------------");
		log.info(String.valueOf(model));


		edit(model, Optional.ofNullable(orderDetails.getId()), Optional.ofNullable(orderDetails.getCurrentTab()));
		//Commented below line to add orderDetails
//		orderDetails = service.create(orderDetails);


	}

	@RequestMapping(path = "/partDetails/{partId}")
	public String onChangePart(Model model, @PathVariable("partId") Long partId) throws ResourceNotFoundException {
		log.info("--------onChangePart----partId:::{}", partId);
		model.addAttribute("part", service.findPartById(partId));
		return "order_details/geometry :: partDetails";
	}

	@RequestMapping(path = "/partDetails", method = RequestMethod.POST)
	public String onChangePartPost(Model model, String partName) throws ResourceNotFoundException {
		log.info("---------partName:::{}", partName);

		Part part = service.findPartByName(partName);
		log.info("---------onChangePartPost---partId:::{}", part);
		if (part == null) {
			log.info("---------if---part:::{}", part);
			model.addAttribute("part", new Part());
		} else {
			log.info("---------else---part:::{}", part);
			model.addAttribute("part", part);
		}
		return "order_details/geometry :: partDetails";
	}

	@RequestMapping(path = "/onAnalysisMethodsChange", method = RequestMethod.POST)
	public String onAnalysisMethodsChange(Model model, @ModelAttribute OrderDetails orderDetailsDto,
			BindingResult bindingResult, RedirectAttributes redirectAttributes, Principal principal) {

		log.info("------------------getGeneralInvestigationMethods::::::::::{}",
				orderDetailsDto.getGeneralInvestigationMethods());
		log.info("------------------getHousedExaminationMethods::::::::::{}",
				orderDetailsDto.getHousedExaminationMethods());
		log.info("------------------getWafersExaminationMethods::::::::::{}",
				orderDetailsDto.getWafersExaminationMethods());
		model.addAttribute("generalInvestigationMethods", orderDetailsDto.getGeneralInvestigationMethods());
		model.addAttribute("housedExaminationMethods", orderDetailsDto.getHousedExaminationMethods());
		model.addAttribute("wafersExaminationMethods", orderDetailsDto.getWafersExaminationMethods());

		return "order_details/analysis_methods.html :: analysisMethodDetails";
	}

	@RequestMapping(path = "/onAnalysisMethodsDelete", method = RequestMethod.POST)
	public String onAnalysisMethodsChangeDelete(Model model, @ModelAttribute OrderDetails orderDetailsDto,
			BindingResult bindingResult, RedirectAttributes redirectAttributes, Principal principal) {

		log.info("------------------getGeneralInvestigationMethods::::::::::{}",
				orderDetailsDto.getGeneralInvestigationMethods());
		log.info("------------------getHousedExaminationMethods::::::::::{}",
				orderDetailsDto.getHousedExaminationMethods());
		log.info("------------------getWafersExaminationMethods::::::::::{}",
				orderDetailsDto.getWafersExaminationMethods());
		model.addAttribute("generalInvestigationMethods", orderDetailsDto.getGeneralInvestigationMethods());
		model.addAttribute("housedExaminationMethods", orderDetailsDto.getHousedExaminationMethods());
		model.addAttribute("wafersExaminationMethods", orderDetailsDto.getWafersExaminationMethods());

		return "order_details/analysis_methods.html :: analysisMethodDetails";
	}
	
	@RequestMapping(value = "/orders")
	@ResponseBody
	public Object getOrderList(@Valid @RequestBody DataTableRequest input, Principal principal) {
		Optional<User> userOps = service.findByEmail(principal.getName());
		if(userOps.isPresent()) {
			User user = userOps.get();
			String isAdmin = "N";
			for(Role role :user.getRoles()) {
				System.out.println("role-----------------------------" + role.getName());
				if(role.getName().equalsIgnoreCase("admin") || role.getName().equalsIgnoreCase("SUPER-ADMIN") || role.getName().equalsIgnoreCase("fa")) {
					isAdmin = "Y";
					break;
				}
			}
			input.getExternalFilter().put("isAdmin", isAdmin);
			input.getExternalFilter().put("userId", String.valueOf(user.getId()));
		}
		DataTableResult<OrderDetails> result  =(DataTableResult)service.getOrders(input);
		List<OrderDetailsDto> orderDetailsDtos = new ArrayList<>();
		for (OrderDetails o : result.getData()) {
			orderDetailsDtos.add(orderDetailsMapper.convertOrderDetailsToOrderDetailsDto(o));
		}



		orderDetailsDtos.forEach(orderDetailsDto -> {

			List<MethodX> list = methodXRepository.findAllGeneralMethodsByOrderId(orderDetailsDto.getId());
			StringBuilder methods = new StringBuilder();
			for(MethodX methodX : list){
				methods.append(methodX.getName());
				methods.append("<br>");
			}
			orderDetailsDto.setDbs_method_temp(methods.toString());

		});
		DataTableResult<OrderDetailsDto> response = new DataTableResult<>();
		response.setRecordsTotal(result.getRecordsTotal());
		response.setDraw(result.getDraw());
		response.setRecordsFiltered(result.getRecordsFiltered());
		response.setData(orderDetailsDtos);
		
		return response;
	}

//	@RequestMapping(value = "/sharedAccess/{orderDetails}")
//	public ModelAndView connectFtpServer(@PathVariable("orderDetails") String orderDetails) throws IOException, ResourceNotFoundException {
////
////		String response = service.connectFtpServer(orderDetails);
////		if (!response.equalsIgnoreCase("found")) {
////			log.info("------------------------- order details not found ------------------------- ");
////			ModelAndView modelAndView = new ModelAndView();
////			modelAndView.setViewName("404.html");
////
////			return modelAndView;
////		}
////		try {
////			Process process = Runtime.getRuntime().exec("cmd.exe /c net use Z: "+sharedFolderPathURl);
////			process.waitFor();
////			if (process.exitValue() == 0) {
////				log.info("The shared folder has been mapped as the Z: drive successfully.");
////				Runtime.getRuntime().exec("cmd /c start    Z: "+ sharedFolderPathURl);
////			} else {
////				log.error("An error occurred while mapping the shared folder as the Z: drive.");
////			}
////		} catch (IOException | InterruptedException e) {
////			log.error("An error occurred: " + e.getMessage());
////		}
////		Runtime.getRuntime().exec("cmd /c start "+ sharedFolderPath+orderDetails);
////		Runtime.getRuntime().exec("cmd /c start "+sharedFolderPathURl);
//
//		try {
//			File remotePath = new File(sharedFolderPathURl);
//			Desktop.getDesktop().open(remotePath);
//			log.info("Open Successfully");
//		} catch (Exception e) {
//			log.error("An error occurred: " + e.getMessage());
//		}
//		return new ModelAndView("redirect:/orderdetails");
//
//	}
//



	@RequestMapping(path = {"/change/language/{ln}", "/orderdetails/change/language/{ln}"},method = RequestMethod.GET)
	public void changeLanguage(@PathVariable("ln") String language, HttpServletRequest httpServletRequest , HttpServletResponse httpServletResponse){
		log.info("-----------Changing language-----------------");
		log.info("------------Reuqest---------{}",httpServletRequest.getRequestURL());
		LocaleConfig localeConfig = new LocaleConfig();
		if(language.equalsIgnoreCase("de")) {
			localeConfig.localeResolver().setLocale(httpServletRequest, httpServletResponse, Locale.GERMAN);
		} else {
			localeConfig.localeResolver().setLocale(httpServletRequest, httpServletResponse, Locale.US);
		}

	}

	@RequestMapping(path = {"/print/order/details"} , method = RequestMethod.GET)
	public void printOrderDetails() throws IOException {
		    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		    InputStream inputStream = classLoader.getResourceAsStream("static/html/insurance.html");
		    OutputStream os = new FileOutputStream("Test.pdf");
		    PdfRendererBuilder builder = new PdfRendererBuilder();
			builder.useFastMode();
			builder.withUri("file:///Users/me/Documents/pdf/in.htm");
			builder.toStream(os);
			builder.run();

	}

	@RequestMapping(path = "/openSharedFolderLink", method = RequestMethod.GET)
	public String openSharedFolderLink(){

		return "order_details/SharedFolderStructure.html";
	}
	@RequestMapping(path = "/printOrder/{id}",method = RequestMethod.GET)
	public String printOrder(@PathVariable("id") Long id, Model model){

		Optional<OrderDetails> orderDetails = orderDetailsRepository.findById(id);
		model.addAttribute("orderDetails",orderDetails.get());

		Optional<Personal> personal = service.findPersonByShort(orderDetails.get().getDbs_ag_name());
		model.addAttribute("personal_phone", personal.isPresent()?personal.get().getPers_phone():"no phone");
		Optional<Part> part = Optional.ofNullable(partRepository.findByName(orderDetails.get().getDbs_part()));
		if(part.isPresent()){
			model.addAttribute("part",part.get());
		}else {
			model.addAttribute("part",new Part());
		}
		List<MethodX> methodXList = methodXRepository.findAllGeneralMethodsByOrderId(orderDetails.get().getId());
		model.addAttribute("methods",methodXList);

		//Print Date
		LocalDateTime localDateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, d MMM yyyy HH:mm:ss");
		String formattedDateTime = localDateTime.format(formatter);
		model.addAttribute("printDate",formattedDateTime);

		return  "OrderPrint.html";
	}


	@PostMapping(path = "/orderdetails/sendEmail")
	@ResponseBody
	public EmailTemplate sendEmail(Principal principal, Model model, @ModelAttribute OrderDetails orderDetailsDto){

		EmailTemplate emailTemplate = new EmailTemplate();

		Map<String,String> replacementMap = new HashMap<>();

		OrderDetails orderDetails = orderDetailsRepository.findById(orderDetailsDto.getId()).get();

		System.out.println(orderDetails);

		User recipientDetails = orderDetails.getUser();
		User senderDetails = userRepository.findByEmail(principal.getName()).get();

		if(recipientDetails == null || senderDetails  == null){
			emailTemplate.setStatus("error");
		}else {
			emailTemplate.setStatus("success");
		}

		replacementMap.put("ORDER_ID", String.valueOf(orderDetails.getId()));
		replacementMap.put("LOT_ID", orderDetails.getDbs_lotid());
		replacementMap.put("GEOMETRY_ID",orderDetails.getDbs_part());

		replacementMap.put("TO_NAME", recipientDetails!=null ? recipientDetails.getFirstName() : "" );
		replacementMap.put("FROM_NAME", senderDetails!=null ? senderDetails.getFirstName() : "");

		log.info("--------recipient-------{}",recipientDetails);
		log.info("---------sender----------{}",senderDetails);

		emailTemplate.setMailTo( recipientDetails!=null ?recipientDetails.getEmail():"");

		if(recipientDetails!=null ) {
			if(recipientDetails.getLanguage()!=null) {
				if (recipientDetails.getLanguage().equals("en")) {

					if(orderDetails!=null ) {
						emailTemplate.setSubject("Order No." + orderDetails.getId() + ": The analysis is finished");
					}
					ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
					InputStream inputStream = classLoader.getResourceAsStream("templates/English_EmailTemplate.txt");

					// File file = ResourceUtils.getFile("classpath:static/html/LinkExpired.html");
					StringBuilder contentBuilder = new StringBuilder();
					try {
						BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
						String str;
						while ((str = in.readLine()) != null) {
							contentBuilder.append(str);
						}
						in.close();
					} catch (IOException e) {
						log.error(e.getMessage());
					}
					System.out.println(contentBuilder);
					if (contentBuilder.length() > 0) {

						String content = contentBuilder.toString();

						System.out.println(content);

						for (Map.Entry<String, String> entry : replacementMap.entrySet()) {
							String key = entry.getKey();
							String value = entry.getValue();
							content = content.replaceAll(key, value);
						}

						emailTemplate.setEmailBody(content);


					}

				}
				else {

					if(orderDetails!=null ) {
						emailTemplate.setSubject("Auftrag-Nr."+orderDetails.getId()+": Die Analyse ist fertig");
					}
					ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
					InputStream inputStream = classLoader.getResourceAsStream("templates/German_EmailTemplate.txt");

					// File file = ResourceUtils.getFile("classpath:static/html/LinkExpired.html");
					StringBuilder contentBuilder = new StringBuilder();
					try {
						BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
						String str;
						while ((str = in.readLine()) != null) {
							contentBuilder.append(str);
						}
						in.close();
					} catch (IOException e) {
						log.error(e.getMessage());
					}
					if (contentBuilder.length() > 0) {

						String content = contentBuilder.toString();
						for (Map.Entry<String, String> entry : replacementMap.entrySet()) {
							String key = entry.getKey();
							String value = entry.getValue();
							content = content.replaceAll(key, value);
						}

						emailTemplate.setEmailBody(content);


					}

				}
			}else {
				emailTemplate.setStatus("error");
			}
		}

		System.out.println(emailTemplate);
		return  emailTemplate;
	}



}