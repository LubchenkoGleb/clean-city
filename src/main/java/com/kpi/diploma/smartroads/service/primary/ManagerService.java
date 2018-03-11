package com.kpi.diploma.smartroads.service.primary;

import com.kpi.diploma.smartroads.model.dto.user.ManagerDto;
import com.kpi.diploma.smartroads.model.dto.user.RegistrationManagerDto;
import com.kpi.diploma.smartroads.model.util.title.value.ContainerValues;

public interface ManagerService {

    ManagerDto registerManager(RegistrationManagerDto registrationManagerDto);

    void createJobRequest(String managerId, String mapObjectId, ContainerValues containerValue);

}
