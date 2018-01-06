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
package pers.linhai.nature.indexaccess.model.enumer;

import org.elasticsearch.common.settings.Settings.Builder;

/**
 * <pre>
 * <b>Description</b>
 *      The index option controls whether field values are indexed. It accepts true or false. Fields that are not indexed are not queryable.
 * 
 *      For the legacy mapping type string the index option only accepts legacy values analyzed (default, treat as full-text field), 
 *  not_analyzed (treat as keyword field) and no.
 * 
 * </pre>
 * @author: shinelon
 * @date: 2017年3月5日 下午5:04:49
 *       
 * @ClassName: [Index]
 * @version: [版本号]
 * @since: [产品/模块版本]
 */
public enum Index
{
 
    /**
     * default value
     */
    DEFAULT,
    
    /**
     * treat as full-text field
     * 
     */
    TRUE(true),
    
    /**
     * treat as keyword field
     * 
     */
    FALSE(false);
    
    /**
     * 枚举值
     */
    private boolean value;
    
    /**
     * 
     * <构造函数>
     * 
     * @author: shinelon
     * @date: 2017年3月5日 下午5:06:08
     *       
     * @param value
     */
    private Index(boolean value)
    {
        this.value = value;
    }
    
    /**
     * 
     * <构造函数>
     * 
     * @author: shinelon
     * @date: 2017年3月5日 下午5:06:08
     *       
     */
    private Index()
    {
        
    }
    
    /**
     * 获取 name
     * 
     * @return 返回 name
     */
    public boolean value()
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
            builder.put("index", value);
        }
    }
}
