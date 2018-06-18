/*
 * 项 目 名:  Storage Tool Service V100R001C00
 * 文 件 名:  pers.linhai.esframework.model.analysis.tokenizers.StandardTokenizer.java
 * 版       权:  XXX Technologies Co., Ltd. Copyright 2017,  All rights reserved.
 * 描       述:  XXX PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * 修 改 人:  shinelon
 * 修改时间:  2017年6月11日
 * 修改内容:  创建
 */
package pers.linhai.nature.esmapper.model.analysis.tokenizers;

import java.io.IOException;

import org.elasticsearch.common.xcontent.XContentBuilder;

import pers.linhai.nature.esmapper.model.analysis.templets.Tokenizer;

/**
 * 
 * 正则标记器
 * @author  shinelon
 * @version  V100R001C00
 */
public class PatternTokenizer extends Tokenizer
{

    /**
     * A Java regular expression, defaults to \W+. 
     */
    private String pattern;
    
    /**
     * Java regular expression flags. lags should be pipe-separated, eg "CASE_INSENSITIVE|COMMENTS". 
     */
    private String flags;
    
    /**
     * Which capture group to extract as tokens. Defaults to -1 (split). 
     */
    private int group = -1;
    
    /** 
     * <默认构造函数>
     */
    public PatternTokenizer()
    {
        super("pattern");
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
        
        if(pattern != null)
        {
            jsonBuilder.field("pattern", pattern);
        }
        
        if(flags != null)
        {
            jsonBuilder.field("flags", flags);
        }
        
        if(group != -1)
        {
            jsonBuilder.field("group", group);
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
     * 返回 group
     *
     * @return group
     */
    public int getGroup()
    {
        return group;
    }

    /**
     * 对group进行赋值
     *
     * @param group
     */
    public void setGroup(int group)
    {
        this.group = group;
    }
}
