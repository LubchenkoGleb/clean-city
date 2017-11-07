package com.kpi.diploma.smartroads.model.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kpi.diploma.smartroads.model.document.MapObject;
import com.kpi.diploma.smartroads.model.document.user.User;
import com.kpi.diploma.smartroads.model.util.data.MapObjectDetail;
import com.kpi.diploma.smartroads.model.util.exception.IncorrectInputDataException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Data
@Slf4j
public class MapObjectDto {

    private Double lat;
    private Double lon;
//    private User owner;
    private List<MapObjectDetail> details;
    protected static final ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static MapObjectDto convert(MapObject mapObject) {
        log.info("'convert' invoked with params'{}'", mapObject);

        MapObjectDto mapObjectDto = mapper.convertValue(mapObject, MapObjectDto.class);
        log.info("'mapObjectDto={}'", mapObjectDto);

        return mapObjectDto;
    }

    public static MapObject convert(MapObjectDto mapObjectDto) {
        log.info("'convert' invoked with params'{}'", mapObjectDto);

        MapObject mapObject = mapper.convertValue(mapObjectDto, MapObject.class);
        log.info("'mapObjectDto={}'", mapObjectDto);

        return mapObject;
    }

    public static String convert(List<MapObject> mapObjects) {
        log.info("'convert' invoked with params'{}'", mapObjects);

        try {
            String mapObjectsJsonString = mapper.writeValueAsString(mapObjects);
            return mapObjectsJsonString;
        } catch (JsonProcessingException e) {
            log.error("incorrect input json exception. Message='{}'", e.getMessage());
            e.printStackTrace();
            throw  new IncorrectInputDataException("incorrect input json exception");
        }
    }
}
