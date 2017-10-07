/*
 * 项 目 名:  Storage Tool Service V100R001C00
 * 文 件 名:  pers.linhai.esframework.model.analysis.charfilters.HTMLStripCharFilter.java
 * 版       权:  XXX Technologies Co., Ltd. Copyright 2017,  All rights reserved.
 * 描       述:  XXX PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * 修 改 人:  shinelon
 * 修改时间:  2017年6月11日
 * 修改内容:  创建
 */
package pers.linhai.nature.indexaccess.model.analysis.normalizers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.elasticsearch.common.xcontent.XContentBuilder;

import pers.linhai.nature.indexaccess.model.analysis.templets.Normalizer;

/**
 * 
 * 
 * @author  shinelon
 * @version  V100R001C00
 */
public class CustomNormalizer extends Normalizer
{
    /**
     * charFilters
     */
    private List<String> charFilterList = new ArrayList<String>();
    
    /**
     * tokenFilters
     */
    private List<String> tokenFilterList = new ArrayList<String>();
    
    /** 
     * <默认构造函数>
     * @param type
     */
    public CustomNormalizer()
    {
        super("custom");
    }

    /**
     * @param jsonBuilder
     * @throws IOException
     */
    public void build(XContentBuilder jsonBuilder) throws IOException
    {
        jsonBuilder.startObject(name);
        jsonBuilder.field("type", type);
        
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
     * 添加一个charFilter
     *
     * @param charFilter void
     */
    public void addCharFilter(String charFilter)
    {
        Objects.nonNull(charFilter);
        charFilterList.add(charFilter);
    }
    
    /**
     * 添加一个tokenFilter
     *
     * @param tokenFilter void
     */
    public void addTokenFilter(String tokenFilter)
    {
        Objects.nonNull(tokenFilter);
        tokenFilterList.add(tokenFilter);
    }
}
