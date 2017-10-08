package com.kpi.diploma.smartroads.model.dto;

import com.kpi.diploma.smartroads.model.document.user.Driver;
import com.kpi.diploma.smartroads.service.util.validation.PasswordMatches;
import lombok.Data;

@Data
@PasswordMatches
public class RegistrationDriverDto {

    private String email;

    private String inviteKey;

    private String password;

    private String matchingPassword;

    private String firstName;

    private String lastName;

    public Driver convertToEntity() {
        Driver driver = new Driver();
        driver.setEmail(this.email);
        driver.setFirstName(this.firstName);
        driver.setLastName(this.lastName);
        driver.setInviteKey(this.inviteKey);
        return driver;
    }

    public static RegistrationDriverDto convertFromEntity(Driver driver) {
        RegistrationDriverDto registrationDriverDto = new RegistrationDriverDto();
        registrationDriverDto.setEmail(driver.getEmail());
        registrationDriverDto.setFirstName(driver.getFirstName());
        registrationDriverDto.setLastName(driver.getLastName());
        return registrationDriverDto;
    }
}
