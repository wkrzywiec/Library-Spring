package com.wkrzywiec.spring.library.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wkrzywiec.spring.library.service.LibraryUserDetailService;

@Component
public class UniqueFieldValidator implements ConstraintValidator<UniqueField, String>{

	@Autowired
	private LibraryUserDetailService userService;
	
	private String column;
	
	
	@Override
	public void initialize(UniqueField constraintAnnotation) {
		column = constraintAnnotation.column();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return !userService.isUsernameAlreadyInUse(value, column);
	}

}
