package com.wkrzywiec.spring.library.validation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = CustomEmailValidator.class)
@Retention(RUNTIME)
@Target({ FIELD, METHOD })
public @interface CustomEmail {

	public String message() default "Incorrect email format!";
	
	public Class<?>[] groups() default {};
	
	public Class<? extends Payload>[] payload() default{};
}
