/*
 * 文 件 名: pers.linhai.esdao.datatype.StringType.java
 * 版    权: XXX Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述: <描述>
 * 修 改 人: shinelon
 * 修改时间: 2017年3月5日 下午2:24:59
 * 跟踪单号: <跟踪单号>
 * 修改单号: <修改单号>
 * 修改内容: <修改内容>
 */
package pers.linhai.nature.esmapper.model.datatypes.quote;

import java.io.IOException;

import org.elasticsearch.common.settings.Settings.Builder;
import org.elasticsearch.common.xcontent.XContentBuilder;

import pers.linhai.nature.esmapper.model.datatypes.DataType;

/**
 * @Description: 
 * <pre>
 *  In Elasticsearch, there is no dedicated array type. Any field can contain zero or more values by default, however, 
 *  all values in the array must be of the same datatype. For instance:
 *  
 *  an array of strings: [ "one", "two" ]
 *  an array of integers: [ 1, 2 ]
 *  an array of arrays: [ 1, [ 2, 3 ]] which is the equivalent of [ 1, 2, 3 ]
 *  an array of objects: [ { "name": "Mary", "age": 12 }, { "name": "John", "age": 10 }] 
 *  
 *  <b>Arrays of objects</b>
 *  Arrays of objects do not work as you would expect: you cannot query each object independently of the other 
 *  objects in the array. If you need to be able to do this then you should use the nested datatype instead of the object datatype.
 *  This is explained in more detail in Nested datatype.
 *  </pre>
 * @author: shinelon
 * @date: 2017年3月5日 下午2:24:59
 *
 * @ClassName: 	[StringType]
 * @version: [版本号]
 * @since: [产品/模块版本]
 */
public class ArrayType_TODO extends DataType
{

    /**
     * <构造函数>
     * @author: shinelon
     * @date: 2017年3月5日 下午2:25:47 
     *
     */
    public ArrayType_TODO()
    {
        this.type = "array";
    }

    /**
     * 
     *
     * @param t
     * @param object
     */
    @Override
    public <T> void setEntityFieldValue(T t, Object object)
    {
        // TODO lilinhai 简要描述
        
    }

    /**
     * 
     *
     * @param xContentBuilder
     * @param pojo
     * @throws IOException
     */
    @Override
    public <T> void setJsonFieldValue(XContentBuilder xContentBuilder, T pojo) throws IOException
    {
        // TODO lilinhai 简要描述
        
    }

    /**
     * 
     *
     * @return
     */
    @Override
    public Builder getMappingParams()
    {
        // TODO lilinhai 简要描述
        return null;
    }
}
