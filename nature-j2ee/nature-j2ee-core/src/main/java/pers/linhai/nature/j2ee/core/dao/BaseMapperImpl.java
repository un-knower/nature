/*
 * <p>Copyright : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p> <p>FileName : BaseMapperImpl.java</p>
 * <p>Package : com.meme.crm.dao.core</p>
 * @Creator lilinhai 2018年2月12日 下午1:37:24
 * @Version V1.0
 */

package pers.linhai.nature.j2ee.core.dao;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import pers.linhai.nature.j2ee.core.dao.exception.MapperException;
import pers.linhai.nature.j2ee.core.dao.processor.CustomEntityProcessor;
import pers.linhai.nature.j2ee.core.dao.processor.DefaultEntityProcessor;
import pers.linhai.nature.j2ee.core.dao.processor.DefaultRowDataProcessor;
import pers.linhai.nature.j2ee.core.dao.processor.IEntityProcessor;
import pers.linhai.nature.j2ee.core.dao.processor.IRowDataJointQueryProcessor;
import pers.linhai.nature.j2ee.core.dao.processor.IRowDataProcessor;
import pers.linhai.nature.j2ee.core.dao.resulthandler.RowDataEntityResultHandler;
import pers.linhai.nature.j2ee.core.dao.resulthandler.RowDataHashMapJointQueryResultHandler;
import pers.linhai.nature.j2ee.core.dao.resulthandler.RowDataHashMapResultHandler;
import pers.linhai.nature.j2ee.core.model.BaseEntity;
import pers.linhai.nature.j2ee.core.model.BaseQuery;
import pers.linhai.nature.j2ee.core.model.EntityBean;
import pers.linhai.nature.j2ee.core.model.JointQuery;
import pers.linhai.nature.j2ee.core.model.builder.ConditionBuilder;
import pers.linhai.nature.j2ee.core.model.builder.WhereBuilder;
import pers.linhai.nature.j2ee.core.model.enumer.BaseField;

/**
 * <p>ClassName      : BaseMapperImpl</p>
 * @author lilinhai 2018年2月12日 下午1:37:24
 * @version 1.0
 */
@SuppressWarnings("unchecked")
public class BaseMapperImpl<Key, Entity extends BaseEntity<Key>, EntityQuery extends BaseQuery> implements IBaseMapper<Key, Entity, EntityQuery>
{
    /**
     * 父接口的命名空间
     */
    private static final String BASE_NAME_SPACE = IBaseMapper.class.getName();
    
    /**
     * 保存方法
     */
    private static final String SAVE = BASE_NAME_SPACE.concat(".save");
    
    /**
     * 删除方法
     */
    private static final String DELETE = BASE_NAME_SPACE.concat(".delete");
    
    /**
     * 修改方法
     */
    private static final String UPDATE = BASE_NAME_SPACE.concat(".update");
    
    /**
     * 查询方法
     */
    private static final String FIND = BASE_NAME_SPACE.concat(".find");
    
    /**
     * 计数方法
     */
    private static final String COUNT = BASE_NAME_SPACE.concat(".count");
    
    /**
     * 实体反射器map映射
     */
    private static Map<Class<? extends BaseEntity<?>>, EntityReflector<? extends BaseEntity<?>>> ENTITY_REFLECTOR_MAP = new HashMap<Class<? extends BaseEntity<?>>, EntityReflector<? extends BaseEntity<?>>>();
    
    /**
     * 日志记录器
     */
    protected Logger logger;
    
    @Autowired
    protected SqlSession sqlSession;
    
    /**
     * mybatis-mapper的命名空间
     */
    protected String namespace;
    
    /**
     * 实体反射器
     */
    private EntityReflector<Entity> entityReflector;
    
    /**
     * 实体查询条件构造器
     */
    protected Constructor<EntityQuery> entityQueryConstructor;
    
