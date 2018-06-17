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
    A filter that provides access to (almost) all of the available stemming token filters through a single unified interface. For example:
    
    PUT /my_index
    {
        "settings": {
            "analysis" : {
                "analyzer" : {
                    "my_analyzer" : {
                        "tokenizer" : "standard",
                        "filter" : ["standard", "lowercase", "my_stemmer"]
                    }
                },
                "filter" : {
                    "my_stemmer" : {
                        "type" : "stemmer",
                        "name" : "light_german"
                    }
                }
            }
        }
    }
 * </pre>
 * @author  shinelon
 * @version  V100R001C00
 */
public class StemmerTokenFilter extends TokenFilter
{
    
    /**
     * The language/name parameter controls the stemmer with the following available values (the preferred filters are marked in bold):
     */
    private String language;
    
    /** 
     * <默认构造函数>
     *
     * @param type
     */
    public StemmerTokenFilter()
    {
        super("stemmer");
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
