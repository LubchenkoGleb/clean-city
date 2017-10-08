package com.kpi.diploma.smartroads.model.document;

import com.kpi.diploma.smartroads.model.document.user.User;
import lombok.Data;

@Data
public class MapObject {

    private Double lat;

    private Double lon;

    private String imageUrl;

    private User creator;
}
