/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : JointQuerySerivceImpl.java</p>
 * <p>Package     : pers.linhai.nature.j2ee.core.service</p>
 * @Creator lilinhai 2018年6月3日 下午7:53:05
 * @Version  V1.0  
 */ 

package pers.linhai.nature.j2ee.core.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pers.linhai.nature.j2ee.core.dao.IJointQueryMapper;
import pers.linhai.nature.j2ee.core.dao.processor.impls.DefaultJointQueryRowDataProcessorImpl;
import pers.linhai.nature.j2ee.core.model.JointQuery;
import pers.linhai.nature.j2ee.core.model.join.JointEntityBean;

/**
 * 关联查询查询业务逻辑层
 * <p>ClassName      : JointQuerySerivceImpl</p>
 * @author lilinhai 2018年6月3日 下午7:53:05
 * @version 1.0
 */
@Service
public class JointQuerySerivceImpl implements IJointQuerySerivce
{
    
    /**
     * 日志记录器
     */
    protected Logger logger = LoggerFactory.getLogger(IJointQuerySerivce.class);
    
    @Autowired
    private IJointQueryMapper jointQueryMapper;
    
    /**
     * 查询方法
     * <p>Title         : findEntityBean lilinhai 2018年6月3日 下午8:09:40</p>
     * @param jointQuery
     * @return 
     * List<JointEntityBean>
     */
    public List<JointEntityBean> find(JointQuery jointQuery)
    {
        try
        {
            DefaultJointQueryRowDataProcessorImpl jointQueryRowDataProcessor = new DefaultJointQueryRowDataProcessorImpl();
            jointQueryMapper.find(jointQuery, jointQueryRowDataProcessor);
            return jointQueryRowDataProcessor.getJointEntityBeanList();
        }
        catch (Throwable e)
        {
            logger.error("List<JointEntityBean> findEntityBean(JointQuery jointQuery): " + jointQuery, e);
            return new ArrayList<JointEntityBean>(0);
        }
    }
    
    /**
     * 统计条目
     * <p>Title         : count lilinhai 2018年6月3日 下午8:09:34</p>
     * @param jointQuery
     * @return 
     * long
     */
    public long count(JointQuery jointQuery)
    {
        return jointQueryMapper.count(jointQuery);
    }
}
