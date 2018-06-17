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
package pers.linhai.nature.esaccessor.model.analysis.analyzers;

import java.io.IOException;

import org.elasticsearch.common.xcontent.XContentBuilder;

import pers.linhai.nature.esaccessor.model.analysis.templets.Analyzer;

/**
 * <pre>
 *      The pattern analyzer uses a regular expression to split the text into terms. 
 *      The regular expression should match the token separators not the tokens themselves. 
 *      The regular expression defaults to \W+ (or all non-word characters).
 * </pre>
 * @author  shinelon
 * @version  V100R001C00
 */
public class PatternAnalyzer extends Analyzer
{

    /**
     * <pre>
     *   A Java regular expression, defaults to \W+. 
     * </pre>
     */
    private String pattern;
    
    /**
     * <pre>
     *   Java regular expression flags. Flags should be pipe-separated, eg "CASE_INSENSITIVE|COMMENTS". 
     * </pre>
     */
    private String flags;
    
    /**
     * Should terms be lowercased or not. Defaults to true. 
     */
    private boolean lowercase = true;
    
    /** 
     * <默认构造函数>
     */
    public PatternAnalyzer()
    {
        super("pattern");
    }

    /**
     * @param jsonBuilder
     * @throws IOException 
     */
    public void build(XContentBuilder jsonBuilder) throws IOException
    {
        packName(jsonBuilder);
        jsonBuilder.field("type", type);
        
        //封装停用词
        packStopwords(jsonBuilder);
        
        if(pattern != null)
        {
            jsonBuilder.field("pattern", pattern);
        }
        
        if(flags != null)
        {
            jsonBuilder.field("flags", flags);
        }
        
        if(!lowercase)
        {
            jsonBuilder.field("lowercase", lowercase);
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
     * 返回 lowercase
     *
     * @return lowercase
     */
    public boolean isLowercase()
    {
        return lowercase;
    }

    /**
     * 对lowercase进行赋值
     *
     * @param lowercase
     */
    public void setLowercase(boolean lowercase)
    {
        this.lowercase = lowercase;
    }
}
