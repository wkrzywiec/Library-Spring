package com.wkrzywiec.spring.library.service;

import java.util.List;

import com.wkrzywiec.spring.library.dto.BookDTO;
import com.wkrzywiec.spring.library.dto.ManageDTO;
import com.wkrzywiec.spring.library.entity.LibraryLog;

public interface LibraryService {

	boolean isUserExceedBooksLimit(int id);
	
	int allManageResultsCount();
	
	int searchManageResultsCount(String searchText, int typeNo, int statusNo);
	
	int calculateManagePagesCount(int resultsCount, int resultsPerPage);
	
	List<ManageDTO> allManageList(int pageNo, int resultsPerPage);

	List<ManageDTO> searchManageList(String searchText, int typeNo, int statusNo, int pageNo, int resultsPerPage);
	
	BookDTO reserveBook(int id, int userId);
	
	void borrowBook(int bookId, int librarianId);
	
	void returnBook(int bookId, int librarianId);
	
	List<ManageDTO> sortManageList(List<ManageDTO> manageDTO, String sortBy);
	
	List<LibraryLog> getLibraryLogsByUser(int userId);
	
	List<LibraryLog> getLibraryLogsByBook(int bookId);
}
