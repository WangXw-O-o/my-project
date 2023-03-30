package com.wxw.cache.redis.service;

import java.util.concurrent.TimeUnit;

public interface RedisService {

    boolean set(String key, String value);

    boolean set(String key, String value, int timeout, TimeUnit timeUnit);

    Object get(String key);
}
