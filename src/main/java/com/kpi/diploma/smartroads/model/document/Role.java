package com.kpi.diploma.smartroads.model.document;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Role {

    @Id
    private String id;
    private String role;

    public Role(String role) {
        this.role = role;
    }
}
