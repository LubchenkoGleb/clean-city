package com.kpi.diploma.smartroads.model.document.map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kpi.diploma.smartroads.model.document.user.User;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Data
@ToString(exclude = "owner")
public class MapObject {

    @Id
    private String id;

    private Double lat;

    private Double lon;

    private String description;
    @DBRef
    @JsonIgnore
    private User owner;
}
