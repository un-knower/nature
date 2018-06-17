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

import org.elasticsearch.common.xcontent.XContentBuilder;

import pers.linhai.nature.esaccessor.model.analysis.templets.TokenFilter;

/**
 * 
 * <pre>
A token filter of type lowercase that normalizes token text to lower case.

Lowercase token filter supports Greek, Irish, and Turkish lowercase token filters through the language parameter. Below is a usage example in a custom analyzer

PUT /lowercase_example
{
  "settings": {
    "analysis": {
      "analyzer": {
        "standard_lowercase_example": {
          "type": "custom",
          "tokenizer": "standard",
          "filter": ["lowercase"]
        },
        "greek_lowercase_example": {
          "type": "custom",
          "tokenizer": "standard",
          "filter": ["greek_lowercase"]
        }
      },
      "filter": {
        "greek_lowercase": {
          "type": "lowercase",
          "language": "greek"
        }
      }
    }
  }
}
 * </pre>
 * @author  shinelon
 * @version  V100R001C00
 */
public class LowercaseTokenFilter extends TokenFilter
{
    
    /**
     * Lowercase token filter supports Greek, Irish, and Turkish lowercase token filters through the language parameter. Below is a usage example in a custom analyzer
     */
    protected String language;
    
    /** 
     * <默认构造函数>
     *
     * @param type
     */
    public LowercaseTokenFilter()
    {
        this("lowercase");
    }
    
    /** 
     * <默认构造函数>
     *
     * @param type
     */
    public LowercaseTokenFilter(String type)
    {
        super(type);
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
        
        if(language != null)
        {
            jsonBuilder.field("language", language);
        }
        
        jsonBuilder.endObject();
    }

    /**
     * 返回 language
     *
     * @return language
     */
    public String getLanguage()
    {
        return language;
    }

    /**
     * 对language进行赋值
     *
     * @param language
     */
    public void setLanguage(String language)
    {
        this.language = language;
    }
}
