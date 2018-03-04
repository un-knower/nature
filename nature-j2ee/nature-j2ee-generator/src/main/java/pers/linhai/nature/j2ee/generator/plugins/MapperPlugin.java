/*
 * <p>Copyright : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p> <p>FileName : MapperPlugin.java</p>
 * <p>Package : codegenerator.plugins</p>
 * @Creator lilinhai 2018年2月4日 下午3:36:17
 * @Version V1.0
 */

package pers.linhai.nature.j2ee.generator.plugins;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.springframework.stereotype.Repository;

import pers.linhai.nature.j2ee.generator.core.api.CommentGenerator;
import pers.linhai.nature.j2ee.generator.core.api.CoreClassImportConstant;
import pers.linhai.nature.j2ee.generator.core.api.GeneratedJavaFile;
import pers.linhai.nature.j2ee.generator.core.api.IntrospectedTable;
import pers.linhai.nature.j2ee.generator.core.api.dom.DefaultJavaFormatter;
import pers.linhai.nature.j2ee.generator.core.api.dom.java.FullyQualifiedJavaType;
import pers.linhai.nature.j2ee.generator.core.api.dom.java.Interface;
import pers.linhai.nature.j2ee.generator.core.api.dom.java.JavaVisibility;
import pers.linhai.nature.j2ee.generator.core.api.dom.java.Method;
import pers.linhai.nature.j2ee.generator.core.api.dom.java.TopLevelClass;
import pers.linhai.nature.j2ee.generator.utils.CodeCommentUtils;


/**
 * <p>Description    : <pre>TODO(这里用一句话描述这个类的作用)</pre></p>
 * <p>ClassName      : MapperPlugin</p>
 * @author lilinhai 2018年2月4日 下午3:36:17
 * @version 1.0
 */
public class MapperPlugin extends BasePlugin
{

    private static final Set<String> METHOD_TO_BE_REMOVED_LIST = new HashSet<String>();
    static
    {
        METHOD_TO_BE_REMOVED_LIST.add("deleteByPrimaryKey");
        METHOD_TO_BE_REMOVED_LIST.add("insert");
        METHOD_TO_BE_REMOVED_LIST.add("insertSelective");
        METHOD_TO_BE_REMOVED_LIST.add("selectByPrimaryKey");
        METHOD_TO_BE_REMOVED_LIST.add("updateByPrimaryKeySelective");
        METHOD_TO_BE_REMOVED_LIST.add("updateByPrimaryKey");
    }

    public void setProperties(Properties properties) 
    {
        super.setProperties(properties);
        moduleName = "dao";
        artifactId = projectName + "-" + moduleName;
    }
    
    /** 
     * <p>Overriding Method: lilinhai 2018年2月4日 下午3:36:17</p>
     * <p>Title: validate</p>
     * <p>Description: TODO</p>
     * @param warnings
     * @return 
     * @see pers.linhai.nature.j2ee.generator.core.api.Plugin#validate(java.util.List)
     */
    public boolean validate(List<String> warnings)
    {
        return true;
    }

    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable)
    {
        CodeCommentUtils.addMapperInterfaceComment(interfaze, introspectedTable);
        interfaze.addImportedType(new FullyQualifiedJavaType(CoreClassImportConstant.IBASE_MAPPER_CLASS));
        interfaze.addImportedType(new FullyQualifiedJavaType(getTargetPackae("model", "query") + "." + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Query"));
        //interfaze.addAnnotation("@Mapper");
        String keyType = introspectedTable.getPrimaryKeyColumns().get(0).getFullyQualifiedJavaType().getShortName();
        interfaze.addSuperInterface(new FullyQualifiedJavaType("IBaseMapper<" + keyType  + ", " + introspectedTable.getBaseRecordType() + ", " + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Query" + ">"));

        List<Method> ml = new ArrayList<Method>();
        for (Method method : interfaze.getMethods())
        {
            if (!METHOD_TO_BE_REMOVED_LIST.contains(method.getName()))
            {
                ml.add(method);
            }
        }

        interfaze.getMethods().clear();
        if (!ml.isEmpty())
        {
            interfaze.getMethods().addAll(ml);
        }
        return true;
    }

    /** 
     * <p>Overriding Method: lilinhai 2018年2月12日 下午1:38:46</p>
     * <p>Title: contextGenerateAdditionalJavaFiles</p>
     * <p>Description: TODO</p>
     * @param introspectedTable
     * @return 
     * @see pers.linhai.nature.j2ee.generator.core.api.PluginAdapter#contextGenerateAdditionalJavaFiles(pers.linhai.nature.j2ee.generator.core.api.IntrospectedTable)
     */ 
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable)
    {
        List<GeneratedJavaFile> javaFileList = new ArrayList<GeneratedJavaFile>();
        
        // 创建实体对应的service实现类
        javaFileList.add(createMapperImpl(introspectedTable));
        return javaFileList;
    }
    
    /**
     * 创建实体对应的service接口
     * <p>Title         : createServiceInterface lilinhai 2018年2月5日 下午2:36:13</p>
     * @param introspectedTable
     * @return 
     * GeneratedJavaFile 
     */ 
    private GeneratedJavaFile createMapperImpl(IntrospectedTable introspectedTable)
    {
        TopLevelClass mapperImpl = new TopLevelClass(getTargetPackae("impl") + "." + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "MapperImpl");
        
        // 给service实现类加注释
        CodeCommentUtils.addMapperImplComment(mapperImpl, introspectedTable);
        
        // 将接口访问权限设置为public
        mapperImpl.setVisibility(JavaVisibility.PUBLIC);
        
        // 添加需要依赖的类
        mapperImpl.addImportedType(new FullyQualifiedJavaType(CoreClassImportConstant.BASE_MAPPER_IMPL_CLASS));
        mapperImpl.addImportedType(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));
        mapperImpl.addImportedType(new FullyQualifiedJavaType(Repository.class.getName()));
        mapperImpl.addImportedType(new FullyQualifiedJavaType(getTargetPackae("dao", "mapper") + ".I" + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Mapper"));
        mapperImpl.addImportedType(new FullyQualifiedJavaType(getTargetPackae("model", "query") + "." + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Query"));
        
        // 添加spring扫描注解
        mapperImpl.addAnnotation("@" + Repository.class.getSimpleName());
        
        // 添加范型继承关系BaseService
        String keyType = introspectedTable.getPrimaryKeyColumns().get(0).getFullyQualifiedJavaType().getShortName();
        mapperImpl.setSuperClass(new FullyQualifiedJavaType("BaseMapperImpl<" + keyType + ", " + introspectedTable.getBaseRecordType() + ", "
            + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Query" + ">"));
        mapperImpl.addSuperInterface(new FullyQualifiedJavaType(getTargetPackae("mapper") + ".I" + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Mapper"));
        
        //添加注释
        CommentGenerator commentGenerator = context.getCommentGenerator();
        commentGenerator.addJavaFileComment(mapperImpl);
        return new GeneratedJavaFile(mapperImpl, getTargetProjectJavaSourceFolder(), "utf-8", new DefaultJavaFormatter());
    }

}
