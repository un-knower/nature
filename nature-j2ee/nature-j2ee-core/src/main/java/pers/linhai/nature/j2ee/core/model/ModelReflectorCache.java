/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : ModelHelperCache.java</p>
 * <p>Package     : pers.linhai.nature.j2ee.core.model</p>
 * @Creator lilinhai 2018年5月31日 下午2:12:11
 * @Version  V1.0  
 */ 

package pers.linhai.nature.j2ee.core.model;

import java.util.HashMap;
import java.util.Map;

import pers.linhai.nature.j2ee.core.dao.EntityReflector;

/**
 * 实体反射器缓存
 * <p>ClassName      : ModelHelperCache</p>
 * @author lilinhai 2018年5月31日 下午2:12:11
 * @version 1.0
 */
public abstract class ModelReflectorCache
{
    
    private static final ModelReflectorCache CACHE = new ModelReflectorCache() {};
    
    private Map<Class<? extends BaseEntity<?>>, EntityReflector<? extends BaseEntity<?>>> entityReflectorMap = new HashMap<Class<? extends BaseEntity<?>>, EntityReflector<? extends BaseEntity<?>>>();
    
    private ModelReflectorCache()
    {
        
    }
    
    public void put(Class<? extends BaseEntity<?>> c, EntityReflector<? extends BaseEntity<?>> entityReflector)
    {
        entityReflectorMap.put(c, entityReflector);
    }
    
    /**
     * 获取实体的帮助器
     * <p>Title         : get lilinhai 2018年5月31日 下午3:23:02</p>
     * @param key
     * @return 
     * ModelHelper
     */
    public EntityReflector<? extends BaseEntity<?>> get(Class<?> c)
    {
        return entityReflectorMap.get(c);
    }
    
    public static ModelReflectorCache getInstance()
    {
        return CACHE;
    }
}
