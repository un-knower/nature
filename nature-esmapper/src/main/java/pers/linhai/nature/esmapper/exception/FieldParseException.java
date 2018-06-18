package pers.linhai.nature.esmapper.exception;

/**
 * 配置文件解析异常
 * 
 * @author admin
 * 
 */
public class FieldParseException extends RuntimeException
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FieldParseException()
	{
		super();
	}

	public FieldParseException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public FieldParseException(String message)
	{
		super(message);
	}

	public FieldParseException(Throwable cause)
	{
		super(cause);
	}

}
