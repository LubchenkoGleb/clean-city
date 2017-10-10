package com.kpi.diploma.smartroads.model.dto;

import com.kpi.diploma.smartroads.model.document.user.Driver;
import lombok.Data;

@Data
public class DriverDto {

    String firstName;
    String lastName;
    String email;

    public static DriverDto convert(Driver driver) {
        DriverDto driverDto = new DriverDto();
        driverDto.setEmail(driver.getEmail());
        driverDto.setFirstName(driver.getFirstName());
        driverDto.setLastName(driver.getLastName());
        return driverDto;
    }
}
