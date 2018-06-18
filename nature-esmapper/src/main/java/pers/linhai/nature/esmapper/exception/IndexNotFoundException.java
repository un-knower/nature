package pers.linhai.nature.esmapper.exception;

/**
 * 配置文件解析异常
 * 
 * @author admin
 * 
 */
public class IndexNotFoundException extends RuntimeException
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IndexNotFoundException()
	{
		super();
	}

	public IndexNotFoundException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public IndexNotFoundException(String message)
	{
		super(message);
	}

	public IndexNotFoundException(Throwable cause)
	{
		super(cause);
	}

}
