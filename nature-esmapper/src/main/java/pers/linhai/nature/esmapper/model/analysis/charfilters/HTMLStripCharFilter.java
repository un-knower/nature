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
package pers.linhai.nature.esmapper.model.analysis.charfilters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.elasticsearch.common.xcontent.XContentBuilder;

import pers.linhai.nature.esmapper.model.analysis.templets.CharFilter;

/**
 * 
 * 
 * @author  shinelon
 * @version  V100R001C00
 */
public class HTMLStripCharFilter extends CharFilter
{
    /**
     * escaped_tags An array of HTML tags which should not be stripped from the original text. 
     */
    private List<String> escapedTagList = new ArrayList<String>();
    
    /** 
     * <默认构造函数>
     * @param type
     */
    public HTMLStripCharFilter()
    {
        super("html_strip");
    }

    /**
     * @param jsonBuilder
     * @throws IOException
     */
    public void build(XContentBuilder jsonBuilder) throws IOException
    {
        jsonBuilder.startObject(name);
        jsonBuilder.field("type", type);
        
        if(!escapedTagList.isEmpty())
        {
            jsonBuilder.array("escaped_tags", escapedTagList.toArray(new String[escapedTagList.size()]));
        }
        jsonBuilder.endObject();
    }
    
    /**
     * 添加一个需要被忽略的html标签名
     *
     * @param escapedTag void
     */
    public void addEscapedTag(String escapedTag)
    {
        Objects.nonNull(escapedTag);
        escapedTagList.add(escapedTag);
    }
    
}
