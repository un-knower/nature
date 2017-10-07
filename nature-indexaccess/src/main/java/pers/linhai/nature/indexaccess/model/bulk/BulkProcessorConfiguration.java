/*
 * 项 目 名:  Storage Tool Service V100R001C00
 * 文 件 名:  pers.linhai.esframework.model.core.BulkProcessorConfiguration.java
 * 版       权:  XXX Technologies Co., Ltd. Copyright 2017,  All rights reserved.
 * 描       述:  XXX PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * 修 改 人:  shinelon
 * 修改时间:  2017年6月6日
 * 修改内容:  创建
 */
package pers.linhai.nature.indexaccess.model.bulk;

import org.elasticsearch.action.bulk.BulkProcessor.Listener;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;

import pers.linhai.nature.indexaccess.core.BulkProcessorListener;

/**
 * 批量处理器配置信息
 * 
 * @author  shinelon
 * @version  V100R001C00
 */
public class BulkProcessorConfiguration
{
    
    /**
     * 设置并发处理请求线程数为10
     */
    private int concurrentRequests = 10;
    
    /**
     * 达到批量1万请求处理一次  
     */
    private int bulkActions = 10000;
    
    /**
     * 达到20M批量处理一次  
     */
    private ByteSizeValue bulkSize = new ByteSizeValue(20, ByteSizeUnit.MB);
    
    /**
     * 设置flush索引周期  10秒一次
     */
    private TimeValue flushInterval = TimeValue.timeValueSeconds(10);
    
    /**
     * 批量处理监听器
     */
    private BulkProcessorListener listener;
    
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
     * 对concurrentRequests进行赋值
     *
     * @param concurrentRequests
     */
    public void setConcurrentRequests(int concurrentRequests)
    {
        this.concurrentRequests = concurrentRequests;
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
     * 对bulkActions进行赋值
     *
     * @param bulkActions
     */
    public void setBulkActions(int bulkActions)
    {
        this.bulkActions = bulkActions;
    }
    
    /**
     * 返回 bulkSize
     *
     * @return bulkSize
     */
    public ByteSizeValue getBulkSize()
    {
        return bulkSize;
    }
    
    /**
     * 对bulkSize进行赋值
     *
     * @param bulkSize
     */
    public void setBulkSize(ByteSizeValue bulkSize)
    {
        this.bulkSize = bulkSize;
    }
    
    /**
     * 返回 flushInterval
     *
     * @return flushInterval
     */
    public TimeValue getFlushInterval()
    {
        return flushInterval;
    }
    
    /**
     * 对flushInterval进行赋值
     *
     * @param flushInterval
     */
    public void setFlushInterval(TimeValue flushInterval)
    {
        this.flushInterval = flushInterval;
    }

    /**
     * 返回 listener
     *
     * @return listener
     */
    public Listener getListener()
    {
        return listener;
    }

    /**
     * 对listener进行赋值
     *
     * @param listener
     */
    public void setListener(BulkProcessorListener listener)
    {
        this.listener = listener;
    }

    /**
     * @return
     */
    public String toString()
    {
        return "BulkProcessorConfiguration [concurrentRequests=" + concurrentRequests + ", bulkActions=" + bulkActions + ", bulkSize=" + bulkSize + ", flushInterval=" + flushInterval + "]";
    }
    
}
