package com.kpi.diploma.smartroads.service.util.image;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.kpi.diploma.smartroads.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Slf4j
@Service
public class ImageService {

    private final UserRepository userRepository;
    private final Cloudinary cloudinary;

    @Autowired
    public ImageService(UserRepository userRepository, Cloudinary cloudinary) {
        this.userRepository = userRepository;
        this.cloudinary = cloudinary;
    }

    public Boolean uploadAvatar(String userId, MultipartFile multipartFile) throws Exception {
        log.info("'uploadAvatar' invoke with params'{}, {}'", userId, multipartFile.getOriginalFilename());

        Map upload = cloudinary.uploader().upload(multipartFile.getBytes(), ObjectUtils.emptyMap());
        String url = upload.get("url").toString();

        return true;
    }

}
