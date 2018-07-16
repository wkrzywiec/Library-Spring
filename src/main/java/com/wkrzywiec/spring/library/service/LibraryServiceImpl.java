package com.wkrzywiec.spring.library.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wkrzywiec.spring.library.dao.BookDAO;
import com.wkrzywiec.spring.library.dao.UserDAO;
import com.wkrzywiec.spring.library.dto.ManageDTO;
import com.wkrzywiec.spring.library.entity.Borrowed;
import com.wkrzywiec.spring.library.entity.Reserved;

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
		//TODO implementacja DAO
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

}
