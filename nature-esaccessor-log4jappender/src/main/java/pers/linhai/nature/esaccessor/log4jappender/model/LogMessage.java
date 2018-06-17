/*
 * 项 目 名:  Storage Tool Service V100R001C00
 * 文 件 名:  pers.linhai.terminator.log.model.Log4j2InfoMessage.java
 * 版       权:  XXX Technologies Co., Ltd. Copyright 2017,  All rights reserved.
 * 描       述:  XXX PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * 修 改 人:  shinelon
 * 修改时间:  2017年8月12日
 * 修改内容:  创建
 */
package pers.linhai.nature.esaccessor.log4jappender.model;

import org.apache.logging.log4j.message.Message;

/**
 * 
 * 
 * @author  shinelon
 * @version  V100R001C00
 */
public class LogMessage implements Message
{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    
    private LogInfo logInfo;
    
    /** 
     * <默认构造函数>
     * @param logInfo
     */
    public LogMessage(LogInfo logInfo)
    {
        this.logInfo = logInfo;
    }

    /**
     * @return
     */
    public String getFormattedMessage()
    {
        return null;
    }

    /**
     * 
     *
     * @return
     */
    public String getFormat()
    {
        return null;
    }

    /**
     * 
     *
     * @return
     */
    public Object[] getParameters()
    {
        return null;
    }

    /**
     * 
     *
     * @return
     */
    public Throwable getThrowable()
    {
        return null;
    }

    /**
     * 返回 logInfo
     *
     * @return logInfo
     */
    public LogInfo getLogInfo()
    {
        return logInfo;
    }
}
