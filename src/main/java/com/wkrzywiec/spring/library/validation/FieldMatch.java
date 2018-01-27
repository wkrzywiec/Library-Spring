package com.wkrzywiec.spring.library.validation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = FieldMatchValidator.class)
@Retention(RUNTIME)
@Target({ TYPE, ANNOTATION_TYPE })
public @interface FieldMatch {

	String message() default "Fields are not matching!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String first();

    String second();

    
    @Target({TYPE, ANNOTATION_TYPE})
    @Retention(RUNTIME)
   
     @interface List{
        FieldMatch[] value();
    }
}
