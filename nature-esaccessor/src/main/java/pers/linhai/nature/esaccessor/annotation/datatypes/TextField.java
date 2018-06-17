/*
 * 项 目 名:  Storage Tool Service V100R001C00
 * 文 件 名:  pers.linhai.esframework.annotation.datatypes.TextField.java
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

import pers.linhai.nature.esaccessor.model.enumer.Analyzer;
import pers.linhai.nature.esaccessor.model.enumer.EagerGlobalOrdinals;
import pers.linhai.nature.esaccessor.model.enumer.Fielddata;
import pers.linhai.nature.esaccessor.model.enumer.IncludeInAll;
import pers.linhai.nature.esaccessor.model.enumer.Index;
import pers.linhai.nature.esaccessor.model.enumer.IndexOptions;
import pers.linhai.nature.esaccessor.model.enumer.Norms;
import pers.linhai.nature.esaccessor.model.enumer.SearchAnalyzer;
import pers.linhai.nature.esaccessor.model.enumer.SearchQuoteAnalyzer;
import pers.linhai.nature.esaccessor.model.enumer.Similarity;
import pers.linhai.nature.esaccessor.model.enumer.Store;
import pers.linhai.nature.esaccessor.model.enumer.TermVector;

/**
 * 
 * 
 * @author  shinelon
 * @version  V100R001C00
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface TextField
{
    
    /**
     * <pre>
     * <b>Description</b> 
     * 
     *      The values of analyzed string fields are passed through an analyzer to convert the string into a stream of tokens or terms. 
     *    For instance, the string "The quick Brown Foxes." may, depending on which analyzer is used, be analyzed to the tokens: quick, brown, fox. 
     *    These are the actual terms that are indexed for the field, which makes it possible to search efficiently for individual words within big blobs of text.
     *      
     *      This analysis process needs to happen not just at index time, but also at query time: the query string needs to be passed through the 
     *    same (or a similar) analyzer so that the terms that it tries to find are in the same format as those that exist in the index.
     * 
     * </pre>
     * 
     * @author: shinelon
     * @date: 2017年3月5日 下午5:25:32
     * @Title: analyzer
     *         
     * @return Analyzer
     */
    Analyzer analyzer() default Analyzer.DEFAULT;
    
    /**
     * 自定义分词器.
     * @return String
     */
    String customAnalyzer() default "";
    
    /**
     * Mapping field-level query time boosting. Accepts a floating point number, defaults to 1.0. 
     *
     * @return float
     */
    float boost() default 1.0f;
    
    /**
     * <pre>
     * Should global ordinals be loaded eagerly on refresh? Accepts true or false (default). 
     * Enabling this is a good idea on fields that are frequently used for (significant) terms aggregations.
     * </pre>
     * @return boolean
     */
    EagerGlobalOrdinals eagerGlobalOrdinals() default EagerGlobalOrdinals.DEFAULT;
    
    /**
     * Can the field use in-memory fielddata for sorting, aggregations, or scripting? Accepts true or false (default).
     *
     * @return boolean
     */
    Fielddata fielddata() default Fielddata.DEFAULT;
    
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
     * <pre>
     * The number of fake term position which should be inserted between each element of an array of strings. 
     * Defaults to the position_increment_gap configured on the analyzer which defaults to 100. 
     * 100 was chosen because it prevents phrase queries with reasonably large slops (less than 100) from matching terms across field values. 
     * </pre>
     * @return int
     */
    int positionIncrementGap() default 100;
    
    /**
     * Whether the field value should be stored and retrievable separately from the _source field. Accepts true or false (default).
     * @return Store
     */
    Store store() default Store.DEFAULT;
    
    /**
     * The analyzer that should be used at search time on analyzed fields. Defaults to the analyzer setting.
     *
     * @return SearchAnalyzer
     */
    SearchAnalyzer searchAnalyzer() default SearchAnalyzer.DEFAULT;
    
    /**
     * 自定义搜索分词器.
     * @return String
     */
    String customSearchAnalyzer() default "";
    
    /**
     * The analyzer that should be used at search time when a phrase is encountered. Defaults to the search_analyzer setting.
     *
     * @return SearchQuoteAnalyzer
     */
    SearchQuoteAnalyzer searchQuoteAnalyzer() default SearchQuoteAnalyzer.DEFAULT;
    
    /**
     * <pre>
     * Elasticsearch allows you to configure a scoring algorithm or similarity per field. 
     * The similarity setting provides a simple way of choosing a similarity algorithm other than the default BM25, such as TF/IDF.
     * </pre>
     * @return Similarity
     */
    Similarity similarity() default Similarity.DEFAULT;
    
    /**
     * Whether term vectors should be stored for an analyzed field. Defaults to no.
     * @return TermVector
     */
    TermVector termVector() default TermVector.DEFAULT;
    
}
