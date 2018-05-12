/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : NumberType.java</p>
 * <p>Package     : com.meme.crm.model.core.datatype</p>
 * @Creator lilinhai 2018年2月15日 下午6:58:54
 * @Version  V1.0  
 */

package pers.linhai.nature.j2ee.core.model.datatypeparser;

/**
 * <p>Description    : <pre>TODO(这里用一句话描述这个类的作用)</pre></p>
 * <p>ClassName      : NumberType</p>
 * @author lilinhai 2018年2月15日 下午6:58:54
 * @version 1.0
 */
public class ShortTypeParser extends DataTypeParser
{
    
    /** 
     * <p>Overriding Method: lilinhai 2018年2月15日 下午6:59:08</p>
     * <p>Title: parse</p>
     * @param value
     * @return 
     * @see com.DataTypeParser.crm.model.core.datatype.DataType#parse(java.lang.String)
     */
    public Object parse(Object value)
    {
        if (value instanceof String)
        {
            return Short.parseShort(value.toString());
        }
        else if (value instanceof Integer)
        {
            return ((Integer) value).shortValue();
        }
        return (Short) value;
    }
    
}
