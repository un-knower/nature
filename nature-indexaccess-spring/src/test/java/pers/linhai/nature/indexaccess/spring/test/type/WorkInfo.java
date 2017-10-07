/*
 * 项 目 名:  Storage Tool Service V100R001C00
 * 文 件 名:  pers.linhai.nature.indexaccess.type.WorkInfo.java
 * 版       权:  XXX Technologies Co., Ltd. Copyright 2017,  All rights reserved.
 * 描       述:  XXX PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * 修 改 人:  shinelon
 * 修改时间:  2017年7月24日
 * 修改内容:  创建
 */
package pers.linhai.nature.indexaccess.spring.test.type;

import pers.linhai.nature.indexaccess.model.datatypes.quote.ObjectType.ObjectField;

/**
 * 
 * 
 * @author  shinelon
 * @version  V100R001C00
 */
public class WorkInfo extends ObjectField
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String job;
    
    private String address;
    
    private int moneny;

    /**
     * 返回 job
     *
     * @return job
     */
    public String getJob()
    {
        return job;
    }

    /**
     * 对job进行赋值
     *
     * @param job
     */
    public void setJob(String job)
    {
        this.job = job;
    }

    /**
     * 返回 address
     *
     * @return address
     */
    public String getAddress()
    {
        return address;
    }

    /**
     * 对address进行赋值
     *
     * @param address
     */
    public void setAddress(String address)
    {
        this.address = address;
    }

    /**
     * 返回 moneny
     *
     * @return moneny
     */
    public int getMoneny()
    {
        return moneny;
    }

    /**
     * 对moneny进行赋值
     *
     * @param moneny
     */
    public void setMoneny(int moneny)
    {
        this.moneny = moneny;
    }

    /**
     * 
     *
     * @return
     */
    public String toString()
    {
        return "WorkInfo [job=" + job + ", address=" + address + ", moneny=" + moneny + "]";
    }
}
