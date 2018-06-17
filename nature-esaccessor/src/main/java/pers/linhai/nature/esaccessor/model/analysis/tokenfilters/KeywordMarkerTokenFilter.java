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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.elasticsearch.common.xcontent.XContentBuilder;

import pers.linhai.nature.esaccessor.model.analysis.templets.TokenFilter;

/**
 * 
 * <pre>
        Protects words from being modified by stemmers. Must be placed before any stemming filters.
        Setting     Description
        
        keywords
            A list of words to use.
        
        keywords_path
            A path (either relative to config location, or absolute) to a list of words.
        
        keywords_pattern
            A regular expression pattern to match against words in the text.
        
        ignore_case
            Set to true to lower case all words first. Defaults to false.
        
        You can configure it like:
        
        PUT /keyword_marker_example
        {
          "settings": {
            "analysis": {
              "analyzer": {
                "protect_cats": {
                  "type": "custom",
                  "tokenizer": "standard",
                  "filter": ["lowercase", "protect_cats", "porter_stem"]
                },
                "normal": {
                  "type": "custom",
                  "tokenizer": "standard",
                  "filter": ["lowercase", "porter_stem"]
                }
              },
              "filter": {
                "protect_cats": {
                  "type": "keyword_marker",
                  "keywords": ["cats"]
                }
              }
            }
          }
        }
 * </pre>
 * @author  shinelon
 * @version  V100R001C00
 */
public class KeywordMarkerTokenFilter extends TokenFilter
{
    
    /**
     * A list of words to use.
     */
    private List<String> keywordList = new ArrayList<String>();
    
    /**
     * A path (either relative to config location, or absolute) to a list of words.
     */
    private String keywordsPath;
    
    /**
     * A regular expression pattern to match against words in the text.
     */
    private String keywordsPattern;
    
    /**
     * Set to true to lower case all words first. Defaults to false.
     */
    private boolean ignoreCase;
    
    /** 
     * <默认构造函数>
     *
     * @param type
     */
    public KeywordMarkerTokenFilter()
    {
        super("stemmer_override");
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
        
        if (keywordsPath != null)
        {
            jsonBuilder.field("keywords_path", keywordsPath);
        }
        
        if(!keywordList.isEmpty())
        {
            jsonBuilder.array("keywords", keywordList.toArray(new String[keywordList.size()]));
        }
        
        if (keywordsPattern != null)
        {
            jsonBuilder.field("keywords_pattern", keywordsPattern);
        }
        
        if (ignoreCase)
        {
            jsonBuilder.field("ignore_case", ignoreCase);
        }
        jsonBuilder.endObject();
    }
    
    /**
     * 添加一个规则
     * @param key
     * @param val void
     */
    public void addkeyword(String val)
    {
        Objects.nonNull(val);
        keywordList.add(val);
    }

    /**
     * 返回 keywordsPath
     *
     * @return keywordsPath
     */
    public String getKeywordsPath()
    {
        return keywordsPath;
    }

    /**
     * 对keywordsPath进行赋值
     *
     * @param keywordsPath
     */
    public void setKeywordsPath(String keywordsPath)
    {
        this.keywordsPath = keywordsPath;
    }

    /**
     * 返回 keywordsPattern
     *
     * @return keywordsPattern
     */
    public String getKeywordsPattern()
    {
        return keywordsPattern;
    }

    /**
     * 对keywordsPattern进行赋值
     *
     * @param keywordsPattern
     */
    public void setKeywordsPattern(String keywordsPattern)
    {
        this.keywordsPattern = keywordsPattern;
    }

    /**
     * 返回 ignoreCase
     *
     * @return ignoreCase
     */
    public boolean isIgnoreCase()
    {
        return ignoreCase;
    }

    /**
     * 对ignoreCase进行赋值
     *
     * @param ignoreCase
     */
    public void setIgnoreCase(boolean ignoreCase)
    {
        this.ignoreCase = ignoreCase;
    }
}
