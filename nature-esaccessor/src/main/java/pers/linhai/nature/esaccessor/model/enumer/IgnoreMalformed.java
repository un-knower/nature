/*
 * 文 件 名: pers.linhai.esdao.enumer.Index.java
 * 版    权: XXX Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述: <描述>
 * 修 改 人: shinelon
 * 修改时间: 2017年3月5日 下午5:04:49
 * 跟踪单号: <跟踪单号>
 * 修改单号: <修改单号>
 * 修改内容: <修改内容>
 */
package pers.linhai.nature.esaccessor.model.enumer;

import org.elasticsearch.common.settings.Settings.Builder;

/**
 * <pre>
 * <b>Description</b>
 *  If true, malformed numbers are ignored. If false (default), malformed numbers throw an exception and reject the whole document.
 * </pre>
 * @author: shinelon
 * @date: 2017年3月5日 下午5:04:49
 *       
 * @ClassName: [Index]
 * @version: [版本号]
 * @since: [产品/模块版本]
 */
public enum IgnoreMalformed
{

    /**
     * default value
     */
    DEFAULT,
    
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
    private IgnoreMalformed(boolean value)
    {
        this.value = value;
    }
    
    private IgnoreMalformed()
    {
        
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
            builder.put("ignore_malformed", value);
        }
    }

}