package com.kpi.diploma.smartroads.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.HashSet;
import java.util.Set;

@Data
public class User {

    @Id
    private String id;
    @CreatedDate
    private String createdDate;
    private String email;
    private String password;
    private Boolean enable = false;
//    @DBRef
    private Set<Role> roles;

    public User() {
        this.roles = new HashSet<>();
    }

    public User(String email, String password) {
        this.roles = new HashSet<>();
        this.email = email;
        this.password = password;
    }
}
