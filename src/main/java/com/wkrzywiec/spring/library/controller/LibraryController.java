package com.wkrzywiec.spring.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.wkrzywiec.spring.library.dao.UserDAO;

@Controller
public class LibraryController {
	
	@Autowired
	private UserDAO userDAO;

	@GetMapping("/")
	public String showHomePage(){
		System.out.println(userDAO.getCustomer(1).toString());
		return "home";
	}
	
}
