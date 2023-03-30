package com.wxw.springbootdemo.cache;

import com.wxw.cache.redis.service.RedisService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class RedisTest {

    @Resource
    private RedisService redisService;

    @Test
    public void testSet() {
        redisService.set("testKey", "testValue");
    }

    @Test
    public void testGet() {
        System.out.println("result: " + redisService.get("testKey"));
    }
}
