package com.wkrzywiec.spring.library.service;

import java.util.List;

import com.wkrzywiec.spring.library.dto.BookDTO;
import com.wkrzywiec.spring.library.dto.ManageDTO;

public interface LibraryService {

	boolean isUserExceedBooksLimit(int id);
	
	int allManageResultsCount();
	
	int searchManageResultsCount(String searchText, int typeNo, int statusNo);
	
	int calculateManagePagesCount(int resultsCount, int resultsPerPage);
	
	List<ManageDTO> allManageList(int pageNo, int resultsPerPage);

	List<ManageDTO> searchManageList(String searchText, int typeNo, int statusNo, int pageNo, int resultsPerPage);
	
	BookDTO reserveBook(int id, String username);
	
	void borrowBook(int bookId, int userId);
	
	void returnBook(int bookId, String username);
}
