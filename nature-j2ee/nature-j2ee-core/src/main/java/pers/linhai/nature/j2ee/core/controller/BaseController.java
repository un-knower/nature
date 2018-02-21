/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : BaseController.java</p>
 * <p>Package     : com.meme.crm.web.controller.core</p>
 * @Creator lilinhai 2018年2月8日 上午9:27:45
 * @Version  V1.0  
 */ 

package pers.linhai.nature.j2ee.core.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 所有控制器的基类
 * <p>ClassName      : BaseController</p>
 * @author lilinhai 2018年2月8日 上午9:27:45
 * @version 1.0
 */
public abstract class BaseController
{

    /**
     * 日志记录器
     */
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    
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
