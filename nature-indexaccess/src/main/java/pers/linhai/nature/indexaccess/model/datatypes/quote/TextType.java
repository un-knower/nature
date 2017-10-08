/*
 * 文 件 名: pers.linhai.esdao.datatype.StringType.java
 * 版    权: XXX Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述: <描述>
 * 修 改 人: shinelon
 * 修改时间: 2017年3月5日 下午2:24:59
 * 跟踪单号: <跟踪单号>
 * 修改单号: <修改单号>
 * 修改内容: <修改内容>
 */
package pers.linhai.nature.indexaccess.model.datatypes.quote;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.List;

import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.settings.Settings.Builder;
import org.elasticsearch.common.xcontent.XContentBuilder;

import pers.linhai.nature.indexaccess.annotation.datatypes.KeywordField;
import pers.linhai.nature.indexaccess.annotation.datatypes.TextField;
import pers.linhai.nature.indexaccess.model.datatypes.DataType;
import pers.linhai.nature.indexaccess.model.enumer.Analyzer;
import pers.linhai.nature.indexaccess.model.enumer.DocValues;
import pers.linhai.nature.indexaccess.model.enumer.EagerGlobalOrdinals;
import pers.linhai.nature.indexaccess.model.enumer.Fielddata;
import pers.linhai.nature.indexaccess.model.enumer.IncludeInAll;
import pers.linhai.nature.indexaccess.model.enumer.Index;
import pers.linhai.nature.indexaccess.model.enumer.IndexOptions;
import pers.linhai.nature.indexaccess.model.enumer.Norms;
import pers.linhai.nature.indexaccess.model.enumer.SearchAnalyzer;
import pers.linhai.nature.indexaccess.model.enumer.SearchQuoteAnalyzer;
import pers.linhai.nature.indexaccess.model.enumer.Similarity;
import pers.linhai.nature.indexaccess.model.enumer.Store;
import pers.linhai.nature.indexaccess.model.enumer.TermVector;
import pers.linhai.nature.utils.StringUtils;

/**
 * @Description: <一句话功能简述>
 * @author: shinelon
 * @date: 2017年3月5日 下午2:24:59
 *
 * @ClassName: 	[StringType]
 * @version: [版本号]
 * @since: [产品/模块版本]
 */
