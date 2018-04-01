/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : EntityRestApiCache.java</p>
 * <p>Package     : pers.linhai.nature.j2ee.generator.restapi</p>
 * @Creator lilinhai 2018年4月1日 下午9:08:10
 * @Version  V1.0  
 */ 

package pers.linhai.nature.j2ee.generator.restapi;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>ClassName      : EntityRestApiCache</p>
 * @author lilinhai 2018年4月1日 下午9:08:10
 * @version 1.0
 */
public abstract class EntityRestApiCache
{
    private static final EntityRestApiCache CACHE = new EntityRestApiCache()
    {};
    
    private List<EntityRestApi> entityRestApiList = new ArrayList<EntityRestApi>();
    
    private EntityRestApiCache()
    {
        
    }
    
    public static EntityRestApiCache getInstance()
    {
        return CACHE;
    }

    /**
     * <p>Get Method   :   entityRestApiList List<EntityRestApi></p>
     * @return entityRestApiList
     */
    public List<EntityRestApi> getEntityRestApiList()
    {
        return entityRestApiList;
    }

    /**
     * <p>Set Method   :   entityRestApiList List<EntityRestApi></p>
     * @param entityRestApiList
     */
    public void addEntityRestApi(EntityRestApi entityRestApi)
    {
        this.entityRestApiList.add(entityRestApi);
    }
}
