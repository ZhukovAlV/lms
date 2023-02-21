package ru.javakids.lms.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import ru.javakids.lms.validator.AdminMustHaveRoleAdminValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy = AdminMustHaveRoleAdminValidator.class)
public @interface AdminMustHaveRoleAdmin {
    String message() default "Нельзя отменить права у администратора";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
