package com.kpi.diploma.smartroads.repository.map;

import com.kpi.diploma.smartroads.model.document.map.Container;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ContainerRepository extends MongoRepository<Container, String> {


    List<Container> findAllByOwnerId(String ownerId);
}
