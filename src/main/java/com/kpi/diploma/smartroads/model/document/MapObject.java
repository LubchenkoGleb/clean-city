package com.kpi.diploma.smartroads.model.document;

import com.kpi.diploma.smartroads.model.document.user.User;
import com.kpi.diploma.smartroads.model.util.data.MapObjectDetail;
import lombok.Data;

import java.util.List;

@Data
public class MapObject {

    private Double lat;

    private Double lon;

    private User owner;

    private List<MapObjectDetail> details;
}
