/*
 * 项 目 名:  Storage Tool Service V100R001C00
 * 文 件 名:  pers.linhai.nature.indexaccess.core.ElasticsearchAppender.java
 * 版       权:  XXX Technologies Co., Ltd. Copyright 2017,  All rights reserved.
 * 描       述:  XXX PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * 修 改 人:  shinelon
 * 修改时间:  2017年7月3日
 * 修改内容:  创建
 */
package pers.linhai.nature.indexaccess.log4jappender.plugins;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.logging.Logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.DefaultConfiguration;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderFactory;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.pattern.DatePatternConverter;
import org.apache.logging.log4j.core.pattern.ExtendedThrowablePatternConverter;
import org.apache.logging.log4j.message.Message;

import pers.linhai.nature.indexaccess.core.AccessorFactory;
import pers.linhai.nature.indexaccess.core.BulkProcessorListener;
import pers.linhai.nature.indexaccess.interfaces.BulkOperation;
import pers.linhai.nature.indexaccess.interfaces.TypeAccessor;
import pers.linhai.nature.indexaccess.log4jappender.hook.IndexAccessShutdownHook;
import pers.linhai.nature.indexaccess.log4jappender.model.LogIndex;
import pers.linhai.nature.indexaccess.log4jappender.model.LogInfo;
import pers.linhai.nature.indexaccess.log4jappender.model.LogMessage;
import pers.linhai.nature.indexaccess.model.bulk.BulkProcessorConfiguration;
import pers.linhai.nature.indexaccess.model.datatypes.quote.DateType.Date;

/**
 * 
 * 
 * @author  shinelon
 * @version  V100R001C00
 */
@Plugin(name = "IndexAccess", category = "Core", elementType = Appender.ELEMENT_TYPE, printObject = true)
public class IndexAccessAppenderPlugin extends AbstractAppender
{
    private static TypeAccessor<LogInfo> logInfoAccessor;
    
    private static BulkOperation<LogInfo> logInfoBulkOperation;
    
    /**
     * ES批量信息处理器
     */
    private BulkProcessorConfigurationPlugin bulkProcessor;
    
    private IndexDynamicSettingsPlugin indexDynamicSettings;
    
    private IndexStaticSettingsPlugin indexStaticSettings;
    
    /**
     * 时间转换器
     */
    private DatePatternConverter datePatternConverter;
    
    /**
     * 异常信息转换器
     */
    private ExtendedThrowablePatternConverter throwablePatternConverter;
    
