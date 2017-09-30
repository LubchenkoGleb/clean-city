package com.kpi.diploma.smartroads.model.document.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kpi.diploma.smartroads.model.document.Role;
import com.kpi.diploma.smartroads.service.validation.PasswordMatches;
import com.kpi.diploma.smartroads.service.validation.ValidateEmail;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Data
@PasswordMatches
public class User {

    @Id
    private String id;

    @NotNull
    @NotEmpty
    @ValidateEmail
    private String email;

    @JsonIgnore
    @NotNull
    @NotEmpty
    private String password;

    private String matchingPassword;

    private Boolean enable = false;

    @CreatedDate
    private String createdDate;

    private Set<Role> roles;

    public User() {
        roles = new HashSet<>();
    }

    public User(String email, String password) {
        this();
        this.email = email;
        this.password = password;
    }
}
