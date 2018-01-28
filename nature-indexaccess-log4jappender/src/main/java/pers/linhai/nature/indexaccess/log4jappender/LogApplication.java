/*
 * 项 目 名:  Storage Tool Service V100R001C00
 * 文 件 名:  pers.linhai.nature.indexaccess.model.Log4j2Application.java
 * 版       权:  XXX Technologies Co., Ltd. Copyright 2017,  All rights reserved.
 * 描       述:  XXX PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * 修 改 人:  shinelon
 * 修改时间:  2017年7月16日
 * 修改内容:  创建
 */
package pers.linhai.nature.indexaccess.log4jappender;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.async.AsyncLoggerContextSelector;
import org.apache.logging.log4j.core.config.ConfigurationFactory;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.plugins.util.PluginManager;

import pers.linhai.nature.indexaccess.log4jappender.plugins.IndexAccessAppenderPlugin;
import pers.linhai.nature.utils.ResourceUtils;

/**
 * 
 * 
 * @author  shinelon
 * @version  V100R001C00
 */
public abstract class LogApplication
{
    
    private static final LogApplication INSTANCE = new LogApplication(){};
    
    /**
     * log4j2上下文，未从配置初始化，还是虚拟上下文
     */
    private final LoggerContext loggerContext;
    
    private IndexAccessAppenderPlugin indexAccessAppenderPlugin;
    
    private LogApplication()
    {
        PluginManager.addPackage(IndexAccessAppenderPlugin.class.getPackage().getName());
        System.setProperty("Log4jContextSelector", AsyncLoggerContextSelector.class.getName());
        loggerContext = (LoggerContext)LogManager.getContext(false);
    }

    public static void initialize() throws IOException
    {
        initialize("classpath:log4j2.properties");
    }
    
    public static void initialize(URL confUrl) throws IOException
    {
        INSTANCE._initialize(confUrl);
    }
    
    public static void initialize(String resource) throws IOException
    {
        Objects.requireNonNull(resource, "Log4j2 configuration resource can't be null.");
        if(!resource.startsWith(ResourceUtils.CLASSPATH_URL_PREFIX))
        {
            resource = ResourceUtils.CLASSPATH_URL_PREFIX + resource;
        }
        URL in = ResourceUtils.getURL(resource);
        INSTANCE._initialize(in);
    }
    
    private void _initialize(URL confUrl) throws IOException
    {
        Objects.requireNonNull(confUrl, "Log4j2 configuration source can't be null.");
        ConfigurationSource source = new ConfigurationSource(confUrl.openStream(), confUrl);
        Configurator.initialize(ConfigurationFactory.getInstance().getConfiguration(loggerContext, source)).start();
        LogManager.getRootLogger().info("Log4j2-ApplicationContext initialized successfully.");
    }
    
    public static boolean isStoped()
    {
        return INSTANCE.loggerContext.isStopping() 
                || INSTANCE.loggerContext.isStopped() 
                || (INSTANCE.indexAccessAppenderPlugin != null && INSTANCE.indexAccessAppenderPlugin.isBulkClosed());
    }
}
