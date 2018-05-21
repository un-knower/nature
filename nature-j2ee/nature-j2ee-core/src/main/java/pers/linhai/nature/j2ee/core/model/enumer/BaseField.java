package pers.linhai.nature.j2ee.core.model.enumer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import pers.linhai.nature.j2ee.core.model.ModelField;
import pers.linhai.nature.j2ee.core.model.enumer.JdbcType;
import pers.linhai.nature.utils.NamingUtils;

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
    private static final List<String> TABLE_FIELD_LIST = new ArrayList<String>();

    /**
     * java实体所有字段名集合
     */
    private static final List<String> JAVA_FIELD_LIST = new ArrayList<String>();

    /**
     * 对应的java实体字段名
     */
    private String javaField;

    /**
     * 对应的table数据库表字段名
     */
    private String tableField;

    /**
     * 字段对应的JDBC类型
     */
    private JdbcType jdbcType;

    static 
    {
        for (BaseField field : values())
        {
            MAP.put(field.getJavaField(), field);
            MAP.put(field.getTableField(), field);
            TABLE_FIELD_LIST.add(field.getTableField());
            JAVA_FIELD_LIST.add(field.getJavaField());
        }
    }

    BaseField(JdbcType jdbcType)
    {
        this.tableField = name().toLowerCase();
        this.javaField = NamingUtils.getCamelCaseString(tableField, false);
        this.jdbcType = jdbcType;
    }

    public String getJavaField()
    {
        return javaField;
    }

    public String getTableField()
    {
        return tableField;
    }

    public String getJdbcType()
    {
        return jdbcType.getType();
    }

    public static BaseField transfer(String javaField)
    {
        return MAP.get(javaField);
    }

    public static List<String> getTableFieldList()
    {
        return TABLE_FIELD_LIST;
    }

    public static List<String> getJavaFieldList()
    {
        return JAVA_FIELD_LIST;
    }
}