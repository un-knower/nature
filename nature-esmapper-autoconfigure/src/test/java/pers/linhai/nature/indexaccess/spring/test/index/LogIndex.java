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

import pers.linhai.nature.esmapper.model.analysis.analyzers.PatternAnalyzer;
import pers.linhai.nature.esmapper.model.core.RolloverStrategy;
import pers.linhai.nature.esmapper.model.index.Index;
import pers.linhai.nature.esmapper.model.index.section.AnalysisSection;
import pers.linhai.nature.esmapper.model.index.section.IndexSection.DynamicSettings;
import pers.linhai.nature.esmapper.model.index.section.IndexSection.StaticSettings;
import pers.linhai.nature.esmapper.model.type.Type;
import pers.linhai.nature.indexaccess.spring.test.type.LogInfo;

/**
 * @author  shinelon
 * @version  V100R001C00
 */
public class LogIndex extends Index
{

    /** 
     * <默认构造函数>
     */
    public LogIndex()
    {
    }

    /**
     * 
     *
     * @param typeClassSet
     */
    @Override
    protected void registerTypes(Set<Class< ? extends Type>> typeClassSet)
    {
        typeClassSet.add(LogInfo.class);
    }

    /**
     * 
     *
     * @return
     */
    @Override
    protected String name()
    {
        // TODO shinelon 简要描述
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
        // TODO shinelon 简要描述
        return null;
    }

    /**
     * 
     *
     * @param rolloverStrategy
     */
    @Override
    protected void rolloverStrategy(RolloverStrategy rolloverStrategy)
    {
        // TODO shinelon 简要描述
        
    }

    /**
     * 
     *
     * @param staticSettings
     */
    @Override
    protected void staticSettings(StaticSettings staticSettings)
    {
        // TODO shinelon 简要描述
        
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

    /**
     * 
     *
     * @param analysisSection
     */
    @Override
    protected void analysisSettings(AnalysisSection analysisSection)
    {
        PatternAnalyzer patternAnalyzer = new PatternAnalyzer();
        patternAnalyzer.setName("common_pattern_analyzer");
        patternAnalyzer.setPattern("\\W|_");
        analysisSection.addAnalyzer(patternAnalyzer);
    }
    
}
