package com.wxw.cache.memcached.service;

import net.rubyeye.xmemcached.GetsResponse;

public interface MemcachedService {

    boolean set(String key, String value);

    boolean set(String key, String value, int expTime);

    boolean add(String key, String value);

    boolean replace(String key, String value);

    boolean append(String key, String value);

    boolean prepend(String key, String value);

    boolean cas(String key, String value, int maxTryTimes);

    Object get(String key);

    GetsResponse<Object> gets(String key);

    boolean delete(String key);

    long incr(String key, int delayTime);

    long decr(String key, int delayTime);

}
