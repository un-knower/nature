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
 *  term_vector
 *  Term vectors contain information about the terms produced by the analysis process, including:
 *    a list of terms.
 *    the position (or order) of each term.
 *    the start and end character offsets mapping the term to its origin in the original string. 
 *  
 *  The term_vector setting accepts:
 *      no                              No term vectors are stored. (default)
 *      yes                             Just the terms in the field are stored.
 *      with_positions                  Terms and positions are stored.
 *      with_offsets                    Terms and character offsets are stored.
 *      with_positions_offsets          Terms, positions, and character offsets are stored.

 *  The fast vector highlighter requires with_positions_offsets. The term vectors API can retrieve whatever is stored.
 * 
 * </pre>
 * @author: shinelon
 * @date: 2017年3月5日 下午5:04:49
 *        
 * @ClassName: [Index]
 * @version: [版本号]
 * @since: [产品/模块版本]
 */
public enum TermVector
{
    
    /**
     * default value
     */
    DEFAULT,
    
    /**
     * No term vectors are stored. (default)
     * 
     */
    NO("no"),
    
    /**
     * Just the terms in the field are stored.
     * 
     */
    YES("yes"),
    
    /**
     * Terms and positions are stored.
     * 
     */
    WITH_POSITIONS("with_positions"),
    
    /**
     * Terms and character offsets are stored.
     * 
     */
    WITH_OFFSETS("with_offsets"),
    
    /**
     * Terms, positions, and character offsets are stored.
     * 
     */
    WITH_POSITIONS_OFFSETS("with_positions_offsets");
    
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
     */
    private TermVector()
    {
        
    }
    
    /**
     * 
     * <构造函数>
     * 
     * @author: shinelon
     * @date: 2017年3月5日 下午5:06:08
     *        
     * @param value
     */
    private TermVector(String value)
    {
        this.value = value;
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
            builder.put("term_vector", value);
        }
    }
}
