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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
public class MappingCharFilter extends CharFilter
{
    
    /**
     *  mappings : A array of mappings, with each element having the form key => value. 
     */
    private List<String> mappingsList = new ArrayList<String>();
    
    /**
     * <pre>
     * A path, either absolute or relative to the config directory, 
     * to a UTF-8 encoded text mappings file containing a key => value mapping per line.
     * </pre>
     */
    private String mappingsPath;
    
    /** 
     * <默认构造函数>
     *
     * @param type
     */
    public MappingCharFilter()
    {
        super("mapping");
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
        
        if (mappingsPath != null)
        {
            jsonBuilder.field("mappings_path", mappingsPath);
        }
        
        if(!mappingsList.isEmpty())
        {
            jsonBuilder.array("mappings", mappingsList.toArray(new String[mappingsList.size()]));
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
        mappingsList.add(key + " => " + val);
    }

    /**
     * 返回 mappingsPath
     *
     * @return mappingsPath
     */
    public String getMappingsPath()
    {
        return mappingsPath;
    }

    /**
     * 对mappingsPath进行赋值
     *
     * @param mappingsPath
     */
    public void setMappingsPath(String mappingsPath)
    {
        this.mappingsPath = mappingsPath;
    }
}
