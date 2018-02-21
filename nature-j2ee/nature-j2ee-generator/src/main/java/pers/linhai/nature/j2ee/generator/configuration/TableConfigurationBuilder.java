/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : JavaModelGeneratorConfigurationBuilder.java</p>
 * <p>Package     : codegenerator.configuration</p>
 * @Creator lilinhai 2018年2月5日 上午10:16:23
 * @Version  V1.0  
 */ 

package pers.linhai.nature.j2ee.generator.configuration;

import pers.linhai.nature.j2ee.generator.core.config.Context;
import pers.linhai.nature.j2ee.generator.core.config.TableConfiguration;

/**
 * <p>ClassName      : SqlMapGeneratorConfigurationBuilder</p>
 * @author lilinhai 2018年2月5日 上午10:16:23
 * @version 1.0
 */
public class TableConfigurationBuilder extends BaseConfigurationBuilder
{

    /**
     * TableConfiguration
     */
    private TableConfiguration tableConfiguration;
    
    /**
     * <p>Title        : TableConfigurationBuilder lilinhai 2018年2月5日 上午10:49:45</p>
     * @param tableConfiguration 
     */ 
    public TableConfigurationBuilder(Context context)
    {
        tableConfiguration = new TableConfiguration(context);
        propertyHolder = tableConfiguration;
        tableName("%");
        enableCountByExample(false);
        enableUpdateByExample(false);
        enableDeleteByExample(false);
        enableSelectByExample(false);
        selectByExampleQueryId("false");
    }

    public TableConfigurationBuilder catalog(String catalog)
    {
        tableConfiguration.setCatalog(catalog);
        return this;
    }
    
    public TableConfigurationBuilder schema(String schema)
    {
        tableConfiguration.setSchema(schema);
        return this;
    }
    
    public TableConfigurationBuilder tableName(String tableName)
    {
        tableConfiguration.setTableName(tableName);
        return this;
    }
    
    public TableConfigurationBuilder domainObjectName(String domainObjectName)
    {
        tableConfiguration.setDomainObjectName(domainObjectName);
        return this;
    }
    
    public TableConfigurationBuilder alias(String alias)
    {
        tableConfiguration.setAlias(alias);
        return this;
    }
    
    public TableConfigurationBuilder enableInsert(boolean bool)
    {
        tableConfiguration.setInsertStatementEnabled(bool);
        return this;
    }
    
    public TableConfigurationBuilder enableSelectByPrimaryKey(boolean bool)
    {
        tableConfiguration.setSelectByPrimaryKeyStatementEnabled(bool);
        return this;
    }
    
    public TableConfigurationBuilder enableSelectByExample(boolean bool)
    {
        tableConfiguration.setSelectByExampleStatementEnabled(bool);
        return this;
    }
    
    public TableConfigurationBuilder enableUpdateByPrimaryKey(boolean bool)
    {
        tableConfiguration.setUpdateByPrimaryKeyStatementEnabled(bool);
        return this;
    }
    
    public TableConfigurationBuilder enableDeleteByPrimaryKey(boolean bool)
    {
        tableConfiguration.setDeleteByPrimaryKeyStatementEnabled(bool);
        return this;
    }
    
    public TableConfigurationBuilder enableDeleteByExample(boolean bool)
    {
        tableConfiguration.setDeleteByExampleStatementEnabled(bool);
        return this;
    }
    
    public TableConfigurationBuilder enableCountByExample(boolean bool)
    {
        tableConfiguration.setCountByExampleStatementEnabled(bool);
        return this;
    }
    
    public TableConfigurationBuilder enableUpdateByExample(boolean bool)
    {
        tableConfiguration.setUpdateByExampleStatementEnabled(bool);
        return this;
    }
    
    public TableConfigurationBuilder selectByPrimaryKeyQueryId(String selectByPrimaryKeyQueryId)
    {
        tableConfiguration.setSelectByPrimaryKeyQueryId(selectByPrimaryKeyQueryId);
        return this;
    }
    
    public TableConfigurationBuilder selectByExampleQueryId(String selectByExampleQueryId)
    {
        tableConfiguration.setSelectByExampleQueryId(selectByExampleQueryId);
        return this;
    }
    
    public TableConfigurationBuilder modelType(String modelType)
    {
        tableConfiguration.setConfiguredModelType(modelType);
        return this;
    }
    
    public TableConfigurationBuilder delimitIdentifiers(boolean bool)
    {
        tableConfiguration.setDelimitIdentifiers(bool);
        return this;
    }
    
    public TableConfigurationBuilder escapeWildcards(boolean bool)
    {
        tableConfiguration.setWildcardEscapingEnabled(bool);
        return this;
    }
    
    public TableConfigurationBuilder delimitAllColumns(boolean bool)
    {
        tableConfiguration.setAllColumnDelimitingEnabled(bool);
        return this;
    }
    
    public TableConfigurationBuilder mapperName(String mapperName)
    {
        tableConfiguration.setMapperName(mapperName);
        return this;
    }
    
    public TableConfigurationBuilder sqlProviderName(String sqlProviderName)
    {
        tableConfiguration.setSqlProviderName(sqlProviderName);
        return this;
    }
    
    public TableConfiguration build()
    {
        return tableConfiguration;
    }
}
