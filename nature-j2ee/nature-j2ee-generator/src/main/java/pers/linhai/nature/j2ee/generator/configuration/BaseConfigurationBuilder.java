/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : BaseConfiguration.java</p>
 * <p>Package     : codegenerator.configuration</p>
 * @Creator lilinhai 2018年2月5日 上午11:09:33
 * @Version  V1.0  
 */ 

package pers.linhai.nature.j2ee.generator.configuration;

import pers.linhai.nature.j2ee.generator.core.config.PropertyHolder;
import pers.linhai.nature.j2ee.generator.plugins.BasePlugin;

/**
 * <p>Description    : <pre>TODO(这里用一句话描述这个类的作用)</pre></p>
 * <p>ClassName      : BaseConfiguration</p>
 * @author lilinhai 2018年2月5日 上午11:09:33
 * @version 1.0
 */
public abstract class BaseConfigurationBuilder extends BasePlugin
{

    protected PropertyHolder propertyHolder;
    
    public BaseConfigurationBuilder addProperty(String name, String value) 
    {
        propertyHolder.addProperty(name, value);
        return this;
    }
    
    public abstract PropertyHolder build();
}
