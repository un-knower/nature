/*
 * <p>Copyright : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p> <p>FileName : BaseMapperImpl.java</p>
 * <p>Package : com.meme.crm.dao.core</p>
 * @Creator lilinhai 2018年2月12日 下午1:37:24
 * @Version V1.0
 */

package pers.linhai.nature.j2ee.core.dao;


import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import pers.linhai.nature.j2ee.core.dao.processor.CustomEntityProcessor;
import pers.linhai.nature.j2ee.core.dao.processor.DefaultEntityProcessor;
import pers.linhai.nature.j2ee.core.dao.processor.DefaultRowDataProcessor;
import pers.linhai.nature.j2ee.core.dao.processor.IEntityProcessor;
import pers.linhai.nature.j2ee.core.dao.processor.IRowDataProcessor;
import pers.linhai.nature.j2ee.core.dao.resulthandler.RowDataEntityResultHandler;
import pers.linhai.nature.j2ee.core.dao.resulthandler.RowDataHashMapResultHandler;
import pers.linhai.nature.j2ee.core.exception.MapperException;
import pers.linhai.nature.j2ee.core.model.BaseEntity;
import pers.linhai.nature.j2ee.core.model.BaseQuery;
import pers.linhai.nature.j2ee.core.model.EntityBean;
import pers.linhai.nature.j2ee.core.model.Where;
import pers.linhai.nature.j2ee.core.model.Where.Condition;
import pers.linhai.nature.reflect.ConstructorAccess;


/**
 * <p>ClassName      : BaseMapperImpl</p>
 * @author lilinhai 2018年2月12日 下午1:37:24
 * @version 1.0
 */
@SuppressWarnings("unchecked")
public class BaseMapperImpl<Key extends Serializable, Entity extends BaseEntity<Key>, EntityQuery extends BaseQuery> implements IBaseMapper<Key, Entity, EntityQuery>
{
    /**
     * 父接口的命名空间
     */
    private static final String baseNamespace = IBaseMapper.class.getName();
    
    /**
     * 保存方法
     */
    private static final String SAVE = baseNamespace.concat(".save");
    
    /**
     * 删除方法
     */
    private static final String DELETE = baseNamespace.concat(".delete");
    
    /**
     * 修改方法
     */
    private static final String UPDATE = baseNamespace.concat(".update");
    
    /**
     * 查询方法
     */
    private static final String FIND = baseNamespace.concat(".find");
    
    /**
     * 计数方法
     */
    private static final String COUNT = baseNamespace.concat(".count");
    
    /**
     * 汇总计算方法
     */
    private static final String SUM = baseNamespace.concat(".sum");
    
    /**
     * 日志记录器
     */
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected SqlSessionTemplate sqlSessionTemplate;
    
    /**
     * mybatis-mapper的命名空间
     */
    protected String namespace;

    /**
     * 实体的所有字段列表
     */
    protected final Map<String, Field> fieldMap = new HashMap<String, Field>();

    /**
     * 实体构造函器
     */
    protected ConstructorAccess<Entity> entityConstructor;

    /**
     * 实体查询条件构造器
     */
    protected ConstructorAccess<EntityQuery> entityQueryConstructor;

    /**
     * <p>Title        : BaseMapperImpl lilinhai 2018年2月12日 下午2:12:25</p>
     * @param namespace 
     */
    public BaseMapperImpl()
    {
        try
        {
            Class<?> interfaces[] = getClass().getInterfaces();
            for (Class<?> inte : interfaces)
            {
                if (inte.getName().endsWith("Mapper") && !inte.getName().endsWith("BaseMapper"))
                {
                    this.namespace = inte.getName();
                    break;
                }
            }

            if (this.namespace == null)
            {
                throw new MapperException("BaseMapperImpl instance inited fail, the namespace is null.");
            }

            ParameterizedType parameterizedType = (ParameterizedType)getClass().getGenericSuperclass();
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            for (Type type : actualTypeArguments)
            {
                if (type instanceof Class)
                {
                    Class<?> c = (Class<?>)type;
                    if (c.getSuperclass() == BaseEntity.class)
                    {
                        entityConstructor = ConstructorAccess.get((Class<Entity>)c);
                        mapField(fieldMap, c.getDeclaredFields());
                        mapField(fieldMap, BaseEntity.class.getDeclaredFields());
                    }
                    else if (c.getSuperclass() == BaseQuery.class)
                    {
                        entityQueryConstructor = ConstructorAccess.get((Class<EntityQuery>)c);
                    }
                }
            }
        }
        catch (Throwable e)
        {
            logger.error("BaseMapperImpl instance inited fail ", e);
            throw new MapperException("BaseMapperImpl instance inited fail ", e);
        }
    }

