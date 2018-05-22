/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : QueryBuilder.java</p>
 * <p>Package     : pers.linhai.nature.j2ee.core.model</p>
 * @Creator lilinhai 2018年5月20日 下午7:09:27
 * @Version  V1.0  
 */ 

package pers.linhai.nature.j2ee.core.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Stack;

import pers.linhai.nature.j2ee.core.model.Where.Condition;
import pers.linhai.nature.j2ee.core.model.enumer.BaseField;
import pers.linhai.nature.j2ee.core.model.enumer.Direction;
import pers.linhai.nature.j2ee.core.model.enumer.LogicalOperator;
import pers.linhai.nature.j2ee.core.model.exception.QueryBuildException;

/**
 * 抽象的查询对象构建器
 * <p>ClassName      : QueryBuilder</p>
 * @author lilinhai 2018年5月20日 下午7:09:27
 * @version 1.0
 */
public abstract class QueryBuilder<EntityQuery extends BaseQuery, EntityQueryBuilder>
{
    /**
     * 是否设置了返回所有字段
     */
    private boolean isReturnAll;
    
    /**
     * 是否设置了返回某个字段
     */
    private boolean isSetReturn;
    
    /**
     * 是否构建结束
     */
    private boolean isOver;
    
    /**
     * where条件是否已经开始编写
     */
    private boolean isWhereBegin;
    
    /**
     * 排序字段是否已经开始编写
     */
    private boolean isOrderByBegin;
    
    /**
     * 分页参数是否已经开始编写
     */
    private boolean isPageBegin;
    
    /**
     * 逻辑运算符and or
     */
    private LogicalOperator logicOperator;
    
    /**
     * 第几页
     */
    private Integer page;
    
    /**
     * 每页显示的大小
     */
    private Integer size;
    
    /**
     * 排序字段集合
     */
    private List<SortField> sortFieldList = new ArrayList<SortField>();
    
    /**
     * 待返回的字段列表
     */
    private List<String> returnFieldList = new ArrayList<String>();
    
    /**
     * where条件构建器
     */
    private WhereBuilder whereBuilder;
    
    private Stack<LogicalOperator> logicalOperatorStack = new Stack<LogicalOperator>();
    private Stack<WhereBuilder> whereBuilderStack = new Stack<WhereBuilder>();
    
    protected EntityQueryBuilder queryBuilder;
    protected EntityQuery query;
    
    public final EntityQueryBuilder start()
    {
        if (isOver)
        {
            // 构建已经结束，该对象不能再调用其他方法
            throw new QueryBuildException("The query builder is finished, and the object can no longer invoke other methods.");
        }
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
        return queryBuilder;
    }
    
    public final EntityQueryBuilder end()
    {
        if (isOver)
        {
            // 构建已经结束，该对象不能再调用其他方法
            throw new QueryBuildException("The query builder is finished, and the object can no longer invoke other methods.");
        }
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
        return queryBuilder;
    }
    
    /**
     * 返回该表中的所有字段
     * <p>Title         : returnAll lilinhai 2018年5月22日 下午2:37:37</p>
     * @return 
     * EntityQueryBuilder
     */
    public final EntityQueryBuilder returnAll()
    {
        if (isOver)
        {
            // 构建已经结束，该对象不能再调用其他方法
            throw new QueryBuildException("The query builder is finished, and the object can no longer invoke other methods.");
        }
        if (isSetReturn)
        {
            //异常处理：在设置了return某个字段后，不能再调用returnAll方法
            throw new QueryBuildException("After setting up a return field, you can no longer invoke the returnAll method.");
        }
        if (isReturnAll)
        {
            //异常处理：returnAll方法只能调用一次
            throw new QueryBuildException("The returnAll method can only be called once.");
        }
        isReturnAll = true;
        return queryBuilder;
    }
    
