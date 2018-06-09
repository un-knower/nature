/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : Condition.java</p>
 * <p>Package     : pers.linhai.nature.j2ee.core.model</p>
 * @Creator lilinhai 2018年6月2日 下午5:05:42
 * @Version  V1.0  
 */ 

package pers.linhai.nature.j2ee.core.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * <p>Description    : <pre>TODO(这里用一句话描述这个类的作用)</pre></p>
 * <p>ClassName      : Condition</p>
 * @author lilinhai 2018年6月2日 下午5:05:42
 * @version 1.0
 */
public class Condition extends FieldModel
{

    /**
     * 子查询条件ID
     */
    private String id;
    
    /**
     * 运算符
     */
    private String operator;
    
    /**
     * 查询字段的值
     */
    private Object value;
    
    /**
     * 该自定义条件字段对饮的JDBC类型
     */
    @JsonIgnore
    private String jdbcType;
    
    /**
     * <p>Title        : Condition lilinhai 2018年5月19日 下午3:19:39</p>
     */ 
    public Condition()
    {
        
    }

    /**
     * <p>Get Method   :   id String</p>
     * @return id
     */
    public String getId()
    {
        return id;
    }
    
    /**
     * <p>Set Method   :   id String</p>
     * @param id
     */
    public void setId(String id)
    {
        this.id = id;
    }
    
    /**
     * <p>Set Method   :   fieldName String</p>
     * please use setField
     * @param fieldName
     */
    @Deprecated
    public void setFieldName(String fieldName)
    {
        setField(fieldName);
    }
    
    /**
     * <p>Get Method   :   operator String</p>
     * @return operator
     */
    public String getOperator()
    {
        return operator;
    }
    
    /**
     * <p>Set Method   :   operator String</p>
     * @param operator
     */
    public void setOperator(String operator)
    {
        this.operator = operator;
    }
    
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
     * <p>Overriding Method: lilinhai 2018年6月2日 下午5:30:35</p>
     * <p>Title: toString</p>
     * @return 
     * @see java.lang.Object#toString()
     */ 
    public String toString()
    {
        return "Condition [id=" + id + ", operator=" + operator + ", value=" + value + ", jdbcType=" + jdbcType + ", toString()=" + super.toString() + "]";
    }
}
