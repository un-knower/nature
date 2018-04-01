/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : DeleteRestApi.java</p>
 * <p>Package     : pers.linhai.nature.j2ee.generator.restapi.mappings</p>
 * @Creator lilinhai 2018年4月1日 下午4:42:14
 * @Version  V1.0  
 */ 

package pers.linhai.nature.j2ee.generator.restapi.mappings;

import pers.linhai.nature.utils.NamingUtils;

/**
 * <p>ClassName      : DeleteRestApi</p>
 * @author lilinhai 2018年4月1日 下午4:42:14
 * @version 1.0
 */
public class DeleteRestApi extends RestApi
{

    /**
     * <p>Title        : DeleteRestApi lilinhai 2018年4月1日 下午10:29:51</p>
     */ 
    public DeleteRestApi(String entityName)
    {
        url = NamingUtils.controllerMappingName(entityName) + "/{id}";
        method = "DELETE";
        apiName = "根据ID删除单个实体";
    }
}
