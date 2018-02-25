package com.kpi.diploma.smartroads.service.primary;

import com.kpi.diploma.smartroads.model.dto.user.DriverDto;
import com.kpi.diploma.smartroads.model.dto.user.ManagerDto;
import com.kpi.diploma.smartroads.model.dto.user.RegistrationDriverDto;
import com.kpi.diploma.smartroads.model.dto.user.RegistrationManagerDto;

import java.util.List;

public interface CompanyService {

    RegistrationDriverDto createDriver(String userName, RegistrationDriverDto driver);

    RegistrationManagerDto createManager(String userName, RegistrationManagerDto manager);

    List<DriverDto> getDrivers(String companyId);

    List<ManagerDto> getManagers(String companyId);
}
