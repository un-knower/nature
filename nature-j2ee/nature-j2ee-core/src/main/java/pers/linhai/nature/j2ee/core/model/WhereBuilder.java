/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : WhereBuilder.java</p>
 * <p>Package     : pers.linhai.nature.j2ee.core.model</p>
 * @Creator lilinhai 2018年5月15日 下午8:42:22
 * @Version  V1.0  
 */ 

package pers.linhai.nature.j2ee.core.model;

import java.util.ArrayList;
import java.util.List;

import pers.linhai.nature.j2ee.core.model.Where.Condition;

/**
 * 查询条件where构建器
 * <p>ClassName      : WhereBuilder</p>
 * @author lilinhai 2018年5月15日 下午8:42:22
 * @version 1.0
 */
public class WhereBuilder
{
    
    /**
     * 查询条件集合
     */
    private List<Condition> conditionList = new ArrayList<Condition>();
    
    /**
     * 逻辑表达式
     */
    private StringBuilder expressionBuilder = new StringBuilder();
    
    private String idPrefix = String.valueOf(this.hashCode());
    
    private int idIndex;
    
    private WhereBuilder(Condition condition)
    {
        condition.setId(getId());
        conditionList.add(condition);
        expressionBuilder.append(condition.getId());
    }
    
    public static WhereBuilder where(Condition condition)
    {
        return new WhereBuilder(condition);
    }
    
    public WhereBuilder and(Condition condition)
    {
        condition.setId(getId());
        conditionList.add(condition);
        expressionBuilder.append(" and ").append(condition.getId());
        return this;
    }
    
    public WhereBuilder or(Condition condition)
    {
        condition.setId(getId());
        conditionList.add(condition);
        expressionBuilder.append(" or ").append(condition.getId());
        return this;
    }
    
    public WhereBuilder or(WhereBuilder whereBuilder)
    {
        for (Condition condition : whereBuilder.conditionList)
        {
            this.conditionList.add(condition);
        }
        this.expressionBuilder.append(" or ( ").append(whereBuilder.expressionBuilder).append(" ) ");
        return this;
    }
    
    public WhereBuilder and(WhereBuilder whereBuilder)
    {
        for (Condition condition : whereBuilder.conditionList)
        {
            this.conditionList.add(condition);
        }
        this.expressionBuilder.append(" and ( ").append(whereBuilder.expressionBuilder).append(" ) ");
        return this;
    }
    
    public Where build() 
    {
        Where where = new Where();
        where.setConditionList(conditionList);
        where.setExpression(expressionBuilder.toString());
        return where;
    }
    
    private String getId()
    {
        return idPrefix + "_" + (++idIndex);
    }
}
