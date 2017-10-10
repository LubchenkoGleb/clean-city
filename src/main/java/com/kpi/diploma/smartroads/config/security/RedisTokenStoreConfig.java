package com.kpi.diploma.smartroads.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.cache.SpringCacheBasedUserCache;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

@Configuration
public class RedisTokenStoreConfig {

    private static final String REDIS_CACHE_NAME = "redis_cache_name";
    private static final String REDIS_PREFIX = "redis_cache_prefix";
    private static final Long EXPIRE = 60 * 60L;

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
    public TokenStore tokenStore(RedisConnectionFactory redisConnectionFactory) {
        RedisTokenStore tokenStore = new RedisTokenStore(redisConnectionFactory);
        return tokenStore;
    }
}