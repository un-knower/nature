/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : BaseServiceImpl.java</p>
 * <p>Package     : com.meme.crm.service.core</p>
 * @Creator lilinhai 2018年2月5日 下午2:59:34
 * @Version  V1.0  
 */ 

package pers.linhai.nature.j2ee.core.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import pers.linhai.nature.j2ee.core.dao.IBaseMapper;
import pers.linhai.nature.j2ee.core.model.BaseEntity;
import pers.linhai.nature.j2ee.core.model.BaseQuery;

/**
 * service基类实现类
 * <p>ClassName      : BaseServiceImpl</p>
 * @author lilinhai 2018年2月5日 下午3:04:25
 * @version 1.0
 */
public abstract class BaseEntityServiceImpl<Key extends Serializable, Entity extends BaseEntity<Key>, EntityQuery extends BaseQuery, Mapper extends IBaseMapper<Key, Entity, EntityQuery>> 
    extends BaseService implements IBaseEntityService<Key, Entity, EntityQuery>
{

    @Autowired
    protected Mapper mapper;

    public int delete(Key id)
    {
        try
        {
            return mapper.delete(id);
        }
        catch (Throwable e)
        {
            logger.error("[Service] deleteByPrimaryKey occor an error", e);
            return -1;
        }
    }

    public int save(Entity record)
    {
        try
        {
            return mapper.save(record);
        }
        catch (Throwable e)
        {
            logger.error("[Service] insert occor an error", e);
            return -1;
        }
    }

    public Entity find(Key id)
    {
        try
        {
            return mapper.find(id);
        }
        catch (Throwable e)
        {
            logger.error("[Service] selectByPrimaryKey occor an error", e);
            return null;
        }
    }

    public int updateSelective(Entity record)
    {
        try
        {
            return mapper.updateSelective(record);
        }
        catch (Throwable e)
        {
            logger.error("[Service] updateByPrimaryKeySelective occor an error", e);
            return -1;
        }
    }

    public int update(Entity record)
    {
        try
        {
            return mapper.update(record);
        }
        catch (Throwable e)
        {
            logger.error("[Service] updateByPrimaryKey occor an error", e);
            return -1;
        }
    }
    
    public Entity findOne(EntityQuery entityQuery)
    {
        try
        {
            return mapper.findOne(entityQuery);
        }
        catch (Throwable e)
        {
            logger.error("[Service] selectByPrimaryKey occor an error", e);
            return null;
        }
    }
    
    /**
     * 统计条目
     * <p>Overriding Method: lilinhai 2018年2月24日 上午11:14:35</p>
     * <p>Title: count</p>
     * @param entityQuery
     * @return 
     * @see pers.linhai.nature.j2ee.core.service.IBaseEntityService#count(pers.linhai.nature.j2ee.core.model.BaseQuery)
     */
    public long count(EntityQuery entityQuery)
    {
        return mapper.count(entityQuery);
    }
    
    /**
     * 统计条目
     * <p>Overriding Method: lilinhai 2018年2月24日 上午11:14:35</p>
     * <p>Title: count</p>
     * @param entityQuery
     * @return 
     * @see pers.linhai.nature.j2ee.core.service.IBaseEntityService#count(pers.linhai.nature.j2ee.core.model.BaseQuery)
     */
    public long sum(EntityQuery entityQuery)
    {
        return mapper.sum(entityQuery);
    }
    
    /**
     * 组合查询记录，返回集合，支持分页
     * <p>Title         : query lilinhai 2018年2月7日 下午5:44:18</p>
     * @param entityQuery
     * @return 
     * List<Entity>
     */
    public List<Entity> find(EntityQuery entityQuery)
    {
        try
        {
            return mapper.find(entityQuery);
        }
        catch (Throwable e)
        {
            logger.error("[Service] query occor an error", e);
            return null;
        }
    }
}
