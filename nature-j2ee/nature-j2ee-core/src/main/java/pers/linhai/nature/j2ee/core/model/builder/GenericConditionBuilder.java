/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : FieldEntityQueryBuilderBuilder.java</p>
 * <p>Package     : pers.linhai.nature.j2ee.core.model</p>
 * @Creator lilinhai 2018年5月23日 上午9:39:06
 * @Version  V1.0  
 */ 

package pers.linhai.nature.j2ee.core.model.builder;

import java.util.List;

import pers.linhai.nature.j2ee.core.model.ModelField;

/**
 * 泛型条件构建器
 * <p>ClassName      : GenericEntityQueryBuilderBuilder</p>
 * @author lilinhai 2018年5月23日 上午9:39:06
 * @version 1.0
 */
public final class GenericConditionBuilder<EntityQueryBuilder, FieldType>
{
    private QueryBuilder<?, EntityQueryBuilder> baseQueryBuilder;
    
    protected ConditionBuilder conditionBuilder;
    
    /**
     * <p>Title        : EntityQueryBuilderBuilder lilinhai 2018年5月15日 下午6:24:46</p>
     * @param EntityQueryBuilder 
     */ 
    public GenericConditionBuilder(QueryBuilder<?, EntityQueryBuilder> baseQueryBuilder, ModelField modelField)
    {
        this.conditionBuilder = ConditionBuilder.field(modelField);
        this.baseQueryBuilder = baseQueryBuilder;
    }
    
    /**
     * 小于
     * <p>Title         : lessThan lilinhai 2018年5月15日 下午6:22:13</p>
     * @param value 
     * void
     */
    public final EntityQueryBuilder lessThan(FieldType value)
    {
        return baseQueryBuilder.append(conditionBuilder.lessThan(value));
    }
    
    /**
     * 大于
     * <p>Title         : greaterThan lilinhai 2018年5月15日 下午6:22:13</p>
     * @param value 
     * void
     */
    public final EntityQueryBuilder greaterThan(FieldType value)
    {
        return baseQueryBuilder.append(conditionBuilder.greaterThan(value));
    }
    
    /**
     * 小于或等于
     * <p>Title         : lessThanOrEqual lilinhai 2018年5月15日 下午6:22:13</p>
     * @param value 
     * void
     */
    public final EntityQueryBuilder lessThanOrEqual(FieldType value)
    {
        return baseQueryBuilder.append(conditionBuilder.lessThanOrEqual(value));
    }
    
    /**
     * 大于或等于
     * <p>Title         : greaterThanOrEqual lilinhai 2018年5月15日 下午6:22:13</p>
     * @param value 
     * void
     */
    public final EntityQueryBuilder greaterThanOrEqual(FieldType value)
    {
        return baseQueryBuilder.append(conditionBuilder.greaterThanOrEqual(value));
    }
    
    /**
     * 等于
     * <p>Title         : equal lilinhai 2018年5月15日 下午6:22:13</p>
     * @param value 
     * void
     */
    public final EntityQueryBuilder equal(FieldType value)
    {
        return baseQueryBuilder.append(conditionBuilder.equal(value));
    }
    
    /**
     * 不等于
     * <p>Title         : noeEqual lilinhai 2018年5月15日 下午6:22:13</p>
     * @param value 
     * void
     */
    public final EntityQueryBuilder notEqual(FieldType value)
    {
        return baseQueryBuilder.append(conditionBuilder.notEqual(value));
    }
    
    /**
     * 像
     * <p>Title         : like lilinhai 2018年5月15日 下午6:22:13</p>
     * @param value 
     * void
     */
    public final EntityQueryBuilder like(FieldType value)
    {
        return baseQueryBuilder.append(conditionBuilder.like(value));
    }
    
    /**
     * 包含
     * <p>Title         : in lilinhai 2018年5月15日 下午6:22:13</p>
     * @param value 
     * void
     */
    public final EntityQueryBuilder in(List<FieldType> valueList)
    {
        return baseQueryBuilder.append(conditionBuilder.in(valueList));
    }
    
    /**
     * 包含
     * <p>Title         : in lilinhai 2018年5月15日 下午6:22:13</p>
     * @param value 
     * void
     */
    @SafeVarargs
    public final EntityQueryBuilder in(FieldType... values)
    {
        return baseQueryBuilder.append(conditionBuilder.in(values));
    }
    
    /**
     * 不包含
     * <p>Title         : in lilinhai 2018年5月15日 下午6:22:13</p>
     * @param value 
     * void
     */
    public final EntityQueryBuilder notIn(List<FieldType> valueList)
    {
        return baseQueryBuilder.append(conditionBuilder.notIn(valueList));
    }
    
    /**
     * 不包含
     * <p>Title         : in lilinhai 2018年5月15日 下午6:22:13</p>
     * @param value 
     * void
     */
    @SafeVarargs
    public final EntityQueryBuilder notIn(FieldType... values)
    {
        return baseQueryBuilder.append(conditionBuilder.notIn(values));
    }
    
    /**
     * 为空
     * <p>Title         : isNull lilinhai 2018年5月15日 下午6:22:13</p>
     * @param value 
     * void
     */
    public final EntityQueryBuilder isNull()
    {
        return baseQueryBuilder.append(conditionBuilder.isNull());
    }
    
    /**
     * 不为空
     * <p>Title         : in lilinhai 2018年5月15日 下午6:22:13</p>
     * @param value 
     * void
     */
    public final EntityQueryBuilder isNotNull()
    {
        return baseQueryBuilder.append(conditionBuilder.isNotNull());
    }
}
