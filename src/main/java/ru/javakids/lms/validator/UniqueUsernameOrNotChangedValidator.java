package ru.javakids.lms.validator;

import ru.javakids.lms.annotation.UniqueUsernameOrNotChanged;
import ru.javakids.lms.entity.User;
import ru.javakids.lms.dto.UserDto;
import ru.javakids.lms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueUsernameOrNotChangedValidator implements ConstraintValidator<UniqueUsernameOrNotChanged, UserDto> {
    @Autowired
    private UserService userService;

    @Override
    public boolean isValid(UserDto userDto, ConstraintValidatorContext context) {
        boolean isValid = !userService.existsByUsername(userDto.getUsername());
        if (!isValid) {
            User user = userService.findUserById(userDto.getId());
            if (user.getUsername().equals(userDto.getUsername()))
                isValid = true;
            else {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                        .addPropertyNode("username").addConstraintViolation();
            }

        }
        return isValid;
    }
}
