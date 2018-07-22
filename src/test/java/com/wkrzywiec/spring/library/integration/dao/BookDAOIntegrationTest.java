package com.wkrzywiec.spring.library.integration.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.wkrzywiec.spring.library.config.LibraryConfig;
import com.wkrzywiec.spring.library.dao.BookDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= LibraryConfig.class)
@WebAppConfiguration("src/main/java")
public class BookDAOIntegrationTest {

	@Autowired
	private BookDAO bookDAO;
	
	@Test
	public void givenAppContext_WhenAutowire_ThenClassesAreAvailble(){
		assertNotNull(bookDAO);
	}
	
	@Test
	@Transactional
	public void givenDAO_WhenGetAllReservedBooks_ThenReceiveTotalCount() {
		//given
		int resultCount = 0;
		//when
		resultCount = bookDAO.getReservedBooksTotalCount();
		//then
		assertTrue(resultCount > 0);
	}
	
	@Test
	@Transactional
	public void givenDAO_WhenGetAllBorrowedBooks_ThenReceiveTotalCount() {
		//given
		int resultCount = 0;
		//when
		resultCount = bookDAO.getBorrowedBooksTotalCount();
		//then
		assertTrue(resultCount > 0);
	}
	
	@Test
	@Transactional
	public void givenUserId_whenGetReservedBooksCountForUser_thenReceiveUserReservedBooksTotalCount() {
		//given
		int userId = 1;
		int resultCount = 0;
		//when
		resultCount = bookDAO.getReservedBooksTotalCountByUser(userId);
		//then
		assertTrue(resultCount > 0);
	}
	
	@Test
	@Transactional
	public void givenUserId_whenGetBorrowedBooksCountForUser_thenReceiveUserBorrowedBooksTotalCount() {
		//given
		int userId = 1;
		int resultCount = 0;
		//when
		resultCount = bookDAO.getBorrowedBooksTotalCountByUser(userId);
		//then
		assertTrue(resultCount > 0);
	}
}
