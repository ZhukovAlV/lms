package ru.javakids.lms.service;

import java.io.InputStream;
import java.util.Optional;

public interface AvatarStorageService {
    void save(Long id, String contentType, InputStream is);

    String getContentTypeByUserId(Long id);

    Optional<byte[]> getAvatarImageByUserId(Long id);
}