    /**
     * <p>Title        : BaseMapperImpl lilinhai 2018年2月12日 下午2:12:25</p>
     * @param namespace 
     */
    public BaseMapperImpl()
    {
        try
        {
            Class< ? > interfaces[] = getClass().getInterfaces();
            for (Class< ? > inte : interfaces)
            {
                if (inte.getName().endsWith("Mapper") && !inte.getName().endsWith("BaseMapper"))
                {
                    this.namespace = inte.getName();
                    logger = LoggerFactory.getLogger(namespace);
                    break;
                }
            }
            
            if (this.namespace == null)
            {
                throw new MapperException("[Mapper-" + getClass().getName() + "] instance inited fail, the namespace is null.");
            }
            
            ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            for (Type type : actualTypeArguments)
            {
                if (type instanceof Class)
                {
                    Class<?> c = (Class<?>) type;
                    if (c.getSuperclass() == BaseEntity.class)
                    {
                        Class<Entity> entityClass = (Class<Entity>) c;
                        entityReflector = new EntityReflector<Entity>(entityClass);
                        ENTITY_REFLECTOR_MAP.put(entityClass, entityReflector);
                    }
                    else if (c.getSuperclass() == BaseQuery.class)
                    {
                        entityQueryConstructor = ((Class<EntityQuery>) c).getConstructor();
                        entityQueryConstructor.setAccessible(true);
                    }
                }
            }
            
            if (entityReflector != null && entityQueryConstructor != null)
            {
                logger.info(" init success.");
            }
            else
            {
                throw new MapperException("BaseMapperImpl instance inited fail, the namespace is null.");
            }
        }
        catch (Throwable e)
        {
            if (logger == null)
            {
                logger = LoggerFactory.getLogger(getClass());
            }
            logger.error(" instance inited fail ", e);
            throw new MapperException("BaseMapperImpl instance inited fail ", e);
        }
    }
    
    /**
     * 保存单个实体
     * <p>Overriding Method: lilinhai 2018年5月18日 上午10:14:40</p>
     * <p>Title: save</p>
     * @param record
     * @return 
     * @see pers.linhai.nature.j2ee.core.dao.IBaseMapper#save(pers.linhai.nature.j2ee.core.model.BaseEntity)
     */
    public int save(Entity record)
    {
        try
        {
            if (record == null || !record.hasPersistentField())
            {
                return 0;
            }
            
            if (record.getCreateTime() == null)
            {
                // 设置创建时间
                record.setCreateTime(new Date());
            }
            
            return sqlSession.insert(SAVE, record);
        }
        catch (Throwable e1)
        {
            logger.error(" IBaseMapper.save(Entity record) occor an error ", e1);
            throw new MapperException(e1);
        }
    }
    
    /**
     * 根据主键删除
     * <p>Overriding Method: lilinhai 2018年5月18日 上午10:15:15</p>
     * <p>Title: delete</p>
     * @param id
     * @return 
     * @see pers.linhai.nature.j2ee.core.dao.IBaseMapper#delete(java.lang.Object)
     */
    public int delete(Key id)
    {
        try
        {
            EntityQuery eq = entityQueryConstructor.newInstance();
            eq.setWhere(WhereBuilder.where(new ConditionBuilder<Key>(BaseField.ID).equal(id)).build());
            return delete(eq);
        }
        catch (Throwable e)
        {
            logger.error(" IBaseMapper.delete(Key id)", e);
            throw new MapperException(e);
        }
    }
    
    /**
     * 根据查询条件entityQuery删除
     * <p>Overriding Method: lilinhai 2018年5月18日 上午10:15:23</p>
     * <p>Title: delete</p>
     * @param entityQuery
     * @return 
     * @see pers.linhai.nature.j2ee.core.dao.IBaseMapper#delete(pers.linhai.nature.j2ee.core.model.BaseQuery)
     */
    public int delete(EntityQuery entityQuery)
    {
        return sqlSession.delete(DELETE, entityQuery);
    }
    
    /**
     * 修改实体，如果设置了id就根据id去修改，如果设置where就根据where去修改（后者优先）
     * <p>Overriding Method: lilinhai 2018年5月18日 上午10:15:30</p>
     * <p>Title: update</p>
     * @param record
     * @return 
     * @see pers.linhai.nature.j2ee.core.dao.IBaseMapper#update(pers.linhai.nature.j2ee.core.model.BaseEntity)
     */
    public int update(Entity record)
    {
        try
        {
            // 若ID和where条件都为空，则修改失败，返回0
            if ((record.getId() == null && record.where() == null) || !record.hasPersistentField())
            {
                return 0;
            }
            
            // 去除ID字段的更新
            record.removePersistentField(BaseField.ID);
            
            if (record.getUpdateTime() == null)
            {
                // 刷新修改时间
                record.setUpdateTime(new Date());
            }
            
            // 如果修改条件为空
            if (record.where() == null)
            {
                record.setWhere(WhereBuilder.where(new ConditionBuilder<Key>(BaseField.ID).equal(record.getId())).build());
            }
            
            return sqlSession.update(UPDATE, record);
        }
        catch (Throwable e1)
        {
            logger.error(" IBaseMapper.update(Entity record) occor an error ", e1);
            throw new MapperException(e1);
        }
    }
    
