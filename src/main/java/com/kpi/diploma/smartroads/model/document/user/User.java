package com.kpi.diploma.smartroads.model.document.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kpi.diploma.smartroads.service.util.validation.PasswordMatches;
import com.kpi.diploma.smartroads.service.util.validation.ValidateEmail;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Data
@PasswordMatches
@ToString(exclude = {"roles"})
@EqualsAndHashCode(exclude = {"roles"})
public class User {

    @Id
    private String id;

    @NotNull
    @NotEmpty
    @ValidateEmail
    @Indexed(unique = true)
    private String email;

    @JsonIgnore
    @NotNull
    @NotEmpty
    private String password;

    private String matchingPassword;

    private Boolean enable = false;

    @CreatedDate
    private String createdDate;

    private String avatarUrl;

    @JsonIgnore
    private Set<Role> roles;

    public User() {
        roles = new HashSet<>();
    }

    public User(String email, String password) {
        this();
        this.email = email;
        this.password = password;
    }

    public boolean hasRole(String role) {
        return this.roles.stream().anyMatch(r -> r.getRole().equals(role));
    }
}
