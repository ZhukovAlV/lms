package ru.javakids.lms.mapper;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.javakids.lms.dto.RegisterUserDto;
import ru.javakids.lms.dto.UserDto;
import ru.javakids.lms.entity.User;
import ru.javakids.lms.service.RoleService;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserMapper {
    private PasswordEncoder encoder;
    private final RoleService roleService;


    public void setEncoder(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    public UserMapper(RoleService roleService) {
        this.roleService = roleService;
    }

    public User mapRegisterUserDtoToUser(RegisterUserDto dto) {
        return new User(dto.getUsername(),
                encoder.encode(dto.getPassword()),
                dto.getEmail(),
                new Date(),
                Set.of(roleService.findByName("ROLE_STUDENT")));
    }

    public UserDto mapUserToUserDto(User user) {
        return new UserDto(user.getId(), user.getUsername(), user.getFirstName(), user.getLastName(), user.getPhoneNumber(), user.getEmail(), user.getCourses(), user.getRoles());
    }

    public void mapUserDtoToUser(UserDto userDto, User user) {
        if (!userDto.getPassword().equals(""))
            user.setPassword(encoder.encode(userDto.getPassword()));
        if (userDto.getRoles() != null)
            user.setRoles(userDto.getRoles());
        if (userDto.getCourses() != null)
            user.setCourses(userDto.getCourses());
        user.setUsername(userDto.getUsername());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setEmail(userDto.getEmail());
    }

    public List<UserDto> mapUserListToUserDtoList(List<User> users) {
        return users.stream()
                .map(u -> new UserDto(u.getId(), u.getUsername()))
                .collect(Collectors.toList());
    }
}
