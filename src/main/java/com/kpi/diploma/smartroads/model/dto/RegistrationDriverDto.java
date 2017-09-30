package com.kpi.diploma.smartroads.model.dto;

import com.kpi.diploma.smartroads.model.document.user.Driver;
import lombok.Data;

@Data
public class RegistrationDriverDto {

    private String email;

    private String inviteUrl;

    private String password;

    private String firstName;

    private String lastName;

    public Driver convert() {
        Driver driver = new Driver();
        driver.setEmail(this.email);
        return driver;
    }
}
