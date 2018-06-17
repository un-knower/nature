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
package pers.linhai.nature.esaccessor.core.processor;

import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkProcessor.Listener;
import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.get.GetRequestBuilder;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.update.UpdateRequestBuilder;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.index.reindex.DeleteByQueryRequestBuilder;

import pers.linhai.nature.esaccessor.core.BulkProcessorListener;
import pers.linhai.nature.esaccessor.model.bulk.BulkProcessorConfiguration;

/**
 * The elastic-search-TransportClient's interfaces package
 * @author: shinelon
 * @date: 2017年3月4日 下午1:57:56
 *
 * @ClassName: 	[TransportClientHandler]
 * @version: [版本号]
 * @since: [产品/模块版本]
 */
public abstract class TransportClientProcessor
{
    /**
     * 实例化一个TransportClientProcessor对象
     * @param index
     * @param indices
     * @param type
     * @return TransportClientProcessor
     */
    public static TransportClientProcessor newInstance(String index, String[] indices, String type)
    {
        return new TransportClientProcessorImpl(index, indices, type);
    }
    
    /**
     * 索引
     * @return String
     */
    public abstract String index();
    
    /**
     * type表
     * @return String
     */
    public abstract String type();
    
    public abstract IndexRequestBuilder getIndexRequestBuilder();
    
    public abstract UpdateRequestBuilder getUpdateRequestBuilder(String id);
    
    public abstract GetRequestBuilder getGetRequestBuilder(String id);

    public abstract SearchRequestBuilder getSearchRequestBuilder();
    
    public abstract SearchRequestBuilder getSearchRequestBuilder(String... indices);
    
    /**
     * The delete API allows one to delete a typed JSON document from a specific index based on its id
     * @param id
     * @return DeleteRequestBuilder
     */
    public abstract DeleteRequestBuilder getDeleteRequestBuilder(String id);
    
    /**
     * 根据查询条件进行删除,返回DeleteByQueryRequestBuilder
     * @return DeleteByQueryRequestBuilder
     */
    public abstract DeleteByQueryRequestBuilder getDeleteByQueryRequestBuilder();
    
    public abstract DeleteByQueryRequestBuilder getDeleteByQueryRequestBuilder(String... indices);
    
    /**
     * 新起一个批量处理器
     * @param bulkProcessorConfiguration
     * @return BulkProcessor
     */
    public abstract BulkProcessor newBulkProcessor(BulkProcessorConfiguration bulkProcessorConfiguration);
    
    
    protected abstract void setNewWriteableIndex(String newWritableIndex);
    
    /**
     * @param indices String[]
     */
    protected abstract void setNewSearchableIndices(String[] indices);
    
    /**
     * TypeHandler的具体实现
     * @author  shinelon
     * @version  V100R001C00
     */
    private static class TransportClientProcessorImpl extends TransportClientProcessor
    {
        
        private static final TransportClient client = ClusterNodeAccessor.instance().getTransportClient();
        
        /**
         * index name
         */
        private String index;
        
        private String[] indices;
        
        /**
         * type name
         */
        private String type;
        
        /**
         * 批量处理操作的监听器
         */
        private BulkProcessorListener defaultListener = new BulkProcessorListener();
        
        /**
         * <构造函数>
         * @author: shinelon
         * @date: 2017年3月4日 下午3:04:06 
         *
         * @param tc
         */
        private TransportClientProcessorImpl(String index, String[] indices, String type)
        {
            //索引库名
            this.index = index;
            this.indices = indices;
            this.type = type;
            defaultListener.setIndex(index);
            defaultListener.setType(type);
        }
        
        /**
         * 新起一个批量处理器
         * @param bulkProcessorConfiguration
         * @return BulkProcessor
         */
        public BulkProcessor newBulkProcessor(BulkProcessorConfiguration bulkProcessorConfiguration)
        {
            Listener listener = bulkProcessorConfiguration.getListener() == null ? defaultListener : bulkProcessorConfiguration.getListener();
            BulkProcessor.Builder builder = BulkProcessor.builder(client, listener);
            builder.setBulkActions(bulkProcessorConfiguration.getBulkActions());
            builder.setBulkSize(bulkProcessorConfiguration.getBulkSize());
            builder.setConcurrentRequests(bulkProcessorConfiguration.getConcurrentRequests());
            builder.setFlushInterval(bulkProcessorConfiguration.getFlushInterval());
            return builder.build();
        }
        
        public IndexRequestBuilder getIndexRequestBuilder()
        {
            return client.prepareIndex(index, type);
        }
        
        public UpdateRequestBuilder getUpdateRequestBuilder(String id)
        {
            return client.prepareUpdate(index, type, id);
        }
        
        public GetRequestBuilder getGetRequestBuilder(String id)
        {
            return client.prepareGet(index, type, id);
        }

        /**
         * The delete API allows one to delete a typed JSON document from a specific index based on its id
         * @param id
         * @return DeleteRequestBuilder
         */
        public DeleteRequestBuilder getDeleteRequestBuilder(String id)
        {
            return client.prepareDelete(index, type, id);
        }
        
        public SearchRequestBuilder getSearchRequestBuilder()
        {
            return getSearchRequestBuilder(indices);
        }
        
        public SearchRequestBuilder getSearchRequestBuilder(String... indices)
        {
            return client.prepareSearch(indices).setTypes(type);
        }
        
        public DeleteByQueryRequestBuilder getDeleteByQueryRequestBuilder()
        {
            return getDeleteByQueryRequestBuilder(indices);
        }
        
        public DeleteByQueryRequestBuilder getDeleteByQueryRequestBuilder(String... indices)
        {
            return DeleteByQueryAction.INSTANCE.newRequestBuilder(client).source(indices);
        }

        /**
         * 设置可写入的单索引，只能写入一个索引
         * @param newWriteableIndex String
         */
        protected void setNewWriteableIndex(String newWriteableIndex)
        {
            this.index = newWriteableIndex;
            defaultListener.setIndex(index);
        }
        
        /**
         * 设置可搜索的多索引
         * @param indices String[]
         */
        protected void setNewSearchableIndices(String[] indices)
        {
            this.indices = indices;
        }

        /**
         * 返回 index
         *
         * @return index
         */
        public String index()
        {
            return index;
        }

        /**
         * 返回 type
         *
         * @return type
         */
        public String type()
        {
            return type;
        }
    }
}
