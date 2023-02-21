package ru.javakids.lms.service.impl;

import ru.javakids.lms.entity.AvatarImage;
import ru.javakids.lms.entity.User;
import ru.javakids.lms.exception.NotFoundException;
import ru.javakids.lms.repository.AvatarImageRepository;
import ru.javakids.lms.service.AvatarStorageService;
import ru.javakids.lms.service.UserService;
import ru.javakids.lms.util.FileUtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.InputStream;
import java.util.Optional;
import java.util.UUID;

@Service
public class AvatarStorageServiceImpl implements AvatarStorageService {

    private final AvatarImageRepository avatarImageRepository;
    private final UserService userService;
    private final FileUtilService fileUtilService;

    @Autowired
    public AvatarStorageServiceImpl(AvatarImageRepository avatarImageRepository,
                                    UserService userService,
                                    FileUtilService fileUtilService) {
        this.avatarImageRepository = avatarImageRepository;
        this.userService = userService;
        this.fileUtilService = fileUtilService;
    }

    @Transactional
    @Override
    public void save(Long id, String contentType, InputStream is) {
        Optional<AvatarImage> opt = avatarImageRepository.findByUserId(id);
        AvatarImage avatarImage;
        String filename;
        if (opt.isEmpty()) {
            filename = UUID.randomUUID().toString();
            User user = userService.findUserById(id);
            avatarImage = new AvatarImage(null, contentType, filename, user);
        } else {
            avatarImage = opt.get();
            filename = avatarImage.getFilename();
            avatarImage.setContentType(contentType);
        }
        avatarImageRepository.save(avatarImage);
        fileUtilService.saveFile(is, filename);
    }

    @Override
    public String getContentTypeByUserId(Long id) {
        return avatarImageRepository.findByUserId(id)
                .map(AvatarImage::getContentType)
                .orElseThrow(() -> new NotFoundException(AvatarImage.class.getSimpleName(), id));
    }

    @Override
    public Optional<byte[]> getAvatarImageByUserId(Long id) {
        return avatarImageRepository.findByUserId(id)
                .map(AvatarImage::getFilename)
                .map(fileUtilService::readFile);
    }
}
