/*
 * 项 目 名:  Storage Tool Service V100R001C00
 * 文 件 名:  pers.linhai.esframework.model.analysis.tokenizers.KeywordTokenizer.java
 * 版       权:  XXX Technologies Co., Ltd. Copyright 2017,  All rights reserved.
 * 描       述:  XXX PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * 修 改 人:  shinelon
 * 修改时间:  2017年6月11日
 * 修改内容:  创建
 */
package pers.linhai.nature.indexaccess.model.analysis.tokenizers;

import java.io.IOException;

import org.elasticsearch.common.xcontent.XContentBuilder;

import pers.linhai.nature.indexaccess.model.analysis.templets.Tokenizer;

/**
 * 
 * 
 * @author  shinelon
 * @version  V100R001C00
 */
public class KeywordTokenizer extends Tokenizer
{
    
    /**
     * The number of characters read into the term buffer in a single pass. Defaults to 256. 
     * The term buffer will grow by this size until all the text has been consumed. It is advisable not to change this setting. 
     */
    private int bufferSize;

    /** 
     * <默认构造函数>
     *
     * @param type
     */
    public KeywordTokenizer()
    {
        super("keyword");
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
        
        if(bufferSize > 0)
        {
            jsonBuilder.field("buffer_size", bufferSize);
        }
        
        jsonBuilder.endObject();
    }

    /**
     * 返回 bufferSize
     *
     * @return bufferSize
     */
    public int getBufferSize()
    {
        return bufferSize;
    }

    /**
     * 对bufferSize进行赋值
     *
     * @param bufferSize
     */
    public void setBufferSize(int bufferSize)
    {
        this.bufferSize = bufferSize;
    }
}
