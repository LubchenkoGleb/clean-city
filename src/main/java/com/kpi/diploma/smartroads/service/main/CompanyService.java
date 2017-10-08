package com.kpi.diploma.smartroads.service.main;

import com.kpi.diploma.smartroads.model.dto.RegistrationDriverDto;
import com.kpi.diploma.smartroads.model.dto.RegistrationManagerDto;

import javax.mail.MessagingException;

public interface CompanyService {

    RegistrationDriverDto createDriver(String userName, RegistrationDriverDto driver) throws MessagingException;

    RegistrationManagerDto createManager(String userName, RegistrationManagerDto manager) throws MessagingException;
}
