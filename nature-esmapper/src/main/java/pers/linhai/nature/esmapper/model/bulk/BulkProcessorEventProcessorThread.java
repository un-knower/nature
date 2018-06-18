/*
 * 项 目 名:  Storage Tool Service V100R001C00
 * 文 件 名:  pers.linhai.nature.indexaccess.model.core.BulkProcessorEventProcessorThread.java
 * 版       权:  XXX Technologies Co., Ltd. Copyright 2017,  All rights reserved.
 * 描       述:  XXX PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * 修 改 人:  shinelon
 * 修改时间:  2017年7月16日
 * 修改内容:  创建
 */
package pers.linhai.nature.esmapper.model.bulk;

import java.util.Queue;

/**
 * 
 * 
 * @author  shinelon
 * @version  V100R001C00
 */
public class BulkProcessorEventProcessorThread extends Thread
{
    
    /**
     * 日志消息队列队列
     */
    private final Queue<BulkProcessorEvent> eventQueue;

    /** 
     * <默认构造函数>
     * @param eventQueue
     */
    public BulkProcessorEventProcessorThread(Queue<BulkProcessorEvent> eventQueue)
    {
        super();
        this.eventQueue = eventQueue;
        setDaemon(true);
        setName("bulkprocessor-event-processor-thread");
    }
    
    public void run()
    {
        while (true)
        {
            try
            {
                if (eventQueue.isEmpty())
                {
                    synchronized (eventQueue)
                    {
                        if (eventQueue.isEmpty())
                        {
                            eventQueue.wait();
                        }
                    }
                }

                //处理日志消息
                doEventLog(eventQueue.poll());
            }
            catch(Throwable e)
            {
                e.printStackTrace();
            }
        }
       
    }
    
    private void doEventLog(BulkProcessorEvent event)
    {
        if (event != null)
        {
            event.process();
        }
    }
}
