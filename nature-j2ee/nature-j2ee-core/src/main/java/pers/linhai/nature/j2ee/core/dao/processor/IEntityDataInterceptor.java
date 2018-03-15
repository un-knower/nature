/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : IRowDataServiceProcessor.java</p>
 * <p>Package     : pers.linhai.nature.j2ee.core.service</p>
 * @Creator lilinhai 2018年3月14日 下午7:22:00
 * @Version  V1.0  
 */ 

package pers.linhai.nature.j2ee.core.dao.processor;

import pers.linhai.nature.j2ee.core.exception.EntityPreProcessSaveException;
import pers.linhai.nature.j2ee.core.exception.EntityPreProcessUpdateException;
import pers.linhai.nature.j2ee.core.model.BaseEntity;
import pers.linhai.nature.j2ee.core.model.EntityBean;

/**
 * 业务数据拦截器
 * <p>ClassName      : IServiceDataInterceptor</p>
 * @author lilinhai 2018年3月14日 下午7:22:00
 * @version 1.0
 */
public interface IEntityDataInterceptor<Entity extends BaseEntity<?>>
{

    /**
     * 上行数据save拦截处理，数据校验等操作处理
     * <p>Title         : process lilinhai 2018年3月14日 下午7:30:14</p>
     * @param entity 
     * void
     */
    void preProcessSave(Entity entity) throws EntityPreProcessSaveException;
    
    /**
     * 上行数据update拦截处理，数据校验等操作处理
     * <p>Title         : process lilinhai 2018年3月14日 下午7:30:14</p>
     * @param entity 
     * void
     */
    void preProcessUpdate(Entity entity) throws EntityPreProcessUpdateException;
    
    /**
     * 下行数据返回拦截处理
     * <p>Title         : process lilinhai 2018年3月14日 下午7:29:58</p>
     * @param entityBean
     * @param entity 
     * void
     */
    void preProcessReturn(EntityBean entityBean, Entity entity);
}