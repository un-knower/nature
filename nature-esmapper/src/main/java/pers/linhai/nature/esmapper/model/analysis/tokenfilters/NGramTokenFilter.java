/*
 * 项 目 名:  Storage Tool Service V100R001C00
 * 文 件 名:  pers.linhai.esframework.model.analysis.tokenfilters.ASCIIFoldingTokenFilter.java
 * 版       权:  XXX Technologies Co., Ltd. Copyright 2017,  All rights reserved.
 * 描       述:  XXX PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * 修 改 人:  shinelon
 * 修改时间:  2017年6月11日
 * 修改内容:  创建
 */
package pers.linhai.nature.esmapper.model.analysis.tokenfilters;

import java.io.IOException;

import org.elasticsearch.common.xcontent.XContentBuilder;

import pers.linhai.nature.esmapper.model.analysis.templets.TokenFilter;

/**
 * 
 * <pre>
        A token filter of type nGram.
        
        The following are settings that can be set for a nGram token filter type:
        Setting     Description
        
        min_gram
            
        
        Defaults to 1.
        
        max_gram
            
        
        Defaults to 2.
 * </pre>
 * @author  shinelon
 * @version  V100R001C00
 */
public class NGramTokenFilter extends TokenFilter
{
    
    /**
     * Defaults to 1.
     */
    protected int minGram;
    
    /**
     *  Defaults to 2.
     */
    protected int maxGram;
    
    /** 
     * <默认构造函数>
     *
     * @param type
     */
    public NGramTokenFilter(String type)
    {
        super(type);
    }

    /** 
     * <默认构造函数>
     *
     * @param type
     */
    public NGramTokenFilter()
    {
        this("nGram");
    }

    /**
     *
     * @param jsonBuilder
     * @throws IOException
     */
    public void build(XContentBuilder jsonBuilder) throws IOException
    {
        jsonBuilder.startObject(name);
        jsonBuilder.field("type", type);
        
        if(minGram > 0)
        {
            jsonBuilder.field("min_gram", minGram);
        }
        
        if(maxGram >= minGram)
        {
            jsonBuilder.field("max_gram", maxGram);
        }
        
        jsonBuilder.endObject();
    }

    /**
     * 返回 minGram
     *
     * @return minGram
     */
    public int getMinGram()
    {
        return minGram;
    }

    /**
     * 对minGram进行赋值
     *
     * @param minGram
     */
    public void setMinGram(int minGram)
    {
        this.minGram = minGram;
    }

    /**
     * 返回 maxGram
     *
     * @return maxGram
     */
    public int getMaxGram()
    {
        return maxGram;
    }

    /**
     * 对maxGram进行赋值
     *
     * @param maxGram
     */
    public void setMaxGram(int maxGram)
    {
        this.maxGram = maxGram;
    }
}
