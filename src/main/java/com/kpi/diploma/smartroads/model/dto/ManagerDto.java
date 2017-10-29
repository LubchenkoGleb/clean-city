package com.kpi.diploma.smartroads.model.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kpi.diploma.smartroads.model.document.user.Manager;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class ManagerDto extends UserDto {
    String firstName;
    String lastName;

    public static ManagerDto convert(Object object) {
        log.info("'convert' invoked with params'{}'", object);

        ManagerDto managerDto = mapper.convertValue(object, ManagerDto.class);
        log.info("'managerDto={}'", managerDto);

        return managerDto;
    }
}
