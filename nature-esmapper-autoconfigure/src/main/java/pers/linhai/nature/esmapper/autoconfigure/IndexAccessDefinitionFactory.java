/*
 * 项 目 名: Storage Tool Service V100R001C00 文 件 名:
 * esdao.esdao.spring.MyBeanDefinitionRegistryPostProcessor.java 版 权: XXX Technologies Co., Ltd.
 * Copyright 2017, All rights reserved. 描 述: XXX PROPRIETARY/CONFIDENTIAL. Use is subject to
 * license terms. 修 改 人: shinelon 修改时间: 2017年7月1日 修改内容: 创建
 */
package pers.linhai.nature.esmapper.autoconfigure;


import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;

import pers.linhai.nature.esmapper.autoconfigure.typeaccessor.impls.TypeAccessorInitializationSpringImpl;
import pers.linhai.nature.esmapper.core.impls.IndexMapperImpl;
import pers.linhai.nature.esmapper.core.processor.IndicesAdminClientProcessor;
import pers.linhai.nature.esmapper.exception.IndexScanException;
import pers.linhai.nature.esmapper.interfaces.TypeAccessorInitialization;
import pers.linhai.nature.esmapper.model.index.Index;
import pers.linhai.nature.utils.NamerUtils;


/**
 * @author  shinelon
 * @version  V100R001C00
 */
@SuppressWarnings("unchecked")
public class IndexAccessDefinitionFactory implements BeanDefinitionRegistryPostProcessor
{
    /**
     * 扫描哪个包下的索引注解
     */
    private String indexScan;

    /**
     *
     * @param beanFactory
     * @throws BeansException
     */
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory)
        throws BeansException
    {}

    /**
     * 
     * @param registry
     * @throws BeansException
     */
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry)
        throws BeansException
    {
        try
        {
            if (indexScan == null)
            {
                throw new IndexScanException("The package for the indexScan can't be null.");
            }

            List<Class<? extends Index>> indexClassList = scan();
            if (indexClassList.isEmpty())
            {
                throw new IndexScanException("IndexAccess-Frame-Initialization must contain at least one index class, but find nothing in the package: " + indexScan);
            }

            // typeAccessorInitialization初始化器spring实现
            TypeAccessorInitialization typeAccessorInitialization = new TypeAccessorInitializationSpringImpl(registry);
            for (Class<? extends Index> indexClass : indexClassList)
            {
                BeanDefinitionBuilder bdb = BeanDefinitionBuilder.genericBeanDefinition(IndexMapperImpl.class);
                Index index = indexClass.getDeclaredConstructor().newInstance();

                // 添加构造函数参数，需要顺序添加
                bdb.addConstructorArgValue(index);

                // 需要在依赖注入分析逻辑执行之前将自定义bean进行注册，否则会出现泛型对象注入错误问题
                bdb.addConstructorArgValue(IndicesAdminClientProcessor.newInstance(index, typeAccessorInitialization));
                String name = NamerUtils.classToProperty(indexClass) + "Accessor";

                // 可以自动生成name
                BeanDefinitionHolder definitionHolder = new BeanDefinitionHolder(bdb.getRawBeanDefinition(), name);
                BeanDefinitionReaderUtils.registerBeanDefinition(definitionHolder, registry);
                Logger.getLogger(getClass().getName()).info("[spring] Register index-bean-definition[" + name + "] successfully. ");
            }
        }
        catch (Throwable e)
        {
            throw new BeanDefinitionStoreException("Error in postProcessBeanDefinitionRegistry", e);
        }
    }

    private List<Class<? extends Index>> scan()
        throws Exception
    {
        List<Class<? extends Index>> indexClassList = new ArrayList<Class<? extends Index>>();
        StandardEnvironment sr = new StandardEnvironment();
        String pck = ClassUtils.convertClassNameToResourcePath(sr.resolveRequiredPlaceholders(indexScan));
        String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + pck + '/' + "**/*.class";
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resourceLoader);

        ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();
        Resource[] resources = ResourcePatternUtils.getResourcePatternResolver(resourceLoader).getResources(packageSearchPath);
        for (Resource resource : resources)
        {
            if (resource.isReadable())
            {
                MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);
                Class<?> resolvedClass = ClassUtils.forName(metadataReader.getAnnotationMetadata().getClassName(), beanClassLoader);
                if (resolvedClass.getSuperclass() == Index.class)
                {
                    indexClassList.add((Class<? extends Index>)resolvedClass);
                }
            }
        }
        return indexClassList;
    }

    /**
     * 对indexScan进行赋值
     *
     * @param indexScan
     */
    public void setIndexScan(String indexScan)
    {
        this.indexScan = indexScan;
    }
}
