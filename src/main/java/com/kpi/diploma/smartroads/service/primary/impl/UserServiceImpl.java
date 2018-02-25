package com.kpi.diploma.smartroads.service.primary.impl;

import com.kpi.diploma.smartroads.model.document.user.User;
import com.kpi.diploma.smartroads.model.dto.user.UserDto;
import com.kpi.diploma.smartroads.model.util.exception.ImageServiceException;
import com.kpi.diploma.smartroads.repository.UserRepository;
import com.kpi.diploma.smartroads.service.primary.UserService;
import com.kpi.diploma.smartroads.service.util.image.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ImageService imageService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           ImageService imageService) {
        this.userRepository = userRepository;
        this.imageService = imageService;
    }

    @Override
    public boolean existByEmail(String email) {
        log.info("'existByEmail' invoked with params'{}'", email);
        User userByEmail = userRepository.findByEmail(email);
        log.info("returned'{}'", userByEmail);
        return userByEmail == null;
    }

    @Override
    public UserDto setAvatar(String userId, MultipartFile multipartFile) throws Exception {
        log.info("'setAvatar' invoked with params'{}, {}'", userId, multipartFile.getOriginalFilename());

        String avatarUrl = imageService.uploadImage(multipartFile);
        User user = userRepository.findOne(userId);
        user.setAvatarUrl(avatarUrl);
        user = userRepository.save(user);

        UserDto userDto = UserDto.convert(user);
        log.info("'userDto={}'", userDto);

        return userDto;
    }

    @Override
    public UserDto deleteAvatar(String userId) {
        log.info("'deleteAvatar' invoked with params'{}'", userId);

        User user = userRepository.findOne(userId);
        if(user.getAvatarUrl() == null) {
            throw new ImageServiceException("user doesn't has avatar");
        }
        Boolean result = imageService.deleteImage(user.getAvatarUrl());

        if(result) {
            user.setAvatarUrl(null);
            user = userRepository.save(user);
            UserDto userDto = UserDto.convert(user);
            log.info("'userDto={}'", userDto);
            return userDto;
        } else {
            throw new ImageServiceException("avatar wasn't deleted");
        }
    }
}
