package com.mindlease.fa.web;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.mindlease.fa.config.LocaleConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mindlease.fa.exception.ResourceNotFoundException;
import com.mindlease.fa.model.User;
import com.mindlease.fa.security.CustomUserDetailsService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/registration")
@Slf4j
public class UserRegistrationController {

	@Autowired
	private CustomUserDetailsService userService;

	@PostMapping
	public String registerUserAccount(Model model, @ModelAttribute("user") @Valid User userDto, BindingResult result,
			RedirectAttributes redirectAttributes) {
		User tosave = null;
		log.info("---------------------hasErrors::{}", result.getAllErrors());
		User existing = userService.findByEmail(userDto.getEmail());
		if (userDto.getId() != null) {
			tosave = userService.findById(Optional.of(userDto.getId())).get();
			tosave.setEmail(userDto.getEmail());
			tosave.setFirstName(userDto.getFirstName());
			tosave.setLastName(userDto.getLastName());
			tosave.setRole(userDto.getRole());
			tosave.setPassword(existing.getPassword());

			model.addAttribute("mode", "edit");
			redirectAttributes.addFlashAttribute("flash_usercreat", "User Suceesfully updated");
		} else {
			if (existing != null) {
				result.rejectValue("email", null, "There is already an account registered with that email");
			}
			tosave = userDto;
			String trimmedInput =  userDto.getPassword().trim();
			boolean isPasswordEmpty = trimmedInput.isEmpty();
			if (userDto.getPassword() == null || isPasswordEmpty)
				result.rejectValue("password", null, "Password should not be empty.");
			if (userDto.getPassword() != null && userDto.getPassword().length() < 4)
				result.rejectValue("password", null, "Password should contain minimum 4 characters");
			model.addAttribute("mode", "save");

			redirectAttributes.addFlashAttribute("flash_usercreat", "User Successfully Created");

		}

		if (result.hasErrors()) {
			List<String> errorList = new ArrayList<>();
			log.info("---------------------hasErrors::{}", result.getAllErrors());

			model.addAttribute("rolesList", userService.findAllRoles().stream().filter(role->!(
					role.getName().equalsIgnoreCase("SUPER-ADMIN")
							|| role.getName().equalsIgnoreCase("FA") || role.getName().equalsIgnoreCase("FA-REQUESTOR"))).collect(Collectors.toList()));
			model.addAttribute("companyList", userService.findAllCompanies());
			model.addAttribute("isError",true);

			result.getAllErrors().forEach(x->{
				errorList.add(x.getDefaultMessage());
			});
			model.addAttribute("errorMessages",errorList);
			/*model.addAttribute("rolesList", userService.findAllRoles());*/
			redirectAttributes.addFlashAttribute("flash_usercreat", "Check Errors!");
			return "admin/user_register";
		}
		tosave.setCompany(userDto.getCompany());
		tosave.setLanguage(userDto.getLanguage());
		User saved = userService.save(tosave);
		model.addAttribute("success",true);
		model.addAttribute("successMessage", "User Successfully Created" );
		return "redirect:/registration/userManagement";
	}

	@RequestMapping("/userManagement")
	public String getAll(Model model) {
		model.addAttribute("usersList", userService.getUsers());
		return "admin/user_list";
	}

	@RequestMapping("/settings")
	public String getProfileDetails(Model model) {
		return "admin/settings";
	}

	@RequestMapping("/changePassword")
	public String getChangePassword(Principal principal, Model model) {
		log.info("------------changePassword---------principal::{}", principal.getName());
		User entity = userService.findByEmail(principal.getName());
		model.addAttribute("user", entity);
		return "admin/change_password";
	}

	@RequestMapping(path = { "/edit" })
	public String editById(Model model, @RequestParam("id") Optional<Integer> id,
			@RequestParam("mode") Optional<String> mode) throws ResourceNotFoundException {
		if (id.isPresent()) {
			User entity = userService.findById(id).get();
			entity.setRole(!entity.getRoles().isEmpty() ? entity.getRoles().iterator().next() : null);
			model.addAttribute("user", entity);
			model.addAttribute("mode", mode.get());
		} else {
			model.addAttribute("user", new User());
			model.addAttribute("mode", "save");
		}

		/*model.addAttribute("rolesList", userService.findAllRoles());*/

		model.addAttribute("modalAttribute",true);
		model.addAttribute("rolesList", userService.findAllRoles().stream().filter(role->!(role.getName().equalsIgnoreCase("SUPER-ADMIN")
				|| role.getName().equalsIgnoreCase("FA") || role.getName().equalsIgnoreCase("FA-REQUESTOR"))).collect(Collectors.toList()));
		model.addAttribute("companyList", userService.findAllCompanies());

		return "admin/user_register";
	}

	@RequestMapping(path = { "/delete" })
	public String deleteById(Model model, @RequestParam("id") Integer id, RedirectAttributes redirectAttributes)
			throws ResourceNotFoundException {
		userService.deleteById(id);
		redirectAttributes.addFlashAttribute("flash_delete", "User Deleted Successfully!");
		return "redirect:/registration/userManagement";
	}

	@RequestMapping("/saveChangePassword")
	public String saveChangePassword(Model model, @ModelAttribute("user") User userDto,
			RedirectAttributes redirectAttributes) {
		List<String> errorList = new ArrayList<>();

		User existing = userService.findById(Optional.of(userDto.getId())).get();
		if (userDto.getPassword() != null && !userService.checkPasswod(userDto.getPassword(), existing)) {
			//bindingResult.rejectValue("password", null, "Old Password doesn't correct");
			errorList.add("Old Password doesn't correct");
		}

		if (userDto.getNewPassword() != null && userDto.getConfirmPassword() != null
				&& !userDto.getNewPassword().equals(userDto.getConfirmPassword())) {
			//bindingResult.rejectValue("confirmPassword", null, "New Password should match with Confirm Password");
			errorList.add("New Password should match with Confirm Password");
		}
		existing.setNewPassword(userDto.getNewPassword());
		existing.setConfirmPassword(userDto.getConfirmPassword());
//		log.info("------------/bindingResult:::{}", bindingResult.getAllErrors());
		if (!errorList.isEmpty()) {
			model.addAttribute("isError",true);
			model.addAttribute("errorMessages",errorList);
			redirectAttributes.addFlashAttribute("flash_password", "Check Errors!");
			return "admin/change_password";
		} else {
			userService.saveChangePassword(existing);
		}
		redirectAttributes.addFlashAttribute("flash_password", "Password updated!");
		return "redirect:/registration/userManagement";
	}

	@RequestMapping(path = {"/registration/userManagement/{ln}", "/orderdetails/change/language/{ln}"},method = RequestMethod.GET)
	public void changeLanguage(@PathVariable("ln") String language, HttpServletRequest httpServletRequest , HttpServletResponse httpServletResponse){
		log.info("-----------Changing language-----------------");
		log.info("------------Request---------{}",httpServletRequest.getRequestURL());
		LocaleConfig localeConfig = new LocaleConfig();
		if(language.equalsIgnoreCase("de")) {
			localeConfig.localeResolver().setLocale(httpServletRequest, httpServletResponse, Locale.GERMAN);
		} else {
			localeConfig.localeResolver().setLocale(httpServletRequest, httpServletResponse, Locale.US);
		}

	}
}
