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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pers.linhai.nature.j2ee.core.web.exception.ControllerException;
import pers.linhai.nature.j2ee.core.web.interceptor.IEntityControllerInterceptor;
import pers.linhai.nature.j2ee.generator.core.api.CommentGenerator;
import pers.linhai.nature.j2ee.generator.core.api.CoreClassImportConstant;
import pers.linhai.nature.j2ee.generator.core.api.GeneratedJavaFile;
import pers.linhai.nature.j2ee.generator.core.api.IntrospectedTable;
import pers.linhai.nature.j2ee.generator.core.api.dom.DefaultJavaFormatter;
import pers.linhai.nature.j2ee.generator.core.api.dom.java.FullyQualifiedJavaType;
import pers.linhai.nature.j2ee.generator.core.api.dom.java.JavaVisibility;
import pers.linhai.nature.j2ee.generator.core.api.dom.java.Method;
import pers.linhai.nature.j2ee.generator.core.api.dom.java.Parameter;
import pers.linhai.nature.j2ee.generator.core.api.dom.java.TopLevelClass;
import pers.linhai.nature.j2ee.generator.utils.CodeCommentUtils;
import pers.linhai.nature.utils.NamingUtils;

/**
 * 控制层代码生成器
 * <p>ClassName      : ServicePlugin</p>
 * @author lilinhai 2018年2月4日 下午9:12:23
 * @version 1.0
 */
public class ControllerPlugin extends BasePlugin
{
    
    private boolean restApiDefaultEnabled;
    
