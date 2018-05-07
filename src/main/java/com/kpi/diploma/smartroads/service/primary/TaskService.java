package com.kpi.diploma.smartroads.service.primary;

import com.fasterxml.jackson.databind.JsonNode;

public interface TaskService {

    void createTasksForAllServices();

    JsonNode createTaskForService(String serviceId);
}
