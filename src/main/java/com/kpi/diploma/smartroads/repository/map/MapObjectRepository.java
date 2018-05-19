package com.kpi.diploma.smartroads.repository.map;

import com.kpi.diploma.smartroads.model.document.map.MapObject;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MapObjectRepository extends MongoRepository<MapObject, String> {

    List<MapObject> findAllByOwnerId(String ownerId);

    List<MapObject> findByLatBetweenAndLonBetween(Double latMin, Double labMax, Double lonMin, Double lonMax);

    List<MapObject> findByOwnerIdAndLatBetweenAndLonBetween(String ownerId, Double latMin, Double labMax, Double lonMin, Double lonMax);

//    List<MapObject> findByLatBetweenAndLonBetweenAAndOwnerId(Double latMin, Double labMax, Double lonMin, Double lonMax);
}
