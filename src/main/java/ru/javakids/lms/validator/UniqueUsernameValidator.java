package ru.javakids.lms.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import ru.javakids.lms.annotation.UniqueUsername;
import ru.javakids.lms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {
    @Autowired
    private UserService userService;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        return !userService.existsByUsername(username);
    }
}
