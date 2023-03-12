package com.wxw.data.config.multi;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * oracle数据源配置类
 */
@Configuration
@ConfigurationProperties(prefix = "oracle")
@Data
public class OracleDataSourceConfig {

    private String driverClassName;
    private String url;
    private String username;
    private String password;

    /**
     * 多数据源时，提供 oracle 的数据源 bean
     * @return
     */
    @Bean
    public DataSource oracleDateSource() {
        DruidDataSource dataSource = new DruidDataSource();
        //数据库配置
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        //Druid连接池配置
        dataSource.setInitialSize(0);   //初始化连接大小
        dataSource.setMaxActive(20);    //连接池最大使用连接数量
        dataSource.setMinIdle(0);       //连接池最小空闲
        dataSource.setMaxWait(6000);    //获取连接最大等待时间
        dataSource.setTimeBetweenEvictionRunsMillis(60000);//配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
        dataSource.setMinEvictableIdleTimeMillis(25200000);//配置一个连接在池中最小生存的时间，单位是毫秒
        dataSource.setRemoveAbandoned(true);//打开removeAbandoned功能
        dataSource.setRemoveAbandonedTimeout(1800);//removeAbandoned, 多少时间内必须关闭连接

        return dataSource;
    }

}
