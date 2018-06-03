/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : JointQueryController.java</p>
 * <p>Package     : pers.linhai.nature.j2ee.core.web.controller</p>
 * @Creator lilinhai 2018年6月3日 下午8:12:11
 * @Version  V1.0  
 */ 

package pers.linhai.nature.j2ee.core.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pers.linhai.nature.j2ee.core.constant.BaseErrorCode;
import pers.linhai.nature.j2ee.core.model.JointQuery;
import pers.linhai.nature.j2ee.core.model.join.JointEntityBean;
import pers.linhai.nature.j2ee.core.service.IJointQuerySerivce;
import pers.linhai.nature.j2ee.core.web.exception.ControllerException;
import pers.linhai.nature.j2ee.core.web.model.PaginationData;
import pers.linhai.nature.j2ee.core.web.model.RestResponse;

/**
 * 关联查询公共控制层
 * <pre>
 * {
        from: [
            {
                joinType: "leftJoin",
                left: {
                    entity: "",
                    field: ""
                },
                right: {
                    entity: "",
                    field: ""
                }
            }
        ],
        select: [
            {
                entity: t,
                fieldList: ""
            },
            {
                entity: t,
                fieldList: ""
            }
        ],
        where:{
            conditionList: [
                {
                    "id": "1",
                    "entity": "",
                    "field": "field",
                    "operator": "like",
                    "value": "%(这是模糊查询，放到该括号中的值为真实值或真实值的一部分)%"
                }
            ],
            "expression" : ""
        },
        orderBy: [
            {
                entity: t,
                field: "",
                direction: ""
            }
        ],
        page: 0,
        size: 10
    }
    </pre>
 * <p>ClassName      : JointQueryController</p>
 * @author lilinhai 2018年6月3日 下午8:12:11
 * @version 1.0
 */
@RestController
@RequestMapping("/joint-query")
public class JointQueryController extends BaseController
{
    
    @Autowired
    private IJointQuerySerivce jointQuerySerivce;
    
    /**
     * 通用关联查询，传了分页参数自动分页
     * <p>Title         : query lilinhai 2018年2月8日 上午9:50:54</p>
     * @param jointQuery
     * @return 
     * RestResponse
     */
    @PostMapping("/find")
    protected RestResponse find(HttpServletRequest request, HttpServletResponse response, @RequestBody JointQuery jointQuery)
    {
        try
        {
            // 分页参数为空，则不进行分页查询
            if (jointQuery.getPage() == null || jointQuery.getSize() == null)
            {
                // 前端若是不分页，则最多返回1000条
                jointQuery.setPage(0);
                jointQuery.setSize(1000);
                return success(jointQuerySerivce.findEntityBean(jointQuery));
            }
            // 分页查询
            else
            {
                // 分页查询的size不能超过1000
                if (jointQuery.getSize() > 1000)
                {
                    return fail(BaseErrorCode.PAGE_QUERY_SIZE_TOO_LARGE_EXCEPTION, "The size of Paging-query can't exceed 1000!");
                }
                PaginationData<JointEntityBean> pageData = new PaginationData<JointEntityBean>();
                pageData.setPage(jointQuery.getPage());
                pageData.setSize(jointQuery.getSize());
                pageData.setTotal(jointQuerySerivce.count(jointQuery));
                pageData.setDataList(jointQuerySerivce.findEntityBean(jointQuery));
                return success(pageData);
            }
        }
        catch (ControllerException e)
        {
            logger.error("[Controller] join-find(HttpServletRequest request, HttpServletResponse response, @RequestBody JointQuery jointQuery) occor an error", e);
            return fail(e.getErrorCode(), e.getMessage());
        }
        catch (Throwable e)
        {
            logger.error("[Controller] join-find(@RequestBody JointQuery jointQuery, HttpServletRequest request) occor an error", e);
            return fail(BaseErrorCode.QUERY_EXCEPTION, e.getMessage());
        }
    }
}
