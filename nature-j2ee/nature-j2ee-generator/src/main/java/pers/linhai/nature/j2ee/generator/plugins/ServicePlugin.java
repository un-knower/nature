/*
 * <p>Copyright : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p> <p>FileName : ServicePlugin.java</p>
 * <p>Package : codegenerator.plugins</p>
 * @Creator lilinhai 2018年2月4日 下午9:12:23
 * @Version V1.0
 */

package pers.linhai.nature.j2ee.generator.plugins;


import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import pers.linhai.nature.j2ee.core.model.EntityBean;
import pers.linhai.nature.j2ee.core.service.exception.EntityDeleteInterceptProcessException;
import pers.linhai.nature.j2ee.core.service.exception.EntitySaveInterceptProcessException;
import pers.linhai.nature.j2ee.core.service.exception.EntityUpdateInterceptProcessException;
import pers.linhai.nature.j2ee.core.service.interceptor.EntityServiceInterceptorImpl;
import pers.linhai.nature.j2ee.generator.core.api.CommentGenerator;
import pers.linhai.nature.j2ee.generator.core.api.CoreClassImportConstant;
import pers.linhai.nature.j2ee.generator.core.api.GeneratedJavaFile;
import pers.linhai.nature.j2ee.generator.core.api.IntrospectedTable;
import pers.linhai.nature.j2ee.generator.core.api.dom.DefaultJavaFormatter;
import pers.linhai.nature.j2ee.generator.core.api.dom.java.FullyQualifiedJavaType;
import pers.linhai.nature.j2ee.generator.core.api.dom.java.Interface;
import pers.linhai.nature.j2ee.generator.core.api.dom.java.JavaVisibility;
import pers.linhai.nature.j2ee.generator.core.api.dom.java.Method;
import pers.linhai.nature.j2ee.generator.core.api.dom.java.Parameter;
import pers.linhai.nature.j2ee.generator.core.api.dom.java.TopLevelClass;
import pers.linhai.nature.j2ee.generator.utils.CodeCommentUtils;
import pers.linhai.nature.utils.NamerUtils;


/**
 * <p>Description    : <pre>TODO(这里用一句话描述这个类的作用)</pre></p>
 * <p>ClassName      : ServicePlugin</p>
 * @author lilinhai 2018年2月4日 下午9:12:23
 * @version 1.0
 */
public class ServicePlugin extends BasePlugin
{

    public void setProperties(Properties properties)
    {
        super.setProperties(properties);
        moduleName = "service";
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

        // 创建实体对应的service接口
        javaFileList.add(createServiceInterface(introspectedTable));

        //创建实体对应的interceptor实现类
        javaFileList.add(createServiceDataInterceptor(introspectedTable));
        
        // 创建实体对应的service实现类
        javaFileList.add(createServiceImpl(introspectedTable));
        return javaFileList;
    }

