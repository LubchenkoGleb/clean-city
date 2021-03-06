package com.kpi.diploma.smartroads.model.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kpi.diploma.smartroads.service.util.validation.PasswordMatches;
import lombok.Data;

@Data
@PasswordMatches
public class RegistrationManagerDto {

    private String email;

    private String inviteKey;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String matchingPassword;

    private String firstName;

    private String lastName;
}
