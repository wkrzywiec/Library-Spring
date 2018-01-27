package com.wkrzywiec.spring.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.wkrzywiec.spring.library.dto.UserDTO;
import com.wkrzywiec.spring.library.service.LibraryUserDetailService;

@Controller
public class LoginController {

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
		
		UserDTO userDTO = new UserDTO();
		model.addAttribute("user", userDTO);
		
		return "register-user";
	}
	
	@PostMapping("/loginPage")
	public String showLoginPageAfterSignUp(Model model){
		
		return "loginPage";
	}
}
