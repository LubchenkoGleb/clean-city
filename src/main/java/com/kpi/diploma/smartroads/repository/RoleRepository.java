package com.kpi.diploma.smartroads.repository;

import com.kpi.diploma.smartroads.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RoleRepository extends MongoRepository<Role, String> {

}
