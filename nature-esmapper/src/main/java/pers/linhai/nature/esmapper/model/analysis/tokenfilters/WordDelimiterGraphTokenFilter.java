/*
 * 项 目 名:  Storage Tool Service V100R001C00
 * 文 件 名:  pers.linhai.esframework.model.analysis.tokenfilters.ASCIIFoldingTokenFilter.java
 * 版       权:  XXX Technologies Co., Ltd. Copyright 2017,  All rights reserved.
 * 描       述:  XXX PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * 修 改 人:  shinelon
 * 修改时间:  2017年6月11日
 * 修改内容:  创建
 */
package pers.linhai.nature.esmapper.model.analysis.tokenfilters;

/**
 * 
 * <pre>
    Named word_delimiter_graph, it splits words into subwords and performs optional transformations on subword groups. 
    Words are split into subwords with the following rules:
    
        split on intra-word delimiters (by default, all non alpha-numeric characters).
        "Wi-Fi" → "Wi", "Fi"
        split on case transitions: "PowerShot" → "Power", "Shot"
        split on letter-number transitions: "SD500" → "SD", "500"
        leading and trailing intra-word delimiters on each subword are ignored: "//hello---there, dude" → "hello", "there", "dude"
        trailing "'s" are removed for each subword: "O’Neil’s" → "O", "Neil" 
    
    Unlike the word_delimiter, this token filter correctly handles positions for multi terms expansion at search-time when 
    any of the following options are set to true:
        preserve_original
        catenate_numbers
        catenate_words
        catenate_all 
 * </pre>
 * @author  shinelon
 * @version  V100R001C00
 */
public class WordDelimiterGraphTokenFilter extends WordDelimiterTokenFilter
{
    
    /** 
     * <默认构造函数>
     *
     * @param type
     */
    public WordDelimiterGraphTokenFilter()
    {
        super("word_delimiter_graph");
    }

}
