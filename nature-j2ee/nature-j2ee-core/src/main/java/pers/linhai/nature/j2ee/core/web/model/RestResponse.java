/*
 * <p>Copyright : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p> <p>FileName : ResponseResult.java</p>
 * <p>Package : com.meme.crm.web.controller.core</p>
 * @Creator lilinhai 2018年2月5日 下午9:43:22
 * @Version V1.0
 */

package pers.linhai.nature.j2ee.core.web.model;

import java.io.Serializable;

/**
 * Java rest接口返回结构
 * <p>ClassName      : ResponseResult</p>
 * @author lilinhai 2018年2月5日 下午9:43:22
 * @version 1.0
 */
public class RestResponse implements Serializable
{
    
    /**
     * <p>Info          : long serialVersionUID lilinhai 2018年2月5日 下午9:43:43</p>
     */
    private static final long serialVersionUID = 1L;
    
    /** 
     * 是否成功，成功找data、失败找msg 
     */
    private final boolean success;
    
    /** 
     * 返回结果编码，成功的话我喜欢设为0 
     */
    private final int code;
    
    /** 
     * 返回消息，一般放置可追溯的错误消息 
     */
    private final String message;
    
    /** 
     * 返回数据 
     */
    private final Object data;
    
    /**
     * <p>Title        : ResponseResult lilinhai 2018年2月5日 下午9:45:53</p>
     * @param success
     * @param code
     * @param msg
     * @param data
     */
    private RestResponse(boolean success, int code, String message, Object data)
    {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }
    
    public static RestResponse fail(int code, String message)
    {
        return new RestResponse(false, code, message, null);
    }
    
    public static RestResponse success(int code, String massage, Object data)
    {
        return new RestResponse(true, code, massage, data);
    }
    
    public static RestResponse success(Object data)
    {
        return new RestResponse(true, 0, "Success", data);
    }
    
    public static RestResponse success()
    {
        return success(null);
    }
    
    /**
     * <p>Get Method   :   serialVersionUID long</p>
     * @return serialversionuid
     */
    public static long getSerialversionuid()
    {
        return serialVersionUID;
    }
    
    /**
     * <p>Get Method   :   success boolean</p>
     * @return success
     */
    public boolean isSuccess()
    {
        return success;
    }
    
    /**
     * <p>Get Method   :   code int</p>
     * @return code
     */
    public int getCode()
    {
        return code;
    }
    
    /**
     * <p>Get Method   :   msg String</p>
     * @return msg
     */
    public String getMessage()
    {
        return message;
    }
    
    /**
     * <p>Get Method   :   data Object</p>
     * @return data
     */
    public Object getData()
    {
        return data;
    }
}
