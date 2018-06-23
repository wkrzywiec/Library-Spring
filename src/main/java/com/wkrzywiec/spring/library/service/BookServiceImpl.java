package com.wkrzywiec.spring.library.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wkrzywiec.spring.library.dao.BookDAO;
import com.wkrzywiec.spring.library.dto.BookDTO;
import com.wkrzywiec.spring.library.entity.Author;
import com.wkrzywiec.spring.library.entity.Book;
import com.wkrzywiec.spring.library.entity.BookCategory;
import com.wkrzywiec.spring.library.entity.Isbn;

@Service("bookService")
public class BookServiceImpl implements BookService {
	
	@Autowired
	BookDAO bookDAO;
	
	@Override
	@Transactional
	public boolean isBookInLibrary(String googleId) {
		
		boolean bookInLibrary = true;
		
		if (bookDAO.getBookByGoogleId(googleId) == null) {
			bookInLibrary = false;
		};
		
		return bookInLibrary;
	}

	@Override
	@Transactional
	public Book saveBook(BookDTO bookDTO, String changedByUsername) {
		
		Book book = null;
		book = this.convertBookDTOToBookEntity(bookDTO);
		book = bookDAO.saveBook(book, changedByUsername);
		
		return book;
	}
	
	private Book convertBookDTOToBookEntity(BookDTO bookDTO) {
		
		Book book = new Book();
		Set<Author> authors = null;
		Set<BookCategory> categories = null;
		Isbn isbn = new Isbn();
		
		authors = this.getAuthorsByName(bookDTO.getAuthors());
		categories = this.getBookCategoriesByName(bookDTO.getBookCategories());
		isbn.setIsbn10(bookDTO.getIsbn_10());
		isbn.setIsbn13(bookDTO.getIsbn_13());
		
		book.setGoogleId(bookDTO.getGoogleId());
		book.setTitle(bookDTO.getTitle());
		book.setAuthors(authors);
		book.setPublisher(bookDTO.getPublisher());
		book.setPublishedDate(bookDTO.getPublishedDate());
		book.setIsbn(isbn);
		book.setPageCount(bookDTO.getPageCount());
		book.setCategories(categories);
		book.setRating(bookDTO.getRating());
		book.setImageLink(bookDTO.getImageLink());
		book.setDescription(bookDTO.getDescription());
		
		return book;		
	}

	private Set<Author> getAuthorsByName(List<String> authorsListString){
		
		Set<Author> authors = new HashSet<Author>();
		
		for (String authorName : authorsListString) {
			Author author = null;
			author = bookDAO.getAuthorByName(authorName);
			
			if(author == null) {
				author = new Author();
				author.setName(authorName);
			} 
			authors.add(author);
		}
		return authors;
	}
	
	private Set<BookCategory> getBookCategoriesByName(List<String> bookCategoriesListString){
		
		Set<BookCategory> categories = new HashSet<BookCategory>();
		
		for(String categoryName : bookCategoriesListString) {
			BookCategory category = null;
			category = bookDAO.getBookCategoryByName(categoryName);
			
			if(category == null) {
				category = new BookCategory();
				category.setName(categoryName);
			}
			categories.add(category);
		}
		
		return categories;
		
	}
}
