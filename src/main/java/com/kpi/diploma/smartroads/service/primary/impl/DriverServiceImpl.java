package com.kpi.diploma.smartroads.service.primary.impl;

import com.kpi.diploma.smartroads.model.document.user.Driver;
import com.kpi.diploma.smartroads.model.dto.user.DriverDto;
import com.kpi.diploma.smartroads.model.dto.user.RegistrationDriverDto;
import com.kpi.diploma.smartroads.model.util.exception.IncorrectInviteKey;
import com.kpi.diploma.smartroads.repository.user.DriverRepository;
import com.kpi.diploma.smartroads.service.primary.DriverService;
import com.kpi.diploma.smartroads.service.util.security.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class DriverServiceImpl implements DriverService {

    private final DriverRepository driverRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public DriverServiceImpl(DriverRepository driverRepository,
                             PasswordEncoder passwordEncoder,
                             TokenService tokenService) {
        this.driverRepository = driverRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    @Override
    @Transactional
    public DriverDto registerDriver(RegistrationDriverDto registrationDriverDto) {
        log.info("'registerManager' invoked with params'{}'", registrationDriverDto);

        Driver driverEntity = driverRepository.findByInviteKey(registrationDriverDto.getInviteKey());

        if (driverEntity == null || !driverEntity.getEmail().equals(registrationDriverDto.getEmail())) {
            String errMsg = "inviteKey not found or doesn't belong to email";
            log.error(errMsg);
            throw new IncorrectInviteKey(errMsg);
        }

        driverEntity.setEnable(true);
        driverEntity.setPassword(passwordEncoder.encode(registrationDriverDto.getPassword()));
        driverEntity.setFirstName(registrationDriverDto.getFirstName());
        driverEntity.setLastName(registrationDriverDto.getLastName());
        driverEntity = driverRepository.save(driverEntity);
        log.info("'driverEntity={}'", driverEntity);

        DriverDto driverDto = DriverDto.convert(driverEntity);
        driverDto.setAccessToken(tokenService.getToken(driverDto.getEmail()));
        return driverDto;
    }

    @Override
    public Boolean sendCoordinates() {
        return null;
    }

    @Override
    public Boolean startTask(Long taskId) {
        return null;
    }

    @Override
    public Boolean startJob(Long jobId) {
        return null;
    }

    @Override
    public Boolean finishTask(Long taskId) {
        return null;
    }

    @Override
    public Boolean finishJod(Long jobId) {
        return null;
    }
}
