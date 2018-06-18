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
 *      A token filter of type asciifolding that converts alphabetic, numeric, 
 *      and symbolic Unicode characters which are not in the first 127 ASCII characters 
 *      (the "Basic Latin" Unicode block) into their ASCII equivalents, if one exists. Example:
 *   PUT /asciifold_example
 *   {
 *       "settings" : {
 *           "analysis" : {
 *               "analyzer" : {
 *                   "default" : {
 *                       "tokenizer" : "standard",
 *                       "filter" : ["standard", "asciifolding"]
 *                   }
 *               }
 *           }
 *       }
 *   }
 *   
 *   Accepts preserve_original setting which defaults to false but if true will keep the original token as well as emit the folded token. For example:
 *   
 *   PUT /asciifold_example
 *   {
 *       "settings" : {
 *           "analysis" : {
 *               "analyzer" : {
 *                   "default" : {
 *                       "tokenizer" : "standard",
 *                       "filter" : ["standard", "my_ascii_folding"]
 *                   }
 *               },
 *               "filter" : {
 *                   "my_ascii_folding" : {
 *                       "type" : "asciifolding",
 *                       "preserve_original" : true
 *                   }
 *               }
 *           }
 *       }
 *   }
 * </pre>
 * @author  shinelon
 * @version  V100R001C00
 */
public class ASCIIFoldingTokenFilter extends TokenFilter
{
    
    /**
     * setting which defaults to false but if true will keep the original token as well as emit the folded token. For example:
     */
    private boolean preserveOriginal;
    
    /** 
     * <默认构造函数>
     *
     * @param type
     */
    public ASCIIFoldingTokenFilter()
    {
        super("asciifolding");
    }

    /**
     * 
     *
     * @param jsonBuilder
     * @throws IOException
     */
    @Override
    public void build(XContentBuilder jsonBuilder) throws IOException
    {
        jsonBuilder.startObject(name);
        jsonBuilder.field("type", type);
        
        if(preserveOriginal)
        {
            jsonBuilder.field("preserve_original", preserveOriginal);
        }
        
        jsonBuilder.endObject();
    }

    /**
     * 返回 preserveOriginal
     *
     * @return preserveOriginal
     */
    public boolean isPreserveOriginal()
    {
        return preserveOriginal;
    }

    /**
     * 对preserveOriginal进行赋值
     *
     * @param preserveOriginal
     */
    public void setPreserveOriginal(boolean preserveOriginal)
    {
        this.preserveOriginal = preserveOriginal;
    }
}