    /**
     * 根据主键获取单个实体
     * <p>Overriding Method: lilinhai 2018年5月18日 上午10:16:33</p>
     * <p>Title: get</p>
     * @param id
     * @return 
     * @see pers.linhai.nature.j2ee.core.dao.IBaseMapper#get(java.lang.Object)
     */
    public Entity get(Key id)
    {
        try
        {
            EntityQuery eq = entityQueryConstructor.newInstance();
            eq.setWhere(WhereBuilder.where(new ConditionBuilder<Key>(BaseField.ID).equal(id)).build());
            return get(eq);
        }
        catch (Throwable e)
        {
            logger.error(" IBaseMapper.get(Key id)", e);
        }
        return null;
    }
    
    /**
     * 根据主键获取单个实体（返回EntityBean）
     * <p>Overriding Method: lilinhai 2018年5月18日 上午10:16:44</p>
     * <p>Title: get</p>
     * @param id
     * @param entityProcessor
     * @return 
     * @see pers.linhai.nature.j2ee.core.dao.IBaseMapper#get(java.lang.Object, pers.linhai.nature.j2ee.core.dao.processor.DefaultRowDataProcessor)
     */
    public EntityBean get(Key id, DefaultRowDataProcessor<Entity> entityProcessor)
    {
        try
        {
            EntityQuery eq = entityQueryConstructor.newInstance();
            eq.setWhere(WhereBuilder.where(new ConditionBuilder<Key>(BaseField.ID).equal(id)).build());
            return get(eq, entityProcessor);
        }
        catch (Throwable e)
        {
            logger.error(" IBaseMapper.get(Key id, DefaultRowDataProcessor<Key, Entity> entityProcessor)", e);
        }
        return null;
    }
    
    /**
     * 根据查询条件获取单个实体，如果匹配到多个，只返回第一个
     * <p>Overriding Method: lilinhai 2018年5月18日 上午10:17:23</p>
     * <p>Title: get</p>
     * @param entityQuery
     * @return 
     * @see pers.linhai.nature.j2ee.core.dao.IBaseMapper#get(pers.linhai.nature.j2ee.core.model.BaseQuery)
     */
    public Entity get(EntityQuery entityQuery)
    {
        entityQuery.setPage(0);
        entityQuery.setSize(1);
        DefaultRowDataProcessor<Entity> entityProcessor = new DefaultRowDataProcessor<Entity>();
        find(entityQuery, entityProcessor);
        if (entityProcessor.getEntityList().isEmpty())
        {
            return null;
        }
        return entityProcessor.getEntityList().get(0);
    }
    
    /**
     * 根据查询条件获取单个实体，如果匹配到多个，只返回第一个
     * <p>Overriding Method: lilinhai 2018年5月18日 上午10:17:51</p>
     * <p>Title: get</p>
     * @param entityQuery
     * @param entityProcessor
     * @return 
     * @see pers.linhai.nature.j2ee.core.dao.IBaseMapper#get(pers.linhai.nature.j2ee.core.model.BaseQuery, pers.linhai.nature.j2ee.core.dao.processor.DefaultRowDataProcessor)
     */
    public EntityBean get(EntityQuery entityQuery, DefaultRowDataProcessor<Entity> entityProcessor)
    {
        entityQuery.setPage(0);
        entityQuery.setSize(1);
        find(entityQuery, entityProcessor);
        if (entityProcessor.getEntityBeanList().isEmpty())
        {
            return null;
        }
        return entityProcessor.getEntityBeanList().get(0);
    }
    
    /**
     * 根据查询条件统计数量
     * <p>Overriding Method: lilinhai 2018年5月18日 上午10:18:01</p>
     * <p>Title: count</p>
     * @param entityQuery
     * @return 
     * @see pers.linhai.nature.j2ee.core.dao.IBaseMapper#count(pers.linhai.nature.j2ee.core.model.BaseQuery)
     */
    public long count(EntityQuery entityQuery)
    {
        final AtomicLong al = new AtomicLong();
        sqlSession.select(COUNT, entityQuery, new ResultHandler<Long>()
        {
            public void handleResult(ResultContext< ? extends Long> resultContext)
            {
                al.set(resultContext.getResultObject());
            }
        });
        return al.get();
    }
    
    /** 
     * <p>Overriding Method: lilinhai 2018年2月12日 下午1:37:43</p>
     * <p>Title: query</p>
     * @param entityQueryBean
     * @return 
     * @see com.meme.crm.dao.core.IBaseMapper#get(com.meme.crm.model.core.BaseQuery)
     */
    @Override
    public List<Entity> find(EntityQuery entityQuery)
    {
        DefaultRowDataProcessor<Entity> entityProcessor = new DefaultRowDataProcessor<Entity>();
        find(entityQuery, entityProcessor);
        return entityProcessor.getEntityList();
    }
    
