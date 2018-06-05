/*
 * <p>Copyright : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p> <p>FileName : MapperPlugin.java</p>
 * <p>Package : codegenerator.plugins</p>
 * @Creator lilinhai 2018年2月4日 下午3:36:17
 * @Version V1.0
 */

package pers.linhai.nature.j2ee.generator.plugins;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import pers.linhai.nature.j2ee.core.dao.exception.IllegalFieldException;
import pers.linhai.nature.j2ee.core.model.DateJsonDeserializer;
import pers.linhai.nature.j2ee.core.model.EntityBean;
import pers.linhai.nature.j2ee.core.model.ModelField;
import pers.linhai.nature.j2ee.core.model.ModelHelper;
import pers.linhai.nature.j2ee.core.model.builder.BaseOrderBuilder;
import pers.linhai.nature.j2ee.core.model.builder.BaseQueryBuilder;
import pers.linhai.nature.j2ee.core.model.builder.BaseSelectBuilder;
import pers.linhai.nature.j2ee.core.model.builder.BaseWhereBuilder;
import pers.linhai.nature.j2ee.core.model.builder.FieldConditionBuilder;
import pers.linhai.nature.j2ee.core.model.builder.OrderByFieldBuilder;
import pers.linhai.nature.j2ee.core.model.enumer.JdbcType;
import pers.linhai.nature.j2ee.generator.core.api.CoreClassImportConstant;
import pers.linhai.nature.j2ee.generator.core.api.GeneratedJavaFile;
import pers.linhai.nature.j2ee.generator.core.api.IntrospectedColumn;
import pers.linhai.nature.j2ee.generator.core.api.IntrospectedTable;
import pers.linhai.nature.j2ee.generator.core.api.dom.DefaultJavaFormatter;
import pers.linhai.nature.j2ee.generator.core.api.dom.java.Field;
import pers.linhai.nature.j2ee.generator.core.api.dom.java.FullyQualifiedJavaType;
import pers.linhai.nature.j2ee.generator.core.api.dom.java.InitializationBlock;
import pers.linhai.nature.j2ee.generator.core.api.dom.java.Interface;
import pers.linhai.nature.j2ee.generator.core.api.dom.java.JavaVisibility;
import pers.linhai.nature.j2ee.generator.core.api.dom.java.Method;
import pers.linhai.nature.j2ee.generator.core.api.dom.java.Parameter;
import pers.linhai.nature.j2ee.generator.core.api.dom.java.TopLevelClass;
import pers.linhai.nature.j2ee.generator.core.api.dom.java.TopLevelEnumeration;
import pers.linhai.nature.j2ee.generator.exception.GeneratorException;
import pers.linhai.nature.j2ee.generator.utils.CodeGeneratorUtils;
import pers.linhai.nature.utils.NamerUtils;


/**
 * <p>Description    : <pre>TODO(这里用一句话描述这个类的作用)</pre></p>
 * <p>ClassName      : MapperPlugin</p>
 * @author lilinhai 2018年2月4日 下午3:36:17
 * @version 1.0
 */
public class ModelPlugin extends BasePlugin
{
    
    private static final Set<String> FIELD_TO_BE_REMOVED_LIST = new HashSet<String>();

    private static final Set<String> METHOD_TO_BE_REMOVED_LIST = new HashSet<String>();
    static
    {
        FIELD_TO_BE_REMOVED_LIST.add("id");
        FIELD_TO_BE_REMOVED_LIST.add("createTime");
        FIELD_TO_BE_REMOVED_LIST.add("updateTime");

        METHOD_TO_BE_REMOVED_LIST.add("getId");
        METHOD_TO_BE_REMOVED_LIST.add("setId");
        METHOD_TO_BE_REMOVED_LIST.add("getCreateTime");
        METHOD_TO_BE_REMOVED_LIST.add("setCreateTime");
        METHOD_TO_BE_REMOVED_LIST.add("getUpdateTime");
        METHOD_TO_BE_REMOVED_LIST.add("setUpdateTime");
    }

    public void setProperties(Properties properties)
    {
        super.setProperties(properties);
        moduleName = "model";
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

    /** 
     * <p>Overriding Method: lilinhai 2018年2月6日 上午10:06:38</p>
     * <p>Title: modelFieldGenerated</p>
     * <p>Description: TODO</p>
     * @param field
     * @param topLevelClass
     * @param introspectedColumn
     * @param introspectedTable
     * @param modelClassType
     * @return 
     * @see pers.linhai.nature.j2ee.generator.core.api.PluginAdapter#modelFieldGenerated(pers.linhai.nature.j2ee.generator.core.api.dom.java.Field, pers.linhai.nature.j2ee.generator.core.api.dom.java.TopLevelClass, pers.linhai.nature.j2ee.generator.core.api.IntrospectedColumn, pers.linhai.nature.j2ee.generator.core.api.IntrospectedTable, pers.linhai.nature.j2ee.generator.core.api.Plugin.ModelClassType)
     */
    public boolean modelFieldGenerated(Field field, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType)
    {
        if (FIELD_TO_BE_REMOVED_LIST.contains(field.getName()))
        {
            return false;
        }
        return true;
    }

    /** 
     * <p>Overriding Method: lilinhai 2018年2月6日 上午10:06:38</p>
     * <p>Title: modelGetterMethodGenerated</p>
     * <p>Description: TODO</p>
     * @param method
     * @param topLevelClass
     * @param introspectedColumn
     * @param introspectedTable
     * @param modelClassType
     * @return 
     * @see pers.linhai.nature.j2ee.generator.core.api.PluginAdapter#modelGetterMethodGenerated(pers.linhai.nature.j2ee.generator.core.api.dom.java.Method, pers.linhai.nature.j2ee.generator.core.api.dom.java.TopLevelClass, pers.linhai.nature.j2ee.generator.core.api.IntrospectedColumn, pers.linhai.nature.j2ee.generator.core.api.IntrospectedTable, pers.linhai.nature.j2ee.generator.core.api.Plugin.ModelClassType)
     */
    public boolean modelGetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType)
    {
        if (METHOD_TO_BE_REMOVED_LIST.contains(method.getName()))
        {
            return false;
        }
        return true;
    }

