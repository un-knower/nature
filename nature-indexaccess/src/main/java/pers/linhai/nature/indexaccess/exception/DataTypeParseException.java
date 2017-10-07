package pers.linhai.nature.indexaccess.exception;

/**
 * 配置文件解析异常
 * 
 * @author admin
 * 
 */
public class DataTypeParseException extends RuntimeException
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DataTypeParseException()
	{
		super();
	}

	public DataTypeParseException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public DataTypeParseException(String message)
	{
		super(message);
	}

	public DataTypeParseException(Throwable cause)
	{
		super(cause);
	}

}
