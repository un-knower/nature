/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : JointQueryEntityBean.java</p>
 * <p>Package     : pers.linhai.nature.j2ee.core.model</p>
 * @Creator lilinhai 2018年6月2日 下午11:25:23
 * @Version  V1.0  
 */ 

package pers.linhai.nature.j2ee.core.model.join;

import java.io.Serializable;

import pers.linhai.nature.j2ee.core.model.EntityBean;
import pers.linhai.nature.j2ee.core.model.exception.JointQueryException;

/**
 * 关联查询的实体bean
 * <p>ClassName      : JointQueryEntityBean</p>
 * @author lilinhai 2018年6月2日 下午11:25:23
 * @version 1.0
 */
public class JointQueryResultBean extends EntityBean
{

    /**
     * <p>Info          : long serialVersionUID lilinhai 2018年6月2日 下午11:25:35</p>
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 关联的实体bean-map
     */
    private JointEntityBean jointEntityBean = new JointEntityBean();
    
    /** 
     * <p>Overriding Method: lilinhai 2018年6月2日 下午11:28:06</p>
     * <p>Title: put</p>
     * @param column
     * @param value
     * @return 
     * @see pers.linhai.nature.j2ee.core.model.EntityBean#put(java.lang.String, java.io.Serializable)
     */ 
    public Serializable put(String column, Serializable value)
    {
        int dotIndex = column.indexOf('@');
        if (dotIndex == -1)
        {
            throw new JointQueryException("The joint-query was successful, but the result failed because the name of the table was not found in the return-column-name：" + column);
        }
        String entity = column.substring(0, dotIndex);
        String field = column.substring(dotIndex + 1);
        EntityBean entityBean = jointEntityBean.get(entity);
        if (entityBean == null)
        {
            entityBean = new EntityBean();
            jointEntityBean.put(entity, entityBean);
        }
        return entityBean.put(field, value);
    }

    /**
     * <p>Get Method   :   jointEntityBean JointEntityBean</p>
     * @return jointEntityBean
     */
    public JointEntityBean getJointEntityBean()
    {
        return jointEntityBean;
    }
}
