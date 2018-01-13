package com.wkrzywiec.spring.library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

	
	@GetMapping("/loginPage")
	public String showLoginPage(){
		return "loginPage";
	}
	
	@GetMapping("/access-denied")
	public String accessDenied(){
		return "access-denied";
	}
	
}
