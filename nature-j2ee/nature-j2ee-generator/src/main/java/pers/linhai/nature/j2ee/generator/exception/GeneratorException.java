/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : GeneratorException.java</p>
 * <p>Package     : codegenerator.exception</p>
 * @Creator lilinhai 2018年2月5日 下午4:09:46
 * @Version  V1.0  
 */ 

package pers.linhai.nature.j2ee.generator.exception;

/**
 * <p>Description    : <pre>TODO(这里用一句话描述这个类的作用)</pre></p>
 * <p>ClassName      : GeneratorException</p>
 * @author lilinhai 2018年2月5日 下午4:09:46
 * @version 1.0
 */
public class GeneratorException extends RuntimeException
{

    /**
     * <p>Info          : long serialVersionUID lilinhai 2018年2月5日 下午4:09:55</p>
     * <p>Description   : <pre>TODO(用一句话描述这个变量表示什么) </pre></p>
     */
    private static final long serialVersionUID = 1L;

    /**
     * <p>Title        : GeneratorException lilinhai 2018年2月5日 下午4:09:50</p>
     * <p>Description  : <pre>TODO(这里用一句话描述这个方法的作用)</pre></p> 
     */ 
    public GeneratorException()
    {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * <p>Title        : GeneratorException lilinhai 2018年2月5日 下午4:09:50</p>
     * <p>Description  : <pre>TODO(这里用一句话描述这个方法的作用)</pre></p>
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace 
     */ 
    public GeneratorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * <p>Title        : GeneratorException lilinhai 2018年2月5日 下午4:09:50</p>
     * <p>Description  : <pre>TODO(这里用一句话描述这个方法的作用)</pre></p>
     * @param message
     * @param cause 
     */ 
    public GeneratorException(String message, Throwable cause)
    {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

    /**
     * <p>Title        : GeneratorException lilinhai 2018年2月5日 下午4:09:50</p>
     * <p>Description  : <pre>TODO(这里用一句话描述这个方法的作用)</pre></p>
     * @param message 
     */ 
    public GeneratorException(String message)
    {
        super(message);
        // TODO Auto-generated constructor stub
    }

    /**
     * <p>Title        : GeneratorException lilinhai 2018年2月5日 下午4:09:50</p>
     * <p>Description  : <pre>TODO(这里用一句话描述这个方法的作用)</pre></p>
     * @param cause 
     */ 
    public GeneratorException(Throwable cause)
    {
        super(cause);
        // TODO Auto-generated constructor stub
    }

    
}
