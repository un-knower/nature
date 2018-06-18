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
 * The fields within the object, which can be of any datatype, including object. New properties may be added to an existing object. 
 * @author  shinelon
 * @version  V100R001C00
 */
public enum Enabled
{
    /**
     * default value
     */
    DEFAULT(true),
    
    /**
     * true
     * 
     */
    TRUE(true),
    
    /**
     * false
     * 
     */
    FALSE(false);
    
    /**
     * 枚举值
     */
    private boolean value;
    
    /**
     * <默认构造函数>
     * @param value
     */
    private Enabled(boolean value)
    {
        this.value = value;
    }

    /**
     * 返回 value
     *
     * @return value
     */
    public boolean isValue()
    {
        return value;
    }
    
    /**
     * 赋值paramMap
     * @param builder void
     */
    public void set(Builder builder)
    {
        if(this != DEFAULT)
        {
            builder.put("enabled", value);
        }
    }
}
