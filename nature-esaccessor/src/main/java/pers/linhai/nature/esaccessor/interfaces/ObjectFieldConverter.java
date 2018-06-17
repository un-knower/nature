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
package pers.linhai.nature.esaccessor.interfaces;

import java.io.IOException;
import java.util.Map;

import org.elasticsearch.common.xcontent.XContentBuilder;

import pers.linhai.nature.esaccessor.model.datatypes.quote.ObjectType.ObjectField;

/**
 * 
 * 
 * @author  shinelon
 * @version  V100R001C00
 */
public interface ObjectFieldConverter<T extends ObjectField>
{
    
    /**
     * 将实体转换成XContentBuilder对象，es能直接使用该对象
     * @param t
     * @return
     * @throws IOException XContentBuilder
     */
    void convert(XContentBuilder xContentBuilder, Object t);
    
    /**
     * 将响应的信息转换成实体bean对象
     *
     * @param valueMap
     * @return T
     */
    T convert(Map<String, Object> valueMap);
    
    /**
     * 将响应的信息映射到给定的实体对象成员中
     *
     * @param t
     * @param valueMap void
     */
    void mapping(T t, Map<String, Object> valueMap);
}
