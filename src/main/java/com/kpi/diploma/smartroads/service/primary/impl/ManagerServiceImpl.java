package com.kpi.diploma.smartroads.service.primary.impl;

import com.kpi.diploma.smartroads.model.document.map.Container;
import com.kpi.diploma.smartroads.model.document.user.Manager;
import com.kpi.diploma.smartroads.model.dto.user.ManagerDto;
import com.kpi.diploma.smartroads.model.dto.user.RegistrationManagerDto;
import com.kpi.diploma.smartroads.model.util.data.MapObjectDetail;
import com.kpi.diploma.smartroads.model.util.exception.IncorrectInviteKey;
import com.kpi.diploma.smartroads.model.util.exception.ResourceNotFoundException;
import com.kpi.diploma.smartroads.model.util.exception.ResourseDoesntBelongToUserException;
import com.kpi.diploma.smartroads.model.util.title.value.ContainerValues;
import com.kpi.diploma.smartroads.repository.ManagerRepository;
import com.kpi.diploma.smartroads.repository.MapObjectRepository;
import com.kpi.diploma.smartroads.service.primary.ManagerService;
import com.kpi.diploma.smartroads.service.util.security.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class ManagerServiceImpl implements ManagerService {

    private final MapObjectRepository mapObjectRepository;
    private final ManagerRepository managerRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @Autowired
    public ManagerServiceImpl(MapObjectRepository mapObjectRepository,
                              ManagerRepository managerRepository,
                              PasswordEncoder passwordEncoder,
                              TokenService tokenService) {
        this.mapObjectRepository = mapObjectRepository;
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

    @Override
    public void createJobRequest(String managerId, String mapObjectId, ContainerValues containerValue) {
        log.info("'createJobRequest' invoked with params'{}, {}, {}'", managerId, mapObjectId, containerValue);

        Manager manager = managerRepository.findOne(managerId);
        log.info("manager boss = " + manager.getBoss() + "");

        Container container = (Container) mapObjectRepository.findOne(mapObjectId);
        log.info("container owner = " + container.getOwner() + "");

        if (!manager.getBoss().getId().equals(container.getOwner().getId())) {

            String errorMessage = "container doesn't belong to company where manager works";
            log.error(errorMessage);
            throw new ResourseDoesntBelongToUserException(errorMessage);

        }

        Optional<MapObjectDetail> optional = container.getDetails().stream()
                .filter(c -> c.getType().equals(containerValue))
                .findAny();

        if (!optional.isPresent()) {

            String errorMessage = "incorrect container type for this map object";
            log.error(errorMessage);
            throw new ResourceNotFoundException(errorMessage);

        }

        optional.get().setFull(true);
        mapObjectRepository.save(container);
        log.info("saved successfully");
    }
}
