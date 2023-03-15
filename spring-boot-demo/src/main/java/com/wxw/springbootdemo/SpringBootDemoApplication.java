package com.wxw.springbootdemo;

import com.alibaba.druid.spring.boot.autoconfigure.properties.DruidStatProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.PropertySource;

/**
 * 启动类
 */
//scanBasePackages 指定需要扫描的包路径（扫描其他module下的Bean），不指定就只能扫描启动类下的Bean
//需要导入对应的依赖，不然无法找到包路径
@SpringBootApplication(scanBasePackages = {"com.wxw.*"})
//只能默认加载.properties文件，想要加载yml配置文件，需要自己实现一个加载类 YmlConfigFactory
@PropertySource(value = {"classpath:application.yml"}, factory = YmlConfigFactory.class)
//扫描注解方式定义的过滤器
@ServletComponentScan(value = {"com/wxw/springbootdemo/web/filter"})
//使配置文件中的配置生效并映射到指定类的属性
@EnableConfigurationProperties({DruidStatProperties.class, DataSourceProperties.class})
public class SpringBootDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDemoApplication.class, args);
    }

}
