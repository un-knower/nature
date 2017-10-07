/*
 * 项 目 名:  Storage Tool Service V100R001C00
 * 文 件 名:  esdao.esdao.generaltest.Test1Impl.java
 * 版       权:  XXX Technologies Co., Ltd. Copyright 2017,  All rights reserved.
 * 描       述:  XXX PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * 修 改 人:  shinelon
 * 修改时间:  2017年7月1日
 * 修改内容:  创建
 */
package pers.linhai.nature.indexaccess.bak.generaltest;

import pers.linhai.nature.indexaccess.bak.index.ObjType;

/**
 * 
 * 
 * @author  shinelon
 * @version  V100R001C00
 */
public class Test1Impl<T extends ObjType> implements ITest1<T>
{
    private Class<T> c;
    
    /** 
     * <默认构造函数>
     *
     */
    public Test1Impl(Class<T> c)
    {
        this.c = c;
    }

    /**
     * 
     *
     * @param t
     */
    public void save(T t)
    {
        System.out.println("AAAAA:"+t.getClass().getName());
    }

    /**
     * 
     *
     * @return
     */
    @Override
    public T get()
    {
        try
        {
            return c.newInstance();
        }
        catch (InstantiationException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        return null;
    }
    
    
}
