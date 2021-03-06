/*
 * 项 目 名:  Storage Tool Service V100R001C00
 * 文 件 名:  esdao.esdao.index.UserIndex.java
 * 版       权:  XXX Technologies Co., Ltd. Copyright 2017,  All rights reserved.
 * 描       述:  XXX PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * 修 改 人:  shinelon
 * 修改时间:  2017年6月11日
 * 修改内容:  创建
 */
package pers.linhai.nature.indexaccess.spring.test.index;

import java.util.Set;

import pers.linhai.nature.esmapper.model.core.RolloverStrategy;
import pers.linhai.nature.esmapper.model.index.Index;
import pers.linhai.nature.esmapper.model.index.section.AnalysisSection;
import pers.linhai.nature.esmapper.model.index.section.IndexSection.DynamicSettings;
import pers.linhai.nature.esmapper.model.index.section.IndexSection.StaticSettings;
import pers.linhai.nature.esmapper.model.type.Type;
import pers.linhai.nature.indexaccess.spring.test.type.UserInfo;

/**
 * @author  shinelon
 * @version  V100R001C00
 */
public class UserIndex extends Index
{

    /** 
     * <默认构造函数>
     */
    public UserIndex()
    {
    }
    
    /**
     * 
     *
     * @param rolloverStrategy
     */
    protected void rolloverStrategy(RolloverStrategy rolloverStrategy)
    {
/*        rolloverStrategy.setEnabled(true);
        rolloverStrategy.setMaxLifeMS(120 * 1000);
        rolloverStrategy.setMaxSizeMB(1);
        rolloverStrategy.setMaxDocs(1000);
        rolloverStrategy.setIndexNumber(3);*/
    }
    
    /**
     * 
     *
     * @return
     */
    @Override
    protected String name()
    {
        return null;
    }

    /**
     * 
     *
     * @return
     */
    @Override
    protected String alias()
    {
        return null;
    }



    protected void registerTypes(Set<Class<? extends Type>> typeClassSet)
    {
        typeClassSet.add(UserInfo.class);
    }

    /**
     * 
     *
     * @param analysisSection
     */
    @Override
    protected void analysisSettings(AnalysisSection analysisSection)
    {
        
    }



    /**
     * 
     *
     * @param staticSettings
     */
    @Override
    protected void staticSettings(StaticSettings staticSettings)
    {
        
    }

    /**
     * 
     *
     * @param dynamicSettings
     */
    @Override
    protected void dynamicSettings(DynamicSettings dynamicSettings)
    {
        //设置副本数为0
        dynamicSettings.setNumberOfReplicas(0);
    }
    
    
}
