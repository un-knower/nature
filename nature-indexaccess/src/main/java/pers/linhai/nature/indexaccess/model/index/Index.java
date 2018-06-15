/*
 * 项 目 名:  Storage Tool Service V100R001C00
 * 文 件 名:  pers.linhai.esdao.core.Index.java
 * 版       权:  XXX Technologies Co., Ltd. Copyright 2017,  All rights reserved.
 * 描       述:  XXX PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * 修 改 人:  shinelon
 * 修改时间:  2017年4月12日
 * 修改内容:  创建
 */
package pers.linhai.nature.indexaccess.model.index;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.settings.put.UpdateSettingsRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;

import pers.linhai.nature.indexaccess.exception.IndexConfigurationException;
import pers.linhai.nature.indexaccess.model.core.RolloverStrategy;
import pers.linhai.nature.indexaccess.model.index.section.AnalysisSection;
import pers.linhai.nature.indexaccess.model.index.section.IndexSection.DynamicSettings;
import pers.linhai.nature.indexaccess.model.index.section.IndexSection.StaticSettings;
import pers.linhai.nature.indexaccess.model.type.MappingConfiguration;
import pers.linhai.nature.indexaccess.model.type.Type;
import pers.linhai.nature.utils.NamerUtils;
import pers.linhai.nature.utils.StringUtils;

/**
 * 索引库配置
 * 
 * @author  shinelon
 * @version  V100R001C00
 */
public abstract class Index
{
    
    private static final Map<Class<? extends Index>, Index> indexMap = new HashMap<Class<? extends Index>, Index>();
    
    private final List<MappingConfiguration<? extends Type>> mappingConfigurationList = new ArrayList<MappingConfiguration<? extends Type>>();
    
    /**
     * 索引库配置
     */
    protected final IndexSettings indexSettings = new IndexSettings();
    
    /**
     * 索引创建策略
     */
    private RolloverStrategy rolloverStrategy;
    
    /**
     * 索引名
     */
    private String name;
    
    /**
     * 索引别名:默认为null
     */
    private String alias;
    
    /** 
     * <默认构造函数>
     * @param name
     */
    protected Index()
    {
        Class<? extends Index> indexClass = getClass();
        
        //校验索引对象只能初始化一次
        if (indexMap.containsKey(indexClass))
        {
            throw new IndexConfigurationException("Index[" + name + "] exists, it's can't be instantiated again.");
        }
        
        name = this.name();
        
        alias = this.alias();
        
        if (StringUtils.isEmpty(name))
        {
            name = NamerUtils.propertyToColumn(indexClass.getSimpleName());
        }
        
        if (StringUtils.isEmpty(alias))
        {
            alias = "alias_" + name;
        }
        
        // 如果索引别名和索引名同名，则异常抛出
        if (alias.equals(name))
        {
            throw new IndexConfigurationException("The alias can't be same to the index name, alias: " + alias + ", indexName:" + name);
        }
        
        indexMap.put(indexClass, this);
        
        Set<Class<? extends Type>> typeClassSet = new HashSet<Class<? extends Type>>();
        this.registerTypes(typeClassSet);
        if (typeClassSet.isEmpty())
        {
            throw new IndexConfigurationException("Index[" + name + "] must have at least one type.");
        }
        
        for (Class<? extends Type> typeClass : typeClassSet)
        {
            registerType(typeClass);
        }
        
        // 子索引管理策略
        this.rolloverStrategy = new RolloverStrategy();
        
        this.rolloverStrategy(rolloverStrategy);
        this.staticSettings(this.indexSettings.indexSection().staticSettings());
        this.dynamicSettings(this.indexSettings.indexSection().dynamicSettings());
        this.analysisSettings(this.indexSettings.analysisSection());
    }
    
    /**
     * 索引名
     * @return String
     */
    protected abstract String name();
    
    /**
     * 别名
     *
     * @return String
     */
    protected abstract String alias();
    
