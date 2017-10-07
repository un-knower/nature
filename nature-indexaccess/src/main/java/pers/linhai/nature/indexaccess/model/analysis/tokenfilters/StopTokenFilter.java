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
import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.common.xcontent.XContentBuilder;

import pers.linhai.nature.indexaccess.model.analysis.templets.TokenFilter;

/**
 * 
 * <pre>
    Stop Token Filter
    
    A token filter of type stop that removes stop words from token streams.
    
    The following are settings that can be set for a stop token filter type:
    
    stopwords
        A list of stop words to use. Defaults to _english_ stop words.
    
    stopwords_path
        A path (either relative to config location, or absolute) to a stopwords file configuration. 
        Each stop word should be in its own "line" (separated by a line break). The file must be UTF-8 encoded.
    
    ignore_case
        Set to true to lower case all words first. Defaults to false.
    
    remove_trailing
        Set to false in order to not ignore the last term of a search if it is a stop word. 
        This is very useful for the completion suggester as a query like green a can be extended to green 
        apple even though you remove stop words in general. Defaults to true. 
     
 * </pre>
 * @author  shinelon
 * @version  V100R001C00
 */
public class StopTokenFilter extends TokenFilter
{
    /**
     * <pre>
     * A list of stop words to use. Defaults to _english_ stop words. 
     *  stop 停用词词元过滤器
     *  因分词词元过滤器JiebaTokenFilter并不处理停用词。因此我们在自定义分析器时，需要定义停用词词元过滤器来处理停用词。 
     *  Elastic Search提供了停用词词元过滤器:
     * A pre-defined stop words list like _english_ or an array containing a list of stop words. Defaults to _english_. 
     * </pre>
     */
    protected List<String> stopwordList = new ArrayList<String>();
    
    /**
     * <pre>
     *  A path (either relative to config location, or absolute) to a stopwords file configuration. 
     *  Each stop word should be in its own "line" (separated by a line break). The file must be UTF-8 encoded. 
     *  stop 停用词词元过滤器
     *  因分词词元过滤器JiebaTokenFilter并不处理停用词。因此我们在自定义分析器时，需要定义停用词词元过滤器来处理停用词。 
     *  Elastic Search提供了停用词词元过滤器:
     *    "stop_filter": {
     *        "type": "stop",
     *        "stopwords_path": "stopwords.txt"
     *    }
     * </pre>
     */
    protected String stopwordsPath;
    
    /**
     * Set to true to lower case all words first. Defaults to false. 
     */
    private boolean ignoreCase;
    
    /**
     * <pre>
     * Set to false in order to not ignore the last term of a search if it is a stop word. 
     * This is very useful for the completion suggester as a query like green a can be extended to green 
     * apple even though you remove stop words in general. Defaults to true. 
     * </pre>
     */
    private boolean removeTrailing = true;
    
    /** 
     * <默认构造函数>
     *
     * @param type
     */
    public StopTokenFilter()
    {
        super("stop");
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
        
        if(!stopwordList.isEmpty())
        {
            jsonBuilder.array("stopwords", stopwordList.toArray(new String[stopwordList.size()]));
        }

        if(stopwordsPath != null)
        {
            jsonBuilder.field("stopwords_path", stopwordsPath);
        }
        
        if(ignoreCase)
        {
            jsonBuilder.field("ignore_case", ignoreCase);
        }
        
        if(!removeTrailing)
        {
            jsonBuilder.field("remove_trailing", removeTrailing);
        }
        jsonBuilder.endObject();
    }

    /**
     * 对stopwords进行赋值
     *
     * @param stopwords
     */
    public void addStopword(String stopword)
    {
        this.stopwordList.add(stopword);
    }

    /**
     * 返回 stopwordsPath
     *
     * @return stopwordsPath
     */
    public String getStopwordsPath()
    {
        return stopwordsPath;
    }

    /**
     * 对stopwordsPath进行赋值
     *
     * @param stopwordsPath
     */
    public void setStopwordsPath(String stopwordsPath)
    {
        this.stopwordsPath = stopwordsPath;
    }
}
