package com.kpi.diploma.smartroads.rest.primary;

import com.kpi.diploma.smartroads.model.document.map.Task;
import com.kpi.diploma.smartroads.service.primary.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/task")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping(value = "/create/{companyId}")
    private ResponseEntity<List<Task>> createTasks(@PathVariable String companyId) {
        log.info("'createTasks' invoked with params'{}'", companyId);

        List<Task> taskForService = taskService.createTaskForService(companyId);
        log.info("'taskForService={}'", taskForService);

        return ResponseEntity.ok(taskForService);
    }
}
