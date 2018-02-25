package com.kpi.diploma.smartroads.service.primary.impl;

import com.kpi.diploma.smartroads.model.document.user.Manager;
import com.kpi.diploma.smartroads.model.dto.user.ManagerDto;
import com.kpi.diploma.smartroads.model.dto.user.RegistrationManagerDto;
import com.kpi.diploma.smartroads.model.util.exception.IncorrectInviteKey;
import com.kpi.diploma.smartroads.repository.ManagerRepository;
import com.kpi.diploma.smartroads.service.primary.ManagerService;
import com.kpi.diploma.smartroads.service.util.security.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ManagerServiceImpl implements ManagerService {

    private final ManagerRepository managerRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @Autowired
    public ManagerServiceImpl(ManagerRepository managerRepository,
                              PasswordEncoder passwordEncoder,
                              TokenService tokenService) {
        this.managerRepository = managerRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    @Override
    public ManagerDto registerManager(RegistrationManagerDto registrationManagerDto) {
        log.info("'registerManager' invoked with params'{}'", registrationManagerDto);

        Manager managerEntity = managerRepository.findByInviteKey(registrationManagerDto.getInviteKey());

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

        ManagerDto managerDto = ManagerDto.convert(managerEntity);
        managerDto.setAccessToken(tokenService.getToken(managerDto.getEmail()));
        return managerDto;
    }
}
