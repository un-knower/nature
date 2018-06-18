/*
 * 文 件 名: pers.linhai.esdao.core.TransportClientHandler.java
 * 版    权: XXX Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述: <描述>
 * 修 改 人: shinelon
 * 修改时间: 2017年3月4日 下午1:57:56
 * 跟踪单号: <跟踪单号>
 * 修改单号: <修改单号>
 * 修改内容: <修改内容>
 */
package pers.linhai.nature.esmapper.core.processor;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesRequestBuilder;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesResponse;
import org.elasticsearch.action.admin.indices.alias.exists.AliasesExistRequestBuilder;
import org.elasticsearch.action.admin.indices.alias.exists.AliasesExistResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.exists.types.TypesExistsResponse;
import org.elasticsearch.action.admin.indices.flush.FlushResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.admin.indices.refresh.RefreshRequest;
import org.elasticsearch.action.admin.indices.refresh.RefreshResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.client.Requests;
import org.elasticsearch.cluster.metadata.AliasMetaData;
import org.elasticsearch.cluster.metadata.IndexMetaData;

import com.carrotsearch.hppc.cursors.ObjectObjectCursor;

import pers.linhai.nature.esmapper.exception.IndexConfigurationException;
import pers.linhai.nature.esmapper.interfaces.TypeAccessorInitialization;
import pers.linhai.nature.esmapper.model.core.RolloverIndexInfo;
import pers.linhai.nature.esmapper.model.index.Index;
import pers.linhai.nature.esmapper.model.type.MappingConfiguration;
import pers.linhai.nature.esmapper.model.type.Type;

/**
 * @author: shinelon
 * @date: 2017年3月4日 下午1:57:56
 *
 * @ClassName: 	[TransportClientHandler]
 * @version: [版本号]
 * @since: [产品/模块版本]
 */
public abstract class IndicesAdminClientProcessor
{
    
    /**
     * 实例化一个TypeHandler对象
     * @param isCreateMapping 
     * @param tc
     * @return TypeHandler
     */
    public static IndicesAdminClientProcessor newInstance(Index index, TypeAccessorInitialization typeAccessorInitialization)
    {
        return new IndicesAdminClientProcessorImpl(index, typeAccessorInitialization);
    }
    
    /**
     * 删除索引
     * void
     */
    public abstract void delete();
    
    /**
     * 删除指定滚动索引
     */
    public abstract void delete(String indexName);
    
    /**
     * 判断索引库是否存在
     * @return boolean
     */
    public abstract boolean exists();
    
    /**
     * 刷出缓冲区
     * void
     */
    public abstract void flush();
    
    /**
     * 刷新索引
     * void
     */
    public abstract void refresh();
    
    /**
     * 返回 index
     *
     * @return index
     */
    public abstract Index getIndex();
    
    /**
     * 返回 transportClientProcessorMap
     *
     * @return transportClientProcessorMap
     */
    public abstract Map<String, TransportClientProcessor> getTransportClientProcessorMap();

    /**
     * 返回 writingIndexName
     *
     * @return writingIndexName
     */
    public abstract String getWritingIndexName();

    /**
     * 返回 indices
     *
     * @return indices
     */
    public abstract String[] getIndices();
    
    /**
     * 创建滚动索引
     * @throws IOException void
     */
    protected abstract void createRolloverIndex() throws IOException;
    
    /**
     * 修改分索引指向
     * @param deletedIndexName
     * @throws IOException void
     */
    protected abstract void updateTransportClientProcessorIndexPointer(String deletedIndexName) throws IOException;
    
    /**
     * IndicesAdminClientProcessor的具体实现
     * @author  shinelon
     * @version  V100R001C00
     */
    private static class IndicesAdminClientProcessorImpl extends IndicesAdminClientProcessor
    {
        
        private static final java.util.logging.Logger LOG = java.util.logging.Logger.getLogger(IndicesAdminClientProcessorImpl.class.getName());
        
        private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        
        private static final IndicesAdminClient indicesAdminClient = ClusterNodeAccessor.instance().getIndicesAdminClient();
        
        private Map<String, TransportClientProcessor> transportClientProcessorMap = new HashMap<String,TransportClientProcessor>();
        
        /**
         *  索引库对象
         */
        private final Index index;
        
        /**
         * 当前正在写入的索引，指向索引别名
         */
        private String writingIndexName;
        
        /**
         * 所有的分索引集合
         */
        private String[] indices;
        
