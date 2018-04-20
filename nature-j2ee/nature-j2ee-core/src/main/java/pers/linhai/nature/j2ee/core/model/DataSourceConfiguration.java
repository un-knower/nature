/*
 * <p>Copyright : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p> <p>FileName : DataSourceConfig.java</p>
 * <p>Package : com.meme.crm.web.console</p>
 * @Creator lilinhai 2018年4月20日 下午6:07:18
 * @Version V1.0
 */

package pers.linhai.nature.j2ee.core.model;


import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.pool.DruidDataSource;


/**
 * 数据源配置
 * <p>ClassName      : DataSourceConfig</p>
 * @author lilinhai 2018年4月20日 下午6:07:18
 * @version 1.0
 */
@Configuration
public class DataSourceConfiguration
{

    @SuppressWarnings("unchecked")
    protected <T extends DataSource> T createDataSource(DataSourceProperties properties, Class<T> type)
    {
        return (T)properties.initializeDataSourceBuilder().type(type).build();
    }

    /**
     * @param properties 读入的配置
     * @return DruidDataSource
     */
    @Bean
    @ConfigurationProperties("spring.datasource")
    public DataSource dataSource(DataSourceProperties properties)
    {
        /*DruidDataSource dataSource = properties.initializeDataSourceBuilder().type(DruidDataSource.class).build();
        DatabaseDriver databaseDriver = DatabaseDriver.fromJdbcUrl(properties.determineUrl());
        String validationQuery = databaseDriver.getValidationQuery();
        if (validationQuery != null)
        {
            dataSource.setTestOnBorrow(true);
            dataSource.setValidationQuery(validationQuery);
        }*/
        return properties.initializeDataSourceBuilder().type(DruidDataSource.class).build();
    }
}
