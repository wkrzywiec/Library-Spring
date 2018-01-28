package com.wkrzywiec.spring.library.integration.dao;

import static org.junit.Assert.*;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.wkrzywiec.spring.library.config.LibraryConfig;
import com.wkrzywiec.spring.library.dao.UserDAO;
import com.wkrzywiec.spring.library.entity.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= LibraryConfig.class)
@WebAppConfiguration("src/main/java")
public class UserDAOIntegrationTest {


	@Autowired
	private UserDAO userDAO;
	
	@Test
	public void givenAppContext_WhenAutowire_ThenClassesAreAvailble(){
		assertNotNull(userDAO);
	}
	
	@Test
	@Transactional
	public void givenUsername_WhenQueryDatabase_ThenGetUser(){
		
		//given
		String username = "admin";
		//when
		User user = userDAO.getActiveUser(username);
		//then
		assertEquals("admin", user.getUsername());
	}
	
	@Test
	@Transactional
	public void givenIncorrectUsername_WhenQueryDatabase_ThenGetNull(){
		
		//given
		String username = "sjkdf";
		//when
		User user = userDAO.getActiveUser(username);
		//then
		assertNull(user);
		
	}
	
	@Test
	@Transactional
	public void givenEmail_WhenQueryDatabase_ThenGetUser(){
		
		//given
		String email = "edard.stark@winterfell.com";
		//when
		User user = userDAO.getActiveUserByEmail(email);
		//then
		assertEquals("admin", user.getUsername());
	}
	
}
