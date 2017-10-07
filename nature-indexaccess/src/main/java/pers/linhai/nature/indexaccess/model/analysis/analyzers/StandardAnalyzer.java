/*
 * 项 目 名:  Storage Tool Service V100R001C00
 * 文 件 名:  pers.linhai.esframework.model.analysis.analyzers.StandardAnalyzer.java
 * 版       权:  XXX Technologies Co., Ltd. Copyright 2017,  All rights reserved.
 * 描       述:  XXX PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * 修 改 人:  shinelon
 * 修改时间:  2017年6月11日
 * 修改内容:  创建
 */
package pers.linhai.nature.indexaccess.model.analysis.analyzers;

import java.io.IOException;

import org.elasticsearch.common.xcontent.XContentBuilder;

import pers.linhai.nature.indexaccess.model.analysis.templets.Analyzer;

/**
 * 标准分词器
 * 编译好的分词器，其实都有对应的标记器tokenizer（对应已编译好的标记器）
 * @author  shinelon
 * @version  V100R001C00
 */
public class StandardAnalyzer extends Analyzer
{

    
    /**
     * The maximum token length. If a token is seen that exceeds this length then it is split at max_token_length intervals. Defaults to 255. 
     */
    private int maxTokenLength;

    /** 
     * <默认构造函数>
     */
    public StandardAnalyzer()
    {
        super("standard");
    }

    /**
     *
     * @param jsonBuilder
     * @throws IOException 
     */
    public void build(XContentBuilder jsonBuilder) throws IOException
    {
        packName(jsonBuilder);
        jsonBuilder.field("type", type);
        
        //封装停用词
        packStopwords(jsonBuilder);
        
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
