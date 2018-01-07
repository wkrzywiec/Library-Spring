package com.wkrzywiec.spring.library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LibraryController {

	@GetMapping("/")
	public String showHomePage(){
		return "home";
	}
	
}
