package com.wkrzywiec.spring.library.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PostalCodeValidator implements ConstraintValidator<PostalCode, String>{

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		
		return value != null && value.matches("[0-9]{2}\\-[0-9]{3}");
	}

}
