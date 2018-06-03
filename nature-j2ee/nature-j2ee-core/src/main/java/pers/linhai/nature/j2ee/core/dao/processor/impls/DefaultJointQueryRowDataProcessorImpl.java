/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : DefaultJointQueryRowDataProcessorImpl.java</p>
 * <p>Package     : pers.linhai.nature.j2ee.core.dao.processor.impls</p>
 * @Creator lilinhai 2018年6月3日 下午7:58:17
 * @Version  V1.0  
 */ 

package pers.linhai.nature.j2ee.core.dao.processor.impls;

import java.util.ArrayList;
import java.util.List;

import pers.linhai.nature.j2ee.core.dao.processor.IJointQueryRowDataProcessor;
import pers.linhai.nature.j2ee.core.model.join.JointEntityBean;

/**
 * 默认的关联查询结果处理器
 * <p>ClassName      : DefaultJointQueryRowDataProcessorImpl</p>
 * @author lilinhai 2018年6月3日 下午7:58:17
 * @version 1.0
 */
public class DefaultJointQueryRowDataProcessorImpl implements IJointQueryRowDataProcessor
{
    
    private List<JointEntityBean> jointEntityBeanList = new ArrayList<JointEntityBean>();
    
    public void process(JointEntityBean jointEntityBean)
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