    /**
     * 需要返回的字段
     * <p>Title         : returnField lilinhai 2018年5月20日 下午7:17:41</p>
     * @param modelField 
     * void
     */
    protected final EntityQueryBuilder _returnField(ModelField modelField)
    {
        if (isOver)
        {
            // 构建已经结束，该对象不能再调用其他方法
            throw new QueryBuildException("The query builder is finished, and the object can no longer invoke other methods.");
        }
        if (isWhereBegin || isOrderByBegin || isPageBegin)
        {
            // 必须在查询条件、排序和分页设置之前设置搜索字段。 
            throw new QueryBuildException("The search fields must be set before query conditions, sorting and paging settings.");
        }
        if (isReturnAll)
        {
            //异常处理：returnAll方法设置后，不能在设置return某个具体字段
            throw new QueryBuildException("After setting the returnAll method, you cannot set a specific field in return.");
        }
        isSetReturn = true;
        returnFieldList.add(modelField.getJavaField());
        return queryBuilder;
    }
    
    protected final EntityQueryBuilder orderBy(SortField sortField)
    {
        if (isOver)
        {
            // 构建已经结束，该对象不能再调用其他方法
            throw new QueryBuildException("The query builder is finished, and the object can no longer invoke other methods.");
        }
        if (!isWhereBegin || isPageBegin)
        {
            // 排序字段设置必须在查询条件设置之后且分页参数设置之前
            throw new QueryBuildException("Sorting field settings must be set after query conditions are set and before paging parameters are set.");
        }
        sortFieldList.add(sortField);
        return queryBuilder;
    }
    
    public final EntityQueryBuilder setPage(Integer page)
    {
        if (isOver)
        {
            // 构建已经结束，该对象不能再调用其他方法
            throw new QueryBuildException("The query builder is finished, and the object can no longer invoke other methods.");
        }
        if (page != null && page < 0)
        {
            // 异常处理
            throw new QueryBuildException("The page can't be empty or less than zero.");
        }
        this.page = page;
        return queryBuilder;
    }
    
    public final EntityQueryBuilder setSize(Integer size)
    {
        if (isOver)
        {
            // 构建已经结束，该对象不能再调用其他方法
            throw new QueryBuildException("The query builder is finished, and the object can no longer invoke other methods.");
        }
        if (size != null && size <= 0)
        {
            // 异常处理
            throw new QueryBuildException("The size can't be empty or less-than-or-equal zero.");
        }
        this.size = size;
        return queryBuilder;
    }
    
    public final EntityQueryBuilder where()
    {
        if (isOver)
        {
            // 构建已经结束，该对象不能再调用其他方法
            throw new QueryBuildException("The query builder is finished, and the object can no longer invoke other methods.");
        }
        if (isWhereBegin)
        {
            // 异常处理
            throw new QueryBuildException("The where function is called, can't be called again.");
        }
        if (!isSetReturn && !isReturnAll)
        {
            // 异常处理：在调用where之前，需要先设置你需要return的字段
            throw new QueryBuildException("Before calling where, you need to set up a field that you need return.");
        }
        isWhereBegin = true;
        logicOperator = LogicalOperator.AND;
        return queryBuilder;
    }
    
    public final EntityQueryBuilder and()
    {
        if (isOver)
        {
            // 构建已经结束，该对象不能再调用其他方法
            throw new QueryBuildException("The query builder is finished, and the object can no longer invoke other methods.");
        }
        if (logicOperator != null)
        {
            // 异常处理
            throw new QueryBuildException("The and function is called, can't be called again.");
        }
        logicOperator = LogicalOperator.AND;
        return queryBuilder;
    }
    
    public final EntityQueryBuilder or()
    {
        if (isOver)
        {
            // 构建已经结束，该对象不能再调用其他方法
            throw new QueryBuildException("The query builder is finished, and the object can no longer invoke other methods.");
        }
        if (logicOperator != null)
        {
            // 异常处理
            throw new QueryBuildException("The or function is called, can't be called again.");
        }
        logicOperator = LogicalOperator.OR;
        return queryBuilder;
    }
    
