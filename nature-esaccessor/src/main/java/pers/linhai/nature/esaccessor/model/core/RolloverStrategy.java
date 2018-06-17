/*
 * 项 目 名:  Storage Tool Service V100R001C00
 * 文 件 名:  pers.linhai.nature.indexaccess.core.IndexCreationStrategy.java
 * 版       权:  XXX Technologies Co., Ltd. Copyright 2017,  All rights reserved.
 * 描       述:  XXX PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * 修 改 人:  shinelon
 * 修改时间:  2017年7月29日
 * 修改内容:  创建
 */
package pers.linhai.nature.esaccessor.model.core;

/**
 * 分索引创建策略
 * @author  shinelon
 * @version  V100R001C00
 */
public class RolloverStrategy
{
    
    
    /**
     * 是否开启
     * 若为true，系统会自动根据指定策略动态生成多索引
     * @return boolean
     */
    private boolean enabled;
    
    /**
     * 单个索引的文档数超过该值，触发子索引创建，默认两亿
     */
    private long maxDocs = 2 * 10000 * 10000;
    
    /**
     * 索引所占据磁盘空间超过50G，触发子索引创建
     * 单位：字节
     */
    private long maxSize = 50 * 1024 * 1024 * 1024;
    
    /**
     * 自索引创建时间起超过默认30天，触发子索引创建
     * 单位：毫秒
     */
    private long maxLife = 30 * 24 * 60 * 60* 1000;
    
    /**
     * 最大保留多少个子索引
     */
    private int indexNumber = 4;

    /**
     * 返回 isSingleIndex
     *
     * @return isSingleIndex
     */
    public boolean isEnabled()
    {
        return enabled;
    }

    /**
     * 返回 maxDocs
     *
     * @return maxDocs
     */
    public long getMaxDocs()
    {
        return maxDocs;
    }

    /**
     * 返回 maxSize
     *
     * @return maxSize
     */
    public long getMaxSize()
    {
        return maxSize;
    }

    /**
     * 返回 maxLife
     *
     * @return maxLife
     */
    public long getMaxLife()
    {
        return maxLife;
    }

    /**
     * 对isSingleIndex进行赋值
     *
     * @param enabled boolean
     */
    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }

    /**
     * 对maxDocs进行赋值
     * @param maxDocs
     */
    public void setMaxDocs(long maxDocs)
    {
        this.maxDocs = maxDocs;
    }

    /**
     * 对maxSize进行赋值
     * @param maxSize
     */
    public void setMaxSizeBytes(long maxSize)
    {
        this.maxSize = maxSize;
    }
    
    /**
     * 对maxSize进行赋值
     * @param maxSize
     */
    public void setMaxSizeMB(long maxSize)
    {
        this.maxSize = maxSize * 1024 * 1024;
    }
    
    /**
     * 对maxSize进行赋值
     * @param maxSize
     */
    public void setMaxSizeGB(long maxSize)
    {
        this.maxSize = maxSize * 1024 * 1024 * 1024;
    }

    /**
     * 对maxLife进行赋值
     * @param maxLife
     */
    public void setMaxLifeMS(long maxLife)
    {
        this.maxLife = maxLife;
    }
    
    /**
     * 对maxLife进行赋值
     * @param maxLife
     */
    public void setMaxLifeDay(long maxLife)
    {
        this.maxLife = maxLife * 24 * 60 * 60* 1000;
    }

    /**
     * 返回 indexNumber
     *
     * @return indexNumber
     */
    public int getIndexNumber()
    {
        return indexNumber;
    }

    /**
     * 对indexNumber进行赋值
     *
     * @param indexNumber
     */
    public void setIndexNumber(int indexNumber)
    {
        this.indexNumber = indexNumber;
    }
}
