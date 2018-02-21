/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : CodeConfiguration.java</p>
 * <p>Package     : codegenerator.configuration</p>
 * @Creator lilinhai 2018年2月5日 上午9:28:42
 * @Version  V1.0  
 */ 

package pers.linhai.nature.j2ee.generator.configuration;

import pers.linhai.nature.j2ee.generator.core.config.Configuration;
import pers.linhai.nature.j2ee.generator.core.config.Context;

/**
 * <p>ClassName      : CodeConfiguration</p>
 * @author lilinhai 2018年2月5日 上午9:28:42
 * @version 1.0
 */
public class CodeConfigurationBuilder
{

    /**
     * mybatis代码生成器配置
     */
    private Configuration configuration = new Configuration();
    
    /**
     * <p>Title        : CodeConfigurationBuilder lilinhai 2018年2月5日 上午9:56:01</p>
     * @param context
     */
    public CodeConfigurationBuilder(Context context)
    {
        this.configuration.addContext(context);
    }
    
    /**
     * 添加依赖jar包
     * <p>Title         : addClasspathEntry lilinhai 2018年2月5日 上午9:32:39</p>
     * @param classpathEntry 
     * void
     */
    public CodeConfigurationBuilder addClasspathEntry(String classpathEntry)
    {
        configuration.addClasspathEntry(classpathEntry);
        return this;
    }
    
    public Configuration build()
    {
        return configuration;
    }
}
