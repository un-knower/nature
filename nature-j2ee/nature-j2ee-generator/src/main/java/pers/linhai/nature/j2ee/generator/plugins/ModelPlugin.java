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
import pers.linhai.nature.j2ee.core.model.ConditionBuilder;
import pers.linhai.nature.j2ee.core.model.DateJsonDeserializer;
import pers.linhai.nature.j2ee.core.model.EntityBean;
import pers.linhai.nature.j2ee.core.model.ModelField;
import pers.linhai.nature.j2ee.core.model.ModelHelper;
import pers.linhai.nature.j2ee.core.model.QueryBuilder;
import pers.linhai.nature.j2ee.core.model.SortField;
import pers.linhai.nature.j2ee.core.model.enumer.Direction;
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
import pers.linhai.nature.utils.NamingUtils;


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
            topLevelClass.addImportedType(JsonDeserialize.class.getName());
            topLevelClass.addImportedType(DateJsonDeserializer.class.getName());
        }
        
        // 判断是否存在Date类型（包含createTime和updateTime之内外）的字段
        for (IntrospectedColumn introspectedColumn : introspectedTable.getAllColumns())
        {
            if (introspectedColumn.getFullyQualifiedJavaType().getFullyQualifiedNameWithoutTypeParameters().equals(Date.class.getName()))
            {
                topLevelClass.addImportedType(Date.class.getName());
                break;
            }
        }

        String keyType = introspectedTable.getPrimaryKeyColumns().get(0).getFullyQualifiedJavaType().getShortName();
        topLevelClass.setSuperClass(new FullyQualifiedJavaType("BaseEntity<" + keyType + ">"));
        topLevelClass.addSuperInterface(new FullyQualifiedJavaType(getTargetPackae("helper") + "." + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Helper"));

        // 添加初始化对象的构造函数
        Method queryConstructorMethod = new Method(introspectedTable.getFullyQualifiedTable().getDomainObjectName());
        queryConstructorMethod.setConstructor(true);
        queryConstructorMethod.setVisibility(JavaVisibility.PUBLIC);
        queryConstructorMethod.setFinal(false);
        queryConstructorMethod.setStatic(false);
        queryConstructorMethod.addBodyLine("super("+introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Field.TABLE_NAME);");
        topLevelClass.addMethod(queryConstructorMethod);
        
        Method initializeMethod = new Method("initialize");
        initializeMethod.setConstructor(false);
        initializeMethod.setVisibility(JavaVisibility.DEFAULT);
        initializeMethod.setFinal(false);
        initializeMethod.setStatic(false);
        initializeMethod.addParameter(new Parameter(new FullyQualifiedJavaType(EntityBean.class.getName()), "entityBean"));
        for (IntrospectedColumn introspectedColumn : introspectedTable.getAllColumns())
        {
            String enumFieldName = introspectedColumn.getActualColumnName().toUpperCase(Locale.ENGLISH);
            initializeMethod.addBodyLine("this." + introspectedColumn.getJavaProperty() + " = ("+introspectedColumn.getFullyQualifiedJavaType().getShortName()+")entityBean.get("+introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Field." + enumFieldName + ".getJavaField());");
        }
        topLevelClass.addMethod(initializeMethod);
        
        Method toEntityBeanMethod = new Method("toEntityBean");
        toEntityBeanMethod.setConstructor(false);
        toEntityBeanMethod.setVisibility(JavaVisibility.PUBLIC);
        toEntityBeanMethod.setFinal(false);
        toEntityBeanMethod.setStatic(false);
        toEntityBeanMethod.setReturnType(new FullyQualifiedJavaType(EntityBean.class.getName()));
        toEntityBeanMethod.addBodyLine("EntityBean entityBean = new EntityBean();");
        toEntityBeanMethod.addBodyLine("entityBean.setInited(true);");
        for (IntrospectedColumn introspectedColumn : introspectedTable.getAllColumns())
        {
            String enumFieldName = introspectedColumn.getActualColumnName().toUpperCase(Locale.ENGLISH);
            toEntityBeanMethod.addBodyLine("entityBean.put(" + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Field." + enumFieldName + ".getJavaField(), this." + introspectedColumn.getJavaProperty() + ");");
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
        fieldEnumeration.addImportedType(new FullyQualifiedJavaType(NamingUtils.class.getName()));
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
        tableFieldList.setName("TABLE_FIELD_LIST");
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
        javaFieldList.setName("JAVA_FIELD_LIST");
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
        tableNameField.setName("TABLE_NAME");
        tableNameField.setStatic(true);
        tableNameField.setType(new FullyQualifiedJavaType(String.class.getName()));
        tableNameField.setInitializationString("\""+introspectedTable.getFullyQualifiedTableNameAtRuntime()+"\"");
        tableNameField.setVisibility(JavaVisibility.PUBLIC);
        fieldEnumeration.addField(tableNameField);
        
        // 添加static块
        InitializationBlock initializationBlock = new InitializationBlock();
        initializationBlock.setStatic(true);
        initializationBlock.addBodyLine("for ("+introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Field"+" field : values())");
        initializationBlock.addBodyLine("{");
        initializationBlock.addBodyLine("MAP.put(field.getJavaField(), field);");
        initializationBlock.addBodyLine("MAP.put(field.getTableField(), field);");
        initializationBlock.addBodyLine("TABLE_FIELD_LIST.add(field.getTableField());");
        initializationBlock.addBodyLine("JAVA_FIELD_LIST.add(field.getJavaField());");
        initializationBlock.addBodyLine("}");
        fieldEnumeration.addInitializationBlock(initializationBlock);
        
        Field javaField = new Field();
        javaField.addJavaDocLine("/**");
        javaField.addJavaDocLine(" * 对应的java实体字段名");
        javaField.addJavaDocLine(" */");
        javaField.setFinal(false);
        javaField.setName("javaField");
        javaField.setStatic(false);
        javaField.setType(new FullyQualifiedJavaType("String"));
        javaField.setVisibility(JavaVisibility.PRIVATE);
        fieldEnumeration.addField(javaField);
        
        Field tableField = new Field();
        tableField.addJavaDocLine("/**");
        tableField.addJavaDocLine(" * 对应的table数据库表字段名");
        tableField.addJavaDocLine(" */");
        tableField.setFinal(false);
        tableField.setName("tableField");
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
        constructorMethod.addBodyLine("this.tableField = name().toLowerCase();");
        constructorMethod.addBodyLine("this.javaField = NamingUtils.getCamelCaseString(tableField, false);");
        constructorMethod.addBodyLine("this.jdbcType = jdbcType;");
        constructorMethod.setVisibility(JavaVisibility.DEFAULT);
        constructorMethod.setFinal(false);
        constructorMethod.setStatic(false);
        fieldEnumeration.addMethod(constructorMethod);
        
        // getJavaField方法
        Method getJavaFieldMethod = new Method("getJavaField");
        getJavaFieldMethod.setFinal(false);
        getJavaFieldMethod.setStatic(false);
        getJavaFieldMethod.setVisibility(JavaVisibility.PUBLIC);
        getJavaFieldMethod.setReturnType(new FullyQualifiedJavaType("String"));
        getJavaFieldMethod.addBodyLine("return javaField;");
        fieldEnumeration.addMethod(getJavaFieldMethod);
        
        // getTableField方法
        Method getTableFieldMethod = new Method("getTableField");
        getTableFieldMethod.setFinal(false);
        getTableFieldMethod.setStatic(false);
        getTableFieldMethod.setVisibility(JavaVisibility.PUBLIC);
        getTableFieldMethod.setReturnType(new FullyQualifiedJavaType("String"));
        getTableFieldMethod.addBodyLine("return tableField;");
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
        transferMethod.addParameter(new Parameter(new FullyQualifiedJavaType("String"), "javaField"));
        transferMethod.setReturnType(new FullyQualifiedJavaType(introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Field"));
        transferMethod.addBodyLine("return MAP.get(javaField);");
        fieldEnumeration.addMethod(transferMethod);
        
        // getTableFieldList方法
        Method getTableFieldListMethod = new Method("getTableFieldList");
        getTableFieldListMethod.setFinal(false);
        getTableFieldListMethod.setStatic(true);
        getTableFieldListMethod.setVisibility(JavaVisibility.PUBLIC);
        getTableFieldListMethod.setReturnType(new FullyQualifiedJavaType(List.class.getName() + "<String>"));
        getTableFieldListMethod.addBodyLine("return TABLE_FIELD_LIST;");
        fieldEnumeration.addMethod(getTableFieldListMethod);
        
        // getTableFieldList方法
        Method getJavaFieldListMethod = new Method("getJavaFieldList");
        getJavaFieldListMethod.setFinal(false);
        getJavaFieldListMethod.setStatic(true);
        getJavaFieldListMethod.setVisibility(JavaVisibility.PUBLIC);
        getJavaFieldListMethod.setReturnType(new FullyQualifiedJavaType(List.class.getName() + "<String>"));
        getJavaFieldListMethod.addBodyLine("return JAVA_FIELD_LIST;");
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
        validFieldMethod.addParameter(new Parameter(new FullyQualifiedJavaType("String"), "javaField"));
        validFieldMethod.addBodyLine("// 如果枚举为空，说明是非法字段");
        validFieldMethod.addBodyLine("if (" + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Field" + ".transfer(javaField) == null)");
        validFieldMethod.addBodyLine("{");
        validFieldMethod.addBodyLine("throw new IllegalFieldException(\" There is an illegal field : \" + javaField);");
        validFieldMethod.addBodyLine("}");
        beanClass.addMethod(validFieldMethod);
        
        // getTableField方法
        Method getTableFieldBaseMethod = new Method("getTableField");
        getTableFieldBaseMethod.addJavaDocLine("/**");
        getTableFieldBaseMethod.addJavaDocLine(" * 获取java字段名对应的数据库表字段名");
        getTableFieldBaseMethod.addJavaDocLine(" */");
        getTableFieldBaseMethod.setFinal(false);
        getTableFieldBaseMethod.setStatic(false);
        getTableFieldBaseMethod.setDefault(true);
        getTableFieldBaseMethod.addParameter(new Parameter(new FullyQualifiedJavaType("String"), "javaField"));
        getTableFieldBaseMethod.setReturnType(new FullyQualifiedJavaType("String"));
        getTableFieldBaseMethod.addBodyLine(introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Field e = null;");
        getTableFieldBaseMethod.addBodyLine("if ((e = " + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Field" + ".transfer(javaField)) != null)");
        getTableFieldBaseMethod.addBodyLine("{");
        getTableFieldBaseMethod.addBodyLine("return e.getTableField();");
        getTableFieldBaseMethod.addBodyLine("}");
        getTableFieldBaseMethod.addBodyLine("throw new IllegalFieldException(\" Can't find the table-field from the java-field : \" + javaField);");
        beanClass.addMethod(getTableFieldBaseMethod);
        
        // getJdbcType方法
        Method getJdbcTypeBaseMethod = new Method("getJdbcType");
        getJdbcTypeBaseMethod.addJavaDocLine("/**");
        getJdbcTypeBaseMethod.addJavaDocLine(" * 获取改该字段对应的JDBC类型");
        getJdbcTypeBaseMethod.addJavaDocLine(" */");
        getJdbcTypeBaseMethod.setFinal(false);
        getJdbcTypeBaseMethod.setStatic(false);
        getJdbcTypeBaseMethod.setDefault(true);
        getJdbcTypeBaseMethod.addParameter(new Parameter(new FullyQualifiedJavaType("String"), "javaField"));
        getJdbcTypeBaseMethod.setReturnType(new FullyQualifiedJavaType("String"));
        getJdbcTypeBaseMethod.addBodyLine(introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Field e = null;");
        getJdbcTypeBaseMethod.addBodyLine("if ((e = " + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Field" + ".transfer(javaField)) != null)");
        getJdbcTypeBaseMethod.addBodyLine("{");
        getJdbcTypeBaseMethod.addBodyLine("return e.getJdbcType();");
        getJdbcTypeBaseMethod.addBodyLine("}");
        getJdbcTypeBaseMethod.addBodyLine("throw new IllegalFieldException(\" Can't find the jdbc-type from the java-field : \" + javaField);");
        beanClass.addMethod(getJdbcTypeBaseMethod);
        
        // existsFieldMethod方法
        Method existsFieldMethod = new Method("existsField");
        existsFieldMethod.addJavaDocLine("/**");
        existsFieldMethod.addJavaDocLine(" * 判断是否存在某个字段");
        existsFieldMethod.addJavaDocLine(" */");
        existsFieldMethod.setFinal(false);
        existsFieldMethod.setStatic(false);
        existsFieldMethod.setDefault(true);
        existsFieldMethod.addParameter(new Parameter(new FullyQualifiedJavaType("String"), "fieldName"));
        existsFieldMethod.setReturnType(new FullyQualifiedJavaType("boolean"));
        existsFieldMethod.addBodyLine("return " + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Field" + ".transfer(fieldName) != null;");
        beanClass.addMethod(existsFieldMethod);
        
        // getAllFieldList方法
        Method getAllFieldListMethod = new Method("getAllFieldList");
        getAllFieldListMethod.addJavaDocLine("/**");
        getAllFieldListMethod.addJavaDocLine(" * 返回所有字段列表");
        getAllFieldListMethod.addJavaDocLine(" */");
        getAllFieldListMethod.setFinal(false);
        getAllFieldListMethod.setStatic(false);
        getAllFieldListMethod.setDefault(true);
        getAllFieldListMethod.setReturnType(new FullyQualifiedJavaType(List.class.getName() + "<String>"));
        getAllFieldListMethod.addBodyLine("return " + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Field.getTableFieldList();");
        beanClass.addMethod(getAllFieldListMethod);
        
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
        beanClass.addImportedType(new FullyQualifiedJavaType(getTargetPackae("field") + "." + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Field"));
        
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
        
        // 添加初始化对象的构造函数
        Method queryConstructorMethod = new Method(introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Query");
        queryConstructorMethod.setConstructor(true);
        queryConstructorMethod.setVisibility(JavaVisibility.PUBLIC);
        queryConstructorMethod.setFinal(false);
        queryConstructorMethod.setStatic(false);
        queryConstructorMethod.addBodyLine("super("+introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Field.TABLE_NAME);");
        beanClass.addMethod(queryConstructorMethod);
        
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
        TopLevelClass beanClass = new TopLevelClass(getTargetPackae("querybuilder") + "." + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "QueryBuilder");

        // 将接口访问权限设置为public
        beanClass.setVisibility(JavaVisibility.PUBLIC);
        
        // 添加需要依赖的类
        beanClass.addImportedType(new FullyQualifiedJavaType(QueryBuilder.class.getName()));
        beanClass.addImportedType(SortField.class.getName());
        beanClass.addImportedType(Direction.class.getName());
        beanClass.addImportedType(ConditionBuilder.class.getName());
        beanClass.addImportedType(List.class.getName());
        beanClass.addImportedType(ArrayList.class.getName());
        beanClass.addImportedType(Date.class.getName());
        beanClass.addImportedType(getTargetPackae("query") + "." + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Query");
        beanClass.addImportedType(new FullyQualifiedJavaType(getTargetPackae("field") + "." + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Field"));
        
        // 添加范型继承关系BaseService
        if (introspectedTable.getPrimaryKeyColumns() == null || introspectedTable.getPrimaryKeyColumns().isEmpty())
        {
            throw new GeneratorException(introspectedTable.getBaseRecordType() + " 该表没有设置主键，请设置！");
        }
        
        // 添加继承关系
        beanClass.setSuperClass(QueryBuilder.class.getName());

        // 添加类注释
        beanClass.addJavaDocLine("/**");
        beanClass.addJavaDocLine(" * <pre>"); //$NON-NLS-1$
        beanClass.addJavaDocLine(" *    针对该实体的QueryBean封装，用于接受前端的组合查询请求参数封装。");
        beanClass.addJavaDocLine(" * ClassName: " + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "QueryBuilder");
        beanClass.addJavaDocLine(" * @author nature-j2ee-code-generator");
        beanClass.addJavaDocLine(" * @version 1.0");
        beanClass.addJavaDocLine(" * </pre>"); //$NON-NLS-1$
        beanClass.addJavaDocLine(" */");

        
        String methodName = "where";
        Method _method = new Method(methodName);
        _method.addJavaDocLine("/**");
        _method.addJavaDocLine(" * 调用where函数，意味着开始设置查询条件！");
        _method.addJavaDocLine(" */");
        _method.setFinal(false);
        _method.setStatic(false);
        _method.setVisibility(JavaVisibility.PUBLIC);
        _method.setReturnType(new FullyQualifiedJavaType(getTargetPackae("querybuilder") + "." + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "QueryBuilder"));
        _method.addBodyLine("_where();");
        _method.addBodyLine("return this;");
        beanClass.addMethod(_method);
        
        methodName = "and";
        _method = new Method(methodName);
        _method.addJavaDocLine("/**");
        _method.addJavaDocLine(" * 调用and函数，意味着开始设置and逻辑运算符！");
        _method.addJavaDocLine(" */");
        _method.setFinal(false);
        _method.setStatic(false);
        _method.setVisibility(JavaVisibility.PUBLIC);
        _method.setReturnType(new FullyQualifiedJavaType(getTargetPackae("querybuilder") + "." + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "QueryBuilder"));
        _method.addBodyLine("_and();");
        _method.addBodyLine("return this;");
        beanClass.addMethod(_method);
        
        methodName = "or";
        _method = new Method(methodName);
        _method.addJavaDocLine("/**");
        _method.addJavaDocLine(" * 调用or函数，意味着开始设置or逻辑运算符！");
        _method.addJavaDocLine(" */");
        _method.setFinal(false);
        _method.setStatic(false);
        _method.setVisibility(JavaVisibility.PUBLIC);
        _method.setReturnType(new FullyQualifiedJavaType(getTargetPackae("querybuilder") + "." + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "QueryBuilder"));
        _method.addBodyLine("_or();");
        _method.addBodyLine("return this;");
        beanClass.addMethod(_method);
        
        methodName = "setPage";
        _method = new Method(methodName);
        _method.addJavaDocLine("/**");
        _method.addJavaDocLine(" * 调用setPage函数，设置分页参数：第几页！");
        _method.addJavaDocLine(" */");
        _method.setFinal(false);
        _method.setStatic(false);
        _method.setVisibility(JavaVisibility.PUBLIC);
        _method.addParameter(new Parameter(new FullyQualifiedJavaType("Integer"), "page"));
        _method.setReturnType(new FullyQualifiedJavaType(getTargetPackae("querybuilder") + "." + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "QueryBuilder"));
        _method.addBodyLine("_page(page);");
        _method.addBodyLine("return this;");
        beanClass.addMethod(_method);
        
        methodName = "setSize";
        _method = new Method(methodName);
        _method.addJavaDocLine("/**");
        _method.addJavaDocLine(" * 调用setPage函数，设置分页大小：每页显示多少条！");
        _method.addJavaDocLine(" */");
        _method.setFinal(false);
        _method.setStatic(false);
        _method.setVisibility(JavaVisibility.PUBLIC);
        _method.addParameter(new Parameter(new FullyQualifiedJavaType("Integer"), "size"));
        _method.setReturnType(new FullyQualifiedJavaType(getTargetPackae("querybuilder") + "." + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "QueryBuilder"));
        _method.addBodyLine("_size(size);");
        _method.addBodyLine("return this;");
        beanClass.addMethod(_method);
        
        methodName = "build";
        _method = new Method(methodName);
        _method.addJavaDocLine("/**");
        _method.addJavaDocLine(" * 调用build函数，意味着builder构建结束！");
        _method.addJavaDocLine(" */");
        _method.setFinal(false);
        _method.setStatic(false);
        _method.setVisibility(JavaVisibility.PUBLIC);
        _method.setReturnType(new FullyQualifiedJavaType(getTargetPackae("query") + "." + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Query"));
        _method.addBodyLine(introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Query query = new " + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Query();");
        _method.addBodyLine("build(query);");
        _method.addBodyLine("return query;");
        beanClass.addMethod(_method);
        
        for (IntrospectedColumn introspectedColumn : introspectedTable.getAllColumns())
        {
            String enumFieldName = introspectedColumn.getActualColumnName().toUpperCase(Locale.ENGLISH);
            methodName = introspectedColumn.getJavaProperty("return", true);
            _method = new Method(methodName);
            _method.addJavaDocLine("/**");
            _method.addJavaDocLine(" * 检索：" + introspectedColumn.getActualColumnName() + "("+introspectedColumn.getRemarks()+")");
            _method.addJavaDocLine(" */");
            _method.setFinal(false);
            _method.setStatic(false);
            _method.setVisibility(JavaVisibility.PUBLIC);
            _method.setReturnType(new FullyQualifiedJavaType(getTargetPackae("querybuilder") + "." + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "QueryBuilder"));
            _method.addBodyLine("_returnField(" + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Field" + "."+enumFieldName+");");
            _method.addBodyLine("return this;");
            beanClass.addMethod(_method);
            
            // 非blob字段，都允许排序和生成where条件
            if (!introspectedColumn.isBLOBColumn())
            {
                methodName = introspectedColumn.getJavaProperty("orderBy", true);
                _method = new Method(methodName);
                _method.addJavaDocLine("/**");
                _method.addJavaDocLine(" * 排列：" + introspectedColumn.getActualColumnName() + "("+introspectedColumn.getRemarks()+")");
                _method.addJavaDocLine(" */");
                _method.setFinal(false);
                _method.setStatic(false);
                _method.setVisibility(JavaVisibility.PUBLIC);
                _method.addParameter(new Parameter(new FullyQualifiedJavaType(Direction.class.getName()), "direction"));
                _method.setReturnType(new FullyQualifiedJavaType(getTargetPackae("querybuilder") + "." + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "QueryBuilder"));
                _method.addBodyLine("SortField sf = new SortField();");
                _method.addBodyLine("sf.setFieldName("+introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Field" + "."+enumFieldName+".getJavaField());");
                _method.addBodyLine("sf.setDirection(direction.name());");
                _method.addBodyLine("orderBy(sf);");
                _method.addBodyLine("return this;");
                beanClass.addMethod(_method);
                
                methodName = introspectedColumn.getJavaProperty() + "Equal";
                _method = new Method(methodName);
                _method.addJavaDocLine("/**");
                _method.addJavaDocLine(" * 字段等于某个值：" + introspectedColumn.getActualColumnName() + "("+introspectedColumn.getRemarks()+")");
                _method.addJavaDocLine(" */");
                _method.setFinal(false);
                _method.setStatic(false);
                _method.setVisibility(JavaVisibility.PUBLIC);
                _method.addParameter(new Parameter(introspectedColumn.getFullyQualifiedJavaType(), introspectedColumn.getJavaProperty()));
                _method.setReturnType(new FullyQualifiedJavaType(getTargetPackae("querybuilder") + "." + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "QueryBuilder"));
                _method.addBodyLine("join(ConditionBuilder.field("+introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Field" + "."+enumFieldName+").equal("+introspectedColumn.getJavaProperty()+"));");
                _method.addBodyLine("return this;");
                beanClass.addMethod(_method);
                
                methodName = introspectedColumn.getJavaProperty() + "IsNull";
                _method = new Method(methodName);
                _method.addJavaDocLine("/**");
                _method.addJavaDocLine(" * 字段为null过滤：" + introspectedColumn.getActualColumnName() + "("+introspectedColumn.getRemarks()+")");
                _method.addJavaDocLine(" */");
                _method.setFinal(false);
                _method.setStatic(false);
                _method.setVisibility(JavaVisibility.PUBLIC);
                _method.setReturnType(new FullyQualifiedJavaType(getTargetPackae("querybuilder") + "." + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "QueryBuilder"));
                _method.addBodyLine("join(ConditionBuilder.field("+introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Field" + "."+enumFieldName+").isNull());");
                _method.addBodyLine("return this;");
                beanClass.addMethod(_method);
                
                methodName = introspectedColumn.getJavaProperty() + "IsNotNull";
                _method = new Method(methodName);
                _method.addJavaDocLine("/**");
                _method.addJavaDocLine(" * 字段不为null过滤：" + introspectedColumn.getActualColumnName() + "("+introspectedColumn.getRemarks()+")");
                _method.addJavaDocLine(" */");
                _method.setFinal(false);
                _method.setStatic(false);
                _method.setVisibility(JavaVisibility.PUBLIC);
                _method.setReturnType(new FullyQualifiedJavaType(getTargetPackae("querybuilder") + "." + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "QueryBuilder"));
                _method.addBodyLine("join(ConditionBuilder.field("+introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Field" + "."+enumFieldName+").isNotNull());");
                _method.addBodyLine("return this;");
                beanClass.addMethod(_method);
                
                // 如果不是boolean类型数据
                if (!introspectedColumn.isBitColumn())
                {
                    methodName = introspectedColumn.getJavaProperty() + "LessThan";
                    _method = new Method(methodName);
                    _method.addJavaDocLine("/**");
                    _method.addJavaDocLine(" * 字段小于某个值：" + introspectedColumn.getActualColumnName() + "("+introspectedColumn.getRemarks()+")");
                    _method.addJavaDocLine(" */");
                    _method.setFinal(false);
                    _method.setStatic(false);
                    _method.setVisibility(JavaVisibility.PUBLIC);
                    _method.addParameter(new Parameter(introspectedColumn.getFullyQualifiedJavaType(), introspectedColumn.getJavaProperty()));
                    _method.setReturnType(new FullyQualifiedJavaType(getTargetPackae("querybuilder") + "." + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "QueryBuilder"));
                    _method.addBodyLine("join(ConditionBuilder.field("+introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Field" + "."+enumFieldName+").lessThan("+introspectedColumn.getJavaProperty()+"));");
                    _method.addBodyLine("return this;");
                    beanClass.addMethod(_method);
                    
                    methodName = introspectedColumn.getJavaProperty() + "LessThanOrEqual";
                    _method = new Method(methodName);
                    _method.addJavaDocLine("/**");
                    _method.addJavaDocLine(" * 字段小于等于某个值：" + introspectedColumn.getActualColumnName() + "("+introspectedColumn.getRemarks()+")");
                    _method.addJavaDocLine(" */");
                    _method.setFinal(false);
                    _method.setStatic(false);
                    _method.setVisibility(JavaVisibility.PUBLIC);
                    _method.addParameter(new Parameter(introspectedColumn.getFullyQualifiedJavaType(), introspectedColumn.getJavaProperty()));
                    _method.setReturnType(new FullyQualifiedJavaType(getTargetPackae("querybuilder") + "." + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "QueryBuilder"));
                    _method.addBodyLine("join(ConditionBuilder.field("+introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Field" + "."+enumFieldName+").lessThanOrEqual("+introspectedColumn.getJavaProperty()+"));");
                    _method.addBodyLine("return this;");
                    beanClass.addMethod(_method);
                    
                    methodName = introspectedColumn.getJavaProperty() + "GreaterThan";
                    _method = new Method(methodName);
                    _method.addJavaDocLine("/**");
                    _method.addJavaDocLine(" * 字段大于某个值：" + introspectedColumn.getActualColumnName() + "("+introspectedColumn.getRemarks()+")");
                    _method.addJavaDocLine(" */");
                    _method.setFinal(false);
                    _method.setStatic(false);
                    _method.setVisibility(JavaVisibility.PUBLIC);
                    _method.addParameter(new Parameter(introspectedColumn.getFullyQualifiedJavaType(), introspectedColumn.getJavaProperty()));
                    _method.setReturnType(new FullyQualifiedJavaType(getTargetPackae("querybuilder") + "." + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "QueryBuilder"));
                    _method.addBodyLine("join(ConditionBuilder.field("+introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Field" + "."+enumFieldName+").greaterThan("+introspectedColumn.getJavaProperty()+"));");
                    _method.addBodyLine("return this;");
                    beanClass.addMethod(_method);
                    
                    methodName = introspectedColumn.getJavaProperty() + "GreaterThanOrEqual";
                    _method = new Method(methodName);
                    _method.addJavaDocLine("/**");
                    _method.addJavaDocLine(" * 字段大于等于某个值：" + introspectedColumn.getActualColumnName() + "("+introspectedColumn.getRemarks()+")");
                    _method.addJavaDocLine(" */");
                    _method.setFinal(false);
                    _method.setStatic(false);
                    _method.setVisibility(JavaVisibility.PUBLIC);
                    _method.addParameter(new Parameter(introspectedColumn.getFullyQualifiedJavaType(), introspectedColumn.getJavaProperty()));
                    _method.setReturnType(new FullyQualifiedJavaType(getTargetPackae("querybuilder") + "." + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "QueryBuilder"));
                    _method.addBodyLine("join(ConditionBuilder.field("+introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Field" + "."+enumFieldName+").greaterThanOrEqual("+introspectedColumn.getJavaProperty()+"));");
                    _method.addBodyLine("return this;");
                    beanClass.addMethod(_method);
                    
                    methodName = introspectedColumn.getJavaProperty() + "In";
                    _method = new Method(methodName);
                    _method.addJavaDocLine("/**");
                    _method.addJavaDocLine(" * 字段在某个范围内in：" + introspectedColumn.getActualColumnName() + "("+introspectedColumn.getRemarks()+")");
                    _method.addJavaDocLine(" */");
                    _method.setFinal(false);
                    _method.setStatic(false);
                    _method.setVisibility(JavaVisibility.PUBLIC);
                    _method.addParameter(new Parameter(introspectedColumn.getFullyQualifiedJavaType(), introspectedColumn.getJavaProperty()+"Arr", true));
                    _method.setReturnType(new FullyQualifiedJavaType(getTargetPackae("querybuilder") + "." + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "QueryBuilder"));
                    _method.addBodyLine("List<Object> objList = new ArrayList<Object>();");
                    _method.addBodyLine("for (Object object : "+introspectedColumn.getJavaProperty()+"Arr)");
                    _method.addBodyLine("{");
                    _method.addBodyLine("objList.add(object);");
                    _method.addBodyLine("}");
                    _method.addBodyLine("join(ConditionBuilder.field("+introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Field" + "."+enumFieldName+").in(objList));");
                    _method.addBodyLine("return this;");
                    beanClass.addMethod(_method);
                    
                    methodName = introspectedColumn.getJavaProperty() + "In";
                    _method = new Method(methodName);
                    _method.addJavaDocLine("/**");
                    _method.addJavaDocLine(" * 字段在某个范围内in：" + introspectedColumn.getActualColumnName() + "("+introspectedColumn.getRemarks()+")");
                    _method.addJavaDocLine(" */");
                    _method.setFinal(false);
                    _method.setStatic(false);
                    _method.setVisibility(JavaVisibility.PUBLIC);
                    _method.addParameter(new Parameter(new FullyQualifiedJavaType("List<"+introspectedColumn.getFullyQualifiedJavaType().getShortName()+">"), introspectedColumn.getJavaProperty() + "List"));
                    _method.setReturnType(new FullyQualifiedJavaType(getTargetPackae("querybuilder") + "." + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "QueryBuilder"));
                    _method.addBodyLine("List<Object> objList = new ArrayList<Object>();");
                    _method.addBodyLine("for (Object object : "+introspectedColumn.getJavaProperty()+"List)");
                    _method.addBodyLine("{");
                    _method.addBodyLine("objList.add(object);");
                    _method.addBodyLine("}");
                    _method.addBodyLine("join(ConditionBuilder.field("+introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Field" + "."+enumFieldName+").in(objList));");
                    _method.addBodyLine("return this;");
                    beanClass.addMethod(_method);
                    
                    // 如果是字符串类型，支持like搜索
                    if (introspectedColumn.isStringColumn())
                    {
                        methodName = introspectedColumn.getJavaProperty() + "Like";
                        _method = new Method(methodName);
                        _method.addJavaDocLine("/**");
                        _method.addJavaDocLine(" * 字段like某个值：" + introspectedColumn.getActualColumnName() + "("+introspectedColumn.getRemarks()+")");
                        _method.addJavaDocLine(" */");
                        _method.setFinal(false);
                        _method.setStatic(false);
                        _method.setVisibility(JavaVisibility.PUBLIC);
                        _method.addParameter(new Parameter(introspectedColumn.getFullyQualifiedJavaType(), introspectedColumn.getJavaProperty()));
                        _method.setReturnType(new FullyQualifiedJavaType(getTargetPackae("querybuilder") + "." + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "QueryBuilder"));
                        _method.addBodyLine("join(ConditionBuilder.field("+introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Field" + "."+enumFieldName+").like("+introspectedColumn.getJavaProperty()+"));");
                        _method.addBodyLine("return this;");
                        beanClass.addMethod(_method);
                    }
                }
            }
            
            
        }
        
        return new GeneratedJavaFile(beanClass, getTargetProjectJavaSourceFolder(), "utf-8", new DefaultJavaFormatter());
    }
}
