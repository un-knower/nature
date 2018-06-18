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

import java.io.IOException;

import org.elasticsearch.common.xcontent.XContentBuilder;

import pers.linhai.nature.esmapper.model.analysis.templets.TokenFilter;

/**
 * 
 * <pre>
Word Delimiter Token Filter
Named word_delimiter, it Splits words into subwords and performs optional transformations on subword groups. Words are split into subwords with the following rules:

    split on intra-word delimiters (by default, all non alpha-numeric characters).
    "Wi-Fi" → "Wi", "Fi"
    split on case transitions: "PowerShot" → "Power", "Shot"
    split on letter-number transitions: "SD500" → "SD", "500"
    leading and trailing intra-word delimiters on each subword are ignored: "//hello---there, dude" → "hello", "there", "dude"
    trailing "'s" are removed for each subword: "O’Neil’s" → "O", "Neil" 
 * </pre>
 * @author  shinelon
 * @version  V100R001C00
 */
public class WordDelimiterTokenFilter extends TokenFilter
{
    
    /**
     * If true causes parts of words to be generated: "PowerShot" ⇒ "Power" "Shot". Defaults to true. 
     */
    protected boolean generateWordParts = true;
    
    /**
     * If true causes number subwords to be generated: "500-42" ⇒ "500" "42". Defaults to true.  
     */
    protected boolean generateNumberParts = true;
    
    /**
     * If true causes maximum runs of word parts to be catenated: "wi-fi" ⇒ "wifi". Defaults to false. 
     */
    protected boolean catenateWords;
    
    /**
     * If true causes maximum runs of number parts to be catenated: "500-42" ⇒ "50042". Defaults to false. 
     */
    protected boolean catenateNumbers;
    
    /**
     * If true causes all subword parts to be catenated: "wi-fi-4000" ⇒ "wifi4000". Defaults to false. 
     */
    protected boolean catenateAll;
    
    /**
     * If true causes "PowerShot" to be two tokens; ("Power-Shot" remains two parts regards). Defaults to true. 
     */
    protected boolean splitOnCaseChange = true;
    
    /**
     * If true includes original words in subwords: "500-42" ⇒ "500-42" "500" "42". Defaults to false. 
     */
    protected boolean preserveOriginal;
    
    /**
     * If true causes "j2se" to be three tokens; "j" "2" "se". Defaults to true. 
     */
    protected boolean splitOnNumerics = true;
    
    /**
     * If true causes trailing "'s" to be removed for each subword: "O’Neil’s" ⇒ "O", "Neil". Defaults to true. 
     */
    protected boolean stemEnglishPossessive = true;
    
    /** 
     * <默认构造函数>
     *
     * @param type
     */
    public WordDelimiterTokenFilter(String type)
    {
        super(type);
    }

    /** 
     * <默认构造函数>
     *
     * @param type
     */
    public WordDelimiterTokenFilter()
    {
        this("word_delimiter");
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
        
        if(!generateWordParts)
        {
            jsonBuilder.field("generate_word_parts", generateWordParts);
        }
        
        if(!generateNumberParts)
        {
            jsonBuilder.field("generate_number_parts", generateNumberParts);
        }
        
        if(catenateWords)
        {
            jsonBuilder.field("catenate_words", catenateWords);
        }
        
        if(catenateNumbers)
        {
            jsonBuilder.field("catenate_numbers", catenateNumbers);
        }
        
        if(catenateAll)
        {
            jsonBuilder.field("catenate_al", catenateAll);
        }
        
        if(!splitOnCaseChange)
        {
            jsonBuilder.field("split_on_case_change", splitOnCaseChange);
        }
        
        if(preserveOriginal)
        {
            jsonBuilder.field("preserve_original", preserveOriginal);
        }
        
        if(!splitOnNumerics)
        {
            jsonBuilder.field("split_on_numerics", splitOnNumerics);
        }
        
        if(!stemEnglishPossessive)
        {
            jsonBuilder.field("stem_english_possessive", stemEnglishPossessive);
        }
        
        jsonBuilder.endObject();
    }

    /**
     * 返回 generateWordParts
     *
     * @return generateWordParts
     */
    public boolean isGenerateWordParts()
    {
        return generateWordParts;
    }

    /**
     * 对generateWordParts进行赋值
     *
     * @param generateWordParts
     */
    public void setGenerateWordParts(boolean generateWordParts)
    {
        this.generateWordParts = generateWordParts;
    }

    /**
     * 返回 generateNumberParts
     *
     * @return generateNumberParts
     */
    public boolean isGenerateNumberParts()
    {
        return generateNumberParts;
    }

    /**
     * 对generateNumberParts进行赋值
     *
     * @param generateNumberParts
     */
    public void setGenerateNumberParts(boolean generateNumberParts)
    {
        this.generateNumberParts = generateNumberParts;
    }

    /**
     * 返回 catenateWords
     *
     * @return catenateWords
     */
    public boolean isCatenateWords()
    {
        return catenateWords;
    }

    /**
     * 对catenateWords进行赋值
     *
     * @param catenateWords
     */
    public void setCatenateWords(boolean catenateWords)
    {
        this.catenateWords = catenateWords;
    }

    /**
     * 返回 catenateNumbers
     *
     * @return catenateNumbers
     */
    public boolean isCatenateNumbers()
    {
        return catenateNumbers;
    }

    /**
     * 对catenateNumbers进行赋值
     *
     * @param catenateNumbers
     */
    public void setCatenateNumbers(boolean catenateNumbers)
    {
        this.catenateNumbers = catenateNumbers;
    }

    /**
     * 返回 catenateAll
     *
     * @return catenateAll
     */
    public boolean isCatenateAll()
    {
        return catenateAll;
    }

    /**
     * 对catenateAll进行赋值
     *
     * @param catenateAll
     */
    public void setCatenateAll(boolean catenateAll)
    {
        this.catenateAll = catenateAll;
    }

    /**
     * 返回 splitOnCaseChange
     *
     * @return splitOnCaseChange
     */
    public boolean isSplitOnCaseChange()
    {
        return splitOnCaseChange;
    }

    /**
     * 对splitOnCaseChange进行赋值
     *
     * @param splitOnCaseChange
     */
    public void setSplitOnCaseChange(boolean splitOnCaseChange)
    {
        this.splitOnCaseChange = splitOnCaseChange;
    }

    /**
     * 返回 preserveOriginal
     *
     * @return preserveOriginal
     */
    public boolean isPreserveOriginal()
    {
        return preserveOriginal;
    }

    /**
     * 对preserveOriginal进行赋值
     *
     * @param preserveOriginal
     */
    public void setPreserveOriginal(boolean preserveOriginal)
    {
        this.preserveOriginal = preserveOriginal;
    }

    /**
     * 返回 splitOnNumerics
     *
     * @return splitOnNumerics
     */
    public boolean isSplitOnNumerics()
    {
        return splitOnNumerics;
    }

    /**
     * 对splitOnNumerics进行赋值
     *
     * @param splitOnNumerics
     */
    public void setSplitOnNumerics(boolean splitOnNumerics)
    {
        this.splitOnNumerics = splitOnNumerics;
    }

    /**
     * 返回 stemEnglishPossessive
     *
     * @return stemEnglishPossessive
     */
    public boolean isStemEnglishPossessive()
    {
        return stemEnglishPossessive;
    }

    /**
     * 对stemEnglishPossessive进行赋值
     *
     * @param stemEnglishPossessive
     */
    public void setStemEnglishPossessive(boolean stemEnglishPossessive)
    {
        this.stemEnglishPossessive = stemEnglishPossessive;
    }

}
