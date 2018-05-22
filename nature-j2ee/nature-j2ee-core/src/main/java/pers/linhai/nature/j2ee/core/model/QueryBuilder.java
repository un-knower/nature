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
import java.util.List;
import java.util.Stack;

import pers.linhai.nature.j2ee.core.model.Where.Condition;
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
        return query;
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
