/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : BaseService.java</p>
 * <p>Package     : com.meme.crm.service.core</p>
 * @Creator lilinhai 2018年2月4日 下午9:37:41
 * @Version  V1.0  
 */ 

package pers.linhai.nature.j2ee.core.service;

import java.util.List;

import pers.linhai.nature.j2ee.core.model.BaseEntity;
import pers.linhai.nature.j2ee.core.model.BaseQuery;
import pers.linhai.nature.j2ee.core.model.EntityBean;

/**
 * <p>ClassName      : BaseService</p>
 * @author lilinhai 2018年2月4日 下午9:37:41
 * @version 1.0
 */
public interface IBaseEntityService<Key, Entity extends BaseEntity<Key>, EntityQuery extends BaseQuery>
{

    /**
     * 根据主键ID删除单个记录。
     */
    void delete(Key id);

    /**
     * 添加单个记录。
     */
    void save(Entity record);

    /**
     * 根据主键id查找单个记录。
     */
    Entity get(Key id);
    
    /**
     * <p>Title         : getEntityBean lilinhai 2018年3月15日 上午12:05:39</p>
     * @param id
     * @return 
     * EntityBean
     */
    EntityBean getEntityBean(Key id);

    /**
     * 修改单个记录，只将不为空的字段更新到记录。
     */
    void update(Entity record);
    
    /**
     * 查询单个实体
     * <p>Title         : findOne lilinhai 2018年2月23日 上午9:56:30</p>
     * @param entityQuery
     * @return 
     * EntityBean
     */
    Entity get(EntityQuery entityQuery);
    
    /**
     * 返回到前端展示专用
     * <p>Title         : getEntityBean lilinhai 2018年3月14日 下午11:54:51</p>
     * @param entityQuery
     * @return 
     * EntityBean
     */
    EntityBean getEntityBean(EntityQuery entityQuery);
    
    /**
     * 统计条目
     * <p>Title         : count lilinhai 2018年2月24日 上午11:11:25</p>
     * @param entityQuery
     * @return 
     * long
     */
    long count(EntityQuery entityQuery);
    
    /**
     * 对某一列进行求和
     * <p>Title         : sum lilinhai 2018年2月28日 下午6:01:21</p>
     * @param entityQuery
     * @return 
     * long
     */
    long sum(EntityQuery entityQuery);
    
    /**
     * 组合查询记录，返回集合，支持分页
     * <p>Title         : query lilinhai 2018年2月7日 下午5:44:18</p>
     * @param entityQuery
     * @return 
     * List<Entity>
     */
    List<Entity> find(EntityQuery entityQuery);
    
    /**
     * 组合条件查询多记录，根据IEntityProcessor的实现，定制化处理返回结果
     * <p>Title         : query lilinhai 2018年2月13日 下午12:26:15</p>
     * @param entityQuery
     * void
     */
    List<EntityBean> findEntityBean(EntityQuery entityQuery);
}
