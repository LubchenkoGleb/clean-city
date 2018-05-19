package com.kpi.diploma.smartroads.repository.map;

import com.kpi.diploma.smartroads.model.document.map.Task;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TaskRepository extends MongoRepository<Task, String> {

    List<Task> findAllByActiveTrue();

    List<Task> findAllByActiveIsTrueAndCompanyId(String companyId);

    Task findByCompanyIdAndActiveIsTrueAndAssignedIsFalse(String companyId);

}
