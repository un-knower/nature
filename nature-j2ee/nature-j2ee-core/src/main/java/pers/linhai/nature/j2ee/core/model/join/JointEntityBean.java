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
     * <p>Overriding Method: lilinhai 2018年6月2日 下午11:28:06</p>
     * <p>Title: put</p>
     * @param column
     * @param value
     * @return 
     * @see pers.linhai.nature.j2ee.core.model.EntityBean#put(java.lang.String, java.io.Serializable)
     */ 
    Object put(String column, Object value)
    {
        int dotIndex = column.indexOf('@');
        if (dotIndex == -1)
        {
            throw new JointQueryException("The joint-query was successful, but the result failed because the name of the table was not found in the return-column-name：" + column);
        }
        String entityProperty = NamerUtils.classToProperty(column.substring(0, dotIndex));
        EntityBean entityBean = super.get(entityProperty);
        if (entityBean == null)
        {
            entityBean = new EntityBean();
            super.put(entityProperty, entityBean);
        }
        return entityBean.put(column.substring(dotIndex + 1), value);
    }
    
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
}
