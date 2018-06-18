/*
 * 项 目 名:  Storage Tool Service V100R001C00
 * 文 件 名:  pers.linhai.esframework.interfaces.BulkOperations.java
 * 版       权:  XXX Technologies Co., Ltd. Copyright 2017,  All rights reserved.
 * 描       述:  XXX PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * 修 改 人:  shinelon
 * 修改时间:  2017年6月10日
 * 修改内容:  创建
 */
package pers.linhai.nature.esmapper.interfaces;

import pers.linhai.nature.esmapper.model.type.Type;

/**
 * 
 * 
 * @author  shinelon
 * @version  V100R001C00
 */
public interface BulkOperation<T extends Type>
{
    
    /**
     * 添加一个实体
     * @param t void
     */
    void add(T t);
    
    /**
     * 删除单个记录
     * @param id void
     */
    void delete(String id);
    
    /**
     * 刷缓冲区操作
     * void
     */
    void flush();
    
    /**
     * 结束批量操作
     * void
     */
    void close();
    
    boolean isClosed();
    
    /**
     * 返回 index
     * @return index
     */
    String getIndex();
}
