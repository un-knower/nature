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
package pers.linhai.nature.esaccessor.model.datatypes.basic.numeric;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.List;

import org.elasticsearch.common.xcontent.XContentBuilder;

import pers.linhai.nature.esaccessor.model.enumer.FloatCategory;

/**
 * @Description: <一句话功能简述>
 * @author: shinelon
 * @date: 2017年3月5日 下午2:24:59
 *
 * @ClassName: 	[StringType]
 * @version: [版本号]
 * @since: [产品/模块版本]
 */
public class FloatType extends NumericType
{
    
    /**
     * <构造函数>
     * @author: shinelon
     * @date: 2017年3月5日 下午2:25:47 
     *
     */
    public FloatType(List<Annotation> annoList)
    {
        super(annoList);
        type = FloatCategory.DEFAULT.value();
    }
    
    /**
     * 
     * 
     * @param type
     * @param field
     */
    public <T> void setEntityFieldValue(T t, Object object)
    {
        invoke(t, object);
    }

    /**
     * 转换成可被索引的数据
     * @param xContentBuilder
     * @param pojo
     * @throws IOException 
     */
    public <T> void setJsonFieldValue(XContentBuilder xContentBuilder, T pojo) throws IOException
    {
        Object obj = invoke(pojo);
        if (obj == null) return;
        xContentBuilder.field(getName(), (float)obj);
    }
}