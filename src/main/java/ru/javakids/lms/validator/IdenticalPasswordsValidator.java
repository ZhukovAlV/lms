package ru.javakids.lms.validator;

import ru.javakids.lms.annotation.IdenticalPasswords;
import ru.javakids.lms.dto.RegisterUserDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IdenticalPasswordsValidator implements ConstraintValidator<IdenticalPasswords, RegisterUserDto> {
    @Override
    public boolean isValid(RegisterUserDto registerUserDto, ConstraintValidatorContext context) {
        boolean isValid = registerUserDto.getPassword().equals(registerUserDto.getRepeatedPassword());
        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                    .addPropertyNode("repeatedPassword").addConstraintViolation();
        }
        return isValid;
    }

}
