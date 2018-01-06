/*
 * 项 目 名:  Storage Tool Service V100R001C00
 * 文 件 名:  pers.linhai.esframework.interfaces.DataConverter.java
 * 版       权:  XXX Technologies Co., Ltd. Copyright 2017,  All rights reserved.
 * 描       述:  XXX PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * 修 改 人:  shinelon
 * 修改时间:  2017年6月10日
 * 修改内容:  创建
 */
package pers.linhai.nature.indexaccess.interfaces;

import java.io.IOException;
import java.util.Map;

import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;

import pers.linhai.nature.indexaccess.model.type.Type;

/**
 * 
 * 
 * @author  shinelon
 * @version  V100R001C00
 */
public interface DataConverter<T extends Type>
{
    
    /**
     * 将实体转换成XContentBuilder对象，es能直接使用该对象
     * @param t
     * @return
     * @throws IOException XContentBuilder
     */
    XContentBuilder convert(T t);
    
    /**
     * 将响应的信息转换成实体bean对象
     *
     * @param valueMap
     * @return T
     */
    T convert(Map<String, Object> valueMap);
    
    /**
     * 将响应的信息转换成实体bean对象
     * <p>Title         : convert lilinhai 2018年1月6日 下午1:49:17</p>
     * @param valueMap
     * @param highlightFieldMap
     * @return 
     * T
     */
    T convert(Map<String, Object> valueMap, Map<String, HighlightField> highlightFieldMap);
    
    /**
     * 将响应的信息映射到给定的实体对象成员中
     *
     * @param t
     * @param valueMap void
     */
    void mapping(T t, Map<String, Object> valueMap);
}
