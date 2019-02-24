package com.imovie.cloud.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;
import java.io.IOException;


@Configuration
@EnableTransactionManagement
@MapperScan("com.imovie.cloud.mapper")
public class MyBatisConfig implements TransactionManagementConfigurer {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    // private static final String MAPPER_LOCATION = "classpath:mybatis/*/*/*.xml";
    private static final String MAPPER_LOCATION = "classpath:mybatis/*Mapper.xml";

    private static final String TYPE_ALIASES_PACKAGE = "com.imovie.cloud.entity";

    private static final String MYBATIS_CONFIG = "classpath:mybatis/mybatis.xml";

    @Autowired
    private DataSource dataSource;

    /**
     * 配置sqlSessionFactory
     *
     * @return SqlSessionFactory对象
     */
    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactorys() {
        log.info("sqlSessionFactory init..........");
        try {
            SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
            sessionFactoryBean.setDataSource(dataSource);
//            sessionFactoryBean.setTransactionFactory(new MultiDataSourceTransactionFactory());
            sessionFactoryBean.setTypeAliasesPackage(TYPE_ALIASES_PACKAGE);
            //PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            //org.springframework.core.io.DefaultResourceLoader
//            sessionFactoryBean.setConfigLocation(resolver.getResource(MYBATIS_CONFIG));
            Resource[] resources = resolver.getResources(MAPPER_LOCATION);
            sessionFactoryBean.setMapperLocations(resources);
            // 开启开启驼峰命名转换
            sessionFactoryBean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
            return sessionFactoryBean.getObject();
        } catch (IOException e) {
            log.error("mybatis resolver mapper*xml is error", e);
            return null;
        } catch (Exception e) {
            log.error("mybatis sqlSessionFactoryBean create error", e);
            return null;
        }
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean
    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }
}
