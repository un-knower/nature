/*
 * 项 目 名:  Storage Tool Service V100R001C00
 * 文 件 名:  esdao.esdao.generaltest.ITest1.java
 * 版       权:  XXX Technologies Co., Ltd. Copyright 2017,  All rights reserved.
 * 描       述:  XXX PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * 修 改 人:  shinelon
 * 修改时间:  2017年7月1日
 * 修改内容:  创建
 */
package pers.linhai.nature.esaccessor.bak.generaltest;

import pers.linhai.nature.esaccessor.bak.index.ObjType;

/**
 * 
 * 
 * @author  shinelon
 * @version  V100R001C00
 */
public interface ITest1<T extends ObjType>
{
    
    void save(T t);
    
    T get();
}
