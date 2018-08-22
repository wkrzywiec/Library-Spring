package com.wkrzywiec.spring.library.controller;


import java.math.BigDecimal;
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
import com.wkrzywiec.spring.library.dto.LibraryLogDTO;
import com.wkrzywiec.spring.library.dto.ManageDTO;
import com.wkrzywiec.spring.library.dto.PenaltyDTO;
import com.wkrzywiec.spring.library.dto.UserDTO;
import com.wkrzywiec.spring.library.dto.UserLogDTO;
import com.wkrzywiec.spring.library.entity.User;
import com.wkrzywiec.spring.library.retrofit.model.RandomQuoteResponse;
import com.wkrzywiec.spring.library.service.BookServiceImpl;
import com.wkrzywiec.spring.library.service.GoogleBookServiceImpl;
import com.wkrzywiec.spring.library.service.LibraryService;
import com.wkrzywiec.spring.library.service.RandomQuoteService;
import com.wkrzywiec.spring.library.service.UserService;

@Controller
public class LibraryController {
	
	public final int USERS_PER_PAGE = 20;
	public final int BOOKS_PER_PAGE = 20;
	public final int MANAGE_PER_PAGE = 30;
	
	@Autowired
	UserService userService;
	
	@Autowired
	BookServiceImpl bookService;
	
	@Autowired
	LibraryService libraryService;
	
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
	
	@GetMapping("/profile")
	public String showProfilePage(Model model) {
		
		int userId = 0;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		userId = userService.getUserByUsername(currentPrincipalName).getId();
		
		return this.showDetailedUserInfoOnAdmin(userId, null, model);
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
												@RequestParam(value="addit", required=false)  Integer additional,
												Model model) {
		
		model = this.addModelsToUserDetailedPage(model, id, additional);
		
		return "user-details";
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
			
		userService.updateUser(id, userDTO, currentPrincipalName);
		
		model = this.addModelsToUserDetailedPage(model, id, null);
		return "user-details";
	}
	
	@GetMapping("/admin-panel/user/{id}/payment")
	public String makePaymentForPenalties(	@PathVariable("id") Integer id,
											Model model) {
		
		boolean paid = false;
		paid = libraryService.makePayment(id);
		
		String message = !paid ? "Not all books are returned to the library. Return all the books so we could proceed with payments." : null;
		model.addAttribute("message", message);
		model = this.addModelsToUserDetailedPage(model, id, null);
		return "user-details";
	}
	
	@GetMapping("/books/search")
	public String showSearchBook(	@RequestParam(value="search", required=false) String searchText,
									@RequestParam(value="pageNo", required=false) Integer pageNo,
									ModelMap model) {
		
		int bookResultsCount = 0;
		int bookPageCount = 0;
		List<BookDTO> bookList = null;
		
		
		if (searchText == null && pageNo == null) {
			return "book-search";
		}
			
		if (searchText != null && pageNo == null){
			pageNo = 1;
			model.put("pageNo", 1);
		}
		
		bookResultsCount = bookService.searchBooksResultsCount(searchText);
		model.addAttribute("resultsCount", bookResultsCount);
		
		bookPageCount = bookService.searchBookPagesCount(searchText, BOOKS_PER_PAGE);
		model.addAttribute("pageCount", bookPageCount);
		
		bookList =  bookService.searchBookList(searchText, pageNo, BOOKS_PER_PAGE);
		model.addAttribute("bookList", bookList);

		return "book-search";
	}
	
	@GetMapping("/books/{id}")
	public String showBookDetails(	@PathVariable("id") Integer bookId,
									@RequestParam(value="action", required=false) String action,
									@RequestParam(value="addit", required=false)  Integer additional,
									@ModelAttribute("user") UserDTO userDTO,
									Model model) {
		
		BookDTO book = null;
		List<LibraryLogDTO> libraryLogs = null;
		int userId = 0;
		if (action != null) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String currentPrincipalName = authentication.getName();
			userId = userService.getUserByUsername(currentPrincipalName).getId();
			
			if (libraryService.isUserExceedBooksLimit(userId)) {
				model.addAttribute("message", "You have exceeded allowed books total count. Please return one of books to be able to make a reservation on this one.");
				book = bookService.getBookDTOById(bookId);
			} else {
				book = libraryService.reserveBook(bookId, userId);
			}
			
		} else {
			book = bookService.getBookDTOById(bookId);
		}
		
		if (additional != null) {
			if (additional == 1) {
				libraryLogs = libraryService.getLibraryLogsByBook(bookId);
				model.addAttribute("logs", libraryLogs);
			} 	
		}
		
		model.addAttribute("book", book);
		
