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
 *      Usually this doesn’t matter. The field value is already part of the _source field, which is stored by default. 
 *   If you only want to retrieve the value of a single field or of a few fields, instead of the whole _source, then this can be achieved with source filtering.
 * 
 *      n certain situations it can make sense to store a field. For instance, if you have a document with a title, a date,
 *   and a very large content field, you may want to retrieve just the title and the date without having to extract those fields from a large _source field:
 * 
 * </pre>
 * @author: shinelon
 * @date: 2017年3月5日 下午5:04:49
 *       
 * @ClassName: [Index]
 * @version: [版本号]
 * @since: [产品/模块版本]
 */
public enum Store
{
    
    /**
     * default value
     */
    DEFAULT,
    
    /**
     * no
     * 
     */
    FALSE(false),
    
    /**
     * yes
     * 
     */
    TRUE(true);
    
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
    private Store(boolean value)
    {
        this.value = value;
    }
    
    /**
     * 
     * <构造函数>
     * 
     * @author: shinelon
     * @date: 2017年3月5日 下午5:06:08
     */
    private Store()
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
            builder.put("store", value);
        }
    }
}
