/*
 * 项 目 名:  Storage Tool Service V100R001C00
 * 文 件 名:  pers.linhai.esdao.core.TypeDAO.java
 * 版       权:  XXX Technologies Co., Ltd. Copyright 2017,  All rights reserved.
 * 描       述:  XXX PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * 修 改 人:  shinelon
 * 修改时间:  2017年4月17日
 * 修改内容:  创建
 */
package pers.linhai.nature.esmapper.core.impls;

import org.apache.logging.log4j.LogManager;
import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequestBuilder;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequestBuilder;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequestBuilder;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;

import pers.linhai.nature.esmapper.core.BulkProcessorListener;
import pers.linhai.nature.esmapper.core.MapperFactory;
import pers.linhai.nature.esmapper.core.processor.TransportClientProcessor;
import pers.linhai.nature.esmapper.exception.TypeAccessorException;
import pers.linhai.nature.esmapper.interfaces.BulkOperation;
import pers.linhai.nature.esmapper.interfaces.DataConverter;
import pers.linhai.nature.esmapper.interfaces.HitCollection;
import pers.linhai.nature.esmapper.interfaces.TypeMapper;
import pers.linhai.nature.esmapper.model.bulk.BulkProcessorConfiguration;
import pers.linhai.nature.esmapper.model.type.MappingConfiguration;
import pers.linhai.nature.esmapper.model.type.Type;

/**
 * DAO实现
 * @author  shinelon
 * @version  V100R001C00
 */
public class TypeMapperImpl<T extends Type> implements TypeMapper<T>
{
    
    private static final java.util.logging.Logger LOG = java.util.logging.Logger.getLogger(TypeMapperImpl.class.getName());
    
    /**
     * ElasticSearch Type实体操作对象
     */
    private TransportClientProcessor transportClientProcessor;
    
    /**
     * 将实体转换成XContentBuilder对象，es能直接使用该对象
     */
    private DataConverter<T> dataConverter;
    
