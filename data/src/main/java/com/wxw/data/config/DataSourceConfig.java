package com.wxw.data.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * 数据源配置类
 */
@org.springframework.context.annotation.Configuration
@MapperScan("com.wxw.data.dao")
public class DataSourceConfig {

    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource dataSource(){
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        druidDataSource.setPassword("123456");
        druidDataSource.setUsername("root");
        druidDataSource.setUrl("jdbc:mysql://localhost:3306/test?serverTimezone=GMT%2B8&useSSL=true");
        return druidDataSource;
    }

    @Bean("sqlSessionFactory1")
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(
                new PathMatchingResourcePatternResolver()
                        .getResources("classpath:mapper/*.xml"));
        Configuration configuration = new Configuration();
        //自动将数据库中的下划线转换为驼峰格式
//        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setDefaultFetchSize(100);
        configuration.setDefaultStatementTimeout(30);

        bean.setConfiguration(configuration);
        return bean.getObject();
    }

}
