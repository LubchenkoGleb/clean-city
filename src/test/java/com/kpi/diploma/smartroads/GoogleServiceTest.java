package com.kpi.diploma.smartroads;


import com.google.maps.errors.ApiException;
import com.kpi.diploma.smartroads.service.util.google.GoogleService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class GoogleServiceTest {

    @Autowired
    private GoogleService googleService;

    @Test
    public void test_googleServiceWorksCorrectly() throws InterruptedException, ApiException, IOException {

//        googleService.buildRoute("kiev", "odessa");

//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        log.info(gson.toJson(result));


    }
}
