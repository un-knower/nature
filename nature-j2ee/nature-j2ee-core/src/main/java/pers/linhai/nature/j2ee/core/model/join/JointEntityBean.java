/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : JointQueryEntityBean.java</p>
 * <p>Package     : pers.linhai.nature.j2ee.core.model</p>
 * @Creator lilinhai 2018年6月2日 下午11:25:23
 * @Version  V1.0  
 */ 

package pers.linhai.nature.j2ee.core.model.join;

import java.util.HashMap;

import pers.linhai.nature.j2ee.core.model.BaseEntity;
import pers.linhai.nature.j2ee.core.model.EntityBean;
import pers.linhai.nature.j2ee.core.model.exception.JointQueryException;
import pers.linhai.nature.utils.NamerUtils;

/**
 * 关联查询的实体bean
 * <p>ClassName      : JointQueryEntityBean</p>
 * @author lilinhai 2018年6月2日 下午11:25:23
 * @version 1.0
 */
public class JointEntityBean extends HashMap<String, EntityBean>
{

    /**
     * <p>Info          : long serialVersionUID lilinhai 2018年6月2日 下午11:25:35</p>
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 获取一个实体对应的entityBean
     * <p>Title         : get lilinhai 2018年6月5日 上午9:48:45</p>
     * @param entityClass
     * @return 
     * EntityBean
     */
    public EntityBean get(Class<?> entityClass)
    {
        if (entityClass.getSuperclass() != BaseEntity.class)
        {
            throw new JointQueryException("The interface can only receive the class type parameters corresponding to the subclasses of BaseEntity.");
        }
        return super.get(NamerUtils.classToProperty(entityClass.getSimpleName()));
    }
    
    /** 
     * <p>Overriding Method: lilinhai 2018年6月5日 上午9:51:25</p>
     * <p>Title: get</p>
     * @param key
     * @return 
     */ 
    public EntityBean get(String key)
    {
        return super.get(NamerUtils.classToProperty(key));
    }
}
