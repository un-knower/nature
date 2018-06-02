/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : LogicalOperator.java</p>
 * <p>Package     : pers.linhai.nature.j2ee.core.model.enumer</p>
 * @Creator lilinhai 2018年5月20日 下午10:34:55
 * @Version  V1.0  
 */ 

package pers.linhai.nature.j2ee.core.model.enumer;

import java.util.HashMap;
import java.util.Map;

/**
 * 关联查询的管理类型
 * <p>ClassName      : LogicalOperator</p>
 * @author lilinhai 2018年5月20日 下午10:34:55
 * @version 1.0
 */
public enum JoinType
{

    /**
     * 左连接
     */
    LEFT_JOIN("leftJoin", "left join"),
    
    /**
     * 右连接
     */
    RIGHT_JOIN("rightJoin", "right join"),
    
    /**
     * 内连接
     */
    INNER_JOIN("rightJoin", "inner join");
    
    /**
     * 所有数据类型的hashmap映射，key为value
     */
    private static final Map<String, JoinType> TYPE_MAP = new HashMap<String, JoinType>();
    
    static
    {
        for (JoinType jt : values())
        {
            TYPE_MAP.put(jt.value, jt);
        }
    }
    
    /**
     * <p>Title        : LogicalOperator lilinhai 2018年2月10日 下午2:29:51</p>
     * @param value 
     */
    private JoinType(String value, String databaseValue)
    {
        this.value = value;
        this.databaseValue = databaseValue;
    }
    
    /**
     * 值
     */
    private String value;
    
    /**
     * 对应的书库库的值
     */
    private String databaseValue;
    
    /**
     * <p>Get Method   :   value String</p>
     * @return value
     */
    public String getValue()
    {
        return value;
    }
    
    /**
     * <p>Get Method   :   databaseValue String</p>
     * @return databaseValue
     */
    public String getDatabaseValue()
    {
        return databaseValue;
    }



    public static JoinType transfer(String value)
    {
        return TYPE_MAP.get(value);
    }
}
