/*
 * 项 目 名:  Storage Tool Service V100R001C00
 * 文 件 名:  pers.linhai.esframework.model.index.section.IndexSection.java
 * 版       权:  XXX Technologies Co., Ltd. Copyright 2017,  All rights reserved.
 * 描       述:  XXX PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * 修 改 人:  shinelon
 * 修改时间:  2017年6月10日
 * 修改内容:  创建
 */
package pers.linhai.nature.esaccessor.model.index.section;

import java.io.IOException;

import org.elasticsearch.common.xcontent.XContentBuilder;

/**
 * 
 * 索引区域
 * @author  shinelon
 * @version  V100R001C00
 */
public class IndexSection
{
    /**
     * They can only be set at index creation time or on a closed index. 
     * 这个集合中的设置只能在索引创建的时候设置
     */
    private StaticSettings staticSettings = new StaticSettings();
    
    /**
     * They can be changed on a live index using the update-index-settings API. 
     * 这个集合中的设置可以在索引运行期间设置
     */
    private DynamicSettings dynamicSettings = new DynamicSettings();
    
    /**
     * 检查是否有新的配置
     *
     * @return boolean
     */
    public boolean hasNewConfig()
    {
        return staticSettings.hasNewConfig() || dynamicSettings.hasNewConfig();
    }
    
    /**
     * 通过输入的XContentBuilder将该分词器的配置封装回去
     * @param jsonBuilder
     * @throws IOException void
     */
    public void build(XContentBuilder jsonBuilder) throws IOException
    {
        staticSettings.build(jsonBuilder);
        
        dynamicSettings.build(jsonBuilder);
    }
    
    /**
     * 返回 staticSettings
     *
     * @return staticSettings
     */
    public StaticSettings staticSettings()
    {
        return staticSettings;
    }

    /**
     * 返回 dynamicSettings
     *
     * @return dynamicSettings
     */
    public DynamicSettings dynamicSettings()
    {
        return dynamicSettings;
    }

    /**
     * They can only be set at index creation time or on a closed index. 
     * @author  shinelon
     * @version  V100R001C00
     */
    public static final class StaticSettings
    {
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
        private String shardCheckOnStartup;
        
        /**
         * <pre>
         *  The default value compresses stored data with LZ4 compression, but this can be set to best_compression which uses 
         *  DEFLATE for a higher compression ratio, at the expense of slower stored fields performance. 
         * </pre>
         */
        private String codec;
        
