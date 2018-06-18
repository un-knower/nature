/*
 * 项 目 名:  Storage Tool Service V100R001C00
 * 文 件 名:  pers.linhai.esframework.core.BatchOperation.java
 * 版       权:  XXX Technologies Co., Ltd. Copyright 2017,  All rights reserved.
 * 描       述:  XXX PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * 修 改 人:  shinelon
 * 修改时间:  2017年6月6日
 * 修改内容:  创建
 */
package pers.linhai.nature.esmapper.core.impls;

import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;

import pers.linhai.nature.esmapper.interfaces.BulkOperation;
import pers.linhai.nature.esmapper.interfaces.DataConverter;
import pers.linhai.nature.esmapper.model.type.Type;

/**
 * 批量操作
 * 
 * @author  shinelon
 * @version  V100R001C00
 */
class BulkOperationImpl<T extends Type> implements BulkOperation<T>
{
    /**
     * index name
     */
    private String index;
    
    /**
     * type name
     */
    private String type;
    
    /**
     * 批量处理器
     */
    private BulkProcessor bulkProcessor;
    
    /**
     * 将实体转换成XContentBuilder对象，es能直接使用该对象
     */
    private DataConverter<T> dataConverter;
    
    private boolean isClosed;
    
    /**
     * <默认构造函数>
     * @param index
     * @param type
     */
    BulkOperationImpl(BulkProcessor bulkProcessor, DataConverter<T> dataConverter, String index, String type)
    {
        this.bulkProcessor = bulkProcessor;
        this.dataConverter = dataConverter;
        this.index = index;
        this.type = type;
    }
    
    /**
     * 添加一个实体
     * @param t void
     */
    public void add(T t)
    {
        IndexRequest indexRequest = new IndexRequest(index, type);
        indexRequest.source(dataConverter.convert(t));
        bulkProcessor.add(indexRequest);
    }
    
    /**
     * 删除单个记录
     * @param id void
     */
    public void delete(String id)
    {
        DeleteRequest deleteRequest = new DeleteRequest(index, type, id);
        bulkProcessor.add(deleteRequest);
    }
    
    /**
     * 返回 isClosed
     *
     * @return isClosed
     */
    public boolean isClosed()
    {
        return isClosed;
    }
    
    /**
     * 结束批量操作
     * void
     */
    public void flush()
    {
        bulkProcessor.flush();
    }

    /**
     * 结束批量操作
     * void
     */
    public void close()
    {
        isClosed = true;
        bulkProcessor.close();
    }

    /**
     * 返回 index
     *
     * @return index
     */
    public String getIndex()
    {
        return index;
    }
}
