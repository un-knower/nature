/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : PersistentField.java</p>
 * <p>Package     : pers.linhai.nature.j2ee.core.model</p>
 * @Creator lilinhai 2018年3月13日 上午9:27:11
 * @Version  V1.0  
 */

package pers.linhai.nature.j2ee.core.model;

/**
 * <p>Description    : <pre>TODO(这里用一句话描述这个类的作用)</pre></p>
 * <p>ClassName      : PersistentField</p>
 * @author lilinhai 2018年3月13日 上午9:27:11
 * @version 1.0
 */
public class PersistentField
{
    
    /**
     * 数据库表实体的字段名
     */
    private String column;
    
    /**
     * 查询字段的值
     */
    private Object value;
    
    /**
     * 该自定义条件字段对饮的JDBC类型
     */
    private String jdbcType;
    
    /**
     * <p>Get Method   :   value String</p>
     * @return value
     */
    public Object getValue()
    {
        return value;
    }
    
    /**
     * <p>Set Method   :   value String</p>
     * @param value
     */
    public void setValue(Object value)
    {
        this.value = value;
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
     * <p>Overriding Method: lilinhai 2018年6月2日 下午3:12:37</p>
     * <p>Title: hashCode</p>
     * <p>Description: TODO</p>
     * @return 
     * @see java.lang.Object#hashCode()
     */ 
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((column == null) ? 0 : column.hashCode());
        return result;
    }

    /** 
     * <p>Overriding Method: lilinhai 2018年6月2日 下午3:12:37</p>
     * <p>Title: equals</p>
     * <p>Description: TODO</p>
     * @param obj
     * @return 
     * @see java.lang.Object#equals(java.lang.Object)
     */ 
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        PersistentField other = (PersistentField) obj;
        if (column == null)
        {
            if (other.column != null) return false;
        }
        else if (!column.equals(other.column)) return false;
        return true;
    }

    /** 
     * <p>Overriding Method: lilinhai 2018年6月2日 下午3:12:39</p>
     * <p>Title: toString</p>
     * <p>Description: TODO</p>
     * @return 
     * @see java.lang.Object#toString()
     */ 
    public String toString()
    {
        return "PersistentField [column=" + column + ", value=" + value + ", jdbcType=" + jdbcType + "]";
    }
}
