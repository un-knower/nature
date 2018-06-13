/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : WhereConditionBuilder.java</p>
 * <p>Package     : pers.linhai.nature.j2ee.core.model.builder</p>
 * @Creator lilinhai 2018年5月26日 下午10:44:23
 * @Version  V1.0  
 */ 

package pers.linhai.nature.j2ee.core.model.builder;

import java.util.Date;
import java.util.Stack;

import pers.linhai.nature.j2ee.core.model.Condition;
import pers.linhai.nature.j2ee.core.model.ModelField;
import pers.linhai.nature.j2ee.core.model.Where;
import pers.linhai.nature.j2ee.core.model.enumer.LogicalOperator;
import pers.linhai.nature.j2ee.core.model.exception.QueryBuildException;

/**
 * <p>ClassName      : WhereConditionBuilder</p>
 * @author lilinhai 2018年5月26日 下午10:44:23
 * @version 1.0
 */
public abstract class BaseWhereBuilder<EntityWhereBuilder>
{
    
    /**
     * 逻辑运算符and or
     */
    private LogicalOperator logicOperator;
    
    /**
     * where条件构建器
     */
    private WhereBuilder whereBuilder;
    
    private Stack<LogicalOperator> logicalOperatorStack = new Stack<LogicalOperator>();
    private Stack<WhereBuilder> whereBuilderStack = new Stack<WhereBuilder>();
    
    private EntityWhereBuilder entityWhereConditionBuilder;

    /**
     * <p>Title        : WhereConditionBuilder lilinhai 2018年5月26日 下午10:45:14</p>
     * @param entityWhereConditionBuilder 
     */ 
    @SuppressWarnings("unchecked")
    public BaseWhereBuilder()
    {
        this.entityWhereConditionBuilder = (EntityWhereBuilder)this;
    }
    
    public final EntityWhereBuilder and()
    {
        if (logicOperator != null)
        {
            // 异常处理
            throw new QueryBuildException("The and function is called, can't be called again.");
        }
        logicOperator = LogicalOperator.AND;
        return entityWhereConditionBuilder;
    }
    
    public final EntityWhereBuilder or()
    {
        if (logicOperator != null)
        {
            // 异常处理
            throw new QueryBuildException("The or function is called, can't be called again.");
        }
        logicOperator = LogicalOperator.OR;
        return entityWhereConditionBuilder;
    }
    
    EntityWhereBuilder append(Condition condition)
    {
        if (whereBuilder == null)
        {
            whereBuilder = WhereBuilder.where(condition);
        }
        else
        {
            if (logicOperator == null)
            {
                // 异常处理
                throw new QueryBuildException("Please select the logical-operator[and or] first.");
            }
            WhereBuilder mergeBranchWhereBuilder = !whereBuilderStack.isEmpty() ? whereBuilderStack.peek() : this.whereBuilder;
            if(logicOperator == LogicalOperator.AND)
            {
                mergeBranchWhereBuilder.and(condition);
            }
            else
            {
                mergeBranchWhereBuilder.or(condition);
            }
        }

        logicOperator = null;
        return entityWhereConditionBuilder;
    }
    
    public final EntityWhereBuilder start()
    {
        if (logicOperator == null)
        {
            // 异常处理
            throw new QueryBuildException("Before connecting a high priority logic expression, you need to perform and or or functions first.");
        }
        
        if (whereBuilder == null)
        {
            // 异常处理
            throw new QueryBuildException("Before connecting a high priority logic expression, the logical expression of the trunk can not be null.");
        }
        whereBuilderStack.add(WhereBuilder.where());
        logicalOperatorStack.add(logicOperator);
        return entityWhereConditionBuilder;
    }
    
    public final EntityWhereBuilder end()
    {
        if (logicalOperatorStack.isEmpty() || whereBuilderStack.isEmpty())
        {
            // 在结束一个高优先逻辑运算式前，先调用start方法，打开一个高优先级逻辑运算式
            throw new QueryBuildException("Before ending a high priority logic expression, first call the start method and open a high priority logic expression.");
        }
        LogicalOperator logicalOperator = logicalOperatorStack.pop();
        WhereBuilder subWhereBuilder = whereBuilderStack.pop();
        if (subWhereBuilder.conditionSize() <= 1)
        {
            // 在结束一个高优先逻辑运算式前，先保证该表达式存在2个及以上的子条件
            throw new QueryBuildException("Before ending a high priority logic formula, it is guaranteed that the expression has 2 or more sub conditions.");
        }
        
        // 同子分支或主干是合并
        WhereBuilder mergeBranchWhereBuilder = !whereBuilderStack.isEmpty() ? whereBuilderStack.peek() : this.whereBuilder;
        if (logicalOperator == LogicalOperator.AND)
        {
            mergeBranchWhereBuilder.and(subWhereBuilder);
        }
        else
        {
            mergeBranchWhereBuilder.or(subWhereBuilder);
        }
        return entityWhereConditionBuilder;
    }
    
    /**
     * 泛型查询条件构建器
     * <p>Title         : build lilinhai 2018年5月23日 下午1:00:26</p>
     * @param modelField
     * @param fieldTypeClass
     * @return 
     * GenericConditionBuilder<EntityQueryBuilder,FieldType>
     */
    protected <FieldType> FieldConditionBuilder<EntityWhereBuilder, FieldType> build(ModelField modelField, Class<FieldType> fieldTypeClass)
    {
        return new FieldConditionBuilder<EntityWhereBuilder, FieldType>(this, modelField);
    }
    
    /**
     * 所有实体的公共ID
     * <p>Title         : id lilinhai 2018年5月23日 上午11:00:57</p>
     * @return 
     * GenericConditionBuilder<EntityQueryBuilder,Long>
     */
    public FieldConditionBuilder<EntityWhereBuilder, Long> id()
    {
        return build(ModelField.ID, Long.class);
    }
    
    /**
     * 所有实体公共创建时间
     * <p>Title         : createTime lilinhai 2018年5月23日 上午11:01:12</p>
     * @return 
     * GenericConditionBuilder<EntityQueryBuilder,Date>
     */
    public FieldConditionBuilder<EntityWhereBuilder, Date> createTime()
    {
        return build(ModelField.CREATE_TIME, Date.class);
    }
    
    /**
     * 所有实体公共修改时间
     * <p>Title         : updateTime lilinhai 2018年5月23日 上午11:01:36</p>
     * @return 
     * GenericConditionBuilder<EntityQueryBuilder,Date>
     */
    public FieldConditionBuilder<EntityWhereBuilder, Date> updateTime()
    {
        return build(ModelField.UPDATE_TIME, Date.class);
    }

    /**
     * <p>Get Method   :   whereBuilder WhereBuilder</p>
     * @return whereBuilder
     */
    public Where build()
    {
        if (whereBuilder == null)
        {
            return null;
        }
        if (!whereBuilderStack.isEmpty())
        {
            // 在执行查询构建器的build方法之前，必须让所有的高优先级逻辑运算式结束
            throw new QueryBuildException("Before executing the build method of the query builder, all high priority logic expressions must be completed.");
        }
        return whereBuilder.build();
    }
}
