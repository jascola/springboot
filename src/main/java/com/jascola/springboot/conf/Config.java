package com.jascola.springboot.conf;

import com.jascola.bean.JdbcConnectBean;
import org.apache.commons.dbcp2.BasicDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.context.annotation.*;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;

import java.io.IOException;


@Configuration
@ComponentScan({"com.jascola.model.dao","com.jascola.job"})
@PropertySource(value = "classpath:database.properties")
@EnableScheduling
public class Config {
    /**
     * 配置数据库连接
     * */
    @Bean
    public static JdbcConnectBean jdbcConnectBean(){
        return new JdbcConnectBean();
    }
    /**
     * 配置数据源
     * */
    @Bean
    public static BasicDataSource basicDataSource(JdbcConnectBean jdbcConnectBean){
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl(jdbcConnectBean.getUrl());
        basicDataSource.setDriverClassName(jdbcConnectBean.getDriver());
        basicDataSource.setPassword(jdbcConnectBean.getPassword());
        basicDataSource.setUsername(jdbcConnectBean.getUsername());
        basicDataSource.setMaxTotal(jdbcConnectBean.getMaxActive());
        basicDataSource.setMinIdle(jdbcConnectBean.getMinIdle());
        basicDataSource.setInitialSize(jdbcConnectBean.getInitialSize());
        basicDataSource.setMaxIdle(jdbcConnectBean.getMaxIdle());
        basicDataSource.setMaxWaitMillis(jdbcConnectBean.getMaxWait());
        return basicDataSource;
    }
    /**
     * 配置batch事务管理器
     * */
    @Bean
    public static DataSourceTransactionManager transactionManager(JdbcConnectBean jdbcConnectBean){
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(basicDataSource(jdbcConnectBean));
        return dataSourceTransactionManager;
    }
    /**
     * 配置mybatis的sqlSessionFactory
     * */
    @Bean(name="sqlSessionFactory")
    public static SqlSessionFactoryBean initSqlSessionFactory(JdbcConnectBean jdbcConnectBean) throws IOException {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(basicDataSource(jdbcConnectBean));
        //配置mapper文件映射
        Resource[] resources = new PathMatchingResourcePatternResolver()
                .getResources("classpath:conf/mapper/*.xml");
        sqlSessionFactoryBean.setMapperLocations(resources);
        return sqlSessionFactoryBean;
    }

    /**
     * 自动扫描包下的接口，与mapper文件对应
     * */
    @Bean
    public static MapperScannerConfigurer mapperScannerConfigurer(){
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        //配置映射文件关联接口
        mapperScannerConfigurer.setBasePackage("com.jascola.model.dao");
        return mapperScannerConfigurer;
    }



}
