/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : IEntityHandler.java</p>
 * <p>Package     : com.meme.crm.dao.core</p>
 * @Creator lilinhai 2018年2月13日 下午12:15:34
 * @Version  V1.0  
 */

package pers.linhai.nature.j2ee.core.dao.processor;

import java.util.ArrayList;
import java.util.List;

import pers.linhai.nature.j2ee.core.model.join.JointEntityBean;

/**
 * <p>ClassName      : IRowDataJointQueryProcessor</p>
 * @author lilinhai 2018年6月13日 下午12:15:34
 * @version 1.0
 */
public abstract class JointQueryRowDataProcessor
{

    protected List<JointEntityBean> jointEntityBeanList = new ArrayList<JointEntityBean>();
    
    public void addJointEntityBean(JointEntityBean jointEntityBean)
    {
        jointEntityBeanList.add(jointEntityBean);
    }

    /**
     * <p>Get Method   :   jointEntityBeanList List<JointEntityBean></p>
     * @return jointEntityBeanList
     */
    public List<JointEntityBean> getJointEntityBeanList()
    {
        return jointEntityBeanList;
    }
}
