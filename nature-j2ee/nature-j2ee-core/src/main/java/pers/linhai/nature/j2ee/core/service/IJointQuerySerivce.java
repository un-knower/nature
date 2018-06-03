/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : IJointQuerySerivce.java</p>
 * <p>Package     : pers.linhai.nature.j2ee.core.service</p>
 * @Creator lilinhai 2018年6月3日 下午7:53:33
 * @Version  V1.0  
 */ 

package pers.linhai.nature.j2ee.core.service;

import java.util.List;

import pers.linhai.nature.j2ee.core.model.JointQuery;
import pers.linhai.nature.j2ee.core.model.join.JointEntityBean;

/**
 * 关联查询业务逻辑层
 * <p>ClassName      : IJointQuerySerivce</p>
 * @author lilinhai 2018年6月3日 下午7:53:33
 * @version 1.0
 */
public interface IJointQuerySerivce
{
    
    /**
     * 查询方法
     * <p>Title         : findEntityBean lilinhai 2018年6月3日 下午8:09:40</p>
     * @param jointQuery
     * @return 
     * List<JointEntityBean>
     */
    public List<JointEntityBean> find(JointQuery jointQuery);
    
    /**
     * 统计条目
     * <p>Title         : count lilinhai 2018年6月3日 下午8:09:34</p>
     * @param jointQuery
     * @return 
     * long
     */
    public long count(JointQuery jointQuery);
}
