package com.kpi.diploma.smartroads.model.document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kpi.diploma.smartroads.model.document.user.User;
import com.kpi.diploma.smartroads.model.util.data.MapObjectDetail;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString(exclude = "owner")
public class MapObject {

    @Id
    private String id;

    private Double lat;

    private Double lon;

    @DBRef
    @JsonIgnore
    private User owner;

    private List<MapObjectDetail> details;

    public MapObject() {
        details = new ArrayList<>();
    }
}
