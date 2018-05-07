package com.kpi.diploma.smartroads.model.util.data;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GoogleRoute {

    private Long length;

    private String encodedRoute;
}
