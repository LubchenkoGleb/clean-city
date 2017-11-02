package com.kpi.diploma.smartroads.model.document.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Data
@Document(collection = "user")
@ToString(exclude = {"managers", "drivers"})
@EqualsAndHashCode(exclude = {"managers", "drivers"})
public class Company extends User {

    private String firstName;

    private String lastName;

    @DBRef
    @JsonIgnore
    private Set<Manager> managers;

    @DBRef
    @JsonIgnore
    private Set<Driver> drivers;

    public Company() {
        super();
        this.managers = new HashSet<>();
        this.drivers = new HashSet<>();
    }
}
