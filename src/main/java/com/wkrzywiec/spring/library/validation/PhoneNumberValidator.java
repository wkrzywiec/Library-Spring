package com.wkrzywiec.spring.library.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneNumberValidator 
			implements ConstraintValidator<PhoneNumber, String>{

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		
		return value != null && value.matches("[0-9]{4}\\-[0-9]{9}");
	}

	
}
