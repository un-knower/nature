/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : JoinTable.java</p>
 * <p>Package     : pers.linhai.nature.j2ee.core.model.join</p>
 * @Creator lilinhai 2018年6月2日 下午5:00:32
 * @Version  V1.0  
 */ 

package pers.linhai.nature.j2ee.core.model.join;

/**
 * 关联表
 * <p>ClassName      : JoinTable</p>
 * @author lilinhai 2018年6月2日 下午5:00:32
 * @version 1.0
 */
public class JoinTable
{
    
    /**
     * 数据库表
     */
    private String table;
    
    /**
     * 列
     */
    private String column;
    
    /**
     * 数据库表对应的实体名
     */
    private String entity;
    
    /**
     * 排序字段名
     */
    private String field;

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
     * <p>Get Method   :   column String</p>
     * @return column
     */
    public String getColumn()
    {
        return column;
    }

    /**
     * <p>Set Method   :   column String</p>
     * @param column
     */
    public void setColumn(String column)
    {
        this.column = column;
    }

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
     * <p>Get Method   :   field String</p>
     * @return field
     */
    public String getField()
    {
        return field;
    }

    /**
     * <p>Set Method   :   field String</p>
     * @param field
     */
    public void setField(String field)
    {
        this.field = field;
    }

    /** 
     * <p>Overriding Method: lilinhai 2018年6月2日 下午5:02:34</p>
     * <p>Title: toString</p>
     * @return 
     * @see java.lang.Object#toString()
     */ 
    public String toString()
    {
        return "JoinTable [table=" + table + ", column=" + column + ", entity=" + entity + ", field=" + field + "]";
    }
}
