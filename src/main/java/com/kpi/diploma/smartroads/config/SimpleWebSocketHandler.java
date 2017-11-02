package com.kpi.diploma.smartroads.config;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
public class SimpleWebSocketHandler extends TextWebSocketHandler {


//    private final EchoService echoService;
//
//    public EchoWebSocketHandler(EchoService echoService) {
//        this.echoService = echoService;
//    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        log.info("Opened new session in instance " + this);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message)
            throws Exception {
        log.info(message.getPayload());
        session.sendMessage(new TextMessage("server loves you"));
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception)
            throws Exception {
        session.close(CloseStatus.SERVER_ERROR);
    }
}