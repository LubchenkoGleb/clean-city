package com.kpi.diploma.smartroads.model.document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kpi.diploma.smartroads.validation.PasswordMatches;
import com.kpi.diploma.smartroads.validation.ValidateEmail;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Data
@PasswordMatches
public class User {

    @Id
    private String id;

    @NotNull @NotEmpty
    @ValidateEmail
    private String email;

//    @JsonIgnore
    @NotNull @NotEmpty
    private String password;
    private String matchingPassword;

    private Boolean enable = false;

    @CreatedDate
    private String createdDate;

//    @DBRef
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
