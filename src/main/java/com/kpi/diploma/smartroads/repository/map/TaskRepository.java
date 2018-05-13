package com.kpi.diploma.smartroads.repository.map;

import com.kpi.diploma.smartroads.model.document.map.Task;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TaskRepository extends MongoRepository<Task, String> {

}
