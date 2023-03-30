package com.wxw.cache.redis.service.impl;

import com.wxw.cache.redis.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class RedisServiceImpl implements RedisService {

    public static final int Default_Timeout_Seconds = 10;

    @Resource
    private RedisTemplate<Object, Object> redisTemplate;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean set(String key, String value) {
        return set(key, value, Default_Timeout_Seconds, TimeUnit.SECONDS);
    }

    @Override
    public boolean set(String key, String value, int timeout, TimeUnit timeUnit) {
        try {
            stringRedisTemplate.opsForValue().set(key, value, timeout, timeUnit);
            return true;
        } catch (Exception e) {
            log.error("redis get string error!", e);
        }
        return false;
    }

    @Override
    public Object get(String key) {
        try {
            return stringRedisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            log.error("redis get string error!", e);
        }
        return null;
    }
}
