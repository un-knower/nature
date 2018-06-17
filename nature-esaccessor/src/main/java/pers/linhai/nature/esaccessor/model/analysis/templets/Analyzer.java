/*
 * 项 目 名:  Storage Tool Service V100R001C00
 * 文 件 名:  pers.linhai.esframework.model.analysis.templets.Analyzer.java
 * 版       权:  XXX Technologies Co., Ltd. Copyright 2017,  All rights reserved.
 * 描       述:  XXX PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * 修 改 人:  shinelon
 * 修改时间:  2017年6月11日
 * 修改内容:  创建
 */
package pers.linhai.nature.esaccessor.model.analysis.templets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.elasticsearch.common.xcontent.XContentBuilder;

/**
 * 
 * 抽象分词器
 * @author  shinelon
 * @version  V100R001C00
 */
public abstract class Analyzer
{
    
    /**
     * 自定义分词器的名字
     */
    protected String name;
    
    /**
     * 分词器类型
     */
    protected String type;
    
    /**
     * <pre>
     *  stop 停用词词元过滤器
     *  因分词词元过滤器JiebaTokenFilter并不处理停用词。因此我们在自定义分析器时，需要定义停用词词元过滤器来处理停用词。 
     *  Elastic Search提供了停用词词元过滤器:
     * A pre-defined stop words list like _english_ or an array containing a list of stop words. Defaults to _english_. 
     * </pre>
     */
    protected List<String> stopwordList = new ArrayList<String>();
    
    /**
     * <pre>
     *  The path to a file containing stop words. This path is relative to the Elasticsearch config directory. 
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
     * <默认构造函数>
     *
     * @param type
     */
    public Analyzer(String type)
    {
        this.type = type;
    }

    /**
     * 通过输入的XContentBuilder将该分词器的配置封装回去
     * @throws IOException 
     * @param jsonBuilder void
     */
    public abstract void build(XContentBuilder jsonBuilder) throws IOException;

    protected void packName(XContentBuilder jsonBuilder) throws IOException
    {
        Objects.requireNonNull(name, "The name of "+ getClass().getSimpleName() +" Analyzer can't be null.");
        jsonBuilder.startObject(name);
    }
    
    /** 
     * 公共属性停用词的封装
     * @param jsonBuilder
     * @throws IOException void
     */
    protected void packStopwords(XContentBuilder jsonBuilder) throws IOException
    {
        if(!stopwordList.isEmpty())
        {
            jsonBuilder.array("stopwords", stopwordList.toArray(new String[stopwordList.size()]));
        }

        if(stopwordsPath != null)
        {
            jsonBuilder.field("stopwords_path", stopwordsPath);
        }
    }
    
    /**
     * 返回 name
     *
     * @return name
     */
    public String getName()
    {
        return name;
    }

    /**
     * 对name进行赋值
     *
     * @param name
     */
    public void setName(String name)
    {
        this.name = name;
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
