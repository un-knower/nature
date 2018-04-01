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
 * <p>Description    : <pre>TODO(这里用一句话描述这个类的作用)</pre></p>
 * <p>ClassName      : DeleteRestApi</p>
 * @author lilinhai 2018年4月1日 下午4:42:14
 * @version 1.0
 */
public class FindRestApi extends RestApi
{

    /**
     * <p>Title        : FindRestApi lilinhai 2018年4月1日 下午10:29:51</p>
     */ 
    public FindRestApi(String entityName)
    {
        url = NamingUtils.controllerMappingName(entityName) + "/find";
        method = "POST";
        apiName = "根据查询条件查找多个实体";
    }
}
