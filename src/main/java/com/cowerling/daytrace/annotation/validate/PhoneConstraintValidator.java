package com.cowerling.daytrace.annotation.validate;

import com.cowerling.daytrace.annotation.Phone;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneConstraintValidator implements ConstraintValidator<Phone, String> {
    @Override
    public void initialize(Phone phone) {}

    @Override
    public boolean isValid(String phoneField, ConstraintValidatorContext constraintValidatorContext) {
        if (phoneField == null) {
            return false;
        }

        return phoneField.matches("^\\d{13}$");
    }
}