    @Override
    public void find(EntityQuery entityQuery, IRowDataProcessor<Entity> entityProcessor)
    {
        RowDataHashMapResultHandler<Entity> myResultHandler = new RowDataHashMapResultHandler<Entity>(entityReflector, entityProcessor);
        sqlSession.select(FIND, entityQuery, myResultHandler);
    }
    
    /**
     * 关联查询
     * <p>Title         : find lilinhai 2018年6月3日 上午12:35:46</p>
     * @param entityQuery
     * @param rowDataJointQueryProcessor 
     * void
     */
    public void find(JointQuery entityQuery, IRowDataJointQueryProcessor rowDataJointQueryProcessor)
    {
        RowDataHashMapJointQueryResultHandler myResultHandler = new RowDataHashMapJointQueryResultHandler(ENTITY_REFLECTOR_MAP, rowDataJointQueryProcessor);
        sqlSession.select(FIND, entityQuery, myResultHandler);
    }
    
    /**
     * 调用自己写的statment sql语句
     * <p>Title         : select lilinhai 2018年2月24日 上午9:51:17</p>
     * @param statment
     * @param params
     * @param entityProcessor 
     * void
     */
    public void find(String statment, Object params, IEntityProcessor<Entity> entityProcessor)
    {
        RowDataEntityResultHandler<Entity> myResultHandler = new RowDataEntityResultHandler<Entity>(entityProcessor);
        sqlSession.select(namespace.concat(".").concat(statment), params, myResultHandler);
    }
    
    /**
     * 调用自己写的statment sql语句
     * <p>Title         : select lilinhai 2018年2月24日 上午9:51:17</p>
     * @param statment
     * @param params
     * void
     */
    public List<Entity> find(String statment, Object params)
    {
        DefaultEntityProcessor<Entity> entityProcessor = new DefaultEntityProcessor<Entity>();
        find(statment, params, entityProcessor);
        return entityProcessor.getEntityList();
    }
    
    /**
     * 调用自己写的statment sql语句
     * <p>Title         : select lilinhai 2018年2月24日 上午9:51:17</p>
     * @param statment
     * @param params
     * void
     */
    public Entity get(String statment, Object params)
    {
        List<Entity> el = find(statment, params);
        if (el != null && !el.isEmpty())
        {
            return el.get(0);
        }
        return null;
    }
    
    /**
     * 调用自己写的statment sql语句
     * <p>Title         : select lilinhai 2018年2月24日 上午9:51:17</p>
     * @param statment
     * @param params
     * @param entityProcessor 
     * void
     */
    public <T> void execFind(String statment, Object params, IEntityProcessor<T> entityProcessor)
    {
        RowDataEntityResultHandler<T> myResultHandler = new RowDataEntityResultHandler<T>(entityProcessor);
        sqlSession.select(namespace.concat(".").concat(statment), params, myResultHandler);
    }
    
    /**
     * 调用自己写的statment sql语句
     * <p>Title         : select lilinhai 2018年2月24日 上午9:51:17</p>
     * @param statment
     * @param params
     * void
     */
    public <T> List<T> execFind(String statment, Object params, Class<T> clazz)
    {
        CustomEntityProcessor<T> entityProcessor = new CustomEntityProcessor<T>();
        execFind(statment, params, entityProcessor);
        return entityProcessor.getEntityList();
    }
    
    /**
     * 调用自己写的statment sql语句
     * <p>Title         : select lilinhai 2018年2月24日 上午9:51:17</p>
     * @param statment
     * @param params
     * void
     */
    public List<Entity> execFind(String statment, Object params)
    {
        CustomEntityProcessor<Entity> entityProcessor = new CustomEntityProcessor<Entity>();
        execFind(statment, params, entityProcessor);
        return entityProcessor.getEntityList();
    }
    
    /**
     * 调用自己写的statment sql语句
     * <p>Title         : select lilinhai 2018年2月24日 上午9:51:17</p>
     * @param statment
     * @param params
     * void
     */
    public <T> T execGet(String statment, Object params, Class<T> clazz)
    {
        List<T> el = execFind(statment, params, clazz);
        if (el != null && !el.isEmpty())
        {
            return el.get(0);
        }
        return null;
    }
    
    /**
     * 调用自己写的statment sql语句
     * <p>Title         : select lilinhai 2018年2月24日 上午9:51:17</p>
     * @param statment
     * @param params
     * void
     */
    public Entity execGet(String statment, Object params)
    {
        List<Entity> el = execFind(statment, params);
        if (el != null && !el.isEmpty())
        {
            return el.get(0);
        }
        return null;
    }
}
