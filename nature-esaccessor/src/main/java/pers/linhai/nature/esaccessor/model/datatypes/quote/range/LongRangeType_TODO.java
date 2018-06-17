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
package pers.linhai.nature.esaccessor.model.datatypes.quote.range;

import java.io.IOException;

import org.elasticsearch.common.settings.Settings.Builder;
import org.elasticsearch.common.xcontent.XContentBuilder;

import pers.linhai.nature.esaccessor.model.datatypes.DataType;

/**
 * @Description: A range of signed 64-bit integers with a minimum value of -263 and maximum of 263-1. 
 * @author: shinelon
 * @date: 2017年3月5日 下午2:24:59
 *
 * @ClassName: 	[StringType]
 * @version: [版本号]
 * @since: [产品/模块版本]
 */
public class LongRangeType_TODO extends DataType
{

    /**
     * <构造函数>
     * @author: shinelon
     * @date: 2017年3月5日 下午2:25:47 
     *
     */
    public LongRangeType_TODO()
    {
        this.type = "long_range";
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
