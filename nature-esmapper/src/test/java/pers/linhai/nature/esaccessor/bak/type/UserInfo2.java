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
package pers.linhai.nature.esaccessor.bak.type;

import pers.linhai.nature.esmapper.annotation.datatypes.NumericField;
import pers.linhai.nature.esmapper.annotation.datatypes.TextField;
import pers.linhai.nature.esmapper.model.enumer.Analyzer;
import pers.linhai.nature.esmapper.model.enumer.Index;
import pers.linhai.nature.esmapper.model.enumer.Store;
import pers.linhai.nature.esmapper.model.type.Type;

/**
 * 
 * 
 * @author  shinelon
 * @version  V100R001C00
 */
public class UserInfo2 extends Type
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @NumericField(index=Index.TRUE, store=Store.TRUE)
    private int age;
    
    @TextField(analyzer=Analyzer.KEYWORD)
    private String name;

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
    
    
}
