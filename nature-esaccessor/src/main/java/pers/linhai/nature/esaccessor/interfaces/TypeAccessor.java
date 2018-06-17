/*
 * 项 目 名:  Storage Tool Service V100R001C00
 * 文 件 名:  pers.linhai.esdao.interfaces.DAO.java
 * 版       权:  XXX Technologies Co., Ltd. Copyright 2017,  All rights reserved.
 * 描       述:  XXX PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * 修 改 人:  shinelon
 * 修改时间:  2017年4月17日
 * 修改内容:  创建
 */
package pers.linhai.nature.esaccessor.interfaces;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;

import pers.linhai.nature.esaccessor.model.bulk.BulkProcessorConfiguration;
import pers.linhai.nature.esaccessor.model.type.Type;

/**
 * 抽象数据放武器
 * 实质为DAO对象
 * @author  shinelon
 * @version  V100R001C00
 */
public interface TypeAccessor<T extends Type>
{
    
    /**
     * 返回 dataConverter
     * @return dataConverter
     */
    DataConverter<T> getDataConverter();
    
    /**
     * 新起一个批量操作对象
     * @param bulkProcessorConfiguration
     * @return BatchOperation<T>
     */
    BulkOperation<T> bulkOperations(BulkProcessorConfiguration bulkProcessorConfiguration);
    
    /**
     * 以默认配置新起一个批量操作对象
     * @return BatchOperation<T>
     */
    BulkOperation<T> bulkOperations();
    
    /**
     * 保存pojo实体对象
     * @param t void
     */
    void save(T t);
    
    /**
     * 根据ID获取实体记录
     * @param id
     * @return T
     */
    T get(String id);
    
    /**
     * 修改单个记录
     *
     * @param t void
     */
    void update(T t);
    
    /**
     * 删除实体 TODO
     * void
     */
    void delete(String id);
    
    /**
     * 根据查询条件批量删除
     *
     * @param qb void
     */
    long deleteByQuery(QueryBuilder qb);
    
    /**
     * 根据查询条件批量删除
     *
     * @param qb
     * @param index
     */
    long deleteByQuery(QueryBuilder qb, String index);
    
    /**
     * 根据查询条件查询doc文档数
     * @param qb
     * @return long
     */
    long hitCount(QueryBuilder qb);
    
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
    HitCollection<T> query(QueryBuilder queryBuilder, HighlightBuilder hb, int start, int howMany);
    
    /**
     * 分页查询
     * @param queryBuilder
     * @param start
     * @param howMany
     * @return HitCollection<T>
     */
    HitCollection<T> query(QueryBuilder queryBuilder, int start, int howMany);
    
    /**
     * 分页查询，查询指定分索引的记录
     * @param queryBuilder
     * @param index
     * @param start
     * @param howMany
     * @return HitCollection<T>
     */
    HitCollection<T> query(QueryBuilder queryBuilder, String index, int start, int howMany);
    
    /**
     * 获取当前正在写的索引库名
     * @return String
     */
    String indexName();
    
    /**
     * 获取实体type的名字
     *
     * @return String
     */
    String typeName();
}
