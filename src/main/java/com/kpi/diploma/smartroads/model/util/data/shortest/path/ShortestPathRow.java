package com.kpi.diploma.smartroads.model.util.data.shortest.path;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ShortestPathRow {

    private String id;

    private List<Long> value;

    private boolean start;

    private boolean finish;

    public ShortestPathRow(String id) {
        this.id = id;
        this.value = new ArrayList<>();
    }
}
