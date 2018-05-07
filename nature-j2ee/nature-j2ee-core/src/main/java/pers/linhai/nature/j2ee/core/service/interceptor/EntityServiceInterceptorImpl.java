/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : EntityServiceInterceptorImpl.java</p>
 * <p>Package     : pers.linhai.nature.j2ee.core.service.interceptor</p>
 * @Creator lilinhai 2018年4月28日 上午11:26:37
 * @Version  V1.0  
 */

package pers.linhai.nature.j2ee.core.service.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import pers.linhai.nature.j2ee.core.dao.IBaseMapper;
import pers.linhai.nature.j2ee.core.model.BaseEntity;
import pers.linhai.nature.j2ee.core.model.BaseQuery;

/**
 * 业务拦截器
 * <p>ClassName      : EntityServiceInterceptorImpl</p>
 * @author lilinhai 2018年4月28日 上午11:26:37
 * @version 1.0
 */
public abstract class EntityServiceInterceptorImpl<Key, Entity extends BaseEntity<Key>, EntityQuery extends BaseQuery, Mapper extends IBaseMapper<Key, Entity, EntityQuery>>
        implements IEntityServiceInterceptor<Key, Entity, EntityQuery, Mapper>
{
    
    /**
     * 日志对象
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());
    
    /**
     * 实体DAO对象
     */
    @Autowired
    protected Mapper mapper;
}
