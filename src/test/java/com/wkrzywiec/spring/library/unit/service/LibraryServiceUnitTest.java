package com.wkrzywiec.spring.library.unit.service;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.wkrzywiec.spring.library.service.LibraryService;
import com.wkrzywiec.spring.library.service.LibraryServiceImpl;

@RunWith(JUnit4.class)
public class LibraryServiceUnitTest {

	LibraryService libraryService;
	
	@Before
	public void setUp() {
		libraryService = new LibraryServiceImpl();
	}
	
	@Test
	public void givenResultsCountAndMaxPagesNumber_thenCalculateTotalPagesCount_givenTotalPagesCount() {
		
		//given
		int resultsPerPage = 10;
		int resultsCount = 0;
		//when
		//then
		assertEquals(1, libraryService.calculateManagePagesCount(resultsCount, resultsPerPage));
		
		//given
		resultsCount = 8;
		//when
		//then
		assertEquals(1, libraryService.calculateManagePagesCount(resultsCount, resultsPerPage));
		
		//given
		resultsCount = 10;
		//when
		//then
		assertEquals(1, libraryService.calculateManagePagesCount(resultsCount, resultsPerPage));
		
		//given
		resultsCount = 13;
		//when
		//then
		assertEquals(2, libraryService.calculateManagePagesCount(resultsCount, resultsPerPage));
		
		//given
		resultsCount = 131;
		//when
		//then
		assertEquals(14, libraryService.calculateManagePagesCount(resultsCount, resultsPerPage));
	}
}
