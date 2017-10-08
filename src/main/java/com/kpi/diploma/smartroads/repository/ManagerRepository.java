package com.kpi.diploma.smartroads.repository;

import com.kpi.diploma.smartroads.model.document.user.Manager;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ManagerRepository extends MongoRepository<Manager, String> {

    Manager findByInviteKey(String inviteUrl);
}
