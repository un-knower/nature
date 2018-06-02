/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : EntityReflecter.java</p>
 * <p>Package     : pers.linhai.nature.j2ee.core.dao</p>
 * @Creator lilinhai 2018年3月16日 下午4:10:26
 * @Version  V1.0  
 */

package pers.linhai.nature.j2ee.core.dao;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

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
    
    /**
     * 实体构造函器
     */
    private Constructor<Entity> entityConstructor;
    
    /**
     * 方法访问器
     */
    private Method method;
    
    /**
     * <p>Title        : EntityReflecter lilinhai 2018年3月16日 下午4:11:34</p>
     */
    public EntityReflector(Class<Entity> c)
    {
        try
        {
            entityConstructor = c.getConstructor();
            entityConstructor.setAccessible(true);
            method = c.getDeclaredMethod("initialize", EntityBean.class);
            method.setAccessible(true);
        }
        catch (Throwable e)
        {
            throw new ReflectException("Init EntityReflector fail: ", e);
        }
    }
    
    public Entity getInstance(EntityBean entityBean)
    {
        try
        {
            Entity entity = entityConstructor.newInstance();
            method.invoke(entity, entityBean);
            return entity;
        }
        catch (Throwable e)
        {
            throw new ReflectException("EntityReflector.getInstance fail: ", e);
        }
    }
}
