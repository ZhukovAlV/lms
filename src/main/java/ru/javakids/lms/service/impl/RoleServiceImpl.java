package ru.javakids.lms.service.impl;

import ru.javakids.lms.entity.Role;
import ru.javakids.lms.exception.NotFoundException;
import ru.javakids.lms.repository.RoleRepository;
import ru.javakids.lms.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findByName(String name) {
        return roleRepository.findRoleByName(name)
                .orElseThrow(() -> new NotFoundException(Role.class.getSimpleName(), name));
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }
}
