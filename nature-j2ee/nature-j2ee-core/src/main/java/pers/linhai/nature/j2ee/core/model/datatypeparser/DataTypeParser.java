/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : DataType.java</p>
 * <p>Package     : com.meme.crm.model.core.datatype</p>
 * @Creator lilinhai 2018年2月15日 下午6:53:37
 * @Version  V1.0  
 */

package pers.linhai.nature.j2ee.core.model.datatypeparser;

import pers.linhai.nature.j2ee.core.dao.exception.DataTypeException;
import pers.linhai.nature.j2ee.core.model.enumer.JdbcType;

/**
 * 数据库DB抽象数据类型
 * <p>ClassName      : DataType</p>
 * @author lilinhai 2018年2月15日 下午6:53:37
 * @version 1.0
 */
public abstract class DataTypeParser
{
    /**
     * 根据不同类型解析返回对象
     * <p>Title         : parse lilinhai 2018年2月15日 下午6:57:50</p>
     * @param value
     * @return 
     * Object
     */
    public abstract Object parse(Object value);
    
    /**
     * 
     * <p>Title         : parse lilinhai 2018年2月15日 下午7:00:23</p>
     * @param jdbcType
     * @param value
     * @return 
     * Object
     */
    public static Object parse(String jdbcType, Object value)
    {
        if (jdbcType == null)
        {
            throw new DataTypeException("DataType can't be null!!");
        }
        DataTypeParser dataType = JdbcType.transfer(jdbcType).getDataTypeParser();
        if (dataType == null)
        {
            throw new DataTypeException("DataType is not supported: " + jdbcType);
        }
        return dataType.parse(value);
    }
}
