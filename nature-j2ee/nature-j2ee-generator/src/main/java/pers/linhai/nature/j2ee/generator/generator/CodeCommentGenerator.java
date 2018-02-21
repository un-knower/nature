/*
 * <p>Copyright : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p> <p>FileName : MyCommentGenerator.java</p>
 * <p>Package : com.meme.crm.dao.generator</p>
 * @Creator lilinhai 2018年2月4日 上午11:01:22
 * @Version V1.0
 */

package pers.linhai.nature.j2ee.generator.generator;


import static pers.linhai.nature.j2ee.generator.core.internal.util.StringUtility.isTrue;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Set;

import pers.linhai.nature.j2ee.generator.core.api.IntrospectedColumn;
import pers.linhai.nature.j2ee.generator.core.api.IntrospectedTable;
import pers.linhai.nature.j2ee.generator.core.api.dom.java.Field;
import pers.linhai.nature.j2ee.generator.core.api.dom.java.FullyQualifiedJavaType;
import pers.linhai.nature.j2ee.generator.core.api.dom.java.InnerClass;
import pers.linhai.nature.j2ee.generator.core.api.dom.java.InnerEnum;
import pers.linhai.nature.j2ee.generator.core.api.dom.java.JavaElement;
import pers.linhai.nature.j2ee.generator.core.api.dom.java.Method;
import pers.linhai.nature.j2ee.generator.core.api.dom.java.TopLevelClass;
import pers.linhai.nature.j2ee.generator.core.config.MergeConstants;
import pers.linhai.nature.j2ee.generator.core.config.PropertyRegistry;
import pers.linhai.nature.j2ee.generator.core.internal.DefaultCommentGenerator;
import pers.linhai.nature.j2ee.generator.utils.CodeCommentUtils;


/**
 * <p>Description    : <pre>TODO(这里用一句话描述这个类的作用)</pre></p>
 * <p>ClassName      : MyCommentGenerator</p>
 * @author lilinhai 2018年2月4日 上午11:01:22
 * @version 1.0
 */
public class CodeCommentGenerator extends DefaultCommentGenerator
{

    private Properties properties;

    private Properties systemPro;

    private boolean suppressDate;

    private boolean suppressAllComments;

    private String currentDateStr;

    public CodeCommentGenerator()
    {
        super();
        properties = new Properties();
        systemPro = System.getProperties();
        suppressDate = false;
        suppressAllComments = false;
        currentDateStr = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
    }

    public void addConfigurationProperties(Properties properties)
    {
        this.properties.putAll(properties);

        suppressDate = isTrue(properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_DATE));

