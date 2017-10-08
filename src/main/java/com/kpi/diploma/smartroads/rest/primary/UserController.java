package com.kpi.diploma.smartroads.rest.primary;

import com.kpi.diploma.smartroads.model.security.MongoUserDetails;
import com.kpi.diploma.smartroads.service.util.image.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping(value = "/user")
public class UserController {

    private final ImageService imageService;

    @Autowired
    public UserController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping(value = "/upload-avatar")
    private ResponseEntity<Boolean> uploadAvatar(@AuthenticationPrincipal MongoUserDetails principal,
                                                 @RequestParam("file") MultipartFile multipartFile) throws Exception {
        log.info("'uploadAvatar' invoked. File name '{}'", multipartFile.getOriginalFilename());

        Boolean result = imageService.uploadAvatar(principal.getUserId(), multipartFile);

        return ResponseEntity.ok(result);
    }
}
