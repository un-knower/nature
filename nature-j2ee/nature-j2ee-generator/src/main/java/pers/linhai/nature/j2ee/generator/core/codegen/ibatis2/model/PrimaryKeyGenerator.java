/**
 *    Copyright 2006-2016 the original author or authors.
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
package pers.linhai.nature.j2ee.generator.core.codegen.ibatis2.model;

import static pers.linhai.nature.j2ee.generator.core.internal.util.JavaBeansUtil.getJavaBeansField;
import static pers.linhai.nature.j2ee.generator.core.internal.util.JavaBeansUtil.getJavaBeansGetter;
import static pers.linhai.nature.j2ee.generator.core.internal.util.JavaBeansUtil.getJavaBeansSetter;
import static pers.linhai.nature.j2ee.generator.core.internal.util.messages.Messages.getString;

import java.util.ArrayList;
import java.util.List;

import pers.linhai.nature.j2ee.generator.core.api.CommentGenerator;
import pers.linhai.nature.j2ee.generator.core.api.FullyQualifiedTable;
import pers.linhai.nature.j2ee.generator.core.api.IntrospectedColumn;
import pers.linhai.nature.j2ee.generator.core.api.Plugin;
import pers.linhai.nature.j2ee.generator.core.api.dom.java.CompilationUnit;
import pers.linhai.nature.j2ee.generator.core.api.dom.java.Field;
import pers.linhai.nature.j2ee.generator.core.api.dom.java.FullyQualifiedJavaType;
import pers.linhai.nature.j2ee.generator.core.api.dom.java.JavaVisibility;
import pers.linhai.nature.j2ee.generator.core.api.dom.java.Method;
import pers.linhai.nature.j2ee.generator.core.api.dom.java.TopLevelClass;
import pers.linhai.nature.j2ee.generator.core.codegen.AbstractJavaGenerator;
import pers.linhai.nature.j2ee.generator.core.codegen.RootClassInfo;

/**
 * 
 * @author Jeff Butler
 * 
 */
public class PrimaryKeyGenerator extends AbstractJavaGenerator {

    public PrimaryKeyGenerator() {
        super();
    }

    @Override
    public List<CompilationUnit> getCompilationUnits() {
        FullyQualifiedTable table = introspectedTable.getFullyQualifiedTable();
        progressCallback.startTask(getString(
                "Progress.7", table.toString())); //$NON-NLS-1$
        Plugin plugins = context.getPlugins();
        CommentGenerator commentGenerator = context.getCommentGenerator();

        TopLevelClass topLevelClass = new TopLevelClass(introspectedTable
                .getPrimaryKeyType());
        topLevelClass.setVisibility(JavaVisibility.PUBLIC);
        commentGenerator.addJavaFileComment(topLevelClass);

        String rootClass = getRootClass();
        if (rootClass != null) {
            topLevelClass.setSuperClass(new FullyQualifiedJavaType(rootClass));
            topLevelClass.addImportedType(topLevelClass.getSuperClass());
        }

        for (IntrospectedColumn introspectedColumn : introspectedTable
                .getPrimaryKeyColumns()) {
            if (RootClassInfo.getInstance(rootClass, warnings)
                    .containsProperty(introspectedColumn)) {
                continue;
            }

            Field field = getJavaBeansField(introspectedColumn, context, introspectedTable);
            if (plugins.modelFieldGenerated(field, topLevelClass,
                    introspectedColumn, introspectedTable,
                    Plugin.ModelClassType.PRIMARY_KEY)) {
                topLevelClass.addField(field);
                topLevelClass.addImportedType(field.getType());
            }

            Method method = getJavaBeansGetter(introspectedColumn, context, introspectedTable);
            if (plugins.modelGetterMethodGenerated(method, topLevelClass,
                    introspectedColumn, introspectedTable,
                    Plugin.ModelClassType.PRIMARY_KEY)) {
                topLevelClass.addMethod(method);
            }

            method = getJavaBeansSetter(introspectedColumn, context, introspectedTable);
            if (plugins.modelSetterMethodGenerated(method, topLevelClass,
                    introspectedColumn, introspectedTable,
                    Plugin.ModelClassType.PRIMARY_KEY)) {
                topLevelClass.addMethod(method);
            }
        }

        List<CompilationUnit> answer = new ArrayList<CompilationUnit>();
        if (context.getPlugins().modelPrimaryKeyClassGenerated(
                topLevelClass, introspectedTable)) {
            answer.add(topLevelClass);
        }
        return answer;
    }
}
