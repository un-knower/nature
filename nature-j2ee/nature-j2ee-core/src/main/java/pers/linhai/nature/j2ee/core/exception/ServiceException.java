/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : MapperException.java</p>
 * <p>Package     : com.meme.crm.dao.exception</p>
 * @Creator lilinhai 2018年2月13日 上午11:11:44
 * @Version  V1.0  
 */ 

package pers.linhai.nature.j2ee.core.exception;

/**
 * <p>Description    : <pre>TODO(这里用一句话描述这个类的作用)</pre></p>
 * <p>ClassName      : MapperException</p>
 * @author lilinhai 2018年2月13日 上午11:11:44
 * @version 1.0
 */
public class ServiceException extends RuntimeException
{

    /**
     * <p>Info          : long serialVersionUID lilinhai 2018年2月13日 上午11:12:07</p>
     * <p>Description   : <pre>TODO(用一句话描述这个变量表示什么) </pre></p>
     */
    private static final long serialVersionUID = 1L;

    /**
     * <p>Title        : MapperException lilinhai 2018年2月13日 上午11:12:03</p>
     * <p>Description  : <pre>TODO(这里用一句话描述这个方法的作用)</pre></p> 
     */ 
    public ServiceException()
    {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * <p>Title        : MapperException lilinhai 2018年2月13日 上午11:12:03</p>
     * <p>Description  : <pre>TODO(这里用一句话描述这个方法的作用)</pre></p>
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace 
     */ 
    public ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * <p>Title        : MapperException lilinhai 2018年2月13日 上午11:12:03</p>
     * <p>Description  : <pre>TODO(这里用一句话描述这个方法的作用)</pre></p>
     * @param message
     * @param cause 
     */ 
    public ServiceException(String message, Throwable cause)
    {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

    /**
     * <p>Title        : MapperException lilinhai 2018年2月13日 上午11:12:03</p>
     * <p>Description  : <pre>TODO(这里用一句话描述这个方法的作用)</pre></p>
     * @param message 
     */ 
    public ServiceException(String message)
    {
        super(message);
        // TODO Auto-generated constructor stub
    }

    /**
     * <p>Title        : MapperException lilinhai 2018年2月13日 上午11:12:03</p>
     * <p>Description  : <pre>TODO(这里用一句话描述这个方法的作用)</pre></p>
     * @param cause 
     */ 
    public ServiceException(Throwable cause)
    {
        super(cause);
        // TODO Auto-generated constructor stub
    }

    
}
