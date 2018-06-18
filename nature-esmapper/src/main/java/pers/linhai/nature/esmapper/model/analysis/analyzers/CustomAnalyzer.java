/*
 * 项 目 名:  Storage Tool Service V100R001C00
 * 文 件 名:  pers.linhai.esframework.model.analysis.analyzers.CustomAnalyzer.java
 * 版       权:  XXX Technologies Co., Ltd. Copyright 2017,  All rights reserved.
 * 描       述:  XXX PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * 修 改 人:  shinelon
 * 修改时间:  2017年6月11日
 * 修改内容:  创建
 */
package pers.linhai.nature.esmapper.model.analysis.analyzers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.common.xcontent.XContentBuilder;

import pers.linhai.nature.esmapper.exception.IndexConfigurationException;
import pers.linhai.nature.esmapper.model.analysis.templets.Analyzer;

/**
 * 
 * 自定义分词器
 * <pre>
 * 
 *   The custom analyzer accepts the following parameters:
 *      tokenizer
 *          A built-in or customised tokenizer. (Required)
 *   
 *      char_filter
 *          An optional array of built-in or customised character filters.
 *   
 *      filter
 *          An optional array of built-in or customised token filters.
 *   
 *      position_increment_gap
 *          When indexing an array of text values, Elasticsearch inserts a fake "gap" between the last term of one value and 
 *          the first term of the next value to ensure that a phrase query doesn’t match two terms from different array elements. 
 *          Defaults to 100. See position_increment_gap for more. 
 * 
 *   PUT my_index
 *   {
 *     "settings": {
 *       "analysis": {
 *         "analyzer": {
 *           "my_custom_analyzer": {
 *             "type": "custom",
 *             "char_filter": [
 *               "emoticons" 
 *             ],
 *             "tokenizer": "punctuation", 
 *             "filter": [
 *               "lowercase",
 *               "english_stop" 
 *             ]
 *           }
 *         },
 *         "tokenizer": {
 *           "punctuation": { 
 *             "type": "pattern",
 *             "pattern": "[ .,!?]"
 *           }
 *         },
 *         "char_filter": {
 *           "emoticons": { 
 *             "type": "mapping",
 *             "mappings": [
 *               ":) => _happy_",
 *               ":( => _sad_"
 *             ]
 *           }
 *         },
 *         "filter": {
 *           "english_stop": { 
 *             "type": "stop",
 *             "stopwords": "_english_"
 *           }
 *         }
 *       }
 *     }
 *   }
 * </pre>
 * @author  shinelon
 * @version  V100R001C00
 */
public class CustomAnalyzer extends Analyzer
{

    /**
     * 标记器:A built-in or customised tokenizer. (custom analyzer Required) 
     */
    protected String tokenizer;
    
    /**
     * <pre>
     * Analyzed text fields take term positions into account, in order to be able to support proximity or phrase queries. 
     * When indexing text fields with multiple values a "fake" gap is added between the values to prevent most phrase queries from matching across the values. 
     * The size of this gap is configured using position_increment_gap and defaults to 100.
     * </pre>
     */
    private int positionIncrementGap = -1;
    
    /**
     * An optional array of built-in or customised character filters. 
     */
    private List<String> charFilterList = new ArrayList<String>(6);
    
    /**
     * An optional array of built-in or customised token filters. 
     */
    private List<String> tokenFilterList = new ArrayList<String>(6);
    
    /** 
     * <默认构造函数>
     *
     * @param type
     */
    public CustomAnalyzer()
    {
        super("custom");
    }

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
        
        //封装停用词
        packStopwords(jsonBuilder);
        
        if(tokenizer == null)
        {
            throw new IndexConfigurationException("tokenizer can't be null in the CustomAnalyzer[ " + name + " ]");
        }
        jsonBuilder.field("tokenizer", tokenizer);
        
        if(positionIncrementGap > -1)
        {
            jsonBuilder.field("position_increment_gap", positionIncrementGap);
        }
        
        if(!charFilterList.isEmpty())
        {
            jsonBuilder.array("char_filter", charFilterList.toArray(new String[charFilterList.size()]));
        }
        
        if(!tokenFilterList.isEmpty())
        {
            jsonBuilder.array("filter", tokenFilterList.toArray(new String[tokenFilterList.size()]));
        }
        
        jsonBuilder.endObject();
    }

    /**
     * 添加一个字符串过滤器
     * 
     *
     * @param charFilter void
     */
    public void addCharFilter(String charFilter)
    {
        charFilterList.add(charFilter);
    }
    
    /**
     * 添加一个标记过滤器
     *
     * @param charFilter void
     */
    public void addTokenFilter(String tokenFilter)
    {
        tokenFilterList.add(tokenFilter);
    }
    
    /**
     * 返回 positionIncrementGap
     *
     * @return positionIncrementGap
     */
    public int getPositionIncrementGap()
    {
        return positionIncrementGap;
    }

    /**
     * 对positionIncrementGap进行赋值
     *
     * @param positionIncrementGap
     */
    public void setPositionIncrementGap(int positionIncrementGap)
    {
        this.positionIncrementGap = positionIncrementGap;
    }
    
    /**
     * 返回 tokenizer
     *
     * @return tokenizer
     */
    public String getTokenizer()
    {
        return tokenizer;
    }
    
    /**
     * 对tokenizer进行赋值
     *
     * @param tokenizer
     */
    public void setTokenizer(String tokenizer)
    {
        this.tokenizer = tokenizer;
    }
}
