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
public final class FieldConditionBuilder<EntityWhereFieldBuilder, FieldType>
{
    private BaseWhereBuilder<EntityWhereFieldBuilder> whereConditionBuilder;
    
    protected ConditionBuilder<FieldType> conditionBuilder;
    
    /**
     * <p>Title        : EntityQueryBuilderBuilder lilinhai 2018年5月15日 下午6:24:46</p>
     * @param EntityWhereConditionBuilder 
     */ 
    public FieldConditionBuilder(BaseWhereBuilder<EntityWhereFieldBuilder> whereConditionBuilder, ModelField modelField)
    {
        this.conditionBuilder = new ConditionBuilder<FieldType>(modelField);
        this.whereConditionBuilder = whereConditionBuilder;
    }
    
    /**
     * 小于
     * <p>Title         : lessThan lilinhai 2018年5月15日 下午6:22:13</p>
     * @param value 
     * void
     */
    public final EntityWhereFieldBuilder lessThan(FieldType value)
    {
        return whereConditionBuilder.append(conditionBuilder.lessThan(value));
    }
    
    /**
     * 大于
     * <p>Title         : greaterThan lilinhai 2018年5月15日 下午6:22:13</p>
     * @param value 
     * void
     */
    public final EntityWhereFieldBuilder greaterThan(FieldType value)
    {
        return whereConditionBuilder.append(conditionBuilder.greaterThan(value));
    }
    
    /**
     * 小于或等于
     * <p>Title         : lessThanOrEqual lilinhai 2018年5月15日 下午6:22:13</p>
     * @param value 
     * void
     */
    public final EntityWhereFieldBuilder lessThanOrEqual(FieldType value)
    {
        return whereConditionBuilder.append(conditionBuilder.lessThanOrEqual(value));
    }
    
    /**
     * 大于或等于
     * <p>Title         : greaterThanOrEqual lilinhai 2018年5月15日 下午6:22:13</p>
     * @param value 
     * void
     */
    public final EntityWhereFieldBuilder greaterThanOrEqual(FieldType value)
    {
        return whereConditionBuilder.append(conditionBuilder.greaterThanOrEqual(value));
    }
    
    /**
     * 等于
     * <p>Title         : equal lilinhai 2018年5月15日 下午6:22:13</p>
     * @param value 
     * void
     */
    public final EntityWhereFieldBuilder equal(FieldType value)
    {
        return whereConditionBuilder.append(conditionBuilder.equal(value));
    }
    
    /**
     * 不等于
     * <p>Title         : noeEqual lilinhai 2018年5月15日 下午6:22:13</p>
     * @param value 
     * void
     */
    public final EntityWhereFieldBuilder notEqual(FieldType value)
    {
        return whereConditionBuilder.append(conditionBuilder.notEqual(value));
    }
    
    /**
     * 像
     * <p>Title         : like lilinhai 2018年5月15日 下午6:22:13</p>
     * @param value 
     * void
     */
    public final EntityWhereFieldBuilder like(FieldType value)
    {
        return whereConditionBuilder.append(conditionBuilder.like(value));
    }
    
    /**
     * 包含
     * <p>Title         : in lilinhai 2018年5月15日 下午6:22:13</p>
     * @param value 
     * void
     */
    public final EntityWhereFieldBuilder in(List<FieldType> valueList)
    {
        return whereConditionBuilder.append(conditionBuilder.in(valueList));
    }
    
    /**
     * 包含
     * <p>Title         : in lilinhai 2018年5月15日 下午6:22:13</p>
     * @param value 
     * void
     */
    @SafeVarargs
    public final EntityWhereFieldBuilder in(FieldType... values)
    {
        return whereConditionBuilder.append(conditionBuilder.in(values));
    }
    
    /**
     * 不包含
     * <p>Title         : in lilinhai 2018年5月15日 下午6:22:13</p>
     * @param value 
     * void
     */
    public final EntityWhereFieldBuilder notIn(List<FieldType> valueList)
    {
        return whereConditionBuilder.append(conditionBuilder.notIn(valueList));
    }
    
    /**
     * 不包含
     * <p>Title         : in lilinhai 2018年5月15日 下午6:22:13</p>
     * @param value 
     * void
     */
    @SafeVarargs
    public final EntityWhereFieldBuilder notIn(FieldType... values)
    {
        return whereConditionBuilder.append(conditionBuilder.notIn(values));
    }
    
    /**
     * 为空
     * <p>Title         : isNull lilinhai 2018年5月15日 下午6:22:13</p>
     * @param value 
     * void
     */
    public final EntityWhereFieldBuilder isNull()
    {
        return whereConditionBuilder.append(conditionBuilder.isNull());
    }
    
    /**
     * 不为空
     * <p>Title         : in lilinhai 2018年5月15日 下午6:22:13</p>
     * @param value 
     * void
     */
    public final EntityWhereFieldBuilder isNotNull()
    {
        return whereConditionBuilder.append(conditionBuilder.isNotNull());
    }
}
