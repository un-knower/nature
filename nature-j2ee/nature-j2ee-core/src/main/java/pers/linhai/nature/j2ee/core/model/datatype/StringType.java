/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : StringType.java</p>
 * <p>Package     : com.meme.crm.model.core.datatype</p>
 * @Creator lilinhai 2018年2月15日 下午7:07:27
 * @Version  V1.0  
 */ 

package pers.linhai.nature.j2ee.core.model.datatype;

/**
 * <p>Description    : <pre>TODO(这里用一句话描述这个类的作用)</pre></p>
 * <p>ClassName      : StringType</p>
 * @author lilinhai 2018年2月15日 下午7:07:27
 * @version 1.0
 */
public class StringType extends DataType
{

    /** 
     * <p>Overriding Method: lilinhai 2018年2月15日 下午7:07:27</p>
     * <p>Title: parse</p>
     * @param value
     * @return 
     * @see com.meme.crm.model.core.datatype.DataType#parse(java.lang.String)
     */
    public Object parse(Object value)
    {
        return (String)value;
    }

}
