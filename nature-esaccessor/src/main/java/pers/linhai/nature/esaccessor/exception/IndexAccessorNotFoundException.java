package pers.linhai.nature.esaccessor.exception;

/**
 * 配置文件解析异常
 * 
 * @author admin
 * 
 */
public class IndexAccessorNotFoundException extends RuntimeException
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IndexAccessorNotFoundException()
	{
		super();
	}

	public IndexAccessorNotFoundException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public IndexAccessorNotFoundException(String message)
	{
		super(message);
	}

	public IndexAccessorNotFoundException(Throwable cause)
	{
		super(cause);
	}

}
