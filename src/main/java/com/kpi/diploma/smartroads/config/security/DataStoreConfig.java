package com.kpi.diploma.smartroads.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.cache.SpringCacheBasedUserCache;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

@Configuration
public class DataStoreConfig {

    private final Environment environment;
    private static final String REDIS_CACHE_NAME = "redis_cache_name";
    private static final String REDIS_PREFIX = "redis_cache_prefix";
    private static final Long EXPIRE = 60 * 60L;

    @Autowired
    public DataStoreConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    RedisCache redisCache(RedisTemplate redisTemplate) {
        RedisCache redisCache = new RedisCache(REDIS_CACHE_NAME, REDIS_PREFIX.getBytes(), redisTemplate, EXPIRE);
        return redisCache;
    }

    @Bean
    public UserCache userCache(RedisCache redisCache) throws Exception {
        UserCache userCache = new SpringCacheBasedUserCache(redisCache);
        return userCache;
    }

    @Bean
    @Profile("default")
    public TokenStore tokenStore() {
        RedisTokenStore tokenStore = new RedisTokenStore(redisConnectionFactory());
        return tokenStore;
    }

    @Bean
    @Profile("default")
    public RedisConnectionFactory redisConnectionFactory() {
        JedisConnectionFactory connectionFactory = new JedisConnectionFactory();

        connectionFactory.setHostName("localhost");
        connectionFactory.setPort(6379);
        connectionFactory.setUsePool(true);

        return connectionFactory;
    }

    @Bean
    @Profile("heroku")
    public TokenStore tokenStoreHeroku() {
        RedisTokenStore tokenStore = new RedisTokenStore(redisConnectionFactoryHeroku());
        return tokenStore;
    }

    @Bean
    @Profile("heroku")
    public RedisConnectionFactory redisConnectionFactoryHeroku() {
        JedisConnectionFactory connectionFactory = new JedisConnectionFactory();

        connectionFactory.setHostName(environment.getProperty("redis.address.host"));
        connectionFactory.setPort(Integer.valueOf(environment.getProperty("redis.address.port")));
        connectionFactory.setClientName(environment.getProperty("redis.credentials.user"));
        connectionFactory.setPassword(environment.getProperty("redis.credentials.password"));
        connectionFactory.setUsePool(true);

        return connectionFactory;
    }
}