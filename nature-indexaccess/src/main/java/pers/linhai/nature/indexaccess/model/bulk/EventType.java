/*
 * 项 目 名:  Storage Tool Service V100R001C00
 * 文 件 名:  pers.linhai.nature.indexaccess.model.bulk.EventType.java
 * 版       权:  XXX Technologies Co., Ltd. Copyright 2017,  All rights reserved.
 * 描       述:  XXX PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * 修 改 人:  shinelon
 * 修改时间:  2017年7月16日
 * 修改内容:  创建
 */
package pers.linhai.nature.indexaccess.model.bulk;

import org.apache.logging.log4j.LogManager;

import pers.linhai.nature.utils.StringUtils;

/**
 * 
 * 
 * @author  shinelon
 * @version  V100R001C00
 */
public enum EventType
{
    
    /**
     * Callback before the bulk is executed.
     */
    BEFORE_BULK(0, "before_bulk")
    {
        public void process(BulkProcessorEvent event)
        {
            if (event.getRequest().numberOfActions() > 2)
            {
                StringBuilder messageBuilder = new StringBuilder();
                messageBuilder.append("Index: ").append(event.getIndex()).append('\n');
                messageBuilder.append("Type: ").append(event.getType()).append('\n');
                messageBuilder.append("ExecutionId: ").append(event.getExecutionId()).append('\n');
                messageBuilder.append("Event: ").append(this.getName()).append("(").append(getValue()).append(")").append('\n');
                messageBuilder.append("NumberOfActions: ").append(event.getRequest().numberOfActions()).append('\n');
                if (!StringUtils.isEmpty(event.getRequest().getDescription()))
                {
                    messageBuilder.append("Description: ").append(event.getRequest().getDescription()).append('\n');
                }
                LogManager.getRootLogger().info(messageBuilder);
            }
        }
    },
    
    /**
     * Callback after a successful execution of bulk request.
     */
    AFTER_SUCCESSFUL_BULK(200, "after_successful_bulk")
    {
        public void process(BulkProcessorEvent event)
        {
            if (event.getRequest().numberOfActions() > 2 && event.getResponse().hasFailures())
            {
                StringBuilder messageBuilder = new StringBuilder();
                messageBuilder.append("Index: ").append(event.getIndex()).append('\n');
                messageBuilder.append("Type: ").append(event.getType()).append('\n');
                messageBuilder.append("ExecutionId: ").append(event.getExecutionId()).append('\n');
                messageBuilder.append("Event: ").append(this.getName()).append("(").append(getValue()).append(")").append('\n');
                messageBuilder.append("NumberOfActions: ").append(event.getRequest().numberOfActions()).append('\n');
                if (event.getResponse() != null && event.getResponse().hasFailures())
                {
                    messageBuilder.append("FailureMessage: ").append(event.getResponse().buildFailureMessage()).append('\n');
                }
                if (!StringUtils.isEmpty(event.getRequest().getDescription()))
                {
                    messageBuilder.append("Description: ").append(event.getRequest().getDescription()).append('\n');
                }
                LogManager.getRootLogger().info(messageBuilder);
            }
        }
    },
    
    /**
     * Callback after a failed execution of bulk request.
     *
     * Note that in case an instance of <code>InterruptedException</code> is passed, which means that request processing has been
     * cancelled externally, the thread's interruption status has been restored prior to calling this method.
     */
    AFTER_FAILURE_BULK(500, "after_failure_bulk")
    {
        public void process(BulkProcessorEvent event)
        {
            StringBuilder messageBuilder = new StringBuilder();
            messageBuilder.append("Index: ").append(event.getIndex()).append('\n');
            messageBuilder.append("Type: ").append(event.getType()).append('\n');
            messageBuilder.append("ExecutionId: ").append(event.getExecutionId()).append('\n');
            messageBuilder.append("Event: ").append(this.getName()).append("(").append(getValue()).append(")").append('\n');
            messageBuilder.append("NumberOfActions: ").append(event.getRequest().numberOfActions()).append('\n');
            if (event.getResponse() != null && event.getResponse().hasFailures())
            {
                messageBuilder.append("FailureMessage: ").append(event.getResponse().buildFailureMessage()).append('\n');
            }
            LogManager.getRootLogger().error(messageBuilder, event.getFailure());
        }
    };
    
    /**
     * 事件值
     */
    private int value;
    
    /**
     * 事件名
     */
    private String name;
    
    /** 
     * <默认构造函数>
     *
     * @param value
     * @param name
     */
    private EventType(int value, String name)
    {
        this.value = value;
        this.name = name;
    }
    
    public abstract void process(BulkProcessorEvent event);
    
    /**
     * 返回 value
     *
     * @return value
     */
    public int getValue()
    {
        return value;
    }
    
    /**
     * 返回 name
     *
     * @return name
     */
    public String getName()
    {
        return name;
    }
    
}
