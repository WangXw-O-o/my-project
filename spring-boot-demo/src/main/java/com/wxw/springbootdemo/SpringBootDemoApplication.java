package com.wxw.springbootdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.PropertySource;

/**
 * 启动类
 */
//scanBasePackages 指定需要扫描的包路径（扫描其他module下的Bean），不指定就只能扫描启动类下的Bean
@SpringBootApplication(scanBasePackages = {"com.wxw.drools", "com.wxw.springbootdemo"})
//只能默认加载.properties文件，想要加载yml配置文件，需要自己实现一个加载类 YmlConfigFactory
@PropertySource(value = {"classpath:application.yml"}, factory = YmlConfigFactory.class)
//扫描注解方式定义的过滤器
@ServletComponentScan(value = {"com/wxw/springbootdemo/web/filter"})
public class SpringBootDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDemoApplication.class, args);
    }

}
