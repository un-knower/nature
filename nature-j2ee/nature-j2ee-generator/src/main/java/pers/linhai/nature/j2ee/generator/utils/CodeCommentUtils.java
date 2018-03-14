/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : CodeCommentUtils.java</p>
 * <p>Package     : codegenerator.utils</p>
 * @Creator lilinhai 2018年2月7日 下午9:42:44
 * @Version  V1.0  
 */ 

package pers.linhai.nature.j2ee.generator.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import pers.linhai.nature.j2ee.generator.core.api.IntrospectedColumn;
import pers.linhai.nature.j2ee.generator.core.api.IntrospectedTable;
import pers.linhai.nature.j2ee.generator.core.api.dom.java.Field;
import pers.linhai.nature.j2ee.generator.core.api.dom.java.Interface;
import pers.linhai.nature.j2ee.generator.core.api.dom.java.JavaElement;
import pers.linhai.nature.j2ee.generator.core.api.dom.java.TopLevelClass;
import pers.linhai.nature.j2ee.generator.core.internal.util.StringUtility;

/**
 * <p>Description    : <pre>TODO(这里用一句话描述这个类的作用)</pre></p>
 * <p>ClassName      : CodeCommentUtils</p>
 * @author lilinhai 2018年2月7日 下午9:42:44
 * @version 1.0
 */
public class CodeCommentUtils
{

