package com.kpi.diploma.smartroads.model.util.data.kmeans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder(toBuilder = true)
public class KMeansRow {

    private String id;

    private List<Long> value;
}
