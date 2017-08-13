package com.kpi.diploma.smartroads.config.db;

import com.mongodb.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

import java.util.Arrays;

@Slf4j
@Configuration
@Profile("heroku")
public class MongoConfig extends AbstractMongoConfiguration {

    @Value("${mongo.credentials.user}")
    private String mongoUser;
    @Value("${mongo.credentials.password}")
    private String mongoPassword;
    @Value("${mongo.database.name}")
    private String mongoDataBaseName;
    @Value("${mongo.address.host}")
    private String mongoHost;
    @Value("${mongo.address.port}")
    private Integer mongoPort;

    @Override
    protected String getDatabaseName() {
        return mongoDataBaseName;
    }

    @Override
    public Mongo mongo() throws Exception {
        log.info("mongoHost: " + mongoHost + ", mongoPort: " + mongoPort);

        MongoCredential credential = MongoCredential
                .createCredential(mongoUser, mongoDataBaseName, mongoPassword.toCharArray());

        ServerAddress serverAddress = new ServerAddress(mongoHost, mongoPort);

        MongoClient mongoClient = new MongoClient(serverAddress, Arrays.asList(credential));

        return mongoClient;
    }
}
