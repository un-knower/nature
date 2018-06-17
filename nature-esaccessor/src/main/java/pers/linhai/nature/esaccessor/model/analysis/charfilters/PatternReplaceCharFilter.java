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
package pers.linhai.nature.esaccessor.model.analysis.charfilters;

import java.io.IOException;

import org.elasticsearch.common.xcontent.XContentBuilder;

import pers.linhai.nature.esaccessor.model.analysis.templets.CharFilter;

/**
 * 
 * <pre>
        The mapping character filter accepts a map of keys and values. 
     Whenever it encounters a string of characters that is the same as a key, it replaces them with the value associated with that key.
        
        Matching is greedy; the longest pattern matching at a given point wins. 
      Replacements are allowed to be the empty string.
 * </pre>
 * @author  shinelon
 * @version  V100R001C00
 */
public class PatternReplaceCharFilter extends CharFilter
{
    
    /**
     * A Java regular expression. Required. 
     */
    private String pattern;
    
    /**
     * Java regular expression flags. Flags should be pipe-separated, eg "CASE_INSENSITIVE|COMMENTS"
     */
    private String flags;
    
    /**
     * <pre>
     * The replacement string, which can reference capture groups using the $1..$9 syntax, as explained here.
     * </pre>
     */
    private String replacement;
    
    /** 
     * <默认构造函数>
     *
     * @param type
     */
    public PatternReplaceCharFilter()
    {
        super("pattern_replace");
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
        
        if (pattern != null)
        {
            jsonBuilder.field("pattern", pattern);
        }
        
        if (flags != null)
        {
            jsonBuilder.field("flags", flags);
        }
        
        if (replacement != null)
        {
            jsonBuilder.field("replacement", replacement);
        }
        jsonBuilder.endObject();
    }

    /**
     * 返回 pattern
     *
     * @return pattern
     */
    public String getPattern()
    {
        return pattern;
    }

    /**
     * 对pattern进行赋值
     *
     * @param pattern
     */
    public void setPattern(String pattern)
    {
        this.pattern = pattern;
    }

    /**
     * 返回 flags
     *
     * @return flags
     */
    public String getFlags()
    {
        return flags;
    }

    /**
     * 对flags进行赋值
     *
     * @param flags
     */
    public void setFlags(String flags)
    {
        this.flags = flags;
    }

    /**
     * 返回 replacement
     *
     * @return replacement
     */
    public String getReplacement()
    {
        return replacement;
    }

    /**
     * 对replacement进行赋值
     *
     * @param replacement
     */
    public void setReplacement(String replacement)
    {
        this.replacement = replacement;
    }
    
}
