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

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pers.linhai.nature.j2ee.core.web.model.RestResponse;
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
import pers.linhai.nature.j2ee.generator.utils.GeneratorNamerUtils;
import pers.linhai.nature.utils.NamerUtils;

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
        controllerClass.addImportedType(new FullyQualifiedJavaType(RequestBody.class.getName()));
        controllerClass.addImportedType(new FullyQualifiedJavaType(PathVariable.class.getName()));
        controllerClass.addImportedType(new FullyQualifiedJavaType(HttpServletRequest.class.getName()));
        controllerClass.addImportedType(new FullyQualifiedJavaType(HttpServletResponse.class.getName()));
        controllerClass.addImportedType(new FullyQualifiedJavaType(RestResponse.class.getName()));
        controllerClass.addImportedType(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));
        controllerClass.addImportedType(new FullyQualifiedJavaType(getTargetPackae("model", "query") + "." + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Query"));
        controllerClass.addImportedType(new FullyQualifiedJavaType(CoreClassImportConstant.BASE_ENTITY_CONTROLLER_CLASS));
        controllerClass.addImportedType(new FullyQualifiedJavaType(getTargetPackae("service", "interfaces") + ".I" + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Service"));
        
        if (restApiDefaultEnabled)
        {
            controllerClass.addImportedType(new FullyQualifiedJavaType(RequestMapping.class.getName()));
            controllerClass.addImportedType(new FullyQualifiedJavaType(PostMapping.class.getName()));
            controllerClass.addImportedType(new FullyQualifiedJavaType(DeleteMapping.class.getName()));
            controllerClass.addImportedType(new FullyQualifiedJavaType(PutMapping.class.getName()));
            controllerClass.addImportedType(new FullyQualifiedJavaType(GetMapping.class.getName()));
        }
        
        // 添加范型继承关系BaseService
        String keyType = introspectedTable.getPrimaryKeyColumns().get(0).getFullyQualifiedJavaType().getShortName();
        controllerClass.setSuperClass(new FullyQualifiedJavaType("BaseEntityController<" + keyType + ", " + introspectedTable.getBaseRecordType() + ", "
            + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Query" + ", I" 
            + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Service" + ">"));
        
        // 添加spring扫描注解
        controllerClass.addAnnotation("@RestController");
        if (restApiDefaultEnabled)
        {
            controllerClass.addAnnotation("@RequestMapping(\"/" + GeneratorNamerUtils.controllerMappingName(introspectedTable.getFullyQualifiedTable().getDomainObjectName()) + "\")");
        }
        else
        {
            controllerClass.addJavaDocLine("//@RequestMapping(\"/" + GeneratorNamerUtils.controllerMappingName(introspectedTable.getFullyQualifiedTable().getDomainObjectName()) + "\")");
        }
        
        //添加注释
        CommentGenerator commentGenerator = context.getCommentGenerator();
        commentGenerator.addJavaFileComment(controllerClass);
        
        // process1添加数据前的校验方法
        Method processMethod1 = new Method("save");
        processMethod1.addJavaDocLine("/**");
        processMethod1.addJavaDocLine(" * 创建一个实体[" + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "]");
        processMethod1.addJavaDocLine(" */");
        processMethod1.setFinal(false);
        processMethod1.setStatic(false);
        processMethod1.setVisibility(JavaVisibility.PUBLIC);
        processMethod1.setReturnType(new FullyQualifiedJavaType(RestResponse.class.getSimpleName()));
        processMethod1.addParameter(new Parameter(new FullyQualifiedJavaType(HttpServletRequest.class.getName()), "request"));
        processMethod1.addParameter(new Parameter(new FullyQualifiedJavaType(HttpServletResponse.class.getName()), "response"));
        Parameter entityParam = new Parameter(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()), NamerUtils.classToProperty(introspectedTable.getFullyQualifiedTable().getDomainObjectName()));
        entityParam.addAnnotation("@RequestBody");
        processMethod1.addParameter(entityParam);
        if (restApiDefaultEnabled)
        {
            processMethod1.addAnnotation("@PostMapping(\"\")");
        }
        else
        {
            processMethod1.addJavaDocLine("//@PostMapping(\"\")");
        }
        processMethod1.addBodyLine("return super.save("+NamerUtils.classToProperty(introspectedTable.getFullyQualifiedTable().getDomainObjectName())+");");
        controllerClass.addMethod(processMethod1);
        
        processMethod1 = new Method("delete");
        processMethod1.addJavaDocLine("/**");
        processMethod1.addJavaDocLine(" * 根据主键删除一个实体[" + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "]");
        processMethod1.addJavaDocLine(" */");
        processMethod1.setFinal(false);
        processMethod1.setStatic(false);
        processMethod1.setVisibility(JavaVisibility.PUBLIC);
        processMethod1.setReturnType(new FullyQualifiedJavaType(RestResponse.class.getSimpleName()));
        processMethod1.addParameter(new Parameter(new FullyQualifiedJavaType(HttpServletRequest.class.getName()), "request"));
        processMethod1.addParameter(new Parameter(new FullyQualifiedJavaType(HttpServletResponse.class.getName()), "response"));
        Parameter idParam = new Parameter(new FullyQualifiedJavaType(keyType), "id");
        idParam.addAnnotation("@PathVariable");
        processMethod1.addParameter(idParam);
        if (restApiDefaultEnabled)
        {
            processMethod1.addAnnotation("@DeleteMapping(\"/{id}\")");
        }
        else
        {
            processMethod1.addJavaDocLine("//@DeleteMapping(\"/{id}\")");
        }
        processMethod1.addBodyLine("return super.delete(id);");
        controllerClass.addMethod(processMethod1);
        
        processMethod1 = new Method("update");
        processMethod1.addJavaDocLine("/**");
        processMethod1.addJavaDocLine(" * 根据主键修改实体属性[" + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "]");
        processMethod1.addJavaDocLine(" */");
        processMethod1.setFinal(false);
        processMethod1.setStatic(false);
        processMethod1.setVisibility(JavaVisibility.PUBLIC);
        processMethod1.setReturnType(new FullyQualifiedJavaType(RestResponse.class.getSimpleName()));
        processMethod1.addParameter(new Parameter(new FullyQualifiedJavaType(HttpServletRequest.class.getName()), "request"));
        processMethod1.addParameter(new Parameter(new FullyQualifiedJavaType(HttpServletResponse.class.getName()), "response"));
        processMethod1.addParameter(entityParam);
        idParam = new Parameter(new FullyQualifiedJavaType(keyType), "id");
        idParam.addAnnotation("@PathVariable");
        processMethod1.addParameter(idParam);
        if (restApiDefaultEnabled)
        {
            processMethod1.addAnnotation("@PutMapping(\"/{id}\")");
        }
        else
        {
            processMethod1.addJavaDocLine("//@PutMapping(\"/id\")");
        }
        processMethod1.addBodyLine("return super.update("+NamerUtils.classToProperty(introspectedTable.getFullyQualifiedTable().getDomainObjectName())+", id);");
        controllerClass.addMethod(processMethod1);
        
        processMethod1 = new Method("get");
        processMethod1.addJavaDocLine("/**");
        processMethod1.addJavaDocLine(" * 根据主键查找单个记录[" + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "]");
        processMethod1.addJavaDocLine(" */");
        processMethod1.setFinal(false);
        processMethod1.setStatic(false);
        processMethod1.setVisibility(JavaVisibility.PUBLIC);
        processMethod1.setReturnType(new FullyQualifiedJavaType(RestResponse.class.getSimpleName()));
        processMethod1.addParameter(new Parameter(new FullyQualifiedJavaType(HttpServletRequest.class.getName()), "request"));
        processMethod1.addParameter(new Parameter(new FullyQualifiedJavaType(HttpServletResponse.class.getName()), "response"));
        processMethod1.addParameter(idParam);
        if (restApiDefaultEnabled)
        {
            processMethod1.addAnnotation("@GetMapping(\"/{id}\")");
        }
        else
        {
            processMethod1.addJavaDocLine("//@GetMapping(\"/id\")");
        }
        processMethod1.addBodyLine("return super.get(id);");
        controllerClass.addMethod(processMethod1);
        
        processMethod1 = new Method("get");
        processMethod1.addJavaDocLine("/**");
        processMethod1.addJavaDocLine(" * 根据查询条件查找单个记录[" + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "]");
        processMethod1.addJavaDocLine(" */");
        processMethod1.setFinal(false);
        processMethod1.setStatic(false);
        processMethod1.setVisibility(JavaVisibility.PUBLIC);
        processMethod1.setReturnType(new FullyQualifiedJavaType(RestResponse.class.getSimpleName()));
        processMethod1.addParameter(new Parameter(new FullyQualifiedJavaType(HttpServletRequest.class.getName()), "request"));
        processMethod1.addParameter(new Parameter(new FullyQualifiedJavaType(HttpServletResponse.class.getName()), "response"));
        Parameter queryParam = new Parameter(new FullyQualifiedJavaType(getTargetPackae("model", "query") + "." + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Query"), "query");
        queryParam.addAnnotation("@RequestBody");
        processMethod1.addParameter(queryParam);
        if (restApiDefaultEnabled)
        {
            processMethod1.addAnnotation("@PostMapping(\"/get\")");
        }
        else
        {
            processMethod1.addJavaDocLine("//@PostMapping(\"/get\")");
        }
        processMethod1.addBodyLine("return super.get(query);");
        controllerClass.addMethod(processMethod1);
        
        processMethod1 = new Method("find");
        processMethod1.addJavaDocLine("/**");
        processMethod1.addJavaDocLine(" * 根据查询条件返回多个 实体，支持分页，若同时传了分页参数page和size，则执行分页查询，否则按默认最大返回1000条执行[" + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "]");
        processMethod1.addJavaDocLine(" */");
        processMethod1.setFinal(false);
        processMethod1.setStatic(false);
        processMethod1.setVisibility(JavaVisibility.PUBLIC);
        processMethod1.setReturnType(new FullyQualifiedJavaType(RestResponse.class.getSimpleName()));
        processMethod1.addParameter(new Parameter(new FullyQualifiedJavaType(HttpServletRequest.class.getName()), "request"));
        processMethod1.addParameter(new Parameter(new FullyQualifiedJavaType(HttpServletResponse.class.getName()), "response"));
        processMethod1.addParameter(queryParam);
        if (restApiDefaultEnabled)
        {
            processMethod1.addAnnotation("@PostMapping(\"/find\")");
        }
        else 
        {
            processMethod1.addJavaDocLine("//@PostMapping(\"/find\")");
        }
        processMethod1.addBodyLine("return super.find(query);");
        controllerClass.addMethod(processMethod1);
        
        processMethod1 = new Method("count");
        processMethod1.addJavaDocLine("/**");
        processMethod1.addJavaDocLine(" * 根据查询条件统计满足条件的实体个数[" + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "]理");
        processMethod1.addJavaDocLine(" */");
        processMethod1.setFinal(false);
        processMethod1.setStatic(false);
        processMethod1.setVisibility(JavaVisibility.PUBLIC);
        processMethod1.setReturnType(new FullyQualifiedJavaType(RestResponse.class.getSimpleName()));
        processMethod1.addParameter(new Parameter(new FullyQualifiedJavaType(HttpServletRequest.class.getName()), "request"));
        processMethod1.addParameter(new Parameter(new FullyQualifiedJavaType(HttpServletResponse.class.getName()), "response"));
        processMethod1.addParameter(queryParam);
        if (restApiDefaultEnabled)
        {
            processMethod1.addAnnotation("@PostMapping(\"/count\")");
        }
        else
        {
            processMethod1.addJavaDocLine("//@PostMapping(\"/count\")");
        }
        processMethod1.addBodyLine("return super.count(query);");
        controllerClass.addMethod(processMethod1);
        
        return new GeneratedJavaFile(controllerClass, getTargetProjectJavaSourceFolder(), "utf-8", new DefaultJavaFormatter());
    }
}