    public void setProperties(Properties properties) 
    {
        super.setProperties(properties);
        moduleName = "web";
        artifactId = projectName + "-" + moduleName;
        restApiDefaultEnabled = Boolean.parseBoolean(properties.getProperty("restApiDefaultEnabled"));
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
        
        //创建实体对应的interceptor实现类
        javaFileList.add(createControllerDataInterceptor(introspectedTable));
        
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
        controllerClass.addImportedType(new FullyQualifiedJavaType(getTargetPackae("interceptor") + "." + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "ControllerInterceptor"));
        controllerClass.addImportedType(new FullyQualifiedJavaType(getTargetPackae("model", "query") + "." + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Query"));
        controllerClass.addImportedType(new FullyQualifiedJavaType(CoreClassImportConstant.BASE_ENTITY_CONTROLLER_CLASS));
        controllerClass.addImportedType(new FullyQualifiedJavaType(getTargetPackae("service", "interfaces") + ".I" + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Service"));
        
        // 添加范型继承关系BaseService
        String keyType = introspectedTable.getPrimaryKeyColumns().get(0).getFullyQualifiedJavaType().getShortName();
        controllerClass.setSuperClass(new FullyQualifiedJavaType("BaseEntityController<" + keyType + ", " + introspectedTable.getBaseRecordType() + ", "
            + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Query" + ", "
            + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "ControllerInterceptor" + ", I" 
            + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Service" + ">"));
        
        // 添加spring扫描注解
        controllerClass.addAnnotation("@RestController");
        controllerClass.addAnnotation("@RequestMapping(\"/" + NamingUtils.controllerMappingName(introspectedTable.getFullyQualifiedTable().getDomainObjectName()) + "\")");
        
        //添加注释
        CommentGenerator commentGenerator = context.getCommentGenerator();
        commentGenerator.addJavaFileComment(controllerClass);
        return new GeneratedJavaFile(controllerClass, getTargetProjectJavaSourceFolder(), "utf-8", new DefaultJavaFormatter());
    }
    
    
    /**
     * 创建实体对应的interceptor接口
     * <p>Title         : createServiceInterface lilinhai 2018年2月5日 下午2:36:13</p>
     * @param introspectedTable
     * @return 
     * GeneratedJavaFile 
     */
    private GeneratedJavaFile createControllerDataInterceptor(IntrospectedTable introspectedTable)
    {
        TopLevelClass interceptor = new TopLevelClass(getTargetPackae("interceptor") + "." + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "ControllerInterceptor");

        // 给interceptor实现类加注释
        CodeCommentUtils.addInterceptorClassComment(interceptor, introspectedTable);

        // 将接口访问权限设置为public
        interceptor.setVisibility(JavaVisibility.PUBLIC);

        // 添加需要依赖的类
        interceptor.addImportedType(new FullyQualifiedJavaType(Component.class.getName()));
        interceptor.addImportedType(new FullyQualifiedJavaType(HttpServletRequest.class.getName()));
        interceptor.addImportedType(new FullyQualifiedJavaType(HttpServletResponse.class.getName()));
        interceptor.addImportedType(new FullyQualifiedJavaType(ControllerException.class.getName()));
        interceptor.addImportedType(new FullyQualifiedJavaType(IEntityControllerInterceptor.class.getName()));
        interceptor.addImportedType(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));
        interceptor.addImportedType(new FullyQualifiedJavaType(getTargetPackae("model", "query") + "." + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Query"));

        // 添加spring扫描注解
        interceptor.addAnnotation("@Component");
        
        // 添加范型继承关系BaseService
        String keyType = introspectedTable.getPrimaryKeyColumns().get(0).getFullyQualifiedJavaType().getShortName();

        // 添加范型继承关系BaseService
        interceptor.addSuperInterface(new FullyQualifiedJavaType(IEntityControllerInterceptor.class.getName() + "<" + keyType + ", " + introspectedTable.getBaseRecordType()
            + ", " + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Query" + ">"));

        // process1添加数据前的校验方法
        Method processMethod1 = new Method("beforeSave");
        processMethod1.addJavaDocLine("/**");
        processMethod1.addJavaDocLine(" * [" + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "]上行数据save前的拦截处理，数据校验等操作处理");
        processMethod1.addJavaDocLine(" */");
        processMethod1.setFinal(false);
        processMethod1.setStatic(false);
        processMethod1.setVisibility(JavaVisibility.PUBLIC);
        processMethod1.addException(new FullyQualifiedJavaType(ControllerException.class.getName()));
        processMethod1.addParameter(new Parameter(new FullyQualifiedJavaType(HttpServletRequest.class.getName()), "request"));
        processMethod1.addParameter(new Parameter(new FullyQualifiedJavaType(HttpServletResponse.class.getName()), "response"));
        processMethod1.addParameter(new Parameter(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()), NamingUtils.variableName(introspectedTable.getFullyQualifiedTable().getDomainObjectName())));
        if (!restApiDefaultEnabled)
        {
            processMethod1.addBodyLine("throw new ControllerException(\"This api is not available.\");");
        }
        else
        {
            processMethod1.addBodyLine("");
        }
        interceptor.addMethod(processMethod1);
        
        processMethod1 = new Method("beforeDelete");
        processMethod1.addJavaDocLine("/**");
        processMethod1.addJavaDocLine(" * [" + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "]上行数据delete前的拦截处理，预防处理误删、搞乱等操作");
        processMethod1.addJavaDocLine(" */");
        processMethod1.setFinal(false);
        processMethod1.setStatic(false);
        processMethod1.setVisibility(JavaVisibility.PUBLIC);
        processMethod1.addException(new FullyQualifiedJavaType(ControllerException.class.getName()));
        processMethod1.addParameter(new Parameter(new FullyQualifiedJavaType(HttpServletRequest.class.getName()), "request"));
        processMethod1.addParameter(new Parameter(new FullyQualifiedJavaType(HttpServletResponse.class.getName()), "response"));
        processMethod1.addParameter(new Parameter(new FullyQualifiedJavaType(keyType), "id"));
        if (!restApiDefaultEnabled)
        {
            processMethod1.addBodyLine("throw new ControllerException(\"This api is not available.\");");
        }
        else
        {
            processMethod1.addBodyLine("");
        }
        interceptor.addMethod(processMethod1);
        
        processMethod1 = new Method("beforeUpdate");
        processMethod1.addJavaDocLine("/**");
        processMethod1.addJavaDocLine(" * [" + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "]上行数据update前的拦截处理，数据校验等操作处理");
        processMethod1.addJavaDocLine(" */");
        processMethod1.setFinal(false);
        processMethod1.setStatic(false);
        processMethod1.setVisibility(JavaVisibility.PUBLIC);
        processMethod1.addException(new FullyQualifiedJavaType(ControllerException.class.getName()));
        processMethod1.addParameter(new Parameter(new FullyQualifiedJavaType(HttpServletRequest.class.getName()), "request"));
        processMethod1.addParameter(new Parameter(new FullyQualifiedJavaType(HttpServletResponse.class.getName()), "response"));
        processMethod1.addParameter(new Parameter(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()), NamingUtils.variableName(introspectedTable.getFullyQualifiedTable().getDomainObjectName())));
        if (!restApiDefaultEnabled)
        {
            processMethod1.addBodyLine("throw new ControllerException(\"This api is not available.\");");
        }
        else
        {
            processMethod1.addBodyLine("");
        }
        interceptor.addMethod(processMethod1);
        
        processMethod1 = new Method("beforeGet");
        processMethod1.addJavaDocLine("/**");
        processMethod1.addJavaDocLine(" * [" + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "]下行数据根据ID进行get前的拦截处理，数据校验等操作处理");
        processMethod1.addJavaDocLine(" */");
        processMethod1.setFinal(false);
        processMethod1.setStatic(false);
        processMethod1.setVisibility(JavaVisibility.PUBLIC);
        processMethod1.addException(new FullyQualifiedJavaType(ControllerException.class.getName()));
        processMethod1.addParameter(new Parameter(new FullyQualifiedJavaType(HttpServletRequest.class.getName()), "request"));
        processMethod1.addParameter(new Parameter(new FullyQualifiedJavaType(HttpServletResponse.class.getName()), "response"));
        processMethod1.addParameter(new Parameter(new FullyQualifiedJavaType(keyType), "id"));
        if (!restApiDefaultEnabled)
        {
            processMethod1.addBodyLine("throw new ControllerException(\"This api is not available.\");");
        }
        else
        {
            processMethod1.addBodyLine("");
        }
        interceptor.addMethod(processMethod1);
        
        processMethod1 = new Method("beforeGet");
        processMethod1.addJavaDocLine("/**");
        processMethod1.addJavaDocLine(" * [" + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "]下行数据根据查询条件进行get前的拦截处理，数据校验等操作处理");
        processMethod1.addJavaDocLine(" */");
        processMethod1.setFinal(false);
        processMethod1.setStatic(false);
        processMethod1.setVisibility(JavaVisibility.PUBLIC);
        processMethod1.addException(new FullyQualifiedJavaType(ControllerException.class.getName()));
        processMethod1.addParameter(new Parameter(new FullyQualifiedJavaType(HttpServletRequest.class.getName()), "request"));
        processMethod1.addParameter(new Parameter(new FullyQualifiedJavaType(HttpServletResponse.class.getName()), "response"));
        processMethod1.addParameter(new Parameter(new FullyQualifiedJavaType(getTargetPackae("model", "query") + "." + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Query"), "query"));
        if (!restApiDefaultEnabled)
        {
            processMethod1.addBodyLine("throw new ControllerException(\"This api is not available.\");");
        }
        else
        {
            processMethod1.addBodyLine("");
        }
        interceptor.addMethod(processMethod1);
        
        processMethod1 = new Method("beforeFind");
        processMethod1.addJavaDocLine("/**");
        processMethod1.addJavaDocLine(" * [" + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "]下行数据根据查询条件进行find前的拦截处理，数据校验等操作处理");
        processMethod1.addJavaDocLine(" */");
        processMethod1.setFinal(false);
        processMethod1.setStatic(false);
        processMethod1.setVisibility(JavaVisibility.PUBLIC);
        processMethod1.addException(new FullyQualifiedJavaType(ControllerException.class.getName()));
        processMethod1.addParameter(new Parameter(new FullyQualifiedJavaType(HttpServletRequest.class.getName()), "request"));
        processMethod1.addParameter(new Parameter(new FullyQualifiedJavaType(HttpServletResponse.class.getName()), "response"));
        processMethod1.addParameter(new Parameter(new FullyQualifiedJavaType(getTargetPackae("model", "query") + "." + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Query"), "query"));
        if (!restApiDefaultEnabled)
        {
            processMethod1.addBodyLine("throw new ControllerException(\"This api is not available.\");");
        }
        else
        {
            processMethod1.addBodyLine("");
        }
        interceptor.addMethod(processMethod1);
        
        processMethod1 = new Method("beforeCount");
        processMethod1.addJavaDocLine("/**");
        processMethod1.addJavaDocLine(" * [" + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "]下行数据根据查询条件进行count前的拦截处理，数据校验等操作处理");
        processMethod1.addJavaDocLine(" */");
        processMethod1.setFinal(false);
        processMethod1.setStatic(false);
        processMethod1.setVisibility(JavaVisibility.PUBLIC);
        processMethod1.addException(new FullyQualifiedJavaType(ControllerException.class.getName()));
        processMethod1.addParameter(new Parameter(new FullyQualifiedJavaType(HttpServletRequest.class.getName()), "request"));
        processMethod1.addParameter(new Parameter(new FullyQualifiedJavaType(HttpServletResponse.class.getName()), "response"));
        processMethod1.addParameter(new Parameter(new FullyQualifiedJavaType(getTargetPackae("model", "query") + "." + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Query"), "query"));
        if (!restApiDefaultEnabled)
        {
            processMethod1.addBodyLine("throw new ControllerException(\"This api is not available.\");");
        }
        else
        {
            processMethod1.addBodyLine("");
        }
        interceptor.addMethod(processMethod1);
        
        // 添加注释
        CommentGenerator commentGenerator = context.getCommentGenerator();
        commentGenerator.addJavaFileComment(interceptor);
        return new GeneratedJavaFile(interceptor, getTargetProjectJavaSourceFolder(), "utf-8", new DefaultJavaFormatter());
    }
    
}
