package com.kpi.diploma.smartroads.service.primary.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.kpi.diploma.smartroads.model.document.map.Container;
import com.kpi.diploma.smartroads.model.document.map.Task;
import com.kpi.diploma.smartroads.model.document.user.Driver;
import com.kpi.diploma.smartroads.model.dto.user.DriverDto;
import com.kpi.diploma.smartroads.model.dto.user.RegistrationDriverDto;
import com.kpi.diploma.smartroads.model.util.data.MapObjectDetail;
import com.kpi.diploma.smartroads.model.util.exception.IncorrectInviteKey;
import com.kpi.diploma.smartroads.model.util.exception.TaskException;
import com.kpi.diploma.smartroads.model.util.title.value.MapObjectDescriptionValues;
import com.kpi.diploma.smartroads.repository.map.ContainerRepository;
import com.kpi.diploma.smartroads.repository.map.TaskRepository;
import com.kpi.diploma.smartroads.repository.user.DriverRepository;
import com.kpi.diploma.smartroads.service.primary.DriverService;
import com.kpi.diploma.smartroads.service.util.ConversionService;
import com.kpi.diploma.smartroads.service.util.security.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class DriverServiceImpl implements DriverService {

    private final ContainerRepository containerRepository;
    private final DriverRepository driverRepository;
    private final PasswordEncoder passwordEncoder;
    private final TaskRepository taskRepository;
    private final TokenService tokenService;

    public DriverServiceImpl(ContainerRepository containerRepository,
                             DriverRepository driverRepository,
                             PasswordEncoder passwordEncoder,
                             TaskRepository taskRepository,
                             TokenService tokenService) {
        this.containerRepository = containerRepository;
        this.driverRepository = driverRepository;
        this.passwordEncoder = passwordEncoder;
        this.taskRepository = taskRepository;
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
    public Task applyTask(String driverId) {
        log.info("'applyTask' params'{}'", driverId);

        Driver driver = driverRepository.findOne(driverId);
        String activeTaskId = driver.getActiveTaskId();

        if (activeTaskId != null && !activeTaskId.isEmpty()) {
            throw new TaskException("driver already has active task");
        }

        Task task = taskRepository.findByCompanyIdAndActiveIsTrueAndAssignedIsFalse(driver.getBoss().getId());

        if (task == null) {
            throw new TaskException("Company doesn't have active and free tasks");
        }

        driver.setActiveTaskId(task.getId());
        driverRepository.save(driver);

        task.setAssigned(true);

        return taskRepository.save(task);
    }

    @Override
    public JsonNode getActiveTask(String driverId) {
        log.info("'getActiveTask' params'{}'", driverId);

        Driver driver = driverRepository.findOne(driverId);

        if (driver.getActiveTaskId() == null) {
            return ConversionService.createArrayNode();
        } else {
            return ConversionService.convertToJsonNode(taskRepository.findOne(driver.getActiveTaskId()));
        }
    }

    @Override
    public Task finishTask(String driverId) {
        log.info("'finishTask' params'{}'", driverId);

        Driver driver = driverRepository.findOne(driverId);
        String activeTaskId = driver.getActiveTaskId();

        if (activeTaskId == null || activeTaskId.isEmpty()) {
            throw new TaskException("driver doesn't have has active task");
        }

        Task task = taskRepository.findByCompanyIdAndActiveIsTrueAndAssignedIsFalse(driver.getBoss().getId());

        driver.setActiveTaskId(null);
        task.setActive(false);

        List<String> containerIds = new ArrayList<>();
        task.getPoints().forEach(taskPoint -> {
            if (taskPoint.getType() != MapObjectDescriptionValues.FINISH) {
                containerIds.add(taskPoint.getRoute().getFinish().getId());
            }
        });
        log.debug("'containerIds={}'", containerIds);

        List<Container> containers = containerRepository.findAllByIdIn(containerIds);
        containers.forEach(container -> {

            MapObjectDetail mapObjectDetail = container.getDetails()
                    .stream()
                    .filter(detail -> detail.getType().toString().equals(task.getContainerValue()))
                    .findAny().get();

            mapObjectDetail.setPending(false);
            mapObjectDetail.setFull(false);
        });

        driverRepository.save(driver);
        containerRepository.save(containers);

        return taskRepository.save(task);
    }
}
