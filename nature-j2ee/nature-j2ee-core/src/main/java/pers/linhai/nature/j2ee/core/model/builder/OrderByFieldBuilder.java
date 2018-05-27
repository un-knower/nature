/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : OrderByBuilder.java</p>
 * <p>Package     : pers.linhai.nature.j2ee.core.model</p>
 * @Creator lilinhai 2018年5月23日 上午11:34:14
 * @Version  V1.0  
 */ 

package pers.linhai.nature.j2ee.core.model.builder;

import pers.linhai.nature.j2ee.core.model.SortField;
import pers.linhai.nature.j2ee.core.model.enumer.Direction;

/**
 * 排序字段构建器
 * <p>ClassName      : OrderByBuilder</p>
 * @author lilinhai 2018年5月23日 上午11:34:14
 * @version 1.0
 */
public class OrderByFieldBuilder<EntityOrderByBuilder>
{
    
    private EntityOrderByBuilder entityOrderByBuilder;
    
    /**
     * 排序字段
     */
    private SortField sortField;
    
    /**
     * <p>Title        : OrderByBuilder lilinhai 2018年5月23日 下午12:44:50</p>
     * @param baseQueryBuilder
     * @param modelField
     */
    public OrderByFieldBuilder(EntityOrderByBuilder entityOrderByBuilder, SortField sortField)
    {
        this.entityOrderByBuilder = entityOrderByBuilder;
        this.sortField = sortField;
    }
    
    public EntityOrderByBuilder asc()
    {
        sortField.setDirection(Direction.ASC.name());
        return entityOrderByBuilder;
    }
    
    public EntityOrderByBuilder desc()
    {
        sortField.setDirection(Direction.DESC.name());
        return entityOrderByBuilder;
    }
}