        /**
         * <构造函数>
         * @author: shinelon
         * @param isCreateTypeAccessor 
         * @param typeAccessorMap 
         * @date: 2017年3月4日 下午3:04:06 
         *
         * @param index
         */
        private IndicesAdminClientProcessorImpl(Index index, TypeAccessorInitialization typeAccessorInitialization)
        {
            try
            {
                this.index = index;
                
                if (this.index.getRolloverStrategy().isEnabled())
                {
                    if (!RolloverIndexMonitor.isStartup())
                    {
                        //启动滚动索引监视器
                        RolloverIndexMonitor.startup();
                    }
                    
                    //添加到滚动索引索引监视器中
                    RolloverIndexMonitor.add(this);
                    
                    //设置索引名
                    setIndexNames(null);
                }
                else
                {
                    // 如果索引库不存在，则创建索引库
                    this.create(this.index.getName());
                    
                    // 创建索引库别名
                    this.createAlias(this.index.getName(), this.index.getAlias());
                    
                    // 初始化该索引库下面的type 映射，即各个mapping
                    this.createMapping(this.index.getName());
                    
                    writingIndexName = this.index.getAlias();
                    
                    indices = new String[]{this.index.getAlias()};
                }
                
                // TypeAccessor 初始化方案，抽象接口，主要用于为spring集成（将实例交给spring容器托管）完美对接
                typeAccessorInitialization.initialize(this);
            }
            catch (Throwable e)
            {
                throw new IndexConfigurationException("Index inited failed: " + index, e);
            }
        }

        /** 
         * 获取滚动索引列表
         *
         * @return List<IndexMetaInfo>
         */
        private List<RolloverIndexInfo> getRolloverIndexInfoList()
        {
            List<IndexMetaData> indexMetaDataList = ClusterAdminClientProcessor.instance().getIndexMetaDataList();
            List<RolloverIndexInfo> rolloverIndexInfoList = new ArrayList<RolloverIndexInfo>();
            for (IndexMetaData indexMetaData : indexMetaDataList)
            {
                if (indexMetaData.getIndex().getName().startsWith(this.index.getName()))
                {
                    RolloverIndexInfo imi = new RolloverIndexInfo();
                    imi.setCreationDate(indexMetaData.getCreationDate());
                    imi.setIndexName(indexMetaData.getIndex().getName());
                    
                    //设置别名
                    Iterator<ObjectObjectCursor<String, AliasMetaData>> aliasMetaDataIterator = indexMetaData.getAliases().iterator();
                    while (aliasMetaDataIterator.hasNext())
                    {
                        ObjectObjectCursor<String, AliasMetaData> objectObjectCursor = aliasMetaDataIterator.next();
                        imi.setIndexAliasName(objectObjectCursor.value.getAlias());
                        break;
                    }
                    rolloverIndexInfoList.add(imi);
                }
            }
            
            if (!rolloverIndexInfoList.isEmpty())
            {
                // 升序排列
                Collections.sort(rolloverIndexInfoList);
            }
            
            return rolloverIndexInfoList;
        }
        
        /**
         * 判断索引库是否存在
         * @return boolean
         */
        public boolean exists()
        {
            return exists(index.getName());
        }
        
        /**
         * 刷出缓冲区
         * void
         */
        public void flush()
        {
            FlushResponse fr = indicesAdminClient.prepareFlush(index.getName()).execute().actionGet();
            if (fr.getShardFailures().length > 0)
            {
                LogManager.getRootLogger().error("[index=" + index.getName() + "] flush failed, ShardFailures: " + fr.getShardFailures().length);
            }
        }
        
        /**
         * 刷新索引
         * void
         */
        public void refresh()
        {
            RefreshResponse rr = indicesAdminClient.refresh(new RefreshRequest(index.getName())).actionGet();
            if (rr.getShardFailures().length > 0)
            {
                LogManager.getRootLogger().error("[index=" + index.getName() + "] refresh failed, ShardFailures: " + rr.getShardFailures().length);
            }
        }
        
        /**
         * 删除默认索引
         */
        public void delete()
        {
            delete(index.getName());
        }
        
        /**
         * 删除指定滚动索引
         */
        public void delete(String indexName)
        {
            DeleteIndexResponse dResponse = indicesAdminClient.prepareDelete(indexName).execute().actionGet();
            if (dResponse.isAcknowledged())
            {
                LogManager.getRootLogger().info("delete index " + indexName + "  successfully!");
            }
            else
            {
                LogManager.getRootLogger().error("Fail to delete index : " + indexName);
            }
        }
        
        /**
         * 判断指定索引是否存在
         * @param indexName
         * @return boolean
         */
        protected boolean exists(String indexName)
        {
            // 判断索引库是否存在
            IndicesExistsResponse indicesExistsResponse = indicesAdminClient.exists(new IndicesExistsRequest(indexName)).actionGet();
            
            // 如果索引库不存在，则创建索引库
            return indicesExistsResponse.isExists();
        }
        
        /**
         * 创建索引
         * @param indexName
         * @throws IOException void
         */
        protected void create(String indexName) throws IOException
        {
            // 如果索引库不存在，则创建索引库
            if (!exists(indexName))
            {
                // 执行索引库创建
                AcknowledgedResponse response = indicesAdminClient.create(index.getCreateIndexRequest(indexName)).actionGet();
                
                if (!response.isAcknowledged())
                {
                    throw new IndexConfigurationException("Index create failed, " + index);
                }
                
                LOG.info("The index[" + indexName + "] created successfully.");
            }
        }
        
