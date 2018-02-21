/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : ContextBuilder.java</p>
 * <p>Package     : codegenerator.configuration</p>
 * @Creator lilinhai 2018年2月5日 上午9:37:35
 * @Version  V1.0  
 */ 

package pers.linhai.nature.j2ee.generator.configuration;

import pers.linhai.nature.j2ee.generator.core.api.dom.DefaultJavaFormatter;
import pers.linhai.nature.j2ee.generator.core.api.dom.DefaultXmlFormatter;
import pers.linhai.nature.j2ee.generator.core.config.CommentGeneratorConfiguration;
import pers.linhai.nature.j2ee.generator.core.config.Context;
import pers.linhai.nature.j2ee.generator.core.config.JDBCConnectionConfiguration;
import pers.linhai.nature.j2ee.generator.core.config.JavaClientGeneratorConfiguration;
import pers.linhai.nature.j2ee.generator.core.config.JavaModelGeneratorConfiguration;
import pers.linhai.nature.j2ee.generator.core.config.ModelType;
import pers.linhai.nature.j2ee.generator.core.config.PluginConfiguration;
import pers.linhai.nature.j2ee.generator.core.config.SqlMapGeneratorConfiguration;
import pers.linhai.nature.j2ee.generator.core.config.TableConfiguration;
import pers.linhai.nature.j2ee.generator.enumer.TargetRuntime;

/**
 * <p>Description    : <pre>TODO(这里用一句话描述这个类的作用)</pre></p>
 * <p>ClassName      : ContextBuilder</p>
 * @author lilinhai 2018年2月5日 上午9:37:35
 * @version 1.0
 */
public class ContextBuilder extends BaseConfigurationBuilder
{

    /**
     * 代码配置上下文
     */
    private Context context;

    /**
     * 
     * <p>Title        : ContextBuilder lilinhai 2018年2月5日 上午9:41:48</p>
     * @param id
     * @param mt
     */
    public ContextBuilder(String id, ModelType mt, TargetRuntime targetRuntime)
    {
        this(id, mt);
        targetRuntime(targetRuntime);
        addProperty("mergeable", "false");
    }
    
    /**
     * 
     * <p>Title        : ContextBuilder lilinhai 2018年2月5日 上午9:41:48</p>
     * @param id
     * @param mt
     */
    public ContextBuilder(String id, ModelType mt)
    {
        this.context = new Context(mt);
        this.context.setId(id);
        propertyHolder = context;
        
        // 格式化XML代码
        addProperty("xmlFormatter", DefaultXmlFormatter.class.getName());
        
        // 格式化java代码
        addProperty("javaFormatter", DefaultJavaFormatter.class.getName());
        
        // 生成的Java文件的编码
        addProperty("javaFileEncoding", "UTF-8");
        
        //是否覆盖mappers配置文件
        addProperty("mergeable", "false");
        
        // 自动识别数据库关键字，默认false，如果设置为true，根据SqlReservedWords中定义的关键字列表； 一般保留默认值，遇到数据库关键字（Java关键字），使用columnOverride覆盖
        addProperty("autoDelimitKeywords", "false");
    }
    
    public ContextBuilder introspectedColumnImpl(String introspectedColumnImpl)
    {
        context.setIntrospectedColumnImpl(introspectedColumnImpl);
        return this;
    }
    
    public ContextBuilder targetRuntime(TargetRuntime targetRuntime)
    {
        context.setTargetRuntime(targetRuntime.getValue());
        return this;
    }
    
    public ContextBuilder jdbcConnectionConfiguration(JDBCConnectionConfiguration jdbcConnectionConfiguration)
    {
        context.setJdbcConnectionConfiguration(jdbcConnectionConfiguration);
        return this;
    }
    
    public ContextBuilder javaModelGeneratorConfiguration(JavaModelGeneratorConfiguration javaModelGeneratorConfiguration)
    {
        context.setJavaModelGeneratorConfiguration(javaModelGeneratorConfiguration);
        return this;
    }
    
    public ContextBuilder commentGeneratorConfiguration(CommentGeneratorConfiguration commentGeneratorConfiguration)
    {
        context.setCommentGeneratorConfiguration(commentGeneratorConfiguration);
        return this;
    }
    
    public ContextBuilder sqlMapGeneratorConfiguration(SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration)
    {
        context.setSqlMapGeneratorConfiguration(sqlMapGeneratorConfiguration);
        return this;
    }
    
    public ContextBuilder javaClientGeneratorConfiguration(JavaClientGeneratorConfiguration javaClientGeneratorConfiguration)
    {
        context.setJavaClientGeneratorConfiguration(javaClientGeneratorConfiguration);
        return this;
    }
    
    public ContextBuilder addTableConfiguration(TableConfiguration tableConfiguration)
    {
        context.addTableConfiguration(tableConfiguration);
        return this;
    }
    
    public ContextBuilder addPluginConfiguration(PluginConfiguration pluginConfiguration)
    {
        context.addPluginConfiguration(pluginConfiguration);
        return this;
    }
    
    public Context build()
    {
        return context;
    }
}