    /** 
     * <构造函数>
     * @param transportClientProcessor2 
     *
     * @param dataTypeMap
     * @param typeEntityConstructor
     * @param transportClientProcessor
     */
    public TypeMapperImpl(MappingConfiguration<T> tec, TransportClientProcessor transportClientProcessor)
    {
        try
        {
            // 初始化EsPojoDAO
            long l_start = System.currentTimeMillis();
            
            //数据entity的构造器，以便用于查询的时候，直接返回该类型的数据
            this.dataConverter = new DataConverterImpl<T>(tec.getDataTypeCollection(), tec.getTypeClass().getConstructor());
            
            this.transportClientProcessor = transportClientProcessor;
            
            MapperFactory.add(tec.getTypeClass(), this);
            
            // 控制台打印初始化完成信息
            LOG.info("TypeAccessor<".concat(tec.getTypeClass().getSimpleName()).concat("> Initialized successfully in ").concat(String.valueOf(System.currentTimeMillis() - l_start)).concat(" ms !"));
        }
        catch (Throwable e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * 返回 dataConverter
     *
     * @return dataConverter
     */
    public DataConverter<T> getDataConverter()
    {
        return dataConverter;
    }

    /**
     * 以给定的配置，新起一个批量操作对象
     * @param bulkProcessorConfiguration
     * @return
     */
    public BulkOperation<T> bulkOperations(BulkProcessorConfiguration bulkProcessorConfiguration)
    {
        return new BulkOperationImpl<T>(transportClientProcessor.newBulkProcessor(bulkProcessorConfiguration)
                , dataConverter, transportClientProcessor.index(), transportClientProcessor.type());
    }
    
    /**
     * 以默认配置，新起一个批量操作对象
     * @return
     */
    public BulkOperation<T> bulkOperations()
    {
        BulkProcessorConfiguration bulkProcessorConfiguration = new BulkProcessorConfiguration();
        BulkProcessorListener bulkProcessorListener = new BulkProcessorListener();
        bulkProcessorListener.setIndex(this.indexName());
        bulkProcessorListener.setType(this.typeName());
        bulkProcessorConfiguration.setListener(bulkProcessorListener);
        return bulkOperations(bulkProcessorConfiguration);
    }
    
    /**
     * 保存pojo实体对象
     * @param t void
     */
    public void save(T t)
    {
        try
        {
            IndexRequestBuilder indexRequestBuilder = transportClientProcessor.getIndexRequestBuilder();
            indexRequestBuilder.setSource(dataConverter.convert(t));
            IndexResponse response = indexRequestBuilder.get();
            if (response == null || response.status() != RestStatus.CREATED)
            {
                throw new TypeAccessorException("Record[" + t + "] save failed, responseInfo: " + response);
            }
            
            //设置响应的id和version到原始记录中
            t.setId(response.getId());
            t.setVersion(response.getVersion());
        }
        catch (Throwable e)
        {
            LogManager.getRootLogger().error("[" + transportClientProcessor.type() + "]Save doc failed, entity: " + t, e);
            throw new TypeAccessorException("TypeDao.save:", e);
        }
    }
    
    /**
     * 根据ID获取实体记录
     * @param id
     * @return T
     */
    public T get(String id)
    {
        GetRequestBuilder getRequestBuilder = transportClientProcessor.getGetRequestBuilder(id);
        GetResponse response = getRequestBuilder.get();
        if(response == null || !response.isExists())
        {
            return null;
        }
        
        T t = dataConverter.convert(response.getSource());
        
        t.setId(response.getId());
        t.setVersion(response.getVersion());
        return t;
    }
    
    /**
     * 修改单个记录
     *
     * @param t void
     */
    public void update(T t)
    {
        try
        {
            //获取一个修改请求对象
            UpdateRequestBuilder request = transportClientProcessor.getUpdateRequestBuilder(t.getId());
            request.setDoc(dataConverter.convert(t));
            
            //新增版本号，这里可以解决并发修改的问题
            if(t.getVersion() != -1)
            {
                request.setVersion(t.getVersion());
            }
            
            UpdateResponse response = request.get();
            if (response == null || response.status() != RestStatus.CREATED)
            {
                throw new TypeAccessorException("Record[" + t + "] update failed, responseInfo: " + response);
            }
            
            //将响应的信息映射到给定的实体对象成员中
            dataConverter.mapping(t, response.getGetResult().getSource());
            
            //设置响应的id和version到原始记录中
            t.setId(response.getId());
            t.setVersion(response.getVersion());
        }
        catch (Throwable e)
        {
            LogManager.getRootLogger().error("[" + transportClientProcessor.type() + "]Update doc failed, entity: " + t, e);
            throw new TypeAccessorException("TypeDao.update:", e);
        }
    }
    
    /**
     * 删除实体 TODO
     * void
     */
    public void delete(String id)
    {
        DeleteRequestBuilder drq = transportClientProcessor.getDeleteRequestBuilder(id);
        DeleteResponse dr = drq.get();
        if(dr != null && dr.status() != RestStatus.FOUND)
        {
            LogManager.getLogger().error("Delete doc failed, docID: " + id);
            throw new TypeAccessorException("Record[id=" + id + "] deleted failed, responseInfo: " + dr);
        }
        
        LogManager.getRootLogger().info("[" + transportClientProcessor.type() + "]Delete doc succeed, docID: " + id);
    }
    
    /**
     * 根据查询条件批量删除
     *
     * @param qb void
     */
    public long deleteByQuery(QueryBuilder qb)
    {
        DeleteByQueryRequestBuilder dbqr = transportClientProcessor.getDeleteByQueryRequestBuilder();
        
        BoolQueryBuilder bqb = QueryBuilders.boolQuery();
        bqb.must(qb);
        bqb.must(QueryBuilders.typeQuery(typeName()));
        BulkByScrollResponse response = dbqr.filter(bqb).get();
        
        return response.getDeleted();
    }
    
    /**
     * 根据查询条件批量删除
     *
     * @param qb void
     */
    public long deleteByQuery(QueryBuilder qb, String index)
    {
        DeleteByQueryRequestBuilder dbqr = transportClientProcessor.getDeleteByQueryRequestBuilder(index);
        
        BoolQueryBuilder bqb = QueryBuilders.boolQuery();
        bqb.must(qb);
        bqb.must(QueryBuilders.typeQuery(typeName()));
        BulkByScrollResponse response = dbqr.filter(bqb).get();
        
        return response.getDeleted();
    }
    
    /**
     * 查询记录数
     *
     * @param qb
     * @return
     */
    public long hitCount(QueryBuilder qb)
    {
        SearchRequestBuilder searchRequestBuilder = transportClientProcessor.getSearchRequestBuilder();
        SearchResponse response = searchRequestBuilder.setQuery(qb).setFrom(0).setSize(0).get();
        return response.getHits().getTotalHits();
    }
    
    /**
     * 高亮查询
     * <p>Title         : query lilinhai 2018年1月6日 下午1:35:41</p>
     * @param queryBuilder
     * @param hb
     * @param start
     * @param howMany
     * @return 
     * HitCollection<T>
     */
    public HitCollection<T> query(QueryBuilder queryBuilder, HighlightBuilder hb, int start, int howMany)
    {
        SearchRequestBuilder searchRequestBuilder = transportClientProcessor.getSearchRequestBuilder();
        
        // 设置查询类型 
        //1.SearchType.DFS_QUERY_THEN_FETCH = 精确查询
        //2.SearchType.SCAN = 扫描查询,无序
        searchRequestBuilder.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
        
        // 设置是否按查询匹配度排序
        searchRequestBuilder.setExplain(true);
     
        //设置高亮显示
        searchRequestBuilder.highlighter(hb);
        SearchResponse response = searchRequestBuilder.setQuery(queryBuilder).setFrom(start).setSize(howMany).get();
        return new HighLightedHitCollectionImpl<T>(response.getHits(), dataConverter);
    }
    
    /**
     * 分页查询
     * @param queryBuilder
     * @param start
     * @param howMany
     * @return HitCollection<T>
     */
    public HitCollection<T> query(QueryBuilder queryBuilder, int start, int howMany)
    {
        try
        {
            SearchRequestBuilder searchRequestBuilder = transportClientProcessor.getSearchRequestBuilder();
            SearchResponse response = searchRequestBuilder.setQuery(queryBuilder).setFrom(start).setSize(howMany).get();
            return new HitCollectionImpl<T>(response.getHits(), dataConverter);
        }
        catch (Throwable e)
        {
            throw new TypeAccessorException("Error type : query. \n" + e.toString(), e);
        }
    }
    
    /**
     * 分页查询，查询指定分索引的记录
     * @param queryBuilder
     * @param subIndex
     * @param start
     * @param howMany
     * @return HitCollection<T>
     */
    public HitCollection<T> query(QueryBuilder queryBuilder, String subIndex, int start, int howMany)
    {
        try
        {
            SearchRequestBuilder searchRequestBuilder = transportClientProcessor.getSearchRequestBuilder(subIndex);
            SearchResponse response = searchRequestBuilder.setQuery(queryBuilder).setFrom(start).setSize(howMany).get();
            return new HitCollectionImpl<T>(response.getHits(), dataConverter);
        }
        catch (Throwable e)
        {
            throw new TypeAccessorException("Error type : query. \n" + e.toString(), e);
        }
    }
    
    /**
     * 获取当前正在写的索引库名
     * @return String
     */
    public String indexName()
    {
        return transportClientProcessor.index();
    }
    
    /**
     * 获取实体type的名字
     *
     * @return String
     */
    public String typeName()
    {
        return transportClientProcessor.type();
    }
}
