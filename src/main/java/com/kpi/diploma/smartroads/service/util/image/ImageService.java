package com.kpi.diploma.smartroads.service.util.image;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Slf4j
@Service
public class ImageService {

    private final Cloudinary cloudinary;

    @Autowired
    public ImageService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String uploadAvatar(String userId, MultipartFile multipartFile) throws Exception {
        log.info("'uploadAvatar' invoke with params'{}, {}'", userId, multipartFile.getOriginalFilename());

        Map upload = cloudinary.uploader().upload(multipartFile.getBytes(), ObjectUtils.emptyMap());
        String url = upload.get("url").toString();
        log.info("'url={}'", url);

        return url;
    }
}
