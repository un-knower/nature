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
 *      By default, fields can be added dynamically to a document, or to inner objects within a document, just by indexing a document containing the new field. For instance:
 * </pre>
 * @author: shinelon
 * @date: 2017年3月5日 下午5:04:49
 *       
 * @ClassName: [Index]
 * @version: [版本号]
 * @since: [产品/模块版本]
 */
public enum Dynamic
{
 
    /**
     * default value
     */
    DEFAULT("true"),
    
    /**
     * Newly detected fields are added to the mapping. (default) 
     * 
     */
    TRUE("true"),
    
    /**
     * <pre>
     *  Newly detected fields are ignored. These fields will not be indexed so will not be searchable but will still appear in the _source field of returned hits. 
     *  These fields will not be added to the mapping, new fields must be added explicitly. 
     * </pre>
     * 
     */
    FALSE("false"),
    
    /**
     * If new fields are detected, an exception is thrown and the document is rejected. New fields must be explicitly added to the mapping. 
     * 
     */
    STRICT("strict");
    
    /**
     * 枚举值
     */
    private String value;
    
    /**
     * 
     * <构造函数>
     * 
     * @author: shinelon
     * @date: 2017年3月5日 下午5:06:08
     *       
     * @param value
     */
    private Dynamic(String value)
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
    private Dynamic()
    {
        
    }
    
    /**
     * 获取 name
     * 
     * @return 返回 name
     */
    public String value()
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
            builder.put("dynamic", value);
        }
    }
}
