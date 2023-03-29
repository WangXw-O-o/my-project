package com.wxw.cache.memcached.service.impl;

import com.wxw.cache.memcached.service.MemcachedService;
import lombok.extern.slf4j.Slf4j;
import net.rubyeye.xmemcached.CASOperation;
import net.rubyeye.xmemcached.GetsResponse;
import net.rubyeye.xmemcached.MemcachedClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class MemcachedServiceImpl implements MemcachedService {

    //默认过期时间
    public static final int DEFAULT_EXP_TIME = 60*60*24;

    @Resource
    private MemcachedClient client;

    @Override
    public boolean set(String key, String value) {
        return set(key, value, DEFAULT_EXP_TIME);
    }

    @Override
    public boolean set(String key, String value, int expTime) {
        try {
            return client.set(key, expTime, value);
        } catch (Exception e) {
            log.error("memcached set error！", e);
            return false;
        }
    }

    @Override
    public boolean add(String key, String value) {
        try {
            return client.add(key, DEFAULT_EXP_TIME, value);
        } catch (Exception e) {
            log.error("memcached add error！", e);
            return false;
        }
    }

    @Override
    public boolean replace(String key, String value) {
        try {
            return client.replace(key, DEFAULT_EXP_TIME, value);
        } catch (Exception e) {
            log.error("memcached replace error！", e);
            return false;
        }
    }

    @Override
    public boolean append(String key, String value) {
        try {
            return client.append(key, value, DEFAULT_EXP_TIME);
        } catch (Exception e) {
            log.error("memcached append error！", e);
            return false;
        }
    }

    @Override
    public boolean prepend(String key, String value) {
        try {
            return client.prepend(key, value, DEFAULT_EXP_TIME);
        } catch (Exception e) {
            log.error("memcached prepend error！", e);
            return false;
        }
    }

    @Override
    public boolean cas(String key, String value, int maxTryTimes) {
        try {
            return client.cas(key, new CASOperation<Object>() {
                @Override
                public int getMaxTries() {
                    return maxTryTimes;
                }

                @Override
                public Object getNewValue(long currentCAS, Object currentValue) {
                    return value;
                }
            });
        } catch (Exception e) {
            log.error("memcached cas error！", e);
            return false;
        }
    }

    @Override
    public Object get(String key) {
        try {
            return client.get(key);
        } catch (Exception e) {
            log.error("memcached get error！", e);
        }
        return null;
    }

    @Override
    public GetsResponse<Object> gets(String key) {
        try {
            return client.gets(key);
        } catch (Exception e) {
            log.error("memcached gets error！", e);
        }
        return null;
    }

    @Override
    public boolean delete(String key) {
        try {
            return client.delete(key);
        } catch (Exception e) {
            log.error("memcached delete error！", e);
        }
        return false;
    }

    @Override
    public long incr(String key, int delayTime) {
        try {
            return client.incr(key, delayTime);
        } catch (Exception e) {
            log.error("memcached incr error！", e);
        }
        return 0;
    }

    @Override
    public long decr(String key, int delayTime) {
        try {
            return client.decr(key, delayTime);
        } catch (Exception e) {
            log.error("memcached decr error！", e);
        }
        return 0;
    }
}
