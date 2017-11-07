package com.kpi.diploma.smartroads.config.socket;

import com.kpi.diploma.smartroads.repository.MapObjectRepository;
import com.kpi.diploma.smartroads.socket.MapObjectSecuredSocketHandler;
import com.kpi.diploma.smartroads.socket.MapObjectUnsecuredSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
public class SocketConfig implements WebSocketConfigurer {

    private final MapObjectRepository mapObjectRepository;

    @Autowired
    public SocketConfig(MapObjectRepository mapObjectRepository) {
        this.mapObjectRepository = mapObjectRepository;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry
                .addHandler(new MapObjectSecuredSocketHandler(), "/socket/map-object/my")
                .setAllowedOrigins("*")
                .withSockJS();

        registry
                .addHandler(new MapObjectUnsecuredSocketHandler(mapObjectRepository), "/socket/map-object/all")
                .setAllowedOrigins("*")
                .withSockJS();
    }
}
