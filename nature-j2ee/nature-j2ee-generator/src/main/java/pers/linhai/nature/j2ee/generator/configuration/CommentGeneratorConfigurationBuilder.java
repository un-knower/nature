/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : JavaModelGeneratorConfigurationBuilder.java</p>
 * <p>Package     : codegenerator.configuration</p>
 * @Creator lilinhai 2018年2月5日 上午10:16:23
 * @Version  V1.0  
 */ 

package pers.linhai.nature.j2ee.generator.configuration;

import pers.linhai.nature.j2ee.generator.core.config.CommentGeneratorConfiguration;

/**
 * <p>ClassName      : SqlMapGeneratorConfigurationBuilder</p>
 * @author lilinhai 2018年2月5日 上午10:16:23
 * @version 1.0
 */
public class CommentGeneratorConfigurationBuilder
{

    /**
     * CommentGeneratorConfiguration
     */
    private CommentGeneratorConfiguration commentGeneratorConfiguration = new CommentGeneratorConfiguration();

    public CommentGeneratorConfigurationBuilder type(String type)
    {
        commentGeneratorConfiguration.setConfigurationType(type);
        return this;
    }
    
    public CommentGeneratorConfigurationBuilder property(String name, String value)
    {
        commentGeneratorConfiguration.addProperty(name, value);
        return this;
    }
    
    public CommentGeneratorConfiguration build()
    {
        return commentGeneratorConfiguration;
    }
}
