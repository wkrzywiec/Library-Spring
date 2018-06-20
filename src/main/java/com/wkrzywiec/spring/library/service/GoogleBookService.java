package com.wkrzywiec.spring.library.service;

import java.util.List;

import com.wkrzywiec.spring.library.dto.BookDTO;
import com.wkrzywiec.spring.library.entity.Book;

public interface GoogleBookService {

	List<BookDTO> searchBookList(String searchText);
	
	BookDTO getSingleBook(String googleId);
}
