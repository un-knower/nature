/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : BaseController.java</p>
 * <p>Package     : com.meme.crm.web.controller.core</p>
 * @Creator lilinhai 2018年2月5日 下午5:45:55
 * @Version  V1.0  
 */ 

package pers.linhai.nature.j2ee.core.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.alibaba.fastjson.JSON;

import pers.linhai.nature.j2ee.core.constant.BaseErrorCode;
import pers.linhai.nature.j2ee.core.exception.ServiceException;
import pers.linhai.nature.j2ee.core.model.BaseEntity;
import pers.linhai.nature.j2ee.core.model.BaseQuery;
import pers.linhai.nature.j2ee.core.model.EntityBean;
import pers.linhai.nature.j2ee.core.service.IBaseEntityService;
import pers.linhai.nature.j2ee.core.service.PaginationData;
import pers.linhai.nature.j2ee.core.web.model.RestResponse;

/**
 * 控制器基类
 * <p>ClassName      : BaseController</p>
 * @author lilinhai 2018年2月5日 下午5:45:55
 * @version 1.0
 */
public abstract class BaseEntityController<Key, Entity extends BaseEntity<Key>, EntityQuery extends BaseQuery, EntityService extends IBaseEntityService<Key, Entity, EntityQuery>> 
    extends BaseController
{
    
    @Autowired
    protected EntityService entityService;
    
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
            entityService.delete(id);
            return success();
        }
        catch (ServiceException e)
        {
            logger.error("[Controller] delete(@PathVariable Key id, HttpServletRequest request) occor an error", e);
            return fail(e.getErrorCode(), e.getMessage());
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
            entityService.save(record);
            return success(record.toEntityBean());
        }
        catch (ServiceException e)
        {
            logger.error("[Controller] save(@RequestBody Entity record, HttpServletRequest request) occor an error", e);
            return fail(e.getErrorCode(), e.getMessage());
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
            record.setId(id);
            entityService.update(record);
            return success(record.toEntityBean());
        }
        catch (ServiceException e)
        {
            logger.error("[Controller] update(@RequestBody Entity record, @PathVariable Key id, HttpServletRequest request) occor an error", e);
            return fail(e.getErrorCode(), e.getMessage());
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
            EntityBean record = entityService.getEntityBean(id);
            if (record == null)
            {
                RestResponse restResponse = fail(BaseErrorCode.GET_FAIL, "[Controller] find occor an error, id：" + id);
                logger.error(JSON.toJSONString(restResponse));
                return restResponse;
            }
            return success(record);
        }
        catch (Throwable e)
        {
            logger.error("[Controller] get(@PathVariable Key id, HttpServletRequest request) occor an error", e);
            return fail(BaseErrorCode.GET_EXCEPTION, e.getMessage() + "，ID：" + id);
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
            EntityBean record = entityService.getEntityBean(entityQuery);
            if (record == null)
            {
                RestResponse restResponse = fail(BaseErrorCode.GET_FAIL, "[Controller] findOne occor an error, entityQuery：" + JSON.toJSONString(entityQuery));
                logger.error(JSON.toJSONString(restResponse));
                return restResponse;
            }
            return success(record);
        }
        catch (Throwable e)
        {
            logger.error("[Controller] get(@RequestBody EntityQuery entityQuery, HttpServletRequest request) occor an error", e);
            return fail(BaseErrorCode.GET_EXCEPTION, e.getMessage() + "，entityQuery：" + JSON.toJSONString(entityQuery));
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
            
            // 分页参数为空，则不进行分页查询
            if (entityQuery.getPage() == null || entityQuery.getSize() == null)
            {
                // 前端若是不分页，则最多返回1000条
                entityQuery.setPage(0);
                entityQuery.setSize(1000);
                return success(entityService.findEntityBean(entityQuery));
            }
            // 分页查询
            else
            {
                // 分页查询的size不能超过1000
                if (entityQuery.getSize() > 1000)
                {
                    return fail(BaseErrorCode.PAGE_QUERY_SIZE_TOO_LARGE_EXCEPTION, "The size of Paging-query can't exceed 1000!");
                }
                PaginationData<EntityBean> pageData = new PaginationData<EntityBean>();
                pageData.setPage(entityQuery.getPage());
                pageData.setSize(entityQuery.getSize());
                pageData.setTotal(entityService.count(entityQuery));
                pageData.setDataList(entityService.findEntityBean(entityQuery));
                return success(pageData);
            }
        }
        catch (Throwable e)
        {
            logger.error("[Controller] find(@RequestBody EntityQuery entityQuery, HttpServletRequest request) occor an error", e);
            return fail(BaseErrorCode.QUERY_EXCEPTION, e.getMessage() + "，entityQuery：" + JSON.toJSONString(entityQuery));
        }
    }
    
    /**
     * 统计记录数
     * <p>Title         : query lilinhai 2018年2月8日 上午9:50:54</p>
     * @param entityQuery
     * @return 
     * RestResponse
     */
    @PostMapping("/count")
    protected RestResponse count(@RequestBody EntityQuery entityQuery, HttpServletRequest request)
    {
        try
        {
            process(request);
            return success(entityService.count(entityQuery));
        }
        catch (Throwable e)
        {
            logger.error("[Controller] count(@RequestBody EntityQuery entityQuery, HttpServletRequest request) occor an error", e);
            return fail(BaseErrorCode.QUERY_EXCEPTION, e.getMessage() + "，entityQuery：" + JSON.toJSONString(entityQuery));
        }
    }
}
