/*
 * 项 目 名:  Storage Tool Service V100R001C00
 * 文 件 名:  pers.linhai.nature.indexaccess.log.ElasticsearchBulkProcessorConfig.java
 * 版       权:  XXX Technologies Co., Ltd. Copyright 2017,  All rights reserved.
 * 描       述:  XXX PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * 修 改 人:  shinelon
 * 修改时间:  2017年7月5日
 * 修改内容:  创建
 */
package pers.linhai.nature.esaccessor.log4jappender.plugins;

import org.apache.logging.log4j.core.Core;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderFactory;
import org.apache.logging.log4j.core.util.Builder;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;

import pers.linhai.nature.esaccessor.model.bulk.BulkProcessorConfiguration;

/**
 * 
 * 
 * @author  shinelon
 * @version  V100R001C00
 */
@Plugin(name = "BulkProcessor", category = Core.CATEGORY_NAME, printObject = true)
public class BulkProcessorConfigurationPlugin implements Builder<BulkProcessorConfigurationPlugin>
{
    
    /**
     * 
     *
     * @return
     */
    public BulkProcessorConfigurationPlugin build()
    {
        return this;
    }

    /**
     * 设置并发处理请求线程数为10
     */
    @PluginBuilderAttribute
    private int concurrentRequests = 10;
    
    /**
     * 达到批量1万请求处理一次  
     */
    @PluginBuilderAttribute
    private int bulkActions = 10000;
    
    /**
     * 达到10M批量处理一次  
     */
    @PluginBuilderAttribute
    private int bulkSize = 10;
    
    /**
     * 设置flush索引周期  10秒一次
     */
    @PluginBuilderAttribute
    private int flushInterval = 10;
    
    @PluginBuilderFactory
    public static final Builder<BulkProcessorConfigurationPlugin> create()
    {
         return new BulkProcessorConfigurationPlugin();
    }

    /**
     * 返回 concurrentRequests
     *
     * @return concurrentRequests
     */
    public int getConcurrentRequests()
    {
        return concurrentRequests;
    }

    /**
     * 返回 bulkActions
     *
     * @return bulkActions
     */
    public int getBulkActions()
    {
        return bulkActions;
    }

    /**
     * 返回 bulkSize
     *
     * @return bulkSize
     */
    public int getBulkSize()
    {
        return bulkSize;
    }

    /**
     * 返回 flushInterval
     *
     * @return flushInterval
     */
    public int getFlushInterval()
    {
        return flushInterval;
    }
    
    /**
     * 获取BulkProcessor配置信息
     *
     * @return BulkProcessorConfiguration
     */
    public BulkProcessorConfiguration to()
    {
        BulkProcessorConfiguration bpc = new BulkProcessorConfiguration();
        bpc.setBulkActions(bulkActions);
        bpc.setBulkSize(new ByteSizeValue(bulkSize, ByteSizeUnit.MB));
        bpc.setConcurrentRequests(concurrentRequests);
        bpc.setFlushInterval(TimeValue.timeValueSeconds(flushInterval));
        return bpc;
    }
}
