package com.wxw.springbootdemo.cache;

import com.wxw.cache.memcached.service.MemcachedService;
import net.rubyeye.xmemcached.MemcachedClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class MemcachedTest {

    @Resource
    private MemcachedService service;

    @Test
    public void testGet() {
        String value = (String) service.get("testKey");
        System.out.println(value);
    }

}
