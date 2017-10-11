/*
 * 项 目 名:  Storage Tool Service V100R001C00
 * 文 件 名:  pers.linhai.nature.indexaccess.Test4.java
 * 版       权:  XXX Technologies Co., Ltd. Copyright 2017,  All rights reserved.
 * 描       述:  XXX PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * 修 改 人:  shinelon
 * 修改时间:  2017年7月24日
 * 修改内容:  创建
 */
package pers.linhai.nature.indexaccess.log4jappender.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import pers.linhai.nature.indexaccess.log4jappender.LogApplication;
import pers.linhai.nature.indexaccess.log4jappender.model.LogInfo;
import pers.linhai.nature.indexaccess.log4jappender.model.LogMessage;
import pers.linhai.nature.indexaccess.log4jappender.test.exception.JunitTestException;

/**
 * 
 * 
 * @author  shinelon
 * @version  V100R001C00
 */
public class IndexAccessAppenderTest
{
    @BeforeClass
    public static void beforeClass()
    {
        try
        {
            LogApplication.initialize();
        }
        catch (Throwable e)
        {
            throw new JunitTestException("IndexAccessAppenderTest.before occor an error.", e);
        }
    }
    
    @Test
    public void addLogMessage()
    {
        Logger LOG = LogManager.getLogger(IndexAccessAppenderTest.class);
        LogInfo loginfo = new LogInfo();
        loginfo.setMessage("test msg");
        loginfo.setLoggerName("testroot");
        LOG.info(new LogMessage(loginfo));
    }
    
    @Test
    public void addMessage()
    {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10; i++)
        {
            LogManager.getRootLogger().info("test string msg " + i);
        }
        System.out.println("Adding 100 log record spends time : " + (System.currentTimeMillis() - start));
    }
    
    @AfterClass
    public static void afterClass()
    {
    }
}
