/*
 * 项 目 名:  Storage Tool Service V100R001C00
 * 文 件 名:  pers.linhai.esframework.model.analysis.tokenizers.StandardTokenizer.java
 * 版       权:  XXX Technologies Co., Ltd. Copyright 2017,  All rights reserved.
 * 描       述:  XXX PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * 修 改 人:  shinelon
 * 修改时间:  2017年6月11日
 * 修改内容:  创建
 */
package pers.linhai.nature.indexaccess.model.analysis.tokenizers;

/**
 * <pre>
 *  The ngram tokenizer first breaks text down into words whenever it encounters one of a list of specified characters, 
 * then it emits N-grams of each word of the specified length.
 *
 *  N-grams are like a sliding window that moves across the word - a continuous sequence of characters of the specified length. 
 * They are useful for querying languages that don’t use spaces or that have long compound words, like German.
 * </pre>
 * @author  shinelon
 * @version  V100R001C00
 */
public class EdgeNGramTokenizer extends NGramTokenizer
{

    
    /** 
     * <默认构造函数>
     */
    public EdgeNGramTokenizer()
    {
        super("edge_ngram");
    }
}