    /** 
     * <p>Overriding Method: lilinhai 2018年2月12日 下午1:37:43</p>
     * <p>Title: insert</p>
     * @param record
     * @return 
     * @see com.meme.crm.dao.core.IBaseMapper#save(com.meme.crm.model.core.BaseEntity)
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
            
            return sqlSessionTemplate.insert(SAVE, record);
        }
        catch (Throwable e1)
        {
            logger.error("IBaseMapper.save(Entity record) occor an error " , e1);
            throw new MapperException(e1);
        }
    }

    /**
     * 根据主键删除
     * <p>Overriding Method: lilinhai 2018年2月13日 下午2:02:10</p>
     * <p>Title: delete</p>
     * @param id
     * @return 
     * @see com.meme.crm.dao.core.IBaseMapper#delete(java.io.Serializable)
     */
    public int delete(Key id)
    {
        try
        {
            EntityQuery eq = entityQueryConstructor.newInstance();
            Where where = new Where();
            List<Condition> conditionList = new ArrayList<Condition>();
            Condition con = new Condition();
            con.setFieldName("id");
            con.setOperator("=");
            con.setValue(id);
            conditionList.add(con);
            where.setConditionList(conditionList);
            eq.setWhere(where);
            return delete(eq);
        }
        catch (Throwable e)
        {
            logger.error("IBaseMapper.delete(Key id)", e);
            throw new MapperException(e);
        }
    }

    /**
     * 根据查询条件entityQuery删除
     * <p>Overriding Method: lilinhai 2018年2月13日 下午2:14:37</p>
     * <p>Title: delete</p>
     * @param entityQuery
     * @return 
     * @see com.meme.crm.dao.core.IBaseMapper#delete(com.meme.crm.model.core.BaseQuery)
     */
    public int delete(EntityQuery entityQuery)
    {
        return sqlSessionTemplate.delete(DELETE, entityQuery);
    }
    
    /** 
     * <p>Overriding Method: lilinhai 2018年2月12日 下午1:37:43</p>
     * <p>Title: updateByPrimaryKeySelective</p>
     * @param record
     * @return 
     * @see com.meme.crm.dao.core.IBaseMapper#update(com.meme.crm.model.core.BaseEntity)
     */
    public int update(Entity record)
    {
        try
        {
            // 若ID和where条件都为空，则修改失败，返回0
            if ((record.getId() == null && record.getWhere() == null) || !record.hasPersistentField())
            {
                return 0;
            }
            
            // 去除ID字段的更新
            record.removePersistentField("id");
            
            if (record.getUpdateTime() == null)
            {
                // 刷新修改时间
                record.setUpdateTime(new Date());
            }

            // 如果修改条件为空
            if (record.getWhere() == null)
            {
                Where where = new Where();
                List<Condition> conditionList = new ArrayList<Condition>();
                Condition con = new Condition();
                con.setFieldName("id");
                con.setOperator("=");
                con.setValue(record.getId());
                conditionList.add(con);
                where.setConditionList(conditionList);
                record.setWhere(where);
            }
            
            return sqlSessionTemplate.update(UPDATE, record);
        }
        catch (Throwable e1)
        {
            logger.error("IBaseMapper.update(Entity record) occor an error ", e1);
            throw new MapperException(e1);
        }
    }

