package com.kpi.diploma.smartroads.service.primary;

import com.kpi.diploma.smartroads.model.dto.map.MapObjectDto;
import com.kpi.diploma.smartroads.model.dto.user.*;

import java.util.List;

public interface CompanyService {

    RegistrationDriverDto createDriver(String userName, RegistrationDriverDto driver);

    RegistrationManagerDto createManager(String userName, RegistrationManagerDto manager);

    void deleteDriver(String driverId, String companyId);

    void deleteManager(String managerId, String companyId);

    List<DriverDto> getDrivers(String companyId);

    List<ManagerDto> getManagers(String companyId);

    MapObjectDto setEndpoint(String marker, MapObjectDto mapObjectDto, String companyId);

    void deleteEndpoint(String mapObjectId, String companyId);

    List<CompanyDto> getCompanies();
}
