package com.kpi.diploma.smartroads.model.document.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kpi.diploma.smartroads.model.document.map.MapObject;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Data
@Document(collection = "user")
@ToString(exclude = {"managers", "drivers", "start", "finish"}, callSuper = true)
@EqualsAndHashCode(exclude = {"managers", "drivers", "start", "finish"}, callSuper = true)
public class Company extends User {

    private String title;

    @DBRef(lazy = true)
    @JsonIgnore
    private Set<Manager> managers;

    @DBRef(lazy = true)
    @JsonIgnore
    private Set<Driver> drivers;

    @DBRef(lazy = true)
    @JsonIgnore
    private MapObject start;

    @DBRef(lazy = true)
    @JsonIgnore
    private MapObject finish;

    public Company() {
        super();
        this.managers = new HashSet<>();
        this.drivers = new HashSet<>();
    }
}
