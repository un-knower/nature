package pers.linhai.nature.indexaccess.exception;

/**
 * 配置文件解析异常
 * 
 * @author admin
 * 
 */
public class IndexConfigurationException extends RuntimeException
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IndexConfigurationException()
	{
		super();
	}

	public IndexConfigurationException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public IndexConfigurationException(String message)
	{
		super(message);
	}

	public IndexConfigurationException(Throwable cause)
	{
		super(cause);
	}

}
