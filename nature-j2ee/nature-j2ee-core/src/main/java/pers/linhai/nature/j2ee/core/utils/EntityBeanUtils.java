/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : EntityBeanUtils.java</p>
 * <p>Package     : pers.linhai.nature.j2ee.core.utils</p>
 * @Creator lilinhai 2018年3月13日 下午8:06:48
 * @Version  V1.0  
 */ 

package pers.linhai.nature.j2ee.core.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import pers.linhai.nature.j2ee.core.model.BaseEntity;
import pers.linhai.nature.j2ee.core.model.EntityBean;

/**
 * <p>ClassName      : EntityBeanUtils</p>
 * @author lilinhai 2018年3月13日 下午8:06:48
 * @version 1.0
 */
public abstract class EntityBeanUtils
{

    public static interface IEntityBeanProcessor
    {
        void process(EntityBean entityBean);
    }
    
    /**
     * 转换实体为EntityBean
     * <p>Title         : transfer lilinhai 2018年3月13日 下午8:17:56</p>
     * @param tList
     * @return 
     * List<EntityBean>
     */
    public static <Key extends Serializable, T extends BaseEntity<Key>> EntityBean transfer(T t)
    {
        return transfer(t, null);
    }
    
    /**
     * 转换实体为EntityBean
     * <p>Title         : transfer lilinhai 2018年3月13日 下午8:17:56</p>
     * @param tList
     * @return 
     * List<EntityBean>
     */
    public static <Key extends Serializable, T extends BaseEntity<Key>> EntityBean transfer(T t, IEntityBeanProcessor entityBeanProcessor)
    {
        EntityBean entityBean = new EntityBean(t);
        if (entityBeanProcessor != null)
        {
            entityBeanProcessor.process(entityBean);
        }
        return entityBean;
    }
    
    /**
     * 转换实体为EntityBean
     * <p>Title         : transfer lilinhai 2018年3月13日 下午8:17:56</p>
     * @param tList
     * @param entityBeanProcessor
     * @return 
     * List<EntityBean>
     */
    public static <Key extends Serializable, T extends BaseEntity<Key>> List<EntityBean> transfer(List<T> tList, IEntityBeanProcessor entityBeanProcessor)
    {
        List<EntityBean> entityBeanList = new ArrayList<EntityBean>();
        for (T t : tList)
        {
            entityBeanList.add(transfer(t, entityBeanProcessor));
        }
        return entityBeanList;
    }
    
    /**
     * 转换实体为EntityBean
     * <p>Title         : transfer lilinhai 2018年3月13日 下午8:17:56</p>
     * @param tList
     * @return 
     * List<EntityBean>
     */
    public static <Key extends Serializable, T extends BaseEntity<Key>> List<EntityBean> transfer(List<T> tList)
    {
        return transfer(tList, null);
    }
}
