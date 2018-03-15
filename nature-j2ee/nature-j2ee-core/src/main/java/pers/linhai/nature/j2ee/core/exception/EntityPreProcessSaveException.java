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
public class EntityPreProcessSaveException extends RuntimeException
{

    /**
     * <p>Info          : long serialVersionUID lilinhai 2018年3月15日 上午10:32:34</p>
     */
    private static final long serialVersionUID = 1L;

    /**
     */ 
    public EntityPreProcessSaveException()
    {
        super();
    }

    /**
     * <p>Title        : EntityValidateException lilinhai 2018年3月15日 上午10:32:50</p>
     * @param message 
     */ 
    public EntityPreProcessSaveException(String message)
    {
        super(message);
    }
}
