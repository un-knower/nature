/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : ReflectionException.java</p>
 * <p>Package     : pers.linhai.nature.exception</p>
 * @Creator lilinhai 2018年6月2日 上午8:57:11
 * @Version  V1.0  
 */ 

package pers.linhai.nature.exception;

/**
 * <p>Description    : <pre>TODO(这里用一句话描述这个类的作用)</pre></p>
 * <p>ClassName      : ReflectionException</p>
 * @author lilinhai 2018年6月2日 上午8:57:11
 * @version 1.0
 */
public class ReflectionException extends RuntimeException
{

    /**
     * <p>Info          : long serialVersionUID lilinhai 2018年6月2日 上午8:57:20</p>
     * <p>Description   : <pre>TODO(用一句话描述这个变量表示什么) </pre></p>
     */
    private static final long serialVersionUID = 1L;

    /**
     * <p>Title        : ReflectionException lilinhai 2018年6月2日 上午8:57:30</p>
     * <p>Description  : <pre>TODO(这里用一句话描述这个方法的作用)</pre></p> 
     */ 
    public ReflectionException()
    {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * <p>Title        : ReflectionException lilinhai 2018年6月2日 上午8:57:30</p>
     * <p>Description  : <pre>TODO(这里用一句话描述这个方法的作用)</pre></p>
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace 
     */ 
    public ReflectionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * <p>Title        : ReflectionException lilinhai 2018年6月2日 上午8:57:30</p>
     * <p>Description  : <pre>TODO(这里用一句话描述这个方法的作用)</pre></p>
     * @param message
     * @param cause 
     */ 
    public ReflectionException(String message, Throwable cause)
    {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

    /**
     * <p>Title        : ReflectionException lilinhai 2018年6月2日 上午8:57:30</p>
     * <p>Description  : <pre>TODO(这里用一句话描述这个方法的作用)</pre></p>
     * @param message 
     */ 
    public ReflectionException(String message)
    {
        super(message);
        // TODO Auto-generated constructor stub
    }

    /**
     * <p>Title        : ReflectionException lilinhai 2018年6月2日 上午8:57:30</p>
     * <p>Description  : <pre>TODO(这里用一句话描述这个方法的作用)</pre></p>
     * @param cause 
     */ 
    public ReflectionException(Throwable cause)
    {
        super(cause);
        // TODO Auto-generated constructor stub
    }
    
    
    
}
