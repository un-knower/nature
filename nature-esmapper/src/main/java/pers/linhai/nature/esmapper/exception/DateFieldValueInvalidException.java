package pers.linhai.nature.esmapper.exception;

/**
 * 配置文件解析异常
 * 
 * @author admin
 * 
 */
public class DateFieldValueInvalidException extends RuntimeException
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DateFieldValueInvalidException()
	{
		super();
	}

	public DateFieldValueInvalidException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public DateFieldValueInvalidException(String message)
	{
		super(message);
	}

	public DateFieldValueInvalidException(Throwable cause)
	{
		super(cause);
	}

}
