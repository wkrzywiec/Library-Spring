package com.wkrzywiec.spring.library.integration.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
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
	
	@Test
	@Transactional
	@Rollback(true)
	public void givenCorrectUser_WhenSaveUser_ThenUserSavedInDb(){
		
		//given
		
		User user = new User();
		user.setUsername("hound");
		user.setPassword("killThemA11");
		user.setEmail("sandor.clegane@mail.com");
		user.setEnable(true);
		
		user.setFirstName("Sandor");
		user.setLastName("Clegane");
		user.setPhone("123456789");
		user.setAddress("Field street 13 / 23");
		user.setPostalCode("12-345");
		user.setCity("King's Landing");
		
		//when
		userDAO.saveUser(user, "hound");
		
		//then
		assertEquals(user, userDAO.getActiveUser("hound"));
	}
	
}
