package com.wxw.data.config.multi;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
//MyBatis的Mapper扫描，配置后无需再到Mapper上使用@Mapper注解
@MapperScan(
        basePackages = "com.wxw.data.dao.mysql",
        sqlSessionFactoryRef = "mysqlSqlSessionFactory",
        sqlSessionTemplateRef = "mysqlSqlSessionTemplate"
)
public class MysqlMyBatisConfig {
    private static final String path = "classpath:mapper/mysql/*.xml";

    private DataSource dataSource;

    /**
     * 加载 mysql 的数据源 bean
     * @param dataSource
     */
    public MysqlMyBatisConfig(@Qualifier("mysqlDateSource") DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public SqlSessionFactory mysqlSqlSessionFactory() throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(
                new PathMatchingResourcePatternResolver()
                        .getResources(path));
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        //自动将数据库中的下划线转换为驼峰格式
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setDefaultFetchSize(100);
        configuration.setDefaultStatementTimeout(30);
        //启用缓存机制：二级缓存开启、在映射文件实际中声明<cached />才实际开启
        configuration.setCacheEnabled(true);
        //启用懒加载机制
        configuration.setLazyLoadingEnabled(true);

        bean.setConfiguration(configuration);
        return bean.getObject();
    }

    @Bean
    public SqlSessionTemplate mysqlSqlSessionTemplate() throws Exception {
        return new SqlSessionTemplate(mysqlSqlSessionFactory());
    }

}
