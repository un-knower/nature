/*
 * 项 目 名:  Storage Tool Service V100R001C00
 * 文 件 名:  pers.linhai.nature.indexaccess.model.core.DataTypeConfiguration.java
 * 版       权:  XXX Technologies Co., Ltd. Copyright 2017,  All rights reserved.
 * 描       述:  XXX PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * 修 改 人:  shinelon
 * 修改时间:  2017年7月15日
 * 修改内容:  创建
 */
package pers.linhai.nature.esmapper.model.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pers.linhai.nature.esmapper.model.datatypes.DataType;

/**
 * 
 * 数据类型集合
 * @author  shinelon
 * @version  V100R001C00
 */
public class DataTypeCollection
{
    
    /**
     * java-field对象转换成的es-type-field对象
     */
    private Map<String, DataType> dataTypeMap;
    
    
    /**
     * 索引库对应所有java对象的成员List集合
     */
    private List<DataType> dataTypeList;

    /** 
     * <默认构造函数>
     * @param size int
     */
    public DataTypeCollection(int size)
    {
        this.dataTypeMap = new HashMap<String, DataType>(size);
        this.dataTypeList = new ArrayList<DataType>(size);
    }

    /**
     * 对dataTypeMap进行赋值
     *
     * @param dataTypeMap
     */
    public void add(DataType dt)
    {
        this.dataTypeMap.put(dt.getName(), dt);
        this.dataTypeList.add(dt);
    }
    
    public DataType get(String name)
    {
        return dataTypeMap.get(name);
    }

    /**
     * 返回 dataTypeMap
     *
     * @return dataTypeMap
     */
    public Map<String, DataType> getDataTypeMap()
    {
        return dataTypeMap;
    }

    /**
     * 返回 dataTypeList
     *
     * @return dataTypeList
     */
    public List<DataType> getDataTypeList()
    {
        return dataTypeList;
    }
}
