package pers.linhai.nature.indexaccess.exception;

/**
 * 配置文件解析异常
 * 
 * @author admin
 * 
 */
public class TypeAccessorNotFoundException extends RuntimeException
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TypeAccessorNotFoundException()
	{
		super();
	}

	public TypeAccessorNotFoundException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public TypeAccessorNotFoundException(String message)
	{
		super(message);
	}

	public TypeAccessorNotFoundException(Throwable cause)
	{
		super(cause);
	}

}
