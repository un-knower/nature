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
package pers.linhai.nature.esaccessor.model.analysis.tokenfilters;

import java.io.IOException;

import org.elasticsearch.common.xcontent.XContentBuilder;

import pers.linhai.nature.esaccessor.model.analysis.templets.TokenFilter;

/**
 * 
 * <pre>
    A token filter of type length that removes words that are too long or too short for the stream.
    
    The following are settings that can be set for a length token filter type:
    Setting     Description
    
    min
        
    
    The minimum number. Defaults to 0.
    
    max
        
    
    The maximum number. Defaults to Integer.MAX_VALUE.
 * </pre>
 * @author  shinelon
 * @version  V100R001C00
 */
public class LengthTokenFilter extends TokenFilter
{
    
    /**
     * The minimum number. Defaults to 0.
     */
    private int min;
    
    /**
     * The maximum number. Defaults to Integer.MAX_VALUE.
     */
    private int max;
    
    /** 
     * <默认构造函数>
     *
     * @param type
     */
    public LengthTokenFilter()
    {
        super("length");
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
        
        if(min > 0)
        {
            jsonBuilder.field("min", min);
        }
        
        if(max >= min)
        {
            jsonBuilder.field("max", max);
        }
        
        jsonBuilder.endObject();
    }

    /**
     * 返回 min
     *
     * @return min
     */
    public int getMin()
    {
        return min;
    }

    /**
     * 对min进行赋值
     *
     * @param min
     */
    public void setMin(int min)
    {
        this.min = min;
    }

    /**
     * 返回 max
     *
     * @return max
     */
    public int getMax()
    {
        return max;
    }

    /**
     * 对max进行赋值
     *
     * @param max
     */
    public void setMax(int max)
    {
        this.max = max;
    }
}