        /**
         * <pre>
         *  The number of shards a custom routing value can go to. Defaults to 1 and can only be set at index creation time. 
         *  This value must be less than the index.number_of_shards unless the index.number_of_shards value is also 1. 
         *  See Routing to an index partition for more details about how this setting is used. 
         * </pre>
         */
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
         * 对numberOfShards进行赋值
         *
         * @param numberOfShards
         */
        public void setNumberOfShards(int numberOfShards)
        {
            this.numberOfShards = numberOfShards;
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
         * 对shardCheckOnStartup进行赋值
         *
         * @param shardCheckOnStartup
         */
        public void setShardCheckOnStartup(String shardCheckOnStartup)
        {
            this.shardCheckOnStartup = shardCheckOnStartup;
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
         * 对codec进行赋值
         *
         * @param codec
         */
        public void setCodec(String codec)
        {
            this.codec = codec;
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

        /**
         * 对routingPartitionSize进行赋值
         *
         * @param routingPartitionSize
         */
        public void setRoutingPartitionSize(int routingPartitionSize)
        {
            this.routingPartitionSize = routingPartitionSize;
        }
        
        /**
         * 检查是否有新的配置
         *
         * @return boolean
         */
        public boolean hasNewConfig()
        {
            return numberOfShards > 0 || shardCheckOnStartup != null || codec != null || routingPartitionSize > 0;
        }
        
        /**
         * 通过输入的XContentBuilder将该分词器的配置封装回去
         * @param jsonBuilder
         * @throws IOException void
         */
        public void build(XContentBuilder jsonBuilder) throws IOException
        {
            //设置分片数
            if(numberOfShards > 0)
            {
                jsonBuilder.field("number_of_shards", numberOfShards);
            }
            
            //设置启动检查分片的策略
            if(shardCheckOnStartup != null)
            {
                jsonBuilder.startObject("shard");
                jsonBuilder.field("check_on_startup", shardCheckOnStartup);
                jsonBuilder.endObject();
            }
            
            //设置数据在存储的时候的压缩策略
            if(codec != null)
            {
                jsonBuilder.field("codec", codec);
            }
            
            //设置路由可以到达的分片数
            if(routingPartitionSize > 0)
            {
                jsonBuilder.field("routing_partition_size", routingPartitionSize);
            }
        }
    }
    
    /**
     * They can be changed on a live index using the update-index-settings API. 
     * 
     * @author  shinelon
     * @version  V100R001C00
     */
    public static final class DynamicSettings
    {
        /**
         * 副本数: The number of replicas each primary shard has. Defaults to 1. 
         */
        private int numberOfReplicas = -1;
        
        /**
         * <pre>
         *  Auto-expand the number of replicas based on the number of available nodes. Set to a dash delimited lower and 
         *  upper bound (e.g. 0-5) or use all for the upper bound (e.g. 0-all). Defaults to false (i.e. disabled).
         * </pre> 
         */
        private boolean autoExpandReplicas;
        
        /**
         * How often to perform a refresh operation, which makes recent changes to the index visible to search. Defaults to 1s. Can be set to -1 to disable refresh.
         */
        private int refreshInterval = 1;
        
        /**
         * <pre>
         * 查询的时候最大返回数，默认为10000
         * The maximum value of from + size for searches to this index. Defaults to 10000. 
         * Search requests take heap memory and time proportional to from + size and this limits that memory. 
         * See Scroll or Search After for a more efficient alternative to raising this. 
         * </pre> 
         */
        private int maxResultWindow;
        
        /**
         * <pre>
         *  The maximum value of window_size for rescore`s in searches of this index. Defaults to `index.max_result_window which defaults to 10000. 
         *  Search requests take heap memory and time proportional to max(window_size, from + size) and this limits that memory. 
         * </pre>
         */
        private int maxRescoreWindow;
        
        /**
         * Set to true to make the index and index metadata read only, false to allow writes and metadata changes. 
         */
        private boolean blocksReadOnly;
        
        /**
         * Set to true to disable read operations against the index. 
         */
        private boolean blocksRead;
        
        /**
         * Set to true to disable write operations against the index. 
         */
        private boolean blocksWrite;
        
        /**
         * Set to true to disable index metadata reads and writes. 
         */
        private boolean blocksMetadata;
        
        /**
         * Maximum number of refresh listeners available on each shard of the index. These listeners are used to implement refresh=wait_for. 
         */
        private int maxRefreshListeners;
        
        
        /**
         * 检查是否有新的配置
         *
         * @return boolean
         */
        public boolean hasNewConfig()
        {
            return numberOfReplicas >= 0 || autoExpandReplicas != false || refreshInterval != 1 || maxResultWindow > 0 
                    || maxRescoreWindow > 0 || blocksReadOnly != false || blocksRead != false || blocksWrite != false 
                    || blocksMetadata != false || maxRefreshListeners > 0;
        }
        
        /**
         * 通过输入的XContentBuilder将该分词器的配置封装回去
         * @param jsonBuilder
         * @throws IOException void
         */
        public void build(XContentBuilder jsonBuilder) throws IOException
        {
            //设置副本数
            if(numberOfReplicas >= 0)
            {
                jsonBuilder.field("number_of_replicas", numberOfReplicas);
            }
            
            //设置启动检查分片的策略
            if(autoExpandReplicas != false)
            {
                jsonBuilder.field("auto_expand_replicas", autoExpandReplicas);
            }
            
            //设置数据在更新后的刷新频率（让搜索可见）
            if(refreshInterval != 1)
            {
                jsonBuilder.field("refresh_interval", refreshInterval);
            }
            
            //设置查询最大返回的数据量
            if(maxResultWindow > 0)
            {
                jsonBuilder.field("max_result_window", maxResultWindow);
            }
            
            //设置查询时最大能截取的分页长度。
            if(maxRescoreWindow > 0)
            {
                jsonBuilder.field("max_rescore_window", maxRescoreWindow);
            }
            
            //设置索引更新后，一个索引在刷新时最大有多少个监听器
            if(maxRefreshListeners > 0)
            {
                jsonBuilder.field("max_refresh_listeners", maxRefreshListeners);
            }
            
            if(blocksReadOnly != false || blocksRead != false || blocksWrite != false || blocksMetadata != false)
            {
                jsonBuilder.startObject("blocks");
                
                if(blocksReadOnly != false)
                {
                    jsonBuilder.field("read_only", blocksReadOnly);
                }
                
                if(blocksRead != false)
                {
                    jsonBuilder.field("read", blocksRead);
                }
                
                if(blocksWrite != false)
                {
                    jsonBuilder.field("write", blocksWrite);
                }
                
                if(blocksMetadata != false)
                {
                    jsonBuilder.field("metadata", blocksMetadata);
                }
                
                jsonBuilder.endObject();
            }
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
         * 对numberOfReplicas进行赋值
         *
         * @param numberOfReplicas
         */
        public void setNumberOfReplicas(int numberOfReplicas)
        {
            this.numberOfReplicas = numberOfReplicas;
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
         * 对autoExpandReplicas进行赋值
         *
         * @param autoExpandReplicas
         */
        public void setAutoExpandReplicas(boolean autoExpandReplicas)
        {
            this.autoExpandReplicas = autoExpandReplicas;
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
         * 对refreshInterval进行赋值
         *
         * @param refreshInterval
         */
        public void setRefreshInterval(int refreshInterval)
        {
            this.refreshInterval = refreshInterval;
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
         * 对maxResultWindow进行赋值
         *
         * @param maxResultWindow
         */
        public void setMaxResultWindow(int maxResultWindow)
        {
            this.maxResultWindow = maxResultWindow;
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
         * 对blocksReadOnly进行赋值
         *
         * @param blocksReadOnly
         */
        public void setBlocksReadOnly(boolean blocksReadOnly)
        {
            this.blocksReadOnly = blocksReadOnly;
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
         * 对blocksRead进行赋值
         *
         * @param blocksRead
         */
        public void setBlocksRead(boolean blocksRead)
        {
            this.blocksRead = blocksRead;
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
         * 对blocksWrite进行赋值
         *
         * @param blocksWrite
         */
        public void setBlocksWrite(boolean blocksWrite)
        {
            this.blocksWrite = blocksWrite;
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
         * 对blocksMetadata进行赋值
         *
         * @param blocksMetadata
         */
        public void setBlocksMetadata(boolean blocksMetadata)
        {
            this.blocksMetadata = blocksMetadata;
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

        /**
         * 对maxRefreshListeners进行赋值
         *
         * @param maxRefreshListeners
         */
        public void setMaxRefreshListeners(int maxRefreshListeners)
        {
            this.maxRefreshListeners = maxRefreshListeners;
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
         * 对maxRescoreWindow进行赋值
         *
         * @param maxRescoreWindow
         */
        public void setMaxRescoreWindow(int maxRescoreWindow)
        {
            this.maxRescoreWindow = maxRescoreWindow;
        }
    }
}
