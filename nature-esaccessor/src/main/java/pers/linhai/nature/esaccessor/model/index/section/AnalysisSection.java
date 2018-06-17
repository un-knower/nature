/*
 * 项 目 名:  Storage Tool Service V100R001C00
 * 文 件 名:  pers.linhai.esframework.model.index.section.AnalysisSection.java
 * 版       权:  XXX Technologies Co., Ltd. Copyright 2017,  All rights reserved.
 * 描       述:  XXX PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * 修 改 人:  shinelon
 * 修改时间:  2017年6月10日
 * 修改内容:  创建
 */
package pers.linhai.nature.esaccessor.model.index.section;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.common.xcontent.XContentBuilder;

import pers.linhai.nature.esaccessor.model.analysis.templets.Analyzer;
import pers.linhai.nature.esaccessor.model.analysis.templets.CharFilter;
import pers.linhai.nature.esaccessor.model.analysis.templets.Normalizer;
import pers.linhai.nature.esaccessor.model.analysis.templets.TokenFilter;
import pers.linhai.nature.esaccessor.model.analysis.templets.Tokenizer;

/**
 * 分析区
 * @author  shinelon
 * @version  V100R001C00
 */
public class AnalysisSection
{
    /**
     * 自定义分词器集合
     */
    private List<Analyzer> analyzerList = new ArrayList<Analyzer>(5);
    
    /**
     * 自定义char过滤器集合
     */
    private List<CharFilter> charFilterList = new ArrayList<CharFilter>(5);
    
    /**
     * 自定义Normalizer集合
     */
    private List<Normalizer> normalizerList = new ArrayList<Normalizer>(5);
    
    /**
     * 自定义token过滤器集合
     */
    private List<TokenFilter> tokenFilterList = new ArrayList<TokenFilter>(5);
    
    /**
     * 自定义Tokenizer集合
     */
    private List<Tokenizer> tokenizerList = new ArrayList<Tokenizer>(5);

    /**
     * 是否存在新的配置文件
     *
     * @return boolean
     */
    public boolean hasNewConfig()
    {
        return !analyzerList.isEmpty() 
                || !charFilterList.isEmpty() 
                || !normalizerList.isEmpty() 
                || !tokenFilterList.isEmpty() 
                || !tokenFilterList.isEmpty();
    }
    
    /**
     * 通过输入的XContentBuilder将该分词器的配置封装回去
     * @param jsonBuilder
     * @throws IOException void
     */
    public void build(XContentBuilder jsonBuilder) throws IOException
    {
        //组装analyzer
        if(!analyzerList.isEmpty())
        {
            jsonBuilder.startObject("analyzer");
            for (Analyzer analyzer : analyzerList)
            {
                analyzer.build(jsonBuilder);
            }
            jsonBuilder.endObject();
        }
        
        //组装char_filter
        if(!charFilterList.isEmpty())
        {
            jsonBuilder.startObject("char_filter");
            for (CharFilter charFilter : charFilterList)
            {
                charFilter.build(jsonBuilder);
            }
            jsonBuilder.endObject();
        }
        
        //组装normalizer
        if(!normalizerList.isEmpty())
        {
            jsonBuilder.startObject("normalizer");
            for (Normalizer normalizer : normalizerList)
            {
                normalizer.build(jsonBuilder);
            }
            jsonBuilder.endObject();
        }
        
        //组装filter
        if(!tokenFilterList.isEmpty())
        {
            jsonBuilder.startObject("filter");
            for (TokenFilter tokenFilter : tokenFilterList)
            {
                tokenFilter.build(jsonBuilder);
            }
            jsonBuilder.endObject();
        }
        
        //组装tokenizer
        if(!tokenizerList.isEmpty())
        {
            jsonBuilder.startObject("tokenizer");
            for (Tokenizer tokenizer : tokenizerList)
            {
                tokenizer.build(jsonBuilder);
            }
            jsonBuilder.endObject();
        }
    }
    
    /**
     * 对analyzerList进行赋值
     *
     * @param analyzer
     */
    public void addAnalyzer(Analyzer analyzer)
    {
        this.analyzerList.add(analyzer);
    }

    /**
     * 对charFilterList进行赋值
     *
     * @param charFilter
     */
    public void addCharFilter(CharFilter charFilter)
    {
        this.charFilterList.add(charFilter);
    }

    /**
     * 对normalizerList进行赋值
     *
     * @param normalizer
     */
    public void addNormalizer(Normalizer normalizer)
    {
        this.normalizerList.add(normalizer);
    }

    /**
     * 对tokenFilterList进行赋值
     *
     * @param tokenFilter
     */
    public void addTokenFilter(TokenFilter tokenFilter)
    {
        this.tokenFilterList.add(tokenFilter);
    }

    /**
     * 对tokenizerList进行赋值
     *
     * @param tokenizer
     */
    public void addTokenizer(Tokenizer tokenizer)
    {
        this.tokenizerList.add(tokenizer);
    }
}
