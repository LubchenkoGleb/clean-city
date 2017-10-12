package com.kpi.diploma.smartroads.rest.primary;

import com.kpi.diploma.smartroads.model.dto.UserDto;
import com.kpi.diploma.smartroads.model.security.MongoUserDetails;
import com.kpi.diploma.smartroads.service.primary.UserService;
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

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/upload-avatar")
    private ResponseEntity<UserDto> uploadAvatar(@AuthenticationPrincipal MongoUserDetails principal,
                                                 @RequestParam("file") MultipartFile multipartFile) throws Exception {
        log.info("'uploadAvatar' invoked. File name '{}'", multipartFile.getOriginalFilename());

        UserDto result = userService.setAvatar(principal.getUserId(), multipartFile);
        log.info("'result={}'", result);

        return ResponseEntity.ok(result);
    }
}
