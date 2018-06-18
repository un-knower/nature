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
package pers.linhai.nature.esmapper.model.analysis.analyzers;

import java.io.IOException;

import org.elasticsearch.common.xcontent.XContentBuilder;

import pers.linhai.nature.esmapper.model.analysis.templets.Analyzer;

/**
 * Stop停用词元分词器
 * @author  shinelon
 * @version  V100R001C00
 */
public class StopAnalyzer extends Analyzer
{

    /** 
     * <默认构造函数>
     */
    public StopAnalyzer()
    {
        super("stop");
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
        
        jsonBuilder.endObject();
    }
}
