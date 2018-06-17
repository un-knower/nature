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
package pers.linhai.nature.esaccessor.annotation.datatypes;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import pers.linhai.nature.esaccessor.model.enumer.DocValues;
import pers.linhai.nature.esaccessor.model.enumer.Index;
import pers.linhai.nature.esaccessor.model.enumer.Store;

/**
 * A double-precision 64-bit IEEE 754 floating point.
 * 
 * @author  shinelon
 * @version  V100R001C00
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface BooleanField
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
}
