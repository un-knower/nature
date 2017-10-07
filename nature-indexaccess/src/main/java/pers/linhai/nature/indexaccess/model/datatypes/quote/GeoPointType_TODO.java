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
package pers.linhai.nature.indexaccess.model.datatypes.quote;

import java.io.IOException;

import org.elasticsearch.common.settings.Settings.Builder;
import org.elasticsearch.common.xcontent.XContentBuilder;

import pers.linhai.nature.indexaccess.model.datatypes.DataType;

/**
 * @Description: <一句话功能简述>
 * <pre>
 * Boolean fields accept JSON true and false values, but can also accept strings and numbers which are interpreted as either true or false:
 *  False values
 *  false, "false", "off", "no", "0", "" (empty string), 0, 0.0 
 * </pre>
 * @author: shinelon
 * @date: 2017年3月5日 下午2:24:59
 *
 * @ClassName: 	[StringType]
 * @version: [版本号]
 * @since: [产品/模块版本]
 */
public class GeoPointType_TODO extends DataType
{

    /**
     * <构造函数>
     * @author: shinelon
     * @date: 2017年3月5日 下午2:25:47 
     *
     */
    public GeoPointType_TODO()
    {
        this.type = "boolean";
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
