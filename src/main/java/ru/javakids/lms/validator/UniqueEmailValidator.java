package ru.javakids.lms.validator;

import ru.javakids.lms.annotation.UniqueEmail;
import ru.javakids.lms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {
    @Autowired
    private UserService userService;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        return !userService.existsByEmail(username);
    }
}
