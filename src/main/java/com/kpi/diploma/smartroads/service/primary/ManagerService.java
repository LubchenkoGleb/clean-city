package com.kpi.diploma.smartroads.service.primary;

import com.kpi.diploma.smartroads.model.dto.ManagerDto;
import com.kpi.diploma.smartroads.model.dto.RegistrationManagerDto;

public interface ManagerService {

    ManagerDto registerManager(RegistrationManagerDto registrationManagerDto);
}
