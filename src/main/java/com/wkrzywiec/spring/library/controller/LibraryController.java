package com.wkrzywiec.spring.library.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.wkrzywiec.spring.library.entity.User;
import com.wkrzywiec.spring.library.service.LibraryUserDetailService;

@Controller
public class LibraryController {
	
	@Autowired
	LibraryUserDetailService userService;

	@GetMapping("/")
	public String showHomePage(){
		
		return "home";
	}
	
	@GetMapping("/admin-panel")
	public String showAdminPanel(Model model){
		
		List<User> userList = userService.getAllUsers();
		
		model.addAttribute("userList", userList);
		return "admin-panel";
	}
}
