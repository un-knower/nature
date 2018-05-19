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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;

import pers.linhai.nature.j2ee.core.constant.BaseErrorCode;
import pers.linhai.nature.j2ee.core.dao.IBaseMapper;
import pers.linhai.nature.j2ee.core.dao.exception.MapperException;
import pers.linhai.nature.j2ee.core.dao.processor.DefaultRowDataProcessor;
import pers.linhai.nature.j2ee.core.dao.processor.IRowDataProcessor;
import pers.linhai.nature.j2ee.core.model.BaseEntity;
import pers.linhai.nature.j2ee.core.model.BaseQuery;
import pers.linhai.nature.j2ee.core.model.EntityBean;
import pers.linhai.nature.j2ee.core.service.exception.EntityDeleteInterceptProcessException;
import pers.linhai.nature.j2ee.core.service.exception.EntitySaveInterceptProcessException;
import pers.linhai.nature.j2ee.core.service.exception.EntityUpdateInterceptProcessException;
import pers.linhai.nature.j2ee.core.service.interceptor.IEntityServiceInterceptor;

/**
 * service基类实现类
 * <p>ClassName      : BaseServiceImpl</p>
 * @author lilinhai 2018年2月5日 下午3:04:25
 * @version 1.0
 */
public abstract class BaseEntityServiceImpl<Key, Entity extends BaseEntity<Key>, EntityQuery extends BaseQuery, Mapper extends IBaseMapper<Key, Entity, EntityQuery>, EntityServiceInterceptor extends IEntityServiceInterceptor<Key, Entity, EntityQuery, Mapper>>
        extends BaseService implements IBaseEntityService<Key, Entity, EntityQuery>
{
    
    /**
     * 日志记录器
     */
    protected Logger logger;
    
    @Autowired
    protected Mapper mapper;
    
    /**
     * 业务数据拦截器
     */
    @Autowired
    private EntityServiceInterceptor entityServiceInterceptor;
    
    /**
     * <p>Title        : BaseEntityServiceImpl lilinhai 2018年4月21日 下午10:34:47</p>
     */
    public BaseEntityServiceImpl()
    {
        logger = LoggerFactory.getLogger(getClass().getInterfaces()[0].getName());
        logger.info(" init success.");
    }
    
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
            entityServiceInterceptor.beforeDelete(id);
            int c = mapper.delete(id);
            if (c != 1)
            {
                throw new EntityDeleteInterceptProcessException(BaseErrorCode.DELETE_FAIL, "[Controller] delete occor an error, record：ID" + id);
            }
            entityServiceInterceptor.afterDelete(id);
        }
        catch (Throwable e)
        {
            if (!(e instanceof MapperException))
            {
                logger.error(" delete(Key id) occor an error, id:" + id, e);
                if (e instanceof EntityDeleteInterceptProcessException)
                {
                    throw e;
                }
            }
            throw new EntityDeleteInterceptProcessException(40000, "[Service] delete(Key id) occor an error, " + e.getMessage());
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
            entityServiceInterceptor.beforeSave(record);
            int c = mapper.save(record);
            if (c != 1)
            {
                throw new EntitySaveInterceptProcessException(BaseErrorCode.INSERT_FAIL, "[Controller] save occor an error, record：" + JSON.toJSONString(record.toEntityBean()));
            }
            entityServiceInterceptor.afterSave(record);
        }
        catch (Throwable e)
        {
            if (!(e instanceof MapperException))
            {
                logger.error(" save(Entity record) occor an error, record: " + record, e);
                if (e instanceof EntitySaveInterceptProcessException)
                {
                    throw e;
                }
            }
            throw new EntitySaveInterceptProcessException(40001, "[Service] save(Entity record) occor an error" + e.getMessage());
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
            entityServiceInterceptor.beforeUpdate(record);
            int c = mapper.update(record);
            if (c != 1)
            {
                throw new EntityUpdateInterceptProcessException(10300, "[Controller] update occor an error, record：" + JSON.toJSONString(record.toEntityBean()));
            }
            entityServiceInterceptor.afterUpdate(record);
        }
        catch (Throwable e)
        {
            if (!(e instanceof MapperException))
            {
                logger.error(" update(Entity record) occor an error, record: " + record, e);
                if (e instanceof EntityUpdateInterceptProcessException)
                {
                    throw e;
                }
            }
            throw new EntityUpdateInterceptProcessException(40002, "[Service] update(Entity record) occor an error" + e.getMessage());
        }
    }
    
    public Entity get(Key id)
    {
        return mapper.get(id);
    }
    
    public EntityBean getEntityBean(Key id)
    {
        DefaultRowDataProcessor<Entity> rowDataServiceProcessor = new DefaultRowDataProcessor<Entity>();
        mapper.get(id, rowDataServiceProcessor);
        
        List<EntityBean> entityBeanList = rowDataServiceProcessor.getEntityBeanList();
        List<Entity> entityList = rowDataServiceProcessor.getEntityList();
        if (!entityBeanList.isEmpty())
        {
            entityServiceInterceptor.beforeReturn(entityBeanList.get(0), entityList.get(0));
            return entityBeanList.get(0);
        }
        return null;
    }
    
    public Entity get(EntityQuery entityQuery)
    {
        try
        {
            return mapper.get(entityQuery);
        }
        catch (Throwable e)
        {
            logger.error(" get(EntityQuery entityQuery) occor an error, entityQuery: " + entityQuery, e);
            return null;
        }
    }
    
    public EntityBean getEntityBean(EntityQuery entityQuery)
    {
        DefaultRowDataProcessor<Entity> rowDataServiceProcessor = new DefaultRowDataProcessor<Entity>();
        mapper.get(entityQuery, rowDataServiceProcessor);
        
        List<EntityBean> entityBeanList = rowDataServiceProcessor.getEntityBeanList();
        List<Entity> entityList = rowDataServiceProcessor.getEntityList();
        if (!entityBeanList.isEmpty())
        {
            entityServiceInterceptor.beforeReturn(entityBeanList.get(0), entityList.get(0));
            return entityBeanList.get(0);
        }
        return null;
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
            logger.error(" find(EntityQuery entityQuery) occor an error, entityQuery: " + entityQuery, e);
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
            DefaultRowDataProcessor<Entity> rowDataServiceProcessor = new DefaultRowDataProcessor<Entity>();
            mapper.find(entityQuery, rowDataServiceProcessor);
            
            List<EntityBean> entityBeanList = rowDataServiceProcessor.getEntityBeanList();
            List<Entity> entityList = rowDataServiceProcessor.getEntityList();
            for (int i = 0; i < entityBeanList.size(); i++)
            {
                // 循环处理单个实体返回
                entityServiceInterceptor.beforeReturn(entityBeanList.get(i), entityList.get(i));
            }
            
            // 批量返回处理
            entityServiceInterceptor.beforeReturn(entityBeanList, entityList);
            return rowDataServiceProcessor.getEntityBeanList();
        }
        catch (Throwable e)
        {
            logger.error(" findEntityBean(EntityQuery entityQuery) occor an error, entityQuery: " + entityQuery, e);
            return new ArrayList<EntityBean>(0);
        }
    }
}
