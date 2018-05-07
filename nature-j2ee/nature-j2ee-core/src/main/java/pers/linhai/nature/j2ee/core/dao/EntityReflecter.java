/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : EntityReflecter.java</p>
 * <p>Package     : pers.linhai.nature.j2ee.core.dao</p>
 * @Creator lilinhai 2018年3月16日 下午4:10:26
 * @Version  V1.0  
 */

package pers.linhai.nature.j2ee.core.dao;

import pers.linhai.nature.j2ee.core.model.BaseEntity;
import pers.linhai.nature.j2ee.core.model.EntityBean;
import pers.linhai.nature.reflect.ConstructorAccess;
import pers.linhai.nature.reflect.MethodAccess;

/**
 * <p>ClassName      : EntityReflecter</p>
 * @author lilinhai 2018年3月16日 下午4:10:26
 * @version 1.0
 */
public class EntityReflecter<Entity extends BaseEntity< ? >>
{
    
    /**
     * 实体构造函器
     */
    private ConstructorAccess<Entity> entityConstructor;
    
    /**
     * 方法访问器
     */
    private MethodAccess methodAccess;
    
    /**
     * 初始化方法的索引
     */
    private int initializeMethodIndex;
    
    /**
     * <p>Title        : EntityReflecter lilinhai 2018年3月16日 下午4:11:34</p>
     */
    public EntityReflecter(Class<Entity> c)
    {
        entityConstructor = ConstructorAccess.get(c);
        methodAccess = MethodAccess.get(c);
        initializeMethodIndex = methodAccess.getIndex("initialize");
    }
    
    public Entity getInstance(EntityBean entityBean)
    {
        Entity entity = entityConstructor.newInstance();
        methodAccess.invoke(entity, initializeMethodIndex, entityBean);
        return entity;
    }
}
