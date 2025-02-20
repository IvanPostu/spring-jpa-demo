package com.ipostu.demo.spring.jar11demo.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NoJohnValueValidator.class)
public @interface NoJohnValue {
    String message() default "Custom validation failed";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
