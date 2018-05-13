package com.kpi.diploma.smartroads.model.util.data.kmeans;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class KMEansRequest {

    private Integer clusters;

    private List<KMeansRow> matrix;
}
