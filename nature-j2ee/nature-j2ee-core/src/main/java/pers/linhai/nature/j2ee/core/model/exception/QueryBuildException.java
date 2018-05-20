/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : IllegalExpression.java</p>
 * <p>Package     : com.leloven.wanka.match.model.expression.exception</p>
 * @Creator lilinhai 2018年1月22日 下午4:13:58
 * @Version  V1.0  
 */

package pers.linhai.nature.j2ee.core.model.exception;

/**
 * 查询对象构建异常
 * <p>ClassName      : IllegalExpression</p>
 * @author lilinhai 2018年1月22日 下午4:13:58
 * @version 1.0
 */
public class QueryBuildException extends RuntimeException
{
    
    /**
     * <p>Info          : long serialVersionUID lilinhai 2018年1月22日 下午4:14:05</p>
     * <p>Description   : <pre>TODO(用一句话描述这个变量表示什么) </pre></p>
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * <p>Title        : IllegalExpression lilinhai 2018年1月22日 下午4:14:15</p>
     * <p>Description  : <pre>TODO(这里用一句话描述这个方法的作用)</pre></p> 
     */
    public QueryBuildException()
    {
        super();
        // TODO Auto-generated constructor stub
    }
    
    /**
     * <p>Title        : IllegalExpression lilinhai 2018年1月22日 下午4:14:15</p>
     * <p>Description  : <pre>TODO(这里用一句话描述这个方法的作用)</pre></p>
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace 
     */
    public QueryBuildException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }
    
    /**
     * <p>Title        : IllegalExpression lilinhai 2018年1月22日 下午4:14:15</p>
     * <p>Description  : <pre>TODO(这里用一句话描述这个方法的作用)</pre></p>
     * @param message
     * @param cause 
     */
    public QueryBuildException(String message, Throwable cause)
    {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }
    
    /**
     * <p>Title        : IllegalExpression lilinhai 2018年1月22日 下午4:14:15</p>
     * <p>Description  : <pre>TODO(这里用一句话描述这个方法的作用)</pre></p>
     * @param message 
     */
    public QueryBuildException(String message)
    {
        super(message);
        // TODO Auto-generated constructor stub
    }
    
    /**
     * <p>Title        : IllegalExpression lilinhai 2018年1月22日 下午4:14:15</p>
     * <p>Description  : <pre>TODO(这里用一句话描述这个方法的作用)</pre></p>
     * @param cause 
     */
    public QueryBuildException(Throwable cause)
    {
        super(cause);
    }
    
}
