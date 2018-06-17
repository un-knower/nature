/*
 * 项 目 名:  Storage Tool Service V100R001C00
 * 文 件 名:  pers.linhai.esframework.interfaces.IndexAccessor.java
 * 版       权:  XXX Technologies Co., Ltd. Copyright 2017,  All rights reserved.
 * 描       述:  XXX PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * 修 改 人:  shinelon
 * 修改时间:  2017年6月10日
 * 修改内容:  创建
 */
package pers.linhai.nature.esaccessor.interfaces;

import pers.linhai.nature.esaccessor.model.index.Index;

/**
 * 索引访问对象
 * @author  shinelon
 * @version  V100R001C00
 */
public interface IndexAccessor<T extends Index>
{
    
    /**
     * 删除索引
     * void
     */
    void delete();
    
    /**
     * 判断索引库是否存在
     * @return boolean
     */
    boolean exists();
    
    /**
     * 刷出缓冲区
     * void
     */
    void flush();
    
    /**
     * 刷新索引
     * void
     */
    void refresh();
    
    /**
     * 返回 index
     *
     * @return index
     */
    T index();
}
