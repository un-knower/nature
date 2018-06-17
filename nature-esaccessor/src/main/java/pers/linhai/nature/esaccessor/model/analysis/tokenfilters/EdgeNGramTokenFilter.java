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

/**
 * 
 * <pre>
    Edge NGram Token Filter
    
    A token filter of type edgeNGram.
    
    The following are settings that can be set for a edgeNGram token filter type:
    Setting     Description
    
    min_gram
        Defaults to 1.
    
    max_gram
        Defaults to 2.
    
    side
        deprecated. Either front or back. Defaults to front.
 * </pre>
 * @author  shinelon
 * @version  V100R001C00
 */
public class EdgeNGramTokenFilter extends NGramTokenFilter
{
    
    /**
     * deprecated. Either front or back. Defaults to front.
     */
    private String side;

    /** 
     * <默认构造函数>
     *
     * @param type
     */
    public EdgeNGramTokenFilter()
    {
        super("edgeNGram");
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
        
        if(side != null && "back,front".contains(side))
        {
            jsonBuilder.field("side", side);
        }
        
        jsonBuilder.endObject();
    }

    /**
     * 返回 side
     *
     * @return side
     */
    public String getSide()
    {
        return side;
    }

    /**
     * 对side进行赋值
     *
     * @param side
     */
    public void setSide(String side)
    {
        this.side = side;
    }
}
