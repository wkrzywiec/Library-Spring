package com.wkrzywiec.spring.library.unit.validation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


import com.wkrzywiec.spring.library.dto.UserDTO;


public class CustomAnnotationsTest {

	@Autowired
	private Validator validator;
	
	private UserDTO user;
	private Set<ConstraintViolation<UserDTO>> violations;
	
	@Before
	public void setUp() throws ParseException {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
		
		//given
		user = new UserDTO();
		user.setUsername("luki95");
		user.setFirstName("Lukasz");
		user.setLastName("Gol");
		user.setPassword("Cr7Gol");
		user.setConfirmPassword("Cr7Gol");
		user.setEmail("luki95@football.com");
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/mm/yyyy");
		Date date = formatter.parse("01/08/1995");
		user.setBirthday(date);
		
		user.setPhone("0048-505004785");
		user.setAddress("Polna 12");
		user.setPostalCode("01-923");
		user.setCity("Warszawa");
		
	}
	
	@Test
	public void givenUserDTO_whenCorrectData_ThenPassValidation(){
		
		//when	
		//then
		violations = validator.validate(user);
        assertTrue(violations.isEmpty());
		
	}
	/*
	@Test
	public void givenUserDTO_whenUsernameAlreadyInUser_ThenDoNotPassValidation(){
		
		//when
		user.setUsername("admin");
		//then 
		violations = validator.validate(user);
		assertFalse(violations.isEmpty());
		
	}
	*/
	@Test
	public void givenUserDTO_whenIncorrectUsername_ThenDoNotPassValidation(){
		
		//when
		user.setUsername("aa");
		//then 
		violations = validator.validate(user);
		assertFalse(violations.isEmpty());
		
		violations.clear();
		//when
		user.setUsername("skheuihedkuhwdugewdugwekmgklnkjabndjkabwdkbwadkkwbdhthrthrhdrhdh");
		//then
		violations = validator.validate(user);
		assertFalse(violations.isEmpty());
	}
	
	@Test
	public void givenUserDTO_WhenNoUsername_ThenDoNotPassValid(){
		
		//when
		user.setUsername("");
		//then
		violations = validator.validate(user);
		assertFalse(violations.isEmpty());
	}
	
	@Test
	public void givenUserDTO_WhenNullUsername_ThenDoNotPassValid(){
		
		//when
		user.setUsername(null);
		//then
		violations = validator.validate(user);
		assertFalse(violations.isEmpty());
	}
	
	@Test
	public void givenUserDTO_WhenIncorrectFirstName_ThenDoNotPassValid(){
		
		//when
		user.setFirstName("s");
		//then
		violations = validator.validate(user);
		assertFalse(violations.isEmpty());
	
		violations.clear();
		
		//when
		user.setFirstName("sdfdsfdsfskheuihedkuhwdugewdugwekmgklnkjabndjkabwdkbwadkkwbdhthrthrhdrhdhdsfsdfsfsfsef");
		//then
		violations = validator.validate(user);
		assertFalse(violations.isEmpty());
	}
	
	@Test
	public void givenUserDTO_WhenNoFirstName_ThenDoNotPassValid(){
		
		//when
		user.setFirstName("");
		//then
		violations = validator.validate(user);
		assertFalse(violations.isEmpty());
	}
	
	@Test
	public void givenUserDTO_WhenNullFirstName_ThenDoNotPassValid(){
		
		//when
		user.setFirstName(null);
		//then
		violations = validator.validate(user);
		assertFalse(violations.isEmpty());
	}
	
	@Test
	public void givenUserDTO_WhenIncorrectLastName_ThenDoNotPassValid(){
		
		//when
		user.setLastName("a");
		//then
		violations = validator.validate(user);
		assertFalse(violations.isEmpty());
		
		violations.clear();
		
		//when
		user.setLastName("sdfdsfdsfskheuihedkuhwdugewdugwekmgklnkjabndjkabwdkbwadkkwbdhthrthrhdrhdhdsfsdfsfsfsef");
		//then
		violations = validator.validate(user);
		assertFalse(violations.isEmpty());		
	}
	
