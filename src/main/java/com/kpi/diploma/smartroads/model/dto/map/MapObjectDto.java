package com.kpi.diploma.smartroads.model.dto.map;

import com.kpi.diploma.smartroads.model.document.map.MapObject;
import com.kpi.diploma.smartroads.model.dto.user.UserDto;
import com.kpi.diploma.smartroads.model.util.data.MapObjectDetail;
import com.kpi.diploma.smartroads.service.util.ConversionService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Slf4j
public class MapObjectDto {

    private String id;

    @NotNull
    private Double lat;

    @NotNull
    private Double lon;

    private String description;

    private UserDto owner;

    public static MapObjectDto convert(MapObject mapObject) {
        log.info("'convert' invoked with params'{}'", mapObject);

        MapObjectDto mapObjectDto = ConversionService.convertToObject(mapObject, MapObjectDto.class);
        log.info("'mapObjectDto={}'", mapObjectDto);

        return mapObjectDto;
    }

    public static MapObject convert(MapObjectDto mapObjectDto) {
        log.info("'convert' invoked with params'{}'", mapObjectDto);

        MapObject mapObject = ConversionService.convertToObject(mapObjectDto, MapObject.class);
        log.info("'mapObjectDto={}'", mapObjectDto);

        return mapObject;
    }

    public static String convert(List<MapObject> mapObjects) {
        log.info("'convert' invoked with params'{}'", mapObjects);

        String converted = ConversionService.convertToJsonString(mapObjects);
        log.info("'converted={}'", converted);

        return converted;
    }
}
