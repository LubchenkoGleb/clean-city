package com.kpi.diploma.smartroads.service.util.validation;

import com.kpi.diploma.smartroads.model.document.user.User;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator
        implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }
    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        User user = (User) obj;

        return user.getPassword() != null &&
                user.getMatchingPassword() != null &&
                user.getPassword().length() < 8 &&
                user.getPassword().matches(".*\\d+.*") &&
                user.getPassword().equals(user.getMatchingPassword());
    }
}