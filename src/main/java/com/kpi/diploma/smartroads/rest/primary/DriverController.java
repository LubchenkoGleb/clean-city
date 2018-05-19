package com.kpi.diploma.smartroads.rest.primary;

import com.fasterxml.jackson.databind.JsonNode;
import com.kpi.diploma.smartroads.model.document.map.Task;
import com.kpi.diploma.smartroads.model.dto.user.DriverDto;
import com.kpi.diploma.smartroads.model.dto.user.RegistrationDriverDto;
import com.kpi.diploma.smartroads.model.util.security.MongoUserDetails;
import com.kpi.diploma.smartroads.service.primary.DriverService;
import com.kpi.diploma.smartroads.service.primary.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/driver-requests")
public class DriverController {

    private final DriverService driverService;

    private final TaskService taskService;

    @Autowired
    public DriverController(DriverService driverService, TaskService taskService) {
        this.driverService = driverService;
        this.taskService = taskService;
    }

    @PostMapping(value = "/confirm")
    private ResponseEntity<DriverDto> confirmProfile(@RequestBody RegistrationDriverDto registrationDriverDto) {
        log.info("'confirmProfile' invoked with params'{}'", registrationDriverDto);

        DriverDto driverDto = driverService.registerDriver(registrationDriverDto);
        log.info("'driverDto={}'", driverDto);

        return ResponseEntity.ok(driverDto);
    }

    @PostMapping(value = "/apply-task")
    private ResponseEntity<Task> applyTask(@AuthenticationPrincipal MongoUserDetails principal) {
        log.info("'acceptTask' params'{}'", principal.getUsername());

        Task task = driverService.applyTask(principal.getUserId());
        log.info("'task={}'", task);

        return ResponseEntity.ok(task);
    }

    @GetMapping(value = "/get-task")
    private ResponseEntity<JsonNode> getTask(@AuthenticationPrincipal MongoUserDetails principal) {
        log.info("'getTask' params'{}'", principal.getUsername());

        return ResponseEntity.ok(driverService.getActiveTask(principal.getUserId()));
    }

    @PostMapping(value = "/finish-task")
    private ResponseEntity<Task> finishTask(@AuthenticationPrincipal MongoUserDetails principal) {
        log.info("'finishTask' params'{}'", principal.getUsername());

        return ResponseEntity.ok(driverService.finishTask(principal.getUserId()));
    }
}
