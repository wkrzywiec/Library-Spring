package com.wkrzywiec.spring.library.dao;

import com.wkrzywiec.spring.library.entity.Author;
import com.wkrzywiec.spring.library.entity.Book;
import com.wkrzywiec.spring.library.entity.BookCategory;

public interface BookDAO {

	Author getAuthorByName(String authorName);
	
	BookCategory getBookCategoryByName(String categoryName);
	
	Book saveBook(Book book, String changedByUsername);

}
