package com.kpi.diploma.smartroads.service.main.impl;

import com.kpi.diploma.smartroads.model.document.user.Manager;
import com.kpi.diploma.smartroads.model.dto.RegistrationManagerDto;
import com.kpi.diploma.smartroads.model.exception.IncorrectInviteKey;
import com.kpi.diploma.smartroads.repository.ManagerRepository;
import com.kpi.diploma.smartroads.service.main.ManagerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ManagerServiceImpl implements ManagerService {

    private final ManagerRepository managerRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ManagerServiceImpl(ManagerRepository managerRepository, PasswordEncoder passwordEncoder) {
        this.managerRepository = managerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public RegistrationManagerDto registerManager(RegistrationManagerDto registrationManagerDto) {
        log.info("'registerManager' invoked with params'{}'", registrationManagerDto);

        Manager managerEntity = managerRepository.findByInviteKey(registrationManagerDto.getInviteUrl());

        if (managerEntity == null || !managerEntity.getEmail().equals(registrationManagerDto.getEmail())) {
            String errMsg = "inviteKey not found or doesn't belong to email";
            log.error(errMsg);
            throw new IncorrectInviteKey(errMsg);
        }

        managerEntity.setEnable(true);
        managerEntity.setPassword(passwordEncoder.encode(registrationManagerDto.getPassword()));
        managerEntity.setFirstName(registrationManagerDto.getFirstName());
        managerEntity.setLastName(registrationManagerDto.getLastName());
        managerEntity = managerRepository.save(managerEntity);
        log.info("'managerEntity={}'", managerEntity);

        return registrationManagerDto;
    }
}
