package com.kpi.diploma.smartroads.rest.primary;

import com.kpi.diploma.smartroads.model.document.map.Task;
import com.kpi.diploma.smartroads.service.primary.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


    @GetMapping(value = "/get-active-task/{companyId}")
    private ResponseEntity<List<Task>> getActiveTaskByCompany(@PathVariable String companyId) {
        return ResponseEntity.ok(taskService.getAllActiveTaskByCompany(companyId));
    }

    @GetMapping(value = "/get-all-active-task")
    private ResponseEntity<List<Task>> getActiveTask() {
        return ResponseEntity.ok(taskService.getAllActiveTask());
    }

    @GetMapping(value = "/get/{id}")
    private ResponseEntity<Task> get(@PathVariable String id) {
        return ResponseEntity.ok(taskService.get(id));
    }
}
