package pers.linhai.nature.esmapper.exception;

/**
 * 配置文件解析异常
 * 
 * @author admin
 * 
 */
public class EsClientInitializationException extends RuntimeException
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EsClientInitializationException()
	{
		super();
	}

	public EsClientInitializationException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public EsClientInitializationException(String message)
	{
		super(message);
	}

	public EsClientInitializationException(Throwable cause)
	{
		super(cause);
	}

}
