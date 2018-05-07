/*
 * <p>Copyright : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p> <p>FileName : DatabaseIdProviderConf.java</p>
 * <p>Package : pers.linhai.nature.j2ee.core.dao.datasource</p>
 * @Creator lilinhai 2018年4月21日 下午3:30:38
 * @Version V1.0
 */

package pers.linhai.nature.j2ee.core.dao.datasource;

import java.util.Properties;

import org.apache.ibatis.mapping.VendorDatabaseIdProvider;
import org.springframework.stereotype.Component;

/**
 * <p>Description    : <pre>TODO(这里用一句话描述这个类的作用)</pre></p>
 * <p>ClassName      : DatabaseIdProviderConf</p>
 * @author lilinhai 2018年4月21日 下午3:30:38
 * @version 1.0
 */
@Component
public class NatureDatabaseIdProvider extends VendorDatabaseIdProvider
{
    
    /**
     * <p>Title        : DatabaseIdProviderConf lilinhai 2018年4月21日 下午3:31:04</p>
     */
    public NatureDatabaseIdProvider()
    {
        Properties properties = new Properties();
        properties.setProperty("Oracle1", "oracle");
        properties.setProperty("MySQL", "mysql");
        properties.setProperty("DB2", "DB2");
        properties.setProperty("SQL Server", "sqlserver");
        setProperties(properties);
    }
}
