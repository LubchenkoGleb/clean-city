package com.kpi.diploma.smartroads.model.dto;

import com.kpi.diploma.smartroads.model.document.user.Manager;
import lombok.Data;

@Data
public class RegistrationManagerDto {

    private String email;

    private String inviteUrl;

    private String password;

    private String firstName;

    private String lastName;

    public Manager convert() {
        Manager manager = new Manager();
        manager.setEmail(this.email);
        return manager;
    }
}
