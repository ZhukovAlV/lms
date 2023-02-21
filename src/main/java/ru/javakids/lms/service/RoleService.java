package ru.javakids.lms.service;

import ru.javakids.lms.entity.Role;

import java.util.List;

public interface RoleService {
    Role findByName(String name);

    List<Role> findAll();
}
