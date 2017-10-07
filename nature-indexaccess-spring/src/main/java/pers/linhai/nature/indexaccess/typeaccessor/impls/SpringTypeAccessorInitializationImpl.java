/*
 * 项 目 名:  Storage Tool Service V100R001C00
 * 文 件 名:  pers.linhai.nature.indexaccess.typeaccessor.impls.SpringTypeAccessorInitializationImpl.java
 * 版       权:  XXX Technologies Co., Ltd. Copyright 2017,  All rights reserved.
 * 描       述:  XXX PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * 修 改 人:  lilinhai
 * 修改时间:  2017年10月6日
 * 修改内容:  创建
 */
package pers.linhai.nature.indexaccess.typeaccessor.impls;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.config.ConstructorArgumentValues.ValueHolder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.ScopeMetadata;
import org.springframework.context.annotation.ScopeMetadataResolver;

import pers.linhai.nature.indexaccess.core.impls.TypeAccessorImpl;
import pers.linhai.nature.indexaccess.core.processor.IndicesAdminClientProcessor;
import pers.linhai.nature.indexaccess.core.processor.TransportClientProcessor;
import pers.linhai.nature.indexaccess.interfaces.TypeAccessorInitialization;
import pers.linhai.nature.indexaccess.model.type.MappingConfiguration;
import pers.linhai.nature.indexaccess.model.type.Type;
import pers.linhai.nature.indexaccess.utils.NamingUtils;

/**
 * 
 * TypeAccessor spring初始化方案，主要用于为spring集成（将实例交给spring容器托管）完美对接
 * @author  lilinhai
 * @version  V100R001C00
 */
public class SpringTypeAccessorInitializationImpl implements TypeAccessorInitialization
{
    
    private ScopeMetadataResolver scopeMetadataResolver;
    
    private BeanNameGenerator beanNameGenerator;
    
    /**
     * spring bean定义注册器
     */
    private BeanDefinitionRegistry registry;
    
    /** 
     * <默认构造函数>
     *
     * @param registry
     * @param scopeMetadataResolver
     * @param beanNameGenerator
     */
    public SpringTypeAccessorInitializationImpl(BeanDefinitionRegistry registry
            , ScopeMetadataResolver scopeMetadataResolver, BeanNameGenerator beanNameGenerator)
    {
        super();
        this.registry = registry;
        this.scopeMetadataResolver = scopeMetadataResolver;
        this.beanNameGenerator = beanNameGenerator;
    }

    /**
     * 
     *
     * @param indicesAdminClientProcessor
     */
    public void initialize(IndicesAdminClientProcessor indicesAdminClientProcessor)
    {
        // 初始化该索引库下面的TransportClientProcessor,一个mapping对应一个TransportClientProcessor对象
        for (MappingConfiguration<? extends Type> tec : indicesAdminClientProcessor.getIndex().getMappingConfigurationlist())
        {
            TransportClientProcessor transportClientProcessor = TransportClientProcessor
                    .newInstance(indicesAdminClientProcessor.getWritingIndexName(), indicesAdminClientProcessor.getIndices(), tec.typeName());
            
            //添加缓存中
            indicesAdminClientProcessor.getTransportClientProcessorMap().put(transportClientProcessor.type(), transportClientProcessor);
            
            // 创建type实体对应的es mapping信息
            createTypeAccessor(tec, transportClientProcessor);
        }
    }
    
    /**
     * 创建type实体对应的es mapping信息
     *
     * @param type
     * @param typeAccessorMap void
     */
    private <T extends Type> void createTypeAccessor(MappingConfiguration<T> tec, TransportClientProcessor typeProcessor)
    {
        AnnotatedGenericBeanDefinition abd = new AnnotatedGenericBeanDefinition(TypeAccessorImpl.class);
        ConstructorArgumentValues ca = new ConstructorArgumentValues();
        ca.addIndexedArgumentValue(0, new ValueHolder(tec));
        ca.addIndexedArgumentValue(1, new ValueHolder(typeProcessor));
        abd.setConstructorArgumentValues(ca);
        ScopeMetadata scopeMetadata = scopeMetadataResolver.resolveScopeMetadata(abd);
        abd.setScope(scopeMetadata.getScopeName());
        String name = NamingUtils.accessorName(tec.getTypeClass());
        
        // 可以自动生成name
        String beanName = (name != null ? name : beanNameGenerator.generateBeanName(abd, registry));
        AnnotationConfigUtils.processCommonDefinitionAnnotations(abd);
        BeanDefinitionHolder definitionHolder = new BeanDefinitionHolder(abd, beanName);
        BeanDefinitionReaderUtils.registerBeanDefinition(definitionHolder, registry);
        
        Logger.getLogger(getClass().getName()).info("[spring] Register mapping-bean-definition[" + beanName + "] successfully. ");
    }
}
