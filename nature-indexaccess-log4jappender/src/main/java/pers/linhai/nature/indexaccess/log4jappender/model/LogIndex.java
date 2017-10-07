/*
 * 项 目 名:  Storage Tool Service V100R001C00
 * 文 件 名:  pers.linhai.nature.indexaccess.log4j2.model.Log4j2Index.java
 * 版       权:  XXX Technologies Co., Ltd. Copyright 2017,  All rights reserved.
 * 描       述:  XXX PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * 修 改 人:  shinelon
 * 修改时间:  2017年7月7日
 * 修改内容:  创建
 */
package pers.linhai.nature.indexaccess.log4jappender.model;

import java.util.Set;

import pers.linhai.nature.indexaccess.log4jappender.plugins.IndexDynamicSettingsPlugin;
import pers.linhai.nature.indexaccess.log4jappender.plugins.IndexStaticSettingsPlugin;
import pers.linhai.nature.indexaccess.model.analysis.analyzers.PatternAnalyzer;
import pers.linhai.nature.indexaccess.model.core.RolloverStrategy;
import pers.linhai.nature.indexaccess.model.index.Index;
import pers.linhai.nature.indexaccess.model.index.section.AnalysisSection;
import pers.linhai.nature.indexaccess.model.index.section.IndexSection.DynamicSettings;
import pers.linhai.nature.indexaccess.model.index.section.IndexSection.StaticSettings;
import pers.linhai.nature.indexaccess.model.type.Type;

/**
 * 
 * 
 * @author  shinelon
 * @version  V100R001C00
 */
public class LogIndex extends Index
{

    private static IndexDynamicSettingsPlugin indexDynamicSettings;
    
    private static IndexStaticSettingsPlugin indexStaticSettings;
    
    /**
     * 对indexDynamicSettings进行赋值
     *
     * @param indexDynamicSettings
     */
    public static void setIndexDynamicSettings(IndexDynamicSettingsPlugin indexDynamicSettings)
    {
        LogIndex.indexDynamicSettings = indexDynamicSettings;
    }
    
    /**
     * 对indexStaticSettings进行赋值
     *
     * @param indexStaticSettings
     */
    public static void setIndexStaticSettings(IndexStaticSettingsPlugin indexStaticSettings)
    {
        LogIndex.indexStaticSettings = indexStaticSettings;
    }

    /** 
     * <默认构造函数>
     *
     */
    public LogIndex()
    {
        
    }
    
    protected String name()
    {
        return "log_index";
    }
    
    protected String alias()
    {
        return "log4j2_index";
    }
    
    protected void registerTypes(Set<Class<? extends Type>> typeClassSet)
    {
        typeClassSet.add(LogInfo.class);
    }
    
    /**
     * 分析设置
     *
     * @param analysisSection void
     */
    protected void analysisSettings(AnalysisSection analysisSection)
    {
        PatternAnalyzer patternAnalyzer = new PatternAnalyzer();
        patternAnalyzer.setName("common_pattern_analyzer");
        patternAnalyzer.setPattern("\\W|_");
        analysisSection.addAnalyzer(patternAnalyzer);
    }
    
    /**
     * 静态设置
     * @param staticSettings void
     */
    protected void staticSettings(StaticSettings staticSettings)
    {
        if(indexStaticSettings != null)
        {
            staticSettings.setNumberOfShards(indexStaticSettings.getNumberOfShards());
            staticSettings.setCodec(indexStaticSettings.getCodec());
            staticSettings.setRoutingPartitionSize(indexStaticSettings.getRoutingPartitionSize());
            staticSettings.setShardCheckOnStartup(indexStaticSettings.getShardCheckOnStartup());
        }
        
    }
    
    /**
     * 动态设置
     * @param dynamicSettings void
     */
    protected void dynamicSettings(DynamicSettings dynamicSettings)
    {
        if(indexDynamicSettings != null)
        {
            dynamicSettings.setNumberOfReplicas(indexDynamicSettings.getNumberOfReplicas());
            dynamicSettings.setAutoExpandReplicas(indexDynamicSettings.isAutoExpandReplicas());
            dynamicSettings.setBlocksMetadata(indexDynamicSettings.isBlocksMetadata());
            dynamicSettings.setBlocksRead(indexDynamicSettings.isBlocksRead());
            dynamicSettings.setBlocksReadOnly(indexDynamicSettings.isBlocksReadOnly());
            dynamicSettings.setBlocksWrite(indexDynamicSettings.isBlocksWrite());
            dynamicSettings.setMaxRefreshListeners(indexDynamicSettings.getMaxRefreshListeners());
            dynamicSettings.setMaxResultWindow(indexDynamicSettings.getMaxResultWindow());
            dynamicSettings.setMaxRescoreWindow(indexDynamicSettings.getMaxRescoreWindow());
            dynamicSettings.setRefreshInterval(indexDynamicSettings.getRefreshInterval());
        }
    }

    /**
     * 
     *
     * @param rolloverStrategy
     */
    @Override
    protected void rolloverStrategy(RolloverStrategy rolloverStrategy)
    {
        
    }
}
