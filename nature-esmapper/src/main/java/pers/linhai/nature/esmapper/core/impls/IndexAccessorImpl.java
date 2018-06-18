/*
 * 项 目 名:  Storage Tool Service V100R001C00
 * 文 件 名:  pers.linhai.esframework.core.impls.IndexAccessorImpl.java
 * 版       权:  XXX Technologies Co., Ltd. Copyright 2017,  All rights reserved.
 * 描       述:  XXX PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * 修 改 人:  shinelon
 * 修改时间:  2017年6月11日
 * 修改内容:  创建
 */
package pers.linhai.nature.esmapper.core.impls;

import pers.linhai.nature.esmapper.core.MapperFactory;
import pers.linhai.nature.esmapper.core.processor.IndicesAdminClientProcessor;
import pers.linhai.nature.esmapper.interfaces.IndexMapper;
import pers.linhai.nature.esmapper.interfaces.TypeAccessorInitialization;
import pers.linhai.nature.esmapper.model.index.Index;

/**
 * 索引访问器实现
 * @author  shinelon
 * @version  V100R001C00
 */
@SuppressWarnings("unchecked")
public class IndexAccessorImpl<T extends Index> implements IndexMapper<T>
{
    
    private static final TypeAccessorInitialization DEFAULT_TYPE_ACCESSOR_INITIALIZATION = new DefaultTypeAccessInitializationImpl();
    
    private T index;
    
    private IndicesAdminClientProcessor indicesAdminClientProcessor;
    
    /** 
     * <默认构造函数>
     *
     * @param index
     */
    public IndexAccessorImpl(T index)
    {
        this(index, DEFAULT_TYPE_ACCESSOR_INITIALIZATION);
    }
    
    /** 
     * <默认构造函数>
     *
     * @param index T
     * @param typeAccessorInitialization TypeAccessorInitialization
     */
    public IndexAccessorImpl(T index, TypeAccessorInitialization typeAccessorInitialization)
    {
        this(index, IndicesAdminClientProcessor.newInstance(index, typeAccessorInitialization));
    }
    
    /**
     * <p>Title        : IndexAccessorImpl lilinhai 2017年12月16日 下午2:14:34</p>
     * @param index
     * @param indicesAdminClientProcessor
     */
    public IndexAccessorImpl(T index, IndicesAdminClientProcessor indicesAdminClientProcessor)
    {
        this.index = index;
        this.indicesAdminClientProcessor = indicesAdminClientProcessor;
        MapperFactory.add((Class<T>)index.getClass(), this);
    }
    
    //删除索引
    public void delete()
    {
        indicesAdminClientProcessor.delete();
    }
    
    /**
     * 判断索引库是否存在
     * @return boolean
     */
    public boolean exists()
    {
        return indicesAdminClientProcessor.exists();
    }
    
    /**
     * 刷出缓冲区
     * void
     */
    public void flush()
    {
        indicesAdminClientProcessor.flush();
    }
    
    /**
     * 刷新索引
     * void
     */
    public void refresh()
    {
        indicesAdminClientProcessor.refresh();
    }

    /**
     * 返回 index
     *
     * @return index
     */
    public T index()
    {
        return index;
    }
}
