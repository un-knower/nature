/*
 * 项 目 名:  Storage Tool Service V100R001C00
 * 文 件 名:  pers.linhai.esframework.model.analysis.analyzers.FingerprintAnalyzer.java
 * 版       权:  XXX Technologies Co., Ltd. Copyright 2017,  All rights reserved.
 * 描       述:  XXX PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * 修 改 人:  shinelon
 * 修改时间:  2017年6月11日
 * 修改内容:  创建
 */
package pers.linhai.nature.esaccessor.model.analysis.analyzers;

import java.io.IOException;

import org.elasticsearch.common.xcontent.XContentBuilder;

import pers.linhai.nature.esaccessor.model.analysis.templets.Analyzer;

/**
 * 
 * 
 * @author  shinelon
 * @version  V100R001C00
 */
public class FingerprintAnalyzer extends Analyzer
{
    
    /** 
     * <默认构造函数>
     *
     * @param type
     */
    public FingerprintAnalyzer()
    {
        super("fingerprint");
    }

    /**
     * The character to use to concate the terms. Defaults to a space. 
     */
    private String separator;
    
    /**
     * The maximum token size to emit. Defaults to 255. Tokens larger than this size will be discarded. 
     */
    private int maxOutputSize;

    /**
     * 
     *
     * @param jsonBuilder
     * @throws IOException
     */
    public void build(XContentBuilder jsonBuilder) throws IOException
    {
        packName(jsonBuilder);
        jsonBuilder.field("type", type);
        
        if(separator != null)
        {
            jsonBuilder.field("separator", separator);
        }
        
        if(maxOutputSize > 0)
        {
            jsonBuilder.field("max_output_size", maxOutputSize);
        }
        
        //封装停用词
        packStopwords(jsonBuilder);
        
        jsonBuilder.endObject();
    }
    
}
