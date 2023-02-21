package ru.javakids.lms.validator;

import ru.javakids.lms.annotation.UniqueEmailOrNotChanged;
import ru.javakids.lms.entity.User;
import ru.javakids.lms.dto.UserDto;
import ru.javakids.lms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueEmailOrNotChangedValidator implements ConstraintValidator<UniqueEmailOrNotChanged, UserDto> {
    @Autowired
    private UserService userService;

    @Override
    public boolean isValid(UserDto userDto, ConstraintValidatorContext context) {
        boolean isValid = !userService.existsByEmail(userDto.getEmail());
        if (!isValid) {
            User user = userService.findUserById(userDto.getId());
            if (user.getEmail()==null || user.getEmail().equals(userDto.getEmail()))
                isValid = true;
            else {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                        .addPropertyNode("email").addConstraintViolation();
            }

        }
        return isValid;
    }
}