/*
 * 项 目 名:  Storage Tool Service V100R001C00
 * 文 件 名:  pers.linhai.esframework.annotation.datatypes.KeywordField.java
 * 版       权:  XXX Technologies Co., Ltd. Copyright 2017,  All rights reserved.
 * 描       述:  XXX PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * 修 改 人:  shinelon
 * 修改时间:  2017年6月21日
 * 修改内容:  创建
 */
package pers.linhai.nature.esaccessor.annotation.datatypes;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import pers.linhai.nature.esaccessor.model.enumer.DocValues;
import pers.linhai.nature.esaccessor.model.enumer.EagerGlobalOrdinals;
import pers.linhai.nature.esaccessor.model.enumer.IncludeInAll;
import pers.linhai.nature.esaccessor.model.enumer.Index;
import pers.linhai.nature.esaccessor.model.enumer.IndexOptions;
import pers.linhai.nature.esaccessor.model.enumer.Norms;
import pers.linhai.nature.esaccessor.model.enumer.Similarity;
import pers.linhai.nature.esaccessor.model.enumer.Store;

/**
 * 
 * 
 * @author  shinelon
 * @version  V100R001C00
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface KeywordField
{
    
    /**
     * Mapping field-level query time boosting. Accepts a floating point number, defaults to 1.0. 
     *
     * @return float
     */
    float boost() default 1.0f;
    
    /**
     * Should the field be stored on disk in a column-stride fashion, so that it can later be used for sorting, aggregations, or scripting? Accepts true (default) or false.
     * @return DocValues
     */
    DocValues docValues() default DocValues.DEFAULT;
    
    /**
     * <pre>
     * Should global ordinals be loaded eagerly on refresh? Accepts true or false (default). 
     * Enabling this is a good idea on fields that are frequently used for (significant) terms aggregations.
     * </pre>
     * @return boolean
     */
    EagerGlobalOrdinals eagerGlobalOrdinals() default EagerGlobalOrdinals.DEFAULT;
    
    /**
     * Do not index any string longer than this value. Defaults to 2147483647 so that all values would be accepted.
     * @return int
     */
    int ignoreAbove() default Integer.MAX_VALUE;
    
    /**
     * <pre>
     * Whether or not the field value should be included in the _all field? Accepts true or false. 
     * Defaults to false if index is set to no, or if a parent object field sets include_in_all to false. Otherwise defaults to true. 
     * </pre>
     * @return boolean
     */
    IncludeInAll includeInAll() default IncludeInAll.DEFAULT;
    
    /**
     * <pre>
     * <b>Description</b> 
     * 
     *      The index option controls whether field values are indexed. 
     *      It accepts true or false. 
     *      Fields that are not indexed are not queryable.
     * 
     * </pre>
     * 
     * @author: shinelon
     * @date: 2017年3月5日 下午5:25:32
     * @Title: index
     *         
     * @return Index
     */
    Index index() default Index.DEFAULT;
    
    
    /**
     * The index_options parameter controls what information is added to the inverted index, for search and highlighting purposes.
     *
     * @return IndexOptions
     */
    IndexOptions indexOptions() default IndexOptions.DEFAULT;
    
    /**
     * Whether field-length should be taken into account when scoring queries. Accepts true (default) or false. 
     * @return boolean
     */
    Norms norms() default Norms.DEFAULT;
    
    /**
     * Accepts a string value which is substituted for any explicit null values. Defaults to null, which means the field is treated as missing. 
     *
     * @return String
     */
    String nullValue() default "null";
    
    /**
     * Whether the field value should be stored and retrievable separately from the _source field. Accepts true or false (default).
     * @return Store
     */
    Store store() default Store.DEFAULT;
    
    /**
     * <pre>
     * Elasticsearch allows you to configure a scoring algorithm or similarity per field. 
     * The similarity setting provides a simple way of choosing a similarity algorithm other than the default BM25, such as TF/IDF.
     * </pre>
     * @return Similarity
     */
    Similarity similarity() default Similarity.DEFAULT;
}
