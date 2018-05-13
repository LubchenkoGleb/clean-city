package com.kpi.diploma.smartroads.repository.map;

import com.kpi.diploma.smartroads.model.document.map.Route;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RouteRepository extends MongoRepository<Route, String> {

    List<Route> findAllByStartId(String startId);

    List<Route> findAllByLengthLessThan(Long value);

    void deleteAllByStartIdOrFinishId(String startId, String finishId);

    Route findByStartIdAndFinishId(String startId, String finishId);
}
