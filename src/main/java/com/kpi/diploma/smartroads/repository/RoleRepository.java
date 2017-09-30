package com.kpi.diploma.smartroads.repository;

import com.kpi.diploma.smartroads.model.document.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepository extends MongoRepository<Role, String> {
    Role findByRole(String role);
}
