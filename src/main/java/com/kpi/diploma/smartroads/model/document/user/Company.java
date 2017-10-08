package com.kpi.diploma.smartroads.model.document.user;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Data
@Document(collection = "user")
public class Company extends User {

    private String firstName;
    private String lastName;
    @DBRef
    private Set<Manager> managers;
    @DBRef
    private Set<Driver> drivers;

    public Company() {
        super();
        this.managers = new HashSet<>();
        this.drivers = new HashSet<>();
    }
}
