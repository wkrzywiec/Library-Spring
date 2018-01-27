package com.wkrzywiec.spring.library.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.stereotype.Component;

@Component
public class PasswordValidator implements ConstraintValidator<PasswordValid, String> {

	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		
	    return value != null && value.matches("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,20}");
		
	}

}
