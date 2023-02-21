package ru.javakids.lms.service;

import ru.javakids.lms.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface SecurityService {
    void changeAuthNameAndPassword(User user);

    String getCurrentPrincipalUsername();
}
