/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : BaseServiceImpl.java</p>
 * <p>Package     : com.meme.crm.service.core</p>
 * @Creator lilinhai 2018年2月5日 下午2:59:34
 * @Version  V1.0  
 */ 

package pers.linhai.nature.j2ee.core.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import pers.linhai.nature.j2ee.core.dao.IBaseMapper;
import pers.linhai.nature.j2ee.core.model.BaseEntity;
import pers.linhai.nature.j2ee.core.model.BaseQuery;
import pers.linhai.nature.j2ee.core.model.EntityBean;

/**
 * service基类实现类
 * <p>ClassName      : BaseServiceImpl</p>
 * @author lilinhai 2018年2月5日 下午3:04:25
 * @version 1.0
 */
public abstract class BaseEntityServiceImpl<Key extends Serializable, Entity extends BaseEntity<Key>, EntityQuery extends BaseQuery, Mapper extends IBaseMapper<Key, Entity, EntityQuery>> 
    extends BaseService implements IBaseEntityService<Key, Entity, EntityQuery>
{

    @Autowired
    protected Mapper mapper;
    
    /**
     * 过滤EntityBeanMap中的一些敏感字段，不需要传递到前端去的字段
     * <p>Title         : entityBeanMapFilter lilinhai 2018年2月10日 下午4:25:09</p>
     * @param entityBeanMap 
     * void
     */
    protected void entityBeanMapFilter(Map<String, Serializable> entityMap, Entity entity){}

    public int delete(Key id)
    {
        try
        {
            return mapper.delete(id);
        }
        catch (Throwable e)
        {
            logger.error("[Service] deleteByPrimaryKey occor an error", e);
            return -1;
        }
    }

    public int save(Entity record)
    {
        try
        {
            return mapper.save(record);
        }
        catch (Throwable e)
        {
            logger.error("[Service] insert occor an error", e);
            return -1;
        }
    }

    public int saveSelective(Entity record)
    {
        try
        {
            return mapper.saveSelective(record);
        }
        catch (Throwable e)
        {
            logger.error("[Service] insertSelective occor an error", e);
            return -1;
        }
    }

    public EntityBean find(Key id)
    {
        try
        {
            Entity entity = mapper.find(id);
            if (entity == null)
            {
                return null;
            }
            return new EntityBean(entity);
        }
        catch (Throwable e)
        {
            logger.error("[Service] selectByPrimaryKey occor an error", e);
            return null;
        }
    }

    public int updateSelective(Entity record)
    {
        try
        {
            return mapper.updateSelective(record);
        }
        catch (Throwable e)
        {
            logger.error("[Service] updateByPrimaryKeySelective occor an error", e);
            return -1;
        }
    }

    public int update(Entity record)
    {
        try
        {
            return mapper.update(record);
        }
        catch (Throwable e)
        {
            logger.error("[Service] updateByPrimaryKey occor an error", e);
            return -1;
        }
    }
    
    /**
     * 分页查询
     * <p>Title         : pageQuery lilinhai 2018年2月7日 下午6:36:29</p>
     * @param entityQuery
     * @return 
     * PaginationData<EntityBean>
     */
    public PaginationData<EntityBean> pageQuery(EntityQuery entityQuery)
    {
        try
        {
            PaginationData<EntityBean> pageData = new PaginationData<EntityBean>();
            int page = entityQuery.getPage() == null ? 0 : entityQuery.getPage();
            int size = entityQuery.getSize() == null ? 20 : entityQuery.getSize();
            pageData.setPage(page);
            pageData.setSize(size);
            pageData.setTotal(mapper.count(entityQuery));
            List<Entity> entityList = mapper.find(entityQuery);
            EntityBean entityBean = null;
            for (Entity entity : entityList)
            {
                entityBean = new EntityBean(entity);
                
                // 过滤EntityBeanMap中的一些敏感字段，不需要传递到前端去的字段
                entityBeanMapFilter(entityBean, entity);
                pageData.addData(entityBean);
            }
            return pageData;
        }
        catch (Throwable e)
        {
            logger.error("[Service] pageQuery occor an error", e);
            return null;
        }
    }
    
    /**
     * 组合查询记录，返回集合，支持分页
     * <p>Title         : query lilinhai 2018年2月7日 下午5:44:18</p>
     * @param entityQuery
     * @return 
     * List<Entity>
     */
    public List<EntityBean> query(EntityQuery entityQuery)
    {
        try
        {
            List<Entity> entityList = mapper.find(entityQuery);
            List<EntityBean> beanList = new ArrayList<EntityBean>();
            if (entityList != null)
            {
                EntityBean entityBean = null;
                for (Entity entity : entityList)
                {
                    entityBean = new EntityBean(entity);
                    
                    // 过滤EntityBeanMap中的一些敏感字段，不需要传递到前端去的字段
                    entityBeanMapFilter(entityBean, entity);
                    beanList.add(entityBean);
                }
            }
            return beanList;
        }
        catch (Throwable e)
        {
            logger.error("[Service] query occor an error", e);
            return null;
        }
    }
}
