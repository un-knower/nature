/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : ServicePlugin.java</p>
 * <p>Package     : codegenerator.plugins</p>
 * @Creator lilinhai 2018年2月4日 下午9:12:23
 * @Version  V1.0  
 */ 

package pers.linhai.nature.j2ee.generator.plugins;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pers.linhai.nature.j2ee.core.controller.BaseEntityController;
import pers.linhai.nature.j2ee.generator.core.api.CommentGenerator;
import pers.linhai.nature.j2ee.generator.core.api.GeneratedJavaFile;
import pers.linhai.nature.j2ee.generator.core.api.IntrospectedTable;
import pers.linhai.nature.j2ee.generator.core.api.dom.DefaultJavaFormatter;
import pers.linhai.nature.j2ee.generator.core.api.dom.java.FullyQualifiedJavaType;
import pers.linhai.nature.j2ee.generator.core.api.dom.java.JavaVisibility;
import pers.linhai.nature.j2ee.generator.core.api.dom.java.TopLevelClass;
import pers.linhai.nature.j2ee.generator.utils.CodeCommentUtils;
import pers.linhai.nature.j2ee.generator.utils.NamingUtils;

/**
 * 控制层代码生成器
 * <p>ClassName      : ServicePlugin</p>
 * @author lilinhai 2018年2月4日 下午9:12:23
 * @version 1.0
 */
public class ControllerPlugin extends BasePlugin
{
    
    public void setProperties(Properties properties) 
    {
        super.setProperties(properties);
        moduleName = "web";
        artifactId = projectName + "-" + moduleName;
    }
    
    /** 
     * <p>Overriding Method: lilinhai 2018年2月4日 下午9:28:33</p>
     * <p>Title: contextGenerateAdditionalJavaFiles</p>
     * @param introspectedTable
     * @return 
     * @see pers.linhai.nature.j2ee.generator.core.api.PluginAdapter#contextGenerateAdditionalJavaFiles(pers.linhai.nature.j2ee.generator.core.api.IntrospectedTable)
     */ 
    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable)
    {
        List<GeneratedJavaFile> javaFileList = new ArrayList<GeneratedJavaFile>();
        
        // 创建实体对应的Controller实现类
        javaFileList.add(createController(introspectedTable));
        return javaFileList;
    }

    /**
     * 创建实体对应的service接口
     * <p>Title         : createServiceInterface lilinhai 2018年2月5日 下午2:36:13</p>
     * @param introspectedTable
     * @return 
     * GeneratedJavaFile 
     */ 
    private GeneratedJavaFile createController(IntrospectedTable introspectedTable)
    {
        TopLevelClass controllerClass = new TopLevelClass(getTargetPackae("controller") + "." + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Controller");
        
        // 给控制层类加注释
        CodeCommentUtils.addServiceClassComment(controllerClass, introspectedTable);
        
        // 将接口访问权限设置为public
        controllerClass.setVisibility(JavaVisibility.PUBLIC);
        
        // 添加需要依赖的类
        controllerClass.addImportedType(new FullyQualifiedJavaType(RestController.class.getName()));
        controllerClass.addImportedType(new FullyQualifiedJavaType(RequestMapping.class.getName()));
        controllerClass.addImportedType(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));
        controllerClass.addImportedType(new FullyQualifiedJavaType(getTargetPackae("model", "query") + "." + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Query"));
        controllerClass.addImportedType(new FullyQualifiedJavaType(BaseEntityController.class.getName()));
        controllerClass.addImportedType(new FullyQualifiedJavaType(getTargetPackae("service", "interfaces") + ".I" + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Service"));
        
        // 添加范型继承关系BaseService
        String keyType = introspectedTable.getPrimaryKeyColumns().get(0).getFullyQualifiedJavaType().getShortName();
        controllerClass.setSuperClass(new FullyQualifiedJavaType("BaseEntityController<" + keyType + ", " + introspectedTable.getBaseRecordType() + ", "
            + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Query" + ", I" 
            + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Service" + ">"));
        
        // 添加spring扫描注解
        controllerClass.addAnnotation("@RestController");
        controllerClass.addAnnotation("@RequestMapping(\"/" + NamingUtils.controllerName(introspectedTable.getFullyQualifiedTable().getDomainObjectName()) + "\")");
        
        //添加注释
        CommentGenerator commentGenerator = context.getCommentGenerator();
        commentGenerator.addJavaFileComment(controllerClass);
        return new GeneratedJavaFile(controllerClass, getTargetProjectJavaSourceFolder(), "utf-8", new DefaultJavaFormatter());
    }
    
}
