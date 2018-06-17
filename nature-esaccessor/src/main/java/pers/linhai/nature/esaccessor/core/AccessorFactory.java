/*
 * 项 目 名:  Storage Tool Service V100R001C00
 * 文 件 名:  pers.linhai.esdao.core.IndexFactory.java
 * 版       权:  XXX Technologies Co., Ltd. Copyright 2017,  All rights reserved.
 * 描       述:  XXX PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * 修 改 人:  shinelon
 * 修改时间:  2017年4月19日
 * 修改内容:  创建
 */
package pers.linhai.nature.esaccessor.core;

import java.util.HashMap;
import java.util.Map;

import pers.linhai.nature.esaccessor.core.impls.IndexAccessorImpl;
import pers.linhai.nature.esaccessor.exception.IndexAccessorNotFoundException;
import pers.linhai.nature.esaccessor.exception.TypeAccessorNotFoundException;
import pers.linhai.nature.esaccessor.interfaces.IndexAccessor;
import pers.linhai.nature.esaccessor.interfaces.TypeAccessor;
import pers.linhai.nature.esaccessor.model.index.Index;
import pers.linhai.nature.esaccessor.model.type.Type;

/**
 * 数据访问器工厂类
 * 
 * @author  shinelon
 * @version  V100R001C00
 */
public abstract class AccessorFactory
{
    
    /**
     * 单例对象.
     */
    private static final AccessorFactory ACCESSOR_FACTORY = new AccessorFactory()
    {
    };
    
    private final Map<Class<? extends Index>, IndexAccessor<? extends Index>> indexAccessorMap = new HashMap<Class<? extends Index>, IndexAccessor<? extends Index>>();
    
    private Map<Class<? extends Type>, TypeAccessor<? extends Type>> typeAccessorMap = new HashMap<Class<? extends Type>, TypeAccessor<? extends Type>>();
    
    private AccessorFactory()
    {
        
    }
    
    /**
     * 加载一个索引
     * 
     *
     * @param indexClass void
     */
    private <T extends Index> void load0(Class<T> indexClass)
    {
        try
        {
            //实例化一个索引对象
            T index = indexClass.getDeclaredConstructor().newInstance();
            
            IndexAccessor<T> indexAccessor = new IndexAccessorImpl<T>(index);
            add(indexClass, indexAccessor);
        }
        catch (Throwable e)
        {
            e.printStackTrace();
        }
    }
    
    private <T extends Index> void add0(Class<T> index, IndexAccessor<T> indexAccessor)
    {
        indexAccessorMap.put(index, indexAccessor);
    }
    
    private <T extends Type> void add0(Class<T> type, TypeAccessor<T> typeAccessor)
    {
        typeAccessorMap.put(type, typeAccessor);
    }
    
    /**
     * 根据索引Class对象获取IndexAccessor
     * @param index
     * @return IndexAccessor<T>
     */
    @SuppressWarnings("unchecked")
    private <T extends Index> IndexAccessor<T> indexAccessor0(Class<T> index)
    {
        IndexAccessor<T> ia = (IndexAccessor<T>)indexAccessorMap.get(index);
        if(ia == null)
        {
            throw new IndexAccessorNotFoundException(index.getSimpleName());
        }
        return ia;
    }
    
    /**
     * 获取实体访问器
     * @param type
     * @return TypeAccessor<? extends MappingType>
     */
    @SuppressWarnings("unchecked")
    private <T extends Type> TypeAccessor<T> typeAccessor0(Class<T> type)
    {
        TypeAccessor<T> ta = (TypeAccessor<T>)typeAccessorMap.get(type);
        if(ta == null)
        {
            throw new TypeAccessorNotFoundException(type.getSimpleName());
        }
        return ta;
    }
    
    
    /**
     * 加载一个索引
     * 
     *
     * @param index void
     */
    public static <T extends Index> void load(Class<T> index)
    {
        ACCESSOR_FACTORY.load0(index);
    }
    
    public static <T extends Index> void add(Class<T> index, IndexAccessor<T> indexAccessor)
    {
        ACCESSOR_FACTORY.add0(index, indexAccessor);
    }
    
    public static <T extends Type> void add(Class<T> type, TypeAccessor<T> typeAccessor)
    {
        ACCESSOR_FACTORY.add0(type, typeAccessor);
    }
    
    /**
     * 根据索引Class对象获取IndexAccessor
     * @param index
     * @return IndexAccessor<T>
     */
    public static <T extends Index> IndexAccessor<T> indexAccessor(Class<T> index)
    {
        return ACCESSOR_FACTORY.indexAccessor0(index);
    }
    
    /**
     * 获取实体访问器
     * @param type
     * @return TypeAccessor<? extends MappingType>
     */
    public static <T extends Type> TypeAccessor<T> typeAccessor(Class<T> type)
    {
        return ACCESSOR_FACTORY.typeAccessor0(type);
    }
}
