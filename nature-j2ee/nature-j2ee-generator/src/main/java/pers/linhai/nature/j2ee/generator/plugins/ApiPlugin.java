/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : ApiPlugin.java</p>
 * <p>Package     : pers.linhai.nature.j2ee.generator.plugins</p>
 * @Creator lilinhai 2018年4月1日 下午4:33:53
 * @Version  V1.0  
 */ 

package pers.linhai.nature.j2ee.generator.plugins;

import java.util.List;
import java.util.Properties;

import pers.linhai.nature.j2ee.generator.core.api.IntrospectedColumn;
import pers.linhai.nature.j2ee.generator.core.api.IntrospectedTable;
import pers.linhai.nature.j2ee.generator.core.api.dom.java.TopLevelClass;
import pers.linhai.nature.j2ee.generator.restapi.EntityRestApi;
import pers.linhai.nature.j2ee.generator.restapi.EntityRestApiCache;
import pers.linhai.nature.j2ee.generator.restapi.FieldInfo;
import pers.linhai.nature.j2ee.generator.restapi.mappings.CountRestApi;
import pers.linhai.nature.j2ee.generator.restapi.mappings.DeleteRestApi;
import pers.linhai.nature.j2ee.generator.restapi.mappings.FindRestApi;
import pers.linhai.nature.j2ee.generator.restapi.mappings.GetByIdRestApi;
import pers.linhai.nature.j2ee.generator.restapi.mappings.GetByQueryRestApi;
import pers.linhai.nature.j2ee.generator.restapi.mappings.SaveRestApi;
import pers.linhai.nature.j2ee.generator.restapi.mappings.UpdateRestApi;
import pers.linhai.nature.utils.StringUtils;

/**
 * <p>Description    : <pre>TODO(这里用一句话描述这个类的作用)</pre></p>
 * <p>ClassName      : ApiPlugin</p>
 * @author lilinhai 2018年4月1日 下午4:33:53
 * @version 1.0
 */
public class ApiPlugin extends BasePlugin
{

    public void setProperties(Properties properties)
    {
        super.setProperties(properties);
        moduleName = "model";
        artifactId = projectName + "-" + moduleName;
    }

    /** 
     * <p>Overriding Method: lilinhai 2018年2月4日 下午3:36:17</p>
     * <p>Title: validate</p>
     * <p>Description: TODO</p>
     * @param warnings
     * @return 
     * @see pers.linhai.nature.j2ee.generator.core.api.Plugin#validate(java.util.List)
     */
    public boolean validate(List<String> warnings)
    {
        return true;
    }

    /** 
     * <p>Overriding Method: lilinhai 2018年4月1日 下午4:36:01</p>
     * <p>Title: modelBaseRecordClassGenerated</p>
     * @param topLevelClass
     * @param introspectedTable
     * @return 
     * @see pers.linhai.nature.j2ee.generator.core.api.PluginAdapter#modelBaseRecordClassGenerated(pers.linhai.nature.j2ee.generator.core.api.dom.java.TopLevelClass, pers.linhai.nature.j2ee.generator.core.api.IntrospectedTable)
     */ 
    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable)
    {
        EntityRestApi entityRestApi = new EntityRestApi();
        entityRestApi.setTableName(introspectedTable.getFullyQualifiedTable().getIntrospectedTableName());
        if (StringUtils.isEmpty(introspectedTable.getRemarks()))
        {
            entityRestApi.setEntityDesc("警告：该实体还没有加注释，含义不明，请加上!");
        }
        else
        {
            entityRestApi.setEntityDesc(introspectedTable.getRemarks());
        }
        entityRestApi.setEntityName(introspectedTable.getFullyQualifiedTable().getDomainObjectName());
        for (IntrospectedColumn introspectedColumn : introspectedTable.getAllColumns())
        {
            FieldInfo fieldInfo = new FieldInfo(introspectedColumn);
            entityRestApi.addFieldInfo(fieldInfo);
        }
        
        entityRestApi.addApi(new SaveRestApi(introspectedTable.getFullyQualifiedTable().getDomainObjectName()));
        entityRestApi.addApi(new DeleteRestApi(introspectedTable.getFullyQualifiedTable().getDomainObjectName()));
        entityRestApi.addApi(new UpdateRestApi(introspectedTable.getFullyQualifiedTable().getDomainObjectName()));
        entityRestApi.addApi(new GetByIdRestApi(introspectedTable.getFullyQualifiedTable().getDomainObjectName()));
        entityRestApi.addApi(new GetByQueryRestApi(introspectedTable.getFullyQualifiedTable().getDomainObjectName()));
        entityRestApi.addApi(new FindRestApi(introspectedTable.getFullyQualifiedTable().getDomainObjectName()));
        entityRestApi.addApi(new CountRestApi(introspectedTable.getFullyQualifiedTable().getDomainObjectName()));
        
        EntityRestApiCache.getInstance().addEntityRestApi(entityRestApi);
        return super.modelBaseRecordClassGenerated(topLevelClass, introspectedTable);
    }

    
    
    
    
    
    
    
    
    
}
