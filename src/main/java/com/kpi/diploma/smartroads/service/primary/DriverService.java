package com.kpi.diploma.smartroads.service.primary;

import com.fasterxml.jackson.databind.JsonNode;
import com.kpi.diploma.smartroads.model.document.map.Task;
import com.kpi.diploma.smartroads.model.dto.user.DriverDto;
import com.kpi.diploma.smartroads.model.dto.user.RegistrationDriverDto;

public interface DriverService {

    DriverDto registerDriver(RegistrationDriverDto registrationDriverDto);

    Task applyTask(String driverId);

    JsonNode getActiveTask(String driverId);

    Task finishTask(String driverId);

}
