/*
 * 项 目 名:  Storage Tool Service V100R001C00
 * 文 件 名:  esdao.esdao.pojo.User.java
 * 版       权:  XXX Technologies Co., Ltd. Copyright 2017,  All rights reserved.
 * 描       述:  XXX PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * 修 改 人:  shinelon
 * 修改时间:  2017年4月9日
 * 修改内容:  创建
 */
package pers.linhai.nature.indexaccess.bak.type;

import pers.linhai.nature.indexaccess.annotation.datatypes.NumericField;
import pers.linhai.nature.indexaccess.annotation.datatypes.TextField;
import pers.linhai.nature.indexaccess.model.enumer.Analyzer;
import pers.linhai.nature.indexaccess.model.enumer.Index;
import pers.linhai.nature.indexaccess.model.enumer.Store;
import pers.linhai.nature.indexaccess.model.type.Type;

/**
 * 
 * 
 * @author  shinelon
 * @version  V100R001C00
 */
public class UserInfo extends Type
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @NumericField(index=Index.TRUE, store=Store.TRUE)
    private int age;
    
    @TextField(analyzer=Analyzer.KEYWORD)
    private String name;
    

    private WorkInfo workInfo;
    
    /**
     * 返回 age
     *
     * @return age
     */
    public int getAge()
    {
        return age;
    }

    /**
     * 对age进行赋值
     *
     * @param age
     */
    public void setAge(int age)
    {
        this.age = age;
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
     * 返回 workInfo
     *
     * @return workInfo
     */
    public WorkInfo getWorkInfo()
    {
        return workInfo;
    }

    /**
     * 对workInfo进行赋值
     *
     * @param workInfo
     */
    public void setWorkInfo(WorkInfo workInfo)
    {
        this.workInfo = workInfo;
    }

    /**
     * 
     *
     * @return
     */
    @Override
    public String toString()
    {
        return "UserInfo [age=" + age + ", name=" + name + ", workInfo=" + workInfo + "]";
    }
    
    
}
