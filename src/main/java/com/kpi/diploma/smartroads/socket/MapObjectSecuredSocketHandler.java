package com.kpi.diploma.smartroads.socket;

import com.kpi.diploma.smartroads.model.util.data.MapCornerCoordinates;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.security.Principal;

@Slf4j
public class MapObjectSecuredSocketHandler extends TextWebSocketHandler {

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        log.info("Opened new session in instance for user'{}'", session.getPrincipal().getName());
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message)
            throws Exception {
        log.info(message.getPayload());
        Principal principal = session.getPrincipal();

        MapCornerCoordinates converted = MapCornerCoordinates.convert(message.getPayload());

        String responseMessage = "user with name '" + principal.getName()
                + "' is fagot because he asked: " + converted.toString();

        session.sendMessage(new TextMessage(responseMessage));
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        session.close(CloseStatus.SERVER_ERROR);
    }
}