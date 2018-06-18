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

import org.elasticsearch.common.xcontent.XContentBuilder;

import pers.linhai.nature.esmapper.model.analysis.templets.Tokenizer;

/**
 * 
 * The path_hierarchy tokenizer takes a hierarchical value like a filesystem path, splits on the path separator, and emits a term for each component in the tree.
 * @author  shinelon
 * @version  V100R001C00
 */
public class PathHierarchyTokenizer extends Tokenizer
{

    /**
     * The character to use as the path separator. Defaults to /. 
     */
    private String delimiter;
    
    /**
     * An optional replacement character to use for the delimiter. Defaults to the delimiter. 
     */
    private String replacement;
    
    /**
     * <pre>
     * he number of characters read into the term buffer in a single pass. 
     * Defaults to 1024. The term buffer will grow by this size until all the text has been consumed. It is advisable not to change this setting. 
     * </pre>
     */
    private int bufferSize;
    
    /**
     * If set to true, emits the tokens in reverse order. Defaults to false. 
     */
    private boolean reverse;
    
    /**
     * The number of initial tokens to skip. Defaults to 0. 
     */
    private int skip;
    
    /** 
     * <默认构造函数>
     */
    public PathHierarchyTokenizer()
    {
        super("path_hierarchy");
    }

    /**
     * 
     *
     * @param jsonBuilder
     * @throws IOException
     */
    public void build(XContentBuilder jsonBuilder) throws IOException
    {
        jsonBuilder.startObject(name);
        jsonBuilder.field("type", type);
        
        if(delimiter != null)
        {
            jsonBuilder.field("delimiter", delimiter);
        }
        
        if(replacement != null)
        {
            jsonBuilder.field("replacement", replacement);
        }
        
        
        if(bufferSize > 0)
        {
            jsonBuilder.field("buffer_size", bufferSize);
        }
        
        if(skip > 0)
        {
            jsonBuilder.field("skip", skip);
        }
        
        if(reverse)
        {
            jsonBuilder.field("reverse", reverse);
        }
        
        jsonBuilder.endObject();
    }

    /**
     * 返回 delimiter
     *
     * @return delimiter
     */
    public String getDelimiter()
    {
        return delimiter;
    }

    /**
     * 对delimiter进行赋值
     *
     * @param delimiter
     */
    public void setDelimiter(String delimiter)
    {
        this.delimiter = delimiter;
    }

    /**
     * 返回 replacement
     *
     * @return replacement
     */
    public String getReplacement()
    {
        return replacement;
    }

    /**
     * 对replacement进行赋值
     *
     * @param replacement
     */
    public void setReplacement(String replacement)
    {
        this.replacement = replacement;
    }

    /**
     * 返回 bufferSize
     *
     * @return bufferSize
     */
    public int getBufferSize()
    {
        return bufferSize;
    }

    /**
     * 对bufferSize进行赋值
     *
     * @param bufferSize
     */
    public void setBufferSize(int bufferSize)
    {
        this.bufferSize = bufferSize;
    }

    /**
     * 返回 reverse
     *
     * @return reverse
     */
    public boolean isReverse()
    {
        return reverse;
    }

    /**
     * 对reverse进行赋值
     *
     * @param reverse
     */
    public void setReverse(boolean reverse)
    {
        this.reverse = reverse;
    }

    /**
     * 返回 skip
     *
     * @return skip
     */
    public int getSkip()
    {
        return skip;
    }

    /**
     * 对skip进行赋值
     *
     * @param skip
     */
    public void setSkip(int skip)
    {
        this.skip = skip;
    }
}
