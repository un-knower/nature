package pers.linhai.nature.esmapper.exception;

/**
 * 配置文件解析异常
 * 
 * @author admin
 * 
 */
public class EsConfigLoadedException extends RuntimeException
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EsConfigLoadedException()
	{
		super();
	}

	public EsConfigLoadedException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public EsConfigLoadedException(String message)
	{
		super(message);
	}

	public EsConfigLoadedException(Throwable cause)
	{
		super(cause);
	}

}
