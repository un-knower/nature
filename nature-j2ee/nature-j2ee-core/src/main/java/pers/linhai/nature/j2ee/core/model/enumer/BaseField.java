package pers.linhai.nature.j2ee.core.model.enumer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import pers.linhai.nature.j2ee.core.model.ModelField;
import pers.linhai.nature.j2ee.core.model.enumer.JdbcType;
import pers.linhai.nature.utils.NamerUtils;

/**
 * 数据库表公共字段枚举
 */
public enum BaseField implements ModelField
{
    ID(JdbcType.BIGINT),
    CREATE_TIME(JdbcType.TIMESTAMP),
    UPDATE_TIME(JdbcType.TIMESTAMP);

    /**
     * 所有字段的hashmap映射
     */
    private static final Map<String, BaseField> MAP = new HashMap<String, BaseField>();

    /**
     * 数据库表所有字段名集合
     */
    private static final List<String> COLUMN_LIST = new ArrayList<String>();

    /**
     * java实体所有字段名集合
     */
    private static final List<String> FIELD_LIST = new ArrayList<String>();

    /**
     * 对应的java实体字段名
     */
    private String field;

    /**
     * 对应的table数据库表字段名
     */
    private String column;

    /**
     * 字段对应的JDBC类型
     */
    private JdbcType jdbcType;

    static 
    {
        for (BaseField field : values())
        {
            MAP.put(field.getField(), field);
            MAP.put(field.getColumn(), field);
            COLUMN_LIST.add(field.getColumn());
            FIELD_LIST.add(field.getField());
        }
    }

    BaseField(JdbcType jdbcType)
    {
        this.column = name().toLowerCase();
        this.field = NamerUtils.columnToProterty(column);
        this.jdbcType = jdbcType;
    }
    
    /** 
     * <p>Overriding Method: lilinhai 2018年6月5日 下午4:46:38</p>
     * <p>Title: getEntity</p>
     * @return 
     * @see pers.linhai.nature.j2ee.core.model.ModelField#getEntity()
     */ 
    @Override
    public String getEntity()
    {
        return null;
    }

    /** 
     * <p>Overriding Method: lilinhai 2018年6月5日 下午4:46:38</p>
     * <p>Title: getTable</p>
     * @return 
     * @see pers.linhai.nature.j2ee.core.model.ModelField#getTable()
     */ 
    public String getTable()
    {
        return null;
    }

    public String getField()
    {
        return field;
    }

    public String getColumn()
    {
        return column;
    }

    public String getJdbcType()
    {
        return jdbcType.getType();
    }

    public static BaseField transfer(String javaField)
    {
        return MAP.get(javaField);
    }

    public static List<String> columnList()
    {
        return COLUMN_LIST;
    }

    public static List<String> fieldList()
    {
        return FIELD_LIST;
    }
}