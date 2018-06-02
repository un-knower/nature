/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : SelectField.java</p>
 * <p>Package     : pers.linhai.nature.j2ee.core.model.join</p>
 * @Creator lilinhai 2018年6月2日 下午4:58:03
 * @Version  V1.0  
 */ 

package pers.linhai.nature.j2ee.core.model.join;

import java.util.List;

/**
 * 关联查询的字段信息
 * <p>ClassName      : SelectField</p>
 * @author lilinhai 2018年6月2日 下午4:58:03
 * @version 1.0
 */
public class SelectField
{

    private String entity;
    
    private List<String> fieldList;
    
    private String table;
    
    private List<String> columnList;

    /**
     * <p>Get Method   :   entity String</p>
     * @return entity
     */
    public String getEntity()
    {
        return entity;
    }

    /**
     * <p>Set Method   :   entity String</p>
     * @param entity
     */
    public void setEntity(String entity)
    {
        this.entity = entity;
    }

    /**
     * <p>Get Method   :   fieldList List<String></p>
     * @return fieldList
     */
    public List<String> getFieldList()
    {
        return fieldList;
    }

    /**
     * <p>Set Method   :   fieldList List<String></p>
     * @param fieldList
     */
    public void setFieldList(List<String> fieldList)
    {
        this.fieldList = fieldList;
    }

    /**
     * <p>Get Method   :   table String</p>
     * @return table
     */
    public String getTable()
    {
        return table;
    }

    /**
     * <p>Set Method   :   table String</p>
     * @param table
     */
    public void setTable(String table)
    {
        this.table = table;
    }

    /**
     * <p>Get Method   :   columnList List<String></p>
     * @return columnList
     */
    public List<String> getColumnList()
    {
        return columnList;
    }

    /**
     * <p>Set Method   :   columnList List<String></p>
     * @param columnList
     */
    public void setColumnList(List<String> columnList)
    {
        this.columnList = columnList;
    }

    /** 
     * <p>Overriding Method: lilinhai 2018年6月2日 下午4:33:28</p>
     * <p>Title: toString</p>
     * @return 
     * @see java.lang.Object#toString()
     */ 
    public String toString()
    {
        return "SelectField [entity=" + entity + ", fieldList=" + fieldList + ", table=" + table + ", columnList=" + columnList + "]";
    }

}
