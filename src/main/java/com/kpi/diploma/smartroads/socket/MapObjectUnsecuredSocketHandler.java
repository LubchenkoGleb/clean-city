package com.kpi.diploma.smartroads.socket;

import com.kpi.diploma.smartroads.model.document.map.MapObject;
import com.kpi.diploma.smartroads.model.document.user.User;
import com.kpi.diploma.smartroads.model.dto.map.MapObjectDto;
import com.kpi.diploma.smartroads.model.util.data.MapCornerCoordinates;
import com.kpi.diploma.smartroads.model.util.title.value.RoleValues;
import com.kpi.diploma.smartroads.repository.map.MapObjectRepository;
import com.kpi.diploma.smartroads.repository.user.DriverRepository;
import com.kpi.diploma.smartroads.repository.user.ManagerRepository;
import com.kpi.diploma.smartroads.repository.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.security.Principal;
import java.util.List;

@Slf4j
public class MapObjectUnsecuredSocketHandler extends TextWebSocketHandler {

    private final UserRepository userRepository;
    private final MapObjectRepository mapObjectRepository;
    private final ManagerRepository managerRepository;
    private final DriverRepository driverRepository;

    public MapObjectUnsecuredSocketHandler(UserRepository userRepository, MapObjectRepository mapObjectRepository,
                                           ManagerRepository managerRepository, DriverRepository driverRepository) {
        this.userRepository = userRepository;
        this.mapObjectRepository = mapObjectRepository;
        this.managerRepository = managerRepository;
        this.driverRepository = driverRepository;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        log.info("Opened new session");
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        MapCornerCoordinates converted = MapCornerCoordinates.convert(message.getPayload());

        Principal principal = session.getPrincipal();
        if (principal != null && principal.getName() != null && !principal.getName().isEmpty()) {
            User user = userRepository.findByEmail(principal.getName());

            String companyId = "";

            if (user.hasRole(RoleValues.COMPANY)) {
                companyId = user.getId();
            } else if (user.hasRole(RoleValues.DRIVER)) {
                companyId = driverRepository.findByEmail(user.getEmail()).getBoss().getId();
            } else if (user.hasRole(RoleValues.MANAGER)) {
                companyId = managerRepository.findByEmail(user.getEmail()).getBoss().getId();
            }

            if (!companyId.isEmpty()) {
                List<MapObject> mapObjects = mapObjectRepository.findByOwnerIdAndLatBetweenAndLonBetween(
                        companyId,
                        converted.getSouthEast().getLat(),
                        converted.getNorthWest().getLat(),
                        converted.getNorthWest().getLon(),
                        converted.getSouthEast().getLon());
                session.sendMessage(new TextMessage(MapObjectDto.convert(mapObjects)));
                return;
            }
        }

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