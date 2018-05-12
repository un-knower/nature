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
public class BooleanTypeParser extends DataTypeParser
{
    
    /**
     * <p>Overriding Method: lilinhai 2018年5月5日 下午3:34:22</p>
     * <p>Title: parse</p>
     * @param value
     * @return 
     * @see pers.linhai.nature.j2ee.core.model.datatypeparser.DataTypeParser#parse(java.lang.Object)
     */
    public Object parse(Object value)
    {
        if (value instanceof String)
        {
            return Boolean.parseBoolean(value.toString());
        }
        return (Boolean) value;
    }
}
