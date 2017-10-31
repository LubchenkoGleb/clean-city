package com.kpi.diploma.smartroads.service.util.image;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.cloudinary.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class ImageService {

    private final Cloudinary cloudinary;

    @Autowired
    public ImageService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String uploadImage(MultipartFile multipartFile) throws Exception {
        log.info("'uploadImage' invoke with params'{}'", multipartFile.getOriginalFilename());

        Map upload = cloudinary.uploader().upload(multipartFile.getBytes(), ObjectUtils.emptyMap());
        log.info("'upload={}'", upload);

        String url = upload.get("imageUrl").toString();
        log.info("'imageUrl={}'", url);

        return url;
    }

    public boolean deleteImage(String url) {
        log.info("'deleteImage' invoked with params'{}'", url);

        try {
            Matcher matcher = Pattern.compile("[^/]*(?=[.][a-zA-Z]+$)").matcher(url);
            if (matcher.find()) {
                Map destroy = cloudinary.uploader().destroy(matcher.group(), ObjectUtils.emptyMap());
                log.info("'destroy={}'", destroy);
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("image wasn't deleted");
        return false;
    }
}
