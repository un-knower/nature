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
package pers.linhai.nature.indexaccess.model.analysis.tokenfilters;

import java.io.IOException;

import org.elasticsearch.common.xcontent.XContentBuilder;

import pers.linhai.nature.indexaccess.model.analysis.templets.TokenFilter;

/**
 * 
 * <pre>
    Shingle Token Filter
    A token filter of type shingle that constructs shingles (token n-grams) from a token stream. In other words, 
    it creates combinations of tokens as a single token. For example, the sentence "please divide this sentence into shingles" might be 
    tokenized into shingles "please divide", "divide this", "this sentence", "sentence into", and "into shingles".
    
    This filter handles position increments > 1 by inserting filler tokens (tokens with termtext "_"). It does not handle a position increment of 0.
    
    The following are settings that can be set for a shingle token filter type:
        max_shingle_size
            The maximum shingle size. Defaults to 2.
        
        min_shingle_size
            The minimum shingle size. Defaults to 2.
        
        output_unigrams
            If true the output will contain the input tokens (unigrams) as well as the shingles. Defaults to true.
        
        output_unigrams_if_no_shingles
            If output_unigrams is false the output will contain the input tokens (unigrams) if no shingles are available. 
            Note if output_unigrams is set to true this setting has no effect. Defaults to false.
        
        token_separator
            The string to use when joining adjacent tokens to form a shingle. Defaults to " ".
        
        filler_token
            The string to use as a replacement for each position at which there is no actual token in the stream. 
            For instance this string is used if the position increment is greater than one when a stop filter is used together with the shingle filter. Defaults to "_"
 * </pre>
 * @author  shinelon
 * @version  V100R001C00
 */
public class ShingleTokenFilter extends TokenFilter
{
    
    /**
     * The maximum shingle size. Defaults to 2.
     */
    private int minShingleSize;
    
    /**
     * The maximum shingle size. Defaults to 2.
     */
    private int maxShingleSize;
    
    /**
     * If true the output will contain the input tokens (unigrams) as well as the shingles. Defaults to true
     */
    private boolean outputUnigrams = true;
    
    /**
     * <pre>
     *  If output_unigrams is false the output will contain the input tokens (unigrams) if no shingles are available. 
     *  Note if output_unigrams is set to true this setting has no effect. Defaults to false.
     * </pre>
     */
    private boolean outputUnigramsIfNoShingles;
    
    /**
     * The string to use when joining adjacent tokens to form a shingle. Defaults to " ".
     */
    private String tokenSeparator;
    
    /**
     * <pre>
     *       The string to use as a replacement for each position at which there is no actual token in the stream. 
     *       For instance this string is used if the position increment is greater than one when a stop filter is used together with the shingle filter. Defaults to "_"
     * </pre>
     */
    private String fillerToken;
    
    /** 
     * <默认构造函数>
     *
     * @param type
     */
    public ShingleTokenFilter()
    {
        super("shingle");
    }

    /**
     *
     * @param jsonBuilder
     * @throws IOException
     */
    public void build(XContentBuilder jsonBuilder) throws IOException
    {
        jsonBuilder.startObject(name);
        jsonBuilder.field("type", type);
        
        if(minShingleSize > 0)
        {
            jsonBuilder.field("min_shingle_size", minShingleSize);
        }
        
        if(maxShingleSize >= minShingleSize)
        {
            jsonBuilder.field("max_shingle_size", maxShingleSize);
        }
        
        if(!outputUnigrams)
        {
            jsonBuilder.field("output_unigrams", outputUnigrams);
        }
        
        if(outputUnigramsIfNoShingles)
        {
            jsonBuilder.field("output_unigrams_if_no_shingles", outputUnigramsIfNoShingles);
        }
        
        if(tokenSeparator != null)
        {
            jsonBuilder.field("token_separator", tokenSeparator);
        }
        
        if(fillerToken != null)
        {
            jsonBuilder.field("filler_token", fillerToken);
        }
        
        jsonBuilder.endObject();
    }

    /**
     * 返回 minShingleSize
     *
     * @return minShingleSize
     */
    public int getMinShingleSize()
    {
        return minShingleSize;
    }

    /**
     * 对minShingleSize进行赋值
     *
     * @param minShingleSize
     */
    public void setMinShingleSize(int minShingleSize)
    {
        this.minShingleSize = minShingleSize;
    }

    /**
     * 返回 maxShingleSize
     *
     * @return maxShingleSize
     */
    public int getMaxShingleSize()
    {
        return maxShingleSize;
    }

    /**
     * 对maxShingleSize进行赋值
     *
     * @param maxShingleSize
     */
    public void setMaxShingleSize(int maxShingleSize)
    {
        this.maxShingleSize = maxShingleSize;
    }

    /**
     * 返回 outputUnigrams
     *
     * @return outputUnigrams
     */
    public boolean isOutputUnigrams()
    {
        return outputUnigrams;
    }

    /**
     * 对outputUnigrams进行赋值
     *
     * @param outputUnigrams
     */
    public void setOutputUnigrams(boolean outputUnigrams)
    {
        this.outputUnigrams = outputUnigrams;
    }

    /**
     * 返回 outputUnigramsIfNoShingles
     *
     * @return outputUnigramsIfNoShingles
     */
    public boolean isOutputUnigramsIfNoShingles()
    {
        return outputUnigramsIfNoShingles;
    }

    /**
     * 对outputUnigramsIfNoShingles进行赋值
     *
     * @param outputUnigramsIfNoShingles
     */
    public void setOutputUnigramsIfNoShingles(boolean outputUnigramsIfNoShingles)
    {
        this.outputUnigramsIfNoShingles = outputUnigramsIfNoShingles;
    }

    /**
     * 返回 tokenSeparator
     *
     * @return tokenSeparator
     */
    public String getTokenSeparator()
    {
        return tokenSeparator;
    }

    /**
     * 对tokenSeparator进行赋值
     *
     * @param tokenSeparator
     */
    public void setTokenSeparator(String tokenSeparator)
    {
        this.tokenSeparator = tokenSeparator;
    }

    /**
     * 返回 fillerToken
     *
     * @return fillerToken
     */
    public String getFillerToken()
    {
        return fillerToken;
    }

    /**
     * 对fillerToken进行赋值
     *
     * @param fillerToken
     */
    public void setFillerToken(String fillerToken)
    {
        this.fillerToken = fillerToken;
    }
    
}
