package com.wxw.cache.memcached.config;

import lombok.Data;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>memcached配置类</p>
 *
 * （1）Linux启动命令，需要指定好启动的ip地址，如果使用localhost，则无法访问：
 *      /usr/bin/memcached -l 192.168.249.128 -p 11211 -m 64M -d
 *
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "memcached")
public class MemcachedConfig {
    private String address;
    private int port;

    @Bean
    public MemcachedClient getMemcachedClient() throws IOException {
        List<InetSocketAddress> addressList = new ArrayList<>();
        addressList.add(new InetSocketAddress(address, port));
        XMemcachedClientBuilder builder = new XMemcachedClientBuilder(addressList);
        return builder.build();
    }

}
