package com.kpi.diploma.smartroads.repository;

import com.kpi.diploma.smartroads.model.document.MapObject;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MapObjectRepository extends MongoRepository<MapObject, String> {
}
