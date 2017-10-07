/*
 * 文 件 名: pers.linhai.esdao.enumer.Analyzer.java
 * 版    权: XXX Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述: <描述>
 * 修 改 人: shinelon
 * 修改时间: 2017年3月5日 下午2:44:16
 * 跟踪单号: <跟踪单号>
 * 修改单号: <修改单号>
 * 修改内容: <修改内容>
 */
package pers.linhai.nature.indexaccess.model.enumer;

import org.elasticsearch.common.settings.Settings.Builder;

/**
 * @Description: <一句话功能简述>
 * @author: shinelon
 * @date: 2017年3月5日 下午2:44:16
 *       
 * @ClassName: [Analyzer]
 * @version: [版本号]
 * @since: [产品/模块版本]
 */
public enum Analyzer
{
    /**
     * default value
     */
    DEFAULT,
    
    /**
     * <pre>
     * <b>Standard Analyzer</b>
     * 
     * <b>Description</b>
     *    The standard analyzer divides text into terms on word boundaries, as defined by the Unicode Text Segmentation algorithm. 
     * It removes most punctuation, lowercases terms, and supports removing stop words. 
     * 
     * <b>Definition</b>
     *    It consists of:
     * 
     *    1. <b>Tokenizer</b>
     *       Standard Tokenizer 
     * 
     *    2. <b>Token Filters</b>
     *       Standard Token Filter
     *       Lower Case Token Filter
     *       Stop Token Filter (disabled by default) 
     * 
     * </pre>
     * 
     */
    STANDARD("standard"),
    
    /**
     * <pre>
     * <b>Simple Analyzer</b>
     * 
     * <b>Description</b>
     *    The simple analyzer divides text into terms whenever it encounters a character which is not a letter. It lowercases all terms. 
     * 
     * <b>Definition</b>
     *    It consists of:
     * 
     *    1. <b>Tokenizer</b>
     *       Lower Case Tokenizer
     * 
     * </pre>
     * 
     */
    SIMPLE("simple"),
    
    
    /**
     * <pre>
     * <b>Whitespace Analyzer</b>
     * 
     * <b>Description</b>
     *    The whitespace analyzer breaks text into terms whenever it encounters a whitespace character.
     * 
     * <b>Definition</b>
     *    It consists of:
     * 
     *    1. <b>Tokenizer</b>
     *       Whitespace Tokenizer
     * 
     * </pre>
     * 
     */
    WHITESPACE("whitespace"),
    
    /**
     * <pre>
     * <b>Stop Analyzer</b>
     * 
     * <b>Description</b>
     *    The stop analyzer is the same as the simple analyzer but adds support for removing stop words. It defaults to using the _english_ stop words.
     * 
     * <b>Definition</b>
     *    It consists of:
     * 
     *    1. <b>Tokenizer</b>
     *       Lower Case Tokenizer
     * 
     *    2. <b>Token Filters</b>
     *       Stop Token Filter
     *       
     * </pre>
     */
    STOP("stop"),
    
    
    /**
     * <pre>
     * <b>Keyword Analyzer</b>
     * 
     * <b>Description</b>
     *    The keyword analyzer is a “noop” analyzer which returns the entire input string as a single token.
     * 
     * <b>Definition</b>
     *    It consists of:
     * 
     *    1. <b>Tokenizer</b>
     *       Keyword Tokenizer
     *       
     * </pre>
     */
    KEYWORD("keyword"),
    
    /**
     * <pre>
     * <b>Pattern Analyzer</b>
     * 
     * <b>Description</b>
     *    The pattern analyzer uses a regular expression to split the text into terms. 
     * The regular expression should match the token separators not the tokens themselves. The regular expression defaults to \W+ (or all non-word characters).
     * 
     * <b>Definition</b>
     *    It consists of:
     * 
     *    1. <b>Tokenizer</b>
     *       Pattern Tokenizer
     * 
     *    2. <b>Token Filters</b>
     *       Lower Case Token Filter
     *       Stop Token Filter (disabled by default) 
     *       
     * </pre>
     */
    PATTERN("pattern"),
    
    /**
     * <pre>
     * <b>Fingerprint Analyzer</b>
     * 
     * <b>Description</b>
     *    The fingerprint analyzer implements a fingerprinting algorithm which is used by the OpenRefine project to assist in clustering.
     * Input text is lowercased, normalized to remove extended characters, sorted, deduplicated and concatenated into a single token. 
     * If a stopword list is configured, stop words will also be removed.
     * 
     * <b>Definition</b>
     *    It consists of:
     * 
     *    1. <b>Tokenizer</b>
     *       Standard Tokenizer
     * 
     *    2. <b>Token Filters</b>
     *       Lower Case Token Filter
     *       ASCII Folding Token Filter
     *       Stop Token Filter (disabled by default) 
     *       Fingerprint Token Filter
     *       
     * </pre>
     */
    FINGERPRINT("fingerprint"),
    
    
    /**
     * 自定义分词器，当选择这个枚举值后，需要同时设置
     * If you do not find an analyzer suitable for your needs, you can create a custom analyzer which combines the appropriate character filters, tokenizer, and token filters.
     */
    CUSTOM;
    
    /**
     * 分词器的值
     */
    private String value;
    
    /**
     * 
     * <构造函数>
     * 
     * @author: shinelon
     * @date: 2017年3月5日 下午3:34:40
     *       
     * @param name
     */
    private Analyzer(String value)
    {
        this.value = value;
    }
    
    /**
     * 
     * <构造函数>
     * 
     * @author: shinelon
     * @date: 2017年3月5日 下午3:34:40
     *       
     * @param name
     */
    private Analyzer()
    {
        
    }

    /**
     * 获取 name
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
        if(this != DEFAULT && this != CUSTOM)
        {
            builder.put("analyzer", value);
        }
    }
}
