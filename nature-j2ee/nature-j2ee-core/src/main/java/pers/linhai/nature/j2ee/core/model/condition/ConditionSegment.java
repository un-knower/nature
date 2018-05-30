/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : Condition.java</p>
 * <p>Package     : com.meme.crm.model.core.condition</p>
 * @Creator lilinhai 2018年2月15日 下午3:39:28
 * @Version  V1.0  
 */

package pers.linhai.nature.j2ee.core.model.condition;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import pers.linhai.nature.j2ee.core.dao.exception.ConditionFormatException;
import pers.linhai.nature.j2ee.core.dao.exception.IllegalOperatorException;
import pers.linhai.nature.j2ee.core.model.Where.Condition;
import pers.linhai.nature.j2ee.core.model.enumer.Operator;

/**
 * 抽象查询条件
 * <p>ClassName      : Condition</p>
 * @author lilinhai 2018年2月15日 下午3:39:28
 * @version 1.0
 */
public abstract class ConditionSegment
{
    
    private static final Map<String, Constructor< ? extends ConditionSegment>> CONDITION_MAP = new HashMap<String, Constructor< ? extends ConditionSegment>>();
    static
    {
        try
        {
            // 普通条件
            Class<CommonConditionSegment> commonConditionClass = CommonConditionSegment.class;
            Constructor<CommonConditionSegment> commonConditionConstructor = commonConditionClass.getConstructor(Condition.class);
            commonConditionConstructor.setAccessible(true);
            
            CONDITION_MAP.put(Operator.NOT_EQUAL.getValue(), commonConditionConstructor);
            CONDITION_MAP.put(Operator.EQUAL.getValue(), commonConditionConstructor);
            CONDITION_MAP.put(Operator.LESS_THAN_OR_EQUAL.getValue(), commonConditionConstructor);
            CONDITION_MAP.put(Operator.GREATER_THAN_OR_EQUAL.getValue(), commonConditionConstructor);
            CONDITION_MAP.put(Operator.LESS_THAN.getValue(), commonConditionConstructor);
            CONDITION_MAP.put(Operator.GREATER_THAN.getValue(), commonConditionConstructor);
            CONDITION_MAP.put(Operator.LIKE.getValue(), commonConditionConstructor);
            
            // in/not in条件
            Class<InConditionSegment> inConditionClass = InConditionSegment.class;
            Constructor<InConditionSegment> inConditionConstructor = inConditionClass.getConstructor(Condition.class);
            inConditionConstructor.setAccessible(true);
            CONDITION_MAP.put(Operator.IN.getValue(), inConditionConstructor);
            CONDITION_MAP.put(Operator.NOT_IN.getValue(), inConditionConstructor);
            
            // is null/is not null条件
            Class<NullValueConditionSegment> nullValueConditionClass = NullValueConditionSegment.class;
            Constructor<NullValueConditionSegment> nullValueConditionConstructor = nullValueConditionClass.getConstructor(Condition.class);
            nullValueConditionConstructor.setAccessible(true);
            CONDITION_MAP.put(Operator.IS_NULL.getValue(), nullValueConditionConstructor);
            CONDITION_MAP.put(Operator.IS_NOT_NULL.getValue(), nullValueConditionConstructor);
        }
        catch (Throwable e)
        {
            e.printStackTrace();
        }
    }
    
    private int type;
    
    /**
     * 子查询条件ID
     */
    protected String id;
    
    /**
     * 字段名
     */
    protected String fieldName;
    
    /**
     * 运算符
     */
    protected String operator;
    
    /**
     * 该自定义条件字段对饮的JDBC类型
     */
    protected String jdbcType;
    
    /**
     * <p>Title        : Condition lilinhai 2018年2月15日 下午4:20:57</p>
     * @param fieldName 
     */
    protected ConditionSegment(Condition condition, int type)
    {
        this(type);
        this.fieldName = condition.getFieldName();
        setId(condition.getId());
        setJdbcType(condition.getJdbcType());
        setOperator(condition.getOperator());
    }
    
    protected ConditionSegment(int type)
    {
        this.type = type;
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
     * <p>Get Method   :   fieldName String</p>
     * @return fieldName
     */
    public String getFieldName()
    {
        return fieldName;
    }
    
    /**
     * <p>Set Method   :   fieldName String</p>
     * @param fieldName
     */
    public void setFieldName(String fieldName)
    {
        this.fieldName = fieldName;
    }

    /**
     * <p>Get Method   :   type int</p>
     * @return type
     */
    public int getType()
    {
        return type;
    }
    
    public abstract void initialize();
    
    public static ConditionSegment parse(Condition condition)
    {
        try
        {
            if (condition.getOperator() == null)
            {
                throw new IllegalOperatorException("Operator can't be null: " + condition);
            }
            
            String operator = condition.getOperator().toLowerCase(Locale.ENGLISH);
            Constructor< ? extends ConditionSegment> conditionConstructor = CONDITION_MAP.get(operator);
            if (conditionConstructor == null)
            {
                throw new IllegalOperatorException("Exist an illegal-operator: " + condition);
            }
            
            condition.setOperator(operator);
            return conditionConstructor.newInstance(condition);
        }
        catch (Throwable e)
        {
            throw new ConditionFormatException(e);
        }
    }
}
