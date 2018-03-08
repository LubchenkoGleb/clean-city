package com.kpi.diploma.smartroads.model.document.map;

import com.kpi.diploma.smartroads.model.util.data.MapObjectDetail;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Container extends MapObject {

    private List<MapObjectDetail> details;

    public Container() {
        details = new ArrayList<>();
    }
}
