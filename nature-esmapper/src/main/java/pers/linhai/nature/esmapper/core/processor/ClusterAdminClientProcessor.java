/*
 * 项 目 名:  Storage Tool Service V100R001C00
 * 文 件 名:  pers.linhai.nature.indexaccess.core.clientprocessor.ClusterAdminClientProcessor.java
 * 版       权:  XXX Technologies Co., Ltd. Copyright 2017,  All rights reserved.
 * 描       述:  XXX PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * 修 改 人:  shinelon
 * 修改时间:  2017年7月30日
 * 修改内容:  创建
 */
package pers.linhai.nature.esmapper.core.processor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.elasticsearch.action.admin.cluster.state.ClusterStateResponse;
import org.elasticsearch.action.admin.cluster.stats.ClusterStatsResponse;
import org.elasticsearch.client.ClusterAdminClient;
import org.elasticsearch.cluster.metadata.IndexMetaData;
import org.elasticsearch.common.collect.ImmutableOpenMap;

import com.carrotsearch.hppc.cursors.ObjectObjectCursor;

/**
 * 
 * 
 * @author  shinelon
 * @version  V100R001C00
 */
public abstract class ClusterAdminClientProcessor
{
    
    private static final ClusterAdminClientProcessor clusterAdminClientProcessor = new ClusterAdminClientProcessorImpl();
    
    private ClusterAdminClientProcessor()
    {
        
    }
    
    /**
     * 单例
     * @return ClusterAdminClientProcessor
     */
    public static ClusterAdminClientProcessor instance()
    {
        return clusterAdminClientProcessor;
    }
    
    public abstract List<IndexMetaData> getIndexMetaDataList();
    
    /**
     * 获取索引元数据信息
     * @return
     */
    public abstract ClusterStatsResponse getClusterStatsResponse();
    
    /**
     * ClusterAdminClientProcessor的具体实现
     * @author  shinelon
     * @version  V100R001C00
     */
    private static class ClusterAdminClientProcessorImpl extends ClusterAdminClientProcessor
    {
        private final ClusterAdminClient clusterAdminClient = ClusterNodeAccessor.instance().getClusterAdminClient();
        
        /**
         * 获取索引元数据信息
         * @return
         */
        public List<IndexMetaData> getIndexMetaDataList()
        {
            List<IndexMetaData> indexMetaDataList = new ArrayList<IndexMetaData>();
            ClusterStateResponse response = clusterAdminClient.prepareState().get();
            ImmutableOpenMap<String, IndexMetaData> immutableOpenMap = response.getState().getMetaData().getIndices();
            Iterator<ObjectObjectCursor<String, IndexMetaData>> indexMetaDataIterator = immutableOpenMap.iterator();  
            while (indexMetaDataIterator.hasNext())
            {
                ObjectObjectCursor<String, IndexMetaData> objectObjectCursor = indexMetaDataIterator.next();
                indexMetaDataList.add(objectObjectCursor.value);
            }
            
            return indexMetaDataList;
        }
        
        /**
         * 获取索引元数据信息
         * @return
         */
        public ClusterStatsResponse getClusterStatsResponse()
        {
            ClusterStatsResponse response = clusterAdminClient.prepareClusterStats().get();
            return response;
        }
    }
}
