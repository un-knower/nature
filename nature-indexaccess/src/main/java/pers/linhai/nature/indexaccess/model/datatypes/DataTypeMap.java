/*
 * 项 目 名:  Storage Tool Service V100R001C00
 * 文 件 名:  pers.linhai.esdao.model.datatype.DataTypeMap.java
 * 版       权:  XXX Technologies Co., Ltd. Copyright 2017,  All rights reserved.
 * 描       述:  XXX PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * 修 改 人:  shinelon
 * 修改时间:  2017年5月31日
 * 修改内容:  创建
 */
package pers.linhai.nature.indexaccess.model.datatypes;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import pers.linhai.nature.indexaccess.exception.DataTypeParseException;
import pers.linhai.nature.indexaccess.model.datatypes.basic.BooleanType;
import pers.linhai.nature.indexaccess.model.datatypes.basic.numeric.ByteType;
import pers.linhai.nature.indexaccess.model.datatypes.basic.numeric.DoubleType;
import pers.linhai.nature.indexaccess.model.datatypes.basic.numeric.FloatType;
import pers.linhai.nature.indexaccess.model.datatypes.basic.numeric.IntegerType;
import pers.linhai.nature.indexaccess.model.datatypes.basic.numeric.LongType;
import pers.linhai.nature.indexaccess.model.datatypes.basic.numeric.ShortType;
import pers.linhai.nature.indexaccess.model.datatypes.quote.DateType;
import pers.linhai.nature.indexaccess.model.datatypes.quote.DateType.Date;
import pers.linhai.nature.indexaccess.model.datatypes.quote.ObjectType;
import pers.linhai.nature.indexaccess.model.datatypes.quote.ObjectType.ObjectField;
import pers.linhai.nature.indexaccess.model.datatypes.quote.TextType;

/**
 * 
 * 
 * @author  shinelon
 * @version  V100R001C00
 */
public final class DataTypeMap
{
    /**
     * 存放字段类型解析器的映射Map
     */
    private static final Map<Class<?>, Constructor<? extends DataType>> DATA_TYPE_MAP = new HashMap<Class<?>, Constructor<? extends DataType>>();
    
    static
    {
        try
        {
            //--------基本数据类型--start-------------
            
            //注册integer数据类型
            registerDataType(IntegerType.class, Integer.class, Integer.TYPE);
            
            //注册byte数据类型
            registerDataType(ByteType.class, Byte.class, Byte.TYPE);
            
            //注册short数据类型
            registerDataType(ShortType.class, Short.class, Short.TYPE);
            
            //注册long数据类型
            registerDataType(LongType.class, Long.class, Long.TYPE);
            
            //注册float数据类型
            registerDataType(FloatType.class, Float.class, Float.TYPE);
            
            //注册double数据类型
            registerDataType(DoubleType.class, Double.class, Double.TYPE);
            
            //注册boolean数据类型
            registerDataType(BooleanType.class, Boolean.class, Boolean.TYPE);
            
            //--------基本数据类型--end-------------
            
            //字符串类型
            registerDataType(TextType.class, String.class);
            
            //对象类型
            registerDataType(ObjectType.class, ObjectField.class);
            
            //日期对象类型
            registerDataType(DateType.class, Date.class);
        }
        catch (Throwable e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * 注册ES数据类型处理器
     * @param dataTypeClass
     * @param mapKeyClasses 
     * void
     */
    private static <T extends DataType> void registerDataType(Class<T> dataTypeClass, Class<?>... mapKeyClasses)
    {
        try
        {
            Constructor<T> dataTypeConstructor = null;
            if (ObjectType.class.isAssignableFrom(dataTypeClass))
            {
                dataTypeConstructor = dataTypeClass.getDeclaredConstructor(Class.class, List.class);
            }
            else
            {
                dataTypeConstructor = dataTypeClass.getDeclaredConstructor(List.class);
            }
            dataTypeConstructor.setAccessible(true);
            for (Class<?> mapKeyClass : mapKeyClasses)
            {
                DATA_TYPE_MAP.put(mapKeyClass, dataTypeConstructor);
            }
        }
        catch (Throwable e)
        {
            throw new DataTypeParseException("registerDataType", e);
        }
    }
    
    /**
     * 获取ES指定数据类型处理器的构造函数
     * @param mapKeyClass
     * @return Constructor<? extends DataType>
     */
    public static Constructor<? extends DataType> get(Class<?> mapKeyClass)
    {
        return DATA_TYPE_MAP.get(mapKeyClass);
    }
    
    /**
     * 框架支持的ES 数据类型
     * @return Set<Class<?>>
     */
    public static Set<Class<?>> dataTypeSupports()
    {
        return DATA_TYPE_MAP.keySet();
    }
}
