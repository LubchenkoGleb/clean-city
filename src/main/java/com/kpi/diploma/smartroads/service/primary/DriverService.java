package com.kpi.diploma.smartroads.service.primary;

import com.kpi.diploma.smartroads.model.dto.user.DriverDto;
import com.kpi.diploma.smartroads.model.dto.user.RegistrationDriverDto;

public interface DriverService {
    DriverDto registerDriver(RegistrationDriverDto registrationDriverDto);
    Boolean sendCoordinates();
    Boolean startTask(Long taskId);
    Boolean startJob(Long jobId);
    Boolean finishTask(Long taskId);
    Boolean finishJod(Long jobId);
}
