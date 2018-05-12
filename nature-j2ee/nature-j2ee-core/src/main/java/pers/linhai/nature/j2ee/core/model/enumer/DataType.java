/*
 * <p>Copyright : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p> <p>FileName : DataTypes.java</p> <p>Package :
 * pers.linhai.nature.j2ee.core.model.datatype</p>
 * @Creator lilinhai 2018年5月12日 上午9:08:19
 * @Version V1.0
 */

package pers.linhai.nature.j2ee.core.model.enumer;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import pers.linhai.nature.j2ee.core.exception.DataTypeException;
import pers.linhai.nature.j2ee.core.model.datatypeparser.BigDecimalTypeParser;
import pers.linhai.nature.j2ee.core.model.datatypeparser.BinaryTypeParser;
import pers.linhai.nature.j2ee.core.model.datatypeparser.BooleanTypeParser;
import pers.linhai.nature.j2ee.core.model.datatypeparser.DataTypeParser;
import pers.linhai.nature.j2ee.core.model.datatypeparser.DataTypeParserMap;
import pers.linhai.nature.j2ee.core.model.datatypeparser.DateTypeParser;
import pers.linhai.nature.j2ee.core.model.datatypeparser.DoubleTypeParser;
import pers.linhai.nature.j2ee.core.model.datatypeparser.FloatTypeParser;
import pers.linhai.nature.j2ee.core.model.datatypeparser.IntegerTypeParser;
import pers.linhai.nature.j2ee.core.model.datatypeparser.LongTypeParser;
import pers.linhai.nature.j2ee.core.model.datatypeparser.StringTypeParser;

/**
 * 数据类型
 * <p>ClassName      : DataTypes</p>
 * @author lilinhai 2018年5月12日 上午9:08:19
 * @version 1.0
 */
public enum DataType
{
    /**
     * ARRAY类型
     */
    //ARRAY(Types.ARRAY, "ARRAY", null),
    
    /**
     * BIGINT类型
     */
    BIGINT(Types.BIGINT, "BIGINT", LongTypeParser.class),
    
    /**
     * BINARY类型，定长的字节流数组，类似char和varchar的关系，一个定长，一个变长
     */
    BINARY(Types.BINARY, "BINARY", BinaryTypeParser.class),
    
    /**
     * BIT类型
     */
    BIT(Types.BIT, "BIT", BooleanTypeParser.class),
    
    /**
     * BLOB类型
     */
    BLOB(Types.BLOB, "BLOB", BinaryTypeParser.class),
    
    /**
     * BOOLEAN类型
     */
    BOOLEAN(Types.BOOLEAN, "BOOLEAN", BooleanTypeParser.class),
    
    /**
     * CHAR类型
     */
    CHAR(Types.CHAR, "CHAR", StringTypeParser.class),
    
    /**
     * CLOB类型
     */
    CLOB(Types.CLOB, "CLOB", StringTypeParser.class),
    
    /**
     * DATALINK类型
     */
    //DATALINK(Types.DATALINK, "DATALINK", null),
    
    /**
     * DATE类型
     */
    DATE(Types.DATE, "DATE", DateTypeParser.class),
    
    /**
     * DECIMAL类型
     */
    DECIMAL(Types.DECIMAL, "DECIMAL", BigDecimalTypeParser.class),
    
    /**
     * DISTINCT类型
     */
    //DISTINCT(Types.DISTINCT, "DISTINCT", null),
    
    /**
     * DOUBLE类型
     */
    DOUBLE(Types.DOUBLE, "DOUBLE", DoubleTypeParser.class),
    
    /**
     * FLOAT类型
     */
    FLOAT(Types.FLOAT, "FLOAT", DoubleTypeParser.class),
    
    /**
     * INTEGER类型
     */
    INTEGER(Types.INTEGER, "INTEGER", IntegerTypeParser.class),
    
    /**
     * JAVA_OBJECT类型
     */
    //JAVA_OBJECT(Types.JAVA_OBJECT, "JAVA_OBJECT", null),
    
    /**
     * LONGVARBINARY类型
     */
    LONGVARBINARY(Types.LONGVARBINARY, "LONGVARBINARY", BinaryTypeParser.class),
    
    /**
     * LONGVARCHAR类型
     */
    LONGVARCHAR(Types.LONGVARCHAR, "LONGVARCHAR", StringTypeParser.class),
    
    /**
     * NCHAR类型
     */
    NCHAR(Types.NCHAR, "NCHAR", StringTypeParser.class),
    
