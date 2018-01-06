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

import java.util.Map;

import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;

import pers.linhai.nature.indexaccess.interfaces.DataConverter;
import pers.linhai.nature.indexaccess.model.type.Type;

/**
 * 高亮搜索结果集合实现
 * @author  shinelon
 * @version  V100R001C00
 */
class HighLightedHitCollectionImpl<T extends Type> extends HitCollectionImpl<T>
{
    
    /**
     * <默认构造函数>
     * @param searchHits
     * @param dataConverter
     */
    HighLightedHitCollectionImpl(SearchHits searchHits, DataConverter<T> dataConverter)
    {
        super(searchHits, dataConverter);
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
     * 返回指定索引下标的实体记录
     * @param i
     * @return T
     */
    public T get(int i)
    {
        SearchHit searchHit = this.searchHits.getAt(i);
        return transfer(searchHit);
    }
    
    
    private T transfer(SearchHit searchHit)
    {
        Map<String, HighlightField> highlightFieldMap = searchHit.getHighlightFields();
        T t = dataConverter.convert(searchHit.getSourceAsMap(), highlightFieldMap);
        setCommon(searchHit, t);
        return t;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
