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

import pers.linhai.nature.j2ee.core.exception.ConditionFormatException;
import pers.linhai.nature.j2ee.core.exception.IllegalOperatorException;
import pers.linhai.nature.j2ee.core.model.Where.ConditionTemp;

/**
 * 抽象查询条件
 * <p>ClassName      : Condition</p>
 * @author lilinhai 2018年2月15日 下午3:39:28
 * @version 1.0
 */
public abstract class Condition
{

    private static final Map<String, Constructor<? extends Condition>> CONDITION_MAP = new HashMap<String, Constructor<? extends Condition>>();
    static
    {
        try
        {
            // 普通条件
            Class<CommonCondition> commonConditionClass = CommonCondition.class;
            Constructor<CommonCondition> commonConditionConstructor = commonConditionClass.getConstructor(ConditionTemp.class);
            commonConditionConstructor.setAccessible(true);
            
            CONDITION_MAP.put("<>", commonConditionConstructor);
            CONDITION_MAP.put("=", commonConditionConstructor);
            CONDITION_MAP.put("<=", commonConditionConstructor);
            CONDITION_MAP.put(">=", commonConditionConstructor);
            CONDITION_MAP.put("<", commonConditionConstructor);
            CONDITION_MAP.put(">", commonConditionConstructor);
            CONDITION_MAP.put("like", commonConditionConstructor);
            
            // in/not in条件
            Class<InCondition> inConditionClass = InCondition.class;
            Constructor<InCondition> inConditionConstructor = inConditionClass.getConstructor(ConditionTemp.class);
            inConditionConstructor.setAccessible(true);
            CONDITION_MAP.put("in", inConditionConstructor);
            CONDITION_MAP.put("not in", inConditionConstructor);
            
            // is null/is not null条件
            Class<NullValueCondition> nullValueConditionClass = NullValueCondition.class;
            Constructor<NullValueCondition> nullValueConditionConstructor = nullValueConditionClass.getConstructor(ConditionTemp.class);
            nullValueConditionConstructor.setAccessible(true);
            CONDITION_MAP.put("is null", nullValueConditionConstructor);
            CONDITION_MAP.put("is not null", nullValueConditionConstructor);
        }
        catch (Throwable e)
        {
            e.printStackTrace();
        }
    }
    
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
    private String jdbcType;

    /**
     * <p>Title        : Condition lilinhai 2018年2月15日 下午4:20:57</p>
     * @param fieldName 
     */ 
    public Condition(ConditionTemp conditionTemp)
    {
        this.fieldName = conditionTemp.getFieldName();
        setId(conditionTemp.getId());
        setJdbcType(conditionTemp.getJdbcType());
        setOperator(conditionTemp.getOperator());
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
    
    public static Condition parse(ConditionTemp conditionTemp)
    {
        if (conditionTemp.getOperator() == null)
        {
            throw new IllegalOperatorException("Exist null value of the param operator: " + conditionTemp.getOperator());
        }
        
        String operator = conditionTemp.getOperator().toLowerCase(Locale.ENGLISH);
        Constructor<? extends Condition> conditionConstructor = CONDITION_MAP.get(operator);
        if (conditionConstructor == null)
        {
            throw new IllegalOperatorException("Exist an illegal-operator: " + conditionTemp.getOperator());
        }
        
        conditionTemp.setOperator(operator);
        
        try
        {
            return conditionConstructor.newInstance(conditionTemp);
        }
        catch (Throwable e)
        {
            throw new ConditionFormatException(e);
        }
    }
}
