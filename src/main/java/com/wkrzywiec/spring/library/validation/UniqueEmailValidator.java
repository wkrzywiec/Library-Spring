package com.wkrzywiec.spring.library.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.wkrzywiec.spring.library.service.LibraryUserDetailService;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

	@Autowired
	private LibraryUserDetailService userService;

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return value != null && !userService.isEmailAlreadyInUse(value);
	}

	
	
}
