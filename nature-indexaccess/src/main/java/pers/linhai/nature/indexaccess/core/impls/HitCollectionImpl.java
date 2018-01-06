/*
 * 项 目 名:  Storage Tool Service V100R001C00
 * 文 件 名:  pers.linhai.nature.indexaccess.model.core.EntityCollection.java
 * 版       权:  XXX Technologies Co., Ltd. Copyright 2017,  All rights reserved.
 * 描       述:  XXX PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * 修 改 人:  shinelon
 * 修改时间:  2017年7月17日
 * 修改内容:  创建
 */
package pers.linhai.nature.indexaccess.core.impls;

import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

import pers.linhai.nature.indexaccess.interfaces.DataConverter;
import pers.linhai.nature.indexaccess.interfaces.HitCollection;
import pers.linhai.nature.indexaccess.model.type.Type;

/**
 * 搜索结果集合实现
 * @author  shinelon
 * @version  V100R001C00
 */
class HitCollectionImpl<T extends Type> implements HitCollection<T>
{
    
    /**
     * 返回的记录数
     */
    SearchHits searchHits;
    
    /**
     * 实体转换器
     */
    DataConverter<T> dataConverter;
    
    /**
     * <默认构造函数>
     * @param searchHits
     * @param dataConverter
     */
    HitCollectionImpl(SearchHits searchHits, DataConverter<T> dataConverter)
    {
        this.searchHits = searchHits;
        this.dataConverter = dataConverter;
    }
    
    /**
     * 迭代
     * @param ec void
     */
    public void each(Consumer<T> ec)
    {
        SearchHit[] searchHits = this.searchHits.getHits();
        for (SearchHit searchHit : searchHits)
        {
            ec.consume(transfer(searchHit));
        }
    }
    
    /**
     * 返回命中的总记录数
     *
     * @return long
     */
    public long total()
    {
        return searchHits.getTotalHits();
    }
    
    /**
     * 返回指定索引下标的实体记录
     * @param i
     * @return T
     */
    public T get(int i)
    {
        return transfer(this.searchHits.getAt(i));
    }
    
    /**
     * 当前返回数据的条数
     * @return int
     */
    public int length()
    {
        return this.searchHits.getHits().length;
    }
    
    T transfer(SearchHit searchHit)
    {
        T t = dataConverter.convert(searchHit.getSourceAsMap());
        setCommon(searchHit, t);
        return t;
    }
    
    void setCommon(SearchHit searchHit, T t)
    {
        t.setVersion(searchHit.getVersion());
        t.setId(searchHit.getId());
        t.setScore(searchHit.getScore());
    }
}
