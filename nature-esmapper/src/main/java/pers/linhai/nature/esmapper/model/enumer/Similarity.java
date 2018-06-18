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
package pers.linhai.nature.esmapper.model.enumer;

import org.elasticsearch.common.settings.Settings.Builder;

/**
 * <pre>
 * <b>Description</b>
 * Elasticsearch allows you to configure a scoring algorithm or similarity per field. 
 * The similarity setting provides a simple way of choosing a similarity algorithm other than the default BM25, such as TF/IDF.
 * </pre>
 * @author: shinelon
 * @date: 2017年3月5日 下午5:04:49
 *       
 * @ClassName: [Index]
 * @version: [版本号]
 * @since: [产品/模块版本]
 */
public enum Similarity
{
 
    /**
     * default value
     */
    DEFAULT,
    
    /**
     * The Okapi BM25 algorithm. The algorithm used by default in Elasticsearch and Lucene. See Pluggable Similarity Algorithms for more information.
     */
    BM25("BM25"),
    
    /**
     * The TF/IDF algorithm which used to be the default in Elasticsearch and Lucene. See Lucene’s Practical Scoring Function for more information.
     */
    CLASSIC("classic"),
    
    /**
     * A simple boolean similarity, which is used when full-text ranking is not needed and the score should only be based on whether the query terms match or not. Boolean similarity gives terms a score equal to their query boost. 
     */
    BOOLEAN("boolean");
    
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
    private Similarity(String value)
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
    private Similarity()
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
            builder.put("similarity", value);
        }
    }
}