    /**
     * 注册mapping类型
     * @param typeClassSet void
     */
    protected abstract void registerTypes(Set<Class<? extends Type>> typeClassSet);
    
    /**
     * 子索引管理策略
     * @param staticSettings void
     */
    protected abstract void rolloverStrategy(RolloverStrategy rolloverStrategy);
    
    /**
     * 静态设置
     * @param staticSettings void
     */
    protected abstract void staticSettings(StaticSettings staticSettings);
    
    /**
     * 动态设置
     * @param dynamicSettings void
     */
    protected abstract void dynamicSettings(DynamicSettings dynamicSettings);
    
    /**
     * 分析设置
     *
     * @param analysisSection void
     */
    protected abstract void analysisSettings(AnalysisSection analysisSection);

    /**
     * 获取创建索引的请求对象
     *
     * @return
     * @throws IOException CreateIndexRequest
     */
    public final CreateIndexRequest getCreateIndexRequest() throws IOException
    {
        return getCreateIndexRequest(name);
    }
    
    /**
     * 获取创建索引的请求对象
     *
     * @return
     * @throws IOException CreateIndexRequest
     */
    public final CreateIndexRequest getCreateIndexRequest(String name) throws IOException
    {
        Objects.requireNonNull(name, "Index name can't be empty or null while in index creating.");
        
        CreateIndexRequest createIndexRequest = new CreateIndexRequest(name);
        if (indexSettings.hasNewConfig())
        {
            XContentBuilder jsonBuilder = XContentFactory.jsonBuilder();
            jsonBuilder.startObject().startObject("settings");
            indexSettings.build(jsonBuilder);
            jsonBuilder.endObject().endObject();
            
            createIndexRequest.source(jsonBuilder);
        }
        
        return createIndexRequest;
    }
    
    /**
     * 获取修改索引的请求对象
     *
     * @return
     * @throws IOException UpdateSettingsRequest
     */
    public final UpdateSettingsRequest getUpdateSettingsRequest() throws IOException
    {
        return getUpdateSettingsRequest(name);
    }
    
    /**
     * 获取修改索引的请求对象
     * @param name
     * @return
     * @throws IOException UpdateSettingsRequest
     */
    public final UpdateSettingsRequest getUpdateSettingsRequest(String name) throws IOException
    {
        Objects.requireNonNull(name, "Index name can't be empty or null while in index updating.");
        UpdateSettingsRequest updateSettingsRequest = new UpdateSettingsRequest(name);
        
        if (indexSettings.indexSection().dynamicSettings().hasNewConfig())
        {
            XContentBuilder jsonBuilder = XContentFactory.jsonBuilder();
            jsonBuilder.startObject();
            indexSettings.indexSection().dynamicSettings().build(jsonBuilder);
            jsonBuilder.endObject();
            updateSettingsRequest.settings(Settings.builder().loadFromSource(jsonBuilder.string(), XContentType.JSON).build());
        }
        
        return updateSettingsRequest;
    }
    
    /**
     * 往索引库中添加一个mappingType
     * @param type void
     */
    private <T extends Type> void registerType(Class<T> type)
    {
        mappingConfigurationList.add(new MappingConfiguration<T>(type, this));
    }
    
    /**
     * 返回 name
     *
     * @return name
     */
    public final String getName()
    {
        return name;
    }
    
    /**
     * 返回 alias
     *
     * @return alias
     */
    public final String getAlias()
    {
        return alias;
    }
    
    /**
     * 返回 mappingConfigurationList
     * @return mappingConfigurationList
     */
    public final List<MappingConfiguration<? extends Type>> getMappingConfigurationlist()
    {
        return mappingConfigurationList;
    }
    

    /**
     * 返回 rolloverStrategy
     * @return rolloverStrategy
     */
    public final RolloverStrategy getRolloverStrategy()
    {
        return rolloverStrategy;
    }

    /**
     * 
     *
     * @return
     */
    public String toString()
    {
        return "Index [name=" + name + ", alias=" + alias + "]";
    }
    
}