    /** 
     * <p>Overriding Method: lilinhai 2018年2月6日 上午10:06:38</p>
     * <p>Title: modelSetterMethodGenerated</p>
     * <p>Description: TODO</p>
     * @param method
     * @param topLevelClass
     * @param introspectedColumn
     * @param introspectedTable
     * @param modelClassType
     * @return 
     * @see pers.linhai.nature.j2ee.generator.core.api.PluginAdapter#modelSetterMethodGenerated(pers.linhai.nature.j2ee.generator.core.api.dom.java.Method, pers.linhai.nature.j2ee.generator.core.api.dom.java.TopLevelClass, pers.linhai.nature.j2ee.generator.core.api.IntrospectedColumn, pers.linhai.nature.j2ee.generator.core.api.IntrospectedTable, pers.linhai.nature.j2ee.generator.core.api.Plugin.ModelClassType)
     */
    public boolean modelSetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType)
    {
        if (METHOD_TO_BE_REMOVED_LIST.contains(method.getName()))
        {
            return false;
        }
        return true;
    }

    /** 
     * <p>Overriding Method: lilinhai 2018年2月6日 上午9:32:27</p>
     * <p>Title: modelBaseRecordClassGenerated</p>
     * @param topLevelClass
     * @param introspectedTable
     * @return 
     * @see pers.linhai.nature.j2ee.generator.core.api.PluginAdapter#modelBaseRecordClassGenerated(pers.linhai.nature.j2ee.generator.core.api.dom.java.TopLevelClass, pers.linhai.nature.j2ee.generator.core.api.IntrospectedTable)
     */
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable)
    {
        topLevelClass.addImportedType(new FullyQualifiedJavaType(CoreClassImportConstant.BASE_ENTITY_CLASS));
        topLevelClass.addImportedType(getTargetPackae("helper") + "." + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Helper");
        topLevelClass.addImportedType(EntityBean.class.getName());
        topLevelClass.addImportedType(new FullyQualifiedJavaType(getTargetPackae("field") + "." + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Field"));
        boolean hasDateField = false;
        
        // 判断是否存在Date类型（除createTime和updateTime之外）的字段
        for (Field f : topLevelClass.getFields())
        {
            if (f.getType().getFullyQualifiedNameWithoutTypeParameters().equals(Date.class.getName()))
            {
                hasDateField = true;
                break;
            }
        }
        if (hasDateField)
        {
            topLevelClass.addImportedType(Date.class.getName());
            topLevelClass.addImportedType(JsonDeserialize.class.getName());
            topLevelClass.addImportedType(DateJsonDeserializer.class.getName());
        }

        String keyType = introspectedTable.getPrimaryKeyColumns().get(0).getFullyQualifiedJavaType().getShortName();
        topLevelClass.setSuperClass(new FullyQualifiedJavaType("BaseEntity<" + keyType + ">"));
        topLevelClass.addSuperInterface(new FullyQualifiedJavaType(getTargetPackae("helper") + "." + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Helper"));

        Method toEntityBeanMethod = new Method("toEntityBean");
        toEntityBeanMethod.setConstructor(false);
        toEntityBeanMethod.setVisibility(JavaVisibility.PUBLIC);
        toEntityBeanMethod.setFinal(false);
        toEntityBeanMethod.setStatic(false);
        toEntityBeanMethod.setReturnType(new FullyQualifiedJavaType(EntityBean.class.getName()));
        toEntityBeanMethod.addBodyLine("EntityBean entityBean = new EntityBean();");
        for (IntrospectedColumn introspectedColumn : introspectedTable.getAllColumns())
        {
            String enumFieldName = introspectedColumn.getActualColumnName().toUpperCase(Locale.ENGLISH);
            toEntityBeanMethod.addBodyLine("entityBean.put(" + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Field." + enumFieldName + ".getField(), this." + introspectedColumn.getJavaProperty() + ");");
        }
        toEntityBeanMethod.addBodyLine("return entityBean;");
        topLevelClass.addMethod(toEntityBeanMethod);
        
        Field field = new Field();
        field.setFinal(true);
        field.setInitializationString("1L"); //$NON-NLS-1$
        field.setName("serialVersionUID"); //$NON-NLS-1$
        field.setStatic(true);
        field.setType(new FullyQualifiedJavaType("long")); //$NON-NLS-1$
        field.setVisibility(JavaVisibility.PRIVATE);
        field.addJavaDocLine("\n"); //$NON-NLS-1$
        field.addJavaDocLine("/**"); //$NON-NLS-1$
        field.addJavaDocLine(" * long serialVersionUID 序列化版本ID");
        field.addJavaDocLine(" */"); //$NON-NLS-1$
        topLevelClass.addField(field);
        
        Collections.sort(topLevelClass.getFields(), new Comparator<Field>()
        {
            public int compare(Field f1, Field f2)
            {
                if (f1.isStatic() && !f2.isStatic())
                {
                    return -1;
                }
                else if (!f1.isStatic() && f2.isStatic())
                {
                    return 1;
                }
                else if (f1.isStatic() == f2.isStatic())
                {
                    return 0;
                }
                return 0;
            }
        });
        return true;
    }

    /** 
     * <p>Overriding Method: lilinhai 2018年2月6日 下午2:37:48</p>
     * <p>Title: contextGenerateAdditionalJavaFiles</p>
     * <p>Description: TODO</p>
     * @param introspectedTable
     * @return 
     * @see pers.linhai.nature.j2ee.generator.core.api.PluginAdapter#contextGenerateAdditionalJavaFiles(pers.linhai.nature.j2ee.generator.core.api.IntrospectedTable)
     */
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable)
    {
        List<GeneratedJavaFile> javaFileList = new ArrayList<GeneratedJavaFile>();

        // 创建实体对应的QueryBean
        javaFileList.add(createEntityQueryBean(introspectedTable));
        
        // 创建实体对应的SelectBuilder
        javaFileList.add(createEntitySelectBuilder(introspectedTable));
        
        // 创建实体对应的WhereBuilder
        javaFileList.add(createEntityWhereBuilder(introspectedTable));
        
        // 创建实体对应的OrderBuilder
        javaFileList.add(createEntityOrderBuilder(introspectedTable));
        
        // 创建实体对应的QueryBuilder
        javaFileList.add(createEntityQueryBuilder(introspectedTable));
        
        // 创建字段枚举
        javaFileList.add(createEntityFieldEnum(introspectedTable));
        
        // 创建实体对应的Bean
        javaFileList.add(createEntityHelper(introspectedTable));
        
        return javaFileList;
    }
    
    private GeneratedJavaFile createEntityFieldEnum(IntrospectedTable introspectedTable)
    {
        TopLevelEnumeration fieldEnumeration = new TopLevelEnumeration(new FullyQualifiedJavaType(getTargetPackae("field") + "." + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Field"));
        
        fieldEnumeration.addImportedType(new FullyQualifiedJavaType(Map.class.getName()));
        fieldEnumeration.addImportedType(new FullyQualifiedJavaType(HashMap.class.getName()));
        fieldEnumeration.addImportedType(new FullyQualifiedJavaType(List.class.getName()));
        fieldEnumeration.addImportedType(new FullyQualifiedJavaType(ArrayList.class.getName()));
        fieldEnumeration.addImportedType(new FullyQualifiedJavaType(JdbcType.class.getName()));
        fieldEnumeration.addImportedType(new FullyQualifiedJavaType(NamerUtils.class.getName()));
        fieldEnumeration.addImportedType(new FullyQualifiedJavaType(ModelField.class.getName()));
        
        fieldEnumeration.setStatic(false);
        fieldEnumeration.setVisibility(JavaVisibility.PUBLIC);
        
        fieldEnumeration.addJavaDocLine("/**");
        fieldEnumeration.addJavaDocLine(" * 该实体所有字段枚举");
        fieldEnumeration.addJavaDocLine(" */");
        
        // 标记该类为内部类
        //fieldEnumeration.setInnerClass(true);
        fieldEnumeration.addSuperInterface(new FullyQualifiedJavaType(ModelField.class.getName()));
        
        Field mapField = new Field();
        mapField.addJavaDocLine("/**");
        mapField.addJavaDocLine(" * 所有字段的hashmap映射");
        mapField.addJavaDocLine(" */");
        mapField.setFinal(true);
        mapField.setName("MAP");
        mapField.setStatic(true);
        mapField.setType(new FullyQualifiedJavaType(Map.class.getName() + "<String, "+introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Field"+">"));
        mapField.setInitializationString("new HashMap<String, "+introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Field"+">()");
        mapField.setVisibility(JavaVisibility.PRIVATE);
        fieldEnumeration.addField(mapField);
        
        Field tableFieldList = new Field();
        tableFieldList.addJavaDocLine("/**");
        tableFieldList.addJavaDocLine(" * 数据库表所有字段名集合");
        tableFieldList.addJavaDocLine(" */");
        tableFieldList.setFinal(true);
        tableFieldList.setName("COLUMN_LIST");
        tableFieldList.setStatic(true);
        tableFieldList.setType(new FullyQualifiedJavaType(List.class.getName() + "<String>"));
        tableFieldList.setInitializationString("new ArrayList<String>()");
        tableFieldList.setVisibility(JavaVisibility.PRIVATE);
        fieldEnumeration.addField(tableFieldList);
        
        Field javaFieldList = new Field();
        javaFieldList.addJavaDocLine("/**");
        javaFieldList.addJavaDocLine(" * java实体所有字段名集合");
        javaFieldList.addJavaDocLine(" */");
        javaFieldList.setFinal(true);
        javaFieldList.setName("FIELD_LIST");
        javaFieldList.setStatic(true);
        javaFieldList.setType(new FullyQualifiedJavaType(List.class.getName() + "<String>"));
        javaFieldList.setInitializationString("new ArrayList<String>()");
        javaFieldList.setVisibility(JavaVisibility.PRIVATE);
        fieldEnumeration.addField(javaFieldList);
        
        //数据库表名
        Field tableNameField = new Field();
        tableNameField.addJavaDocLine("/**");
        tableNameField.addJavaDocLine(" * 数据库表名");
        tableNameField.addJavaDocLine(" */");
        tableNameField.setFinal(true);
        tableNameField.setName("TABLE");
        tableNameField.setStatic(true);
        tableNameField.setType(new FullyQualifiedJavaType(String.class.getName()));
        tableNameField.setInitializationString("\""+introspectedTable.getFullyQualifiedTableNameAtRuntime()+"\"");
        tableNameField.setVisibility(JavaVisibility.PUBLIC);
        fieldEnumeration.addField(tableNameField);
        
        //java实体类名
        Field entityNameField = new Field();
        entityNameField.addJavaDocLine("/**");
        entityNameField.addJavaDocLine(" * java实体类名");
        entityNameField.addJavaDocLine(" */");
        entityNameField.setFinal(true);
        entityNameField.setName("ENTITY");
        entityNameField.setStatic(true);
        entityNameField.setType(new FullyQualifiedJavaType(String.class.getName()));
        entityNameField.setInitializationString("\""+introspectedTable.getFullyQualifiedTable().getDomainObjectName()+"\"");
        entityNameField.setVisibility(JavaVisibility.PUBLIC);
        fieldEnumeration.addField(entityNameField);
        
        // 添加static块
        InitializationBlock initializationBlock = new InitializationBlock();
        initializationBlock.setStatic(true);
        initializationBlock.addBodyLine("for ("+introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Field"+" field : values())");
        initializationBlock.addBodyLine("{");
        initializationBlock.addBodyLine("MAP.put(field.getField(), field);");
        //initializationBlock.addBodyLine("MAP.put(field.getColumn(), field);");
        initializationBlock.addBodyLine("COLUMN_LIST.add(field.getColumn());");
        initializationBlock.addBodyLine("FIELD_LIST.add(field.getField());");
        initializationBlock.addBodyLine("}");
        fieldEnumeration.addInitializationBlock(initializationBlock);
        
        Field javaField = new Field();
        javaField.addJavaDocLine("/**");
        javaField.addJavaDocLine(" * 对应的java实体字段名");
        javaField.addJavaDocLine(" */");
        javaField.setFinal(false);
        javaField.setName("field");
        javaField.setStatic(false);
        javaField.setType(new FullyQualifiedJavaType("String"));
        javaField.setVisibility(JavaVisibility.PRIVATE);
        fieldEnumeration.addField(javaField);
        
        Field tableField = new Field();
        tableField.addJavaDocLine("/**");
        tableField.addJavaDocLine(" * 对应的table数据库表字段名");
        tableField.addJavaDocLine(" */");
        tableField.setFinal(false);
        tableField.setName("column");
        tableField.setStatic(false);
        tableField.setType(new FullyQualifiedJavaType("String"));
        tableField.setVisibility(JavaVisibility.PRIVATE);
        fieldEnumeration.addField(tableField);
        
        Field jdbcType = new Field();
        jdbcType.addJavaDocLine("/**");
        jdbcType.addJavaDocLine(" * 字段对应的JDBC类型");
        jdbcType.addJavaDocLine(" */");
        jdbcType.setFinal(false);
        jdbcType.setName("jdbcType");
        jdbcType.setStatic(false);
        jdbcType.setType(new FullyQualifiedJavaType(JdbcType.class.getName()));
        jdbcType.setVisibility(JavaVisibility.PRIVATE);
        fieldEnumeration.addField(jdbcType);
        
        // 添加初始化对象的构造函数
        Method constructorMethod = new Method(introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Field");
        constructorMethod.setConstructor(true);
        constructorMethod.addParameter(new Parameter(new FullyQualifiedJavaType(JdbcType.class.getName()), "jdbcType"));
        constructorMethod.addBodyLine("this.column = name().toLowerCase();");
        constructorMethod.addBodyLine("this.field = NamerUtils.columnToProterty(column);");
        constructorMethod.addBodyLine("this.jdbcType = jdbcType;");
        constructorMethod.setVisibility(JavaVisibility.DEFAULT);
        constructorMethod.setFinal(false);
        constructorMethod.setStatic(false);
        fieldEnumeration.addMethod(constructorMethod);
        
        // getEntity方法
        Method getEntityMethod = new Method("getEntity");
        getEntityMethod.setFinal(false);
        getEntityMethod.setStatic(false);
        getEntityMethod.setVisibility(JavaVisibility.PUBLIC);
        getEntityMethod.setReturnType(new FullyQualifiedJavaType("String"));
        getEntityMethod.addBodyLine("return ENTITY;");
        fieldEnumeration.addMethod(getEntityMethod);
        
        // getTable方法
        Method getTableMethod = new Method("getTable");
        getTableMethod.setFinal(false);
        getTableMethod.setStatic(false);
        getTableMethod.setVisibility(JavaVisibility.PUBLIC);
        getTableMethod.setReturnType(new FullyQualifiedJavaType("String"));
        getTableMethod.addBodyLine("return TABLE;");
        fieldEnumeration.addMethod(getTableMethod);
        
        // getJavaField方法
        Method getJavaFieldMethod = new Method("getField");
        getJavaFieldMethod.setFinal(false);
        getJavaFieldMethod.setStatic(false);
        getJavaFieldMethod.setVisibility(JavaVisibility.PUBLIC);
        getJavaFieldMethod.setReturnType(new FullyQualifiedJavaType("String"));
        getJavaFieldMethod.addBodyLine("return field;");
        fieldEnumeration.addMethod(getJavaFieldMethod);
        
        // getTableField方法
        Method getTableFieldMethod = new Method("getColumn");
        getTableFieldMethod.setFinal(false);
        getTableFieldMethod.setStatic(false);
        getTableFieldMethod.setVisibility(JavaVisibility.PUBLIC);
        getTableFieldMethod.setReturnType(new FullyQualifiedJavaType("String"));
        getTableFieldMethod.addBodyLine("return column;");
        fieldEnumeration.addMethod(getTableFieldMethod);
        
        // getJdbcType方法
        Method getJdbcTypeMethod = new Method("getJdbcType");
        getJdbcTypeMethod.setFinal(false);
        getJdbcTypeMethod.setStatic(false);
        getJdbcTypeMethod.setVisibility(JavaVisibility.PUBLIC);
        getJdbcTypeMethod.setReturnType(new FullyQualifiedJavaType("String"));
        getJdbcTypeMethod.addBodyLine("return jdbcType.getType();");
        fieldEnumeration.addMethod(getJdbcTypeMethod);
        
        // transfer方法
        Method transferMethod = new Method("transfer");
        transferMethod.setFinal(false);
        transferMethod.setStatic(true);
        transferMethod.setVisibility(JavaVisibility.PUBLIC);
        transferMethod.addParameter(new Parameter(new FullyQualifiedJavaType("String"), "field"));
        transferMethod.setReturnType(new FullyQualifiedJavaType(introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Field"));
        transferMethod.addBodyLine("return MAP.get(field);");
        fieldEnumeration.addMethod(transferMethod);
        
        // getColumnList方法
        Method getTableFieldListMethod = new Method("getColumnList");
        getTableFieldListMethod.setFinal(false);
        getTableFieldListMethod.setStatic(true);
        getTableFieldListMethod.setVisibility(JavaVisibility.PUBLIC);
        getTableFieldListMethod.setReturnType(new FullyQualifiedJavaType(List.class.getName() + "<String>"));
        getTableFieldListMethod.addBodyLine("return COLUMN_LIST;");
        fieldEnumeration.addMethod(getTableFieldListMethod);
        
        // getFieldList方法
        Method getJavaFieldListMethod = new Method("getFieldList");
        getJavaFieldListMethod.setFinal(false);
        getJavaFieldListMethod.setStatic(true);
        getJavaFieldListMethod.setVisibility(JavaVisibility.PUBLIC);
        getJavaFieldListMethod.setReturnType(new FullyQualifiedJavaType(List.class.getName() + "<String>"));
        getJavaFieldListMethod.addBodyLine("return FIELD_LIST;");
        fieldEnumeration.addMethod(getJavaFieldListMethod);
        for (IntrospectedColumn introspectedColumn : introspectedTable.getAllColumns())
        {
            String enumFieldName = introspectedColumn.getActualColumnName().toUpperCase(Locale.ENGLISH);
            fieldEnumeration.addEnumConstant(enumFieldName + "(JdbcType." + introspectedColumn.getJdbcTypeName() + ")");
        }
        return new GeneratedJavaFile(fieldEnumeration, getTargetProjectJavaSourceFolder(), "utf-8", new DefaultJavaFormatter());
    }
    
    /**
     * <p>Title         : createEntityHelper lilinhai 2018年5月20日 上午11:18:24</p>
     * <p>Description   : <pre>TODO(这里用一句话描述这个方法的作用)</pre></p>
     * @param introspectedTable
     * @return 
     * GeneratedJavaFile 
     */ 
    private GeneratedJavaFile createEntityHelper(IntrospectedTable introspectedTable)
    {
        Interface beanClass = new Interface(getTargetPackae("helper") + "." + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Helper");

        // 将接口访问权限设置为public
        beanClass.setVisibility(JavaVisibility.PUBLIC);
        
        // 添加需要依赖的类
        beanClass.addImportedType(ModelHelper.class.getName());
        beanClass.addImportedType(List.class.getName());
        beanClass.addImportedType(IllegalFieldException.class.getName());
        beanClass.addImportedType(new FullyQualifiedJavaType(getTargetPackae("field") + "." + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Field"));
        
        // 添加范型继承关系BaseService
        if (introspectedTable.getPrimaryKeyColumns() == null || introspectedTable.getPrimaryKeyColumns().isEmpty())
        {
            throw new GeneratorException(introspectedTable.getBaseRecordType() + " 该表没有设置主键，请设置！");
        }
        
        // 添加继承关系
        beanClass.addSuperInterface(new FullyQualifiedJavaType(ModelHelper.class.getName()));

        // 添加类注释
        beanClass.addJavaDocLine("/**");
        beanClass.addJavaDocLine(" * <pre>"); //$NON-NLS-1$
        beanClass.addJavaDocLine(" *    针对该实体的Helper封装，用于提供校验字段名的完整性的借口，以及返回实体所有数据库字段名集合等辅助功能。");
        beanClass.addJavaDocLine(" * ClassName: " + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Helper");
        beanClass.addJavaDocLine(" * @author nature-j2ee-code-generator");
        beanClass.addJavaDocLine(" * @version 1.0");
        beanClass.addJavaDocLine(" * </pre>"); //$NON-NLS-1$
        beanClass.addJavaDocLine(" */");
        
        // validField方法
        Method validFieldMethod = new Method("validField");
        validFieldMethod.addJavaDocLine("/**");
        validFieldMethod.addJavaDocLine(" * 校验输入字段的合法性");
        validFieldMethod.addJavaDocLine(" */");
        validFieldMethod.setFinal(false);
        validFieldMethod.setStatic(false);
        validFieldMethod.setDefault(true);
        validFieldMethod.addParameter(new Parameter(new FullyQualifiedJavaType("String"), "field"));
        validFieldMethod.addBodyLine("// 如果枚举为空，说明是非法字段");
        validFieldMethod.addBodyLine("if (" + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Field" + ".transfer(field) == null)");
        validFieldMethod.addBodyLine("{");
        validFieldMethod.addBodyLine("throw new IllegalFieldException(\" There is an illegal field : \" + field);");
        validFieldMethod.addBodyLine("}");
        beanClass.addMethod(validFieldMethod);
        
        // getColumn方法
        Method getTableFieldBaseMethod = new Method("getColumn");
        getTableFieldBaseMethod.addJavaDocLine("/**");
        getTableFieldBaseMethod.addJavaDocLine(" * 获取java字段名对应的数据库表字段名");
        getTableFieldBaseMethod.addJavaDocLine(" */");
        getTableFieldBaseMethod.setFinal(false);
        getTableFieldBaseMethod.setStatic(false);
        getTableFieldBaseMethod.setDefault(true);
        getTableFieldBaseMethod.addParameter(new Parameter(new FullyQualifiedJavaType("String"), "field"));
        getTableFieldBaseMethod.setReturnType(new FullyQualifiedJavaType("String"));
        getTableFieldBaseMethod.addBodyLine(introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Field e = null;");
        getTableFieldBaseMethod.addBodyLine("if ((e = " + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Field" + ".transfer(field)) != null)");
        getTableFieldBaseMethod.addBodyLine("{");
        getTableFieldBaseMethod.addBodyLine("return e.getColumn();");
        getTableFieldBaseMethod.addBodyLine("}");
        getTableFieldBaseMethod.addBodyLine("throw new IllegalFieldException(\" Can't find the table-field from the java-field : \" + field);");
        beanClass.addMethod(getTableFieldBaseMethod);
        
        // getJdbcType方法
        Method getJdbcTypeBaseMethod = new Method("getJdbcType");
        getJdbcTypeBaseMethod.addJavaDocLine("/**");
        getJdbcTypeBaseMethod.addJavaDocLine(" * 获取改该字段对应的JDBC类型");
        getJdbcTypeBaseMethod.addJavaDocLine(" */");
        getJdbcTypeBaseMethod.setFinal(false);
        getJdbcTypeBaseMethod.setStatic(false);
        getJdbcTypeBaseMethod.setDefault(true);
        getJdbcTypeBaseMethod.addParameter(new Parameter(new FullyQualifiedJavaType("String"), "field"));
        getJdbcTypeBaseMethod.setReturnType(new FullyQualifiedJavaType("String"));
        getJdbcTypeBaseMethod.addBodyLine(introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Field e = null;");
        getJdbcTypeBaseMethod.addBodyLine("if ((e = " + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Field" + ".transfer(field)) != null)");
        getJdbcTypeBaseMethod.addBodyLine("{");
        getJdbcTypeBaseMethod.addBodyLine("return e.getJdbcType();");
        getJdbcTypeBaseMethod.addBodyLine("}");
        getJdbcTypeBaseMethod.addBodyLine("throw new IllegalFieldException(\" Can't find the jdbc-type from the java-field : \" + field);");
        beanClass.addMethod(getJdbcTypeBaseMethod);
        
        // existsFieldMethod方法
        Method existsFieldMethod = new Method("existsField");
        existsFieldMethod.addJavaDocLine("/**");
        existsFieldMethod.addJavaDocLine(" * 判断是否存在某个字段");
        existsFieldMethod.addJavaDocLine(" */");
        existsFieldMethod.setFinal(false);
        existsFieldMethod.setStatic(false);
        existsFieldMethod.setDefault(true);
        existsFieldMethod.addParameter(new Parameter(new FullyQualifiedJavaType("String"), "field"));
        existsFieldMethod.setReturnType(new FullyQualifiedJavaType("boolean"));
        existsFieldMethod.addBodyLine("return " + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Field" + ".transfer(field) != null;");
        beanClass.addMethod(existsFieldMethod);
        
        // fieldList方法
        Method getAllFieldListMethod = new Method("fieldList");
        getAllFieldListMethod.addJavaDocLine("/**");
        getAllFieldListMethod.addJavaDocLine(" * 返回所有字段列表");
        getAllFieldListMethod.addJavaDocLine(" */");
        getAllFieldListMethod.setFinal(false);
        getAllFieldListMethod.setStatic(false);
        getAllFieldListMethod.setDefault(true);
        getAllFieldListMethod.setReturnType(new FullyQualifiedJavaType(List.class.getName() + "<String>"));
        getAllFieldListMethod.addBodyLine("return " + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Field.getFieldList();");
        beanClass.addMethod(getAllFieldListMethod);
        
        // columnList方法
        Method getAllColumnListMethod = new Method("columnList");
        getAllColumnListMethod.addJavaDocLine("/**");
        getAllColumnListMethod.addJavaDocLine(" * 返回所有字段列表");
        getAllColumnListMethod.addJavaDocLine(" */");
        getAllColumnListMethod.setFinal(false);
        getAllColumnListMethod.setStatic(false);
        getAllColumnListMethod.setDefault(true);
        getAllColumnListMethod.setReturnType(new FullyQualifiedJavaType(List.class.getName() + "<String>"));
        getAllColumnListMethod.addBodyLine("return " + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Field.getColumnList();");
        beanClass.addMethod(getAllColumnListMethod);
        
        // table方法
        Method getTableNameMethod = new Method("table");
        getTableNameMethod.addJavaDocLine("/**");
        getTableNameMethod.addJavaDocLine(" * 获取数据库表名");
        getTableNameMethod.addJavaDocLine(" */");
        getTableNameMethod.setFinal(false);
        getTableNameMethod.setStatic(false);
        getTableNameMethod.setDefault(true);
        getTableNameMethod.setReturnType(new FullyQualifiedJavaType("String"));
        getTableNameMethod.addBodyLine("return " + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Field.TABLE;");
        beanClass.addMethod(getTableNameMethod);
        
        // entity方法
        Method getEntityMethod = new Method("entity");
        getEntityMethod.addJavaDocLine("/**");
        getEntityMethod.addJavaDocLine(" * 获取数据库表名");
        getEntityMethod.addJavaDocLine(" */");
        getEntityMethod.setFinal(false);
        getEntityMethod.setStatic(false);
        getEntityMethod.setDefault(true);
        getEntityMethod.setReturnType(new FullyQualifiedJavaType("String"));
        getEntityMethod.addBodyLine("return " + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Field.ENTITY;");
        beanClass.addMethod(getEntityMethod);
        
        return new GeneratedJavaFile(beanClass, getTargetProjectJavaSourceFolder(), "utf-8", new DefaultJavaFormatter());
    }
    
    /**
     * 创建实体对应的查询Bean
     * <p>Title         : createServiceInterface lilinhai 2018年2月5日 下午2:36:13</p>
     * @param introspectedTable
     * @return 
     * GeneratedJavaFile 
     */
    private GeneratedJavaFile createEntityQueryBean(IntrospectedTable introspectedTable)
    {
        TopLevelClass beanClass = new TopLevelClass(getTargetPackae("query") + "." + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Query");

        // 将接口访问权限设置为public
        beanClass.setVisibility(JavaVisibility.PUBLIC);
        
        // 添加需要依赖的类
        beanClass.addImportedType(CoreClassImportConstant.BASE_QUERY_CLASS);
        beanClass.addImportedType(new FullyQualifiedJavaType(getTargetPackae("helper") + "." + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Helper"));
        
        // 添加范型继承关系BaseService
        if (introspectedTable.getPrimaryKeyColumns() == null || introspectedTable.getPrimaryKeyColumns().isEmpty())
        {
            throw new GeneratorException(introspectedTable.getBaseRecordType() + " 该表没有设置主键，请设置！");
        }
        
        // 添加继承关系
        beanClass.setSuperClass("BaseQuery");
        beanClass.addSuperInterface(new FullyQualifiedJavaType(getTargetPackae("helper") + "." + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Helper"));

        // 添加类注释
        beanClass.addJavaDocLine("/**");
        beanClass.addJavaDocLine(" * <pre>"); //$NON-NLS-1$
        beanClass.addJavaDocLine(" *    针对该实体的QueryBean封装，用于接受前端的组合查询请求参数封装。");
        beanClass.addJavaDocLine(" * ClassName: " + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Query");
        beanClass.addJavaDocLine(" * @author nature-j2ee-code-generator");
        beanClass.addJavaDocLine(" * @version 1.0");
        beanClass.addJavaDocLine(" * </pre>"); //$NON-NLS-1$
        beanClass.addJavaDocLine(" */");

        CodeGeneratorUtils.generateToString(introspectedTable, beanClass);
        
        Field field = new Field();
        field.setFinal(true);
        field.setInitializationString("1L"); //$NON-NLS-1$
        field.setName("serialVersionUID"); //$NON-NLS-1$
        field.setStatic(true);
        field.setType(new FullyQualifiedJavaType("long")); //$NON-NLS-1$
        field.setVisibility(JavaVisibility.PRIVATE);
        field.addJavaDocLine("\n"); //$NON-NLS-1$
        field.addJavaDocLine("/**"); //$NON-NLS-1$
        field.addJavaDocLine(" * long serialVersionUID 序列化版本ID");
        field.addJavaDocLine(" */"); //$NON-NLS-1$
        beanClass.addField(field);
        Collections.sort(beanClass.getFields(), new Comparator<Field>()
        {
            public int compare(Field f1, Field f2)
            {
                if (f1.isStatic() && !f2.isStatic())
                {
                    return -1;
                }
                else if (!f1.isStatic() && f2.isStatic())
                {
                    return 1;
                }
                else if (f1.isStatic() == f2.isStatic())
                {
                    return 0;
                }
                return 0;
            }
        });
        
        return new GeneratedJavaFile(beanClass, getTargetProjectJavaSourceFolder(), "utf-8", new DefaultJavaFormatter());
    }
    
    /**
     * <p>Title         : createEntityQueryBuilder lilinhai 2018年5月20日 下午5:35:33</p>
     * <p>Description   : <pre>TODO(这里用一句话描述这个方法的作用)</pre></p>
     * @param introspectedTable
     * @return 
     * GeneratedJavaFile 
     */ 
    private GeneratedJavaFile createEntityQueryBuilder(IntrospectedTable introspectedTable)
    {
        TopLevelClass beanClass = new TopLevelClass(getTargetPackae("builder.querybuilder") + "." + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "QueryBuilder");

        // 将接口访问权限设置为public
        beanClass.setVisibility(JavaVisibility.PUBLIC);
        
        // 添加需要依赖的类
        beanClass.addImportedType(new FullyQualifiedJavaType(BaseQueryBuilder.class.getName()));
        beanClass.addImportedType(getTargetPackae("builder.selectbuilder") + "." + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "SelectBuilder");
        beanClass.addImportedType(getTargetPackae("builder.wherebuilder") + "." + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "WhereBuilder");
        beanClass.addImportedType(getTargetPackae("builder.orderbuilder") + "." + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "OrderBuilder");
        beanClass.addImportedType(getTargetPackae("query") + "." + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Query");
        
        // 添加继承关系
        beanClass.setSuperClass(new FullyQualifiedJavaType("BaseQueryBuilder<"+introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "SelectBuilder, "
                +introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "WhereBuilder, "+introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "OrderBuilder, "
                +introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Query>"));
        
        // 添加范型继承关系BaseService
        if (introspectedTable.getPrimaryKeyColumns() == null || introspectedTable.getPrimaryKeyColumns().isEmpty())
        {
            throw new GeneratorException(introspectedTable.getBaseRecordType() + " 该表没有设置主键，请设置！");
        }
        
        // 添加初始化对象的构造函数
        Method queryConstructorMethod = new Method(introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "QueryBuilder");
        queryConstructorMethod.setConstructor(true);
        queryConstructorMethod.setVisibility(JavaVisibility.PUBLIC);
        queryConstructorMethod.setFinal(false);
        queryConstructorMethod.setStatic(false);
        queryConstructorMethod.addBodyLine("selectBuilder = new "+introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "SelectBuilder();");
        queryConstructorMethod.addBodyLine("whereBuilder = new "+introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "WhereBuilder();");
        queryConstructorMethod.addBodyLine("orderBuilder = new "+introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "OrderBuilder();");
        queryConstructorMethod.addBodyLine("query = new "+introspectedTable.getFullyQualifiedTable().getDomainObjectName()+"Query();");
        beanClass.addMethod(queryConstructorMethod);
        
        // 添加类注释
        beanClass.addJavaDocLine("/**");
        beanClass.addJavaDocLine(" * <pre>"); //$NON-NLS-1$
        beanClass.addJavaDocLine(" *    针对该实体的QueryBuilder封装，用于针对代表（实体）的查询对象构建。");
        beanClass.addJavaDocLine(" * ClassName: " + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "QueryBuilder");
        beanClass.addJavaDocLine(" * @author nature-j2ee-code-generator");
        beanClass.addJavaDocLine(" * @version 1.0");
        beanClass.addJavaDocLine(" * </pre>"); //$NON-NLS-1$
        beanClass.addJavaDocLine(" */");
        return new GeneratedJavaFile(beanClass, getTargetProjectJavaSourceFolder(), "utf-8", new DefaultJavaFormatter());
    }
    
    /**
     * <p>Title         : createEntityQueryBuilder lilinhai 2018年5月20日 下午5:35:33</p>
     * <p>Description   : <pre>TODO(这里用一句话描述这个方法的作用)</pre></p>
     * @param introspectedTable
     * @return 
     * GeneratedJavaFile 
     */ 
    private GeneratedJavaFile createEntitySelectBuilder(IntrospectedTable introspectedTable)
    {
        TopLevelClass beanClass = new TopLevelClass(getTargetPackae("builder.selectbuilder") + "." + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "SelectBuilder");

        // 将接口访问权限设置为public
        beanClass.setVisibility(JavaVisibility.PUBLIC);
        
        // 添加需要依赖的类
        beanClass.addImportedType(BaseSelectBuilder.class.getName());
        beanClass.addImportedType(new FullyQualifiedJavaType(getTargetPackae("field") + "." + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Field"));
        
        // 添加继承关系
        beanClass.setSuperClass(new FullyQualifiedJavaType("BaseSelectBuilder<"+introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "SelectBuilder>"));
        
        // 添加范型继承关系BaseService
        if (introspectedTable.getPrimaryKeyColumns() == null || introspectedTable.getPrimaryKeyColumns().isEmpty())
        {
            throw new GeneratorException(introspectedTable.getBaseRecordType() + " 该表没有设置主键，请设置！");
        }
        
        // 添加类注释
        beanClass.addJavaDocLine("/**");
        beanClass.addJavaDocLine(" * <pre>"); //$NON-NLS-1$
        beanClass.addJavaDocLine(" *    针对该实体的字段选择器封装，用于构建查询条件的字段返回选择。");
        beanClass.addJavaDocLine(" * ClassName: " + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "SelectBuilder");
        beanClass.addJavaDocLine(" * @author nature-j2ee-code-generator");
        beanClass.addJavaDocLine(" * @version 1.0");
        beanClass.addJavaDocLine(" * </pre>"); //$NON-NLS-1$
        beanClass.addJavaDocLine(" */");
        
        String methodName = null;
        Method _method = null;
        for (IntrospectedColumn introspectedColumn : introspectedTable.getAllColumns())
        {
            // 公共属性，用父类的方法，不必重新生成
            if (FIELD_TO_BE_REMOVED_LIST.contains(introspectedColumn.getJavaProperty()))
            {
                continue;
            }
            String enumFieldName = introspectedColumn.getActualColumnName().toUpperCase(Locale.ENGLISH);
            methodName = introspectedColumn.getJavaProperty();
            _method = new Method(methodName);
            _method.addJavaDocLine("/**");
            _method.addJavaDocLine(" * 检索：" + introspectedColumn.getActualColumnName() + "("+introspectedColumn.getRemarks()+")");
            _method.addJavaDocLine(" */");
            _method.setFinal(false);
            _method.setStatic(false);
            _method.setVisibility(JavaVisibility.PUBLIC);
            _method.setReturnType(new FullyQualifiedJavaType(introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "SelectBuilder"));
            _method.addBodyLine("select(" + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Field" + "."+enumFieldName+");");
            _method.addBodyLine("return this;");
            beanClass.addMethod(_method);
        }
        
        return new GeneratedJavaFile(beanClass, getTargetProjectJavaSourceFolder(), "utf-8", new DefaultJavaFormatter());
    }
    
    /**
     * <p>Title         : createEntityQueryBuilder lilinhai 2018年5月20日 下午5:35:33</p>
     * <p>Description   : <pre>TODO(这里用一句话描述这个方法的作用)</pre></p>
     * @param introspectedTable
     * @return 
     * GeneratedJavaFile 
     */ 
    private GeneratedJavaFile createEntityWhereBuilder(IntrospectedTable introspectedTable)
    {
        TopLevelClass beanClass = new TopLevelClass(getTargetPackae("builder.wherebuilder") + "." + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "WhereBuilder");

        // 将接口访问权限设置为public
        beanClass.setVisibility(JavaVisibility.PUBLIC);
        
        // 添加需要依赖的类
        beanClass.addImportedType(BaseWhereBuilder.class.getName());
        beanClass.addImportedType(FieldConditionBuilder.class.getName());
        beanClass.addImportedType(new FullyQualifiedJavaType(getTargetPackae("field") + "." + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Field"));
        
        // 添加继承关系
        beanClass.setSuperClass(new FullyQualifiedJavaType("BaseWhereBuilder<"+introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "WhereBuilder>"));
        
        // 添加范型继承关系BaseService
        if (introspectedTable.getPrimaryKeyColumns() == null || introspectedTable.getPrimaryKeyColumns().isEmpty())
        {
            throw new GeneratorException(introspectedTable.getBaseRecordType() + " 该表没有设置主键，请设置！");
        }
        
        // 添加类注释
        beanClass.addJavaDocLine("/**");
        beanClass.addJavaDocLine(" * <pre>"); //$NON-NLS-1$
        beanClass.addJavaDocLine(" *    针对该实体的WhereBuilder封装，构建指定实体的查询条件。");
        beanClass.addJavaDocLine(" * ClassName: " + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "WhereBuilder");
        beanClass.addJavaDocLine(" * @author nature-j2ee-code-generator");
        beanClass.addJavaDocLine(" * @version 1.0");
        beanClass.addJavaDocLine(" * </pre>"); //$NON-NLS-1$
        beanClass.addJavaDocLine(" */");

        
        String methodName = null;
        Method _method = null;
        for (IntrospectedColumn introspectedColumn : introspectedTable.getAllColumns())
        {
            // 公共属性，用父类的方法，不必重新生成
            if (FIELD_TO_BE_REMOVED_LIST.contains(introspectedColumn.getJavaProperty()))
            {
                continue;
            }
            if (introspectedColumn.getFullyQualifiedJavaType().getFullyQualifiedNameWithoutTypeParameters().equals(Date.class.getName()))
            {
                beanClass.addImportedType(Date.class.getName());
            }
            String enumFieldName = introspectedColumn.getActualColumnName().toUpperCase(Locale.ENGLISH);
            
            // 非blob字段，都允许排序和生成where条件
            if (!introspectedColumn.isBLOBColumn())
            {
                methodName = introspectedColumn.getJavaProperty();
                _method = new Method(methodName);
                _method.addJavaDocLine("/**");
                _method.addJavaDocLine(" * 字段查询条件设置（支持：等于，大于，小于，大于等于，小于等于，in，notIn，isNull，isNotNull等操作符）：" + introspectedColumn.getActualColumnName() + "("+introspectedColumn.getRemarks()+")");
                _method.addJavaDocLine(" */");
                _method.setFinal(false);
                _method.setStatic(false);
                _method.setVisibility(JavaVisibility.PUBLIC);
                _method.setReturnType(new FullyQualifiedJavaType("FieldConditionBuilder<"+introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "WhereBuilder, "+introspectedColumn.getFullyQualifiedJavaType()+">"));
                _method.addBodyLine("return build("+introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Field" + "."+enumFieldName+", "+introspectedColumn.getFullyQualifiedJavaType().getShortName()+".class);");
                beanClass.addMethod(_method);
            }
            
            
        }
        
        return new GeneratedJavaFile(beanClass, getTargetProjectJavaSourceFolder(), "utf-8", new DefaultJavaFormatter());
    }
    
    /**
     * <p>Title         : createEntityQueryBuilder lilinhai 2018年5月20日 下午5:35:33</p>
     * <p>Description   : <pre>TODO(这里用一句话描述这个方法的作用)</pre></p>
     * @param introspectedTable
     * @return 
     * GeneratedJavaFile 
     */ 
    private GeneratedJavaFile createEntityOrderBuilder(IntrospectedTable introspectedTable)
    {
        TopLevelClass beanClass = new TopLevelClass(getTargetPackae("builder.orderbuilder") + "." + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "OrderBuilder");

        // 将接口访问权限设置为public
        beanClass.setVisibility(JavaVisibility.PUBLIC);
        
        // 添加需要依赖的类
        beanClass.addImportedType(BaseOrderBuilder.class.getName());
        beanClass.addImportedType(OrderByFieldBuilder.class.getName());
        beanClass.addImportedType(new FullyQualifiedJavaType(getTargetPackae("field") + "." + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Field"));
        
        // 添加继承关系
        beanClass.setSuperClass(new FullyQualifiedJavaType("BaseOrderBuilder<"+introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "OrderBuilder>"));
        
        // 添加范型继承关系BaseService
        if (introspectedTable.getPrimaryKeyColumns() == null || introspectedTable.getPrimaryKeyColumns().isEmpty())
        {
            throw new GeneratorException(introspectedTable.getBaseRecordType() + " 该表没有设置主键，请设置！");
        }
        
        // 添加类注释
        beanClass.addJavaDocLine("/**");
        beanClass.addJavaDocLine(" * <pre>"); //$NON-NLS-1$
        beanClass.addJavaDocLine(" *    针对该实体的排序器封装，对查询后结果的排序参数构建。");
        beanClass.addJavaDocLine(" * ClassName: " + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "OrderByBuilder");
        beanClass.addJavaDocLine(" * @author nature-j2ee-code-generator");
        beanClass.addJavaDocLine(" * @version 1.0");
        beanClass.addJavaDocLine(" * </pre>"); //$NON-NLS-1$
        beanClass.addJavaDocLine(" */");

        
        String methodName = null;
        Method _method = null;
        for (IntrospectedColumn introspectedColumn : introspectedTable.getAllColumns())
        {
            // 公共属性，用父类的方法，不必重新生成
            if (FIELD_TO_BE_REMOVED_LIST.contains(introspectedColumn.getJavaProperty()))
            {
                continue;
            }
            String enumFieldName = introspectedColumn.getActualColumnName().toUpperCase(Locale.ENGLISH);
            
            // 非blob字段，都允许排序和生成where条件
            if (!introspectedColumn.isBLOBColumn())
            {
                methodName = introspectedColumn.getJavaProperty();
                _method = new Method(methodName);
                _method.addJavaDocLine("/**");
                _method.addJavaDocLine(" * 排序：" + introspectedColumn.getActualColumnName() + "("+introspectedColumn.getRemarks()+")");
                _method.addJavaDocLine(" */");
                _method.setFinal(false);
                _method.setStatic(false);
                _method.setVisibility(JavaVisibility.PUBLIC);
                _method.setReturnType(new FullyQualifiedJavaType("OrderByFieldBuilder<"+introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "OrderBuilder>"));
                _method.addBodyLine("return orderBy("+introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Field" + "."+enumFieldName+");");
                beanClass.addMethod(_method);
            }
        }
        
        return new GeneratedJavaFile(beanClass, getTargetProjectJavaSourceFolder(), "utf-8", new DefaultJavaFormatter());
    }
}
