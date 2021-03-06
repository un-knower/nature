/*
 * 项 目 名:  Storage Tool Service V100R001C00
 * 文 件 名:  pers.linhai.esframework.core.XContentBuilderTransfer.java
 * 版       权:  XXX Technologies Co., Ltd. Copyright 2017,  All rights reserved.
 * 描       述:  XXX PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * 修 改 人:  shinelon
 * 修改时间:  2017年6月7日
 * 修改内容:  创建
 */
package pers.linhai.nature.esmapper.core.impls;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.Map.Entry;

import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;

import pers.linhai.nature.esmapper.exception.TypeAccessorException;
import pers.linhai.nature.esmapper.interfaces.DataConverter;
import pers.linhai.nature.esmapper.model.core.DataTypeCollection;
import pers.linhai.nature.esmapper.model.datatypes.DataType;
import pers.linhai.nature.esmapper.model.type.Type;

/**
 * 数据转换器
 * @author  shinelon
 * @version  V100R001C00
 */
public class DataConverterImpl<T extends Type> implements DataConverter<T>
{
    
    /**
     * 索引库对应所有java对象的成员List集合
     */
    private DataTypeCollection dataTypeCollection;
    
    /**
     * 数据entity的构造器，以便用于查询的时候，直接返回该类型的数据
     */
    private Constructor<T> entityConstructor;

    /**
     * 
     * <默认构造函数>
     *
     * @param dataTypeCollection
     * @param constructor
     */
    DataConverterImpl(DataTypeCollection dataTypeCollection, Constructor<T> constructor)
    {
        this.dataTypeCollection = dataTypeCollection;
        this.entityConstructor = constructor;
    }

    /**
     * 将实体转换成XContentBuilder对象，es能直接使用该对象
     * TODO id version的转换问题
     * @param t
     * @return
     * @throws IOException XContentBuilder
     */
    public XContentBuilder convert(T t)
    {
        try
        {
            XContentBuilder jsonBuilder = XContentFactory.jsonBuilder();
            jsonBuilder.startObject();
            for (DataType dt : dataTypeCollection.getDataTypeList())
            {
                dt.setJsonFieldValue(jsonBuilder, t);
            }
            jsonBuilder.endObject();
            return jsonBuilder;
        }
        catch (Throwable e)
        {
            throw new TypeAccessorException("TypeDao.toXContentBuilder:", e);
        }
    }
    
    /**
     * 将响应的信息转换成实体bean对象
     *
     * @param valueMap
     * @return T
     */
    public T convert(Map<String, Object> valueMap)
    {
        T t = null;
        try
        {
            t = entityConstructor.newInstance();
            mapping(t, valueMap);
        }
        catch (Throwable e)
        {
            e.printStackTrace();
        }
        return t;
    }
    
    /** 
     * <p>Overriding Method: lilinhai 2018年1月6日 下午1:49:26</p>
     * <p>Title: convert</p>
     * @param valueMap
     * @param highlightFieldMap
     * @return 
     * @see pers.linhai.nature.esmapper.interfaces.DataConverter#convert(java.util.Map, java.util.Map)
     */ 
    public T convert(Map<String, Object> valueMap, Map<String, HighlightField> highlightFieldMap)
    {
        T t = null;
        try
        {
            t = entityConstructor.newInstance();
            for (Entry<String, Object> e : valueMap.entrySet())
            {
                HighlightField hf = highlightFieldMap.get(e.getKey());
                if (hf != null)
                {
                    Text[] ts =  hf.getFragments();
                    StringBuilder sb = new StringBuilder();
                    for (Text text : ts)
                    {
                        sb.append(text.string());
                    }
                    dataTypeCollection.get(e.getKey()).setEntityFieldValue(t, sb.toString());
                }
                else
                {
                    dataTypeCollection.get(e.getKey()).setEntityFieldValue(t, e.getValue());
                }
            }
        }
        catch (Throwable e1)
        {
            e1.printStackTrace();
        }
        return t;
    }

    /**
     * 将响应的信息映射到给定的实体对象成员中
     *
     * @param t
     * @param valueMap void
     */
    public void mapping(T t, Map<String, Object> valueMap)
    {
        for (Entry<String, Object> e : valueMap.entrySet())
        {
            dataTypeCollection.get(e.getKey()).setEntityFieldValue(t, e.getValue());
        }
    }
}