    /** 
     * <p>Overriding Method: lilinhai 2018年2月12日 下午1:37:43</p>
     * <p>Title: selectByPrimaryKey</p>
     * @param id
     * @return 
     * @see com.meme.crm.dao.core.IBaseMapper#get(java.io.Serializable)
     */
    public Entity get(Key id)
    {
        try
        {
            EntityQuery eq = entityQueryConstructor.newInstance();
            Where where = new Where();
            List<Condition> conditionList = new ArrayList<Condition>();
            Condition con = new Condition();
            con.setFieldName("id");
            con.setOperator("=");
            con.setValue(id);
            conditionList.add(con);
            where.setConditionList(conditionList);
            eq.setWhere(where);
            return get(eq);
        }
        catch (Throwable e)
        {
            logger.error("IBaseMapper.get(Key id)", e);
        }
        return null;
    }
    
    public EntityBean get(Key id, DefaultRowDataProcessor<Entity> entityProcessor)
    {
        try
        {
            EntityQuery eq = entityQueryConstructor.newInstance();
            Where where = new Where();
            List<Condition> conditionList = new ArrayList<Condition>();
            Condition con = new Condition();
            con.setFieldName("id");
            con.setOperator("=");
            con.setValue(id);
            conditionList.add(con);
            where.setConditionList(conditionList);
            eq.setWhere(where);
            return get(eq, entityProcessor);
        }
        catch (Throwable e)
        {
            logger.error("IBaseMapper.get(Key id, DefaultRowDataProcessor<Key, Entity> entityProcessor)", e);
        }
        return null;
    }

    /** 
     * <p>Overriding Method: lilinhai 2018年2月12日 下午1:37:43</p>
     * <p>Title: query</p>
     * @param entityQueryBean
     * @return 
     * @see com.meme.crm.dao.core.IBaseMapper#get(com.meme.crm.model.core.BaseQuery)
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
     * <p>Overriding Method: lilinhai 2018年2月12日 下午1:37:43</p>
     * <p>Title: query</p>
     * @param entityQueryBean
     * @return 
     * @see com.meme.crm.dao.core.IBaseMapper#get(com.meme.crm.model.core.BaseQuery)
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
     * <p>Overriding Method: lilinhai 2018年2月12日 下午1:37:43</p>
     * <p>Title: queryCount</p>
     * @param entityQuery
     * @return 
     * @see com.meme.crm.dao.core.IBaseMapper#count
     */
    @Override
    public long count(EntityQuery entityQuery)
    {
        final AtomicLong al = new AtomicLong();
        sqlSessionTemplate.select(COUNT, entityQuery, new ResultHandler<Long>()
        {
            public void handleResult(ResultContext<? extends Long> resultContext)
            {
                al.set(resultContext.getResultObject());
            }
        });
        return al.get();
    }
    
    /** 
     * <p>Overriding Method: lilinhai 2018年2月12日 下午1:37:43</p>
     * <p>Title: queryCount</p>
     * @param entityQuery
     * @return 
     * @see com.meme.crm.dao.core.IBaseMapper#sum
     */
    public long sum(EntityQuery entityQuery)
    {
        final AtomicLong al = new AtomicLong();
        sqlSessionTemplate.select(SUM, entityQuery, new ResultHandler<Long>()
        {
            public void handleResult(ResultContext<? extends Long> resultContext)
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
        RowDataHashMapResultHandler<Key, Entity> myResultHandler = new RowDataHashMapResultHandler<Key, Entity>(fieldMap, entityConstructor, entityProcessor);
        sqlSessionTemplate.select(FIND, entityQuery, myResultHandler);
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
        sqlSessionTemplate.select(namespace.concat(".").concat(statment), params, myResultHandler);
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
    public Entity findOne(String statment, Object params)
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
        sqlSessionTemplate.select(namespace.concat(".").concat(statment), params, myResultHandler);
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

    /**
     * 将实体所有字段建立hashMap映射
     * <p>Title         : mapField lilinhai 2018年2月13日 上午11:29:47</p>
     * @param javaFieldMap
     * @param fs 
     * void
     */
    private void mapField(Map<String, Field> javaFieldMap, Field[] fs)
    {
        for (Field field : fs)
        {
            // 静态成员跳过处理
            if (Modifier.isStatic(field.getModifiers()))
            {
                continue;
            }

            if (",persistentFieldMap,".contains(field.getName()))
            {
                continue;
            }
            field.setAccessible(true);
            javaFieldMap.put(field.getName(), field);
        }
    }
}
