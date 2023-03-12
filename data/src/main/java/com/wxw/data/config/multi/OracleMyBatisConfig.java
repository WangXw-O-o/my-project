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
        basePackages = "com.wxw.data.dao.oracle",
        sqlSessionFactoryRef = "oracleSqlSessionFactory",
        sqlSessionTemplateRef = "oracleSqlSessionTemplate"
)
public class OracleMyBatisConfig {

    private static final String path = "classpath:mapper/oracle/*.xml";

    private DataSource dataSource;

    /**
     * 加载 oracle 的数据源 bean
     * @param dataSource
     */
    public OracleMyBatisConfig(@Qualifier("oracleDateSource") DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public SqlSessionFactory oracleSqlSessionFactory() throws Exception {
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
        //启用缓存机制
        configuration.setCacheEnabled(true);
        //启用懒加载机制
        configuration.setLazyLoadingEnabled(true);

        bean.setConfiguration(configuration);
        return bean.getObject();
    }

    @Bean
    public SqlSessionTemplate oracleSqlSessionTemplate() throws Exception {
        return new SqlSessionTemplate(oracleSqlSessionFactory());
    }
}
