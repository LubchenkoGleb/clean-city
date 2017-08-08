package com.kpi.diploma.smartroads.repository;

import com.kpi.diploma.smartroads.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

//@RepositoryRestResource(collectionResourceRel = "users", path = "users")
public interface UserRepository extends MongoRepository<User, String> {
}
