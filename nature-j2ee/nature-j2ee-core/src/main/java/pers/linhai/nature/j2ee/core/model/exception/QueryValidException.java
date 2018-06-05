/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : QueryValidException.java</p>
 * <p>Package     : pers.linhai.nature.j2ee.core.model.exception</p>
 * @Creator lilinhai 2018年6月5日 下午3:21:30
 * @Version  V1.0  
 */ 

package pers.linhai.nature.j2ee.core.model.exception;

/**
 * 查询对象校验异常
 * <p>ClassName      : QueryValidException</p>
 * @author lilinhai 2018年6月5日 下午3:21:30
 * @version 1.0
 */
public class QueryValidException extends RuntimeException
{

    /**
     * <p>Info          : long serialVersionUID lilinhai 2018年6月5日 下午3:21:33</p>
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 错误码
     */
    protected int errorCode;
    
    public QueryValidException(int errorCode, String message)
    {
        super(message);
        this.errorCode = errorCode;
    }
}
