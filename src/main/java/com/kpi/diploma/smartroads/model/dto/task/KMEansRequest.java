package com.kpi.diploma.smartroads.model.dto.task;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class KMEansRequest {

    private Integer clusters;

    private List<KMeansRow> matrix;
}
