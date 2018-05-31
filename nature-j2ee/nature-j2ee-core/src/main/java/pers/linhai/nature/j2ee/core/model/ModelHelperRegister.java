/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : ModelHelperRegister.java</p>
 * <p>Package     : pers.linhai.nature.j2ee.core.model</p>
 * @Creator lilinhai 2018年5月31日 上午11:45:56
 * @Version  V1.0  
 */ 

package pers.linhai.nature.j2ee.core.model;

import java.util.List;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

/**
 * <p>ClassName      : ModelHelperRegister</p>
 * @author lilinhai 2018年5月31日 上午11:45:56
 * @version 1.0
 */
@Component
public class ModelHelperRegister implements InitializingBean   
{
    
    @Autowired
    private ResourceLoader resourceLoader;
    
    @Autowired
    private BeanFactory beanFactory;

    
    /** 
     * <p>Overriding Method: lilinhai 2018年5月31日 下午3:06:10</p>
     * <p>Title: afterPropertiesSet</p>
     * @throws Exception 
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */ 
    public void afterPropertiesSet() throws Exception
    {
        List<String> packages = AutoConfigurationPackages.get(this.beanFactory);
        for (String pkg : packages)
        {
            // 扫描所有实体
            String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + pkg.replace('.', '/') + '/' + "**/*.class";
            MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resourceLoader);
            Resource[] resources = ResourcePatternUtils.getResourcePatternResolver(resourceLoader).getResources(packageSearchPath);
            for (Resource resource : resources)
            {
                if (resource.isReadable())
                {
                    MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);
                    Class<?> resolvedClass = ClassUtils.forName(metadataReader.getAnnotationMetadata().getClassName(), resourceLoader.getClassLoader());
                    if (resolvedClass.getSuperclass() == BaseEntity.class)
                    {
                        ModelHelperCache.getInstance().put(resolvedClass.getSimpleName(), (ModelHelper)resolvedClass.getConstructor().newInstance());
                    }
                }
            }
        }
    }
}
