/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : ConditionBuilder.java</p>
 * <p>Package     : pers.linhai.nature.j2ee.core.model.condition</p>
 * @Creator lilinhai 2018年5月15日 下午6:18:39
 * @Version  V1.0  
 */ 

package pers.linhai.nature.j2ee.core.model;

import java.util.ArrayList;
import java.util.List;

import pers.linhai.nature.j2ee.core.model.Where.Condition;
import pers.linhai.nature.j2ee.core.model.enumer.Operator;

/**
 * 查询条件构建器
 * <p>ClassName      : ConditionBuilder</p>
 * @author lilinhai 2018年5月15日 下午6:18:39
 * @version 1.0
 */
public class ConditionBuilder
{
    
    /**
     * 自定义条件对象
     */
    public Condition condition;
    
    /**
     * <p>Title        : ConditionBuilder lilinhai 2018年5月15日 下午6:24:46</p>
     * @param condition 
     */ 
    private ConditionBuilder(ModelField modelField)
    {
        this(modelField.getJavaField());
    }
    
    /**
     * <p>Title        : ConditionBuilder lilinhai 2018年5月15日 下午6:24:46</p>
     * @param condition 
     */ 
    private ConditionBuilder(String fieldName)
    {
        condition = new Condition();
        condition.setFieldName(fieldName);
    }

    /**
     * 通过传入字段名初始化一个条件构建器ConditionBuilder
     * <p>Title         : field lilinhai 2018年5月15日 下午6:23:45</p>
     * @param modelField
     * @return 
     * ConditionBuilder
     */
    public static ConditionBuilder field(ModelField modelField)
    {
        return new ConditionBuilder(modelField);
    }
    
    /**
     * 通过传入字段名初始化一个条件构建器ConditionBuilder
     * <p>Title         : field lilinhai 2018年5月15日 下午6:23:45</p>
     * @param fieldName
     * @return 
     * ConditionBuilder
     */
    public static ConditionBuilder field(String fieldName)
    {
        return new ConditionBuilder(fieldName);
    }
    
    /**
     * 小于
     * <p>Title         : lessThan lilinhai 2018年5月15日 下午6:22:13</p>
     * @param value 
     * void
     */
    public final Condition lessThan(Object value)
    {
        condition.setOperator(Operator.LESS_THAN.getValue());
        condition.setValue(value);
        return condition;
    }
    
    /**
     * 大于
     * <p>Title         : greaterThan lilinhai 2018年5月15日 下午6:22:13</p>
     * @param value 
     * void
     */
    public final Condition greaterThan(Object value)
    {
        condition.setOperator(Operator.GREATER_THAN.getValue());
        condition.setValue(value);
        return condition;
    }
    
    /**
     * 小于或等于
     * <p>Title         : lessThanOrEqual lilinhai 2018年5月15日 下午6:22:13</p>
     * @param value 
     * void
     */
    public final Condition lessThanOrEqual(Object value)
    {
        condition.setOperator(Operator.LESS_THAN_OR_EQUAL.getValue());
        condition.setValue(value);
        return condition;
    }
    
    /**
     * 大于或等于
     * <p>Title         : greaterThanOrEqual lilinhai 2018年5月15日 下午6:22:13</p>
     * @param value 
     * void
     */
    public final Condition greaterThanOrEqual(Object value)
    {
        condition.setOperator(Operator.GREATER_THAN_OR_EQUAL.getValue());
        condition.setValue(value);
        return condition;
    }
    
    /**
     * 大于或等于
     * <p>Title         : equal lilinhai 2018年5月15日 下午6:22:13</p>
     * @param value 
     * void
     */
    public final Condition equal(Object value)
    {
        condition.setOperator(Operator.EQUAL.getValue());
        condition.setValue(value);
        return condition;
    }
    
    /**
     * 不等于
     * <p>Title         : noeEqual lilinhai 2018年5月15日 下午6:22:13</p>
     * @param value 
     * void
     */
    public final Condition notEqual(Object value)
    {
        condition.setOperator(Operator.NOT_EQUAL.getValue());
        condition.setValue(value);
        return condition;
    }
    
    /**
     * 像
     * <p>Title         : like lilinhai 2018年5月15日 下午6:22:13</p>
     * @param value 
     * void
     */
    public final Condition like(Object value)
    {
        condition.setOperator(Operator.LIKE.getValue());
        condition.setValue(value);
        return condition;
    }
    
    /**
     * 包含
     * <p>Title         : in lilinhai 2018年5月15日 下午6:22:13</p>
     * @param value 
     * void
     */
    public final <T> Condition in(List<T> valueList)
    {
        condition.setOperator(Operator.IN.getValue());
        condition.setValue(valueList);
        return condition;
    }
    
    /**
     * 包含
     * <p>Title         : in lilinhai 2018年5月15日 下午6:22:13</p>
     * @param value 
     * void
     */
    @SafeVarargs
    public final <T> Condition in(T... values)
    {
        List<T> objList = new ArrayList<T>();
        for (T object : values)
        {
            objList.add(object);
        }
        return in(objList);
    }
    
    /**
     * 不包含
     * <p>Title         : in lilinhai 2018年5月15日 下午6:22:13</p>
     * @param value 
     * void
     */
    public final <T> Condition notIn(List<T> valueList)
    {
        condition.setOperator(Operator.NOT_IN.getValue());
        condition.setValue(valueList);
        return condition;
    }
    
    /**
     * 不包含
     * <p>Title         : in lilinhai 2018年5月15日 下午6:22:13</p>
     * @param value 
     * void
     */
    @SafeVarargs
    public final <T> Condition notIn(T... values)
    {
        List<Object> objList = new ArrayList<Object>();
        for (Object object : values)
        {
            objList.add(object);
        }
        return notIn(objList);
    }
    
    /**
     * 为空
     * <p>Title         : isNull lilinhai 2018年5月15日 下午6:22:13</p>
     * @param value 
     * void
     */
    public final Condition isNull()
    {
        condition.setOperator(Operator.IS_NULL.getValue());
        return condition;
    }
    
    /**
     * 不为空
     * <p>Title         : in lilinhai 2018年5月15日 下午6:22:13</p>
     * @param value 
     * void
     */
    public final Condition isNotNull()
    {
        condition.setOperator(Operator.IS_NOT_NULL.getValue());
        return condition;
    }
}
