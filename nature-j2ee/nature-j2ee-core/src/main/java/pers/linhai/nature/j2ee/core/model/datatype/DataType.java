/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : DataType.java</p>
 * <p>Package     : com.meme.crm.model.core.datatype</p>
 * @Creator lilinhai 2018年2月15日 下午6:53:37
 * @Version  V1.0  
 */

package pers.linhai.nature.j2ee.core.model.datatype;

import java.util.HashMap;
import java.util.Map;

import pers.linhai.nature.j2ee.core.exception.DataTypeException;

/**
 * 数据库DB抽象数据类型
 * <p>ClassName      : DataType</p>
 * @author lilinhai 2018年2月15日 下午6:53:37
 * @version 1.0
 */
public abstract class DataType
{
    
    private static Map<String, DataType> DATA_TYPE_MAP = new HashMap<String, DataType>();
    static
    {
        ByteType byteType = new ByteType();
        ShortType shortType = new ShortType();
        IntegerType integerType = new IntegerType();
        LongType longType = new LongType();
        FloatType floatType = new FloatType();
        DoubleType doubleType = new DoubleType();
        BooleanType booleanType = new BooleanType();
        StringType stringType = new StringType();
        DateType dateType = new DateType();
        
        DATA_TYPE_MAP.put("TINYINT", byteType);
        DATA_TYPE_MAP.put("SMALLINT", shortType);
        DATA_TYPE_MAP.put("INTEGER", integerType);
        DATA_TYPE_MAP.put("BIGINT", longType);
        DATA_TYPE_MAP.put("floatType", floatType);
        DATA_TYPE_MAP.put("doubleType", doubleType);
        DATA_TYPE_MAP.put("BIT", booleanType);
        DATA_TYPE_MAP.put("VARCHAR", stringType);
        DATA_TYPE_MAP.put("TIMESTAMP", dateType);
    }
    
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
        DataType dataType = DATA_TYPE_MAP.get(jdbcType);
        if (dataType == null)
        {
            throw new DataTypeException("DataType is not supported: " + jdbcType);
        }
        return dataType.parse(value);
    }
}
