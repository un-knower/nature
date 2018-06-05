/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : JointQueryProcessorProducer.java</p>
 * <p>Package     : pers.linhai.nature.j2ee.core.dao</p>
 * @Creator lilinhai 2018年6月5日 下午12:34:55
 * @Version  V1.0  
 */ 

package pers.linhai.nature.j2ee.core.dao;

import pers.linhai.nature.j2ee.core.dao.processor.JointQueryRowDataProcessor;
import pers.linhai.nature.j2ee.core.model.QueryValidator;

/**
 * 抽象的JointQueryProcessor生产者
 * <p>ClassName      : JointQueryProcessorProducer</p>
 * @author lilinhai 2018年6月5日 下午12:34:55
 * @version 1.0
 */
public abstract class JointQueryHelper
{
    
    /**
     * 获取一个结果处理器
     * <p>Title         : getJointQueryRowDataProcessor lilinhai 2018年6月5日 下午12:36:54</p>
     * @return 
     * JointQueryRowDataProcessor
     */
    public abstract JointQueryRowDataProcessor getJointQueryRowDataProcessor();
    
    /**
     * 返回一个查询校验器
     * <p>Title         : getQueryValidator lilinhai 2018年6月5日 下午3:02:34</p>
     * @return 
     * QueryValidator
     */
    public abstract QueryValidator getQueryValidator();
}
