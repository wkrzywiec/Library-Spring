package com.wkrzywiec.spring.library.service;

import com.wkrzywiec.spring.library.dto.BookDTO;
import com.wkrzywiec.spring.library.entity.Book;

public interface BookService {

	boolean isBookInLibrary(String googleId);
	
	Book saveBook(BookDTO bookDTO, String changedByUsername);
	
}
