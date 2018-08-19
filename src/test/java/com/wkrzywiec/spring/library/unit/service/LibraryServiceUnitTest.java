package com.wkrzywiec.spring.library.unit.service;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.wkrzywiec.spring.library.dto.ManageDTO;
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
	
	@Test
	public void givenManageList_thenSortByRelevant_thenListSorted() {
		List<ManageDTO> manageList = this.prepareUnsortedManageDTOList();
		libraryService.sortManageList(manageList, "rel");
		assertEquals(manageList, this.manageListSortedByRelevancy());
	}
	
	@Test
	public void givenManageList_thenSortByBookAsc_thenListSorted() {
		List<ManageDTO> manageList = this.prepareUnsortedManageDTOList();
		libraryService.sortManageList(manageList, "bookAsc");
		assertEquals(manageList, this.manageListSortedByAsc());
	}
	
	@Test
	public void givenManageList_thenSortByBookDes_thenListSorted() {
		List<ManageDTO> manageList = this.prepareUnsortedManageDTOList();
		libraryService.sortManageList(manageList, "bookDes");
		assertEquals(manageList, this.manageListSortedDes());
	}
	
	@Test
	public void givenManageList_thenSortByUserAsc_thenListSorted() {
		List<ManageDTO> manageList = this.prepareUnsortedManageDTOList();
		libraryService.sortManageList(manageList, "userAsc");
		assertEquals(manageList, this.manageListSortedByAsc());
	}
	
	@Test
	public void givenManageList_thenSortByUserDes_thenListSorted() {
		List<ManageDTO> manageList = this.prepareUnsortedManageDTOList();
		libraryService.sortManageList(manageList, "userDes");
		assertEquals(manageList, this.manageListSortedDes());
	}
	
	@Test
	public void givenManageList_thenSortByStatusAsc_thenListSorted() {
		List<ManageDTO> manageList = this.prepareUnsortedManageDTOList();
		libraryService.sortManageList(manageList, "statusAsc");
		assertEquals(manageList, this.manageListSortedByAsc());
	}
	
	@Test
	public void givenManageList_thenSortByStatusDes_thenListSorted() {
		List<ManageDTO> manageList = this.prepareUnsortedManageDTOList();
		libraryService.sortManageList(manageList, "statusDes");
		assertEquals(manageList, this.manageListSortedByStatusDes());
	}
	
	@Test
	public void givenDays_thenCalculatePenalty_thenGetPenalty() {
		
		int days = 0;
		assertEquals(libraryService.calculatePenalty(days), new BigDecimal(0.00));
		
		days = 1;
		assertEquals(new BigDecimal(0.05).setScale(2, BigDecimal.ROUND_HALF_EVEN), libraryService.calculatePenalty(days));
		
		days = 3;
		assertEquals(new BigDecimal(0.15).setScale(2, BigDecimal.ROUND_HALF_EVEN), libraryService.calculatePenalty(days));
		
		days = 7;
		assertEquals(new BigDecimal(0.35).setScale(2, BigDecimal.ROUND_HALF_EVEN), libraryService.calculatePenalty(days));
		
		days = 8;
		assertEquals(new BigDecimal(0.45).setScale(2, BigDecimal.ROUND_HALF_EVEN), libraryService.calculatePenalty(days));
	
		days = 10;
		assertEquals(new BigDecimal(0.65).setScale(2, BigDecimal.ROUND_HALF_EVEN), libraryService.calculatePenalty(days));
	
	
		days = 14;
		assertEquals(new BigDecimal(1.05).setScale(2, BigDecimal.ROUND_HALF_EVEN), libraryService.calculatePenalty(days));
	}
	
	private List<ManageDTO> prepareUnsortedManageDTOList(){
		
		List<ManageDTO> manageList = new ArrayList<ManageDTO>();
		ManageDTO manage1 = new ManageDTO();
		
		manage1.setBookTitle("Bible");
		manage1.setUserFirstName("Johnny");
		manage1.setUserLastName("Bravo");
		manage1.setBookStatus("RESERVED");
		try {
			manage1.setDueDate(new SimpleDateFormat("dd/MM/yyyy").parse("15/07/2018"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		manageList.add(manage1);
		
		ManageDTO manage2 = new ManageDTO();
		manage2.setBookTitle("Atlas Shrugged");
		manage2.setUserFirstName("Johnny");
		manage2.setUserLastName("Arkin");
		manage2.setBookStatus("BORROWED");
		try {
			manage2.setDueDate(new SimpleDateFormat("dd/MM/yyyy").parse("08/07/2018"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		manageList.add(manage2);
		
		ManageDTO manage3 = new ManageDTO();
		manage3.setBookTitle("Chemistry");
		manage3.setUserFirstName("Johnny");
		manage3.setUserLastName("Cranston");
		manage3.setBookStatus("RESERVED");
		try {
			manage3.setDueDate(new SimpleDateFormat("dd/MM/yyyy").parse("30/07/2018"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		manageList.add(manage3);
		return manageList;
	}
	
	private List<ManageDTO> manageListSortedByRelevancy(){
		
		List<ManageDTO> manageList = new ArrayList<ManageDTO>();
		ManageDTO manage1 = new ManageDTO();
		
		manage1.setBookTitle("Bible");
		manage1.setUserFirstName("Johnny");
		manage1.setUserLastName("Bravo");
		manage1.setBookStatus("RESERVED");
		try {
			manage1.setDueDate(new SimpleDateFormat("dd/MM/yyyy").parse("15/07/2018"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		manageList.add(manage1);
		
		ManageDTO manage2 = new ManageDTO();
		manage2.setBookTitle("Atlas Shrugged");
		manage2.setUserFirstName("Johnny");
		manage2.setUserLastName("Arkin");
		manage2.setBookStatus("BORROWED");
		try {
			manage2.setDueDate(new SimpleDateFormat("dd/MM/yyyy").parse("08/07/2018"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		manageList.add(manage2);
		
		ManageDTO manage3 = new ManageDTO();
		manage3.setBookTitle("Chemistry");
		manage3.setUserFirstName("Johnny");
		manage3.setUserLastName("Cranston");
		manage3.setBookStatus("RESERVED");
		try {
			manage3.setDueDate(new SimpleDateFormat("dd/MM/yyyy").parse("30/07/2018"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		manageList.add(manage3);
		
		return manageList;
	}
	
	private List<ManageDTO> manageListSortedByAsc(){
		
		List<ManageDTO> manageList = new ArrayList<ManageDTO>();
		
		ManageDTO manage1 = new ManageDTO();
		manage1.setBookTitle("Atlas Shrugged");
		manage1.setUserFirstName("Johnny");
		manage1.setUserLastName("Arkin");
		manage1.setBookStatus("BORROWED");
		try {
			manage1.setDueDate(new SimpleDateFormat("dd/MM/yyyy").parse("08/07/2018"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		manageList.add(manage1);
		
		ManageDTO manage2 = new ManageDTO();
		manage2.setBookTitle("Bible");
		manage2.setUserFirstName("Johnny");
		manage2.setUserLastName("Bravo");
		manage2.setBookStatus("RESERVED");
		try {
			manage2.setDueDate(new SimpleDateFormat("dd/MM/yyyy").parse("15/07/2018"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		manageList.add(manage2);
		
		ManageDTO manage3 = new ManageDTO();
		manage3.setBookTitle("Chemistry");
		manage3.setUserFirstName("Johnny");
		manage3.setUserLastName("Cranston");
		manage3.setBookStatus("RESERVED");
		try {
			manage3.setDueDate(new SimpleDateFormat("dd/MM/yyyy").parse("30/07/2018"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		manageList.add(manage3);
		
		return manageList;
	}
	
	private List<ManageDTO> manageListSortedDes(){
		
		List<ManageDTO> manageList = new ArrayList<ManageDTO>();
		
		ManageDTO manage3 = new ManageDTO();
		manage3.setBookTitle("Chemistry");
		manage3.setUserFirstName("Johnny");
		manage3.setUserLastName("Cranston");
		manage3.setBookStatus("RESERVED");
		try {
			manage3.setDueDate(new SimpleDateFormat("dd/MM/yyyy").parse("30/07/2018"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		manageList.add(manage3);
		
		ManageDTO manage1 = new ManageDTO();
		manage1.setBookTitle("Bible");
		manage1.setUserFirstName("Johnny");
		manage1.setUserLastName("Bravo");
		manage1.setBookStatus("RESERVED");
		try {
			manage1.setDueDate(new SimpleDateFormat("dd/MM/yyyy").parse("15/07/2018"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		manageList.add(manage1);
		
		ManageDTO manage2 = new ManageDTO();
		manage2.setBookTitle("Atlas Shrugged");
		manage2.setUserFirstName("Johnny");
		manage2.setUserLastName("Arkin");
		manage2.setBookStatus("BORROWED");
		try {
			manage2.setDueDate(new SimpleDateFormat("dd/MM/yyyy").parse("08/07/2018"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		manageList.add(manage2);
		
		return manageList;
	}
	
	private List<ManageDTO> manageListSortedByStatusDes(){
		
		List<ManageDTO> manageList = new ArrayList<ManageDTO>();
		
		ManageDTO manage1 = new ManageDTO();
		manage1.setBookTitle("Bible");
		manage1.setUserFirstName("Johnny");
		manage1.setUserLastName("Bravo");
		manage1.setBookStatus("RESERVED");
		try {
			manage1.setDueDate(new SimpleDateFormat("dd/MM/yyyy").parse("15/07/2018"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		manageList.add(manage1);
		
		ManageDTO manage3 = new ManageDTO();
		manage3.setBookTitle("Chemistry");
		manage3.setUserFirstName("Johnny");
		manage3.setUserLastName("Cranston");
		manage3.setBookStatus("RESERVED");
		try {
			manage3.setDueDate(new SimpleDateFormat("dd/MM/yyyy").parse("30/07/2018"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		manageList.add(manage3);
		
		ManageDTO manage2 = new ManageDTO();
		manage2.setBookTitle("Atlas Shrugged");
		manage2.setUserFirstName("Johnny");
		manage2.setUserLastName("Arkin");
		manage2.setBookStatus("BORROWED");
		try {
			manage2.setDueDate(new SimpleDateFormat("dd/MM/yyyy").parse("08/07/2018"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		manageList.add(manage2);
		
		return manageList;
	}
}

