package com.kpi.diploma.smartroads.model.dto;

import com.kpi.diploma.smartroads.model.document.user.Driver;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class DriverDto extends UserDto {

    String firstName;
    String lastName;

    public static DriverDto convertToDriverDto(Driver driver) {
        log.info("'convert' invoked with params'{}'", driver);
        DriverDto driverDto = new DriverDto();
        driverDto.setFirstName(driver.getFirstName());
        driverDto.setLastName(driver.getLastName());
        driverDto.setEmail(driver.getEmail());
        driverDto.setAvatarUrl(driver.getAvatarUrl());
        return driverDto;
    }
}
