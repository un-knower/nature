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
        Overrides stemming algorithms, by applying a custom mapping, then protecting these terms from being modified by stemmers. Must be placed before any stemming filters.
        
        Rules are separated by =>
        Setting     Description
        
        rules
            
        
        A list of mapping rules to use.
        
        rules_path
            
        
        A path (either relative to config location, or absolute) to a list of mappings.
        
        Here is an example:
        
        PUT /my_index
        {
            "settings": {
                "analysis" : {
                    "analyzer" : {
                        "my_analyzer" : {
                            "tokenizer" : "standard",
                            "filter" : ["lowercase", "custom_stems", "porter_stem"]
                        }
                    },
                    "filter" : {
                        "custom_stems" : {
                            "type" : "stemmer_override",
                            "rules_path" : "analysis/stemmer_override.txt"
                        }
                    }
                }
            }
        }
        
        Copy as cURLView in Console 
        
        Where the file looks like:
        
        running => run
        
        stemmer => stemmer
        
        You can also define the overrides rules inline:
        
        PUT /my_index
        {
            "settings": {
                "analysis" : {
                    "analyzer" : {
                        "my_analyzer" : {
                            "tokenizer" : "standard",
                            "filter" : ["lowercase", "custom_stems", "porter_stem"]
                        }
                    },
                    "filter" : {
                        "custom_stems" : {
                            "type" : "stemmer_override",
                            "rules" : [
                                "running => run",
                                "stemmer => stemmer"
                            ]
                        }
                    }
                }
            }
        }
 * </pre>
 * @author  shinelon
 * @version  V100R001C00
 */
public class StemmerOverrideTokenFilter extends TokenFilter
{
    
    /**
     * A list of mapping rules to use.
     */
    private List<String> ruleList = new ArrayList<String>();
    
    /**
     * A path (either relative to config location, or absolute) to a list of mappings.
     */
    private String rulesPath;
    
    /** 
     * <默认构造函数>
     *
     * @param type
     */
    public StemmerOverrideTokenFilter()
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
        
        if (rulesPath != null)
        {
            jsonBuilder.field("rules_path", rulesPath);
        }
        
        if(!ruleList.isEmpty())
        {
            jsonBuilder.array("rules", ruleList.toArray(new String[ruleList.size()]));
        }
        jsonBuilder.endObject();
    }
    
    /**
     * 添加一个规则
     * @param key
     * @param val void
     */
    public void addRule(String key, String val)
    {
        Objects.nonNull(key);
        Objects.nonNull(val);
        ruleList.add(key + " => " + val);
    }

    /**
     * 返回 rulesPath
     *
     * @return rulesPath
     */
    public String getRulesPath()
    {
        return rulesPath;
    }

    /**
     * 对rulesPath进行赋值
     *
     * @param rulesPath
     */
    public void setRulesPath(String rulesPath)
    {
        this.rulesPath = rulesPath;
    }
}
