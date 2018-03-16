/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : DateJsonSerializer.java</p>
 * <p>Package     : pers.linhai.nature.j2ee.core.model</p>
 * @Creator lilinhai 2018年3月13日 下午5:28:59
 * @Version  V1.0  
 */ 

package pers.linhai.nature.j2ee.core.model;

import java.io.IOException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import pers.linhai.nature.j2ee.core.exception.DataTypeException;
import pers.linhai.nature.j2ee.core.model.datatype.DateType;

/**
 * <p>Description    : <pre>TODO(这里用一句话描述这个类的作用)</pre></p>
 * <p>ClassName      : DateJsonSerializer</p>
 * @author lilinhai 2018年3月13日 下午5:28:59
 * @version 1.0
 */
public class DateJsonDeserializer extends JsonDeserializer<Date>
{

    private DateType dateType = new DateType();

    /** 
     * <p>Overriding Method: lilinhai 2018年3月13日 下午5:31:12</p>
     * <p>Title: deserialize</p>
     * @param p
     * @param ctxt
     * @return
     * @throws IOException
     * @throws JsonProcessingException 
     * @see com.fasterxml.jackson.databind.JsonDeserializer#deserialize(com.fasterxml.jackson.core.JsonParser, com.fasterxml.jackson.databind.DeserializationContext)
     */ 
    public Date deserialize(JsonParser p, DeserializationContext ctxt)
        throws IOException,
        JsonProcessingException
    {
        try
        {
            return dateType.parse(p.getText());
        }
        catch (DataTypeException e) 
        {
            throw new DataTypeException("fieldName:" + p.getCurrentName(), e);
        }
    }

}
