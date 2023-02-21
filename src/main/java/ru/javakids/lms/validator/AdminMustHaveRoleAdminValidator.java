package ru.javakids.lms.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import ru.javakids.lms.annotation.AdminMustHaveRoleAdmin;
import ru.javakids.lms.entity.User;
import ru.javakids.lms.dto.UserDto;
import ru.javakids.lms.service.RoleService;
import ru.javakids.lms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public class AdminMustHaveRoleAdminValidator implements ConstraintValidator<AdminMustHaveRoleAdmin, UserDto> {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @Override
    public boolean isValid(UserDto userDto, ConstraintValidatorContext context) {
        User user = userService.findUserById(userDto.getId());
        boolean isValid = true;
        boolean isUserAdmin = user.getRoles().contains(roleService.findByName("ROLE_ADMIN"));
        if (isUserAdmin){
            boolean isUserStillAdmin = userDto.getRoles().contains(roleService.findByName("ROLE_ADMIN"));
            if (!isUserStillAdmin) isValid = false;
        }
        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                    .addPropertyNode("roles").addConstraintViolation();
        }
        return isValid;
    }

}