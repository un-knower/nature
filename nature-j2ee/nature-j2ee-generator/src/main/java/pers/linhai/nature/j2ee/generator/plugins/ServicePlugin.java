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

import org.springframework.stereotype.Service;

import pers.linhai.nature.j2ee.generator.core.api.CommentGenerator;
import pers.linhai.nature.j2ee.generator.core.api.CoreClassImportConstant;
import pers.linhai.nature.j2ee.generator.core.api.GeneratedJavaFile;
import pers.linhai.nature.j2ee.generator.core.api.IntrospectedTable;
import pers.linhai.nature.j2ee.generator.core.api.dom.DefaultJavaFormatter;
import pers.linhai.nature.j2ee.generator.core.api.dom.java.FullyQualifiedJavaType;
import pers.linhai.nature.j2ee.generator.core.api.dom.java.Interface;
import pers.linhai.nature.j2ee.generator.core.api.dom.java.JavaVisibility;
import pers.linhai.nature.j2ee.generator.core.api.dom.java.TopLevelClass;
import pers.linhai.nature.j2ee.generator.utils.CodeCommentUtils;

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
        serviceImpl.addImportedType(new FullyQualifiedJavaType(getTargetPackae("interfaces") + ".I" + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Service"));
        serviceImpl.addImportedType(new FullyQualifiedJavaType(CoreClassImportConstant.BASE_ENTITY_SERVICE_IMPL_CLASS));
        serviceImpl.addImportedType(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));
        serviceImpl.addImportedType(new FullyQualifiedJavaType(getTargetPackae("dao", "mapper") + ".I" + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Mapper"));
        serviceImpl.addImportedType(new FullyQualifiedJavaType(getTargetPackae("model", "query") + "." + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Query"));
        
        // 添加spring扫描注解
        serviceImpl.addAnnotation("@Service");
        
        // 添加范型继承关系BaseService
        String keyType = introspectedTable.getPrimaryKeyColumns().get(0).getFullyQualifiedJavaType().getShortName();
        serviceImpl.setSuperClass(new FullyQualifiedJavaType("BaseEntityServiceImpl<" + keyType + ", " + introspectedTable.getBaseRecordType() + ", "
            + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Query" + ", I" 
            + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Mapper" + ">"));
        serviceImpl.addSuperInterface(new FullyQualifiedJavaType(getTargetPackae("interfaces") + ".I" + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Service"));
        
        //添加注释
        CommentGenerator commentGenerator = context.getCommentGenerator();
        commentGenerator.addJavaFileComment(serviceImpl);
        return new GeneratedJavaFile(serviceImpl, getTargetProjectJavaSourceFolder(), "utf-8", new DefaultJavaFormatter());
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
        serviceInterface.addSuperInterface(new FullyQualifiedJavaType("IBaseEntityService<" + keyType + ", " + introspectedTable.getBaseRecordType() + ", "
            + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Query" + ">"));
        
        //添加注释
        CommentGenerator commentGenerator = context.getCommentGenerator();
        commentGenerator.addJavaFileComment(serviceInterface);
        return new GeneratedJavaFile(serviceInterface, getTargetProjectJavaSourceFolder(), "utf-8", new DefaultJavaFormatter());
    }

    
}
