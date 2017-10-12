package com.kpi.diploma.smartroads.service.primary;

import com.kpi.diploma.smartroads.model.dto.RegistrationDriverDto;

public interface DriverService {
    RegistrationDriverDto registerDriver(RegistrationDriverDto registrationDriverDto);
    Boolean sendCoordinates();
    Boolean startTask(Long taskId);
    Boolean startJob(Long jobId);
    Boolean finishTask(Long taskId);
    Boolean finishJod(Long jobId);
}
