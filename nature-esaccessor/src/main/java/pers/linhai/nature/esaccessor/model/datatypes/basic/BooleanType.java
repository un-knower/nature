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
package pers.linhai.nature.esaccessor.model.datatypes.basic;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.List;

import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.settings.Settings.Builder;
import org.elasticsearch.common.xcontent.XContentBuilder;

import pers.linhai.nature.esaccessor.annotation.datatypes.BooleanField;
import pers.linhai.nature.esaccessor.model.datatypes.DataType;
import pers.linhai.nature.esaccessor.model.enumer.DocValues;
import pers.linhai.nature.esaccessor.model.enumer.Index;
import pers.linhai.nature.esaccessor.model.enumer.Store;

/**
 * @Description: <一句话功能简述>
 * <pre>
 * Boolean fields accept JSON true and false values, but can also accept strings and numbers which are interpreted as either true or false:
 *  False values
 *  false, "false", "off", "no", "0", "" (empty string), 0, 0.0 
 * </pre>
 * @author: shinelon
 * @date: 2017年3月5日 下午2:24:59
 *
 * @ClassName: 	[StringType]
 * @version: [版本号]
 * @since: [产品/模块版本]
 */
public class BooleanType extends DataType
{
    
    /**
     * Mapping field-level query time boosting. Accepts a floating point number, defaults to 1.0. 
     *
     * @return float
     */
    private float boost = 1.0f;
    
    /**
     * Should the field be stored on disk in a column-stride fashion, so that it can later be used for sorting, aggregations, or scripting? Accepts true (default) or false.
     * @return DocValues
     */
    private DocValues docValues = DocValues.DEFAULT;
    
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
     * Accepts a string value which is substituted for any explicit null values. Defaults to null, which means the field is treated as missing. 
     *
     * @return String
     */
    private String nullValue = "null";
    
    /**
     * Whether the field value should be stored and retrievable separately from the _source field. Accepts true or false (default).
     * @return Store
     */
    private Store store = Store.DEFAULT;
    
    /**
     * <构造函数>
     * @author: shinelon
     * @date: 2017年3月5日 下午2:25:47 
     *
     */
    public BooleanType(List<Annotation> annoList)
    {
        this.type = "boolean";
        BooleanField anno = getAnnotation(annoList, BooleanField.class);
        if(anno != null)
        {
            boost = anno.boost();
            docValues = anno.docValues();
            index = anno.index();
            nullValue = anno.nullValue();
            store = anno.store();
        }
    }
    
    /**
     * 
     *
     * @return
     */
    public Builder getMappingParams()
    {
        Settings.Builder builder = Settings.builder();
        builder.put("type", type);
        
        if(boost != 1.0f)
        {
            builder.put("boost", boost);
        }
        
        docValues.set(builder);
        index.set(builder);
        
        if(!"null".equals(nullValue))
        {
            builder.put("null_value", nullValue);
        }
        
        store.set(builder);
        return builder;
    }

    /**
     * 
     * 
     * @param type
     * @param field
     */
    public <T> void setEntityFieldValue(T t, Object object)
    {
        invoke(t, object);
    }

    /**
     * 转换成可被索引的数据
     * @param xContentBuilder
     * @param pojo
     * @throws IOException 
     */
    public <T> void setJsonFieldValue(XContentBuilder xContentBuilder, T pojo) throws IOException
    {
        Object obj = invoke(pojo);
        if (obj == null) return;
        xContentBuilder.field(getName(), (boolean)obj);
    }
}

