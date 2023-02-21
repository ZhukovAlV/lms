package ru.javakids.lms.service;

import ru.javakids.lms.entity.User;
import ru.javakids.lms.dto.RegisterUserDto;
import ru.javakids.lms.dto.UserDto;

import java.util.List;

public interface UserService {
    User getUserById(Long id);

    User findUserById(Long id);

    List<User> findUsersNotAssignedToCourse(Long CourseId);

    boolean existsById(Long userId);

    void saveRegisterUserDto(RegisterUserDto registerUserDto);

    void saveUserDto(UserDto userDto);

    User findUserByUsername(String username);

    UserDto findUserDtoById(Long id);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    List<UserDto> findAllUserDto();
}
