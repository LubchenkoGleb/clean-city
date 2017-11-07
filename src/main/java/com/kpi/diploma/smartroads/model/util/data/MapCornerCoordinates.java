package com.kpi.diploma.smartroads.model.util.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kpi.diploma.smartroads.model.util.exception.IncorrectInputDataException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@Data
public class MapCornerCoordinates {

    protected static final ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
    }

    private Node northWest;
    private Node southEast;

    public static MapCornerCoordinates convert(String json) {
        try {
            MapCornerCoordinates mapCornerCoordinates = mapper.readValue(json, MapCornerCoordinates.class);
            return mapCornerCoordinates;
        } catch (IOException e) {
            throw new IncorrectInputDataException("json can't be parsed");
        }
    }
}
