/*
 * <p>Copyright : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p> <p>FileName : BaseServiceImpl.java</p>
 * <p>Package : com.meme.crm.service.core</p>
 * @Creator lilinhai 2018年2月5日 下午2:59:34
 * @Version V1.0
 */

package pers.linhai.nature.j2ee.core.service;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import pers.linhai.nature.j2ee.core.dao.IBaseMapper;
import pers.linhai.nature.j2ee.core.dao.processor.IRowDataProcessor;
import pers.linhai.nature.j2ee.core.model.BaseEntity;
import pers.linhai.nature.j2ee.core.model.BaseQuery;
import pers.linhai.nature.j2ee.core.model.EntityBean;


/**
 * service基类实现类
 * <p>ClassName      : BaseServiceImpl</p>
 * @author lilinhai 2018年2月5日 下午3:04:25
 * @version 1.0
 */
public abstract class BaseEntityServiceImpl<Key extends Serializable
    , Entity extends BaseEntity<Key>
    , EntityQuery extends BaseQuery
    , Mapper extends IBaseMapper<Key, Entity, EntityQuery>
    , EntityDataInterceptor extends IEntityDataInterceptor<Key, Entity>> 
    extends BaseService implements IBaseEntityService<Key, Entity, EntityQuery>
{

    @Autowired
    protected Mapper mapper;
    
    /**
     * 业务数据拦截器
     */
    @Autowired
    private EntityDataInterceptor entityDataInterceptor;

    public int delete(Key id)
    {
        try
        {
            return mapper.delete(id);
        }
        catch (Throwable e)
        {
            logger.error("[Service] delete(Key id) occor an error", e);
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
            logger.error("[Service] save(Entity record) occor an error", e);
            return -1;
        }
    }

    public Entity get(Key id)
    {
        try
        {
            return mapper.get(id);
        }
        catch (Throwable e)
        {
            logger.error("[Service] get(Key id) occor an error", e);
            return null;
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
            logger.error("[Service] update(Entity record) occor an error", e);
            return -1;
        }
    }

    public Entity get(EntityQuery entityQuery)
    {
        try
        {
            return mapper.get(entityQuery);
        }
        catch (Throwable e)
        {
            logger.error("[Service] get(EntityQuery entityQuery) occor an error", e);
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
     * @see pers.linhai.nature.j2ee.core.service.IBaseEntityService#sum(pers.linhai.nature.j2ee.core.model.BaseQuery)
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
            logger.error("[Service] find(EntityQuery entityQuery) occor an error", e);
            return null;
        }
    }

    /** 
     * <p>Overriding Method: lilinhai 2018年3月14日 下午5:09:46</p>
     * <p>Title: find</p>
     * @param entityQuery
     * @see pers.linhai.nature.j2ee.core.service.IBaseEntityService#find(pers.linhai.nature.j2ee.core.model.BaseQuery, pers.linhai.nature.j2ee.core.dao.processor.IRowDataProcessor)
     */
    public List<EntityBean> findEntityBean(EntityQuery entityQuery)
    {
        List<EntityBean> entityBeanList = new ArrayList<EntityBean>();
        try
        {
            mapper.find(entityQuery, new IRowDataProcessor<Entity>()
            {
                /** 
                 * <p>Overriding Method: lilinhai 2018年3月14日 下午5:43:55</p>
                 * <p>Title: process</p>
                 * @param entityBean
                 * @param entity 
                 * @see pers.linhai.nature.j2ee.core.dao.processor.IRowDataProcessor#process(pers.linhai.nature.j2ee.core.model.EntityBean, java.lang.Object)
                 */ 
                public void process(EntityBean entityBean, Entity entity)
                {
                    entityDataInterceptor.process(entityBean, entity);
                }
            });
        }
        catch (Throwable e)
        {
            logger.error("[Service] find(EntityQuery entityQuery, IRowDataProcessor<Entity> entityProcessor) occor an error", e);
        }
        return entityBeanList;
    }
}
