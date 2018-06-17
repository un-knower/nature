/*
 * 项 目 名:  Storage Tool Service V100R001C00
 * 文 件 名:  pers.linhai.nature.indexaccess.core.IndexAccessScheduler.java
 * 版       权:  XXX Technologies Co., Ltd. Copyright 2017,  All rights reserved.
 * 描       述:  XXX PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * 修 改 人:  shinelon
 * 修改时间:  2017年7月30日
 * 修改内容:  创建
 */
package pers.linhai.nature.esaccessor.core.processor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.elasticsearch.action.admin.cluster.stats.ClusterStatsNodeResponse;
import org.elasticsearch.action.admin.cluster.stats.ClusterStatsResponse;
import org.elasticsearch.action.admin.indices.stats.CommonStats;
import org.elasticsearch.action.admin.indices.stats.ShardStats;
import org.elasticsearch.cluster.metadata.AliasMetaData;
import org.elasticsearch.cluster.metadata.IndexMetaData;

import com.carrotsearch.hppc.cursors.ObjectObjectCursor;

import pers.linhai.nature.esaccessor.model.core.RolloverIndexInfo;
import pers.linhai.nature.esaccessor.model.core.RolloverStrategy;
import pers.linhai.nature.esaccessor.model.core.RolloverIndexInfo.StatInfo;
import pers.linhai.nature.esaccessor.model.index.Index;

/**
 * 
 * 
 * @author  shinelon
 * @version  V100R001C00
 */
public abstract class RolloverIndexMonitor extends Thread
{
    
    private static final RolloverIndexMonitor rolloverIndexMonitor = new RolloverIndexMonitor()
    {
    };
    
    private Map<String, IndicesAdminClientProcessor> indicesAdminClientProcessorMap = new HashMap<String, IndicesAdminClientProcessor>();
    
    private boolean isStartup;
    
    private RolloverIndexMonitor()
    {
        
    }
    
    public static void startup()
    {
        //设置为守护线程
        rolloverIndexMonitor.setDaemon(true);
        rolloverIndexMonitor.setName("index-access-scheduler");
        rolloverIndexMonitor.start();
        rolloverIndexMonitor.isStartup = true;
    }
    
    /**
     * 返回 isStartup
     *
     * @return isStartup
     */
    public static boolean isStartup()
    {
        return rolloverIndexMonitor.isStartup;
    }

    /**
     * 添加一个TransportClientProcessor对象，让线程去给他动态指定index
     * @param indexName
     * @param transportClientProcessor void
     */
    public static void add(IndicesAdminClientProcessor indicesAdminClientProcessor)
    {
        rolloverIndexMonitor.indicesAdminClientProcessorMap.put(indicesAdminClientProcessor.getIndex().getName(), indicesAdminClientProcessor);
    }

    /**
     * 
     *
     */
    public void run()
    {
        sleep0(10 * 1000);
        
        while (true)
        {
            try
            {
                Map<String, List<RolloverIndexInfo>> indexMetaInfoListMap = new HashMap<String, List<RolloverIndexInfo>>();
                build(indexMetaInfoListMap);
                
                for (Entry<String, List<RolloverIndexInfo>> e : indexMetaInfoListMap.entrySet())
                {
                    List<RolloverIndexInfo> rolloverIndexInfoList = e.getValue();
                    Collections.sort(rolloverIndexInfoList);
                    
                    RolloverIndexInfo newRolloverIndexInfo = rolloverIndexInfoList.get(rolloverIndexInfoList.size() - 1);
                    
                    IndicesAdminClientProcessor indicesAdminClientProcessor = indicesAdminClientProcessorMap.get(e.getKey());
                    
                    Index index = indicesAdminClientProcessor.getIndex();
                    boolean needCreatRolloverIndex = needCreatRolloverIndex(newRolloverIndexInfo, index);
                           
                    //索引的文档数超过门限,索引所占据磁盘空间超过门限,索引最大生命超过门限。三种情况满足其一，创建滚动索引
                    if (needCreatRolloverIndex)
                    {
                        // 创建滚动索引
                        indicesAdminClientProcessor.createRolloverIndex();
                        
                        // 如果滚动索引数量超过指定门限（默认为4），则删除最老的
                        if (rolloverIndexInfoList.size() == index.getRolloverStrategy().getIndexNumber())
                        {
                            // 修改当前写索引的指针为最新创建的滚动索引
                            indicesAdminClientProcessor.updateTransportClientProcessorIndexPointer(rolloverIndexInfoList.get(0).getIndexName());
                            
                            indicesAdminClientProcessor.delete(rolloverIndexInfoList.get(0).getIndexName());
                            LogManager.getRootLogger().info("Deleted rolling index [" + rolloverIndexInfoList.get(0).getIndexName() + "] : rolloverIndexNumber[" 
                                    + rolloverIndexInfoList.size() + "] == maxIndexNumber[" + index.getRolloverStrategy().getIndexNumber() + "]");
                        }
                        else
                        {
                            // 修改当前写索引的指针为最新创建的滚动索引
                            indicesAdminClientProcessor.updateTransportClientProcessorIndexPointer(null);
                        }
                    }
                    
                    if (rolloverIndexInfoList.size() > index.getRolloverStrategy().getIndexNumber())
                    {
                        // 修改当前写索引的指针为最新创建的滚动索引
                        indicesAdminClientProcessor.updateTransportClientProcessorIndexPointer(rolloverIndexInfoList.get(0).getIndexName());
                        
                        indicesAdminClientProcessor.delete(rolloverIndexInfoList.get(0).getIndexName());
                        LogManager.getRootLogger().info("Deleted rolling index [" + rolloverIndexInfoList.get(0).getIndexName() + "] : rolloverIndexNumber[" 
                                + rolloverIndexInfoList.size() + "] > maxIndexNumber[" + index.getRolloverStrategy().getIndexNumber() + "]");
                    }
                }
            }
            catch (Throwable e)
            {
                e.printStackTrace();
            }
            finally 
            {
                sleep0(30 * 1000);
            }
        }
    }
    
