/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : JointQueryMapperImpl.java</p>
 * <p>Package     : pers.linhai.nature.j2ee.core.dao</p>
 * @Creator lilinhai 2018年6月3日 上午8:52:41
 * @Version  V1.0  
 */ 

package pers.linhai.nature.j2ee.core.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pers.linhai.nature.j2ee.core.dao.processor.IJointQueryRowDataProcessor;
import pers.linhai.nature.j2ee.core.dao.resulthandler.JointQueryResultHandler;
import pers.linhai.nature.j2ee.core.dao.resulthandler.LongResultHandler;
import pers.linhai.nature.j2ee.core.model.JointQuery;

/**
 * <p>Description    : <pre>TODO(这里用一句话描述这个类的作用)</pre></p>
 * <p>ClassName      : JointQueryMapperImpl</p>
 * @author lilinhai 2018年6月3日 上午8:52:41
 * @version 1.0
 */
@Repository
public class JointQueryMapperImpl implements IJointQueryMapper
{
    
    /**
     * 父接口的命名空间
     */
    private static final String BASE_NAME_SPACE = IJointQueryMapper.class.getName();
    
    
    /**
     * 查询方法
     */
    private static final String JOIN_FIND = BASE_NAME_SPACE.concat(".jointFind");
    
    /**
     * 计数方法
     */
    private static final String COUNT = BASE_NAME_SPACE.concat(".count");
    
    @Autowired
    protected SqlSession sqlSession;
    
    /**
     * 关联查询
     * <p>Title         : find lilinhai 2018年6月3日 上午12:35:46</p>
     * @param entityQuery
     * @param rowDataJointQueryProcessor 
     * void
     */
    public void find(JointQuery jointQuery, IJointQueryRowDataProcessor rowDataJointQueryProcessor)
    {
        JointQueryResultHandler myResultHandler = new JointQueryResultHandler(rowDataJointQueryProcessor);
        sqlSession.select(JOIN_FIND, jointQuery, myResultHandler);
    }
    
    /**
     * 根据查询条件统计数量
     * <p>Overriding Method: lilinhai 2018年5月18日 上午10:18:01</p>
     * <p>Title: count</p>
     * @param jointQuery
     * @return 
     * @see pers.linhai.nature.j2ee.core.dao.IBaseMapper#count(pers.linhai.nature.j2ee.core.model.BaseQuery)
     */
    public long count(JointQuery jointQuery)
    {
        LongResultHandler longResultHandler = new LongResultHandler();
        sqlSession.select(COUNT, jointQuery, longResultHandler);
        return longResultHandler.getValue();
    }
}
