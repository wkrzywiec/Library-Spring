package com.wkrzywiec.spring.library.service;

import java.util.List;

import com.wkrzywiec.spring.library.dto.BookDTO;
import com.wkrzywiec.spring.library.retrofit.model.ItemAPIModel;

public interface GoogleBookService {

	List<BookDTO> searchBookList(String searchText);
	
	BookDTO getSingleBook(String googleId);
}
