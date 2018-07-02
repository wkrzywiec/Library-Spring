package com.wkrzywiec.spring.library.service;

import java.util.List;

import com.wkrzywiec.spring.library.dto.BookDTO;
import com.wkrzywiec.spring.library.entity.Book;

public interface BookService {

	boolean isBookInLibrary(String googleId);
	
	List<BookDTO> searchBookList(String searchText, int pageNo, int resultsPerPage);
	
	int searchBooksResultsCount(String searchText);
	
	int searchBookPagesCount(String searchText, int resultsPerPage);
	
	Book saveBook(BookDTO bookDTO, String changedByUsername);
	
}
