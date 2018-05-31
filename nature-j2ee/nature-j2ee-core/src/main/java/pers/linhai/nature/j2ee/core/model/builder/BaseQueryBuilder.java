/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : QueryBuilder.java</p>
 * <p>Package     : pers.linhai.nature.j2ee.core.model</p>
 * @Creator lilinhai 2018年5月20日 下午7:09:27
 * @Version  V1.0  
 */ 

package pers.linhai.nature.j2ee.core.model.builder;

import pers.linhai.nature.j2ee.core.model.BaseQuery;
import pers.linhai.nature.j2ee.core.model.exception.QueryBuildException;

/**
 * 抽象的查询对象构建器
 * <p>ClassName      : QueryBuilder</p>
 * @author lilinhai 2018年5月20日 下午7:09:27
 * @version 1.0
 */
public abstract class BaseQueryBuilder<EntitySelectBuilder extends BaseSelectBuilder<EntitySelectBuilder>
    , EntityWhereBuilder extends BaseWhereBuilder<EntityWhereBuilder>
        , EntityOrderBuilder extends BaseOrderBuilder<EntityOrderBuilder>, EntityQuery extends BaseQuery>
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
     * 第几页
     */
    private Integer page;
    
    /**
     * 每页显示的大小
     */
    private Integer size;
    
    /**
     * 字段选择构建器
     */
    protected EntitySelectBuilder selectBuilder;
    
    /**
     * 条件构建器
     */
    protected EntityWhereBuilder whereBuilder;
    
    /**
     * 排序字段构建器
     */
    protected EntityOrderBuilder orderBuilder;
    
    
    protected EntityQuery query;
    
    /**
     * 字段选择构建器
     */
    public EntitySelectBuilder select()
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
        return selectBuilder;
    }
    
    /**
     * 条件构建器
     */
    public EntityWhereBuilder where()
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
        if (!selectBuilder.isSetSelectField())
        {
            // 异常处理：在调用where之前，需要先设置你需要return的字段
            throw new QueryBuildException("Before calling where, you need to set up a field that you need return.");
        }
        return whereBuilder;
    }
    
    /**
     * 条件构建器
     */
    public EntityOrderBuilder orderBy()
    {
        if (isOver)
        {
            // 构建已经结束，该对象不能再调用其他方法
            throw new QueryBuildException("The query builder is finished, and the object can no longer invoke other methods.");
        }
        if (isPageBegin)
        {
            // 排序字段设置必须在分页参数设置之前
            throw new QueryBuildException("Sorting field settings must be set before paging parameters are set.");
        }
        isOrderByBegin = true;
        return orderBuilder;
    }
    
    /**
     * 设置分页起始页
     * <p>Title         : setPage lilinhai 2018年5月27日 上午12:13:18</p>
     * @param page
     * @return 
     * QueryBuilder<EntitySelectFieldBuilder,EntityWhereFieldBuilder,EntityOrderByFieldBuilder,EntityQuery>
     */
    public final BaseQueryBuilder<EntitySelectBuilder, EntityWhereBuilder, EntityOrderBuilder, EntityQuery> setPage(Integer page)
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
        return this;
    }
    
    /**
     * 设置分页大小
     * <p>Title         : setSize lilinhai 2018年5月27日 上午12:13:41</p>
     * @param size
     * @return 
     * QueryBuilder<EntitySelectFieldBuilder,EntityWhereFieldBuilder,EntityOrderByFieldBuilder,EntityQuery>
     */
    public final BaseQueryBuilder<EntitySelectBuilder, EntityWhereBuilder, EntityOrderBuilder, EntityQuery> setSize(Integer size)
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
        return this;
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

        query.setPage(page);
        query.setSize(size);
        query.setSelect(selectBuilder.build());
        query.setSortFieldList(orderBuilder.build());
        query.setWhere(whereBuilder.build());
        isOver = true;
        return query;
    }
}