    public static void addControlerClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable)
    {
        StringBuilder sb = new StringBuilder();
        setClassCommonComment(topLevelClass, sb);
        String remarks = introspectedTable.getRemarks();
        if (StringUtility.stringHasValue(remarks))
        {
            topLevelClass.addJavaDocLine(" *   1）Controller控制层，对应的实体含义："); //$NON-NLS-1$
            String[] remarkLines = remarks.split(System.getProperty("line.separator")); //$NON-NLS-1$
            for (String remarkLine : remarkLines)
            {
                topLevelClass.addJavaDocLine(" *      " + remarkLine); //$NON-NLS-1$
            }
        }
        else
        {
            topLevelClass.addJavaDocLine(" *   1）<label style=\"color:red\">警告：该实体在数据库表["+introspectedTable.getFullyQualifiedTable().getIntrospectedTableName()+"]中未加注释，请加上！</label>"); //$NON-NLS-1$
        }
        topLevelClass.addJavaDocLine(" *"); //$NON-NLS-1$

        sb = new StringBuilder();
        sb.append(" *   2）Controller控制层对应的实体所对应数据库表："); //$NON-NLS-1$
        sb.append(introspectedTable.getFullyQualifiedTable());
        topLevelClass.addJavaDocLine(sb.toString());
        topLevelClass.addJavaDocLine(" * </pre>"); //$NON-NLS-1$
        topLevelClass.addJavaDocLine(" */"); //$NON-NLS-1$
    }
    
    public static void addInterceptorClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable)
    {
        StringBuilder sb = new StringBuilder();
        setClassCommonComment(topLevelClass, sb);
        String remarks = introspectedTable.getRemarks();
        if (StringUtility.stringHasValue(remarks))
        {
            topLevelClass.addJavaDocLine(" *   1）实体数据拦截器实现类，对应的实体含义："); //$NON-NLS-1$
            String[] remarkLines = remarks.split(System.getProperty("line.separator")); //$NON-NLS-1$
            for (String remarkLine : remarkLines)
            {
                topLevelClass.addJavaDocLine(" *      " + remarkLine); //$NON-NLS-1$
            }
        }
        else
        {
            topLevelClass.addJavaDocLine(" *   1）<label style=\"color:red\">警告：该实体在数据库表["+introspectedTable.getFullyQualifiedTable().getIntrospectedTableName()+"]中未加注释，请加上！</label>"); //$NON-NLS-1$
        }
        topLevelClass.addJavaDocLine(" *"); //$NON-NLS-1$

        sb = new StringBuilder();
        sb.append(" *   2）实体所对应数据库表："); //$NON-NLS-1$
        sb.append(introspectedTable.getFullyQualifiedTable());
        topLevelClass.addJavaDocLine(sb.toString());
        topLevelClass.addJavaDocLine(" * </pre>"); //$NON-NLS-1$
        topLevelClass.addJavaDocLine(" */"); //$NON-NLS-1$
    }
    
    public static void addServiceClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable)
    {
        StringBuilder sb = new StringBuilder();
        setClassCommonComment(topLevelClass, sb);
        String remarks = introspectedTable.getRemarks();
        if (StringUtility.stringHasValue(remarks))
        {
            topLevelClass.addJavaDocLine(" *   1）Service业务层接口实现类，对应的实体含义："); //$NON-NLS-1$
            String[] remarkLines = remarks.split(System.getProperty("line.separator")); //$NON-NLS-1$
            for (String remarkLine : remarkLines)
            {
                topLevelClass.addJavaDocLine(" *      " + remarkLine); //$NON-NLS-1$
            }
        }
        else
        {
            topLevelClass.addJavaDocLine(" *   1）<label style=\"color:red\">警告：该实体在数据库表["+introspectedTable.getFullyQualifiedTable().getIntrospectedTableName()+"]中未加注释，请加上！</label>"); //$NON-NLS-1$
        }
        topLevelClass.addJavaDocLine(" *"); //$NON-NLS-1$

        sb = new StringBuilder();
        sb.append(" *   2）Service业务层实现类对应的实体所对应数据库表："); //$NON-NLS-1$
        sb.append(introspectedTable.getFullyQualifiedTable());
        topLevelClass.addJavaDocLine(sb.toString());
        topLevelClass.addJavaDocLine(" * </pre>"); //$NON-NLS-1$
        topLevelClass.addJavaDocLine(" */"); //$NON-NLS-1$
    }
    
    public static void addServiceInterfaceComment(Interface interfaceClass, IntrospectedTable introspectedTable)
    {
        StringBuilder sb = new StringBuilder();
        setClassCommonComment(interfaceClass, sb);
        String remarks = introspectedTable.getRemarks();
        if (StringUtility.stringHasValue(remarks))
        {
            interfaceClass.addJavaDocLine(" *   1）Service业务层接口对应的实体含义："); //$NON-NLS-1$
            String[] remarkLines = remarks.split(System.getProperty("line.separator")); //$NON-NLS-1$
            for (String remarkLine : remarkLines)
            {
                interfaceClass.addJavaDocLine(" *      " + remarkLine); //$NON-NLS-1$
            }
        }
        else
        {
            interfaceClass.addJavaDocLine(" *   1）<label style=\"color:red\">警告：该Service接口对应的实体在数据库表["+introspectedTable.getFullyQualifiedTable().getIntrospectedTableName()+"]中未加注释，请加上！</label>"); //$NON-NLS-1$
        }
        interfaceClass.addJavaDocLine(" *"); //$NON-NLS-1$

        sb = new StringBuilder();
        sb.append(" *   2）Service业务层接口对应数据库表："); //$NON-NLS-1$
        sb.append(introspectedTable.getFullyQualifiedTable());
        interfaceClass.addJavaDocLine(sb.toString());
        interfaceClass.addJavaDocLine(" * </pre>"); //$NON-NLS-1$
        interfaceClass.addJavaDocLine(" */"); //$NON-NLS-1$
    }
    
    public static void addMapperImplComment(TopLevelClass interfaceClass, IntrospectedTable introspectedTable)
    {
        StringBuilder sb = new StringBuilder();
        setClassCommonComment(interfaceClass, sb);
        String remarks = introspectedTable.getRemarks();
        if (StringUtility.stringHasValue(remarks))
        {
            interfaceClass.addJavaDocLine(" *   1）Mapper实现类对应的实体含义："); //$NON-NLS-1$
            String[] remarkLines = remarks.split(System.getProperty("line.separator")); //$NON-NLS-1$
            for (String remarkLine : remarkLines)
            {
                interfaceClass.addJavaDocLine(" *      " + remarkLine); //$NON-NLS-1$
            }
        }
        else
        {
            interfaceClass.addJavaDocLine(" *   1）<label style=\"color:red\">警告：该Mapper对应的实体在数据库表["+introspectedTable.getFullyQualifiedTable().getIntrospectedTableName()+"]中未加注释，请加上！</label>"); //$NON-NLS-1$
        }
        interfaceClass.addJavaDocLine(" *"); //$NON-NLS-1$

        sb = new StringBuilder();
        sb.append(" *   2）Mapper实现类对应数据库表："); //$NON-NLS-1$
        sb.append(introspectedTable.getFullyQualifiedTable());
        interfaceClass.addJavaDocLine(sb.toString());
        interfaceClass.addJavaDocLine(" * </pre>"); //$NON-NLS-1$
        interfaceClass.addJavaDocLine(" */"); //$NON-NLS-1$
    }
    
    public static void addMapperInterfaceComment(Interface interfaceClass, IntrospectedTable introspectedTable)
    {
        StringBuilder sb = new StringBuilder();
        setClassCommonComment(interfaceClass, sb);
        String remarks = introspectedTable.getRemarks();
        if (StringUtility.stringHasValue(remarks))
        {
            interfaceClass.addJavaDocLine(" *   1）Mapper对应的实体含义："); //$NON-NLS-1$
            String[] remarkLines = remarks.split(System.getProperty("line.separator")); //$NON-NLS-1$
            for (String remarkLine : remarkLines)
            {
                interfaceClass.addJavaDocLine(" *      " + remarkLine); //$NON-NLS-1$
            }
        }
        else
        {
            interfaceClass.addJavaDocLine(" *   1）<label style=\"color:red\">警告：该Mapper对应的实体在数据库表["+introspectedTable.getFullyQualifiedTable().getIntrospectedTableName()+"]中未加注释，请加上！</label>"); //$NON-NLS-1$
        }
        interfaceClass.addJavaDocLine(" *"); //$NON-NLS-1$

        sb = new StringBuilder();
        sb.append(" *   2）Mapper对应数据库表："); //$NON-NLS-1$
        sb.append(introspectedTable.getFullyQualifiedTable());
        interfaceClass.addJavaDocLine(sb.toString());
        interfaceClass.addJavaDocLine(" * </pre>"); //$NON-NLS-1$
        interfaceClass.addJavaDocLine(" */"); //$NON-NLS-1$
    }
    
    public static void addModelClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable)
    {
        StringBuilder sb = new StringBuilder();
        setClassCommonComment(topLevelClass, sb);
        String remarks = introspectedTable.getRemarks();
        if (StringUtility.stringHasValue(remarks))
        {
            topLevelClass.addJavaDocLine(" *   1）实体含义："); //$NON-NLS-1$
            String[] remarkLines = remarks.split(System.getProperty("line.separator")); //$NON-NLS-1$
            for (String remarkLine : remarkLines)
            {
                topLevelClass.addJavaDocLine(" *      " + remarkLine); //$NON-NLS-1$
            }
        }
        else
        {
            topLevelClass.addJavaDocLine(" *   1）<label style=\"color:red\">警告：该实体在数据库表["+introspectedTable.getFullyQualifiedTable().getIntrospectedTableName()+"]中未加注释，请加上！</label>"); //$NON-NLS-1$
        }
        topLevelClass.addJavaDocLine(" *"); //$NON-NLS-1$

        sb = new StringBuilder();
        sb.append(" *   2）该实体对应数据库表："); //$NON-NLS-1$
        sb.append(introspectedTable.getFullyQualifiedTable());
        topLevelClass.addJavaDocLine(sb.toString());
        topLevelClass.addJavaDocLine(" * </pre>"); //$NON-NLS-1$
        topLevelClass.addJavaDocLine(" */"); //$NON-NLS-1$
    }

    /**
     * <p>Title         : setClassCommonComment lilinhai 2018年2月8日 下午5:06:25</p>
     * <p>Description   : <pre>TODO(这里用一句话描述这个方法的作用)</pre></p>
     * @param topLevelClass
     * @param sb 
     * void 
     */ 
    private static void setClassCommonComment(JavaElement topLevelClass, StringBuilder sb)
    {
        topLevelClass.addJavaDocLine("/**"); //$NON-NLS-1$
        sb.append(" * @author nature-j2ee-code-generator ");
        topLevelClass.addJavaDocLine(sb.toString());

        sb = new StringBuilder();
        sb.append(" * @date ");
        sb.append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        sb.append(" ");
        //topLevelClass.addJavaDocLine(sb.toString());

        sb = new StringBuilder();
        sb.append(" * @version ");
        sb.append("1.0");
        sb.append(" ");
        topLevelClass.addJavaDocLine(sb.toString());
        topLevelClass.addJavaDocLine(" * <pre>"); //$NON-NLS-1$
    }
    
    public static void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn)
    {

        field.addJavaDocLine("/**"); //$NON-NLS-1$
        field.addJavaDocLine(" * <pre>"); //$NON-NLS-1$
        String remarks = introspectedColumn.getRemarks();
        if (StringUtility.stringHasValue(remarks))
        {
            field.addJavaDocLine(" *   1）字段含义："); //$NON-NLS-1$
            String[] remarkLines = remarks.split(System.getProperty("line.separator")); //$NON-NLS-1$
            for (String remarkLine : remarkLines)
            {
                field.addJavaDocLine(" *      " + remarkLine); //$NON-NLS-1$
            }
        }
        else
        {
            field.addJavaDocLine(" *   1）<label style=\"color:red\">警告：该字段在数据库表中未加注释，请加上！</label>"); //$NON-NLS-1$
        }

        field.addJavaDocLine(" *"); //$NON-NLS-1$
        StringBuilder sb = new StringBuilder();
        sb.append(" *   2）该字段对应数据库表中： "); //$NON-NLS-1$
        sb.append(introspectedTable.getFullyQualifiedTable()).append('.').append(introspectedColumn.getActualColumnName());
        field.addJavaDocLine(sb.toString());
        field.addJavaDocLine(" * </pre>"); //$NON-NLS-1$
        field.addJavaDocLine(" */"); //$NON-NLS-1$
    }
}
