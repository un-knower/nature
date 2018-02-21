/*
 * <p>Copyright : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p> <p>FileName :
 * JDBCConnectionConfigurationBuilder.java</p> <p>Package : codegenerator.configuration</p>
 * @Creator lilinhai 2018年2月5日 上午10:07:36
 * @Version V1.0
 */

package pers.linhai.nature.j2ee.generator.configuration;


import pers.linhai.nature.j2ee.generator.core.config.JDBCConnectionConfiguration;


/**
 * <p>ClassName      : JDBCConnectionConfigurationBuilder</p>
 * @author lilinhai 2018年2月5日 上午10:07:36
 * @version 1.0
 */
public class JDBCConnectionConfigurationBuilder extends BaseConfigurationBuilder
{

    private JDBCConnectionConfiguration jdbcConnectionConfiguration = new JDBCConnectionConfiguration();

    /**
     * <p>Title        : JDBCConnectionConfigurationBuilder lilinhai 2018年2月5日 上午11:15:14</p>
     */ 
    public JDBCConnectionConfigurationBuilder()
    {
        super();
        propertyHolder = jdbcConnectionConfiguration;
        
        // 加上如下参数方可获取数据库表的备注信息
        addProperty("remarksReporting", "true");
        addProperty("remarks", "true");
        addProperty("useInformationSchema", "true");
    }

    public JDBCConnectionConfigurationBuilder driverClass(String driverClass)
    {
        jdbcConnectionConfiguration.setDriverClass(driverClass);
        return this;
    }

    public JDBCConnectionConfigurationBuilder connectionURL(String connectionURL)
    {
        jdbcConnectionConfiguration.setConnectionURL(connectionURL);
        return this;
    }

    public JDBCConnectionConfigurationBuilder userId(String userId)
    {
        jdbcConnectionConfiguration.setUserId(userId);
        return this;
    }

    public JDBCConnectionConfigurationBuilder password(String password)
    {
        jdbcConnectionConfiguration.setPassword(password);
        return this;
    }

    public JDBCConnectionConfiguration build()
    {
        return jdbcConnectionConfiguration;
    }
}
