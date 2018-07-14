package com.wkrzywiec.spring.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wkrzywiec.spring.library.dao.BookDAO;
import com.wkrzywiec.spring.library.dao.UserDAO;

@Service("libraryService")
public class LibraryServiceImpl implements LibraryService {

	public final int MAX_BOOKS_COUNT_PER_USER = 3;
	@Autowired
	private BookDAO bookDAO;
	
	@Autowired
	private UserDAO userDAO;
	
	@Override
	@Transactional
	public boolean isUserExceedBooksLimit(int id) {
		
		boolean exceedLimit = false;
		int bookCount = 0;
		
		bookCount += this.reservedBookTotalCountByUser(id);
		bookCount += this.borrowedBookTotalCountByUser(id);
		
		if (bookCount >= MAX_BOOKS_COUNT_PER_USER) {
			exceedLimit = true;
		}
		
		return exceedLimit;
	}
	
	private int reservedBookTotalCountByUser(int userId) {
		return bookDAO.getReservedBooksTotalCountByUser(userId);
	}
	
	private int borrowedBookTotalCountByUser(int userId) {
		return bookDAO.getBorrowedBooksTotalCountByUser(userId);
	}

}
