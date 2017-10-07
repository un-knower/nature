/*
 * 项 目 名:  Storage Tool Service V100R001C00
 * 文 件 名:  pers.linhai.esframework.model.analysis.templets.Tokenizer.java
 * 版       权:  XXX Technologies Co., Ltd. Copyright 2017,  All rights reserved.
 * 描       述:  XXX PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * 修 改 人:  shinelon
 * 修改时间:  2017年6月11日
 * 修改内容:  创建
 */
package pers.linhai.nature.indexaccess.model.analysis.templets;

import java.io.IOException;

import org.elasticsearch.common.xcontent.XContentBuilder;

/**
 * 抽象标记器
 * 
 * @author  shinelon
 * @version  V100R001C00
 */
public abstract class Tokenizer
{
    
    /**
     * 标记器名字
     */
    protected String name;
    
    /**
     * 标记器类型
     */
    protected String type;
    
    /** 
     * <默认构造函数>
     *
     * @param type
     */
    public Tokenizer(String type)
    {
        this.type = type;
    }
    
    /**
     * 通过输入的XContentBuilder将该分词器的配置封装回去
     * @throws IOException 
     * @param jsonBuilder void
     */
    public abstract void build(XContentBuilder jsonBuilder) throws IOException;

    /**
     * 返回 name
     *
     * @return name
     */
    public String getName()
    {
        return name;
    }

    /**
     * 对name进行赋值
     *
     * @param name
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * 返回 type
     *
     * @return type
     */
    public String getType()
    {
        return type;
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
