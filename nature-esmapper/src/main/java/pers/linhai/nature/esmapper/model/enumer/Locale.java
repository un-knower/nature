/*
 * 项 目 名:  Storage Tool Service V100R001C00
 * 文 件 名:  pers.linhai.esdao.annotation.enumer.Bool.java
 * 版       权:  XXX Technologies Co., Ltd. Copyright 2017,  All rights reserved.
 * 描       述:  XXX PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * 修 改 人:  shinelon
 * 修改时间:  2017年4月9日
 * 修改内容:  创建
 */
package pers.linhai.nature.esmapper.model.enumer;

import org.elasticsearch.common.settings.Settings.Builder;

/**
 * 
 * 
 * @author  shinelon
 * @version  V100R001C00
 */
public enum Locale
{
    /**
     * default value
     */
    DEFAULT("ROOT"),
    
    /**
     * 
     */
    CANADA("CANADA"),
    
    /**
     * 
     */
    CANADA_FRENCH("CANADA_FRENCH"),
    
    /**
     * 
     */
    CHINA("CHINA"),
    
    /**
     * 
     */
    CHINESE("CHINESE"),
    
    /**
     * 
     */
    ENGLISH("ENGLISH"),
    
    /**
     * 
     */
    FRANCE("FRANCE"),
    
    /**
     * 
     */
    FRENCH("FRENCH"),
    
    /**
     * 
     */
    GERMAN("GERMAN"),
    
    /**
     * 
     */
    GERMANY("GERMANY"),
    
    /**
     * 
     */
    ITALIAN("ITALIAN"),
    
    /**
     * 
     */
    ITALY("ITALY"),
    
    /**
     * 
     */
    JAPAN("JAPAN"),
    
    /**
     * 
     */
    JAPANESE("JAPANESE"),
    
    /**
     * 
     */
    KOREA("KOREA"),
    
    /**
     * 
     */
    KOREAN("KOREAN"),
    
    /**
     * 
     */
    PRC("PRC"),
    
    /**
     * 
     */
    ROOT("ROOT"),
    
    /**
     * 
     */
    SIMPLIFIED_CHINESE("SIMPLIFIED_CHINESE"),
    
    /**
     * 
     */
    TAIWAN("TAIWAN"),
    
    /**
     * 
     */
    TRADITIONAL_CHINESE("TRADITIONAL_CHINESE"),
    
    /**
     * 
     */
    UK("UK"),
    
    /**
     * 
     */
    US("US");
    
    /**
     * 枚举值
     */
    private String value;
    
    /**
     * <默认构造函数>
     * @param value
     */
    private Locale(String value)
    {
        this.value = value;
    }
    
    /**
     * 赋值paramMap
     * @param builder void
     */
    public void set(Builder builder)
    {
        if (this != DEFAULT)
        {
            builder.put("locale", value);
        }
    }
}
