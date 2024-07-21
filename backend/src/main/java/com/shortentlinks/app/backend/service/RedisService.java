package com.shortentlinks.app.backend.service;

import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;
    private static final Integer DEFAULT_EXPIRE_TIME_IN_DAY = 1;

    @Autowired
    public RedisService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void set(String key, String value) {
        this.redisTemplate.opsForValue().set(key, value, DEFAULT_EXPIRE_TIME_IN_DAY, TimeUnit.DAYS);
    }

    public String get(String key) {
        // Get value from Redis
        return this.redisTemplate.opsForValue().get(key);
    }
}
