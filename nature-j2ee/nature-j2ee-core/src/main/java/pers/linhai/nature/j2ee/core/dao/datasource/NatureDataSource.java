/*
 * <p>Copyright : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p> <p>FileName : DataSourceConfig.java</p>
 * <p>Package : com.meme.crm.web.console</p>
 * @Creator lilinhai 2018年4月20日 下午6:07:18
 * @Version V1.0
 */

package pers.linhai.nature.j2ee.core.dao.datasource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.alibaba.druid.filter.config.ConfigFilter;
import com.alibaba.druid.filter.encoding.EncodingConvertFilter;
import com.alibaba.druid.filter.logging.CommonsLogFilter;
import com.alibaba.druid.filter.logging.Log4j2Filter;
import com.alibaba.druid.filter.logging.Log4jFilter;
import com.alibaba.druid.filter.logging.Slf4jLogFilter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.wall.WallFilter;

/**
 * 数据源配置
 * <p>ClassName      : DataSourceConfig</p>
 * @author lilinhai 2018年4月20日 下午6:07:18
 * @version 1.0
 */
@Component
@ConfigurationProperties(prefix = "nature.datasource")
public class NatureDataSource extends DruidDataSource
{
    /**
     * <p>Info          : long serialVersionUID lilinhai 2018年4月20日 下午10:53:09</p>
     */
    private static final long serialVersionUID = 1L;
    
    @Autowired(required = false)
    public void addStatFilter(StatFilter statFilter)
    {
        super.filters.add(statFilter);
    }
    
    @Autowired(required = false)
    public void addConfigFilter(ConfigFilter configFilter)
    {
        super.filters.add(configFilter);
    }
    
    @Autowired(required = false)
    public void addEncodingConvertFilter(EncodingConvertFilter encodingConvertFilter)
    {
        super.filters.add(encodingConvertFilter);
    }
    
    @Autowired(required = false)
    public void addSlf4jLogFilter(Slf4jLogFilter slf4jLogFilter)
    {
        super.filters.add(slf4jLogFilter);
    }
    
    @Autowired(required = false)
    public void addLog4jFilter(Log4jFilter log4jFilter)
    {
        super.filters.add(log4jFilter);
    }
    
    @Autowired(required = false)
    public void addLog4j2Filter(Log4j2Filter log4j2Filter)
    {
        super.filters.add(log4j2Filter);
    }
    
    @Autowired(required = false)
    public void addCommonsLogFilter(CommonsLogFilter commonsLogFilter)
    {
        super.filters.add(commonsLogFilter);
    }
    
    @Autowired(required = false)
    public void addWallFilter(WallFilter wallFilter)
    {
        super.filters.add(wallFilter);
    }
}
