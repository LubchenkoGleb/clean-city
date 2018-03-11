package com.kpi.diploma.smartroads.model.util.data;

import com.kpi.diploma.smartroads.model.util.title.value.ContainerValues;
import lombok.Data;

@Data
public class MapObjectDetail {

    private Integer amount;

    private ContainerValues type;

    private boolean isFull;
}
