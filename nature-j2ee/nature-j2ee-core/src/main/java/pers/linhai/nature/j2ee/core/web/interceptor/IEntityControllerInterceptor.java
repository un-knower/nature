/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : IEntityWebInterceptor.java</p>
 * <p>Package     : pers.linhai.nature.j2ee.core.web.interceptor</p>
 * @Creator lilinhai 2018年4月29日 上午10:15:45
 * @Version  V1.0  
 */ 

package pers.linhai.nature.j2ee.core.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pers.linhai.nature.j2ee.core.model.BaseEntity;
import pers.linhai.nature.j2ee.core.web.exception.ControllerInterceptProcessDeleteException;
import pers.linhai.nature.j2ee.core.web.exception.ControllerInterceptProcessSaveException;
import pers.linhai.nature.j2ee.core.web.exception.ControllerInterceptProcessUpdateException;

/**
 * 实体控制层拦截器标准接口
 * <p>ClassName      : IEntityWebInterceptor</p>
 * @author lilinhai 2018年4月29日 上午10:15:45
 * @version 1.0
 */
public interface IEntityControllerInterceptor<Key, Entity extends BaseEntity<Key>>
{
    /**
     * 上行数据save前的拦截处理，数据校验等操作处理
     * <p>Title         : process lilinhai 2018年3月14日 下午7:30:14</p>
     * @param entity 
     * void
     */
    void beforeSave(HttpServletRequest request, HttpServletResponse response, Entity entity) throws ControllerInterceptProcessSaveException;

    /**
     * 上行数据delete前的拦截处理，预防处理误删、搞乱等操作
     * <p>Title         : process lilinhai 2018年3月14日 下午7:30:14</p>
     * @param id 
     * void
     */
    void beforeDelete(HttpServletRequest request, HttpServletResponse response, Key id) throws ControllerInterceptProcessDeleteException;
    
    /**
     * 上行数据update前的拦截处理，数据校验等操作处理
     * <p>Title         : process lilinhai 2018年3月14日 下午7:30:14</p>
     * @param entity 
     * void
     */
    void beforeUpdate(HttpServletRequest request, HttpServletResponse response, Entity entity) throws ControllerInterceptProcessUpdateException;
}
