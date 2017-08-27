package com.kpi.diploma.smartroads.repository;

import com.kpi.diploma.smartroads.model.document.User;
import com.kpi.diploma.smartroads.model.title.Fields;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

//@RepositoryRestResource(collectionResourceRel = "users", path = "users")
public interface UserRepository extends MongoRepository<User, String> {

    User findByEmail(@Param(Fields.EMAIL) String email);
}
