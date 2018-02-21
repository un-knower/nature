/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : JavaModelGeneratorConfigurationBuilder.java</p>
 * <p>Package     : codegenerator.configuration</p>
 * @Creator lilinhai 2018年2月5日 上午10:16:23
 * @Version  V1.0  
 */ 

package pers.linhai.nature.j2ee.generator.configuration;

import pers.linhai.nature.j2ee.generator.core.config.JavaModelGeneratorConfiguration;

/**
 * <p>Description    : <pre>TODO(这里用一句话描述这个类的作用)</pre></p>
 * <p>ClassName      : JavaModelGeneratorConfigurationBuilder</p>
 * @author lilinhai 2018年2月5日 上午10:16:23
 * @version 1.0
 */
public class JavaModelGeneratorConfigurationBuilder extends BaseConfigurationBuilder
{

    /**
     * JavaModelGeneratorConfiguration
     */
    private JavaModelGeneratorConfiguration javaModelGeneratorConfiguration = new JavaModelGeneratorConfiguration();

    /**
     * <p>Title        : JavaModelGeneratorConfigurationBuilder lilinhai 2018年2月5日 上午11:08:51</p>
     */ 
    public JavaModelGeneratorConfigurationBuilder(String groupId, String projectName)
    {
        super();
        propertyHolder = javaModelGeneratorConfiguration;
        addProperty("trimStrings", "true");
        addProperty("enableSubPackages", "false");
        this.groupId = groupId;
        this.projectName = projectName;
        moduleName = "model";
        artifactId = projectName + "-" + moduleName;
        targetPackage(getTargetPackae("entity"));
        targetProject(getTargetProjectJavaSourceFolder());
    }
    
    

    private JavaModelGeneratorConfigurationBuilder targetPackage(String targetPackage)
    {
        javaModelGeneratorConfiguration.setTargetPackage(targetPackage);
        return this;
    }
    
    private JavaModelGeneratorConfigurationBuilder targetProject(String targetProject)
    {
        javaModelGeneratorConfiguration.setTargetProject(targetProject);
        return this;
    }

    /** 
     * <p>Overriding Method: lilinhai 2018年2月5日 上午11:17:53</p>
     * <p>Title: build</p>
     * @return 
     * @see pers.linhai.nature.j2ee.generator.configuration.BaseConfigurationBuilder#build()
     */ 
    public JavaModelGeneratorConfiguration build()
    {
        return javaModelGeneratorConfiguration;
    }
}
