package com.kpi.diploma.smartroads;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kpi.diploma.smartroads.model.util.data.MapObjectDetail;
import com.kpi.diploma.smartroads.model.util.title.value.ContainerValues;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class SocketTest {

    @Test(expected = IOException.class)
    public void test_enumConversion() throws IOException {
        MapObjectDetail mapObjectDetail = new MapObjectDetail();
        mapObjectDetail.setType(ContainerValues.GLASS);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.valueToTree(mapObjectDetail);
        log.info("'node={}'", node);

        mapObjectDetail = mapper.readValue(
                "{\"imageUrl\":null,\"amount\":null,\"type\":\"GLASS\"}",
                MapObjectDetail.class);
        log.info("'mapObjectDetail={}'", mapObjectDetail);

        mapObjectDetail = mapper.readValue(
                "{\"imageUrl\":null,\"amount\":null,\"type\":\"FAIL\"}",
                MapObjectDetail.class);
        log.info("'mapObjectDetail={}'", mapObjectDetail);
    }
}
