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
    The index_options parameter controls what information is added to the inverted index, for search and highlighting purposes. 
    It accepts the following settings:
    
    docs
    Only the doc number is indexed. Can answer the question Does this term exist in this field?
    
    freqs
    Doc number and term frequencies are indexed. Term frequencies are used to score repeated terms higher than single terms.
    
    positions
    Doc number, term frequencies, and term positions (or order) are indexed. Positions can be used for proximity or phrase queries.
    
    offsets
    Doc number, term frequencies, positions, and start and end character offsets (which map the term back to the original string) are indexed. Offsets are used by the postings highlighter. 
 * 
 * </pre>
 * @author: shinelon
 * @date: 2017年3月5日 下午5:04:49
 *       
 * @ClassName: [Index]
 * @version: [版本号]
 * @since: [产品/模块版本]
 */
public enum IndexOptions
{
 
    DEFAULT,
    
    /**
     * Only the doc number is indexed. Can answer the question Does this term exist in this field?
     */
    DOCS("docs"),
    
    /**
     * Doc number and term frequencies are indexed. Term frequencies are used to score repeated terms higher than single terms. 
     * 
     */
    FREQS("freqs"),
    
    /**
     * Doc number, term frequencies, and term positions (or order) are indexed. Positions can be used for proximity or phrase queries.
     */
    POSITIONS("positions"),
    
    /**
     * treat as keyword field
     * 
     */
    OFFSETS("offsets");
    
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
    private IndexOptions(String value)
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
    private IndexOptions()
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
            builder.put("index_options", value);
        }
    }
}
