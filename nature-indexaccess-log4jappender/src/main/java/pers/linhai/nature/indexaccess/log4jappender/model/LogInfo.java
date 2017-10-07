/*
 * 项 目 名:  Storage Tool Service V100R001C00
 * 文 件 名:  pers.linhai.nature.indexaccess.log4j2.model.LogInfo.java
 * 版       权:  XXX Technologies Co., Ltd. Copyright 2017,  All rights reserved.
 * 描       述:  XXX PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * 修 改 人:  shinelon
 * 修改时间:  2017年7月7日
 * 修改内容:  创建
 */
package pers.linhai.nature.indexaccess.log4jappender.model;

import pers.linhai.nature.indexaccess.annotation.datatypes.KeywordField;
import pers.linhai.nature.indexaccess.annotation.datatypes.TextField;
import pers.linhai.nature.indexaccess.model.datatypes.quote.DateType.Date;
import pers.linhai.nature.indexaccess.model.enumer.Analyzer;
import pers.linhai.nature.indexaccess.model.type.Type;

/**
 * 日志信息
 * @author  shinelon
 * @version  V100R001C00
 */
public class LogInfo extends Type
{
    
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 日志记录所属上下文ID
     */
    @KeywordField
    private String contextId;

    /**
     * 日志当前小时整点时间，方便查看日志上下文
     */
    private long wholePointTime;
    
    /**
     * 同一个上下文中，同一个时间整点下的行号
     */
    private int lineNumber;
    
    /**
     * logger名字空间的全称
     */
    @TextField(analyzer = Analyzer.CUSTOM, customAnalyzer = "common_pattern_analyzer")
    private String loggerName;
    
    /**
     * 日志输出级别
     */
    @TextField(analyzer = Analyzer.CUSTOM, customAnalyzer = "common_pattern_analyzer")
    private String level;
    
    /**
     * 产生该日志的线程
     */
    @TextField(analyzer = Analyzer.CUSTOM, customAnalyzer = "common_pattern_analyzer")
    private String thread;
    
    /**
     * 输出日志事件的发生位置，包括类目名、发生的线程，以及在代码中的行数
     */
    @TextField(analyzer = Analyzer.CUSTOM, customAnalyzer = "common_pattern_analyzer")
    private String location;
    
    /**
     * 输出消息
     */
    @TextField(analyzer = Analyzer.CUSTOM, customAnalyzer = "common_pattern_analyzer")
    private String message;
    
    /**
     * 日志发生时间
     */
    private Date logDate;
    
    
    /**
     * 返回 contextId
     *
     * @return contextId
     */
    public String getContextId()
    {
        return contextId;
    }

    /**
     * 对contextId进行赋值
     *
     * @param contextId
     */
    public void setContextId(String contextId)
    {
        this.contextId = contextId;
    }

    /**
     * 返回 loggerName
     *
     * @return loggerName
     */
    public String getLoggerName()
    {
        return loggerName;
    }
    
    /**
     * 对loggerName进行赋值
     *
     * @param loggerName
     */
    public void setLoggerName(String loggerName)
    {
        this.loggerName = loggerName;
    }
    
    /**
     * 返回 level
     *
     * @return level
     */
    public String getLevel()
    {
        return level;
    }
    
    /**
     * 对level进行赋值
     *
     * @param level
     */
    public void setLevel(String level)
    {
        this.level = level;
    }
    
    /**
     * 返回 thread
     *
     * @return thread
     */
    public String getThread()
    {
        return thread;
    }
    
    /**
     * 对thread进行赋值
     *
     * @param thread
     */
    public void setThread(String thread)
    {
        this.thread = thread;
    }
    
    /**
     * 返回 location
     *
     * @return location
     */
    public String getLocation()
    {
        return location;
    }
    
    /**
     * 对location进行赋值
     *
     * @param location
     */
    public void setLocation(String location)
    {
        this.location = location;
    }
    
    /**
     * 返回 message
     *
     * @return message
     */
    public String getMessage()
    {
        return message;
    }
    
    /**
     * 对message进行赋值
     *
     * @param message
     */
    public void setMessage(String message)
    {
        this.message = message;
    }
    
    /**
     * 返回 date
     *
     * @return logDate
     */
    public Date getLogDate()
    {
        return logDate;
    }

    /**
     * 对date进行赋值
     *
     * @param logDate
     */
    public void setLogDate(Date logDate)
    {
        this.logDate = logDate;
    }

    /**
     * 返回 lineNumber
     *
     * @return lineNumber
     */
    public int getLineNumber()
    {
        return lineNumber;
    }

    /**
     * 对lineNumber进行赋值
     *
     * @param lineNumber
     */
    public void setLineNumber(int lineNumber)
    {
        this.lineNumber = lineNumber;
    }

    /**
     * 返回 wholePointTime
     *
     * @return wholePointTime
     */
    public long getWholePointTime()
    {
        return wholePointTime;
    }

    /**
     * 对wholePointTime进行赋值
     *
     * @param wholePointTime
     */
    public void setWholePointTime(long wholePointTime)
    {
        this.wholePointTime = wholePointTime;
    }

    /**
     * @return
     */
    public String toString()
    {
        return "LogInfo [contextId=" + contextId + ", wholePointTime=" + wholePointTime + ", lineNumber=" 
                + lineNumber + ", loggerName=" + loggerName + ", level=" + level + ", thread=" + thread
                + ", location=" + location + ", message=" + message + ", logDate=" + logDate + "]";
    }
}
