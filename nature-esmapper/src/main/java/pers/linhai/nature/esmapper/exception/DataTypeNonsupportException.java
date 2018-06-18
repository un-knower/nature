package pers.linhai.nature.esmapper.exception;

/**
 * 配置文件解析异常
 * 
 * @author admin
 * 
 */
public class DataTypeNonsupportException extends RuntimeException
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DataTypeNonsupportException()
	{
		super();
	}

	public DataTypeNonsupportException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public DataTypeNonsupportException(String message)
	{
		super(message);
	}

	public DataTypeNonsupportException(Throwable cause)
	{
		super(cause);
	}

}
