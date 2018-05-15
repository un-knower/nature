/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : DataTypeParserMap.java</p>
 * <p>Package     : pers.linhai.nature.j2ee.core.model.datatypeparser</p>
 * @Creator lilinhai 2018年5月12日 上午11:00:20
 * @Version  V1.0  
 */ 

package pers.linhai.nature.j2ee.core.model.datatypeparser;

import java.util.HashMap;
import java.util.Map;

import pers.linhai.nature.j2ee.core.dao.exception.DataTypeException;

/**
 * 数据类型解析器Hash注册表
 * <p>ClassName      : DataTypeParserMap</p>
 * @author lilinhai 2018年5月12日 上午11:00:20
 * @version 1.0
 */
public abstract class DataTypeParserMap
{
    private static Map<Class<? extends DataTypeParser>, DataTypeParser> DATA_TYPE_PARSER_MAP = new HashMap<Class<? extends DataTypeParser>, DataTypeParser>();
    
    static
    {
        DATA_TYPE_PARSER_MAP.put(BinaryTypeParser.class, new BinaryTypeParser());
        DATA_TYPE_PARSER_MAP.put(BigDecimalTypeParser.class, new BigDecimalTypeParser());
        DATA_TYPE_PARSER_MAP.put(ByteTypeParser.class, new ByteTypeParser());
        DATA_TYPE_PARSER_MAP.put(ShortTypeParser.class, new ShortTypeParser());
        DATA_TYPE_PARSER_MAP.put(IntegerTypeParser.class, new IntegerTypeParser());
        DATA_TYPE_PARSER_MAP.put(LongTypeParser.class, new LongTypeParser());
        DATA_TYPE_PARSER_MAP.put(FloatTypeParser.class, new FloatTypeParser());
        DATA_TYPE_PARSER_MAP.put(DoubleTypeParser.class, new DoubleTypeParser());
        DATA_TYPE_PARSER_MAP.put(BooleanTypeParser.class, new BooleanTypeParser());
        DATA_TYPE_PARSER_MAP.put(StringTypeParser.class, new StringTypeParser());
        DATA_TYPE_PARSER_MAP.put(DateTypeParser.class, new DateTypeParser());
    }
    
    public static DataTypeParser get(Class<? extends DataTypeParser> dataTypeParserClass)
    {
        DataTypeParser dataTypeParser = DATA_TYPE_PARSER_MAP.get(dataTypeParserClass);
        if (dataTypeParser == null)
        {
            throw new DataTypeException("Illegal data type parser: " + dataTypeParserClass.getName());
        }
        return dataTypeParser;
    }
}
