/*
 * 项 目 名:  Storage Tool Service V100R001C00
 * 文 件 名:  pers.linhai.esframework.model.analysis.tokenizers.StandardTokenizer.java
 * 版       权:  XXX Technologies Co., Ltd. Copyright 2017,  All rights reserved.
 * 描       述:  XXX PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * 修 改 人:  shinelon
 * 修改时间:  2017年6月11日
 * 修改内容:  创建
 */
package pers.linhai.nature.esmapper.model.analysis.tokenizers;

import java.io.IOException;

import org.elasticsearch.common.xcontent.XContentBuilder;

import pers.linhai.nature.esmapper.model.analysis.templets.Tokenizer;

/**
 * 
 * The uax_url_email tokenizer is like the standard tokenizer except that it recognises URLs and email addresses as single tokens.
 * @author  shinelon
 * @version  V100R001C00
 */
public class UaxUrlEmailTokenizer extends Tokenizer
{

    /**
     * The maximum token length. If a token is seen that exceeds this length then it is split at max_token_length intervals. Defaults to 255. 
     */
    private int maxTokenLength;
    
    /** 
     * <默认构造函数>
     */
    public UaxUrlEmailTokenizer()
    {
        super("uax_url_email");
    }

    /**
     * 
     *
     * @param jsonBuilder
     * @throws IOException
     */
    public void build(XContentBuilder jsonBuilder) throws IOException
    {
        jsonBuilder.startObject(name);
        jsonBuilder.field("type", type);
        
        if(maxTokenLength > 0)
        {
            jsonBuilder.field("max_token_length", maxTokenLength);
        }
        
        jsonBuilder.endObject();
    }

    /**
     * 返回 maxTokenLength
     *
     * @return maxTokenLength
     */
    public int getMaxTokenLength()
    {
        return maxTokenLength;
    }

    /**
     * 对maxTokenLength进行赋值
     *
     * @param maxTokenLength
     */
    public void setMaxTokenLength(int maxTokenLength)
    {
        this.maxTokenLength = maxTokenLength;
    }
    
}
