package com.kpi.diploma.smartroads.model.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.security.core.GrantedAuthority;

@Data
public class Role {

    @Id
    private String id;
    private String role;

    public Role(String role) {
        this.role = role;
    }
}