    protected final EntityQueryBuilder append(Condition condition)
    {
        // 检查where函数是否已经调用执行 
        checkIsWhereBegin();
        if (logicOperator == null)
        {
            // 异常处理
            throw new QueryBuildException("Please select the logical-operator[and or] first.");
        }
        if (whereBuilder == null)
        {
            whereBuilder = WhereBuilder.where(condition);
        }
        else
        {
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
        return queryBuilder;
    }
    
    /**
     * 构建最终的实体查询对象
     * <p>Title         : build lilinhai 2018年5月22日 下午12:54:49</p>
     * @return 
     * EntityQuery
     */
    public final EntityQuery build()
    {
        if (isOver)
        {
            // 构建已经结束，该对象不能再调用其他方法
            throw new QueryBuildException("The query builder is finished, and the object can no longer invoke other methods.");
        }
        if (!whereBuilderStack.isEmpty())
        {
            // 在执行查询构建器的build方法之前，必须让所有的高优先级逻辑运算式结束
            throw new QueryBuildException("Before executing the build method of the query builder, all high priority logic expressions must be completed.");
        }
        query.setPage(page);
        query.setSize(size);
        query.setReturnFieldList(returnFieldList);
        query.setSortFieldList(sortFieldList);
        query.setWhere(whereBuilder.build());
        isOver = true;
        
        // 资源释放
        whereBuilder = null;
        returnFieldList = null;
        sortFieldList = null;
        queryBuilder = null;
        return query;
    }
    
    /**
     * 检索：id()
     */
    public EntityQueryBuilder returnId()
    {
        _returnField(BaseField.ID);
        return queryBuilder;
    }

    /**
     * 排列：id()
     */
    public EntityQueryBuilder orderById(Direction direction)
    {
        SortField sf = new SortField();
        sf.setFieldName(BaseField.ID.getJavaField());
        sf.setDirection(direction.name());
        orderBy(sf);
        return queryBuilder;
    }

    /**
     * 字段等于某个值：id()
     */
    public EntityQueryBuilder idEqual(Long id)
    {
        append(ConditionBuilder.field(BaseField.ID).equal(id));
        return queryBuilder;
    }

    /**
     * 字段为null过滤：id()
     */
    public EntityQueryBuilder idIsNull()
    {
        append(ConditionBuilder.field(BaseField.ID).isNull());
        return queryBuilder;
    }

    /**
     * 字段不为null过滤：id()
     */
    public EntityQueryBuilder idIsNotNull()
    {
        append(ConditionBuilder.field(BaseField.ID).isNotNull());
        return queryBuilder;
    }

    /**
     * 字段小于某个值：id()
     */
    public EntityQueryBuilder idLessThan(Long id)
    {
        append(ConditionBuilder.field(BaseField.ID).lessThan(id));
        return queryBuilder;
    }

    /**
     * 字段小于等于某个值：id()
     */
    public EntityQueryBuilder idLessThanOrEqual(Long id)
    {
        append(ConditionBuilder.field(BaseField.ID).lessThanOrEqual(id));
        return queryBuilder;
    }

    /**
     * 字段大于某个值：id()
     */
    public EntityQueryBuilder idGreaterThan(Long id)
    {
        append(ConditionBuilder.field(BaseField.ID).greaterThan(id));
        return queryBuilder;
    }

    /**
     * 字段大于等于某个值：id()
     */
    public EntityQueryBuilder idGreaterThanOrEqual(Long id)
    {
        append(ConditionBuilder.field(BaseField.ID).greaterThanOrEqual(id));
        return queryBuilder;
    }

    /**
     * 字段在某个范围内in：id()
     */
    public EntityQueryBuilder idIn(Long ... idArr)
    {
        append(ConditionBuilder.field(BaseField.ID).in(idArr));
        return queryBuilder;
    }

    /**
     * 字段在某个范围内in：id()
     */
    public EntityQueryBuilder idIn(List<Long> idList)
    {
        append(ConditionBuilder.field(BaseField.ID).in(idList));
        return queryBuilder;
    }
    
    /**
     * 字段不在某个范围内in：id()
     */
    public EntityQueryBuilder idNotIn(Long ... idArr)
    {
        append(ConditionBuilder.field(BaseField.ID).notIn(idArr));
        return queryBuilder;
    }

    /**
     * 字段不在某个范围内in：id()
     */
    public EntityQueryBuilder idNotIn(List<Long> idList)
    {
        append(ConditionBuilder.field(BaseField.ID).notIn(idList));
        return queryBuilder;
    }

    /**
     * 检索：create_time()
     */
    public EntityQueryBuilder returnCreateTime()
    {
        _returnField(BaseField.CREATE_TIME);
        return queryBuilder;
    }

    /**
     * 排列：create_time()
     */
    public EntityQueryBuilder orderByCreateTime(Direction direction)
    {
        SortField sf = new SortField();
        sf.setFieldName(BaseField.CREATE_TIME.getJavaField());
        sf.setDirection(direction.name());
        orderBy(sf);
        return queryBuilder;
    }

    /**
     * 字段等于某个值：create_time()
     */
    public EntityQueryBuilder createTimeEqual(Date createTime)
    {
        append(ConditionBuilder.field(BaseField.CREATE_TIME).equal(createTime));
        return queryBuilder;
    }

    /**
     * 字段为null过滤：create_time()
     */
    public EntityQueryBuilder createTimeIsNull()
    {
        append(ConditionBuilder.field(BaseField.CREATE_TIME).isNull());
        return queryBuilder;
    }

    /**
     * 字段不为null过滤：create_time()
     */
    public EntityQueryBuilder createTimeIsNotNull()
    {
        append(ConditionBuilder.field(BaseField.CREATE_TIME).isNotNull());
        return queryBuilder;
    }

    /**
     * 字段小于某个值：create_time()
     */
    public EntityQueryBuilder createTimeLessThan(Date createTime)
    {
        append(ConditionBuilder.field(BaseField.CREATE_TIME).lessThan(createTime));
        return queryBuilder;
    }

    /**
     * 字段小于等于某个值：create_time()
     */
    public EntityQueryBuilder createTimeLessThanOrEqual(Date createTime)
    {
        append(ConditionBuilder.field(BaseField.CREATE_TIME).lessThanOrEqual(createTime));
        return queryBuilder;
    }

    /**
     * 字段大于某个值：create_time()
     */
    public EntityQueryBuilder createTimeGreaterThan(Date createTime)
    {
        append(ConditionBuilder.field(BaseField.CREATE_TIME).greaterThan(createTime));
        return queryBuilder;
    }

    /**
     * 字段大于等于某个值：create_time()
     */
    public EntityQueryBuilder createTimeGreaterThanOrEqual(Date createTime)
    {
        append(ConditionBuilder.field(BaseField.CREATE_TIME).greaterThanOrEqual(createTime));
        return queryBuilder;
    }

    /**
     * 字段在某个范围内in：create_time()
     */
    public EntityQueryBuilder createTimeIn(Date ... createTimeArr)
    {
        append(ConditionBuilder.field(BaseField.CREATE_TIME).in(createTimeArr));
        return queryBuilder;
    }

    /**
     * 字段在某个范围内in：create_time()
     */
    public EntityQueryBuilder createTimeIn(List<Date> createTimeList)
    {
        append(ConditionBuilder.field(BaseField.CREATE_TIME).in(createTimeList));
        return queryBuilder;
    }
    
    /**
     * 字段不在某个范围内in：create_time()
     */
    public EntityQueryBuilder createTimeNotIn(Date ... createTimeArr)
    {
        append(ConditionBuilder.field(BaseField.CREATE_TIME).notIn(createTimeArr));
        return queryBuilder;
    }

    /**
     * 字段不在某个范围内in：create_time()
     */
    public EntityQueryBuilder createTimeNotIn(List<Date> createTimeList)
    {
        append(ConditionBuilder.field(BaseField.CREATE_TIME).notIn(createTimeList));
        return queryBuilder;
    }

    /**
     * 检索：update_time()
     */
    public EntityQueryBuilder returnUpdateTime()
    {
        _returnField(BaseField.UPDATE_TIME);
        return queryBuilder;
    }

    /**
     * 排列：update_time()
     */
    public EntityQueryBuilder orderByUpdateTime(Direction direction)
    {
        SortField sf = new SortField();
        sf.setFieldName(BaseField.UPDATE_TIME.getJavaField());
        sf.setDirection(direction.name());
        orderBy(sf);
        return queryBuilder;
    }

    /**
     * 字段等于某个值：update_time()
     */
    public EntityQueryBuilder updateTimeEqual(Date updateTime)
    {
        append(ConditionBuilder.field(BaseField.UPDATE_TIME).equal(updateTime));
        return queryBuilder;
    }

    /**
     * 字段为null过滤：update_time()
     */
    public EntityQueryBuilder updateTimeIsNull()
    {
        append(ConditionBuilder.field(BaseField.UPDATE_TIME).isNull());
        return queryBuilder;
    }

    /**
     * 字段不为null过滤：update_time()
     */
    public EntityQueryBuilder updateTimeIsNotNull()
    {
        append(ConditionBuilder.field(BaseField.UPDATE_TIME).isNotNull());
        return queryBuilder;
    }

    /**
     * 字段小于某个值：update_time()
     */
    public EntityQueryBuilder updateTimeLessThan(Date updateTime)
    {
        append(ConditionBuilder.field(BaseField.UPDATE_TIME).lessThan(updateTime));
        return queryBuilder;
    }

    /**
     * 字段小于等于某个值：update_time()
     */
    public EntityQueryBuilder updateTimeLessThanOrEqual(Date updateTime)
    {
        append(ConditionBuilder.field(BaseField.UPDATE_TIME).lessThanOrEqual(updateTime));
        return queryBuilder;
    }

    /**
     * 字段大于某个值：update_time()
     */
    public EntityQueryBuilder updateTimeGreaterThan(Date updateTime)
    {
        append(ConditionBuilder.field(BaseField.UPDATE_TIME).greaterThan(updateTime));
        return queryBuilder;
    }

    /**
     * 字段大于等于某个值：update_time()
     */
    public EntityQueryBuilder updateTimeGreaterThanOrEqual(Date updateTime)
    {
        append(ConditionBuilder.field(BaseField.UPDATE_TIME).greaterThanOrEqual(updateTime));
        return queryBuilder;
    }

    /**
     * 字段在某个范围内in：update_time()
     */
    public EntityQueryBuilder updateTimeIn(Date ... updateTimeArr)
    {
        append(ConditionBuilder.field(BaseField.UPDATE_TIME).in(updateTimeArr));
        return queryBuilder;
    }

    /**
     * 字段在某个范围内in：update_time()
     */
    public EntityQueryBuilder updateTimeIn(List<Date> updateTimeList)
    {
        append(ConditionBuilder.field(BaseField.UPDATE_TIME).in(updateTimeList));
        return queryBuilder;
    }
    
    /**
     * 字段不在某个范围内in：update_time()
     */
    public EntityQueryBuilder updateTimeNotIn(Date ... updateTimeArr)
    {
        append(ConditionBuilder.field(BaseField.UPDATE_TIME).notIn(updateTimeArr));
        return queryBuilder;
    }

    /**
     * 字段不在某个范围内in：update_time()
     */
    public EntityQueryBuilder updateTimeNotIn(List<Date> updateTimeList)
    {
        append(ConditionBuilder.field(BaseField.UPDATE_TIME).notIn(updateTimeList));
        return queryBuilder;
    }
    
    /**
     * 检查where函数是否已经条用执行
     * <p>Title         : checkIsWhereBegin lilinhai 2018年5月20日 下午9:55:55</p>
     * void
     */
    private EntityQueryBuilder checkIsWhereBegin()
    {
        if (isOver)
        {
            // 构建已经结束，该对象不能再调用其他方法
            throw new QueryBuildException("The query builder is finished, and the object can no longer invoke other methods.");
        }
        if (!isWhereBegin)
        {
            // 异常处理
            throw new QueryBuildException("Before setting the where condition, please call the where function first.");
        }
        return queryBuilder;
    }
}
