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

import pers.linhai.nature.j2ee.core.exception.IllegalFieldException;
import pers.linhai.nature.j2ee.core.model.DateJsonDeserializer;
import pers.linhai.nature.j2ee.core.model.EntityBean;
import pers.linhai.nature.j2ee.generator.core.api.CoreClassImportConstant;
import pers.linhai.nature.j2ee.generator.core.api.GeneratedJavaFile;
import pers.linhai.nature.j2ee.generator.core.api.IntrospectedColumn;
import pers.linhai.nature.j2ee.generator.core.api.IntrospectedTable;
import pers.linhai.nature.j2ee.generator.core.api.dom.DefaultJavaFormatter;
import pers.linhai.nature.j2ee.generator.core.api.dom.java.Field;
import pers.linhai.nature.j2ee.generator.core.api.dom.java.FullyQualifiedJavaType;
import pers.linhai.nature.j2ee.generator.core.api.dom.java.InitializationBlock;
import pers.linhai.nature.j2ee.generator.core.api.dom.java.JavaVisibility;
import pers.linhai.nature.j2ee.generator.core.api.dom.java.Method;
import pers.linhai.nature.j2ee.generator.core.api.dom.java.Parameter;
import pers.linhai.nature.j2ee.generator.core.api.dom.java.TopLevelClass;
import pers.linhai.nature.j2ee.generator.core.api.dom.java.TopLevelEnumeration;
import pers.linhai.nature.j2ee.generator.exception.GeneratorException;
import pers.linhai.nature.j2ee.generator.utils.CodeGeneratorUtils;


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
        topLevelClass.addImportedType(IllegalFieldException.class.getName());
        topLevelClass.addImportedType(EntityBean.class.getName());
        topLevelClass.addImportedType(Date.class.getName());
        topLevelClass.addImportedType(new FullyQualifiedJavaType(getTargetPackae("field") + "." + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Field"));
        boolean hasDateField = false;
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
        
        // 添加范型继承关系BaseService
        if (introspectedTable.getPrimaryKeyColumns() == null || introspectedTable.getPrimaryKeyColumns().isEmpty())
        {
            throw new GeneratorException(introspectedTable.getBaseRecordType() + " 该表没有设置主键，请设置！");
        }
        String keyType = introspectedTable.getPrimaryKeyColumns().get(0).getFullyQualifiedJavaType().getShortName();
        topLevelClass.setSuperClass(new FullyQualifiedJavaType("BaseEntity<" + keyType + ">"));

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
        
        // validField方法
        Method validFieldMethod = new Method("validField");
        validFieldMethod.addJavaDocLine("/**");
        validFieldMethod.addJavaDocLine(" * 校验输入字段的合法性");
        validFieldMethod.addJavaDocLine(" */");
        validFieldMethod.setFinal(false);
        validFieldMethod.setStatic(false);
        validFieldMethod.setVisibility(JavaVisibility.PUBLIC);
        validFieldMethod.addParameter(new Parameter(new FullyQualifiedJavaType("String"), "javaField"));
        validFieldMethod.addBodyLine("// 如果枚举为空，说明是非法字段");
        validFieldMethod.addBodyLine("if (" + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Field" + ".transfer(javaField) == null)");
        validFieldMethod.addBodyLine("{");
        validFieldMethod.addBodyLine("throw new IllegalFieldException(\" There is an illegal field : \" + javaField);");
        validFieldMethod.addBodyLine("}");
        topLevelClass.addMethod(validFieldMethod);
        
        // getTableField方法
        Method getTableFieldBaseMethod = new Method("getTableField");
        getTableFieldBaseMethod.addJavaDocLine("/**");
        getTableFieldBaseMethod.addJavaDocLine(" * 获取java字段名对应的数据库表字段名");
        getTableFieldBaseMethod.addJavaDocLine(" */");
        getTableFieldBaseMethod.setFinal(false);
        getTableFieldBaseMethod.setStatic(false);
        getTableFieldBaseMethod.setVisibility(JavaVisibility.PUBLIC);
        getTableFieldBaseMethod.addParameter(new Parameter(new FullyQualifiedJavaType("String"), "javaField"));
        getTableFieldBaseMethod.setReturnType(new FullyQualifiedJavaType("String"));
        getTableFieldBaseMethod.addBodyLine(introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Field e = null;");
        getTableFieldBaseMethod.addBodyLine("if ((e = " + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Field" + ".transfer(javaField)) != null)");
        getTableFieldBaseMethod.addBodyLine("{");
        getTableFieldBaseMethod.addBodyLine("return e.getTableField();");
        getTableFieldBaseMethod.addBodyLine("}");
        getTableFieldBaseMethod.addBodyLine("throw new IllegalFieldException(\" Can't find the table-field from the java-field : \" + javaField);");
        topLevelClass.addMethod(getTableFieldBaseMethod);
        
        // getJdbcType方法
        Method getJdbcTypeBaseMethod = new Method("getJdbcType");
        getJdbcTypeBaseMethod.addJavaDocLine("/**");
        getJdbcTypeBaseMethod.addJavaDocLine(" * 获取改该字段对应的JDBC类型");
        getJdbcTypeBaseMethod.addJavaDocLine(" */");
        getJdbcTypeBaseMethod.setFinal(false);
        getJdbcTypeBaseMethod.setStatic(false);
        getJdbcTypeBaseMethod.setVisibility(JavaVisibility.PUBLIC);
        getJdbcTypeBaseMethod.addParameter(new Parameter(new FullyQualifiedJavaType("String"), "fieldName"));
        getJdbcTypeBaseMethod.setReturnType(new FullyQualifiedJavaType("String"));
        getJdbcTypeBaseMethod.addBodyLine(introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Field e = null;");
        getJdbcTypeBaseMethod.addBodyLine("if ((e = " + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Field" + ".transfer(fieldName)) != null)");
        getJdbcTypeBaseMethod.addBodyLine("{");
        getJdbcTypeBaseMethod.addBodyLine("return e.getJdbcType();");
        getJdbcTypeBaseMethod.addBodyLine("}");
        getJdbcTypeBaseMethod.addBodyLine("throw new IllegalFieldException(\" Can't find the jdbc-type from the java-field : \" + fieldName);");
        topLevelClass.addMethod(getJdbcTypeBaseMethod);
        
        // existsFieldMethod方法
        Method existsFieldMethod = new Method("existsField");
        existsFieldMethod.addJavaDocLine("/**");
        existsFieldMethod.addJavaDocLine(" * 判断是否存在某个字段");
        existsFieldMethod.addJavaDocLine(" */");
        existsFieldMethod.setFinal(false);
        existsFieldMethod.setStatic(false);
        existsFieldMethod.setVisibility(JavaVisibility.PUBLIC);
        existsFieldMethod.addParameter(new Parameter(new FullyQualifiedJavaType("String"), "fieldName"));
        existsFieldMethod.setReturnType(new FullyQualifiedJavaType("boolean"));
        existsFieldMethod.addBodyLine("return " + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Field" + ".transfer(fieldName) != null;");
        topLevelClass.addMethod(existsFieldMethod);

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

        // 创建实体对应的Bean
        //javaFileList.add(createEntityBean(introspectedTable));

        // 创建实体对应的QueryBean
        javaFileList.add(createEntityQueryBean(introspectedTable));
        
        // 创建字段枚举
        javaFileList.add(createEntityFieldEnum(introspectedTable));
        
        return javaFileList;
    }
    
    private GeneratedJavaFile createEntityFieldEnum(IntrospectedTable introspectedTable)
    {
        TopLevelEnumeration fieldEnumeration = new TopLevelEnumeration(new FullyQualifiedJavaType(getTargetPackae("field") + "." + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Field"));
        
        fieldEnumeration.addImportedType(new FullyQualifiedJavaType(Map.class.getName()));
        fieldEnumeration.addImportedType(new FullyQualifiedJavaType(HashMap.class.getName()));
        fieldEnumeration.addImportedType(new FullyQualifiedJavaType(List.class.getName()));
        fieldEnumeration.addImportedType(new FullyQualifiedJavaType(ArrayList.class.getName()));
        
        fieldEnumeration.setStatic(false);
        fieldEnumeration.setVisibility(JavaVisibility.PUBLIC);
        
        fieldEnumeration.addJavaDocLine("/**");
        fieldEnumeration.addJavaDocLine(" * 该实体所有字段枚举");
        fieldEnumeration.addJavaDocLine(" */");
        
        // 标记该类为内部类
        fieldEnumeration.setInnerClass(true);
        
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
        initializationBlock.addBodyLine("TABLE_FIELD_LIST.add(field.tableField);");
        initializationBlock.addBodyLine("JAVA_FIELD_LIST.add(field.javaField);");
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
        jdbcType.setType(new FullyQualifiedJavaType("String"));
        jdbcType.setVisibility(JavaVisibility.PRIVATE);
        fieldEnumeration.addField(jdbcType);
        
        // 添加初始化对象的构造函数
        Method constructorMethod = new Method(introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Field");
        constructorMethod.setConstructor(true);
        constructorMethod.addParameter(new Parameter(new FullyQualifiedJavaType("String"), "javaField"));
        constructorMethod.addParameter(new Parameter(new FullyQualifiedJavaType("String"), "tableField"));
        constructorMethod.addParameter(new Parameter(new FullyQualifiedJavaType("String"), "jdbcType"));
        constructorMethod.addBodyLine("this.javaField = javaField;");
        constructorMethod.addBodyLine("this.tableField = tableField;");
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
        getJdbcTypeMethod.addBodyLine("return jdbcType;");
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
            fieldEnumeration.addEnumConstant(enumFieldName + "(\"" + introspectedColumn.getJavaProperty() + "\", \"" + introspectedColumn.getActualColumnName() + "\", \"" + introspectedColumn.getJdbcTypeName() + "\")");
        }
        return new GeneratedJavaFile(fieldEnumeration, getTargetProjectJavaSourceFolder(), "utf-8", new DefaultJavaFormatter());
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
        beanClass.addImportedType(new FullyQualifiedJavaType(List.class.getName()));
        beanClass.addImportedType(IllegalFieldException.class.getName());
        beanClass.addImportedType(new FullyQualifiedJavaType(getTargetPackae("field") + "." + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Field"));
        
        // 添加范型继承关系BaseService
        if (introspectedTable.getPrimaryKeyColumns() == null || introspectedTable.getPrimaryKeyColumns().isEmpty())
        {
            throw new GeneratorException(introspectedTable.getBaseRecordType() + " 该表没有设置主键，请设置！");
        }
        
        // 添加继承关系
        beanClass.setSuperClass("BaseQuery");

        // 添加类注释
        beanClass.addJavaDocLine("/**");
        beanClass.addJavaDocLine(" * <pre>"); //$NON-NLS-1$
        beanClass.addJavaDocLine(" *    针对该实体的QueryBean封装，用于接受前端的组合查询请求参数封装。");
        beanClass.addJavaDocLine(" * ClassName: " + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "QueryBean");
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
        
        // validField方法
        Method validFieldMethod = new Method("validField");
        validFieldMethod.addJavaDocLine("/**");
        validFieldMethod.addJavaDocLine(" * 校验输入字段的合法性");
        validFieldMethod.addJavaDocLine(" */");
        validFieldMethod.setFinal(false);
        validFieldMethod.setStatic(false);
        validFieldMethod.setVisibility(JavaVisibility.PUBLIC);
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
        getTableFieldBaseMethod.setVisibility(JavaVisibility.PUBLIC);
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
        getJdbcTypeBaseMethod.setVisibility(JavaVisibility.PUBLIC);
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
        existsFieldMethod.setVisibility(JavaVisibility.PUBLIC);
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
        getAllFieldListMethod.setVisibility(JavaVisibility.PUBLIC);
        getAllFieldListMethod.setReturnType(new FullyQualifiedJavaType(List.class.getName() + "<String>"));
        getAllFieldListMethod.addBodyLine("return " + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Field.getTableFieldList();");
        beanClass.addMethod(getAllFieldListMethod);
        
        return new GeneratedJavaFile(beanClass, getTargetProjectJavaSourceFolder(), "utf-8", new DefaultJavaFormatter());
    }
}
