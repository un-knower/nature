/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : EntityReflecter.java</p>
 * <p>Package     : pers.linhai.nature.j2ee.core.dao</p>
 * @Creator lilinhai 2018年3月16日 下午4:10:26
 * @Version  V1.0  
 */

package pers.linhai.nature.j2ee.core.dao;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import pers.linhai.nature.j2ee.core.dao.exception.ReflectException;
import pers.linhai.nature.j2ee.core.model.BaseEntity;
import pers.linhai.nature.j2ee.core.model.EntityBean;

/**
 * <p>ClassName      : EntityReflecter</p>
 * @author lilinhai 2018年3月16日 下午4:10:26
 * @version 1.0
 */
public class EntityReflector<Entity extends BaseEntity< ? >>
{
    
    private static final Map<String, Field> COMMON_FIELD_MAP = new HashMap<String, Field>(3);
    static
    {
        Set<String> excludeFieldSet = new HashSet<String>();
        excludeFieldSet.add("persistentFieldMap");
        parse(BaseEntity.class.getDeclaredFields(), COMMON_FIELD_MAP, excludeFieldSet);
    }
    
    private Map<String, Field> entityFieldMap = new HashMap<String, Field>();
    
    /**
     * 实体构造函器
     */
    private Constructor<Entity> entityConstructor;
    
    /**
     * <p>Title        : EntityReflecter lilinhai 2018年3月16日 下午4:11:34</p>
     */
    public EntityReflector(Class<Entity> c)
    {
        try
        {
            entityConstructor = c.getConstructor();
            entityConstructor.setAccessible(true);
            
            parse(c.getDeclaredFields(), entityFieldMap, null);
            entityFieldMap.putAll(COMMON_FIELD_MAP);
        }
        catch (Throwable e)
        {
            throw new ReflectException("Init EntityReflector fail: ", e);
        }
    }
    
    public Entity newInstance()
    {
        try
        {
            return entityConstructor.newInstance();
        }
        catch (Throwable e)
        {
            throw new ReflectException("EntityReflector.newInstance fail: ", e);
        }
    }
    
    public Entity getInstance(EntityBean entityBean)
    {
        try
        {
            Entity entity = newInstance();
            Field field = null;
            for (Entry<String, Serializable> e : entityBean.entrySet())
            {
                if ((field = entityFieldMap.get(e.getKey())) != null)
                {
                    field.set(entity, e.getValue());
                }
            }
            return entity;
        }
        catch (Throwable e)
        {
            throw new ReflectException("EntityReflector.getInstance fail: ", e);
        }
    }
    
    /**
     * 解析
     * <p>Title         : parse lilinhai 2018年2月21日 上午9:13:25</p>
     * @param fs
     * @param fieldList 
     * void
     */
    protected static void parse(Field[] fs, Map<String, Field> fieldMap, Set<String> excludeFieldSet)
    {
        for (Field field : fs)
        {
            // 静态成员跳过处理
            if (Modifier.isStatic(field.getModifiers()))
            {
                continue;
            }
            
            if (excludeFieldSet != null && excludeFieldSet.contains(field.getName()))
            {
                continue;
            }
            
            field.setAccessible(true);
            fieldMap.put(field.getName(), field);
        }
    }
}
