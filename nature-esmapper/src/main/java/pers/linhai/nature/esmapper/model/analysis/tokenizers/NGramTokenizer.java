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
package pers.linhai.nature.esmapper.model.analysis.tokenizers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.elasticsearch.common.xcontent.XContentBuilder;

import pers.linhai.nature.esmapper.exception.IndexConfigurationException;
import pers.linhai.nature.esmapper.model.analysis.templets.Tokenizer;

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
public class NGramTokenizer extends Tokenizer
{

    /**
     * Minimum length of characters in a gram. Defaults to 1. 
     */
    private int minGram;
    
    /**
     * Maximum length of characters in a gram. Defaults to 2. 
     */
    private int maxGram;
    
    /**
     * <pre>
     * 
     *   Character classes that should be included in a token. Elasticsearch will split on characters that don’t belong to the classes specified.
     *   Defaults to [] (keep all characters).
     *   Character classes may be any of the following:
     *       letter —  for example a, b, ï or 京
     *       digit —  for example 3 or 7
     *       whitespace —  for example " " or "\n"
     *       punctuation — for example ! or "
     *       symbol —  for example $ or √ 
     * 
     * </pre>
     */
    private List<String> tokenCharList = new ArrayList<String>(6);
    
    /**
     * 支持可配置的tokenShar
     */
    static final Set<String> tokenSharsSupportSet = new HashSet<String>();
    
    static
    {
        //  letter —  for example a, b, ï or 京 
        tokenSharsSupportSet.add("letter");
        
        //  digit —  for example 3 or 7 
        tokenSharsSupportSet.add("digit");
        
        //  whitespace —  for example " " or "\n" 
        tokenSharsSupportSet.add("whitespace");
        
        //  punctuation — for example ! or " 
        tokenSharsSupportSet.add("punctuation");
        
        //  symbol —  for example $ or √ 
        tokenSharsSupportSet.add("symbol");
    }
    
    /** 
     * <默认构造函数>
     *
     * @param type
     */
    public NGramTokenizer(String type)
    {
        super(type);
    }

    /** 
     * <默认构造函数>
     */
    public NGramTokenizer()
    {
        super("ngram");
    }

    /**
     * @param jsonBuilder
     * @throws IOException
     */
    public void build(XContentBuilder jsonBuilder) throws IOException
    {
        jsonBuilder.startObject(name);
        jsonBuilder.field("type", type);
        
        if(minGram > 0)
        {
            jsonBuilder.field("min_gram", minGram);
        }
        
        if(maxGram > 0)
        {
            jsonBuilder.field("max_gram", maxGram);
        }
        
        if(!tokenCharList.isEmpty())
        {
            jsonBuilder.array("token_chars", tokenCharList.toArray(new String[tokenCharList.size()]));
        }
        
        jsonBuilder.endObject();
    }

    /**
     * 添加一个字符串过滤器
     * 
     *
     * @param charFilter void
     */
    public void addTokenChar(String tokenChar)
    {
        if(tokenSharsSupportSet.contains(tokenChar))
        {
            tokenCharList.add(tokenChar);
        }
        else
        {
            throw new IndexConfigurationException("Token Char[" + tokenChar + "] is not supported in the NGramTokenizer or EdgeNGramTokenizer.");
        }
    }
    
    /**
     * 返回 minGram
     *
     * @return minGram
     */
    public int getMinGram()
    {
        return minGram;
    }

    /**
     * 对minGram进行赋值
     *
     * @param minGram
     */
    public void setMinGram(int minGram)
    {
        this.minGram = minGram;
    }

    /**
     * 返回 maxGram
     *
     * @return maxGram
     */
    public int getMaxGram()
    {
        return maxGram;
    }

    /**
     * 对maxGram进行赋值
     *
     * @param maxGram
     */
    public void setMaxGram(int maxGram)
    {
        this.maxGram = maxGram;
    }
}
