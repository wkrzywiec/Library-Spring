package com.wkrzywiec.spring.library.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wkrzywiec.spring.library.dao.BookDAO;
import com.wkrzywiec.spring.library.dao.UserDAO;
import com.wkrzywiec.spring.library.dto.BookDTO;
import com.wkrzywiec.spring.library.dto.LibraryLogDTO;
import com.wkrzywiec.spring.library.dto.ManageDTO;
import com.wkrzywiec.spring.library.dto.PenaltyDTO;
import com.wkrzywiec.spring.library.entity.Author;
import com.wkrzywiec.spring.library.entity.Book;
import com.wkrzywiec.spring.library.entity.BookCategory;
import com.wkrzywiec.spring.library.entity.Borrowed;
import com.wkrzywiec.spring.library.entity.LibraryLog;
import com.wkrzywiec.spring.library.entity.BookPenalty;
import com.wkrzywiec.spring.library.entity.Reserved;
import com.wkrzywiec.spring.library.entity.User;

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

		int resultsCount = 0;
		List<Integer> idList = null;
		List<User> userList = null;
		List<Book> bookList = null;
		
		if (typeNo == 1) {
			
			userList = userDAO.searchUsers(searchText, 1, 5);
			idList = this.getIDFromEachUserInList(userList);
			resultsCount = this.getMangeResultTotalCountForUsersIdList(idList, statusNo);
			
		} else if (typeNo == 2) {
			
			bookList = bookDAO.searchBookList(searchText, 1, 100);
			idList = this.getIDFromEachBookInList(bookList);
			resultsCount = this.getMangeResultTotalCountForBookIdList(idList, statusNo);
		
		}
		return resultsCount;
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
	public List<ManageDTO> getManageListByUser(int userId) {
		
		List<ManageDTO> manageList = new ArrayList<ManageDTO>();
		List <Reserved> reservedList = null;
		List <Borrowed> borrowedList = null;
		
		reservedList = bookDAO.getReservedBooksListByUserId(userId);
		borrowedList = bookDAO.getBorrowedBooksListByUserId(userId);
		
		manageList = this.combineReservedAndBorrowedBooksList(reservedList, borrowedList);
		
		return manageList;
	}

	@Override
	@Transactional
	public List<ManageDTO> allManageList(int pageNo, int resultsPerPage) {
		
		List<ManageDTO> manageList = new ArrayList<ManageDTO>();
		List <Reserved> reservedList = null;
		List <Borrowed> borrowedList = null;
		
		reservedList = bookDAO.getAllReservedBooksList();
		borrowedList = bookDAO.getAllBorrowedBooksList();
		
		manageList = this.combineReservedAndBorrowedBooksList(reservedList, borrowedList);
		
		return manageList;
	}

	@Override
	@Transactional
	public List<ManageDTO> searchManageList(String searchText, int typeNo, int statusNo, int pageNo,
			int resultsPerPage) {
		
		List<ManageDTO> manageList = new ArrayList<ManageDTO>();
		List<Integer> idList = null;
		List<User> userList = null;
		List<Book> bookList = null;
		
		if (typeNo == 1) {
			
			userList = userDAO.searchUsers(searchText, 1, 7);
			idList = this.getIDFromEachUserInList(userList);
			manageList = this.getManageDTOListByUserIdList(idList, statusNo);
			
		} else if (typeNo == 2) {
			
			bookList = bookDAO.searchBookList(searchText, 1, resultsPerPage);
			idList = this.getIDFromEachBookInList(bookList);
			manageList = this.getManageDTOListByBookIdList(idList, statusNo, pageNo, resultsPerPage);
		}
		return manageList;
	}
	
	@Override
	@Transactional
	public BookDTO reserveBook(int id, int userId) {
		
		BookDTO bookDTO = null;
		Book bookEntity = null;
		
		bookEntity = bookDAO.reserveBook(id, userId, DAYS_AFTER_RESERVATION);
		bookDTO = this.convertBookEntityToBookDTO(bookEntity);
		
		return bookDTO;
	}
	
	@Override
	@Transactional
	public void borrowBook(int bookId, int librarianId) {
		Reserved reserved = bookDAO.getReservedBooksListByBookId(bookId).get(0);
		int userId = reserved.getUser().getId();
		bookDAO.borrowBook(bookId, userId, DAYS_AFTER_BORROWED);
	}

	@Override
	@Transactional
	public void returnBook(int bookId, int librarianId) {
		
		Borrowed borrowed = bookDAO.getBorrowedBookByBookId(bookId);
		Calendar calendar = Calendar.getInstance();
		java.util.Date currentDate = calendar.getTime();
		
		if (currentDate.after(borrowed.getDueDate())) {
			
			bookDAO.setReturnDateForPenalty(bookId);
		}
		bookDAO.returnBook(bookId);
	}
	
	@Override
	public List<ManageDTO> sortManageList(List<ManageDTO> manageDTO, String sortBy) {
		
		Comparator<ManageDTO> manageComparator = null;
		
		if (sortBy.equals("bookAsc")) {
			manageComparator = Comparator.comparing(ManageDTO::getBookTitle);
		} else if (sortBy.equals("bookDes")) {
			manageComparator = Comparator.comparing(ManageDTO::getBookTitle).reversed();
		} else if (sortBy.equals("userAsc")) {
			manageComparator = Comparator.comparing(ManageDTO::getUserLastName);
		} else if (sortBy.equals("userDes")) {
			manageComparator = Comparator.comparing(ManageDTO::getUserLastName).reversed();
		} else if (sortBy.equals("statusAsc")) {
			manageComparator = Comparator.comparing(ManageDTO::getBookStatus);
		} else if (sortBy.equals("statusDes")) {
			manageComparator = Comparator.comparing(ManageDTO::getBookStatus).reversed();
		} else {
			return manageDTO;
		}
		
		Collections.sort(manageDTO, manageComparator);
		
		return manageDTO;
	}
	
	@Override
	@Transactional
	public List<LibraryLogDTO> getLibraryLogsByUser(int userId) {
		
		List<LibraryLog> libraryLogs = null;
		List<LibraryLogDTO> libraryLogsDTO = null;
		libraryLogs = bookDAO.getLibraryLogsByUser(userId);
		libraryLogsDTO = this.convertLibraryLogsEntityListToLibraryLogsDTOList(libraryLogs);
	
		return libraryLogsDTO;
	}

	@Override
	@Transactional
	public List<LibraryLogDTO> getLibraryLogsByBook(int bookId) {
		
		List<LibraryLog> libraryLogs = null;
		List<LibraryLogDTO> libraryLogsDTO = null;
		libraryLogs = bookDAO.getLibraryLogsByBook(bookId);
		libraryLogsDTO = this.convertLibraryLogsEntityListToLibraryLogsDTOList(libraryLogs);
		return libraryLogsDTO;
	}

	@Override
	@Transactional
	public List<PenaltyDTO> getPenaltiesByUser(int userId) {
		
		List<BookPenalty> penalties = null;
		List<PenaltyDTO> penaltiesDTO = null;
		
		penalties = bookDAO.getBookPenaltiesByUser(userId);
		penaltiesDTO = this.converBookPenaltiesEntityListToDTOList(penalties);
		
		return penaltiesDTO;
	}
	
	@Override
	public BigDecimal calculatePenalty(int days) {
		
		BigDecimal penalty = new BigDecimal(0.00).setScale(2, BigDecimal.ROUND_HALF_EVEN);
		
		for (int i = 1; i<= days; i++) {
			penalty = penalty.add(this.calculatePenaltyInDay(i));
		}
		
		return penalty;
	}

	@Override
	public BigDecimal sumPenalties(List<PenaltyDTO> penalties) {
		
		BigDecimal penaltiesTotal = new BigDecimal(0.00).setScale(2, BigDecimal.ROUND_HALF_EVEN);
		
		for(PenaltyDTO penalty : penalties) {
			penaltiesTotal = penaltiesTotal.add(penalty.getPenalty());
		}
		
		return penaltiesTotal;
	}

	@Override
	@Transactional
	public boolean makePayment(int userId) {
		
		boolean areAllBooksReturned = true;
		List<PenaltyDTO> penalties = null;
		penalties = this.getPenaltiesByUser(userId);
		
		for (PenaltyDTO penalty : penalties) {
			if (penalty.getReturnDate() == null) {
				areAllBooksReturned = false;
				break;
			}
		}
		if (areAllBooksReturned) {
			userDAO.penaltiesPaidForUser(userId);
		}
		
		return areAllBooksReturned;
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
	
	private List<ManageDTO> combineReservedAndBorrowedBooksList(List<Reserved> reservedList, List<Borrowed> borrowedList){
		
		List<ManageDTO> manageList = new ArrayList<ManageDTO>();
		ManageDTO manageDTO = null;
		
		if (reservedList != null) {
			for (Reserved reserved : reservedList) {
				
				manageDTO = this.convertReservedToManageDTO(reserved);
				manageList.add(manageDTO);
			}
		}
		
		if (borrowedList != null) {
			for (Borrowed borrowed : borrowedList) {
				manageDTO = this.convertBorrowedToManageDTO(borrowed);
				manageList.add(manageDTO);
			}
		}
		return manageList;
	}
	
	private ManageDTO convertReservedToManageDTO(Reserved reserved) {
		
		ManageDTO manageDTO = new ManageDTO();
		
		manageDTO.setUserFirstName(reserved.getUser().getFirstName());
		manageDTO.setUserLastName(reserved.getUser().getLastName());
		manageDTO.setUserId(reserved.getUser().getId());
		manageDTO.setBookTitle(reserved.getBook().getTitle());
		manageDTO.setBookId(reserved.getBook().getId());
		manageDTO.setBookStatus("RESERVED");
		manageDTO.setDueDate(reserved.getDueDate());
		
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
		manageDTO.setDueDate(borrowed.getDueDate());
		
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
	
	private List<ManageDTO> getManageDTOListByUserIdList(List<Integer> idList, int statusNo){
		
		List<ManageDTO> manageList = null;
		List<Reserved> reservedList = new ArrayList<Reserved>();
		List<Reserved> reservedListTemp = null;
		List<Borrowed> borrowedList = new ArrayList<Borrowed>();
		List<Borrowed> borrowedListTemp = null;

		if (statusNo == 1) {
			for (Integer userId : idList) {
				reservedListTemp = bookDAO.getReservedBooksListByUserId(userId);
				reservedList.addAll(reservedListTemp);
			}
		} else if (statusNo == 2) {
			for (Integer userId : idList) {
				borrowedListTemp = bookDAO.getBorrowedBooksListByUserId(userId);
				borrowedList.addAll(borrowedListTemp);
			}
			
		} else {
			for (Integer userId : idList) {
				reservedListTemp = bookDAO.getReservedBooksListByUserId(userId);
				reservedList.addAll(reservedListTemp);
				borrowedListTemp = bookDAO.getBorrowedBooksListByUserId(userId);
				borrowedList.addAll(borrowedListTemp);
			}
		}
		manageList = this.combineReservedAndBorrowedBooksList(reservedList, borrowedList);
		
		return manageList;
	}
	
	private List<ManageDTO> getManageDTOListByBookIdList(List<Integer> idList, int statusNo, int pageNo, int resultsPerPage){
		
		List<ManageDTO> manageList = null;
		List<Reserved> reservedList = new ArrayList<Reserved>();
		List<Reserved> reservedListTemp = null;
		List<Borrowed> borrowedList = new ArrayList<Borrowed>();
		List<Borrowed> borrowedListTemp = null;

		if (statusNo == 1) {
			for (Integer bookId : idList) {
				reservedListTemp = bookDAO.getReservedBooksListByBookId(bookId);
				reservedList.addAll(reservedListTemp);
			}
		} else if (statusNo == 2) {
			for (Integer bookId : idList) {
				borrowedListTemp = bookDAO.getBorrowedBooksListByBookId(bookId);
				borrowedList.addAll(borrowedListTemp);
			}
			
		} else {
			for (Integer bookId : idList) {
				reservedListTemp = bookDAO.getReservedBooksListByBookId(bookId);
				reservedList.addAll(reservedListTemp);
				borrowedListTemp = bookDAO.getBorrowedBooksListByBookId(bookId);
				borrowedList.addAll(borrowedListTemp);
			}
		}
		manageList = this.combineReservedAndBorrowedBooksList(reservedList, borrowedList);
		
		return manageList;
	}
	
	private List<Integer> getIDFromEachUserInList(List<User> userList){
		
		List<Integer> idList = new ArrayList<Integer>();
		
		for (User user : userList) {
			idList.add(user.getId());
		}
		return idList;
	}
	
	private List<Integer> getIDFromEachBookInList(List<Book> bookList){
		
		List<Integer> idList = new ArrayList<Integer>();
		
		for (Book book : bookList) {
			idList.add(book.getId());
		}
		return idList;
	}
	
	private int getMangeResultTotalCountForUsersIdList(List<Integer> userIdList, int statusNo) {
		
		int resultsCount = 0;
		if (statusNo == 1) {
			for (Integer userId : userIdList) resultsCount += bookDAO.getReservedBooksTotalCountByUser(userId);
		} else if (statusNo == 2) {
			for (Integer userId : userIdList) resultsCount += bookDAO.getBorrowedBooksTotalCountByUser(userId);
		} else {
			for (Integer userId : userIdList) {
				resultsCount += bookDAO.getReservedBooksTotalCountByUser(userId);
				resultsCount += bookDAO.getBorrowedBooksTotalCountByUser(userId);
			}
		}
		return resultsCount;
	}
	
	private int getMangeResultTotalCountForBookIdList(List<Integer> bookIdList, int statusNo) {
		
		int resultsCount = 0;
		if (statusNo == 1) {
			for (Integer bookId : bookIdList) resultsCount += bookDAO.getReservedBooksTotalCountByBook(bookId);
		} else if (statusNo == 2) {
			for (Integer bookId : bookIdList) resultsCount += bookDAO.getBorrowedBooksTotalCountByBook(bookId);
		} else {
			for (Integer bookId : bookIdList) {
				resultsCount += bookDAO.getReservedBooksTotalCountByBook(bookId);
				resultsCount += bookDAO.getBorrowedBooksTotalCountByBook(bookId);
			}
		}
		return resultsCount;
	}
	
	private List<LibraryLogDTO> convertLibraryLogsEntityListToLibraryLogsDTOList(List<LibraryLog> libraryLogs) {
		
		List<LibraryLogDTO> libraryLogsDTOList = new ArrayList<LibraryLogDTO>();
		LibraryLogDTO libraryLogDTO;
		
		if (libraryLogs != null) {
			for (LibraryLog log: libraryLogs) {
				libraryLogDTO = this.convertLibraryLogEntityToLibraryLogDTO(log);
				libraryLogsDTOList.add(libraryLogDTO);
			}
		} 
		return libraryLogsDTOList;
	}

	private LibraryLogDTO convertLibraryLogEntityToLibraryLogDTO(LibraryLog log) {
		
		LibraryLogDTO libraryLogDTO = new LibraryLogDTO();
		libraryLogDTO.setId(log.getId());
		libraryLogDTO.setBookId(log.getBook().getId());
		libraryLogDTO.setBookTitle(log.getBook().getTitle());
		libraryLogDTO.setUserId(log.getUser().getId());
		libraryLogDTO.setUserFirstName(log.getUser().getFirstName());
		libraryLogDTO.setUserLastName(log.getUser().getLastName());
		libraryLogDTO.setUsername(log.getUser().getUsername());
		libraryLogDTO.setMessage(log.getMessage());
		libraryLogDTO.setDated(log.getDated());
		libraryLogDTO.setChangedByUsername(log.getChangedByUsername());
		
		return libraryLogDTO;
	}
	
	@Transactional
	private List<PenaltyDTO> converBookPenaltiesEntityListToDTOList(List<BookPenalty> penalties) {
		
		List<PenaltyDTO> penaltiesDTO = null;
		PenaltyDTO penaltyDTO = null;
		int days = 0;
		
		if (penalties != null) {
			penaltiesDTO = new ArrayList<PenaltyDTO>();
			for (BookPenalty penalty : penalties) {
				penaltyDTO = new PenaltyDTO();
				
				penaltyDTO.setBookId(penalty.getBook().getId());
				penaltyDTO.setBookTitle(penalty.getBook().getTitle());
				penaltyDTO.setDueDate(penalty.getDueDate());
				if (penalty.getReturnDate() != null) {
					penaltyDTO.setReturnDate(penalty.getReturnDate());	
					days = this.calculateDaysDifference(penalty.getReturnDate(), penalty.getDueDate());
				} else {
					days = this.calculateDaysDifference(new Date(new java.util.Date().getTime()), penalty.getDueDate());
				}
				penaltyDTO.setDays(days);
				penaltyDTO.setPenalty(this.calculatePenalty(days));
				
				penaltiesDTO.add(penaltyDTO);
			}
		}
		
		return penaltiesDTO;
	}

	private int calculateDaysDifference(Date returnDate, Date dueDate) {
		
		long diff = returnDate.getTime() - dueDate.getTime();
		
		return (int) TimeUnit.MILLISECONDS.toDays(diff);
	}

	private BigDecimal calculatePenaltyInDay(int day) {
		
		int week = (int) Math.ceil((double) day/7);
		BigDecimal penaltyInDay = new BigDecimal(0.05*week).setScale(2, BigDecimal.ROUND_HALF_EVEN);
		return penaltyInDay;
	}

}