	@Test
	public void givenUserDTO_WhenNoLastName_ThenDoNotPassValid(){
		
		//when
		user.setLastName("");
		//then
		violations = validator.validate(user);
		assertFalse(violations.isEmpty());
	}
	
	@Test
	public void givenUserDTO_WhenNullLastName_ThenDoNotPassValid(){
		
		//when
		user.setLastName(null);
		//then
		violations = validator.validate(user);
		assertFalse(violations.isEmpty());
	}
	
	@Test
	public void givenUserDTO_WhenIncorrectPassword_ThenDoNotPassValid(){
		
		String[] passwordsList = {"111", "aF", "football2017", "football", "A223534123",
				"a223534123"};
		
		for (String password : passwordsList){
			//when
			user.setPassword(password);
			user.setConfirmPassword(password);
			//then
			//System.out.println(violations.iterator().next().getMessage());
			violations = validator.validate(user);
			assertFalse(violations.isEmpty());
			violations.clear();
		}
		
	}
	
	
	@Test
	public void givenUserDTO_WhenPassAndConfirmPassAreNotSame_ThenDoNotPassValid(){
		
		user.setPassword("Footbal2017");
		user.setConfirmPassword("Footbal2018");
		
		violations = validator.validate(user);
		assertFalse(violations.isEmpty());
	}
	
	
	@Test
	public void givenUserDTO_WhenNoPassword_ThenDoNotPassValid(){
		
		//when
		user.setPassword("");
		//then
		violations = validator.validate(user);
		assertFalse(violations.isEmpty());
	}
	
	@Test
	public void givenUserDTO_WhenNullPassword_ThenDoNotPassValid(){
		
		//when
		user.setPassword(null);
		//then
		violations = validator.validate(user);
		assertFalse(violations.isEmpty());
	}
	
	@Test
	public void givenUserDTO_WhenNoConfirmPassword_ThenDoNotPassValid(){
		
		//when
		user.setConfirmPassword("");
		//then
		violations = validator.validate(user);
		assertFalse(violations.isEmpty());
	}
	
	@Test
	public void givenUserDTO_WhenNullConfirmPassword_ThenDoNotPassValid(){
		
		//when
		user.setConfirmPassword(null);
		//then
		violations = validator.validate(user);
		assertFalse(violations.isEmpty());
	}

	@Test
	public void givenUserDTO_WhenIncorrectEmail_ThenDoNotPassValid(){
		
		//when
		user.setEmail("luki95");
		//then
		violations = validator.validate(user);
		assertFalse(violations.isEmpty());
		
		violations.clear();
		user.setEmail("luki95@");
		violations = validator.validate(user);
		assertFalse(violations.isEmpty());
		
		violations.clear();
		user.setEmail("luki95@sdf");
		violations = validator.validate(user);
		assertFalse(violations.isEmpty());
		
		violations.clear();
		user.setEmail("luki95.com");
		violations = validator.validate(user);
		assertFalse(violations.isEmpty());
		
		violations.clear();
		user.setEmail("luki95.@com");
		violations = validator.validate(user);
		assertFalse(violations.isEmpty());
		
		violations.clear();
		user.setEmail("luki9*@com.com");
		violations = validator.validate(user);
		assertFalse(violations.isEmpty());
		
	}
	

	@Test
	public void givenUserDTO_WhenNoEmail_ThenDoNotPassValid(){
		
		//when
		user.setEmail("");
		//then
		violations = validator.validate(user);
		assertFalse(violations.isEmpty());
	}
	
	@Test
	public void givenUserDTO_WhenNullEmail_ThenDoNotPassValid(){
		
		//when
		user.setEmail(null);
		//then
		violations = validator.validate(user);
		assertFalse(violations.isEmpty());
	}
	
	//TODO String Date validation
	
