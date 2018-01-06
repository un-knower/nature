/*
 * 项 目 名:  Storage Tool Service V100R001C00
 * 文 件 名:  pers.linhai.nature.indexaccess.model.core.EntityCollection.java
 * 版       权:  XXX Technologies Co., Ltd. Copyright 2017,  All rights reserved.
 * 描       述:  XXX PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * 修 改 人:  shinelon
 * 修改时间:  2017年7月17日
 * 修改内容:  创建
 */
package pers.linhai.nature.indexaccess.interfaces;

import pers.linhai.nature.indexaccess.model.type.Type;

/**
 * 
 * 搜索结果集
 * @author  shinelon
 * @version  V100R001C00
 */
public interface HitCollection<T extends Type> extends Iterable<T>
{
    
    /**
     * 迭代
     * @param ec void
     */
    void each(Consumer<T> ec);
    
    /**
     * 返回命中的总记录数
     *
     * @return long
     */
    long total();
    
    /**
     * 返回指定索引下标的实体记录
     * @param i
     * @return T
     */
    T get(int i);
    
    
    /**
     * 当前返回数据的条数
     * @return int
     */
    int length();
    
    /**
     * 消费者
     * @author  shinelon
     * @version  V100R001C00
     */
    public static interface Consumer<T extends Type>
    {
        
        void consume(T t);
    }
}
