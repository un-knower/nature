/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : DefaultEntityProcessor.java</p>
 * <p>Package     : com.meme.crm.dao.core</p>
 * @Creator lilinhai 2018年2月13日 下午12:22:43
 * @Version  V1.0  
 */

package pers.linhai.nature.j2ee.core.dao.processor;

import java.util.ArrayList;
import java.util.List;

import pers.linhai.nature.j2ee.core.model.BaseEntity;

/**
 * <p>Description    : <pre>TODO(这里用一句话描述这个类的作用)</pre></p>
 * <p>ClassName      : DefaultEntityProcessor</p>
 * @author lilinhai 2018年2月13日 下午12:22:43
 * @version 1.0
 */
public class DefaultEntityProcessor<Entity extends BaseEntity< ? >> implements IEntityProcessor<Entity>
{
    
    private List<Entity> entityList = new ArrayList<Entity>();
    
    /** 
     * <p>Overriding Method: lilinhai 2018年2月13日 下午12:23:02</p>
     * <p>Title: process</p>
     * @param entity 
     * @see com.meme.crm.dao.core.IRowDataProcessor#preUpdate(com.meme.crm.model.core.BaseEntity)
     */
    public void process(Entity entity)
    {
        entityList.add(entity);
    }
    
    /**
     * <p>Get Method   :   entityList List<Entity></p>
     * @return entityList
     */
    public List<Entity> getEntityList()
    {
        return entityList;
    }
}
