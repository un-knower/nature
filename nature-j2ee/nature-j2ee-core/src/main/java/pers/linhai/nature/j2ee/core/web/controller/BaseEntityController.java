/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : BaseController.java</p>
 * <p>Package     : com.meme.crm.web.controller.core</p>
 * @Creator lilinhai 2018年2月5日 下午5:45:55
 * @Version  V1.0  
 */ 

package pers.linhai.nature.j2ee.core.web.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.alibaba.fastjson.JSON;

import pers.linhai.nature.j2ee.core.model.BaseEntity;
import pers.linhai.nature.j2ee.core.model.BaseQuery;
import pers.linhai.nature.j2ee.core.model.EntityBean;
import pers.linhai.nature.j2ee.core.service.IBaseEntityService;
import pers.linhai.nature.j2ee.core.service.PaginationData;

/**
 * 控制器基类
 * <p>ClassName      : BaseController</p>
 * @author lilinhai 2018年2月5日 下午5:45:55
 * @version 1.0
 */
public abstract class BaseEntityController<Key extends Serializable, Entity extends BaseEntity<Key>, EntityQuery extends BaseQuery, EntityService extends IBaseEntityService<Key, Entity, EntityQuery>> 
    extends BaseController
{
    
    @Autowired
    protected EntityService entityService;
    
    /**
     * 过滤EntityBeanMap中的一些敏感字段，不需要传递到前端去的字段
     * <p>Title         : entityBeanMapFilter lilinhai 2018年2月10日 下午4:25:09</p>
     * @param entityBeanMap 
     * void
     */
    protected void entityBeanMapFilter(Map<String, Serializable> entityMap, Entity entity){}
    
    /**
     * 选择性修改的时候过滤掉某些字段
     * <p>Title         : updateSelectiveEntityFilter lilinhai 2018年2月26日 下午3:44:10</p>
     * @param entity 
     * void
     */
    protected void updateSelectiveEntityFilter(Entity entity) {}
    
    /**
     * 处理request请求的通用放飞
     * <p>Title         : doRequest lilinhai 2018年3月2日 下午9:45:08</p>
     * <p>Description   : <pre>TODO(这里用一句话描述这个方法的作用)</pre></p>
     * @param request 
     * void
     */
    protected void process(HttpServletRequest request) {};
    
    /**
     * 根据主键删除一个实体
     * <p>Title         : deleteByPrimaryKey lilinhai 2018年2月5日 下午11:23:58</p>
     * @param id
     * @return 
     * ResponseResult
     */
    @DeleteMapping("/{id}")
    protected RestResponse delete(@PathVariable Key id, HttpServletRequest request)
    {
        try
        {
            process(request);
            int count = entityService.delete(id);
            if (count != 1)
            {
                RestResponse restResponse = fail(RestErrorCode.DELETE_FAIL, "[Controller] delete occor an error, record：ID" + id);
                logger.error(JSON.toJSONString(restResponse));
                return restResponse;
            }
            return success();
        }
        catch (Throwable e)
        {
            logger.error("[Controller] delete(@PathVariable Key id, HttpServletRequest request) occor an error", e);
            return fail(RestErrorCode.DELETE_EXCEPTION, e.getMessage() + "，ID：" + id);
        }
    }

    /**
     * 创建一个实体
     * <p>Title         : insert lilinhai 2018年2月5日 下午11:23:43</p>
     * @param record
     * @return 
     * ResponseResult
     */
    @PostMapping("")
    protected RestResponse save(@RequestBody Entity record, HttpServletRequest request)
    {
        try
        {
            process(request);
            int count = entityService.save(record);
            if (count != 1)
            {
                RestResponse restResponse = fail(RestErrorCode.INSERT_FAIL, "[Controller] save occor an error, record：" + JSON.toJSONString(new EntityBean(record)));
                logger.error(JSON.toJSONString(restResponse));
                return restResponse;
            }
            EntityBean bean = new EntityBean(record);
            entityBeanMapFilter(bean, record);
            return success(bean);
        }
        catch (Throwable e)
        {
            logger.error("[Controller] save(@RequestBody Entity record, HttpServletRequest request) occor an error", e);
            return fail(RestErrorCode.INSERT_EXCEPTION, e.getMessage() + "，record：" + JSON.toJSONString(new EntityBean(record)));
        }
    }

    /**
     * 根据 主键选择性的更新记录
     * <p>Title         : updateByPrimaryKeySelective lilinhai 2018年2月5日 下午11:22:48</p>
     * @param record
     * @param id
     * @return 
     * ResponseResult
     */
    @PutMapping("/{id}")
    protected RestResponse update(@RequestBody Entity record, @PathVariable Key id, HttpServletRequest request)
    {
        try
        {
            process(request);
            
            //选择性修改的时候过滤掉某些字段
            updateSelectiveEntityFilter(record);
            record.setId(id);
            int count = entityService.update(record);
            if (count != 1)
            {
                RestResponse restResponse = fail(10300, "[Controller] update occor an error, record：" + JSON.toJSONString(new EntityBean(record)));
                logger.error(JSON.toJSONString(restResponse));
                return restResponse;
            }
            EntityBean bean = new EntityBean(record);
            entityBeanMapFilter(bean, record);
            return success(bean);
        }
        catch (Throwable e)
        {
            logger.error("[Controller] update(@RequestBody Entity record, @PathVariable Key id, HttpServletRequest request) occor an error", e);
            return fail(10301, e.getMessage() + "，record：" + JSON.toJSONString(new EntityBean(record)));
        }
    }

    /**
     * 根据主键查找单个记录
     * <p>Title         : selectByPrimaryKey lilinhai 2018年2月5日 下午11:23:05</p>
     * @param id
     * @return 
     * ResponseResult
     */
    @GetMapping("/{id}")
    protected RestResponse get(@PathVariable Key id, HttpServletRequest request)
    {
        try
        {
            process(request);
            Entity entity = entityService.get(id);
            if (entity == null)
            {
                RestResponse restResponse = fail(RestErrorCode.GET_FAIL, "[Controller] find occor an error, id：" + id);
                logger.error(JSON.toJSONString(restResponse));
                return restResponse;
            }
            EntityBean record = new EntityBean(entity);
            entityBeanMapFilter(record, entity);
            return success(record);
        }
        catch (Throwable e)
        {
            logger.error("[Controller] get(@PathVariable Key id, HttpServletRequest request) occor an error", e);
            return fail(RestErrorCode.GET_EXCEPTION, e.getMessage() + "，ID：" + id);
        }
    }
    
    /**
     * 根据查询条件查找单个记录
     * <p>Title         : selectByPrimaryKey lilinhai 2018年2月5日 下午11:23:05</p>
     * @param id
     * @return 
     * ResponseResult
     */
    @PostMapping("/get")
    protected RestResponse get(@RequestBody EntityQuery entityQuery, HttpServletRequest request)
    {
        try
        {
            process(request);
            Entity entity = entityService.get(entityQuery);
            if (entity == null)
            {
                RestResponse restResponse = fail(RestErrorCode.GET_FAIL, "[Controller] findOne occor an error, entityQuery：" + JSON.toJSONString(entityQuery));
                logger.error(JSON.toJSONString(restResponse));
                return restResponse;
            }
            EntityBean record = new EntityBean(entity);
            entityBeanMapFilter(record, entity);
            return success(record);
        }
        catch (Throwable e)
        {
            logger.error("[Controller] get(@RequestBody EntityQuery entityQuery, HttpServletRequest request) occor an error", e);
            return fail(RestErrorCode.GET_EXCEPTION, e.getMessage() + "，entityQuery：" + JSON.toJSONString(entityQuery));
        }
    }
    
    /**
     * 通用查询，传了分页参数自动分页
     * <p>Title         : query lilinhai 2018年2月8日 上午9:50:54</p>
     * @param entityQuery
     * @return 
     * RestResponse
     */
    @PostMapping("/find")
    protected RestResponse find(@RequestBody EntityQuery entityQuery, HttpServletRequest request)
    {
        try
        {
            process(request);
            
            List<Entity> entityList = entityService.find(entityQuery);
            List<EntityBean> beanList = new ArrayList<EntityBean>();
            EntityBean entityBean = null;
            for (Entity entity : entityList)
            {
                entityBean = new EntityBean(entity);
                
                // 过滤EntityBeanMap中的一些敏感字段，不需要传递到前端去的字段
                entityBeanMapFilter(entityBean, entity);
                beanList.add(entityBean);
            }
            
            // 分页参数为空，则不进行分页查询
            if (entityQuery.getPage() == null || entityQuery.getSize() == null)
            {
                if (entityList == null)
                {
                    RestResponse restResponse = fail(RestErrorCode.QUERY_FAIL, "[Controller] query occor an error, entityQuery：" + JSON.toJSONString(entityQuery));
                    logger.error(JSON.toJSONString(restResponse));
                    return restResponse;
                }
                return success(beanList);
            }
            // 分页查询
            else
            {
                PaginationData<EntityBean> pageData = new PaginationData<EntityBean>();
                pageData.setPage(entityQuery.getPage());
                pageData.setSize(entityQuery.getSize());
                pageData.setTotal(entityService.count(entityQuery));
                pageData.setDataList(beanList);
                return success(pageData);
            }
        }
        catch (Throwable e)
        {
            logger.error("[Controller] find(@RequestBody EntityQuery entityQuery, HttpServletRequest request) occor an error", e);
            return fail(RestErrorCode.QUERY_EXCEPTION, e.getMessage() + "，entityQuery：" + JSON.toJSONString(entityQuery));
        }
    }
}
