/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : OrderByBuilder.java</p>
 * <p>Package     : pers.linhai.nature.j2ee.core.model</p>
 * @Creator lilinhai 2018年5月23日 上午11:34:14
 * @Version  V1.0  
 */ 

package pers.linhai.nature.j2ee.core.model.builder;

import pers.linhai.nature.j2ee.core.model.ModelField;
import pers.linhai.nature.j2ee.core.model.SortField;
import pers.linhai.nature.j2ee.core.model.enumer.Direction;

/**
 * 排序字段构建器
 * <p>ClassName      : OrderByBuilder</p>
 * @author lilinhai 2018年5月23日 上午11:34:14
 * @version 1.0
 */
public class OrderByBuilder<EntityQueryBuilder>
{
    
    private QueryBuilder<?, EntityQueryBuilder> baseQueryBuilder;
    
    /**
     * 排序字段
     */
    private SortField sortField;
    
    /**
     * <p>Title        : OrderByBuilder lilinhai 2018年5月23日 下午12:44:50</p>
     * @param baseQueryBuilder
     * @param modelField
     */
    public OrderByBuilder(QueryBuilder<?, EntityQueryBuilder> baseQueryBuilder, ModelField modelField)
    {
        this.baseQueryBuilder = baseQueryBuilder;
        SortField sortField = new SortField();
        sortField.setFieldName(modelField.getJavaField());
        sortField.setDirection(Direction.ASC.name());
        this.sortField = sortField;
        baseQueryBuilder.orderBy(sortField);
    }
    
    public EntityQueryBuilder asc()
    {
        sortField.setDirection(Direction.ASC.name());
        return baseQueryBuilder.queryBuilder;
    }
    
    public EntityQueryBuilder desc()
    {
        sortField.setDirection(Direction.DESC.name());
        return baseQueryBuilder.queryBuilder;
    }
}
