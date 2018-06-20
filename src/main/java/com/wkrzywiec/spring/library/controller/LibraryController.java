package com.wkrzywiec.spring.library.controller;


import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wkrzywiec.spring.library.dto.BookDTO;
import com.wkrzywiec.spring.library.dto.UserDTO;
import com.wkrzywiec.spring.library.entity.User;
import com.wkrzywiec.spring.library.entity.UserLog;
import com.wkrzywiec.spring.library.retrofit.model.RandomQuoteResponse;
import com.wkrzywiec.spring.library.service.BookServiceImpl;
import com.wkrzywiec.spring.library.service.GoogleBookServiceImpl;
import com.wkrzywiec.spring.library.service.LibraryUserDetailService;
import com.wkrzywiec.spring.library.service.RandomQuoteService;

@Controller
public class LibraryController {
	
	public static final int USERS_PER_PAGE = 20;
	
	@Autowired
	LibraryUserDetailService userService;
	
	@Autowired
	BookServiceImpl bookService;
	
	@Autowired
	RandomQuoteService quoteService;
	
	@Autowired
	GoogleBookServiceImpl googleBookService;

	@GetMapping("/")
	public String showHomePage(Model model){
		
		RandomQuoteResponse quote = quoteService.getRandomResponse();
		
		model.addAttribute("quote", quote);
		return "home";
	}
	
	@GetMapping("/admin-panel")
	public String showAdminPanel(	@RequestParam(value="search", required=false) String searchText,
									@RequestParam(value="pageNo", required=false) Integer pageNo,
									ModelMap model){
		
		if (searchText == null && pageNo == null) {
			return "admin-panel";
		}
			
		if (searchText != null && pageNo == null){
			pageNo = 1;
			model.put("pageNo", 1);
		}
		
		model.addAttribute("resultsCount", userService.searchUsersResultsCount(searchText));
			
		model.addAttribute("pageCount", userService.searchUserPagesCount(searchText, USERS_PER_PAGE));
			
		model.addAttribute("userList", userService.searchUsers(searchText, pageNo, USERS_PER_PAGE));
		return "admin-panel";
	}
	
	@GetMapping("/admin-panel/new-user")
	public String registerSpecialUser (Model model){
		
		UserDTO userDTO = new UserDTO();
		model.addAttribute("user", userDTO);
		
		return "register-user-special";
	}

	@PostMapping("/admin-panel/new-user")
	public String processRegisterSpecialUserForm(
				@Valid @ModelAttribute("user") UserDTO userDTO,
				BindingResult bindingResult,
				Model model){
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		
		if (bindingResult.hasErrors()){
			return "register-user-special";
		} else {
			userService.saveSpecialUser(userDTO, currentPrincipalName);
			model.addAttribute("newUserRegister", true);
			return "admin-panel";
		}
	}
	
	@GetMapping("/admin-panel/user/{id}")
	public String showDetailedUserInfoOnAdmin(	@PathVariable("id") Integer id,
												@RequestParam(value="add", required=false)  Integer addId,
												Model model) {
		
		User user = userService.getUserById(id);
		List<UserLog> userLogs = null;
		if (addId != null) {
			if (addId == 1) {
				userLogs = userService.getUserLogs(user.getId());
			}
			model.addAttribute("logs", userLogs);
		}
		
		model.addAttribute("user", user);
		
		UserDTO userDTO = new UserDTO();
		model.addAttribute("userDTO", userDTO);
		
		return "edit-user-admin";
	}
	
	@PostMapping("/admin-panel/user/{id}/update")
	public String updateUserByAdmin(@PathVariable("id") Integer id,
									@ModelAttribute("user") UserDTO userDTO,
									Model model) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		
		if (userDTO.isEnable()) {
			userService.enableUser(id, currentPrincipalName);
		} else {
			userService.disableUser(id, currentPrincipalName);
		}
			
		User updatedUser = userService.updateUser(id, userDTO, currentPrincipalName);
			
		model.addAttribute("user", updatedUser);
		return "edit-user-admin";
	}
	
	@GetMapping("/books/add-book")
	public String findNewBook(	@RequestParam(value="search", required=false) String searchText,
								Model model) {
		
		if (searchText == null) {
			return "add-book";
		}

		model.addAttribute("bookList", googleBookService.searchBookList(searchText));
		return "add-book";
	}
	
	@GetMapping("/books/add-book/{googleId}")
	public String addNewBook(	@PathVariable("googleId") String googleId,
								@RequestParam(value="search", required=false) String searchText,
								Model model) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		
		boolean bookAlreadyInLibrary = true;
		
		if (searchText == null) {
			return "add-book";
		}
		
		BookDTO bookDTO = googleBookService.getSingleBook(googleId);
		bookService.saveBook(bookDTO, currentPrincipalName);
		
		model.addAttribute("bookList", googleBookService.searchBookList(searchText));
		return "add-book";
	}

}
