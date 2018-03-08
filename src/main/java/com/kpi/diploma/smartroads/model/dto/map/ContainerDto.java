package com.kpi.diploma.smartroads.model.dto.map;

import com.kpi.diploma.smartroads.model.util.data.MapObjectDetail;
import lombok.Data;

import java.util.List;

@Data
public class ContainerDto extends MapObjectDto {

    private List<MapObjectDetail> details;

}
