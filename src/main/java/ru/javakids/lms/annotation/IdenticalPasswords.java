package ru.javakids.lms.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import ru.javakids.lms.validator.IdenticalPasswordsValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy = IdenticalPasswordsValidator.class)
public @interface IdenticalPasswords {
    String message() default "Пароли не совпадают";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
