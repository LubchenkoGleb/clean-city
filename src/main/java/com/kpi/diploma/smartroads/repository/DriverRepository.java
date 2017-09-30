package com.kpi.diploma.smartroads.repository;

import com.kpi.diploma.smartroads.model.document.user.Driver;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DriverRepository extends MongoRepository<Driver, String> {

    Driver findByInviteUrl(String inviteUrl);

}
