package com.kpi.diploma.smartroads.model.dto;

import com.kpi.diploma.smartroads.model.document.MapObject;
import lombok.Data;

@Data
public class MapObjectDto {

    private Double lat;

    private Double lon;

    private String imageUrl;

    public MapObject convert() {
        MapObject mapObject = new MapObject();
        mapObject.setLat(lat);
        mapObject.setLon(lon);
        mapObject.setImageUrl(imageUrl);
        return mapObject;
    }
}
