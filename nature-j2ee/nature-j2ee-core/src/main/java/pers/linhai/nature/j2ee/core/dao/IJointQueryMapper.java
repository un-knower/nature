/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : IJointQueryMapper.java</p>
 * <p>Package     : pers.linhai.nature.j2ee.core.dao</p>
 * @Creator lilinhai 2018年6月3日 上午8:47:43
 * @Version  V1.0  
 */ 

package pers.linhai.nature.j2ee.core.dao;

import pers.linhai.nature.j2ee.core.dao.processor.IRowDataJointQueryProcessor;
import pers.linhai.nature.j2ee.core.model.JointQuery;

/**
 * 关联查询数据mapper
 * <p>ClassName      : IJointQueryMapper</p>
 * @author lilinhai 2018年6月3日 上午8:47:43
 * @version 1.0
 */
public interface IJointQueryMapper
{
    
    /**
     * 关联查询
     * <p>Title         : find lilinhai 2018年6月3日 上午12:35:46</p>
     * @param entityQuery
     * @param rowDataJointQueryProcessor 
     * void
     */
    void find(JointQuery entityQuery, IRowDataJointQueryProcessor rowDataJointQueryProcessor);
    
    /**
     * 计数
     * <p>Title         : count lilinhai 2018年6月3日 上午11:02:06</p>
     * @param entityQuery
     * @return 
     * long
     */
    long count(JointQuery entityQuery);
}