        suppressAllComments = isTrue(properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_ALL_COMMENTS));
    }

    /**
     * This method adds the custom javadoc tag for. You may do nothing if you do
     * not wish to include the Javadoc tag - however, if you do not include the
     * Javadoc tag then the Java merge capability of the eclipse plugin will
     * break.
     * 
     * @param javaElement
     *            the java element
     */
    protected void addJavadocTag(JavaElement javaElement, boolean markAsDoNotDelete)
    {
        javaElement.addJavaDocLine(" *");
        StringBuilder sb = new StringBuilder();
        sb.append(" * ");
        sb.append(MergeConstants.NEW_ELEMENT_TAG);
        if (markAsDoNotDelete)
        {
            sb.append(" do_not_delete_during_merge");
        }
        String s = getDateString();
        if (s != null)
        {
            sb.append(' ');
            sb.append(s);
        }
        javaElement.addJavaDocLine(sb.toString());
    }

    /**
     * This method returns a formated date string to include in the Javadoc tag
     * and XML comments. You may return null if you do not want the date in
     * these documentation elements.
     * 
     * @return a string representing the current timestamp, or null
     */
    protected String getDateString()
    {
        String result = null;
        if (!suppressDate)
        {
            result = currentDateStr;
        }
        return result;
    }

    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable)
    {
        if (suppressAllComments)
        {
            return;
        }
        StringBuilder sb = new StringBuilder();
        innerClass.addJavaDocLine("/**");
        sb.append(" * ");
        sb.append(introspectedTable.getFullyQualifiedTable());
        sb.append(" ");
        sb.append(getDateString());
        innerClass.addJavaDocLine(sb.toString().replace("\n", " "));
        innerClass.addJavaDocLine(" */");
    }

    public void addEnumComment(InnerEnum innerEnum, IntrospectedTable introspectedTable)
    {
        if (suppressAllComments)
        {
            return;
        }
        StringBuilder sb = new StringBuilder();
        innerEnum.addJavaDocLine("/**");
        sb.append(" * ");
        sb.append(introspectedTable.getFullyQualifiedTable());
        innerEnum.addJavaDocLine(sb.toString().replace("\n", " "));
        innerEnum.addJavaDocLine(" */");
    }

    public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn)
    {
        if (suppressAllComments)
        {
            return;
        }
        CodeCommentUtils.addFieldComment(field, introspectedTable, introspectedColumn);
    }

    public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable)
    {
        if (suppressAllComments)
        {
            return;
        }
        method.addJavaDocLine("/**");
        addJavadocTag(method, false);
        method.addJavaDocLine(" */");
    }

    public void addGetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn)
    {}

    public void addSetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn)
    {}

    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable, boolean markAsDoNotDelete)
    {
        if (suppressAllComments)
        {
            return;
        }
        StringBuilder sb = new StringBuilder();
        innerClass.addJavaDocLine("/**");
        sb.append(" * ");
        sb.append(introspectedTable.getFullyQualifiedTable());
        innerClass.addJavaDocLine(sb.toString().replace("\n", " "));
        sb.setLength(0);
        sb.append(" * @author ");
        sb.append(systemPro.getProperty("user.name"));
        sb.append(" ");
        sb.append(currentDateStr);
        innerClass.addJavaDocLine(" */");
    }

    /** 
     * <p>Overriding Method: lilinhai 2018年2月4日 上午11:03:24</p>
     * <p>Title: addModelClassComment</p>
     * <p>Description: TODO</p>
     * @param topLevelClass
     * @param introspectedTable 
     * @see pers.linhai.nature.j2ee.generator.core.api.CommentGenerator#addModelClassComment(pers.linhai.nature.j2ee.generator.core.api.dom.java.TopLevelClass, pers.linhai.nature.j2ee.generator.core.api.IntrospectedTable)
     */
    @Override
    public void addModelClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable)
    {
        if (suppressAllComments)
        {
            return;
        }

        CodeCommentUtils.addModelClassComment(topLevelClass, introspectedTable);
    }

    /** 
     * <p>Overriding Method: lilinhai 2018年2月4日 上午11:03:24</p>
     * <p>Title: addGeneralMethodAnnotation</p>
     * <p>Description: TODO</p>
     * @param method
     * @param introspectedTable
     * @param imports 
     * @see pers.linhai.nature.j2ee.generator.core.api.CommentGenerator#addGeneralMethodAnnotation(pers.linhai.nature.j2ee.generator.core.api.dom.java.Method, pers.linhai.nature.j2ee.generator.core.api.IntrospectedTable, java.util.Set)
     */
    @Override
    public void addGeneralMethodAnnotation(Method method, IntrospectedTable introspectedTable, Set<FullyQualifiedJavaType> imports)
    {
        // TODO Auto-generated method stub

    }

    /** 
     * <p>Overriding Method: lilinhai 2018年2月4日 上午11:03:24</p>
     * <p>Title: addGeneralMethodAnnotation</p>
     * <p>Description: TODO</p>
     * @param method
     * @param introspectedTable
     * @param introspectedColumn
     * @param imports 
     * @see pers.linhai.nature.j2ee.generator.core.api.CommentGenerator#addGeneralMethodAnnotation(pers.linhai.nature.j2ee.generator.core.api.dom.java.Method, pers.linhai.nature.j2ee.generator.core.api.IntrospectedTable, pers.linhai.nature.j2ee.generator.core.api.IntrospectedColumn, java.util.Set)
     */
    @Override
    public void addGeneralMethodAnnotation(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn, Set<FullyQualifiedJavaType> imports)
    {
        // TODO Auto-generated method stub

    }

    /** 
     * <p>Overriding Method: lilinhai 2018年2月4日 上午11:03:24</p>
     * <p>Title: addFieldAnnotation</p>
     * <p>Description: TODO</p>
     * @param field
     * @param introspectedTable
     * @param imports 
     * @see pers.linhai.nature.j2ee.generator.core.api.CommentGenerator#addFieldAnnotation(pers.linhai.nature.j2ee.generator.core.api.dom.java.Field, pers.linhai.nature.j2ee.generator.core.api.IntrospectedTable, java.util.Set)
     */
    @Override
    public void addFieldAnnotation(Field field, IntrospectedTable introspectedTable, Set<FullyQualifiedJavaType> imports)
    {
        // TODO Auto-generated method stub

    }

    /** 
     * <p>Overriding Method: lilinhai 2018年2月4日 上午11:03:24</p>
     * <p>Title: addFieldAnnotation</p>
     * <p>Description: TODO</p>
     * @param field
     * @param introspectedTable
     * @param introspectedColumn
     * @param imports 
     * @see pers.linhai.nature.j2ee.generator.core.api.CommentGenerator#addFieldAnnotation(pers.linhai.nature.j2ee.generator.core.api.dom.java.Field, pers.linhai.nature.j2ee.generator.core.api.IntrospectedTable, pers.linhai.nature.j2ee.generator.core.api.IntrospectedColumn, java.util.Set)
     */
    @Override
    public void addFieldAnnotation(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn, Set<FullyQualifiedJavaType> imports)
    {
        // TODO Auto-generated method stub

    }

    /** 
     * <p>Overriding Method: lilinhai 2018年2月4日 上午11:03:24</p>
     * <p>Title: addClassAnnotation</p>
     * <p>Description: TODO</p>
     * @param innerClass
     * @param introspectedTable
     * @param imports 
     * @see pers.linhai.nature.j2ee.generator.core.api.CommentGenerator#addClassAnnotation(pers.linhai.nature.j2ee.generator.core.api.dom.java.InnerClass, pers.linhai.nature.j2ee.generator.core.api.IntrospectedTable, java.util.Set)
     */
    @Override
    public void addClassAnnotation(InnerClass innerClass, IntrospectedTable introspectedTable, Set<FullyQualifiedJavaType> imports)
    {
        // TODO Auto-generated method stub

    }
}
