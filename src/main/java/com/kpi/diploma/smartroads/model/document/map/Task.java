package com.kpi.diploma.smartroads.model.document.map;

import com.kpi.diploma.smartroads.model.util.title.value.ContainerValues;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
public class Task {

    @Id
    private String id;

    private List<TaskPoint> points;

    private String companyId;

    private String executorId;

    private String containerValue;

    private boolean isActive;
}
