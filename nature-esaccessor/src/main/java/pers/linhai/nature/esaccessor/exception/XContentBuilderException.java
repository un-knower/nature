package pers.linhai.nature.esaccessor.exception;

/**
 * 配置文件解析异常
 * 
 * @author admin
 * 
 */
public class XContentBuilderException extends RuntimeException
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public XContentBuilderException()
	{
		super();
	}

	public XContentBuilderException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public XContentBuilderException(String message)
	{
		super(message);
	}

	public XContentBuilderException(Throwable cause)
	{
		super(cause);
	}

}
