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
import pers.linhai.nature.j2ee.core.model.Where.Condition;

/**
 * 抽象查询条件
 * <p>ClassName      : Condition</p>
 * @author lilinhai 2018年2月15日 下午3:39:28
 * @version 1.0
 */
public abstract class ConditionSegment
{

    private static final Map<String, Constructor<? extends ConditionSegment>> CONDITION_MAP = new HashMap<String, Constructor<? extends ConditionSegment>>();
    static
    {
        try
        {
            // 普通条件
            Class<CommonConditionSegment> commonConditionClass = CommonConditionSegment.class;
            Constructor<CommonConditionSegment> commonConditionConstructor = commonConditionClass.getConstructor(Condition.class);
            commonConditionConstructor.setAccessible(true);
            
            CONDITION_MAP.put("<>", commonConditionConstructor);
            CONDITION_MAP.put("=", commonConditionConstructor);
            CONDITION_MAP.put("<=", commonConditionConstructor);
            CONDITION_MAP.put(">=", commonConditionConstructor);
            CONDITION_MAP.put("<", commonConditionConstructor);
            CONDITION_MAP.put(">", commonConditionConstructor);
            CONDITION_MAP.put("like", commonConditionConstructor);
            
            // in/not in条件
            Class<InConditionSegment> inConditionClass = InConditionSegment.class;
            Constructor<InConditionSegment> inConditionConstructor = inConditionClass.getConstructor(Condition.class);
            inConditionConstructor.setAccessible(true);
            CONDITION_MAP.put("in", inConditionConstructor);
            CONDITION_MAP.put("not in", inConditionConstructor);
            
            // is null/is not null条件
            Class<NullValueConditionSegment> nullValueConditionClass = NullValueConditionSegment.class;
            Constructor<NullValueConditionSegment> nullValueConditionConstructor = nullValueConditionClass.getConstructor(Condition.class);
            nullValueConditionConstructor.setAccessible(true);
            CONDITION_MAP.put("is null", nullValueConditionConstructor);
            CONDITION_MAP.put("is not null", nullValueConditionConstructor);
        }
        catch (Throwable e)
        {
            e.printStackTrace();
        }
    }
    
    protected int type;
    
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
    protected ConditionSegment(Condition conditionTemp)
    {
        this.fieldName = conditionTemp.getFieldName();
        setId(conditionTemp.getId());
        setJdbcType(conditionTemp.getJdbcType());
        setOperator(conditionTemp.getOperator());
    }
    
    protected ConditionSegment()
    {
        
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
     * <p>Get Method   :   type int</p>
     * @return type
     */
    public int getType()
    {
        return type;
    }

    public static ConditionSegment parse(Condition conditionBean)
    {
        if (conditionBean.getOperator() == null)
        {
            throw new IllegalOperatorException("Exist null value of the param operator: " + conditionBean.getOperator());
        }
        
        String operator = conditionBean.getOperator().toLowerCase(Locale.ENGLISH);
        Constructor<? extends ConditionSegment> conditionConstructor = CONDITION_MAP.get(operator);
        if (conditionConstructor == null)
        {
            throw new IllegalOperatorException("Exist an illegal-operator: " + conditionBean.getOperator());
        }
        
        conditionBean.setOperator(operator);
        
        try
        {
            return conditionConstructor.newInstance(conditionBean);
        }
        catch (Throwable e)
        {
            throw new ConditionFormatException(e);
        }
    }
}
