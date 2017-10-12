package com.kpi.diploma.smartroads.model.dto;

import com.kpi.diploma.smartroads.model.document.user.User;
import lombok.Data;

@Data
public class UserDto {

    String email;
    String avatarUrl;

    public static UserDto convert(User user) {
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setAvatarUrl(user.getAvatarUrl());
        return userDto;
    }
}
