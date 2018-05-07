package com.kpi.diploma.smartroads.model.document.map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kpi.diploma.smartroads.model.document.user.User;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString(exclude = {"owner", "startRoutes"})
public class MapObject {

    @Id
    private String id;

    private Double lat;

    private Double lon;

    private String description;

    @DBRef(lazy = true)
    @JsonIgnore
    private User owner;

    @DBRef(lazy = true)
    @JsonIgnore
    private List<Route> startRoutes;

    public MapObject() {
        this.startRoutes = new ArrayList<>();
    }

    public MapObject(String id) {
        this();
        this.id = id;
    }
}
