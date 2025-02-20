package com.ipostu.demo.spring.jar11demo.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NoJohnValueValidator implements ConstraintValidator<NoJohnValue, String> {
    @Override
    public void initialize(NoJohnValue constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        return !value.startsWith("John");
    }
}
