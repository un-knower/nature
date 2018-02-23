/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : BaseController.java</p>
 * <p>Package     : com.meme.crm.web.controller.core</p>
 * @Creator lilinhai 2018年2月5日 下午5:45:55
 * @Version  V1.0  
 */ 

package pers.linhai.nature.j2ee.core.controller;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
     * 根据主键删除一个实体
     * <p>Title         : deleteByPrimaryKey lilinhai 2018年2月5日 下午11:23:58</p>
     * @param id
     * @return 
     * ResponseResult
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    protected RestResponse delete(@PathVariable Key id)
    {
        try
        {
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
            logger.error("[Controller] delete occor an error", e);
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
    @RequestMapping(value = "", method = RequestMethod.POST)
    protected RestResponse save(@RequestBody Entity record)
    {
        try
        {
            int count = entityService.save(record);
            if (count != 1)
            {
                RestResponse restResponse = fail(RestErrorCode.INSERT_FAIL, "[Controller] save occor an error, record：" + record);
                logger.error(JSON.toJSONString(restResponse));
                return restResponse;
            }
            return success(record);
        }
        catch (Throwable e)
        {
            logger.error("[Controller] save occor an error", e);
            return fail(RestErrorCode.INSERT_EXCEPTION, e.getMessage() + "，record：" + record);
        }
    }

    /**
     * 选择性创建一个实体
     * <p>Title         : insertSelective lilinhai 2018年2月5日 下午11:23:21</p>
     * @param record
     * @return 
     * ResponseResult
     */
    @RequestMapping(value = "/selective", method = RequestMethod.POST)
    protected RestResponse saveSelective(Entity record)
    {
        try
        {
            int count = entityService.saveSelective(record);
            if (count != 1)
            {
                RestResponse restResponse = fail(RestErrorCode.INSERT_SELECTIVE_FAIL, "[Controller] saveSelective occor an error, record：" + record);
                logger.error(JSON.toJSONString(restResponse));
                return restResponse;
            }
            return success(record);
        }
        catch (Throwable e)
        {
            logger.error("[Controller] saveSelective occor an error", e);
            return fail(RestErrorCode.INSERT_SELECTIVE_EXCEPTION, e.getMessage() + "，record：" + record);
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
    @RequestMapping(value = "/selective/{id}", method = RequestMethod.PUT)
    protected RestResponse updateSelective(@RequestBody Entity record, @PathVariable Key id)
    {
        try
        {
            record.setId(id);
            int count = entityService.updateSelective(record);
            if (count != 1)
            {
                RestResponse restResponse = fail(10300, "[Controller] updateSelective occor an error, record：" + record);
                logger.error(JSON.toJSONString(restResponse));
                return restResponse;
            }
            return success(record);
        }
        catch (Throwable e)
        {
            logger.error("[Controller] updateSelective occor an error", e);
            return fail(10301, e.getMessage() + "，record：" + record);
        }
    }

    /**
     * 根据主键更新记录
     * <p>Title         : updateByPrimaryKey lilinhai 2018年2月5日 下午11:22:26</p>
     * @param record
     * @param id
     * @return 
     * ResponseResult
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    protected RestResponse update(@RequestBody Entity record, @PathVariable Key id)
    {
        try
        {
            record.setId(id);
            int count = entityService.update(record);
            if (count != 1)
            {
                RestResponse restResponse = fail(10302, "[Controller] update occor an error, record：" + record);
                logger.error(JSON.toJSONString(restResponse));
                return restResponse;
            }
            return success(record);
        }
        catch (Throwable e)
        {
            logger.error("[Controller] update occor an error", e);
            return fail(10303, e.getMessage() + "，record：" + record);
        }
    }

    /**
     * 根据主键查找单个记录
     * <p>Title         : selectByPrimaryKey lilinhai 2018年2月5日 下午11:23:05</p>
     * @param id
     * @return 
     * ResponseResult
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    protected RestResponse find(@PathVariable Key id)
    {
        try
        {
            EntityBean record = entityService.find(id);
            if (record == null)
            {
                RestResponse restResponse = fail(RestErrorCode.GET_FAIL, "[Controller] find occor an error, id：" + id);
                logger.error(JSON.toJSONString(restResponse));
                return restResponse;
            }
            return success(record);
        }
        catch (Throwable e)
        {
            logger.error("[Controller] find occor an error", e);
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
    @RequestMapping(value = "/findOne", method = RequestMethod.POST)
    protected RestResponse findOne(@RequestBody EntityQuery entityQuery)
    {
        try
        {
            EntityBean record = entityService.findOne(entityQuery);
            if (record == null)
            {
                RestResponse restResponse = fail(RestErrorCode.GET_FAIL, "[Controller] findOne occor an error, entityQuery：" + entityQuery);
                logger.error(JSON.toJSONString(restResponse));
                return restResponse;
            }
            return success(record);
        }
        catch (Throwable e)
        {
            logger.error("[Controller] findOne occor an error", e);
            return fail(RestErrorCode.GET_EXCEPTION, e.getMessage() + "，entityQuery：" + entityQuery);
        }
    }
    
    /**
     * 分页查询
     * <p>Title         : pageQuery lilinhai 2018年2月7日 下午6:36:29</p>
     * @param entityQuery
     * @return 
     * PaginationData<EntityBean>
     */
    @RequestMapping(value = "/pagequery", method = RequestMethod.POST)
    protected RestResponse pageQuery(@RequestBody EntityQuery entityQuery)
    {
        try
        {
            PaginationData<EntityBean> pageData = entityService.pageQuery(entityQuery);
            if (pageData == null)
            {
                RestResponse restResponse = fail(RestErrorCode.PAGE_QUERY_FAIL, "[Controller] pageQuery occor an error, entityQuery：" + entityQuery);
                logger.error(JSON.toJSONString(restResponse));
                return restResponse;
            }
            return success(pageData);
        }
        catch (Throwable e)
        {
            logger.error("[Controller] pageQuery occor an error", e);
            return fail(RestErrorCode.PAGE_QUERY_EXCEPTION, e.getMessage() + "，entityQuery：" + entityQuery);
        }
    }
    
    /**
     * 普通查询，不带分页
     * <p>Title         : query lilinhai 2018年2月8日 上午9:50:54</p>
     * @param entityQuery
     * @return 
     * RestResponse
     */
    @RequestMapping(value = "/find", method = RequestMethod.POST)
    protected RestResponse find(@RequestBody EntityQuery entityQuery)
    {
        try
        {
            List<EntityBean> dataList = entityService.find(entityQuery);
            if (dataList == null)
            {
                RestResponse restResponse = fail(RestErrorCode.QUERY_FAIL, "[Controller] query occor an error, entityQuery：" + entityQuery);
                logger.error(JSON.toJSONString(restResponse));
                return restResponse;
            }
            return success(dataList);
        }
        catch (Throwable e)
        {
            logger.error("[Controller] query occor an error", e);
            return fail(RestErrorCode.QUERY_EXCEPTION, e.getMessage() + "，entityQuery：" + entityQuery);
        }
    }
}
