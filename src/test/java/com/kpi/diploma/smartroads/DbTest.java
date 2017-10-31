package com.kpi.diploma.smartroads;

import com.kpi.diploma.smartroads.model.document.user.User;
import com.kpi.diploma.smartroads.model.util.title.Documents;
import com.kpi.diploma.smartroads.repository.UserRepository;
import com.mongodb.DBCollection;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
//@ActiveProfiles("heroku")
@Slf4j
public class DbTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private JedisConnectionFactory jedisConnectionFactory;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void test_deleteFromDb() {

        int database = jedisConnectionFactory.getDatabase();
        log.info(jedisConnectionFactory.getClientName());
        log.info(jedisConnectionFactory.getPassword());

        log.info("'test_deleteFromDb' invoked");
        DBCollection collection = mongoTemplate.getCollection(Documents.USER);
        log.info("collection.count'{}'", collection.count());
        Assert.assertNotEquals(0, collection.count());

//        System.out.println(mongoClient.getCredentialsList().toString());
        collection.drop();

        Assert.assertEquals(collection.count(), 0);
    }

    @Test
    @Transactional
    public void test_mongoTransaction() {
        User user = new User();
        user.setEmail("unit_test");
        userRepository.save(user);
    }
}
