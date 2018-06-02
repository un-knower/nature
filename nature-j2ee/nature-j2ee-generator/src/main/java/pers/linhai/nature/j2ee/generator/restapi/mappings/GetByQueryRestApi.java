/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : DeleteRestApi.java</p>
 * <p>Package     : pers.linhai.nature.j2ee.generator.restapi.mappings</p>
 * @Creator lilinhai 2018年4月1日 下午4:42:14
 * @Version  V1.0  
 */ 

package pers.linhai.nature.j2ee.generator.restapi.mappings;

import pers.linhai.nature.j2ee.generator.utils.GeneratorNamerUtils;

/**
 * <p>ClassName      : DeleteRestApi</p>
 * @author lilinhai 2018年4月1日 下午4:42:14
 * @version 1.0
 */
public class GetByQueryRestApi extends RestApi
{

    /**
     * <p>Title        : GetByQueryRestApi lilinhai 2018年4月1日 下午10:29:51</p>
     */ 
    public GetByQueryRestApi(String entityName)
    {
        url = "/" + GeneratorNamerUtils.controllerMappingName(entityName) + "/get";
        method = "POST";
        apiName = "根据查询条件查找单个实体";
        apiColor = "#10A54A";
        apiId = 5;
    }
}
