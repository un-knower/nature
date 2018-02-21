/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : CodeGeneratorUtils.java</p>
 * <p>Package     : codegenerator.utils</p>
 * @Creator lilinhai 2018年2月7日 下午9:52:07
 * @Version  V1.0  
 */ 

package pers.linhai.nature.j2ee.generator.utils;

import pers.linhai.nature.j2ee.generator.core.api.IntrospectedTable;
import pers.linhai.nature.j2ee.generator.core.api.dom.java.Field;
import pers.linhai.nature.j2ee.generator.core.api.dom.java.FullyQualifiedJavaType;
import pers.linhai.nature.j2ee.generator.core.api.dom.java.JavaVisibility;
import pers.linhai.nature.j2ee.generator.core.api.dom.java.Method;
import pers.linhai.nature.j2ee.generator.core.api.dom.java.TopLevelClass;

/**
 * <p>Description    : <pre>TODO(这里用一句话描述这个类的作用)</pre></p>
 * <p>ClassName      : CodeGeneratorUtils</p>
 * @author lilinhai 2018年2月7日 下午9:52:07
 * @version 1.0
 */
public class CodeGeneratorUtils
{

    public static void generateToString(IntrospectedTable introspectedTable, TopLevelClass topLevelClass)
    {
        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setReturnType(FullyQualifiedJavaType.getStringInstance());
        method.setName("toString"); //$NON-NLS-1$
        if (introspectedTable.isJava5Targeted())
        {
            method.addAnnotation("@Override"); //$NON-NLS-1$
        }

        method.addBodyLine("StringBuilder sb = new StringBuilder();"); //$NON-NLS-1$
        method.addBodyLine("sb.append(getClass().getSimpleName());"); //$NON-NLS-1$
        method.addBodyLine("sb.append(\" [\");"); //$NON-NLS-1$
        method.addBodyLine("sb.append(\"Hash = \").append(hashCode());"); //$NON-NLS-1$
        StringBuilder sb = new StringBuilder();
        for (Field field : topLevelClass.getFields())
        {
            String property = field.getName();
            sb.setLength(0);
            sb.append("sb.append(\"").append(", ").append(property) //$NON-NLS-1$ //$NON-NLS-2$
                .append("=\")").append(".append(").append(property) //$NON-NLS-1$ //$NON-NLS-2$
                .append(");"); //$NON-NLS-1$
            method.addBodyLine(sb.toString());
        }

        method.addBodyLine("sb.append(\"]\");"); //$NON-NLS-1$
        if (topLevelClass.getSuperClass() != null)
        {
            method.addBodyLine("sb.append(\", from super class \");"); //$NON-NLS-1$
            method.addBodyLine("sb.append(super.toString());"); //$NON-NLS-1$
        }
        method.addBodyLine("return sb.toString();"); //$NON-NLS-1$

        topLevelClass.addMethod(method);
    }
}
