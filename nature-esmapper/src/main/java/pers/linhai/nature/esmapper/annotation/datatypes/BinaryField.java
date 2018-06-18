/*
 * 项 目 名:  Storage Tool Service V100R001C00
 * 文 件 名:  pers.linhai.esframework.annotation.datatypes.BinaryField.java
 * 版       权:  XXX Technologies Co., Ltd. Copyright 2017,  All rights reserved.
 * 描       述:  XXX PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * 修 改 人:  shinelon
 * 修改时间:  2017年6月21日
 * 修改内容:  创建
 */
package pers.linhai.nature.esmapper.annotation.datatypes;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import pers.linhai.nature.esmapper.model.enumer.DocValues;
import pers.linhai.nature.esmapper.model.enumer.Store;

/**
 * 
 * 
 * @author  shinelon
 * @version  V100R001C00
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface BinaryField
{
    /**
     * Should the field be stored on disk in a column-stride fashion, so that it can later be used for sorting, aggregations, or scripting? Accepts true (default) or false.
     * @return boolean
     */
    DocValues docValues() default DocValues.DEFAULT;
    
    /**
     * Whether the field value should be stored and retrievable separately from the _source field. Accepts true or false (default).
     * @return boolean
     */
    Store store() default Store.DEFAULT;
}
