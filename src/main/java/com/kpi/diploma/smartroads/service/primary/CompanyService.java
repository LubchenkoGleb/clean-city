package com.kpi.diploma.smartroads.service.primary;

import com.kpi.diploma.smartroads.model.dto.DriverDto;
import com.kpi.diploma.smartroads.model.dto.ManagerDto;
import com.kpi.diploma.smartroads.model.dto.RegistrationDriverDto;
import com.kpi.diploma.smartroads.model.dto.RegistrationManagerDto;

import javax.mail.MessagingException;
import java.util.List;

public interface CompanyService {

    RegistrationDriverDto createDriver(String userName, RegistrationDriverDto driver) throws MessagingException;

    RegistrationManagerDto createManager(String userName, RegistrationManagerDto manager) throws MessagingException;

    List<DriverDto> getDrivers(String companyId);

    List<ManagerDto> getManagers(String companyId);
}
