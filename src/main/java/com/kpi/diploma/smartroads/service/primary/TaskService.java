package com.kpi.diploma.smartroads.service.primary;

import com.kpi.diploma.smartroads.model.document.map.Task;

import java.util.List;

public interface TaskService {

    void createTasksForAllServices();

    List<Task> createTaskForService(String serviceId);

    List<Task> getAllActiveTask();

    List<Task> getAllActiveTaskByCompany(String companyId);

    Task get(String id);

}
