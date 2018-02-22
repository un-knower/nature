/**
 *    Copyright 2006-2017 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package pers.linhai.nature.j2ee.generator.core.codegen.ibatis2.dao.elements;

import java.util.Set;
import java.util.TreeSet;

import pers.linhai.nature.j2ee.generator.core.api.dom.java.FullyQualifiedJavaType;
import pers.linhai.nature.j2ee.generator.core.api.dom.java.Interface;
import pers.linhai.nature.j2ee.generator.core.api.dom.java.JavaVisibility;
import pers.linhai.nature.j2ee.generator.core.api.dom.java.Method;
import pers.linhai.nature.j2ee.generator.core.api.dom.java.Parameter;
import pers.linhai.nature.j2ee.generator.core.api.dom.java.TopLevelClass;

/**
 * Generates the Update By primary Key with BLOBs method.
 * 
 * @author Jeff Butler
 * 
 */
public class UpdateByPrimaryKeyWithBLOBsMethodGenerator extends
        AbstractDAOElementGenerator {

    private boolean generateForJava5;

    public UpdateByPrimaryKeyWithBLOBsMethodGenerator(boolean generateForJava5) {
        super();
        this.generateForJava5 = generateForJava5;
    }

    @Override
    public void addImplementationElements(TopLevelClass topLevelClass) {
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
        Method method = getMethodShell(importedTypes);
        if (generateForJava5) {
            method.addAnnotation("@Override"); //$NON-NLS-1$
        }

        StringBuilder sb = new StringBuilder();
        sb.append("int rows = "); //$NON-NLS-1$
        sb.append(daoTemplate.getUpdateMethod(introspectedTable
                .getIbatis2SqlMapNamespace(), introspectedTable
                .getUpdateByPrimaryKeyWithBLOBsStatementId(), "record")); //$NON-NLS-1$
        method.addBodyLine(sb.toString());

        method.addBodyLine("return rows;"); //$NON-NLS-1$

        if (context.getPlugins()
                .clientUpdateByPrimaryKeyWithBLOBsMethodGenerated(method,
                        topLevelClass, introspectedTable)) {
            topLevelClass.addImportedTypes(importedTypes);
            topLevelClass.addMethod(method);
        }
    }

    @Override
    public void addInterfaceElements(Interface interfaze) {
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
        Method method = getMethodShell(importedTypes);

        if (context.getPlugins()
                .clientUpdateByPrimaryKeyWithBLOBsMethodGenerated(method,
                        interfaze, introspectedTable)) {
            interfaze.addImportedTypes(importedTypes);
            interfaze.addMethod(method);
        }
    }

    private Method getMethodShell(Set<FullyQualifiedJavaType> importedTypes) {
        FullyQualifiedJavaType parameterType;

        if (introspectedTable.getRules().generateRecordWithBLOBsClass()) {
            parameterType = new FullyQualifiedJavaType(introspectedTable
                    .getRecordWithBLOBsType());
        } else {
            parameterType = new FullyQualifiedJavaType(introspectedTable
                    .getBaseRecordType());
        }

        importedTypes.add(parameterType);

        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        method.setName(getDAOMethodNameCalculator()
                .getUpdateByPrimaryKeyWithBLOBsMethodName(introspectedTable));
        method.addParameter(new Parameter(parameterType, "record")); //$NON-NLS-1$

        for (FullyQualifiedJavaType fqjt : daoTemplate.getCheckedExceptions()) {
            method.addException(fqjt);
            importedTypes.add(fqjt);
        }

        context.getCommentGenerator().addGeneralMethodComment(method,
                introspectedTable);

        return method;
    }
}