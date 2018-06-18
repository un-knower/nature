/*
 * 项 目 名:  Storage Tool Service V100R001C00
 * 文 件 名:  pers.linhai.nature.indexaccess.interfaces.TypeAccessorInitialization.java
 * 版       权:  XXX Technologies Co., Ltd. Copyright 2017,  All rights reserved.
 * 描       述:  XXX PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * 修 改 人:  lilinhai
 * 修改时间:  2017年10月6日
 * 修改内容:  创建
 */
package pers.linhai.nature.esmapper.interfaces;

import pers.linhai.nature.esmapper.core.processor.IndicesAdminClientProcessor;

/**
 * TypeAccessor 初始化方案，抽象接口，主要用于为spring集成（将实例交给spring容器托管）完美对接
 * @author  lilinhai
 * @version  V100R001C00
 */
public interface TypeAccessorInitialization
{
    
    /**
     *
     * @param mappingConfigurationlist void
     */
    void initialize(IndicesAdminClientProcessor indicesAdminClientProcessor);
    
    /**
     * 初始化配置信息
     * <p>Title         : initializeConfiguration lilinhai 2018年6月18日 上午12:42:06</p>
     * void
     */
    void initializeConfiguration();
}
