/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : BaseController.java</p>
 * <p>Package     : com.meme.crm.web.controller.core</p>
 * @Creator lilinhai 2018年2月8日 上午9:27:45
 * @Version  V1.0  
 */

package pers.linhai.nature.j2ee.core.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;

import pers.linhai.nature.j2ee.core.web.model.RestResponse;

/**
 * 所有控制器的基类
 * <p>ClassName      : BaseController</p>
 * @author lilinhai 2018年2月8日 上午9:27:45
 * @version 1.0
 */
public abstract class BaseController
{
    
    @Autowired
    protected ObjectMapper objectMapper;
    
    /**
     * 日志记录器
     */
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    
    /**
     * 将对象转换为json字串
     * <p>Title         : toJSON lilinhai 2018年6月6日 下午3:19:56</p>
     * @param obj
     * @return 
     * String
     */
    protected String toJSON(Object obj)
    {
        try
        {
            return objectMapper.writeValueAsString(obj);
        }
        catch (Throwable e)
        {
            logger.error("Jackson-Serialization error ", e);
            return null;
        }
    }
    
    /**
     * 失败通用响应
     * <p>Title         : fail lilinhai 2018年2月8日 上午9:56:12</p>
     * @param code
     * @param message
     * @return 
     * RestResponse
     */
    protected RestResponse fail(int code, String message)
    {
        return RestResponse.fail(code, message);
    }
    
    /**
     * 失败通用响应
     * <p>Title         : fail lilinhai 2018年2月8日 上午9:56:12</p>
     * @param code
     * @param message
     * @return 
     * RestResponse
     */
    protected RestResponse fail(int code, String message, Object data)
    {
        return RestResponse.fail(code, message, data);
    }
    
    /**
     * 成功通用响应
     * <p>Title         : success lilinhai 2018年2月8日 上午9:56:56</p>
     * @param data
     * @return 
     * RestResponse
     */
    protected RestResponse success(Object data)
    {
        return RestResponse.success(data);
    }
    
    /**
     * 成功通用响应
     * <p>Title         : success lilinhai 2018年2月8日 上午9:56:56</p>
     * @return 
     * RestResponse
     */
    protected RestResponse success()
    {
        return RestResponse.success();
    }
}
