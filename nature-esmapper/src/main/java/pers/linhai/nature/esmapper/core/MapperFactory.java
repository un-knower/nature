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
package pers.linhai.nature.esmapper.core;

import java.util.HashMap;
import java.util.Map;

import pers.linhai.nature.esmapper.core.impls.IndexMapperImpl;
import pers.linhai.nature.esmapper.exception.IndexAccessorNotFoundException;
import pers.linhai.nature.esmapper.exception.TypeAccessorNotFoundException;
import pers.linhai.nature.esmapper.interfaces.IndexMapper;
import pers.linhai.nature.esmapper.interfaces.TypeMapper;
import pers.linhai.nature.esmapper.model.client.Configuration;
import pers.linhai.nature.esmapper.model.index.Index;
import pers.linhai.nature.esmapper.model.type.Type;

/**
 * 数据访问器工厂类
 * 
 * @author  shinelon
 * @version  V100R001C00
 */
public abstract class MapperFactory
{
    
    /**
     * 单例对象.
     */
    private static final MapperFactory FACTORY = new MapperFactory()
    {
    };
    
    private final Map<Class<? extends Index>, IndexMapper<? extends Index>> indexMapperMap = new HashMap<Class<? extends Index>, IndexMapper<? extends Index>>();
    
    private Map<Class<? extends Type>, TypeMapper<? extends Type>> typeMapperMap = new HashMap<Class<? extends Type>, TypeMapper<? extends Type>>();
    
    private MapperFactory()
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
            
            IndexMapper<T> indexAccessor = new IndexMapperImpl<T>(index);
            add(indexClass, indexAccessor);
        }
        catch (Throwable e)
        {
            e.printStackTrace();
        }
    }
    
    private <T extends Index> void add0(Class<T> index, IndexMapper<T> indexAccessor)
    {
        indexMapperMap.put(index, indexAccessor);
    }
    
    private <T extends Type> void add0(Class<T> type, TypeMapper<T> typeAccessor)
    {
        typeMapperMap.put(type, typeAccessor);
    }
    
    /**
     * 根据索引Class对象获取IndexAccessor
     * @param index
     * @return IndexAccessor<T>
     */
    @SuppressWarnings("unchecked")
    private <T extends Index> IndexMapper<T> indexAccessor0(Class<T> index)
    {
        IndexMapper<T> ia = (IndexMapper<T>)indexMapperMap.get(index);
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
    private <T extends Type> TypeMapper<T> typeAccessor0(Class<T> type)
    {
        TypeMapper<T> ta = (TypeMapper<T>)typeMapperMap.get(type);
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
        // 加载es配置文件
        Configuration.getInstance().load();
        FACTORY.load0(index);
    }
    
    public static <T extends Index> void add(Class<T> index, IndexMapper<T> indexAccessor)
    {
        FACTORY.add0(index, indexAccessor);
    }
    
    public static <T extends Type> void add(Class<T> type, TypeMapper<T> typeAccessor)
    {
        FACTORY.add0(type, typeAccessor);
    }
    
    /**
     * 根据索引Class对象获取IndexAccessor
     * @param index
     * @return IndexAccessor<T>
     */
    public static <T extends Index> IndexMapper<T> indexMapper(Class<T> index)
    {
        return FACTORY.indexAccessor0(index);
    }
    
    /**
     * 获取实体访问器
     * @param type
     * @return TypeAccessor<? extends MappingType>
     */
    public static <T extends Type> TypeMapper<T> typeMapper(Class<T> type)
    {
        return FACTORY.typeAccessor0(type);
    }
}
