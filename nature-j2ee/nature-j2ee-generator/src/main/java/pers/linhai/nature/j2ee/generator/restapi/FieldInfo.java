/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : FieldInfo.java</p>
 * <p>Package     : pers.linhai.nature.j2ee.generator.restapi</p>
 * @Creator lilinhai 2018年4月1日 下午9:01:01
 * @Version  V1.0  
 */ 

package pers.linhai.nature.j2ee.generator.restapi;

import pers.linhai.nature.j2ee.generator.core.api.IntrospectedColumn;

/**
 * <p>Description    : <pre>TODO(这里用一句话描述这个类的作用)</pre></p>
 * <p>ClassName      : FieldInfo</p>
 * @author lilinhai 2018年4月1日 下午9:01:01
 * @version 1.0
 */
public class FieldInfo
{

    /**
     * java字段名
     */
    private String javaFieldName;
    
    /**
     * 数据库表字段名
     */
    private String tableFieldName;
    
    /**
     * 字段描述
     */
    private String fieldDesc;
    
    /**
     * 数据库字段类型
     */
    private String jdbcType;
    
    /**
     * <p>Title : FieldInfo lilinhai 2018年4月1日 下午9:39:29</p>
     */ 
    public FieldInfo(IntrospectedColumn introspectedColumn)
    {
        this.javaFieldName = introspectedColumn.getJavaProperty();
        this.tableFieldName = introspectedColumn.getActualColumnName();
        this.jdbcType = introspectedColumn.getJdbcTypeName();
        this.fieldDesc = introspectedColumn.getRemarks();
        if (this.fieldDesc == null)
        {
            this.fieldDesc = "警告：该字段在数据库表中未加注释，请加上！";
        }
    }

    /**
     * <p>Get Method   :   javaFieldName String</p>
     * @return javaFieldName
     */
    public String getJavaFieldName()
    {
        return javaFieldName;
    }

    /**
     * <p>Set Method   :   javaFieldName String</p>
     * @param javaFieldName
     */
    public void setJavaFieldName(String javaFieldName)
    {
        this.javaFieldName = javaFieldName;
    }

    /**
     * <p>Get Method   :   tableFieldName String</p>
     * @return tableFieldName
     */
    public String getTableFieldName()
    {
        return tableFieldName;
    }

    /**
     * <p>Set Method   :   tableFieldName String</p>
     * @param tableFieldName
     */
    public void setTableFieldName(String tableFieldName)
    {
        this.tableFieldName = tableFieldName;
    }

    /**
     * <p>Get Method   :   fieldDesc String</p>
     * @return fieldDesc
     */
    public String getFieldDesc()
    {
        return fieldDesc;
    }

    /**
     * <p>Set Method   :   fieldDesc String</p>
     * @param fieldDesc
     */
    public void setFieldDesc(String fieldDesc)
    {
        this.fieldDesc = fieldDesc;
    }

    /**
     * <p>Get Method   :   jdbcType String</p>
     * @return jdbcType
     */
    public String getJdbcType()
    {
        return jdbcType;
    }

    /**
     * <p>Set Method   :   jdbcType String</p>
     * @param jdbcType
     */
    public void setJdbcType(String jdbcType)
    {
        this.jdbcType = jdbcType;
    }
}
