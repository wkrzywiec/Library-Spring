package com.wkrzywiec.spring.library.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wkrzywiec.spring.library.dao.BookDAO;
import com.wkrzywiec.spring.library.dao.UserDAO;
import com.wkrzywiec.spring.library.dto.BookDTO;
import com.wkrzywiec.spring.library.dto.ManageDTO;
import com.wkrzywiec.spring.library.entity.Author;
import com.wkrzywiec.spring.library.entity.Book;
import com.wkrzywiec.spring.library.entity.BookCategory;
import com.wkrzywiec.spring.library.entity.Borrowed;
import com.wkrzywiec.spring.library.entity.Reserved;

@Service("libraryService")
public class LibraryServiceImpl implements LibraryService {

	public final int MAX_BOOKS_COUNT_PER_USER = 3;
	public final int DAYS_AFTER_RESERVATION = 3;
	public final int DAYS_AFTER_BORROWED = 30;
	
	@Autowired
	private BookDAO bookDAO;
	
	@Autowired
	private UserDAO userDAO;
	
	@Override
	@Transactional
	public boolean isUserExceedBooksLimit(int id) {
		
		boolean exceedLimit = false;
		int bookCount = 0;
		
		bookCount += this.reservedBooksTotalCountByUser(id);
		bookCount += this.borrowedBooksTotalCountByUser(id);
		
		if (bookCount >= MAX_BOOKS_COUNT_PER_USER) {
			exceedLimit = true;
		}
		
		return exceedLimit;
	}
	
	@Override
	@Transactional
	public int allManageResultsCount() {

		int resultsCount = 0;
		resultsCount += this.reservedBooksTotalCount();
		resultsCount += this.borrowedBooksTotalCount();
		
		return resultsCount;
	}

	@Override
	@Transactional
	public int searchManageResultsCount(String searchText, int typeNo, int statusNo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int calculateManagePagesCount(int resultsCount, int resultsPerPage) {
		
		int pagesCount = 1;
		pagesCount = (int) Math.floorDiv(resultsCount, resultsPerPage);
		if (resultsCount == 0) pagesCount = 1;
		if (resultsCount % resultsPerPage != 0) pagesCount += 1;
		return pagesCount;
	}

	@Override
	@Transactional
	public List<ManageDTO> allManageList(int pageNo, int resultsPerPage) {
		
		List<ManageDTO> manageList = new ArrayList<ManageDTO>();
		List <Reserved> reservedList = null;
		List <Borrowed> borrowedList = null;
		
		reservedList = bookDAO.getAllReservedBooks();
		borrowedList = bookDAO.getAllBorrowedBooks();
		
		ManageDTO manageDTO = null;
		for (Reserved reserved : reservedList) {
			
			manageDTO = this.convertReservedToManageDTO(reserved);
			manageList.add(manageDTO);
		}
		
		for (Borrowed borrowed : borrowedList) {
			manageDTO = this.convertBorrowedToManageDTO(borrowed);
			manageList.add(manageDTO);
		}
		
		return manageList;
	}

	@Override
	@Transactional
	public List<ManageDTO> searchManageList(String searchText, int typeNo, int statusNo, int pageNo,
			int resultsPerPage) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	@Transactional
	public BookDTO reserveBook(int id, String username) {
		
		BookDTO bookDTO = null;
		Book bookEntity = null;
		
		int userId = 0;
		userId = userDAO.getActiveUser(username).getId();
		
		bookEntity = bookDAO.reserveBook(id, userId, DAYS_AFTER_RESERVATION);
		bookDTO = this.convertBookEntityToBookDTO(bookEntity);
		
		return bookDTO;
	}
	
	@Override
	@Transactional
	public void borrowBook(int bookId, int userId) {
		bookDAO.borrowBook(bookId, userId, DAYS_AFTER_BORROWED);
	}

	@Override
	@Transactional
	public void returnBook(int bookId, String username) {
		bookDAO.returnBook(bookId);
	}

	private int reservedBooksTotalCountByUser(int userId) {
		return bookDAO.getReservedBooksTotalCountByUser(userId);
	}
	
	private int borrowedBooksTotalCountByUser(int userId) {
		return bookDAO.getBorrowedBooksTotalCountByUser(userId);
	}
	
	private int reservedBooksTotalCount() {
		return bookDAO.getReservedBooksTotalCount();
	}
	
	private int borrowedBooksTotalCount() {
		return bookDAO.getBorrowedBooksTotalCount();
	}
	
	private ManageDTO convertReservedToManageDTO(Reserved reserved) {
		
		ManageDTO manageDTO = new ManageDTO();
		
		manageDTO.setUserFirstName(reserved.getUser().getFirstName());
		manageDTO.setUserLastName(reserved.getUser().getLastName());
		manageDTO.setUserId(reserved.getUser().getId());
		manageDTO.setBookTitle(reserved.getBook().getTitle());
		manageDTO.setBookId(reserved.getBook().getId());
		manageDTO.setBookStatus("RESERVED");
		manageDTO.setDueDate(reserved.getDeadlineDate());
		
		return manageDTO;
	}
	
	private ManageDTO convertBorrowedToManageDTO(Borrowed borrowed) {
		
		ManageDTO manageDTO = new ManageDTO();
		
		manageDTO.setUserFirstName(borrowed.getUser().getFirstName());
		manageDTO.setUserLastName(borrowed.getUser().getLastName());
		manageDTO.setUserId(borrowed.getUser().getId());
		manageDTO.setBookTitle(borrowed.getBook().getTitle());
		manageDTO.setBookId(borrowed.getBook().getId());
		manageDTO.setBookStatus("BORROWED");
		manageDTO.setDueDate(borrowed.getDeadlineDate());
		
		return manageDTO;
	}
	
	private BookDTO convertBookEntityToBookDTO(Book bookEntity) {
		
		
		BookDTO bookDTO = new BookDTO();
		List<String> authors = new ArrayList<String>();
		List<String> categories = new ArrayList<String>();
		
		bookDTO.setId(bookEntity.getId());
		bookDTO.setGoogleId(bookEntity.getGoogleId());
		bookDTO.setTitle(bookEntity.getTitle());
		
		for (Author author : bookEntity.getAuthors()) {
			authors.add(author.getName());
		}
		bookDTO.setAuthors(authors);
		
		bookDTO.setPublisher(bookEntity.getPublisher());
		bookDTO.setPublishedDate(bookEntity.getPublishedDate());
		bookDTO.setIsbn_10(bookEntity.getIsbn().getIsbn10());
		bookDTO.setIsbn_13(bookEntity.getIsbn().getIsbn13());
		bookDTO.setPageCount(bookEntity.getPageCount());
		
		for (BookCategory category: bookEntity.getCategories()) {
			categories.add(category.getName());
		}
		bookDTO.setBookCategories(categories);
		
		bookDTO.setRating(bookEntity.getRating());
		bookDTO.setImageLink(bookEntity.getImageLink());
		bookDTO.setDescription(bookEntity.getDescription());

		bookDTO.setStatus(this.checkBookStatus(bookEntity.getId()));
		return bookDTO;
	}

	@Transactional
	private String checkBookStatus(int id) {
	
		String status = null;
		
		if (bookDAO.isBookReserved(id)) {
			status = "RESERVED";
		} else if (bookDAO.isBookBorrowed(id)) {
			status = "BORROWED";
		} else {
			status = "AVAILABLE";
		}
		return status;
	}

}
