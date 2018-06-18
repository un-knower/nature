/*
 * 项 目 名:  Storage Tool Service V100R001C00
 * 文 件 名:  pers.linhai.esframework.model.index.section.IndexSettings.java
 * 版       权:  XXX Technologies Co., Ltd. Copyright 2017,  All rights reserved.
 * 描       述:  XXX PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * 修 改 人:  shinelon
 * 修改时间:  2017年6月11日
 * 修改内容:  创建
 */
package pers.linhai.nature.esmapper.model.index;

import java.io.IOException;

import org.elasticsearch.common.xcontent.XContentBuilder;

import pers.linhai.nature.esmapper.model.index.section.AnalysisSection;
import pers.linhai.nature.esmapper.model.index.section.IndexSection;

/**
 * @author  shinelon
 * @version  V100R001C00
 */
public class IndexSettings
{
    /**
     * index区域
     */
    private IndexSection indexSection = new IndexSection();
    
    /**
     * 分词器配置区
     */
    private AnalysisSection analysisSection = new AnalysisSection();

    public boolean hasNewConfig()
    {
        return indexSection.hasNewConfig() || analysisSection.hasNewConfig();
    }
    
    /**
     * 通过输入的XContentBuilder将该分词器的配置封装回去
     * @param jsonBuilder
     * @throws IOException void
     */
    public void build(XContentBuilder jsonBuilder) throws IOException
    {
        if(indexSection.hasNewConfig())
        {
            jsonBuilder.startObject("index");
            indexSection.build(jsonBuilder);
            jsonBuilder.endObject();
        }
        
        if(analysisSection.hasNewConfig())
        {
            jsonBuilder.startObject("analysis");
            analysisSection.build(jsonBuilder);
            jsonBuilder.endObject();
        }
    }
    
    /**
     * 返回 indexSection
     *
     * @return indexSection
     */
    public IndexSection indexSection()
    {
        return indexSection;
    }

    /**
     * 返回 analysisSection
     * @return analysisSection
     */
    public AnalysisSection analysisSection()
    {
        return analysisSection;
    }
}
