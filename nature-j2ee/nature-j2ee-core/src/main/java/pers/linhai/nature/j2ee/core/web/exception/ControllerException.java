/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : MapperException.java</p>
 * <p>Package     : com.meme.crm.dao.exception</p>
 * @Creator lilinhai 2018年2月13日 上午11:11:44
 * @Version  V1.0  
 */

package pers.linhai.nature.j2ee.core.web.exception;

/**
 * <p>Description    : <pre>TODO(这里用一句话描述这个类的作用)</pre></p>
 * <p>ClassName      : MapperException</p>
 * @author lilinhai 2018年2月13日 上午11:11:44
 * @version 1.0
 */
public class ControllerException extends RuntimeException
{
    
    /**
     * <p>Info          : long serialVersionUID lilinhai 2018年2月13日 上午11:12:07</p>
     * <p>Description   : <pre>TODO(用一句话描述这个变量表示什么) </pre></p>
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 错误码
     */
    protected int errorCode;
    
    public ControllerException(int errorCode, String message)
    {
        super(message);
        this.errorCode = errorCode;
    }
    
    /**
     * <p>Get Method   :   errorCode int</p>
     * @return errorCode
     */
    public int getErrorCode()
    {
        return errorCode;
    }
}
