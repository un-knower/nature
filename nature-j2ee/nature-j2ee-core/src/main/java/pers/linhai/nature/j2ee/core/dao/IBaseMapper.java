/*
 * <p>Copyright : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p> <p>FileName : BaseMapper.java</p> <p>Package
 * : com.meme.crm.dao.core</p>
 * @Creator lilinhai 2018年2月4日 下午4:27:46
 * @Version V1.0
 */

package pers.linhai.nature.j2ee.core.dao;


import java.io.Serializable;
import java.util.List;

import pers.linhai.nature.j2ee.core.model.BaseEntity;
import pers.linhai.nature.j2ee.core.model.BaseQuery;


/**
 * 公共mapper
 * <p>ClassName      : BaseMapper</p>
 * @author lilinhai 2018年2月4日 下午4:27:46
 * @version 1.0
 */
public interface IBaseMapper<Key extends Serializable, Entity extends BaseEntity<Key>, EntityQuery extends BaseQuery>
{

    /**
     * 添加单个记录。
     */
    int save(Entity record);

    /**
     * 添加单个记录，只将不为空的字段插入表中。
     */
    int saveSelective(Entity record);

    /**
     * 根据主键ID删除单个记录。
     * <p>Title         : delete lilinhai 2018年2月13日 下午2:09:23</p>
     * @param id
     * @return 
     * int
     */
    int delete(Key id);

    /**
     * 根据查询条件删除
     * <p>Title         : delete lilinhai 2018年2月13日 下午2:08:59</p>
     * @param entityQuery
     * @return 
     * int
     */
    int delete(EntityQuery entityQuery);

    /**
     * 修改单个记录，只将不为空的字段更新到记录。
     */
    int updateSelective(Entity record);

    /**
     * 修改单个记录。
     */
    int update(Entity record);

    /**
     * 根据主键id查找单个记录。
     */
    Entity find(Key id);

    /**
     * 根据条件查询单个记录
     * <p>Title         : findOne lilinhai 2018年2月13日 下午1:53:54</p>
     * @param entityQuery
      * @return 
     * Entity
     */
    Entity findOne(EntityQuery entityQuery);

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
     * @param entityQueryBean
     * @param entityProcessor 
     * void
     */
    void find(EntityQuery entityQueryBean, IEntityProcessor<Entity> entityProcessor);

    /**
     * 统计记录条数
     * <p>Title         : count lilinhai 2018年2月13日 下午2:13:26</p>
     * @param entityQuery
     * @return 
     * long
     */
    long count(EntityQuery entityQuery);
}