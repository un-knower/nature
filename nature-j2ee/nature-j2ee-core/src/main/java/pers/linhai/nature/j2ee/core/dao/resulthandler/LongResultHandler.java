/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : LongResultHandler.java</p>
 * <p>Package     : pers.linhai.nature.j2ee.core.dao</p>
 * @Creator lilinhai 2018年6月3日 下午7:25:07
 * @Version  V1.0  
 */ 

package pers.linhai.nature.j2ee.core.dao.resulthandler;

import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;

/**
 * long型返回结果处理器
 * <p>ClassName      : LongResultHandler</p>
 * @author lilinhai 2018年6月3日 下午7:25:07
 * @version 1.0
 */
public class LongResultHandler implements ResultHandler<Long>
{
    
    private long value;
    
    /** 
     * <p>Overriding Method: lilinhai 2018年6月3日 下午7:25:07</p>
     * <p>Title: handleResult</p>
     * @param resultContext 
     * @see org.apache.ibatis.session.ResultHandler#handleResult(org.apache.ibatis.session.ResultContext)
     */
    public void handleResult(ResultContext< ? extends Long> resultContext)
    {
        value = resultContext.getResultObject();
    }

    /**
     * <p>Get Method   :   value long</p>
     * @return value
     */
    public long getValue()
    {
        return value;
    }
}
