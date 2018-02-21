/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : JavaModelGeneratorConfigurationBuilder.java</p>
 * <p>Package     : codegenerator.configuration</p>
 * @Creator lilinhai 2018年2月5日 上午10:16:23
 * @Version  V1.0  
 */ 

package pers.linhai.nature.j2ee.generator.configuration;

import pers.linhai.nature.j2ee.generator.core.config.SqlMapGeneratorConfiguration;

/**
 * <p>ClassName      : SqlMapGeneratorConfigurationBuilder</p>
 * @author lilinhai 2018年2月5日 上午10:16:23
 * @version 1.0
 */
public class SqlMapGeneratorConfigurationBuilder extends BaseConfigurationBuilder
{

    /**
     * JavaClientGeneratorConfiguration
     */
    private SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration = new SqlMapGeneratorConfiguration();

    /**
     * <p>Title        : SqlMapGeneratorConfigurationBuilder lilinhai 2018年2月5日 上午11:16:11</p>
     */ 
    public SqlMapGeneratorConfigurationBuilder(String groupId, String projectName)
    {
        super();
        propertyHolder = sqlMapGeneratorConfiguration;
        addProperty("enableSubPackages", "true");
        
        this.groupId = groupId;
        this.projectName = projectName;
        moduleName = "dao";
        artifactId = projectName + "-" + moduleName;
        targetProject(getTargetProjectResourcesFolder());
        targetPackage("mapper.entity");
    }

    private SqlMapGeneratorConfigurationBuilder targetPackage(String targetPackage)
    {
        sqlMapGeneratorConfiguration.setTargetPackage(targetPackage);
        return this;
    }
    
    private SqlMapGeneratorConfigurationBuilder targetProject(String targetProject)
    {
        sqlMapGeneratorConfiguration.setTargetProject(targetProject);
        return this;
    }
    
    public SqlMapGeneratorConfiguration build()
    {
        return sqlMapGeneratorConfiguration;
    }
}
