package pers.linhai.nature.esaccessor.exception;

/**
 * 配置文件解析异常
 * 
 * @author admin
 * 
 */
public class DAONotFoundException extends RuntimeException
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DAONotFoundException()
	{
		super();
	}

	public DAONotFoundException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public DAONotFoundException(String message)
	{
		super(message);
	}

	public DAONotFoundException(Throwable cause)
	{
		super(cause);
	}

}
