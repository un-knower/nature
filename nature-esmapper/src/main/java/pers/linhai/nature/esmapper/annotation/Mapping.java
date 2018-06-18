/*
 * 项 目 名:  Storage Tool Service V100R001C00
 * 文 件 名:  pers.linhai.esdao.annotation.Type.java
 * 版       权:  XXX Technologies Co., Ltd. Copyright 2017,  All rights reserved.
 * 描       述:  XXX PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * 修 改 人:  shinelon
 * 修改时间:  2017年4月12日
 * 修改内容:  创建
 */
package pers.linhai.nature.esmapper.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 索引表
 * 
 * @author  shinelon
 * @version  V100R001C00
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Mapping
{
    
    /**
     * 索引表的名字
     * @return String
     */
    String name() default "";
    
    /**
     * <pre>
     *      The _all field is a special catch-all field which concatenates the values of all of the other fields into one big string,
     *   using space as a delimiter, which is then analyzed and indexed, but not stored. This means that it can be searched, but not retrieved.
     *
     *      The _all field allows you to search for values in documents without knowing which field contains the value. 
     *   This makes it a useful option when getting started with a new dataset.
     *      
     *      Note All values treated as strings
     * </pre>
     * @return boolean
     */
    boolean enabledAll() default false;
}
