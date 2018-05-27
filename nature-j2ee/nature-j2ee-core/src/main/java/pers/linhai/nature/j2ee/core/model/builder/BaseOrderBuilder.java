/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : OrderByFieldBuilder.java</p>
 * <p>Package     : pers.linhai.nature.j2ee.core.model.builder</p>
 * @Creator lilinhai 2018年5月26日 下午11:49:10
 * @Version  V1.0  
 */ 

package pers.linhai.nature.j2ee.core.model.builder;

import java.util.ArrayList;
import java.util.List;

import pers.linhai.nature.j2ee.core.model.ModelField;
import pers.linhai.nature.j2ee.core.model.SortField;
import pers.linhai.nature.j2ee.core.model.enumer.BaseField;
import pers.linhai.nature.j2ee.core.model.enumer.Direction;

/**
 * 排序字段构建器
 * <p>ClassName      : OrderByFieldBuilder</p>
 * @author lilinhai 2018年5月26日 下午11:49:10
 * @version 1.0
 */
public abstract class BaseOrderBuilder<EntityOrderByBuilder>
{
    
    /**
     * 排序字段集合
     */
    private List<SortField> sortFieldList = new ArrayList<SortField>();
    
    private EntityOrderByBuilder entityOrderByBuilder;

    /**
     * <p>Title        : OrderByFieldBuilder lilinhai 2018年5月26日 下午11:49:54</p>
     * @param sortFieldList
     * @param entityOrderByBuilder 
     */ 
    @SuppressWarnings("unchecked")
    public BaseOrderBuilder()
    {
        this.entityOrderByBuilder = (EntityOrderByBuilder)this;
    }
    
    /**
     * 排序
     * <p>Title         : orderBy lilinhai 2018年5月23日 下午1:02:21</p>
     * @param sortField
     * @return 
     * EntityQueryBuilder
     */
    final EntityOrderByBuilder orderBy(SortField sortField)
    {
        sortFieldList.add(sortField);
        return entityOrderByBuilder;
    }
    
    /**
     * 排序
     * <p>Title         : buildOrderByBuilder lilinhai 2018年5月23日 下午12:50:35</p>
     * @param modelField
     * @return 
     * OrderByBuilder<EntityQueryBuilder>
     */
    protected OrderByFieldBuilder<EntityOrderByBuilder> orderBy(ModelField modelField)
    {
        SortField sortField = new SortField();
        sortField.setFieldName(modelField.getJavaField());
        sortField.setDirection(Direction.ASC.name());
        orderBy(sortField);
        return new OrderByFieldBuilder<EntityOrderByBuilder>(entityOrderByBuilder, sortField);
    }
    
    /**
     * 公共id排序
     * <p>Title         : orderById lilinhai 2018年5月23日 下午12:46:39</p>
     * @return 
     * OrderByBuilder<EntityQueryBuilder>
     */
    public OrderByFieldBuilder<EntityOrderByBuilder> id()
    {
        return orderBy(BaseField.ID);
    }
    
    /**
     * 公共创建时间排序
     * <p>Title         : orderById lilinhai 2018年5月23日 下午12:46:39</p>
     * @return 
     * OrderByBuilder<EntityQueryBuilder>
     */
    public OrderByFieldBuilder<EntityOrderByBuilder> createTime()
    {
        return orderBy(BaseField.CREATE_TIME);
    }
    
    /**
     * 公共修改时间排序
     * <p>Title         : orderById lilinhai 2018年5月23日 下午12:46:39</p>
     * @return 
     * OrderByBuilder<EntityQueryBuilder>
     */
    public OrderByFieldBuilder<EntityOrderByBuilder> updateTime()
    {
        return orderBy(BaseField.UPDATE_TIME);
    }

    /**
     * <p>Get Method   :   sortFieldList List<SortField></p>
     * @return sortFieldList
     */
    public List<SortField> build()
    {
        return sortFieldList;
    }
    
}