        /**
         * 创建索引别名
         * @param indexName String
         * @param aliasName String
         */
        protected void createAlias(String indexName, String aliasName)
        {
            AliasesExistRequestBuilder builder = indicesAdminClient.prepareAliasesExist(aliasName);
            AliasesExistResponse response = builder.execute().actionGet();
            if (!response.isExists())
            {
                IndicesAliasesRequestBuilder b = indicesAdminClient.prepareAliases();
                b.addAlias(indexName, aliasName);
                IndicesAliasesResponse r = b.execute().actionGet();
                if (!r.isAcknowledged())
                {
                    throw new IndexConfigurationException("Index Alias name create failed, " + aliasName);
                }
                
                LOG.info("The index-alias[" + aliasName + "] created successfully.");
            }
        }
        
        /**
         * 创建该索引库下的map映射表
         * @param indexName void
         */
        protected void createMapping(String indexName)
        {
            // 初始化该索引库下面的type 映射，即各个mapping
            for (MappingConfiguration< ? extends Type> tec : this.index.getMappingConfigurationlist())
            {
                // 创建type实体对应的es mapping信息
                TypesExistsResponse typesExistsResponse = indicesAdminClient.prepareTypesExists(indexName).setTypes(tec.typeName()).get();
                if (!typesExistsResponse.isExists())
                {
                    PutMappingRequest mappingRequest = Requests.putMappingRequest(indexName).type(tec.typeName()).source(tec.getMappingStructure());
                    AcknowledgedResponse response = indicesAdminClient.putMapping(mappingRequest).actionGet();
                    if (!response.isAcknowledged())
                    {
                        throw new IndexConfigurationException("The index-mapping[" + index.getName() + "-" + tec.typeName() + "] create failed, " + index);
                    }
                    
                    LOG.info("The index-mapping[" + indexName + "-" + tec.typeName() + "] created successfully.");
                }
            }
        }
        
        /**
         * 返回 index
         *
         * @return index
         */
        public Index getIndex()
        {
            return index;
        }
        
        /**
         * 创建滚动索引
         * @throws IOException void
         */
        protected void createRolloverIndex() throws IOException
        {
            String date = dateFormat.format(new Date());
            
            // 索引真身名
            String indexName = this.index.getName() + "_" + date;
            
            // 索引别名
            String indexAliasName = this.index.getAlias() + "_" + date;
            
            // 如果索引库不存在，则创建索引库
            this.create(indexName);
            
            // 创建索引库别名
            this.createAlias(indexName, indexAliasName);
            
            // 初始化该索引库下面的type 映射，即各个mapping
            this.createMapping(indexName);
            
            writingIndexName = indexAliasName;
            indices = new String[]{writingIndexName};
        }
        
        protected void updateTransportClientProcessorIndexPointer(String deletedIndexName) throws IOException
        {
            //设置索引名
            setIndexNames(deletedIndexName);
            
            for (Entry<String, TransportClientProcessor> e : transportClientProcessorMap.entrySet())
            {
                e.getValue().setNewWriteableIndex(writingIndexName);
                e.getValue().setNewSearchableIndices(indices);
            }
        }
        
        /**
         * 返回 transportClientProcessorMap
         *
         * @return transportClientProcessorMap
         */
        public Map<String, TransportClientProcessor> getTransportClientProcessorMap()
        {
            return transportClientProcessorMap;
        }

        /**
         * 返回 writingIndexName
         *
         * @return writingIndexName
         */
        public String getWritingIndexName()
        {
            return writingIndexName;
        }

        /**
         * 返回 indices
         *
         * @return indices
         */
        public String[] getIndices()
        {
            return indices;
        }

        /** 
         * 设置索引名
         * @param deletedIndexName
         * @throws IOException void
         */
        private void setIndexNames(String deletedIndexName) throws IOException
        {
            //获取滚动索引列表
            List<RolloverIndexInfo> rolloverIndexInfoList = getRolloverIndexInfoList();
            
            if (rolloverIndexInfoList.isEmpty())
            {
                // 创建滚动索引
                this.createRolloverIndex();
            }
            else
            {
                // 取最后一个索引，它是最新/最近一次创建的。
                writingIndexName = rolloverIndexInfoList.get(rolloverIndexInfoList.size() - 1).getIndexAliasName();
                List<String> indexNameList = new ArrayList<String>();
                for (int i = 0; i < rolloverIndexInfoList.size(); i++)
                {
                    String indexAliasName = rolloverIndexInfoList.get(i).getIndexAliasName();
                    if(deletedIndexName != null)
                    {
                        if (!rolloverIndexInfoList.get(i).getIndexName().equals(deletedIndexName))
                        {
                            indexNameList.add(indexAliasName);
                        }
                    }
                    else
                    {
                        indexNameList.add(indexAliasName);
                    }
                }
                indices = indexNameList.toArray(new String[0]);
            }
        }
    }
}
