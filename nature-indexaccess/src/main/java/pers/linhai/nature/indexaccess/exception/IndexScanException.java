package pers.linhai.nature.indexaccess.exception;

/**
 * 配置文件解析异常
 * 
 * @author admin
 * 
 */
public class IndexScanException extends RuntimeException
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IndexScanException()
	{
		super();
	}

	public IndexScanException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public IndexScanException(String message)
	{
		super(message);
	}

	public IndexScanException(Throwable cause)
	{
		super(cause);
	}

}
