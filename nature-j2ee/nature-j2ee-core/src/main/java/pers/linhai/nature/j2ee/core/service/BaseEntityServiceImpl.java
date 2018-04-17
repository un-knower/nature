/*
 * <p>Copyright : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p> <p>FileName : BaseServiceImpl.java</p>
 * <p>Package : com.meme.crm.service.core</p>
 * @Creator lilinhai 2018年2月5日 下午2:59:34
 * @Version V1.0
 */

package pers.linhai.nature.j2ee.core.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;

import pers.linhai.nature.j2ee.core.constant.BaseErrorCode;
import pers.linhai.nature.j2ee.core.dao.IBaseMapper;
import pers.linhai.nature.j2ee.core.dao.processor.DefaultRowDataProcessor;
import pers.linhai.nature.j2ee.core.dao.processor.IEntityDataInterceptor;
import pers.linhai.nature.j2ee.core.dao.processor.IRowDataProcessor;
import pers.linhai.nature.j2ee.core.exception.EntityDeleteInterceptProcessException;
import pers.linhai.nature.j2ee.core.exception.EntitySaveInterceptProcessException;
import pers.linhai.nature.j2ee.core.exception.EntityUpdateInterceptProcessException;
import pers.linhai.nature.j2ee.core.exception.ServiceException;
import pers.linhai.nature.j2ee.core.model.BaseEntity;
import pers.linhai.nature.j2ee.core.model.BaseQuery;
import pers.linhai.nature.j2ee.core.model.EntityBean;


/**
 * service基类实现类
 * <p>ClassName      : BaseServiceImpl</p>
 * @author lilinhai 2018年2月5日 下午3:04:25
 * @version 1.0
 */
public abstract class BaseEntityServiceImpl<Key
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

    /**
     * 公共业务层删除方法
     * <p>Overriding Method: lilinhai 2018年4月2日 下午6:04:22</p>
     * <p>Title: delete</p>
     * @param id
     * @return 
     * @see pers.linhai.nature.j2ee.core.service.IBaseEntityService#delete(java.lang.Object)
     */
    @Transactional(rollbackFor = {Throwable.class})
    public void delete(Key id)
    {
        try
        {
            entityDataInterceptor.beforeDelete(id);
            int c = mapper.delete(id);
            if (c != 1)
            {
                throw new EntityDeleteInterceptProcessException(BaseErrorCode.DELETE_FAIL, "[Controller] delete occor an error, record：ID" + id);
            }
            entityDataInterceptor.afterDelete(id);
        }
        catch (ServiceException e) 
        {
            throw e;
        }
        catch (Throwable e)
        {
            logger.error("[Service] delete(Key id) occor an error", e);
            throw new ServiceException(40000, "[Service] delete(Key id) occor an error, " + e.getMessage());
        }
    }

    /**
     * 保存方法
     * <p>Overriding Method: lilinhai 2018年3月15日 下午2:30:26</p>
     * <p>Title: save</p>
     * @param record
     * @return 
     * @see pers.linhai.nature.j2ee.core.service.IBaseEntityService#save(pers.linhai.nature.j2ee.core.model.BaseEntity)
     */
    @Transactional(rollbackFor = {Throwable.class})
    public void save(Entity record)
    {
        try
        {
            entityDataInterceptor.beforeSave(record);
            int c = mapper.save(record);
            if (c != 1)
            {
                throw new EntitySaveInterceptProcessException(BaseErrorCode.INSERT_FAIL, "[Controller] save occor an error, record：" + JSON.toJSONString(record.toEntityBean()));
            }
            entityDataInterceptor.afterSave(record);
        }
        catch (ServiceException e) 
        {
            throw e;
        }
        catch (Throwable e)
        {
            logger.error("[Service] save(Entity record) occor an error", e);
            throw new ServiceException(40001, "[Service] save(Entity record) occor an error" + e.getMessage());
        }
    }
    
    /**
     * 实体修改
     * <p>Overriding Method: lilinhai 2018年3月15日 下午2:31:58</p>
     * <p>Title: update</p>
     * @param record
     * @return 
     * @see pers.linhai.nature.j2ee.core.service.IBaseEntityService#update(pers.linhai.nature.j2ee.core.model.BaseEntity)
     */
    @Transactional(rollbackFor = {Throwable.class})
    public void update(Entity record)
    {
        try
        {
            entityDataInterceptor.beforeUpdate(record);
            int c = mapper.update(record);
            if (c != 1)
            {
                throw new EntityUpdateInterceptProcessException(10300, "[Controller] update occor an error, record：" + JSON.toJSONString(record.toEntityBean()));
            }
            entityDataInterceptor.afterUpdate(record);
        }
        catch (ServiceException e) 
        {
            throw e;
        }
        catch (Throwable e)
        {
            logger.error("[Service] update(Entity record) occor an error", e);
            throw new ServiceException(40002, "[Service] update(Entity record) occor an error" + e.getMessage());
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
    
    public EntityBean getEntityBean(Key id)
    {
        return mapper.get(id, new DefaultRowDataProcessor<Entity>(entityDataInterceptor));
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
    
    public EntityBean getEntityBean(EntityQuery entityQuery)
    {
        return mapper.get(entityQuery, new DefaultRowDataProcessor<Entity>(entityDataInterceptor));
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
     * <p>Overriding Method: lilinhai 2018年4月17日 上午9:45:21</p>
     * <p>Title: find</p>
     * @param entityQuery
     * @param entityProcessor 
     * @see pers.linhai.nature.j2ee.core.service.IBaseEntityService#find(pers.linhai.nature.j2ee.core.model.BaseQuery, pers.linhai.nature.j2ee.core.dao.processor.IRowDataProcessor)
     */ 
    public void find(EntityQuery entityQuery, IRowDataProcessor<Entity> entityProcessor)
    {
        mapper.find(entityQuery, entityProcessor);
    }

    /**
     * <p>Overriding Method: lilinhai 2018年3月14日 下午11:51:08</p>
     * <p>Title: findEntityBean</p>
     * @param entityQuery
     * @return 
     * @see pers.linhai.nature.j2ee.core.service.IBaseEntityService#findEntityBean(pers.linhai.nature.j2ee.core.model.BaseQuery)
     */
    public List<EntityBean> findEntityBean(EntityQuery entityQuery)
    {
        try
        {
            DefaultRowDataProcessor<Entity> rowDataServiceProcessor = new DefaultRowDataProcessor<Entity>(entityDataInterceptor);
            mapper.find(entityQuery, rowDataServiceProcessor);
            entityDataInterceptor.beforeReturn(rowDataServiceProcessor.getEntityBeanList(), rowDataServiceProcessor.getEntityList());
            return rowDataServiceProcessor.getEntityBeanList();
        }
        catch (Throwable e)
        {
            logger.error("[Service] findEntityBean(EntityQuery entityQuery) occor an error", e);
            return new ArrayList<EntityBean>(0);
        }
    }
}
