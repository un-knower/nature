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
package pers.linhai.nature.esmapper.annotation.datatypes;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
JSON doesn’t have a date datatype, so dates in Elasticsearch can either be:

    strings containing formatted dates, e.g. "2015-01-01" or "2015/01/01 12:10:30".
    a long number representing milliseconds-since-the-epoch.
    an integer representing seconds-since-the-epoch. 

Internally, dates are converted to UTC (if the time-zone is specified) and stored as a long number representing milliseconds-since-the-epoch.

Date formats can be customised, but if no format is specified then it uses the default:

"strict_date_optional_time||epoch_millis"

This means that it will accept dates with optional timestamps, which conform to the formats supported by strict_date_optional_time or milliseconds-since-the-epoch.
    </pre>
 * 
 * @author  shinelon
 * @version  V100R001C00
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface DateField
{
    
    /**
     * <pre>
     * default "strict_date_optional_time||epoch_millis"
     * 
     * Multiple date formats
     * Multiple formats can be specified by separating them with || as a separator. Each format will be tried in turn until a matching format is found. 
     * The first format will be used to convert the milliseconds-since-the-epoch value back into a string.
     * </pre>
     * @return String
     */
    String format() default "";
}
