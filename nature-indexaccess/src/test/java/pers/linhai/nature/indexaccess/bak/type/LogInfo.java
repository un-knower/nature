/*
 * 项 目 名:  Storage Tool Service V100R001C00
 * 文 件 名:  esdao.esdao.pojo.User.java
 * 版       权:  XXX Technologies Co., Ltd. Copyright 2017,  All rights reserved.
 * 描       述:  XXX PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * 修 改 人:  shinelon
 * 修改时间:  2017年4月9日
 * 修改内容:  创建
 */
package pers.linhai.nature.indexaccess.bak.type;

import pers.linhai.nature.indexaccess.annotation.datatypes.NumericField;
import pers.linhai.nature.indexaccess.annotation.datatypes.TextField;
import pers.linhai.nature.indexaccess.model.enumer.Analyzer;
import pers.linhai.nature.indexaccess.model.enumer.Index;
import pers.linhai.nature.indexaccess.model.enumer.Store;
import pers.linhai.nature.indexaccess.model.type.Type;

/**
 * 
 * 
 * @author  shinelon
 * @version  V100R001C00
 */
public class LogInfo extends Type
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @NumericField(index=Index.TRUE, store=Store.TRUE)
    private long date;
    
    @TextField(analyzer=Analyzer.STANDARD)
    private String log;

    /**
     * 返回 date
     *
     * @return date
     */
    public long getDate()
    {
        return date;
    }

    /**
     * 返回 log
     *
     * @return log
     */
    public String getLog()
    {
        return log;
    }

    /**
     * 对date进行赋值
     *
     * @param date
     */
    public void setDate(long date)
    {
        this.date = date;
    }

    /**
     * 对log进行赋值
     *
     * @param log
     */
    public void setLog(String log)
    {
        this.log = log;
    }

    
}
