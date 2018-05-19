package com.kpi.diploma.smartroads.config.socket;

import com.kpi.diploma.smartroads.repository.map.MapObjectRepository;
import com.kpi.diploma.smartroads.repository.user.DriverRepository;
import com.kpi.diploma.smartroads.repository.user.ManagerRepository;
import com.kpi.diploma.smartroads.repository.user.UserRepository;
import com.kpi.diploma.smartroads.socket.MapObjectUnsecuredSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
public class SocketConfig implements WebSocketConfigurer {

    private final MapObjectRepository mapObjectRepository;

    private final UserRepository userRepository;

    private final ManagerRepository managerRepository;

    private final DriverRepository driverRepository;

    @Autowired
    public SocketConfig(MapObjectRepository mapObjectRepository, UserRepository userRepository,
                        ManagerRepository managerRepository, DriverRepository driverRepository) {
        this.mapObjectRepository = mapObjectRepository;
        this.userRepository = userRepository;
        this.managerRepository = managerRepository;
        this.driverRepository = driverRepository;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {

        registry
                .addHandler(new MapObjectUnsecuredSocketHandler(
                        userRepository, mapObjectRepository, managerRepository, driverRepository),
                        "/socket/map-object/all")
                .setAllowedOrigins("*")
                .withSockJS();
    }
}
