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
package pers.linhai.nature.indexaccess.log4jappender.plugins;

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
@Plugin(name = "IndexStaticSettings", category = Core.CATEGORY_NAME, printObject = true)
public class IndexStaticSettingsPlugin implements Builder<IndexStaticSettingsPlugin>
{
    
    /**
     * 
     *
     * @return
     */
    public IndexStaticSettingsPlugin build()
    {
        return this;
    }
    
    /**
     * <pre>
     *  The number of primary shards that an index should have. Defaults to 5. This setting can only be set at index creation time. 
     *  It cannot be changed on a closed index. Note: the number of shards are limited to 1024 per index. This limitation is a safety limit 
     *  to prevent accidental creation of indices that can destabilize a cluster due to resource allocation. The limit can be modified by 
     *  specifying export ES_JAVA_OPTS="-Des.index.max_number_of_shards=128" system property on every node that is part of the cluster. 
     *  
     *  索引应该具有的主碎片的数目。默认值为5。只能在索引创建时间设置此设置。它不能在封闭索引上更改。注意：每个索引的碎片数量限制为1024个。
     *  此限制是一种安全限制，以防止意外创建索引，因为资源分配会破坏集群的稳定性。限制可以修改指定出口es_java_opts =”的指标。max_number_of_shards = 128“在每一个节点是群集的一部分的系统性能。
     * </pre>
     */
    @PluginBuilderAttribute
    private int numberOfShards;
    
    /**
     * <pre>
     *  This functionality is experimental and may be changed or removed completely in a future release. Elastic will take a best effort approach to fix any issues,
     *  but experimental features are not subject to the support SLA of official GA features. Whether or not shards should be checked for corruption before opening. 
     *  When corruption is detected, it will prevent the shard from being opened. Accepts:
     *       false
     *           (default) Don’t check for corruption when opening a shard. 
     *       checksum
     *           Check for physical corruption. 
     *       true
     *           Check for both physical and logical corruption. This is much more expensive in terms of CPU and memory usage. 
     *       fix
     *           Check for both physical and logical corruption. Segments that were reported as corrupted will be automatically removed. This option may result in data loss. Use with extreme caution! 
     *   
     *       Checking shards may take a lot of time on large indices.
     * </pre>
     */
    @PluginBuilderAttribute
    private String shardCheckOnStartup;
    
    /**
     * <pre>
     *  The default value compresses stored data with LZ4 compression, but this can be set to best_compression which uses 
     *  DEFLATE for a higher compression ratio, at the expense of slower stored fields performance. 
     * </pre>
     */
    @PluginBuilderAttribute
    private String codec;
    
    /**
     * <pre>
     *  The number of shards a custom routing value can go to. Defaults to 1 and can only be set at index creation time. 
     *  This value must be less than the index.number_of_shards unless the index.number_of_shards value is also 1. 
     *  See Routing to an index partition for more details about how this setting is used. 
     * </pre>
     */
    @PluginBuilderAttribute
    private int routingPartitionSize;

    /**
     * 返回 numberOfShards
     *
     * @return numberOfShards
     */
    public int getNumberOfShards()
    {
        return numberOfShards;
    }

    /**
     * 返回 shardCheckOnStartup
     *
     * @return shardCheckOnStartup
     */
    public String getShardCheckOnStartup()
    {
        return shardCheckOnStartup;
    }

    /**
     * 返回 codec
     *
     * @return codec
     */
    public String getCodec()
    {
        return codec;
    }

    /**
     * 返回 routingPartitionSize
     *
     * @return routingPartitionSize
     */
    public int getRoutingPartitionSize()
    {
        return routingPartitionSize;
    }
    
    @PluginBuilderFactory
    public static final Builder<IndexStaticSettingsPlugin> create()
    {
        return new IndexStaticSettingsPlugin();
    }
}