    /**
     * 是否满足创建滚动索引的条件
     * 索引的文档数超过门限,索引所占据磁盘空间超过门限,索引最大生命超过门限。三种情况满足其一，创建滚动索引
     * @param newRolloverIndexInfo
     * @param index
     * @return boolean
     */
    private boolean needCreatRolloverIndex(RolloverIndexInfo newRolloverIndexInfo, Index index)
    {
        boolean needCreatRolloverIndex = false;
        StatInfo statInfo = newRolloverIndexInfo.getStatInfo();
        RolloverStrategy rolloverStrategy = index.getRolloverStrategy();
        if (statInfo.getDocsCount() > rolloverStrategy.getMaxDocs())
        {
            needCreatRolloverIndex = true;
            LogManager.getRootLogger().info("Satisfy rolling index creation criteria 1 with index[" 
                    + index.getName() + "] : docsCount[" + statInfo.getDocsCount() + "] > maxDocs[" + rolloverStrategy.getMaxDocs() + "]");
        }
        else if (statInfo.getSizeInBytes() > rolloverStrategy.getMaxSize())
        {
            needCreatRolloverIndex = true;
            LogManager.getRootLogger().info("Satisfy rolling index creation criteria 2 with index[" 
                    + index.getName() + "] : sizeInBytes[" + statInfo.getSizeInBytes() + "] > maxSize[" + rolloverStrategy.getMaxSize() + "]");
        }
        else if (System.currentTimeMillis() - newRolloverIndexInfo.getCreationDate() > rolloverStrategy.getMaxLife())
        {
            needCreatRolloverIndex = true;
            LogManager.getRootLogger().info("Satisfy rolling index creation criteria 3 with index[" 
                    + index.getName() + "] : indexLife[" +(System.currentTimeMillis() - newRolloverIndexInfo.getCreationDate()) 
                    + "] > maxLife[" + rolloverStrategy.getMaxLife() + "]");
        }
        return needCreatRolloverIndex;
    }

    /** 
     * 
     *
     * @param commonStatsListMap
     * @param indexMetaInfoListMap void
     */
    private void build(Map<String, List<RolloverIndexInfo>> indexMetaInfoListMap)
    {
        Map<String, List<CommonStats>> commonStatsListMap = new HashMap<String, List<CommonStats>>();
        build0(commonStatsListMap);
        
        List<IndexMetaData> indexMetaDataList = ClusterAdminClientProcessor.instance().getIndexMetaDataList();
        for (IndexMetaData indexMetaData : indexMetaDataList)
        {
            for (IndicesAdminClientProcessor indicesAdminClientProcessor : indicesAdminClientProcessorMap.values())
            {
                String indexName = indicesAdminClientProcessor.getIndex().getName();
                if (indexMetaData.getIndex().getName().startsWith(indexName))
                {
                    List<RolloverIndexInfo> indexMetaInfoList = indexMetaInfoListMap.get(indexName);
                    if (indexMetaInfoList == null)
                    {
                        indexMetaInfoList = new ArrayList<RolloverIndexInfo>();
                        indexMetaInfoListMap.put(indexName, indexMetaInfoList);
                    }
                    
                    RolloverIndexInfo imi = new RolloverIndexInfo();
                    imi.setCreationDate(indexMetaData.getCreationDate());
                    imi.setIndexName(indexMetaData.getIndex().getName());
                    imi.setStatInfo(commonStatsListMap.get(indexMetaData.getIndex().getName()));
                    
                    //设置别名
                    Iterator<ObjectObjectCursor<String, AliasMetaData>> aliasMetaDataIterator = indexMetaData.getAliases().iterator();
                    while (aliasMetaDataIterator.hasNext())
                    {
                        ObjectObjectCursor<String, AliasMetaData> objectObjectCursor = aliasMetaDataIterator.next();
                        imi.setIndexAliasName(objectObjectCursor.value.getAlias());
                        break;
                    }
                    indexMetaInfoList.add(imi);
                }
            }
        }
    }

    /** 
     * 
     *
     * @param commonStatsListMap void
     */
    private void build0(Map<String, List<CommonStats>> commonStatsListMap)
    {
        ClusterStatsResponse clusterStatsIndices = ClusterAdminClientProcessor.instance().getClusterStatsResponse();
        for (ClusterStatsNodeResponse r : clusterStatsIndices.getNodes())
        {
            for (ShardStats shardStats : r.shardsStats())
            {
                List<CommonStats> shardStatsList = commonStatsListMap.get(shardStats.getShardRouting().getIndexName());
                if (shardStatsList == null)
                {
                    shardStatsList = new ArrayList<CommonStats>();
                    commonStatsListMap.put(shardStats.getShardRouting().getIndexName(), shardStatsList);
                }
                shardStatsList.add(shardStats.getStats());
            }
        }
    }
    
    
    private void sleep0(long millis)
    {
        try
        {
            sleep(millis);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
    
}
