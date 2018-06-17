/*
 * 项 目 名:  Storage Tool Service V100R001C00
 * 文 件 名:  pers.linhai.nature.indexaccess.log.ElasticsearchBulkProcessorConfig.java
 * 版       权:  XXX Technologies Co., Ltd. Copyright 2017,  All rights reserved.
 * 描       述:  XXX PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * 修 改 人:  shinelon
 * 修改时间:  2017年7月5日
 * 修改内容:  创建
 */
package pers.linhai.nature.esaccessor.log4jappender.plugins;

import org.apache.logging.log4j.core.Core;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderFactory;
import org.apache.logging.log4j.core.util.Builder;

/**
 * 
 * @author  shinelon
 * @version  V100R001C00
 */
@Plugin(name = "IndexDynamicSettings", category = Core.CATEGORY_NAME, printObject = true)
public class IndexDynamicSettingsPlugin implements Builder<IndexDynamicSettingsPlugin>
{
    
    /**
     * 
     *
     * @return
     */
    public IndexDynamicSettingsPlugin build()
    {
        return this;
    }
    
    /**
     * 副本数: The number of replicas each primary shard has. Defaults to 1. 
     */
    @PluginBuilderAttribute
    private int numberOfReplicas = -1;
    
    /**
     * <pre>
     *  Auto-expand the number of replicas based on the number of available nodes. Set to a dash delimited lower and 
     *  upper bound (e.g. 0-5) or use all for the upper bound (e.g. 0-all). Defaults to false (i.e. disabled).
     * </pre> 
     */
    @PluginBuilderAttribute
    private boolean autoExpandReplicas;
    
    /**
     * How often to perform a refresh operation, which makes recent changes to the index visible to search. Defaults to 1s. Can be set to -1 to disable refresh.
     */
    @PluginBuilderAttribute
    private int refreshInterval = 1;
    
    /**
     * <pre>
     * 查询的时候最大返回数，默认为10000
     * The maximum value of from + size for searches to this index. Defaults to 10000. 
     * Search requests take heap memory and time proportional to from + size and this limits that memory. 
     * See Scroll or Search After for a more efficient alternative to raising this. 
     * </pre> 
     */
    @PluginBuilderAttribute
    private int maxResultWindow;
    
    /**
     * <pre>
     *  The maximum value of window_size for rescore`s in searches of this index. Defaults to `index.max_result_window which defaults to 10000. 
     *  Search requests take heap memory and time proportional to max(window_size, from + size) and this limits that memory. 
     * </pre>
     */
    @PluginBuilderAttribute
    private int maxRescoreWindow;
    
    /**
     * Set to true to make the index and index metadata read only, false to allow writes and metadata changes. 
     */
    @PluginBuilderAttribute
    private boolean blocksReadOnly;
    
    /**
     * Set to true to disable read operations against the index. 
     */
    @PluginBuilderAttribute
    private boolean blocksRead;
    
    /**
     * Set to true to disable write operations against the index. 
     */
    @PluginBuilderAttribute
    private boolean blocksWrite;
    
    /**
     * Set to true to disable index metadata reads and writes. 
     */
    @PluginBuilderAttribute
    private boolean blocksMetadata;
    
    /**
     * Maximum number of refresh listeners available on each shard of the index. These listeners are used to implement refresh=wait_for. 
     */
    @PluginBuilderAttribute
    private int maxRefreshListeners;
    
    @PluginBuilderFactory
    public static final Builder<IndexDynamicSettingsPlugin> create()
    {
        return new IndexDynamicSettingsPlugin();
    }
    
    /**
     * 返回 numberOfReplicas
     *
     * @return numberOfReplicas
     */
    public int getNumberOfReplicas()
    {
        return numberOfReplicas;
    }
    
    /**
     * 返回 autoExpandReplicas
     *
     * @return autoExpandReplicas
     */
    public boolean isAutoExpandReplicas()
    {
        return autoExpandReplicas;
    }
    
    /**
     * 返回 refreshInterval
     *
     * @return refreshInterval
     */
    public int getRefreshInterval()
    {
        return refreshInterval;
    }
    
    /**
     * 返回 maxResultWindow
     *
     * @return maxResultWindow
     */
    public int getMaxResultWindow()
    {
        return maxResultWindow;
    }
    
    /**
     * 返回 maxRescoreWindow
     *
     * @return maxRescoreWindow
     */
    public int getMaxRescoreWindow()
    {
        return maxRescoreWindow;
    }
    
    /**
     * 返回 blocksReadOnly
     *
     * @return blocksReadOnly
     */
    public boolean isBlocksReadOnly()
    {
        return blocksReadOnly;
    }
    
    /**
     * 返回 blocksRead
     *
     * @return blocksRead
     */
    public boolean isBlocksRead()
    {
        return blocksRead;
    }
    
    /**
     * 返回 blocksWrite
     *
     * @return blocksWrite
     */
    public boolean isBlocksWrite()
    {
        return blocksWrite;
    }
    
    /**
     * 返回 blocksMetadata
     *
     * @return blocksMetadata
     */
    public boolean isBlocksMetadata()
    {
        return blocksMetadata;
    }
    
    /**
     * 返回 maxRefreshListeners
     *
     * @return maxRefreshListeners
     */
    public int getMaxRefreshListeners()
    {
        return maxRefreshListeners;
    }
}
