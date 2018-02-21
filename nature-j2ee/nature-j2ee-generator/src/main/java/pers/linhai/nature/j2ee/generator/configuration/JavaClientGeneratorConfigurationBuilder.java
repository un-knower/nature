/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : JavaModelGeneratorConfigurationBuilder.java</p>
 * <p>Package     : codegenerator.configuration</p>
 * @Creator lilinhai 2018年2月5日 上午10:16:23
 * @Version  V1.0  
 */ 

package pers.linhai.nature.j2ee.generator.configuration;

import pers.linhai.nature.j2ee.generator.core.config.JavaClientGeneratorConfiguration;
import pers.linhai.nature.j2ee.generator.enumer.ConfigurationType;

/**
 * <p>ClassName      : JavaModelGeneratorConfigurationBuilder</p>
 * @author lilinhai 2018年2月5日 上午10:16:23
 * @version 1.0
 */
public class JavaClientGeneratorConfigurationBuilder extends BaseConfigurationBuilder
{

    /**
     * JavaClientGeneratorConfiguration
     */
    private JavaClientGeneratorConfiguration javaClientGeneratorConfiguration = new JavaClientGeneratorConfiguration();

    /**
     * <p>Title        : JavaClientGeneratorConfigurationBuilder lilinhai 2018年2月5日 上午11:16:56</p>
     */ 
    public JavaClientGeneratorConfigurationBuilder(String groupId, String projectName)
    {
        super();
        propertyHolder = javaClientGeneratorConfiguration;
        addProperty("enableSubPackages", "true");
        
        this.groupId = groupId;
        this.projectName = projectName;
        moduleName = "dao";
        artifactId = projectName + "-" + moduleName;
        targetPackage(getTargetPackae("mapper"));
        targetProject(getTargetProjectJavaSourceFolder());
    }

    private JavaClientGeneratorConfigurationBuilder targetPackage(String targetPackage)
    {
        javaClientGeneratorConfiguration.setTargetPackage(targetPackage);
        return this;
    }
    
    private JavaClientGeneratorConfigurationBuilder targetProject(String targetProject)
    {
        javaClientGeneratorConfiguration.setTargetProject(targetProject);
        return this;
    }
    
    public JavaClientGeneratorConfigurationBuilder type(ConfigurationType configurationType)
    {
        javaClientGeneratorConfiguration.setConfigurationType(configurationType.getValue());
        return this;
    }
    
    public JavaClientGeneratorConfigurationBuilder implementationPackage(String implementationPackage)
    {
        javaClientGeneratorConfiguration.setImplementationPackage(implementationPackage);
        return this;
    }
    
    public JavaClientGeneratorConfiguration build()
    {
        return javaClientGeneratorConfiguration;
    }
}
