/*
 * 项 目 名:  Storage Tool Service V100R001C00
 * 文 件 名:  pers.linhai.nature.indexaccess.model.core.BulkProcessorEvent.java
 * 版       权:  XXX Technologies Co., Ltd. Copyright 2017,  All rights reserved.
 * 描       述:  XXX PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * 修 改 人:  shinelon
 * 修改时间:  2017年7月16日
 * 修改内容:  创建
 */
package pers.linhai.nature.esaccessor.model.bulk;

import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;

/**
 * 批量处理事件
 * 
 * @author  shinelon
 * @version  V100R001C00
 */
public class BulkProcessorEvent
{
    private EventType event;
    
    private long executionId;
    
    private BulkRequest request;
    
    private BulkResponse response;
    
    private Throwable failure;
    
    /**
     * index name
     */
    private String index;
    
    /**
     * type name
     */
    private String type;
    
    /**
     * 对event进行赋值
     *
     * @param event
     */
    public void setEvent(EventType event)
    {
        this.event = event;
    }
    
    /**
     * 对executionId进行赋值
     *
     * @param executionId
     */
    public void setExecutionId(long executionId)
    {
        this.executionId = executionId;
    }
    
    /**
     * 对request进行赋值
     *
     * @param request
     */
    public void setRequest(BulkRequest request)
    {
        this.request = request;
    }
    
    /**
     * 对response进行赋值
     *
     * @param response
     */
    public void setResponse(BulkResponse response)
    {
        this.response = response;
    }
    
    /**
     * 对failure进行赋值
     *
     * @param failure
     */
    public void setFailure(Throwable failure)
    {
        this.failure = failure;
    }
    
    /**
     * 返回 event
     *
     * @return event
     */
    public EventType getEvent()
    {
        return event;
    }
    
    /**
     * 返回 executionId
     *
     * @return executionId
     */
    public long getExecutionId()
    {
        return executionId;
    }
    
    /**
     * 返回 request
     *
     * @return request
     */
    public BulkRequest getRequest()
    {
        return request;
    }
    
    /**
     * 返回 response
     *
     * @return response
     */
    public BulkResponse getResponse()
    {
        return response;
    }
    
    /**
     * 返回 failure
     *
     * @return failure
     */
    public Throwable getFailure()
    {
        return failure;
    }
    
    public void process()
    {
        event.process(this);
    }

    /**
     * 返回 index
     *
     * @return index
     */
    public String getIndex()
    {
        return index;
    }

    /**
     * 对index进行赋值
     *
     * @param index
     */
    public void setIndex(String index)
    {
        this.index = index;
    }

    /**
     * 返回 type
     *
     * @return type
     */
    public String getType()
    {
        return type;
    }

    /**
     * 对type进行赋值
     *
     * @param type
     */
    public void setType(String type)
    {
        this.type = type;
    }
}
