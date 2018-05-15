/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : ConditionBuilder.java</p>
 * <p>Package     : pers.linhai.nature.j2ee.core.model.condition</p>
 * @Creator lilinhai 2018年5月15日 下午6:18:39
 * @Version  V1.0  
 */ 

package pers.linhai.nature.j2ee.core.model;

import pers.linhai.nature.j2ee.core.model.Where.Condition;
import pers.linhai.nature.j2ee.core.model.condition.Operator;

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
    public ConditionBuilder(String fieldName)
    {
        condition = new Condition();
        condition.setFieldName(fieldName);
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
    public Condition lessThan(Object value)
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
    public Condition greaterThan(Object value)
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
    public Condition lessThanOrEqual(Object value)
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
    public Condition greaterThanOrEqual(Object value)
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
    public Condition equal(Object value)
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
    public Condition notEqual(Object value)
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
    public Condition like(Object value)
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
    public Condition in(Object value)
    {
        condition.setOperator(Operator.IN.getValue());
        condition.setValue(value);
        return condition;
    }
    
    /**
     * 不包含
     * <p>Title         : in lilinhai 2018年5月15日 下午6:22:13</p>
     * @param value 
     * void
     */
    public Condition notIn(Object value)
    {
        condition.setOperator(Operator.NOT_IN.getValue());
        condition.setValue(value);
        return condition;
    }
    
    /**
     * 为空
     * <p>Title         : isNull lilinhai 2018年5月15日 下午6:22:13</p>
     * @param value 
     * void
     */
    public Condition isNull()
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
    public Condition isNotNull()
    {
        condition.setOperator(Operator.IS_NOT_NULL.getValue());
        return condition;
    }
}
