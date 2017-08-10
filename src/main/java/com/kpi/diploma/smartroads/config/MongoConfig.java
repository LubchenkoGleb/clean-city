package com.kpi.diploma.smartroads.config;

import com.mongodb.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

import java.util.Arrays;

@Configuration
@Profile("heroku")
public class MongoConfig extends AbstractMongoConfiguration {

    @Value("${mongo.user}")
    private String mongoUser;
    @Value("${mongo.password}")
    private String mongoPassword;
    @Value("${mongo.database.name}")
    private String mongoDataBaseName;
    @Value("${mongo.host}")
    private String mongoHost;
    @Value("${mongo.port}")
    private Integer mongoPort;

    @Override
    protected String getDatabaseName() {
        return mongoDataBaseName;
    }

    @Override
    public Mongo mongo() throws Exception {

        // Set credentials
        MongoCredential credential = MongoCredential
                .createCredential(mongoUser, mongoDataBaseName, mongoPassword.toCharArray());

        ServerAddress serverAddress = new ServerAddress(mongoHost, mongoPort);

        // Mongo Client
        MongoClient mongoClient = new MongoClient(serverAddress, Arrays.asList(credential));

        return mongoClient;
    }
}
