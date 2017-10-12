package com.kpi.diploma.smartroads.service.primary;

import com.kpi.diploma.smartroads.model.dto.UserDto;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    boolean existByEmail(String email);

    UserDto setAvatar(String userId, MultipartFile multipartFile) throws Exception;
}
