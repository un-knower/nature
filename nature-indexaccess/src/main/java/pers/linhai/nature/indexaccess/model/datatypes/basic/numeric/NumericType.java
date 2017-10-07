/*
 * 项 目 名:  Storage Tool Service V100R001C00
 * 文 件 名:  pers.linhai.esframework.model.datatypes.basic.NumericType.java
 * 版       权:  XXX Technologies Co., Ltd. Copyright 2017,  All rights reserved.
 * 描       述:  XXX PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * 修 改 人:  shinelon
 * 修改时间:  2017年6月24日
 * 修改内容:  创建
 */
package pers.linhai.nature.indexaccess.model.datatypes.basic.numeric;

import java.lang.annotation.Annotation;
import java.util.List;

import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.settings.Settings.Builder;

import pers.linhai.nature.indexaccess.annotation.datatypes.NumericField;
import pers.linhai.nature.indexaccess.model.datatypes.DataType;
import pers.linhai.nature.indexaccess.model.enumer.Coerce;
import pers.linhai.nature.indexaccess.model.enumer.DocValues;
import pers.linhai.nature.indexaccess.model.enumer.FloatCategory;
import pers.linhai.nature.indexaccess.model.enumer.IgnoreMalformed;
import pers.linhai.nature.indexaccess.model.enumer.IncludeInAll;
import pers.linhai.nature.indexaccess.model.enumer.Index;
import pers.linhai.nature.indexaccess.model.enumer.Store;

/**
 * 
 * <pre>
 * The following numeric types are supported:
    
    long
    A signed 64-bit integer with a minimum value of -263 and a maximum value of 263-1.
    
    integer
    A signed 32-bit integer with a minimum value of -231 and a maximum value of 231-1.
    
    short
    A signed 16-bit integer with a minimum value of -32,768 and a maximum value of 32,767.
    
    byte
    A signed 8-bit integer with a minimum value of -128 and a maximum value of 127.
    
    double
    A double-precision 64-bit IEEE 754 floating point.
    
    float
    A single-precision 32-bit IEEE 754 floating point.
    
    half_float
    A half-precision 16-bit IEEE 754 floating point.
    
    scaled_float
    A floating point that is backed by a long and a fixed scaling factor. 
 *  </pre>
 * @author  shinelon
 * @version  V100R001C00
 */
public abstract class NumericType extends DataType
{
    
    /**
     * Try to convert strings to numbers and truncate fractions for integers. Accepts true (default) and false.
     *
     * @return Coerce
     */
    protected Coerce coerce = Coerce.DEFAULT;
    
    /**
     * Mapping field-level query time boosting. Accepts a floating point number, defaults to 1.0. 
     *
     * @return float
     */
    protected float boost = 1.0f;
    
    /**
     * Should the field be stored on disk in a column-stride fashion, so that it can later be used for sorting, aggregations, or scripting? Accepts true (default) or false.
     * @return DocValues
     */
    protected DocValues docValues = DocValues.DEFAULT;
    
    /**
     * If true, malformed numbers are ignored. If false (default), malformed numbers throw an exception and reject the whole document.
     *
     * @return IgnoreMalformed
     */
    protected IgnoreMalformed ignoreMalformed = IgnoreMalformed.DEFAULT;
    
    /**
     * <pre>
     * Whether or not the field value should be included in the _all field? Accepts true or false. 
     * Defaults to false if index is set to no, or if a parent object field sets include_in_all to false. Otherwise defaults to true. 
     * </pre>
     * @return boolean
     */
    protected IncludeInAll includeInAll = IncludeInAll.DEFAULT;
    
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
    protected Index index = Index.DEFAULT;
    
    /**
     * Accepts a string value which is substituted for any explicit null values. Defaults to null, which means the field is treated as missing. 
     *
     * @return String
     */
    protected String nullValue = "null";
    
    /**
     * Whether the field value should be stored and retrievable separately from the _source field. Accepts true or false (default).
     * @return Store
     */
    protected Store store = Store.DEFAULT;
    
    /**
     * <pre>
     * The scaling factor to use when encoding values. Values will be multiplied by this factor at index time and rounded to the closest long value. 
     * For instance, a scaled_float with a scaling_factor of 10 would internally store 2.34 as 23 and all search-time operations (queries, aggregations, sorting) 
     * will behave as if the document had a value of 2.3. High values of scaling_factor improve accuracy but also increase space requirements. This parameter is required. 
     * </pre>
     */
    private int scalingFactor = -1;

    /** 
     * <默认构造函数>
     *
     */
    public NumericType(List<Annotation> annoList)
    {
        NumericField anno = getAnnotation(annoList, NumericField.class);
        if(anno != null)
        {
            boost = anno.boost();
            docValues = anno.docValues();
            ignoreMalformed = anno.ignoreMalformed();
            includeInAll = anno.includeInAll();
            index = anno.index();
            nullValue = anno.nullValue();
            store = anno.store();
            
            //如果是浮点数，则需要判断类型进行不同类型处理
            if(getClass() == FloatType.class)
            {
                this.type = anno.floatCategory().value();
                if(anno.floatCategory() == FloatCategory.SCALED_FLOAT)
                {
                    scalingFactor = anno.scalingFactor();
                }
            }
        }
    }

    /**
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
        ignoreMalformed.set(builder);
        includeInAll.set(builder);
        index.set(builder);
        
        if(!"null".equals(nullValue))
        {
            builder.put("null_value", nullValue);
        }
        
        store.set(builder);
        
        if(scalingFactor > 0)
        {
            builder.put("scaling_factor", scalingFactor);
        }
        return builder;
    }
    
}
