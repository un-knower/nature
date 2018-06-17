/*
 * 项 目 名:  Storage Tool Service V100R001C00
 * 文 件 名:  pers.linhai.nature.indexaccess.model.core.IndexMetaInfo.java
 * 版       权:  XXX Technologies Co., Ltd. Copyright 2017,  All rights reserved.
 * 描       述:  XXX PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * 修 改 人:  shinelon
 * 修改时间:  2017年7月30日
 * 修改内容:  创建
 */
package pers.linhai.nature.esaccessor.model.core;

import java.util.List;
import java.util.Objects;

import org.elasticsearch.action.admin.indices.stats.CommonStats;

/**
 * 
 * 
 * @author  shinelon
 * @version  V100R001C00
 */
public class RolloverIndexInfo implements Comparable<RolloverIndexInfo>
{
    
    /**
     * 滚动索引名
     */
    private String indexName;
    
    /**
     * 索引别名
     */
    private String indexAliasName;
    
    /**
     * 创建时间
     */
    private long creationDate;
    
    /**
     * 统计信息
     */
    private StatInfo statInfo;

    /**
     * 返回 indexName
     *
     * @return indexName
     */
    public String getIndexName()
    {
        return indexName;
    }

    /**
     * 对indexName进行赋值
     *
     * @param indexName
     */
    public void setIndexName(String indexName)
    {
        this.indexName = indexName;
    }

    /**
     * 返回 indexAliasName
     *
     * @return indexAliasName
     */
    public String getIndexAliasName()
    {
        return indexAliasName;
    }

    /**
     * 对indexAliasName进行赋值
     *
     * @param indexAliasName
     */
    public void setIndexAliasName(String indexAliasName)
    {
        this.indexAliasName = indexAliasName;
    }

    /**
     * 返回 creationDate
     *
     * @return creationDate
     */
    public long getCreationDate()
    {
        return creationDate;
    }

    /**
     * 对creationDate进行赋值
     *
     * @param creationDate
     */
    public void setCreationDate(long creationDate)
    {
        this.creationDate = creationDate;
    }
    
    /**
     * 对statInfo进行赋值
     *
     * @param statInfo
     */
    public void setStatInfo(List<CommonStats> commonStatsList)
    {
        Objects.requireNonNull(commonStatsList, "CommonStatsList con't be null");
        this.statInfo = new StatInfo();
        for (CommonStats commonStats : commonStatsList)
        {
            statInfo.addDocsCount(commonStats.getDocs().getCount());
            statInfo.addSizeInBytes(commonStats.getStore().getSizeInBytes());
        }
    }
    
    /**
     * 返回 statInfo
     *
     * @return statInfo
     */
    public StatInfo getStatInfo()
    {
        return statInfo;
    }

    /**
     * 
     *
     * @param o
     * @return
     */
    public int compareTo(RolloverIndexInfo o)
    {
        return (int)(this.creationDate - o.creationDate);
    }
    
    /**
     * 索引统计信息
     * @author  shinelon
     * @version  V100R001C00
     */
    public static class StatInfo
    {
        /**
         * 文档数
         */
        private long docsCount;
        
        /**
         * 磁盘空间占据大小
         */
        private long sizeInBytes;

        /**
         * 返回 docsCount
         *
         * @return docsCount
         */
        public long getDocsCount()
        {
            return docsCount;
        }

        /**
         * 对docsCount进行赋值
         *
         * @param docsCount
         */
        public void addDocsCount(long docsCount)
        {
            this.docsCount += docsCount;
        }

        /**
         * 返回 sizeInBytes
         *
         * @return sizeInBytes
         */
        public long getSizeInBytes()
        {
            return sizeInBytes;
        }

        /**
         * 对sizeInBytes进行赋值
         *
         * @param sizeInBytes
         */
        public void addSizeInBytes(long sizeInBytes)
        {
            this.sizeInBytes += sizeInBytes;
        }

        /**
         * 
         *
         * @return
         */
        public String toString()
        {
            return "StatInfo [docsCount=" + docsCount + ", sizeInBytes=" + sizeInBytes + "]";
        }
    }
    
}