		return "book-details";
	}
	
	@GetMapping("/books/manager")
	public String bookManager(	@RequestParam(value="search", required=false) String searchText,
								@RequestParam(value="pageNo", required=false) Integer pageNo,
								@RequestParam(value="type", required=false) Integer typeNo,
								@RequestParam(value="status", required=false) Integer statusNo,
								@RequestParam(value="sort", required=false) String sortBy,
								@RequestParam(value="action", required=false) Integer action,
								@RequestParam(value="id", required=false) Integer bookId,
								ModelMap model) {
		
		int resultsCount = 0;
		int pageCount = 0;
		List<ManageDTO> manageList = null;
		
		if (searchText == null && pageNo == null) {
			return "book-manager";
		}
		
		if (searchText != null && pageNo == null){
			pageNo = 1;
			model.put("pageNo", (int) pageNo);
			
			if (typeNo == null) {
				typeNo = 2;
				model.put("optionNo", (int) typeNo);
			}
			
			if (statusNo == null) {
				statusNo = 3;
				model.put("statusNo", (int) statusNo);
			}
		}
		
		if (sortBy == null) {
			sortBy = "rel";
			model.put("sort", sortBy);
		}
		
		if (action != null) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String currentPrincipalName = authentication.getName();
			
			int userId = userService.getUserByUsername(currentPrincipalName).getId();
			
			if (action == 1) {
				libraryService.borrowBook(bookId, userId);
			} else if (action == 2) {
				libraryService.returnBook(bookId, userId);
			}
		}
		
		resultsCount = libraryService.searchManageResultsCount(searchText, typeNo, statusNo);
		model.addAttribute("resultsCount", resultsCount);
		
		pageCount = libraryService.calculateManagePagesCount(resultsCount, MANAGE_PER_PAGE);
		model.addAttribute("pageCount", pageCount);
		
		manageList = libraryService.searchManageList(searchText, typeNo, statusNo, pageNo, MANAGE_PER_PAGE);
		
		manageList = libraryService.sortManageList(manageList, sortBy);
		
		model.addAttribute("manageList", manageList);
		
		return "book-manager";
	}
	
	@GetMapping("/books/manager/showAll")
	public String bookManager(	@RequestParam(value="pageNo", required=false) Integer pageNo,
								@RequestParam(value="action", required=false) Integer action,
								@RequestParam(value="id", required=false) Integer bookId,
								ModelMap model) {
		
		if (pageNo == null) {
			pageNo = 1;
			model.put("pageNo", pageNo);
		}
		
		if (action != null) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String currentPrincipalName = authentication.getName();
			
			int userId = userService.getUserByUsername(currentPrincipalName).getId();
			
			if (action == 1) {
				libraryService.borrowBook(bookId, userId);
			} else if (action == 2) {
				libraryService.returnBook(bookId, userId);
			}
			
		}
		
		int resultsCount = 0;
		int pageCount = 0;
		List<ManageDTO> manageList = null;
		
		resultsCount = libraryService.allManageResultsCount();
		model.addAttribute("resultsCount", resultsCount);
		
		pageCount = libraryService.calculateManagePagesCount(resultsCount, MANAGE_PER_PAGE);
		model.addAttribute("pageCount", pageCount);
		
		manageList = libraryService.allManageList(pageNo, MANAGE_PER_PAGE);
		model.addAttribute("manageList", manageList);
		
		return "book-manager";
	};
	
	
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
		
		if (searchText == null) {
			return "add-book";
		}
		
		boolean bookAlreadyInLibrary = true;
		
		bookAlreadyInLibrary = bookService.isBookInLibrary(googleId);
		BookDTO bookDTO = null;
		if (!bookAlreadyInLibrary) {
			bookDTO = googleBookService.getSingleBook(googleId);
			bookService.saveBook(bookDTO, currentPrincipalName);
		}
		
		if (bookAlreadyInLibrary) {
			model.addAttribute("message", "Book with this Google Id: " + googleId + " is already in the library.");
		} else {
			model.addAttribute("message", "Book: '" + bookDTO.getTitle() + "' with GoogleID " + googleId + " was added to library.");
		}
		
		model.addAttribute("bookList", googleBookService.searchBookList(searchText));
		return "add-book";
	}
	
	private Model addModelsToUserDetailedPage(Model model, Integer id, Integer additional) {
		
		List<UserLogDTO> userLogs = null;
		List<LibraryLogDTO> libraryLogs = null;
		List<ManageDTO> manageDTO = null;
		BigDecimal penaltiesTotal = null;
		boolean allBooksReturned = false;
		
		User user = userService.getUserById(id);
		model.addAttribute("user", user);
		
		if (additional != null) {
			if (additional == 1) {
				userLogs = userService.getUserLogs(user.getId());
				model.addAttribute("logs", userLogs);
			} else if (additional == 2) {
				libraryLogs = libraryService.getLibraryLogsByUser(user.getId());
				model.addAttribute("logs", libraryLogs);
			}
		}
		
		UserDTO userDTO = new UserDTO();
		model.addAttribute("userDTO", userDTO);
		
		manageDTO = libraryService.getManageListByUser(id);
		model.addAttribute("manageList", manageDTO);
		
		List<PenaltyDTO> penalties = null;
		penalties = libraryService.getPenaltiesByUser(id);
		model.addAttribute("penalties", penalties);
		
		penaltiesTotal = libraryService.sumPenalties(penalties);
		model.addAttribute("penaltiesTotal", penaltiesTotal);
		
		allBooksReturned = this.areAllBooksReturned(penalties);
		model.addAttribute("allBooksReturned", allBooksReturned);
		
		return model;
	}

	private boolean areAllBooksReturned(List<PenaltyDTO> penalties) {
		
		boolean allBooksReturned = true;
		
		for(PenaltyDTO penalty : penalties) {
			if (penalty.getReturnDate() == null) {
				allBooksReturned = false;
				break;
			}
		}
		return allBooksReturned;
	}

}
