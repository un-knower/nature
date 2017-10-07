/*
 * 项 目 名:  Storage Tool Service V100R001C00
 * 文 件 名:  pers.linhai.esframework.annotation.datatypes.numeric.DoubleField.java
 * 版       权:  XXX Technologies Co., Ltd. Copyright 2017,  All rights reserved.
 * 描       述:  XXX PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * 修 改 人:  shinelon
 * 修改时间:  2017年6月21日
 * 修改内容:  创建
 */
package pers.linhai.nature.indexaccess.annotation.datatypes;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import pers.linhai.nature.indexaccess.model.enumer.Coerce;
import pers.linhai.nature.indexaccess.model.enumer.DocValues;
import pers.linhai.nature.indexaccess.model.enumer.FloatCategory;
import pers.linhai.nature.indexaccess.model.enumer.IgnoreMalformed;
import pers.linhai.nature.indexaccess.model.enumer.IncludeInAll;
import pers.linhai.nature.indexaccess.model.enumer.Index;
import pers.linhai.nature.indexaccess.model.enumer.Store;

/**
 * A double-precision 64-bit IEEE 754 floating point.
 * 
 * @author  shinelon
 * @version  V100R001C00
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface NumericField
{

    
    /**
     * Try to convert strings to numbers and truncate fractions for integers. Accepts true (default) and false.
     *
     * @return Coerce
     */
    Coerce coerce() default Coerce.DEFAULT;
    
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
     * If true, malformed numbers are ignored. If false (default), malformed numbers throw an exception and reject the whole document.
     *
     * @return IgnoreMalformed
     */
    IgnoreMalformed ignoreMalformed() default IgnoreMalformed.DEFAULT;
    
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
     * 浮点数类型.
     * 当且仅当数据类型问哦float型时，才支持该属性.
     * </pre>
     * @return FloatType
     */
    FloatCategory floatCategory() default FloatCategory.DEFAULT;

    /**
     * 当浮点数类型设置为scaled_float时，该字段值才生效.
     *
     * @return int
     */
    int scalingFactor() default -1;
}
