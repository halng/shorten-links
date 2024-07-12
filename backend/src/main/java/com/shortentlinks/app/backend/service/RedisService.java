package com.shortentlinks.app.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public RedisService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void set(String key, String value) {
        // Set key value pair to Redis
        this.redisTemplate.opsForValue().set(key, value);
    }

    public String get(String key) {
        // Get value from Redis
        return this.redisTemplate.opsForValue().get(key);
    }
}
