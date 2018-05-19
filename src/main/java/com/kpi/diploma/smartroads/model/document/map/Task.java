package com.kpi.diploma.smartroads.model.document.map;

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

    private boolean active;

    private boolean assigned;
}
