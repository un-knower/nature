/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : DefaultEntityProcessor.java</p>
 * <p>Package     : com.meme.crm.dao.core</p>
 * @Creator lilinhai 2018年2月13日 下午12:22:43
 * @Version  V1.0  
 */

package pers.linhai.nature.j2ee.core.dao.processor.impls;

import java.util.ArrayList;
import java.util.List;

import pers.linhai.nature.j2ee.core.dao.processor.IEntityQueryRowDataProcessor;
import pers.linhai.nature.j2ee.core.model.BaseEntity;
import pers.linhai.nature.j2ee.core.model.EntityBean;

/**
 * <p>Description    : <pre>TODO(这里用一句话描述这个类的作用)</pre></p>
 * <p>ClassName      : DefaultEntityProcessor</p>
 * @author lilinhai 2018年2月13日 下午12:22:43
 * @version 1.0
 */
public class DefaultEntityQueryRowDataProcessor<Entity extends BaseEntity< ? >> implements IEntityQueryRowDataProcessor<Entity>
{
    
    private List<Entity> entityList = new ArrayList<Entity>();
    
    private List<EntityBean> entityBeanList = new ArrayList<EntityBean>();
    
    /**
     * <p>Title        : DefaultRowDataProcessor lilinhai 2018年3月14日 下午11:37:39</p>
     * <p>Description  : <pre>TODO(这里用一句话描述这个方法的作用)</pre></p> 
     */
    public DefaultEntityQueryRowDataProcessor()
    {
    }
    
    /** 
     * <p>Overriding Method: lilinhai 2018年2月13日 下午12:23:02</p>
     * <p>Title: process</p>
     * @param entity 
     * @see com.meme.crm.dao.core.IEntityQueryRowDataProcessor#preUpdate(com.meme.crm.model.core.BaseEntity)
     */
    public void process(EntityBean entityBean, Entity entity)
    {
        entityList.add(entity);
        entityBeanList.add(entityBean);
    }
    
    /**
     * <p>Get Method   :   entityList List<Entity></p>
     * @return entityList
     */
    public List<Entity> getEntityList()
    {
        return entityList;
    }
    
    /**
     * <p>Get Method   :   entityBeanList List<EntityBean></p>
     * @return entityBeanList
     */
    public List<EntityBean> getEntityBeanList()
    {
        return entityBeanList;
    }
}
