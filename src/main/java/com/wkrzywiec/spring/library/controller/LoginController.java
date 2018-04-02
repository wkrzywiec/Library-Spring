package com.wkrzywiec.spring.library.controller;

import java.util.Arrays;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.wkrzywiec.spring.library.dto.UserDTO;
import com.wkrzywiec.spring.library.service.LibraryUserDetailService;

@Controller
public class LoginController {

	/*@Autowired
    ApplicationContext applicationContext;*/
	
	@Autowired
	LibraryUserDetailService userService;
	
	@GetMapping("/loginPage")
	public String showLoginPage(){
		return "loginPage";
	}
	
	@GetMapping("/access-denied")
	public String accessDenied(){
		return "access-denied";
	}
	
	@GetMapping("/register-user")
	public String registerUser(Model model){
		
		//System.out.println(Arrays.asList(applicationContext.getBeanDefinitionNames()));
		
		UserDTO userDTO = new UserDTO();
		model.addAttribute("user", userDTO);
		
		return "register-user";
	}
	
	@PostMapping("/loginPage")
	public String showLoginPageAfterSignUp(){
		return "loginPage";
	}
	
	@PostMapping("/register-user")
	public String processRegisterForm(
				@Valid @ModelAttribute("user") UserDTO userDTO,
				BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()){
			return "register-user";
		} else {
			userService.saveReaderUser(userDTO);
			return "successful-registration";
		}
	}
	
}
