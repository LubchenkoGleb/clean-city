package com.kpi.diploma.smartroads.model.dto;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kpi.diploma.smartroads.model.document.user.User;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class UserDto {

    private String email;
    private String avatarUrl;
    protected static final ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static UserDto convert(Object object) {
        log.info("'convert' invoked with params'{}'", object);

        UserDto userDto = mapper.convertValue(object, UserDto.class);
        log.info("'userDto={}'", userDto);

        return userDto;
    }
}
