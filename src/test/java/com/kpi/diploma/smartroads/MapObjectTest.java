package com.kpi.diploma.smartroads;

import com.kpi.diploma.smartroads.model.document.map.MapObject;
import com.kpi.diploma.smartroads.repository.MapObjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class MapObjectTest {

    @Autowired
    private MapObjectRepository mapObjectRepository;

    @Test
    public void test_findMapObjectsInArea() {
        log.info("'test_findMapObjectsInArea' invoked");

        List<MapObject> all = mapObjectRepository.findAll();
        log.info("'all.size()={}'", all.size());

        List<MapObject> mapObjects = mapObjectRepository
                .findByLatBetweenAndLonBetween(33D, 34D, 53D, 54D);
        log.info("'mapObjects.size()={}'", mapObjects.size());
        log.info("'mapObjects={}'", mapObjects);
    }
}
