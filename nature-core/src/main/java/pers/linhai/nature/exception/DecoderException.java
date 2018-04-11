/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : DecoderException.java</p>
 * <p>Package     : pers.linhai.nature.exception</p>
 * @Creator lilinhai 2018年4月11日 下午9:56:17
 * @Version  V1.0  
 */ 

package pers.linhai.nature.exception;

/**
 * <p>Description    : <pre>TODO(这里用一句话描述这个类的作用)</pre></p>
 * <p>ClassName      : DecoderException</p>
 * @author lilinhai 2018年4月11日 下午9:56:17
 * @version 1.0
 */
public class DecoderException extends RuntimeException
{

    /**
     * <p>Info          : long serialVersionUID lilinhai 2018年4月11日 下午9:56:32</p>
     * <p>Description   : <pre>TODO(用一句话描述这个变量表示什么) </pre></p>
     */
    private static final long serialVersionUID = 1L;

    /**
     * <p>Title        : DecoderException lilinhai 2018年4月11日 下午9:56:22</p>
     * <p>Description  : <pre>TODO(这里用一句话描述这个方法的作用)</pre></p> 
     */ 
    public DecoderException()
    {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * <p>Title        : DecoderException lilinhai 2018年4月11日 下午9:56:22</p>
     * <p>Description  : <pre>TODO(这里用一句话描述这个方法的作用)</pre></p>
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace 
     */ 
    public DecoderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * <p>Title        : DecoderException lilinhai 2018年4月11日 下午9:56:22</p>
     * <p>Description  : <pre>TODO(这里用一句话描述这个方法的作用)</pre></p>
     * @param message
     * @param cause 
     */ 
    public DecoderException(String message, Throwable cause)
    {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

    /**
     * <p>Title        : DecoderException lilinhai 2018年4月11日 下午9:56:22</p>
     * <p>Description  : <pre>TODO(这里用一句话描述这个方法的作用)</pre></p>
     * @param message 
     */ 
    public DecoderException(String message)
    {
        super(message);
    }

    /**
     * <p>Title        : DecoderException lilinhai 2018年4月11日 下午9:56:22</p>
     * @param cause 
     */ 
    public DecoderException(Throwable cause)
    {
        super(cause);
    }
}
