package com.kpi.diploma.smartroads.socket;

import com.kpi.diploma.smartroads.model.document.MapObject;
import com.kpi.diploma.smartroads.model.dto.MapObjectDto;
import com.kpi.diploma.smartroads.model.util.data.MapCornerCoordinates;
import com.kpi.diploma.smartroads.repository.MapObjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.List;

@Slf4j
public class MapObjectUnsecuredSocketHandler extends TextWebSocketHandler {

    private final MapObjectRepository mapObjectRepository;

    public MapObjectUnsecuredSocketHandler(MapObjectRepository mapObjectRepository) {
        this.mapObjectRepository = mapObjectRepository;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        log.info("Opened new session");
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message)
            throws Exception {
        log.info("'handleTextMessage' invoked with params'{}'", message.getPayload());

        MapCornerCoordinates converted = MapCornerCoordinates.convert(message.getPayload());
        List<MapObject> mapObjects = mapObjectRepository.findByLatBetweenAndLonBetween(
                converted.getSouthEast().getLat(),
                converted.getNorthWest().getLat(),
                converted.getNorthWest().getLon(),
                converted.getSouthEast().getLon());

        session.sendMessage(new TextMessage(MapObjectDto.convert(mapObjects)));
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        session.close(CloseStatus.SERVER_ERROR);
    }
}