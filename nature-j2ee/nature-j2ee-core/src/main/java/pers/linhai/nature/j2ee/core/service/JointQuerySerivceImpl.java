/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : JointQuerySerivceImpl.java</p>
 * <p>Package     : pers.linhai.nature.j2ee.core.service</p>
 * @Creator lilinhai 2018年6月3日 下午7:53:05
 * @Version  V1.0  
 */ 

package pers.linhai.nature.j2ee.core.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pers.linhai.nature.j2ee.core.constant.BaseErrorCode;
import pers.linhai.nature.j2ee.core.dao.IJointQueryMapper;
import pers.linhai.nature.j2ee.core.dao.processor.JointQueryRowDataProcessor;
import pers.linhai.nature.j2ee.core.model.JointQuery;
import pers.linhai.nature.j2ee.core.model.join.JointEntityBean;
import pers.linhai.nature.j2ee.core.model.join.TableJointor;
import pers.linhai.nature.j2ee.core.service.exception.ServiceException;
import pers.linhai.nature.j2ee.core.spring.BeanFactory;
import pers.linhai.nature.utils.StringUtils;

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
     * 关联查询通用方法
     * <p>Title         : findEntityBean lilinhai 2018年6月3日 下午8:09:40</p>
     * @param jointQuery
     * @return 
     * List<JointEntityBean>
     */
    public List<JointEntityBean> find(JointQuery jointQuery)
    {
        try
        {
            JointQueryRowDataProcessor jointQueryRowDataProcessor = validQuery(jointQuery);
            
            jointQueryMapper.find(jointQuery, jointQueryRowDataProcessor);
            return jointQueryRowDataProcessor.getJointEntityBeanList();
        }
        catch (Throwable e)
        {
            logger.error("List<JointEntityBean> find(JointQuery jointQuery): " + jointQuery, e);
            if (e instanceof ServiceException)
            {
                throw e;
            }
            throw new ServiceException(BaseErrorCode.JOINT_QUERY_PROCESS_ERROR, "Failure of joint-query result processing.");
        }
    }

    /**
     * 校验查询对象是否合法
     * <p>Title         : validQuery lilinhai 2018年6月4日 下午4:53:59</p>
     * @param jointQuery
     * @return 
     * JointQueryRowDataProcessor 
     */ 
    private JointQueryRowDataProcessor validQuery(JointQuery jointQuery)
    {
        // 校验关联查询的结果处理器是否有定义
        if (StringUtils.isEmpty(jointQuery.getProcessorId()))
        {
            throw new ServiceException(BaseErrorCode.JOINT_QUERY_PROCESSORID_UNDEFINED, "Joint-Query Processor ID is undefined.");
        }
        
        // 解密并得到关联查询结果处理器
        JointQueryRowDataProcessor jointQueryRowDataProcessor = BeanFactory.getBean(jointQuery.getProcessorId(), JointQueryRowDataProcessor.class);
        if (jointQueryRowDataProcessor == null)
        {
            throw new ServiceException(BaseErrorCode.JOINT_QUERY_PROCESSORID_ILLEGAL, "Joint-Query Processor ID is illegal.");
        }
        
        for (TableJointor tableJointor : jointQuery.getTableJointorList())
        {
            if (!jointQueryRowDataProcessor.canProcess(tableJointor.getLeft().getEntity()) || !jointQueryRowDataProcessor.canProcess(tableJointor.getRight().getEntity()))
            {
                throw new ServiceException(BaseErrorCode.JOINT_QUERY_PROCESSORID_NOT_SUPPORTED, "The processor cannot handle a given entity: " + tableJointor.getLeft().getEntity() + ", " + tableJointor.getRight().getEntity());
            }
        }
        return jointQueryRowDataProcessor;
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
