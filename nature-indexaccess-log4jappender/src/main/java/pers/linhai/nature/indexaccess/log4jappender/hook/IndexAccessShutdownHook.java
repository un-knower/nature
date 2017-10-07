/*
 * 项 目 名:  Storage Tool Service V100R001C00
 * 文 件 名:  pers.linhai.nature.indexaccess.log4j2.Log4j2ShutdownHookThread.java
 * 版       权:  XXX Technologies Co., Ltd. Copyright 2017,  All rights reserved.
 * 描       述:  XXX PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * 修 改 人:  shinelon
 * 修改时间:  2017年7月16日
 * 修改内容:  创建
 */
package pers.linhai.nature.indexaccess.log4jappender.hook;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configurator;

import pers.linhai.nature.indexaccess.core.processor.ClusterNodeAccessor;
import pers.linhai.nature.indexaccess.interfaces.BulkOperation;
import pers.linhai.nature.indexaccess.log4jappender.model.LogInfo;

/**
 * 
 * 
 * @author  shinelon
 * @version  V100R001C00
 */
public class IndexAccessShutdownHook extends Thread
{

    private BulkOperation<LogInfo> logInfoBulkOperation;
    
    private LoggerContext loggerContext;
    
    /** 
     * <默认构造函数>
     *
     * @param logInfoBulkOperation BulkOperation<LogInfo>
     * @param loggerContext LoggerContext
     */
    public IndexAccessShutdownHook(BulkOperation<LogInfo> logInfoBulkOperation, LoggerContext loggerContext)
    {
        this.logInfoBulkOperation = logInfoBulkOperation;
        this.loggerContext = loggerContext;
    }

    /**
     * 
     *
     */
    public void run()
    {
        if (logInfoBulkOperation != null)
        {
            try
            {
                LogManager.getRootLogger().info("Shutting down log4j2 LoggerContext-IndexAccess, and closing ES-TransportClient-Application.");
                logInfoBulkOperation.flush();
                sleep(100);
                logInfoBulkOperation.close();
                ClusterNodeAccessor.instance().shutdown();
            }
            catch (Throwable e)
            {
                e.printStackTrace();
            }
        }
        
        if (loggerContext != null)
        {
            Configurator.shutdown(loggerContext);
        }
    }
    
}
