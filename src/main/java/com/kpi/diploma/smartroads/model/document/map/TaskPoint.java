package com.kpi.diploma.smartroads.model.document.map;

import com.kpi.diploma.smartroads.model.util.title.value.MapObjectDescriptionValues;
import lombok.Data;

@Data
public class TaskPoint {

    private Route route;

    private Integer amount;

    private MapObjectDescriptionValues type;
}