    /**
     * NCLOB类型
     */
    NCLOB(Types.NCLOB, "NCLOB", StringTypeParser.class),
    
    /**
     * NVARCHAR类型
     */
    NVARCHAR(Types.NVARCHAR, "NVARCHAR", StringTypeParser.class),
    
    /**
     * LONGNVARCHAR类型
     */
    LONGNVARCHAR(Types.LONGNVARCHAR, "LONGNVARCHAR", StringTypeParser.class),
    
    /**
     * NULL类型
     */
    //NULL(Types.NULL, "NULL", null),
    
    /**
     * NUMERIC类型
     */
    NUMERIC(Types.NUMERIC, "NUMERIC", BigDecimalTypeParser.class),
    
    /**
     * OTHER类型
     */
    //OTHER(Types.OTHER, "OTHER", null),
    
    /**
     * REAL类型
     */
    REAL(Types.REAL, "REAL", FloatTypeParser.class),
    
    /**
     * REF类型
     */
    //REF(Types.REF, "REF", null),
    
    /**
     * SMALLINT类型
     */
    SMALLINT(Types.SMALLINT, "SMALLINT", IntegerTypeParser.class),
    
    /**
     * STRUCT类型
     */
    //STRUCT(Types.STRUCT, "STRUCT", null),
    
    /**
     * TIME类型
     */
    TIME(Types.TIME, "TIME", DateTypeParser.class),
    
    /**
     * TIMESTAMP类型
     */
    TIMESTAMP(Types.TIMESTAMP, "TIMESTAMP", DateTypeParser.class),
    
    /**
     * TINYINT类型
     */
    TINYINT(Types.TINYINT, "TINYINT", IntegerTypeParser.class),
    
    /**
     * VARBINARY类型，变长的字节流数组
     */
    VARBINARY(Types.VARBINARY, "VARBINARY", BinaryTypeParser.class),
    
    /**
     * VARCHAR类型
     */
    VARCHAR(Types.VARCHAR, "VARCHAR", StringTypeParser.class);
    
    /**
     * 所有数据类型的hashmap映射，key为code
     */
    private static final Map<Integer, DataType> CODE_MAP = new HashMap<Integer, DataType>();
    
    /**
     * 所有数据类型的hashmap映射，key为type
     */
    private static final Map<String, DataType> TYPE_MAP = new HashMap<String, DataType>();
    
    static
    {
        for (DataType dt : values())
        {
            CODE_MAP.put(dt.code, dt);
            TYPE_MAP.put(dt.type, dt);
        }
    }
    
    /**
     * 构造函数
     * <p>Title        : DataTypes lilinhai 2018年5月12日 上午10:55:35</p>
     * @param code
     * @param type
     * @param dataTypeParser 
     */ 
    DataType(int code, String type, Class<? extends DataTypeParser> dataTypeParserClass)
    {
        this.code = code;
        this.type = type;
        this.dataTypeParser = DataTypeParserMap.get(dataTypeParserClass);
    }

    /**
     * type code, that identifies the generic SQL type
     */
    private int code;
    
    /**
     * 对应数据库的类型
     */
    private String type;
    
    /**
     * 数据类型解析器
     */
    private DataTypeParser dataTypeParser;

    /**
     * <p>Get Method   :   code int</p>
     * @return code
     */
    public int getCode()
    {
        return code;
    }

    /**
     * <p>Get Method   :   type String</p>
     * @return type
     */
    public String getType()
    {
        return type;
    }

    /**
     * <p>Get Method   :   dataTypeParser DataTypeParser</p>
     * @return dataTypeParser
     */
    public DataTypeParser getDataTypeParser()
    {
        return dataTypeParser;
    }
    
    /**
     * 转换
     * <p>Title         : transfer lilinhai 2018年5月12日 下午8:30:25</p>
     * @param code
     * @return 
     * DataType
     */
    public static DataType transfer(int code)
    {
        DataType dt = CODE_MAP.get(code);
        if (dt == null)
        {
            throw new DataTypeException("Illegal data type code: " + code);
        }
        return dt;
    }
    
    /**
     * 转换
     * <p>Title         : transfer lilinhai 2018年5月12日 下午8:30:37</p>
     * @param type
     * @return 
     * DataType
     */
    public static DataType transfer(String type)
    {
        DataType dt = TYPE_MAP.get(type);
        if (dt == null)
        {
            throw new DataTypeException("Illegal data type: " + type);
        }
        return dt;
    }
}
