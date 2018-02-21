/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : SerializablePlugin.java</p>
 * <p>Package     : codegenerator.plugins</p>
 * @Creator lilinhai 2018年2月4日 下午2:41:22
 * @Version  V1.0  
 */ 

package pers.linhai.nature.j2ee.generator.plugins;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import pers.linhai.nature.j2ee.generator.core.api.IntrospectedTable;
import pers.linhai.nature.j2ee.generator.core.api.PluginAdapter;
import pers.linhai.nature.j2ee.generator.core.api.dom.java.Field;
import pers.linhai.nature.j2ee.generator.core.api.dom.java.FullyQualifiedJavaType;
import pers.linhai.nature.j2ee.generator.core.api.dom.java.JavaVisibility;
import pers.linhai.nature.j2ee.generator.core.api.dom.java.TopLevelClass;

/**
 * <p>Description    : <pre>TODO(这里用一句话描述这个类的作用)</pre></p>
 * <p>ClassName      : SerializablePlugin</p>
 * @author lilinhai 2018年2月4日 下午2:41:22
 * @version 1.0
 */
public class SerializablePlugin extends PluginAdapter {

    private FullyQualifiedJavaType serializable;
    private FullyQualifiedJavaType gwtSerializable;
    private boolean addGWTInterface;
    private boolean suppressJavaInterface;
    
    private boolean serialVersionUID;

    public SerializablePlugin() {
        super();
        serializable = new FullyQualifiedJavaType("java.io.Serializable"); //$NON-NLS-1$
        gwtSerializable = new FullyQualifiedJavaType("com.google.gwt.user.client.rpc.IsSerializable"); //$NON-NLS-1$
    }

    @Override
    public boolean validate(List<String> warnings) {
        // this plugin is always valid
        return true;
    }

    @Override
    public void setProperties(Properties properties) {
        super.setProperties(properties);
        addGWTInterface = Boolean.valueOf(properties.getProperty("addGWTInterface")); //$NON-NLS-1$
        suppressJavaInterface = Boolean.valueOf(properties.getProperty("suppressJavaInterface")); //$NON-NLS-1$
        serialVersionUID = Boolean.valueOf(properties.getProperty("serialVersionUID")); //$NON-NLS-1$
    }

    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass,
            IntrospectedTable introspectedTable) {
        makeSerializable(topLevelClass, introspectedTable);
        return true;
    }

    @Override
    public boolean modelPrimaryKeyClassGenerated(TopLevelClass topLevelClass,
            IntrospectedTable introspectedTable) {
        makeSerializable(topLevelClass, introspectedTable);
        return true;
    }

    @Override
    public boolean modelRecordWithBLOBsClassGenerated(
            TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        makeSerializable(topLevelClass, introspectedTable);
        return true;
    }

    protected void makeSerializable(TopLevelClass topLevelClass,
            IntrospectedTable introspectedTable) {
        if (addGWTInterface) {
            topLevelClass.addImportedType(gwtSerializable);
            topLevelClass.addSuperInterface(gwtSerializable);
        }

        if (suppressJavaInterface) {
            topLevelClass.addImportedType(serializable);
            topLevelClass.addSuperInterface(serializable);
        }
        
        if (serialVersionUID) {
            Field field = new Field();
            field.setFinal(true);
            field.setInitializationString("1L"); //$NON-NLS-1$
            field.setName("serialVersionUID"); //$NON-NLS-1$
            field.setStatic(true);
            field.setType(new FullyQualifiedJavaType("long")); //$NON-NLS-1$
            field.setVisibility(JavaVisibility.PRIVATE);
            field.addJavaDocLine("/**"); //$NON-NLS-1$
            field.addJavaDocLine(" * long serialVersionUID 生成时间：" + (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()));
            field.addJavaDocLine(" */"); //$NON-NLS-1$
            topLevelClass.addField(field);
        }
    }
}

