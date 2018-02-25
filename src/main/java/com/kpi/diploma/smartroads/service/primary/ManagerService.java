package com.kpi.diploma.smartroads.service.primary;

import com.kpi.diploma.smartroads.model.dto.user.ManagerDto;
import com.kpi.diploma.smartroads.model.dto.user.RegistrationManagerDto;

public interface ManagerService {

    ManagerDto registerManager(RegistrationManagerDto registrationManagerDto);
}
