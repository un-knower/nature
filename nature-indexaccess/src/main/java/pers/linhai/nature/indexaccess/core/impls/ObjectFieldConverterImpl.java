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
package pers.linhai.nature.indexaccess.core.impls;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.Map.Entry;

import org.elasticsearch.common.xcontent.XContentBuilder;

import pers.linhai.nature.indexaccess.exception.TypeAccessorException;
import pers.linhai.nature.indexaccess.interfaces.ObjectFieldConverter;
import pers.linhai.nature.indexaccess.model.core.DataTypeCollection;
import pers.linhai.nature.indexaccess.model.datatypes.DataType;
import pers.linhai.nature.indexaccess.model.datatypes.quote.ObjectType.ObjectField;

/**
 * 数据转换器
 * @author  shinelon
 * @version  V100R001C00
 */
public class ObjectFieldConverterImpl<T extends ObjectField> implements ObjectFieldConverter<T>
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
     * @param entityConstructor
     */
    public ObjectFieldConverterImpl(DataTypeCollection dataTypeCollection, Constructor<T> entityConstructor)
    {
        this.dataTypeCollection = dataTypeCollection;
        this.entityConstructor = entityConstructor;
    }

    /**
     * 将实体转换成XContentBuilder对象，es能直接使用该对象
     * TODO id version的转换问题
     * @param t
     * @return
     * @throws IOException XContentBuilder
     */
    public void convert(XContentBuilder xContentBuilder, Object t)
    {
        try
        {
            for (DataType dt : dataTypeCollection.getDataTypeList())
            {
                dt.setJsonFieldValue(xContentBuilder, t);
            }
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
