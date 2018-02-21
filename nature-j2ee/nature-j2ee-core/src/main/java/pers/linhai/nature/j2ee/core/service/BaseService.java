/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : BaseService.java</p>
 * <p>Package     : com.meme.crm.service.core</p>
 * @Creator lilinhai 2018年2月8日 上午9:37:11
 * @Version  V1.0  
 */ 

package pers.linhai.nature.j2ee.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 所有业务类的基类
 * <p>ClassName      : BaseService</p>
 * @author lilinhai 2018年2月8日 上午9:37:11
 * @version 1.0
 */
public abstract class BaseService
{

    /**
     * 日志记录器
     */
    protected final Logger logger = LoggerFactory.getLogger(getClass());
}
