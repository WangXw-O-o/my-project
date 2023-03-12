package com.wxw.data.config.single;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.Data;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * mybatis 单数据源配置
 */
//@Configuration
//@MapperScan("com.wxw.data.dao") //MyBatis的Mapper扫描，配置后无需再到Mapper上使用@Mapper注解
//@ConfigurationProperties(prefix = "mysql")
@Data
public class SingleDataSourceMybatisConfig {

    private String driverClassName;
    private String url;
    private String username;
    private String password;

//    @Primary
//    @Bean("mysqlDateSource")
    public DataSource dateSource() {
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

//    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(
                new PathMatchingResourcePatternResolver()
                        .getResources("classpath:mapper/mysql/*.xml"));
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        //自动将数据库中的下划线转换为驼峰格式
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setDefaultFetchSize(100);
        configuration.setDefaultStatementTimeout(30);
        //启用缓存机制
        configuration.setCacheEnabled(true);
        //启用懒加载机制
        configuration.setLazyLoadingEnabled(true);

        bean.setConfiguration(configuration);
        return bean.getObject();
    }

}
