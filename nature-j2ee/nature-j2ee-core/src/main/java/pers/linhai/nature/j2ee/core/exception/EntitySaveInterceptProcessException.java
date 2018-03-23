/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : EntityValidateException.java</p>
 * <p>Package     : pers.linhai.nature.j2ee.core.exception</p>
 * @Creator lilinhai 2018年3月15日 上午10:32:31
 * @Version  V1.0  
 */ 

package pers.linhai.nature.j2ee.core.exception;

/**
 * <p>ClassName      : EntityValidateException</p>
 * @author lilinhai 2018年3月15日 上午10:32:31
 * @version 1.0
 */
public class EntitySaveInterceptProcessException extends RuntimeException
{

    /**
     * <p>Info          : long serialVersionUID lilinhai 2018年3月15日 上午10:32:34</p>
     */
    private static final long serialVersionUID = 1L;

    /**
     */ 
    public EntitySaveInterceptProcessException()
    {
        super();
    }

    /**
     * <p>Title        : EntityValidateException lilinhai 2018年3月15日 上午10:32:50</p>
     * @param message 
     */ 
    public EntitySaveInterceptProcessException(String message)
    {
        super(message);
    }

    /**
     * <p>Title        : EntitySaveInterceptProcessException lilinhai 2018年3月23日 上午9:36:41</p>
     * <p>Description  : <pre>TODO(这里用一句话描述这个方法的作用)</pre></p>
     * @param message
     * @param cause 
     */ 
    public EntitySaveInterceptProcessException(String message, Throwable cause)
    {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

    /**
     * <p>Title        : EntitySaveInterceptProcessException lilinhai 2018年3月23日 上午9:36:41</p>
     * <p>Description  : <pre>TODO(这里用一句话描述这个方法的作用)</pre></p>
     * @param cause 
     */ 
    public EntitySaveInterceptProcessException(Throwable cause)
    {
        super(cause);
        // TODO Auto-generated constructor stub
    }
}
