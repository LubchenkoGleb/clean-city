package com.kpi.diploma.smartroads.service.main;

import com.kpi.diploma.smartroads.model.dto.RegistrationDriverDto;
import com.kpi.diploma.smartroads.model.dto.RegistrationManagerDto;

public interface CompanyService {

    RegistrationDriverDto createDriver(String userName, RegistrationDriverDto driver);

    RegistrationManagerDto createManager(String userName, RegistrationManagerDto manager);
}
