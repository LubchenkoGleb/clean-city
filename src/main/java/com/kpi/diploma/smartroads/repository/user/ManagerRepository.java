package com.kpi.diploma.smartroads.repository.user;

import com.kpi.diploma.smartroads.model.document.user.Driver;
import com.kpi.diploma.smartroads.model.document.user.Manager;
import com.kpi.diploma.smartroads.model.document.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ManagerRepository extends MongoRepository<Manager, String> {

    Manager findByInviteKey(String inviteUrl);

    Manager findByEmail(String email);

    Manager findByIdAndBossId(String id, String bossId);
}
