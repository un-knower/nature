/*
 * <p>Copyright : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p> <p>FileName : BaseMapperImpl.java</p>
 * <p>Package : com.meme.crm.dao.core</p>
 * @Creator lilinhai 2018年2月12日 下午1:37:24
 * @Version V1.0
 */

package pers.linhai.nature.j2ee.core.dao;


import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import pers.linhai.nature.j2ee.core.exception.MapperException;
import pers.linhai.nature.j2ee.core.model.BaseEntity;
import pers.linhai.nature.j2ee.core.model.BaseQuery;
import pers.linhai.nature.j2ee.core.model.JdbcModel.PersistentField;
import pers.linhai.nature.j2ee.core.model.Where;
import pers.linhai.nature.j2ee.core.model.Where.Condition;


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
    private final String baseNamespace = IBaseMapper.class.getName();

    /**
     * 日志记录器
     */
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 
     */
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
    protected Constructor<Entity> entityConstructor;

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
                        entityConstructor = ((Class<Entity>)c).getConstructor();
                        entityConstructor.setAccessible(true);

                        Entity entity = entityConstructor.newInstance();
                        mapField(fieldMap, c.getDeclaredFields(), entity);
                        mapField(fieldMap, BaseEntity.class.getDeclaredFields(), entity);
                    }
                    else if (c.getSuperclass() == BaseQuery.class)
                    {
                        entityQueryConstructor = ((Class<EntityQuery>)c).getConstructor();
                        entityQueryConstructor.setAccessible(true);
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
            if (record == null)
            {
                return 0;
            }

            // 设置创建时间
            record.setCreateTime(new Date());
            
            // 待更新的字段集合
            List<PersistentField> persistentFieldList = new ArrayList<PersistentField>();

            Object val = null;
            for (Entry<String, Field> e : fieldMap.entrySet())
            {
                if ((val = e.getValue().get(record)) == null)
                {
                    continue;
                }
                PersistentField fv = new PersistentField();
                fv.setFieldName(e.getValue().getName());
                fv.setValue(val);
                persistentFieldList.add(fv);
            }
            record.setPersistentFieldList(persistentFieldList);

            return sqlSessionTemplate.update(baseNamespace + ".save", record);
        }
        catch (Throwable e1)
        {
            logger.error("save0 occor an error " , e1);
            return 0;
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
            logger.error("IBaseMapper.deleteByPrimaryKey", e);
        }
        return 0;
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
        return sqlSessionTemplate.delete(baseNamespace + ".delete", entityQuery);
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
            logger.error("IBaseMapper.selectByPrimaryKey", e);
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
        DefaultEntityProcessor<Entity> entityProcessor = new DefaultEntityProcessor<Entity>();
        find(entityQuery, entityProcessor);
        if (entityProcessor.getEntityList().isEmpty())
        {
            return null;
        }
        return entityProcessor.getEntityList().get(0);
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
            if (record.getId() == null && record.getWhere() == null)
            {
                return 0;
            }
            
            if (record.getUpdatedFieldNameSet().isEmpty())
            {
                return 0;
            }
            
            // 刷新修改时间
            record.setUpdateTime(new Date());
            if (record.getPersistentFieldList() == null)
            {
                // 待更新的字段集合
                List<PersistentField> persistentFieldList = new ArrayList<PersistentField>();
                Field field = null;
                for (String fieldName : record.getUpdatedFieldNameSet())
                {
                    field = fieldMap.get(fieldName);
                    PersistentField fv = new PersistentField();
                    fv.setFieldName(field.getName());
                    fv.setValue(field.get(record));
                    persistentFieldList.add(fv);
                }
                record.setPersistentFieldList(persistentFieldList);
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
            
            return sqlSessionTemplate.update(baseNamespace + ".update", record);
        }
        catch (Throwable e1)
        {
            logger.error("update0 occor an error ", e1);
            return 0;
        }
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
        sqlSessionTemplate.select(baseNamespace + ".count", entityQuery, new ResultHandler<Long>()
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
        sqlSessionTemplate.select(baseNamespace + ".sum", entityQuery, new ResultHandler<Long>()
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
        DefaultEntityProcessor<Entity> entityProcessor = new DefaultEntityProcessor<Entity>();
        find(entityQuery, entityProcessor);
        return entityProcessor.getEntityList();
    }

    @Override
    public void find(EntityQuery entityQuery, IEntityProcessor<Entity> entityProcessor)
    {
        RowDataHashMapResultHandler<Key, Entity> myResultHandler = new RowDataHashMapResultHandler<Key, Entity>(fieldMap, entityConstructor, entityProcessor);
        sqlSessionTemplate.select(baseNamespace + ".find", entityQuery, myResultHandler);
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
        RowDataHashMapResultHandler<Key, Entity> myResultHandler = new RowDataHashMapResultHandler<Key, Entity>(fieldMap, entityConstructor, entityProcessor);
        sqlSessionTemplate.select(namespace + "." + statment, params, myResultHandler);
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
        sqlSessionTemplate.select(namespace + "." + statment, params, myResultHandler);
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
    private void mapField(Map<String, Field> javaFieldMap, Field[] fs, Entity entity)
    {
        for (Field field : fs)
        {
            // 静态成员跳过处理
            if (Modifier.isStatic(field.getModifiers()))
            {
                continue;
            }

            if (",persistentFieldList,persistentFieldNameSet,".contains(field.getName()))
            {
                continue;
            }
            field.setAccessible(true);
            javaFieldMap.put(entity.getTableField(field.getName()), field);
        }
    }
}
