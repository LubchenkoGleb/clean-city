package com.kpi.diploma.smartroads.validation;

import com.kpi.diploma.smartroads.model.document.User;

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
                user.getPassword().equals(user.getMatchingPassword());
    }
}