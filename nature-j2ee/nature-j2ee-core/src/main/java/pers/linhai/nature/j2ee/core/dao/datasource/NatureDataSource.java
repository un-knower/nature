/*
 * <p>Copyright : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p> <p>FileName : DataSourceConfig.java</p>
 * <p>Package : com.meme.crm.web.console</p>
 * @Creator lilinhai 2018年4月20日 下午6:07:18
 * @Version V1.0
 */

package pers.linhai.nature.j2ee.core.dao.datasource;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.alibaba.druid.pool.DruidDataSource;


/**
 * 数据源配置
 * <p>ClassName      : DataSourceConfig</p>
 * @author lilinhai 2018年4月20日 下午6:07:18
 * @version 1.0
 */
@Component
@ConfigurationProperties("spring.datasource")
public class NatureDataSource extends DruidDataSource
{

    /**
     * <p>Info          : long serialVersionUID lilinhai 2018年4月20日 下午10:53:09</p>
     */
    private static final long serialVersionUID = 1L;
    
}
