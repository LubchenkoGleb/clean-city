package com.kpi.diploma.smartroads.model.document.map;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Data
@ToString
public class Route {

    @Id
    private String id;

    @DBRef(lazy = true)
    private MapObject start;

    @DBRef(lazy = true)
    private MapObject finish;

    private Long length;

    private String encodedRoute;

    public Route() {
    }

    public Route(MapObject start, MapObject finish, Long length, String encodedRoute) {
        this.start = start;
        this.finish = finish;
        this.length = length;
        this.encodedRoute = encodedRoute;
    }
}
