/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : EntityRestApi.java</p>
 * <p>Package     : pers.linhai.nature.j2ee.generator.restapi</p>
 * @Creator lilinhai 2018年4月1日 下午4:41:02
 * @Version  V1.0  
 */ 

package pers.linhai.nature.j2ee.generator.restapi;

import java.util.ArrayList;
import java.util.List;

import pers.linhai.nature.j2ee.generator.restapi.mappings.RestApi;

/**
 * <p>ClassName      : EntityRestApi</p>
 * @author lilinhai 2018年4月1日 下午4:41:02
 * @version 1.0
 */
public class EntityRestApi implements Comparable<EntityRestApi>
{

    /**
     * 实体对应的数据库表名
     */
    private String tableName;
    
    /**
     * 实体描述
     */
    private String entityDesc;
    
    /**
     * java实体名
     */
    private String entityName;
    
    /**
     * 字段信息列表
     */
    private List<FieldInfo> fieldInfoList = new ArrayList<FieldInfo>();
    
    /**
     * api集合
     */
    private List<RestApi> apiList = new ArrayList<RestApi>();

    /**
     * <p>Get Method   :   tableName String</p>
     * @return tableName
     */
    public String getTableName()
    {
        return tableName;
    }

    /**
     * <p>Set Method   :   tableName String</p>
     * @param tableName
     */
    public void setTableName(String tableName)
    {
        this.tableName = tableName;
    }

    /**
     * <p>Get Method   :   entityDesc String</p>
     * @return entityDesc
     */
    public String getEntityDesc()
    {
        return entityDesc;
    }

    /**
     * <p>Set Method   :   entityDesc String</p>
     * @param entityDesc
     */
    public void setEntityDesc(String entityDesc)
    {
        this.entityDesc = entityDesc;
    }
    
    /**
     * <p>Get Method   :   entityName String</p>
     * @return entityName
     */
    public String getEntityName()
    {
        return entityName;
    }

    /**
     * <p>Set Method   :   entityName String</p>
     * @param entityName
     */
    public void setEntityName(String entityName)
    {
        this.entityName = entityName;
    }

    /**
     * <p>Get Method   :   apiList List<RestApi></p>
     * @return apiList
     */
    public List<RestApi> getApiList()
    {
        return apiList;
    }

    /**
     * <p>Set Method   :   apiList List<RestApi></p>
     * @param apiList
     */
    public void addApi(RestApi restApi)
    {
        this.apiList.add(restApi);
    }

    /**
     * <p>Get Method   :   fieldInfoList List<FieldInfo></p>
     * @return fieldInfoList
     */
    public List<FieldInfo> getFieldInfoList()
    {
        return fieldInfoList;
    }

    /**
     * <p>Set Method   :   fieldInfoList List<FieldInfo></p>
     * @param fieldInfoList
     */
    public void addFieldInfo(FieldInfo fieldInfo)
    {
        this.fieldInfoList.add(fieldInfo);
    }

    /** 
     * <p>Overriding Method: lilinhai 2018年4月2日 下午3:39:08</p>
     * <p>Title: compareTo</p>
     * <p>Description: TODO</p>
     * @param o
     * @return 
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */ 
    public int compareTo(EntityRestApi o)
    {
        return tableName.compareTo(o.tableName);
    }
}