    /**
     * 创建实体对应的service接口
     * <p>Title         : createServiceInterface lilinhai 2018年2月5日 下午2:36:13</p>
     * @param introspectedTable
     * @return 
     * GeneratedJavaFile 
     */
    private GeneratedJavaFile createServiceImpl(IntrospectedTable introspectedTable)
    {
        TopLevelClass serviceImpl = new TopLevelClass(getTargetPackae("impls") + "." + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "ServiceImpl");

        // 给service实现类加注释
        CodeCommentUtils.addServiceClassComment(serviceImpl, introspectedTable);

        // 将接口访问权限设置为public
        serviceImpl.setVisibility(JavaVisibility.PUBLIC);

        // 添加需要依赖的类
        serviceImpl.addImportedType(new FullyQualifiedJavaType(Service.class.getName()));
        serviceImpl.addImportedType(new FullyQualifiedJavaType(getTargetPackae("interceptor") + "." + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "ServiceInterceptor"));
        serviceImpl.addImportedType(new FullyQualifiedJavaType(getTargetPackae("interfaces") + ".I" + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Service"));
        serviceImpl.addImportedType(new FullyQualifiedJavaType(CoreClassImportConstant.BASE_ENTITY_SERVICE_IMPL_CLASS));
        serviceImpl.addImportedType(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));
        serviceImpl.addImportedType(new FullyQualifiedJavaType(getTargetPackae("dao", "mapper") + ".I" + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Mapper"));
        serviceImpl.addImportedType(new FullyQualifiedJavaType(getTargetPackae("model", "query") + "." + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Query"));

        // 添加spring扫描注解
        serviceImpl.addAnnotation("@Service");

        // 添加范型继承关系BaseService
        String keyType = introspectedTable.getPrimaryKeyColumns().get(0).getFullyQualifiedJavaType().getShortName();
        serviceImpl.setSuperClass(
            new FullyQualifiedJavaType("BaseEntityServiceImpl<" + keyType 
                + ", " + introspectedTable.getBaseRecordType() 
                + ", " + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Query" 
                + ", I" + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Mapper"
                + ", " + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "ServiceInterceptor" + ">"));
        serviceImpl.addSuperInterface(new FullyQualifiedJavaType(getTargetPackae("interfaces") + ".I" + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Service"));

        // 添加注释
        CommentGenerator commentGenerator = context.getCommentGenerator();
        commentGenerator.addJavaFileComment(serviceImpl);
        return new GeneratedJavaFile(serviceImpl, getTargetProjectJavaSourceFolder(), "utf-8", new DefaultJavaFormatter());
    }

    /**
     * 创建实体对应的interceptor接口
     * <p>Title         : createServiceInterface lilinhai 2018年2月5日 下午2:36:13</p>
     * @param introspectedTable
     * @return 
     * GeneratedJavaFile 
     */
    private GeneratedJavaFile createServiceDataInterceptor(IntrospectedTable introspectedTable)
    {
        TopLevelClass interceptor = new TopLevelClass(getTargetPackae("interceptor") + "." + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "ServiceInterceptor");

        // 给interceptor实现类加注释
        CodeCommentUtils.addInterceptorClassComment(interceptor, introspectedTable);

        // 将接口访问权限设置为public
        interceptor.setVisibility(JavaVisibility.PUBLIC);

        // 添加需要依赖的类
        interceptor.addImportedType(new FullyQualifiedJavaType(Component.class.getName()));
        interceptor.addImportedType(new FullyQualifiedJavaType(List.class.getName()));
        interceptor.addImportedType(new FullyQualifiedJavaType(EntitySaveInterceptProcessException.class.getName()));
        interceptor.addImportedType(new FullyQualifiedJavaType(EntityUpdateInterceptProcessException.class.getName()));
        interceptor.addImportedType(new FullyQualifiedJavaType(EntityDeleteInterceptProcessException.class.getName()));
        interceptor.addImportedType(new FullyQualifiedJavaType(EntityServiceInterceptorImpl.class.getName()));
        interceptor.addImportedType(new FullyQualifiedJavaType(EntityBean.class.getName()));
        interceptor.addImportedType(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));
        interceptor.addImportedType(new FullyQualifiedJavaType(getTargetPackae("dao", "mapper") + ".I" + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Mapper"));
        interceptor.addImportedType(new FullyQualifiedJavaType(getTargetPackae("model", "query") + "." + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Query"));

        // 添加spring扫描注解
        interceptor.addAnnotation("@Component");
        
        // 添加范型继承关系BaseService
        String keyType = introspectedTable.getPrimaryKeyColumns().get(0).getFullyQualifiedJavaType().getShortName();

        // 添加范型继承关系BaseService
        interceptor.setSuperClass(new FullyQualifiedJavaType(EntityServiceInterceptorImpl.class.getName() + "<" + keyType + ", " + introspectedTable.getBaseRecordType() 
        + ", " + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Query" 
        + ", I" + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Mapper"
        + ">"));

        // process1添加数据前的校验方法
        Method processMethod1 = new Method("beforeSave");
        processMethod1.addJavaDocLine("/**");
        processMethod1.addJavaDocLine(" * [" + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "]上行数据save前的拦截处理，数据校验等操作处理");
        processMethod1.addJavaDocLine(" */");
        processMethod1.setFinal(false);
        processMethod1.setStatic(false);
        processMethod1.setVisibility(JavaVisibility.PUBLIC);
        processMethod1.addException(new FullyQualifiedJavaType(EntitySaveInterceptProcessException.class.getName()));
        processMethod1.addParameter(new Parameter(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()), NamerUtils.classToProperty(introspectedTable.getFullyQualifiedTable().getDomainObjectName())));
        processMethod1.addBodyLine("");
        interceptor.addMethod(processMethod1);
        
        // process1添加数据前的校验方法
        Method afterMethod = new Method("afterSave");
        afterMethod.addJavaDocLine("/**");
        afterMethod.addJavaDocLine(" * [" + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "]上行数据save后的拦截处理");
        afterMethod.addJavaDocLine(" */");
        afterMethod.setFinal(false);
        afterMethod.setStatic(false);
        afterMethod.setVisibility(JavaVisibility.PUBLIC);
        afterMethod.addException(new FullyQualifiedJavaType(EntitySaveInterceptProcessException.class.getName()));
        afterMethod.addParameter(new Parameter(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()), NamerUtils.classToProperty(introspectedTable.getFullyQualifiedTable().getDomainObjectName())));
        afterMethod.addBodyLine("");
        interceptor.addMethod(afterMethod);
        
        // beforeDelete添加数据前的校验方法
        Method beforeDelete = new Method("beforeDelete");
        beforeDelete.addJavaDocLine("/**");
        beforeDelete.addJavaDocLine(" * [" + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "]上行数据delete前的拦截处理，预防处理误删、搞乱等操作");
        beforeDelete.addJavaDocLine(" */");
        beforeDelete.setFinal(false);
        beforeDelete.setStatic(false);
        beforeDelete.setVisibility(JavaVisibility.PUBLIC);
        beforeDelete.addException(new FullyQualifiedJavaType(EntityDeleteInterceptProcessException.class.getName()));
        beforeDelete.addParameter(new Parameter(new FullyQualifiedJavaType(keyType), "id"));
        beforeDelete.addBodyLine("");
        interceptor.addMethod(beforeDelete);
        
        // afterDelete添加数据前的校验方法
        Method afterDelete = new Method("afterDelete");
        afterDelete.addJavaDocLine("/**");
        afterDelete.addJavaDocLine(" * [" + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "]上行数据delete后的拦截处理");
        afterDelete.addJavaDocLine(" */");
        afterDelete.setFinal(false);
        afterDelete.setStatic(false);
        afterDelete.setVisibility(JavaVisibility.PUBLIC);
        afterDelete.addException(new FullyQualifiedJavaType(EntityDeleteInterceptProcessException.class.getName()));
        afterDelete.addParameter(new Parameter(new FullyQualifiedJavaType(keyType), "id"));
        afterDelete.addBodyLine("");
        interceptor.addMethod(afterDelete);
        
        // process 修改数据钱的校验处理方法
        Method processMethod = new Method("beforeUpdate");
        processMethod.addJavaDocLine("/**");
        processMethod.addJavaDocLine(" * [" + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "]上行数据update前的拦截处理，数据校验等操作处理");
        processMethod.addJavaDocLine(" */");
        processMethod.setFinal(false);
        processMethod.setStatic(false);
        processMethod.setVisibility(JavaVisibility.PUBLIC);
        processMethod.addException(new FullyQualifiedJavaType(EntityUpdateInterceptProcessException.class.getName()));
        processMethod.addParameter(new Parameter(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()), NamerUtils.classToProperty(introspectedTable.getFullyQualifiedTable().getDomainObjectName())));
        processMethod.addBodyLine("");
        interceptor.addMethod(processMethod);
        
        // process 修改数据钱的校验处理方法
        Method afterUpdateMethod = new Method("afterUpdate");
        afterUpdateMethod.addJavaDocLine("/**");
        afterUpdateMethod.addJavaDocLine(" * [" + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "]上行数据update后的拦截处理");
        afterUpdateMethod.addJavaDocLine(" */");
        afterUpdateMethod.setFinal(false);
        afterUpdateMethod.setStatic(false);
        afterUpdateMethod.setVisibility(JavaVisibility.PUBLIC);
        afterUpdateMethod.addException(new FullyQualifiedJavaType(EntityUpdateInterceptProcessException.class.getName()));
        afterUpdateMethod.addParameter(new Parameter(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()), NamerUtils.classToProperty(introspectedTable.getFullyQualifiedTable().getDomainObjectName())));
        afterUpdateMethod.addBodyLine("");
        interceptor.addMethod(afterUpdateMethod);
        
        // process2方法
        Method processMethod2 = new Method("beforeReturn");
        processMethod2.addJavaDocLine("/**");
        processMethod2.addJavaDocLine(" * [" + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "]下行数据返回拦截处理");
        processMethod2.addJavaDocLine(" */");
        processMethod2.setFinal(false);
        processMethod2.setStatic(false);
        processMethod2.setVisibility(JavaVisibility.PUBLIC);
        processMethod2.addParameter(new Parameter(new FullyQualifiedJavaType(EntityBean.class.getName()), "entityBean"));
        processMethod2.addParameter(new Parameter(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()), NamerUtils.classToProperty(introspectedTable.getFullyQualifiedTable().getDomainObjectName())));
        processMethod2.addBodyLine("");
        interceptor.addMethod(processMethod2);
        
        // process2方法
        Method beforeReturn = new Method("beforeReturn");
        beforeReturn.addJavaDocLine("/**");
        beforeReturn.addJavaDocLine(" * [" + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "]下行数据返回批量处理");
        beforeReturn.addJavaDocLine(" */");
        beforeReturn.setFinal(false);
        beforeReturn.setStatic(false);
        beforeReturn.setVisibility(JavaVisibility.PUBLIC);
        beforeReturn.addParameter(new Parameter(new FullyQualifiedJavaType("List<" + EntityBean.class.getName() + ">"), "entityBeanList"));
        beforeReturn.addParameter(new Parameter(new FullyQualifiedJavaType("List<" + introspectedTable.getBaseRecordType() + ">"), NamerUtils.classToProperty(introspectedTable.getFullyQualifiedTable().getDomainObjectName()) + "List"));
        beforeReturn.addBodyLine("");
        interceptor.addMethod(beforeReturn);
        
        // 添加注释
        CommentGenerator commentGenerator = context.getCommentGenerator();
        commentGenerator.addJavaFileComment(interceptor);
        return new GeneratedJavaFile(interceptor, getTargetProjectJavaSourceFolder(), "utf-8", new DefaultJavaFormatter());
    }

    /**
     * 创建实体对应的service接口
     * <p>Title         : createServiceInterface lilinhai 2018年2月5日 下午2:36:13</p>
     * @param introspectedTable
     * @return 
     * GeneratedJavaFile 
     */
    private GeneratedJavaFile createServiceInterface(IntrospectedTable introspectedTable)
    {
        Interface serviceInterface = new Interface(getTargetPackae("interfaces") + ".I" + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Service");

        // 给service接口新增注释
        CodeCommentUtils.addServiceInterfaceComment(serviceInterface, introspectedTable);

        // 将接口访问权限设置为public
        serviceInterface.setVisibility(JavaVisibility.PUBLIC);

        // 添加需要依赖的类
        serviceInterface.addImportedType(new FullyQualifiedJavaType(CoreClassImportConstant.IBASE_ENTITY_SERVICE_CLASS));
        serviceInterface.addImportedType(new FullyQualifiedJavaType(getTargetPackae("model", "query") + "." + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Query"));
        serviceInterface.addImportedType(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));

        // 添加范型继承关系BaseService
        String keyType = introspectedTable.getPrimaryKeyColumns().get(0).getFullyQualifiedJavaType().getShortName();
        serviceInterface.addSuperInterface(new FullyQualifiedJavaType(
            "IBaseEntityService<" + keyType + ", " + introspectedTable.getBaseRecordType() + ", " + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Query" + ">"));

        // 添加注释
        CommentGenerator commentGenerator = context.getCommentGenerator();
        commentGenerator.addJavaFileComment(serviceInterface);
        return new GeneratedJavaFile(serviceInterface, getTargetProjectJavaSourceFolder(), "utf-8", new DefaultJavaFormatter());
    }

}
