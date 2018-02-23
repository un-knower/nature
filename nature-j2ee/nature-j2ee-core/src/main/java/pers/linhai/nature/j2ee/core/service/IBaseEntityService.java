/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : BaseService.java</p>
 * <p>Package     : com.meme.crm.service.core</p>
 * @Creator lilinhai 2018年2月4日 下午9:37:41
 * @Version  V1.0  
 */ 

package pers.linhai.nature.j2ee.core.service;

import java.io.Serializable;
import java.util.List;

import pers.linhai.nature.j2ee.core.model.BaseEntity;
import pers.linhai.nature.j2ee.core.model.BaseQuery;
import pers.linhai.nature.j2ee.core.model.EntityBean;

/**
 * <p>ClassName      : BaseService</p>
 * @author lilinhai 2018年2月4日 下午9:37:41
 * @version 1.0
 */
public interface IBaseEntityService<Key extends Serializable, Entity extends BaseEntity<Key>, EntityQuery extends BaseQuery>
{

    /**
     * 根据主键ID删除单个记录。
     */
    int delete(Key id);

    /**
     * 添加单个记录。
     */
    int save(Entity record);

    /**
     * 添加单个记录，只将不为空的字段插入表中。
     */
    int saveSelective(Entity record);

    /**
     * 根据主键id查找单个记录。
     */
    EntityBean find(Key id);

    /**
     * 修改单个记录，只将不为空的字段更新到记录。
     */
    int updateSelective(Entity record);

    /**
     * 修改单个记录。
     */
    int update(Entity record);
    
    /**
     * 查询单个实体
     * <p>Title         : findOne lilinhai 2018年2月23日 上午9:56:30</p>
     * @param entityQuery
     * @return 
     * EntityBean
     */
    EntityBean findOne(EntityQuery entityQuery);
    
    /**
     * 分页查询
     * <p>Title         : pageQuery lilinhai 2018年2月7日 下午6:36:29</p>
     * @param entityQuery
     * @return 
     * PaginationData<EntityBean>
     */
    PaginationData<EntityBean> pageQuery(EntityQuery entityQuery);
    
    /**
     * 组合查询记录，返回集合，支持分页
     * <p>Title         : query lilinhai 2018年2月7日 下午5:44:18</p>
     * @param entityQuery
     * @return 
     * List<Entity>
     */
    List<EntityBean> find(EntityQuery entityQuery);
}
