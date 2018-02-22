/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : BasePlugin.java</p>
 * <p>Package     : codegenerator.plugins</p>
 * @Creator lilinhai 2018年2月5日 下午4:30:09
 * @Version  V1.0  
 */ 

package pers.linhai.nature.j2ee.generator.plugins;

import java.util.List;
import java.util.Properties;

import pers.linhai.nature.j2ee.generator.core.api.PluginAdapter;
import pers.linhai.nature.j2ee.generator.exception.GeneratorException;

/**
 * <p>Description    : <pre>TODO(这里用一句话描述这个类的作用)</pre></p>
 * <p>ClassName      : BasePlugin</p>
 * @author lilinhai 2018年2月5日 下午4:30:09
 * @version 1.0
 */
public abstract class BasePlugin extends PluginAdapter
{

    /**
     * java源码生成地方
     */
    private static final String JAVA_SOURCE_FOLDER = "src/main/java";
    
    /**
     * resources源码生成地方
     */
    private static final String RESOURCES_FOLDER = "src/main/resources";
    
    /**
     * 组ID
     */
    protected String groupId;
    
    /**
     * 项目名
     */
    protected String projectName;
    
    /**
     * 输出路径
     */
    protected String outPutPath;
    
    /**
     * 模块项目ID
     */
    protected String artifactId;
    
    /**
     * 模块名
     */
    protected String moduleName;
    
    /** 
     * <p>Overriding Method: lilinhai 2018年2月5日 下午4:30:09</p>
     * <p>Title: validate</p>
     * @param warnings
     * @return 
     * @see pers.linhai.nature.j2ee.generator.core.api.Plugin#validate(java.util.List)
     */
    public boolean validate(List<String> warnings)
    {
        return true;
    }
    
    public void setProperties(Properties properties) 
    {
        super.setProperties(properties);
        groupId = getProperty("groupId");
        projectName = getProperty("projectName");
        outPutPath = getProperty("outPutPath");
    }

    protected String getTargetProjectJavaSourceFolder()
    {
        if (!(outPutPath.endsWith("/") || outPutPath.endsWith("\\")))
        {
            outPutPath += "/";
        }
        return outPutPath + artifactId + "/" + JAVA_SOURCE_FOLDER;
    }
    
    protected String getTargetProjectResourcesFolder()
    {
        if (!(outPutPath.endsWith("/") || outPutPath.endsWith("\\")))
        {
            outPutPath += "/";
        }
        return outPutPath + artifactId + "/" + RESOURCES_FOLDER;
    }
    
    protected String getTargetPackae(String subPackage)
    {
        return getTargetPackae(moduleName, subPackage);
    }
    
    protected String getTargetPackae(String moduleName, String subPackage)
    {
        if (subPackage == null || subPackage.length() == 0)
        {
            return groupId + "." + projectName + "." + moduleName;
        }
        return groupId + "." + projectName + "." + moduleName + "." + subPackage;
    }
    
    protected String getProperty(String name)
    {
        String targetProject = properties.getProperty(name);
        if (targetProject == null)
        {
            throw new GeneratorException(getClass().getName() + "." + name + " is null.");
        }
        return targetProject;
    }
}
