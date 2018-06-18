/*
 * 项 目 名:  Storage Tool Service V100R001C00
 * 文 件 名:  pers.linhai.nature.indexaccess.interfaces.BulkProcessorListener.java
 * 版       权:  XXX Technologies Co., Ltd. Copyright 2017,  All rights reserved.
 * 描       述:  XXX PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * 修 改 人:  shinelon
 * 修改时间:  2017年7月15日
 * 修改内容:  创建
 */
package pers.linhai.nature.esmapper.core;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.elasticsearch.action.bulk.BulkProcessor.Listener;

import pers.linhai.nature.esmapper.model.bulk.BulkProcessorEvent;
import pers.linhai.nature.esmapper.model.bulk.BulkProcessorEventProcessorThread;
import pers.linhai.nature.esmapper.model.bulk.EventType;

import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;

/**
 * A listener for the execution of BulkProcessor.
 * @author  shinelon
 * @version  V100R001C00
 */
public class BulkProcessorListener implements Listener
{
    /**
     * 日志消息队列队列
     */
    private static final Queue<BulkProcessorEvent> EVENT_QUEUE = new ConcurrentLinkedQueue<BulkProcessorEvent>();
    
    private static final Thread BULK_PROCESSOR_EVENT_PROCESSOR_THREAD = new BulkProcessorEventProcessorThread(EVENT_QUEUE);
    
    static
    {
        BULK_PROCESSOR_EVENT_PROCESSOR_THREAD.start();
    }
    
    /**
     * index name
     */
    private String index;
    
    /**
     * type name
     */
    private String type;
    
    /** 
     * <默认构造函数>
     */
    public BulkProcessorListener()
    {
        
    }

    /**
     * Callback before the bulk is executed.
     */
    public void beforeBulk(long executionId, BulkRequest request)
    {
        BulkProcessorEvent event = new BulkProcessorEvent();
        event.setEvent(EventType.BEFORE_BULK);
        event.setExecutionId(executionId);
        event.setRequest(request);
        event.setIndex(index);
        event.setType(type);
        EVENT_QUEUE.add(event);
        synchronized (EVENT_QUEUE)
        {
            EVENT_QUEUE.notify();
        }
    }

    /**
     * Callback after a successful execution of bulk request.
     */
    public void afterBulk(long executionId, BulkRequest request, BulkResponse response)
    {
        BulkProcessorEvent event = new BulkProcessorEvent();
        event.setEvent(EventType.AFTER_SUCCESSFUL_BULK);
        event.setExecutionId(executionId);
        event.setRequest(request);
        event.setResponse(response);
        event.setIndex(index);
        event.setType(type);
        EVENT_QUEUE.add(event);
        synchronized (EVENT_QUEUE)
        {
            EVENT_QUEUE.notify();
        }
    }

    /**
     * Callback after a failed execution of bulk request.
     *
     * Note that in case an instance of <code>InterruptedException</code> is passed, which means that request processing has been
     * cancelled externally, the thread's interruption status has been restored prior to calling this method.
     */
    public void afterBulk(long executionId, BulkRequest request, Throwable failure)
    {
        BulkProcessorEvent event = new BulkProcessorEvent();
        event.setEvent(EventType.AFTER_FAILURE_BULK);
        event.setExecutionId(executionId);
        event.setRequest(request);
        event.setFailure(failure);
        event.setIndex(index);
        event.setType(type);
        EVENT_QUEUE.add(event);
        synchronized (EVENT_QUEUE)
        {
            EVENT_QUEUE.notify();
        }
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
     * 对type进行赋值
     *
     * @param type
     */
    public void setType(String type)
    {
        this.type = type;
    }
}
