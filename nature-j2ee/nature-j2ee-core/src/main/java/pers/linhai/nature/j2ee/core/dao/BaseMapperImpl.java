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
import pers.linhai.nature.j2ee.core.model.JdbcModel.UpdateField;
import pers.linhai.nature.j2ee.core.model.Where;
import pers.linhai.nature.j2ee.core.model.Where.ConditionBean;


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
    private final Map<String, Field> fieldMap = new HashMap<String, Field>();

    /**
     * 实体构造函器
     */
    private Constructor<Entity> entityConstructor;

    /**
     * 实体查询条件构造器
     */
    private Constructor<EntityQuery> entityQueryConstructor;

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
        return save0(record, false);
    }

    /** 
     * <p>Overriding Method: lilinhai 2018年2月12日 下午1:37:43</p>
     * <p>Title: insertSelective</p>
     * @param record
     * @return 
     * @see com.meme.crm.dao.core.IBaseMapper#saveSelective(com.meme.crm.model.core.BaseEntity)
     */
    public int saveSelective(Entity record)
    {
        return save0(record, true);
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
            List<ConditionBean> conditionList = new ArrayList<ConditionBean>();
            ConditionBean con = new ConditionBean();
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
     * @see com.meme.crm.dao.core.IBaseMapper#find(java.io.Serializable)
     */
    public Entity find(Key id)
    {
        try
        {
            EntityQuery eq = entityQueryConstructor.newInstance();
            Where where = new Where();
            List<ConditionBean> conditionList = new ArrayList<ConditionBean>();
            ConditionBean con = new ConditionBean();
            con.setFieldName("id");
            con.setOperator("=");
            con.setValue(id);
            conditionList.add(con);
            where.setConditionList(conditionList);
            eq.setWhere(where);
            return findOne(eq);
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
     * @see com.meme.crm.dao.core.IBaseMapper#find(com.meme.crm.model.core.BaseQuery)
     */
    public Entity findOne(EntityQuery entityQuery)
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
     * @see com.meme.crm.dao.core.IBaseMapper#updateSelective(com.meme.crm.model.core.BaseEntity)
     */
    public int updateSelective(Entity record)
    {
        return update0(record, true);
    }

    /** 
     * <p>Overriding Method: lilinhai 2018年2月12日 下午1:37:43</p>
     * <p>Title: updateByPrimaryKey</p>
     * @param record
     * @return 
     * @see com.meme.crm.dao.core.IBaseMapper#update(com.meme.crm.model.core.BaseEntity)
     */
    public int update(Entity record)
    {
        return update0(record, false);
    }

    /** 
     * <p>Overriding Method: lilinhai 2018年2月12日 下午1:37:43</p>
     * <p>Title: queryCount</p>
     * @param entityQuery
     * @return 
     * @see com.meme.crm.dao.core.IBaseMapper#count(com.meme.crm.model.core.BaseQuery)
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
     * <p>Title: query</p>
     * @param entityQueryBean
     * @return 
     * @see com.meme.crm.dao.core.IBaseMapper#find(com.meme.crm.model.core.BaseQuery)
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
        RowDataResultHandler<Entity> myResultHandler = new RowDataResultHandler<Entity>(fieldMap, entityConstructor, entityProcessor);
        sqlSessionTemplate.select(baseNamespace + ".query", entityQuery, myResultHandler);
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

            if ("where,tableName,updateFieldList".contains(field.getName()))
            {
                continue;
            }
            field.setAccessible(true);
            javaFieldMap.put(entity.getTableField(field.getName()), field);
        }
    }

    /**
     * 修改
     * <p>Title         : update0 lilinhai 2018年2月13日 下午6:17:55</p>
     * @param record
     * @param isSelective
     * @return 
     * int
     */
    private int update0(Entity record, boolean isSelective)
    {

        try
        {
            // 若ID和where条件都为空，则修改失败，返回0
            if (record.getId() == null && record.getWhere() == null)
            {
                return 0;
            }

            if (record.getUpdateFieldList() == null)
            {
                // 待更新的字段集合
                List<UpdateField> updateFieldList = new ArrayList<UpdateField>();

                Object val = null;
                for (Entry<String, Field> e : fieldMap.entrySet())
                {
                    if (e.getKey().equals("id"))
                    {
                        continue;
                    }
                    if ((val = e.getValue().get(record)) == null && isSelective)
                    {
                        continue;
                    }

                    UpdateField fv = new UpdateField();
                    fv.setFieldName(e.getValue().getName());
                    fv.setValue(val);
                    updateFieldList.add(fv);
                }
                record.setUpdateFieldList(updateFieldList);
            }

            // 如果修改条件为空
            if (record.getWhere() == null)
            {
                Where where = new Where();
                List<ConditionBean> conditionList = new ArrayList<ConditionBean>();
                ConditionBean con = new ConditionBean();
                con.setFieldName("id");
                con.setOperator("=");
                con.setValue(record.getId());
                conditionList.add(con);
                where.setConditionList(conditionList);
                record.setWhere(where);
            }
            
            // 刷新修改时间
            record.setUpdateTime(new Date());

            return sqlSessionTemplate.update(baseNamespace + ".update", record);
        }
        catch (Throwable e1)
        {
            logger.error("update0 occor an error, isSelective： " + isSelective, e1);
            return 0;
        }
    }

    /**
     * 保存数据
     * <p>Title         : save lilinhai 2018年2月13日 下午6:13:13</p>
     * @param record
     * @param Selective
     * @return 
     * int
     */
    private int save0(Entity record, boolean isSelective)
    {
        try
        {
            if (record == null)
            {
                return 0;
            }

            // 待更新的字段集合
            List<UpdateField> updateFieldList = new ArrayList<UpdateField>();

            Object val = null;
            for (Entry<String, Field> e : fieldMap.entrySet())
            {
                if ((val = e.getValue().get(record)) == null && isSelective)
                {
                    continue;
                }
                UpdateField fv = new UpdateField();
                fv.setFieldName(e.getValue().getName());
                fv.setValue(val);
                updateFieldList.add(fv);
            }
            record.setUpdateFieldList(updateFieldList);
            
            // 设置创建时间
            record.setCreateTime(new Date());
            return sqlSessionTemplate.update(baseNamespace + ".save", record);
        }
        catch (Throwable e1)
        {
            logger.error("save0 occor an error, isSelective： " + isSelective, e1);
            return 0;
        }
    }
}