    /** 
     * <默认构造函数>
     * @param name
     * @param filter
     * @param layout
     */
    protected IndexAccessAppenderPlugin(String name, Filter filter, Layout< ? extends Serializable> layout)
    {
        super(name, filter, layout);
        try
        {
            datePatternConverter = DatePatternConverter.newInstance(new String[] {"DEFAULT"});
            Method extendedThrowablePatternConverterNewInstanceMethod = ExtendedThrowablePatternConverter.class.getDeclaredMethod("newInstance", Configuration.class, String[].class);
            throwablePatternConverter = (ExtendedThrowablePatternConverter)extendedThrowablePatternConverterNewInstanceMethod.invoke(null, new DefaultConfiguration(), null);
        }
        catch (NoSuchMethodException e)
        {
            try
            {
                Method extendedThrowablePatternConverterNewInstanceMethod = ExtendedThrowablePatternConverter.class.getDeclaredMethod("newInstance", String[].class);
                throwablePatternConverter = (ExtendedThrowablePatternConverter)extendedThrowablePatternConverterNewInstanceMethod.invoke(null, (Object) new String[] {});
            }
            catch (Exception e1)
            {
                e1.printStackTrace();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * @param event
     */
    public void append(LogEvent event)
    {
        if (logInfoBulkOperation == null)
        {
            Logger.getGlobal().info(event.getMessage().getFormattedMessage());
        }
        else
        {
            LogInfo li = null;
            Message message = event.getMessage();
            if (message instanceof LogMessage)
            {
                li = ((LogMessage)message).getLogInfo();
            }
            else
            {
                li = buildLogInfo(event, message);
            }
            
            if (li.getLogDate() == null)
            {
                li.setLogDate(new Date(System.currentTimeMillis()));
            }
            
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(li.getLogDate().getMilliseconds());
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);  
            calendar.set(Calendar.SECOND, 0);  
            calendar.set(Calendar.MILLISECOND, 0); 
            
            //设置日志发生时间的整点毫秒时间戳
            li.setWholePointTime(calendar.getTimeInMillis());
            
            if (!logInfoBulkOperation.isClosed() && li != null)
            {
                logInfoBulkOperation.add(li);
            }
        }
    }

    /**
     * <p>Title         : buildLogInfo lilinhai 2018年1月28日 下午11:26:00</p>
     * <p>Description   : <pre>TODO(这里用一句话描述这个方法的作用)</pre></p>
     * @param event
     * @param message
     * @return 
     * LogInfo 
     */ 
    private LogInfo buildLogInfo(LogEvent event, Message message)
    {
        LogInfo li;
        li = new LogInfo();
        StackTraceElement ste = event.getSource();
        if (ste != null)
        {
            li.setLocation(ste.toString());
        }
        li.setLevel(event.getLevel().name());
        li.setLoggerName(event.getLoggerName().trim().equals("") ? "root" : event.getLoggerName());
        li.setThread(event.getThreadName());
        StringBuilder sb = new StringBuilder();
        datePatternConverter.format(event.getTimeMillis(), sb);
        Date d = new Date(event.getTimeMillis());
        d.setFormatTime(sb.toString());
        li.setLogDate(d);
        sb.setLength(0);
        sb.append(message.getFormattedMessage()).append('\n');
        throwablePatternConverter.format(event, sb);
        li.setMessage(sb.toString());
        return li;
    }

    /**
     * 初始化ES
     * 
     * void
     */
    private void initLogInfoAccessor()
    {
        try
        {
            if (logInfoAccessor == null)
            {
                LogIndex.setIndexDynamicSettings(indexDynamicSettings);
                LogIndex.setIndexStaticSettings(indexStaticSettings);
                AccessorFactory.load(LogIndex.class);
                logInfoAccessor = AccessorFactory.typeAccessor(LogInfo.class);
                
                if (logInfoBulkOperation == null)
                {
                    if (bulkProcessor == null)
                    {
                        BulkProcessorConfiguration bulkProcessorConfiguration = bulkProcessor.to();
                        BulkProcessorListener bulkProcessorListener = new BulkProcessorListener();
                        bulkProcessorListener.setIndex(logInfoAccessor.indexName());
                        bulkProcessorListener.setType(logInfoAccessor.toString());
                        bulkProcessorConfiguration.setListener(bulkProcessorListener);
                        logInfoBulkOperation = logInfoAccessor.bulkOperations(bulkProcessorConfiguration);
                    }
                    else
                    {
                        logInfoBulkOperation = logInfoAccessor.bulkOperations();
                    }
                }
                
                Runtime.getRuntime().addShutdownHook(new IndexAccessShutdownHook(logInfoBulkOperation, (LoggerContext)LogManager.getContext(false)));
            }
        }
        catch (Throwable e) 
        {
            e.printStackTrace();
        }
    }
    
    /**
     * 判断应用程序是否停止
     * 
     *
     * @return boolean
     */
    public boolean isBulkClosed()
    {
        return logInfoBulkOperation == null || logInfoBulkOperation.isClosed() || isStopped() || isStopping();
    }
    
    @PluginBuilderFactory
    public static org.apache.logging.log4j.core.util.Builder<IndexAccessAppenderPlugin> create()
    {
        return new org.apache.logging.log4j.core.util.Builder<IndexAccessAppenderPlugin>()
        {
            /**
             * The name of Appender
             */
            @PluginBuilderAttribute
            private String name;
            
            @PluginElement("Filter")
            private Filter filter;
            
            @PluginElement("Layout")
            private Layout<? extends Serializable> layout;
            
            @PluginElement("BulkProcessor")
            private BulkProcessorConfigurationPlugin bulkProcessor;
            
            @PluginElement("IndexDynamicSettings")
            private IndexDynamicSettingsPlugin indexDynamicSettings;
            
            @PluginElement("IndexStaticSettings")
            private IndexStaticSettingsPlugin indexStaticSettings;
            
            /**
             * @return
             */
            public IndexAccessAppenderPlugin build()
            {
                IndexAccessAppenderPlugin elasticsearchAppender = new IndexAccessAppenderPlugin(name, filter, layout);
                elasticsearchAppender.bulkProcessor = bulkProcessor;
                elasticsearchAppender.indexDynamicSettings = indexDynamicSettings;
                elasticsearchAppender.indexStaticSettings = indexStaticSettings;
                elasticsearchAppender.initLogInfoAccessor();
                return elasticsearchAppender;
            }
        };
    }
    
    
}
