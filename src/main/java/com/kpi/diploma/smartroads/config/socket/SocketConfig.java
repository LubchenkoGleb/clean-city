package com.kpi.diploma.smartroads.config.socket;

import com.kpi.diploma.smartroads.socket.MapObjectUnsecuredSocketHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
public class SocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry
                .addHandler(new MapObjectUnsecuredSocketHandler(), "/socket/map-object/my")
                .setAllowedOrigins("*")
                .withSockJS();
    }
}