	@Test(expected = ParseException.class)
	public void givenUserDTO_WhenNoBirthday_ThenDoNotPassValid() throws ParseException{
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/mm/yyyy");
		Date date = formatter.parse("");
		//when
		user.setBirthday(date);
		//then
		violations = validator.validate(user);
		assertFalse(violations.isEmpty());
	}
	
	public void givenUserDTO_WhenNullBirthday_ThenDoNotPassValid(){
		
		//when
		user.setBirthday(null);
		//then
		violations = validator.validate(user);
		assertFalse(violations.isEmpty());
	}
	
	@Test
	public void givenUserDTO_WhenIncorrectPhone_ThenDoNotPassValid(){
		
		String[] phoneList = {"111", "aaaF", "0-223534123", "05-223534123", "00a-223534123",
				"007-223b34123", "0071-12323445", "123 123 123", "+48-152152152"};
		
		for (String phone : phoneList){
			//when
			user.setPhone(phone);
			//then
			violations = validator.validate(user);
			assertFalse(violations.isEmpty());
			violations.clear();
		}
	}
	
	@Test
	public void givenUserDTO_WhenNoPhone_ThenDoNotPassValid(){
		
		//when
		user.setPhone("");
		//then
		violations = validator.validate(user);
		assertFalse(violations.isEmpty());
	}
	
	@Test
	public void givenUserDTO_WhenNullPhone_ThenDoNotPassValid(){
		
		//when
		user.setPhone(null);
		//then
		violations = validator.validate(user);
		assertFalse(violations.isEmpty());
	}
	
	
	public void givenUserDTO_WhenIncorrectLengthAddress_ThenDoNotPassValid(){
	
		//when
		user.setAddress("s");
		//then
		violations = validator.validate(user);
		assertFalse(violations.isEmpty());
	
		//when
		violations.clear();
		user.setAddress("sdfdsfdsfskheuihedkuhwdugewdugwekmgklnkjabndjkabwdkbwadkkwbdhthrthrhdrhdhdsfsdfsfsfsef");
		//then
		violations = validator.validate(user);
		assertFalse(violations.isEmpty());
		
	}
	
	
	@Test
	public void givenUserDTO_WhenNoAddress_ThenDoNotPassValid(){
		
		//when
		user.setAddress("");
		//then
		violations = validator.validate(user);
		assertFalse(violations.isEmpty());
	}
	
	@Test
	public void givenUserDTO_WhenNullAddress_ThenDoNotPassValid(){
		
		//when
		user.setAddress(null);
		//then
		violations = validator.validate(user);
		assertFalse(violations.isEmpty());
	}
	
	
	@Test
	public void givenUserDTO_WhenIncorrectPostalCode_ThenDoNotPassValid(){
		
		String[] postalCodesList = {"0-152", "00513", "aa-15h", "123", "00--546",
				"00 152"};
		
		for (String postalCode : postalCodesList){
			//when
			user.setPostalCode(postalCode);
			//then
			violations = validator.validate(user);
			assertFalse(violations.isEmpty());
			violations.clear();
		}
		
	}
	
	@Test
	public void givenUserDTO_WhenNoPostalCode_ThenDoNotPassValid(){
		
		//when
		user.setPostalCode("");
		//then
		violations = validator.validate(user);
		assertFalse(violations.isEmpty());
	}
	
	@Test
	public void givenUserDTO_WhenNullPostalCode_ThenDoNotPassValid(){
		
		//when
		user.setPostalCode(null);
		//then
		violations = validator.validate(user);
		assertFalse(violations.isEmpty());
	}
	
	@Test
	public void givenUserDTO_WhenNoCity_ThenDoNotPassValid(){
		
		//when
		user.setCity("");
		//then
		violations = validator.validate(user);
		assertFalse(violations.isEmpty());
	}
	
	@Test
	public void givenUserDTO_WhenNullCity_ThenDoNotPassValid(){
		
		//when
		user.setCity(null);
		//then
		violations = validator.validate(user);
		assertFalse(violations.isEmpty());
	}
	
	
}
