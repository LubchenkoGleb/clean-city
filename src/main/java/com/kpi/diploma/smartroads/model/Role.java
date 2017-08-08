package com.kpi.diploma.smartroads.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Role {

    @Id
    private String id;
    private String role;

    public Role() {
    }

    public Role(String role) {
        this.role = role;
    }
}
