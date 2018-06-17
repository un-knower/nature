package pers.linhai.nature.esaccessor.exception;

/**
 * 配置文件解析异常
 * 
 * @author admin
 * 
 */
public class EsConfigParseException extends RuntimeException
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EsConfigParseException()
	{
		super();
	}

	public EsConfigParseException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public EsConfigParseException(String message)
	{
		super(message);
	}

	public EsConfigParseException(Throwable cause)
	{
		super(cause);
	}

}