public class TextType extends DataType
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
     * @return
     * @return: Analyzer
     */
    private Analyzer analyzer = Analyzer.DEFAULT;
    
    /**
     * 自定义分词器
     * @return String
     */
    private String customAnalyzer = "";
    
    /**
     * Mapping field-level query time boosting. Accepts a floating point number, defaults to 1.0. 
     *
     * @return float
     */
    private float boost = 1.0f;
    
    /**
     * <pre>
     * Should global ordinals be loaded eagerly on refresh? Accepts true or false (default). 
     * Enabling this is a good idea on fields that are frequently used for (significant) terms aggregations.
     * </pre>
     * @return boolean
     */
    private EagerGlobalOrdinals eagerGlobalOrdinals = EagerGlobalOrdinals.DEFAULT;
    
    /**
     * Can the field use in-memory fielddata for sorting, aggregations, or scripting? Accepts true or false (default).
     *
     * @return boolean
     */
    private Fielddata fielddata = Fielddata.DEFAULT;
    
    /**
     * <pre>
     * Whether or not the field value should be included in the _all field? Accepts true or false. 
     * Defaults to false if index is set to no, or if a parent object field sets include_in_all to false. Otherwise defaults to true. 
     * </pre>
     * @return boolean
     */
    private IncludeInAll includeInAll = IncludeInAll.DEFAULT;
    
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
     * @return
     * @return: Index
     */
    private Index index = Index.DEFAULT;
    
    
    /**
     * The index_options parameter controls what information is added to the inverted index, for search and highlighting purposes.
     *
     * @return IndexOptions
     */
    private IndexOptions indexOptions = IndexOptions.DEFAULT;
    
    /**
     * Whether field-length should be taken into account when scoring queries. Accepts true (default) or false. 
     * @return boolean
     */
    private Norms norms = Norms.DEFAULT;
    
    /**
     * <pre>
     * The number of fake term position which should be inserted between each element of an array of strings. 
     * Defaults to the position_increment_gap configured on the analyzer which defaults to 100. 
     * 100 was chosen because it prevents phrase queries with reasonably large slops (less than 100) from matching terms across field values. 
     * </pre>
     * @return int
     */
    private int positionIncrementGap = 100;
    
    /**
     * Whether the field value should be stored and retrievable separately from the _source field. Accepts true or false (default).
     * @return Store
     */
    private Store store = Store.DEFAULT;
    
    /**
     * The analyzer that should be used at search time on analyzed fields. Defaults to the analyzer setting.
     *
     * @return SearchAnalyzer
     */
    private SearchAnalyzer searchAnalyzer = SearchAnalyzer.DEFAULT;
    
    /**
     * 自定义搜索分词器
     * @return String
     */
    private String customSearchAnalyzer = "";
    
    /**
     * The analyzer that should be used at search time when a phrase is encountered. Defaults to the search_analyzer setting.
     *
     * @return SearchQuoteAnalyzer
     */
    private SearchQuoteAnalyzer searchQuoteAnalyzer = SearchQuoteAnalyzer.DEFAULT;
    
    /**
     * <pre>
     * Elasticsearch allows you to configure a scoring algorithm or similarity per field. 
     * The similarity setting provides a simple way of choosing a similarity algorithm other than the default BM25, such as TF/IDF.
     * </pre>
     * @return Similarity
     */
    private Similarity similarity = Similarity.DEFAULT;
    
    /**
     * Whether term vectors should be stored for an analyzed field. Defaults to no.
     * @return TermVector
     */
    private TermVector termVector = TermVector.DEFAULT;
    
    /**
     * 关键词字段的属性
     * Should the field be stored on disk in a column-stride fashion, so that it can later be used for sorting, aggregations, or scripting? Accepts true (default) or false.
     * @return DocValues
     */
    private DocValues docValues = DocValues.DEFAULT;
    
    /**
     * 关键词字段的属性
     * Do not index any string longer than this value. Defaults to 2147483647 so that all values would be accepted.
     * @return int
     */
    private int ignoreAbove = Integer.MAX_VALUE;
    
    /**
     * 关键词字段的属性
     * Accepts a string value which is substituted for any explicit null values. Defaults to null, which means the field is treated as missing. 
     *
     * @return String
     */
    private String nullValue = "null";

    /**
     * <构造函数>
     * @author: shinelon
     * @date: 2017年3月5日 下午2:25:47 
     *
     */
    public TextType(List<Annotation> annoList)
    {
        type = "text";
        TextField anno = getAnnotation(annoList, TextField.class);
        if(anno != null)
        {
            analyzer = anno.analyzer();
            customAnalyzer = anno.customAnalyzer();
            boost = anno.boost();
            eagerGlobalOrdinals = anno.eagerGlobalOrdinals();
            fielddata = anno.fielddata();
            includeInAll = anno.includeInAll();
            index = anno.index();
            indexOptions = anno.indexOptions();
            norms = anno.norms();
            positionIncrementGap = anno.positionIncrementGap();
            store = anno.store();
            searchAnalyzer = anno.searchAnalyzer();
            customSearchAnalyzer = anno.customSearchAnalyzer();
            searchQuoteAnalyzer = anno.searchQuoteAnalyzer();
            similarity = anno.similarity();
            termVector = anno.termVector();
        }
        else
        {
            KeywordField annoKeyword = getAnnotation(annoList, KeywordField.class);
            if(annoKeyword != null)
            {
                type = "keyword";
                boost = annoKeyword.boost();
                docValues = annoKeyword.docValues();
                eagerGlobalOrdinals = annoKeyword.eagerGlobalOrdinals();
                ignoreAbove = annoKeyword.ignoreAbove();
                includeInAll = annoKeyword.includeInAll();
                index = annoKeyword.index();
                indexOptions = annoKeyword.indexOptions();
                norms = annoKeyword.norms();
                nullValue = annoKeyword.nullValue();
                store = annoKeyword.store();
                similarity = annoKeyword.similarity();
            }
        }
    }

    /**
     * 
     *
     * @param t
     * @param object
     */
    public <T> void setEntityFieldValue(T t, Object object)
    {
        invoke(t, object);
    }

    /**
     * 
     *
     * @param xContentBuilder
     * @param pojo
     * @throws IOException
     */
    @Override
    public <T> void setJsonFieldValue(XContentBuilder xContentBuilder, T pojo) throws IOException
    {
        Object obj = invoke(pojo);
        if (obj == null) return;
        xContentBuilder.field(getName(), String.valueOf(obj));
    }

    /**
     *
     * @return
     */
    public Builder getMappingParams()
    {
        Settings.Builder builder = Settings.builder();
        builder.put("type", type);
        
        //keyword和text字段的公共参数
        if(boost != 1.0f)
        {
            builder.put("boost", boost);
        }
        
        eagerGlobalOrdinals.set(builder);
        includeInAll.set(builder);
        index.set(builder);
        indexOptions.set(builder);
        norms.set(builder);
        store.set(builder);
        similarity.set(builder);
        
        if(type.equals("text"))
        {
            analyzer.set(builder);
            
            //如果是自定义分词器，则设置自定义分词器
            if(analyzer == Analyzer.CUSTOM && !StringUtils.isEmpty(customAnalyzer))
            {
                builder.put("analyzer", customAnalyzer);
            }
            
            fielddata.set(builder);
            
            if(positionIncrementGap != 100)
            {
                builder.put("position_increment_gap", positionIncrementGap);
            }
            
            searchAnalyzer.set(builder);
            if(searchAnalyzer == SearchAnalyzer.CUSTOM && !StringUtils.isEmpty(customSearchAnalyzer))
            {
                builder.put("search_analyzer", customSearchAnalyzer);
            }
            
            searchQuoteAnalyzer.set(builder);
            
            termVector.set(builder);
        }
        else
        {
            docValues.set(builder);
            
            if(!"null".equals(nullValue))
            {
                builder.put("null_value", nullValue);
            }
            
            if(ignoreAbove != Integer.MAX_VALUE)
            {
                builder.put("ignore_above", ignoreAbove);
            }
        }
        
        return builder;
    }
    
    
}
