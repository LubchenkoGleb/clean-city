package com.kpi.diploma.smartroads.model.dto.user;

import com.kpi.diploma.smartroads.service.util.ConversionService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class UserDto {

    private String email;
    private String avatarUrl;

    public static UserDto convert(Object object) {
        log.info("'convert' invoked with params'{}'", object);

        UserDto userDto = ConversionService.convertToObject(object, UserDto.class);
        log.info("'userDto={}'", userDto);

        return userDto;
    }
}
