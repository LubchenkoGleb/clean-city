package com.kpi.diploma.smartroads.model.dto.map;

import com.kpi.diploma.smartroads.model.document.map.Container;
import com.kpi.diploma.smartroads.model.util.data.MapObjectDetail;
import com.kpi.diploma.smartroads.service.util.ConversionService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Data
@Slf4j
public class ContainerDto extends MapObjectDto {

    private List<MapObjectDetail> details;

    public static ContainerDto convertContainer(Container container) {
        log.info("'convert' invoked with params'{}'", container);

        ContainerDto containerDto = ConversionService.convertToObject(container, ContainerDto.class);
        log.info("'containerDto={}'", containerDto);

        return containerDto;
    }

    public static Container convertContainer(ContainerDto containerDto) {
        log.info("'convert' invoked with params'{}'", containerDto);

        Container container = ConversionService.convertToObject(containerDto, Container.class);
        log.info("'container={}'", container);

        return container;
    }
}
