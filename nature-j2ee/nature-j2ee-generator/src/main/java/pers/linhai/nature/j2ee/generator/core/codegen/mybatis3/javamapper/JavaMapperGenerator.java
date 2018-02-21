/**
 * Copyright 2006-2017 the original author or authors. Licensed under the Apache License, Version
 * 2.0 (the "License"); you may not use this file except in compliance with the License. You may
 * obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by
 * applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See
 * the License for the specific language governing permissions and limitations under the License.
 */
package pers.linhai.nature.j2ee.generator.core.codegen.mybatis3.javamapper;


import static pers.linhai.nature.j2ee.generator.core.internal.util.StringUtility.stringHasValue;
import static pers.linhai.nature.j2ee.generator.core.internal.util.messages.Messages.getString;

import java.util.ArrayList;
import java.util.List;

import pers.linhai.nature.j2ee.generator.core.api.CommentGenerator;
import pers.linhai.nature.j2ee.generator.core.api.dom.java.CompilationUnit;
import pers.linhai.nature.j2ee.generator.core.api.dom.java.FullyQualifiedJavaType;
import pers.linhai.nature.j2ee.generator.core.api.dom.java.Interface;
import pers.linhai.nature.j2ee.generator.core.api.dom.java.JavaVisibility;
import pers.linhai.nature.j2ee.generator.core.codegen.AbstractJavaClientGenerator;
import pers.linhai.nature.j2ee.generator.core.codegen.AbstractXmlGenerator;
import pers.linhai.nature.j2ee.generator.core.codegen.mybatis3.javamapper.elements.AbstractJavaMapperMethodGenerator;
import pers.linhai.nature.j2ee.generator.core.codegen.mybatis3.javamapper.elements.CountByExampleMethodGenerator;
import pers.linhai.nature.j2ee.generator.core.codegen.mybatis3.javamapper.elements.DeleteByExampleMethodGenerator;
import pers.linhai.nature.j2ee.generator.core.codegen.mybatis3.javamapper.elements.DeleteByPrimaryKeyMethodGenerator;
import pers.linhai.nature.j2ee.generator.core.codegen.mybatis3.javamapper.elements.InsertMethodGenerator;
import pers.linhai.nature.j2ee.generator.core.codegen.mybatis3.javamapper.elements.InsertSelectiveMethodGenerator;
import pers.linhai.nature.j2ee.generator.core.codegen.mybatis3.javamapper.elements.SelectByExampleWithBLOBsMethodGenerator;
import pers.linhai.nature.j2ee.generator.core.codegen.mybatis3.javamapper.elements.SelectByExampleWithoutBLOBsMethodGenerator;
import pers.linhai.nature.j2ee.generator.core.codegen.mybatis3.javamapper.elements.SelectByPrimaryKeyMethodGenerator;
import pers.linhai.nature.j2ee.generator.core.codegen.mybatis3.javamapper.elements.UpdateByExampleSelectiveMethodGenerator;
import pers.linhai.nature.j2ee.generator.core.codegen.mybatis3.javamapper.elements.UpdateByExampleWithBLOBsMethodGenerator;
import pers.linhai.nature.j2ee.generator.core.codegen.mybatis3.javamapper.elements.UpdateByExampleWithoutBLOBsMethodGenerator;
import pers.linhai.nature.j2ee.generator.core.codegen.mybatis3.javamapper.elements.UpdateByPrimaryKeySelectiveMethodGenerator;
import pers.linhai.nature.j2ee.generator.core.codegen.mybatis3.javamapper.elements.UpdateByPrimaryKeyWithBLOBsMethodGenerator;
import pers.linhai.nature.j2ee.generator.core.codegen.mybatis3.javamapper.elements.UpdateByPrimaryKeyWithoutBLOBsMethodGenerator;
import pers.linhai.nature.j2ee.generator.core.codegen.mybatis3.xmlmapper.XMLMapperGenerator;
import pers.linhai.nature.j2ee.generator.core.config.PropertyRegistry;


/**
 * @author Jeff Butler
 * 
 */
public class JavaMapperGenerator extends AbstractJavaClientGenerator
{

    public JavaMapperGenerator()
    {
        super(true);
    }

    public JavaMapperGenerator(boolean requiresMatchedXMLGenerator)
    {
        super(requiresMatchedXMLGenerator);
    }

    @Override
    public List<CompilationUnit> getCompilationUnits()
    {
        progressCallback.startTask(getString("Progress.17", //$NON-NLS-1$
            introspectedTable.getFullyQualifiedTable().toString()));
        CommentGenerator commentGenerator = context.getCommentGenerator();

        FullyQualifiedJavaType type = new FullyQualifiedJavaType(introspectedTable.getMyBatis3JavaMapperType());
        Interface interfaze = new Interface(type);
        interfaze.setVisibility(JavaVisibility.PUBLIC);
        commentGenerator.addJavaFileComment(interfaze);

        String rootInterface = introspectedTable.getTableConfigurationProperty(PropertyRegistry.ANY_ROOT_INTERFACE);
        if (!stringHasValue(rootInterface))
        {
            rootInterface = context.getJavaClientGeneratorConfiguration().getProperty(PropertyRegistry.ANY_ROOT_INTERFACE);
        }

        if (stringHasValue(rootInterface))
        {
            FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType(rootInterface);
            interfaze.addSuperInterface(fqjt);
            interfaze.addImportedType(fqjt);
        }

        addCountByExampleMethod(interfaze);
        addDeleteByExampleMethod(interfaze);
        addDeleteByPrimaryKeyMethod(interfaze);
        addInsertMethod(interfaze);
        addInsertSelectiveMethod(interfaze);
        addSelectByExampleWithBLOBsMethod(interfaze);
        addSelectByExampleWithoutBLOBsMethod(interfaze);
        addSelectByPrimaryKeyMethod(interfaze);
        addUpdateByExampleSelectiveMethod(interfaze);
        addUpdateByExampleWithBLOBsMethod(interfaze);
        addUpdateByExampleWithoutBLOBsMethod(interfaze);
        addUpdateByPrimaryKeySelectiveMethod(interfaze);
        addUpdateByPrimaryKeyWithBLOBsMethod(interfaze);
        addUpdateByPrimaryKeyWithoutBLOBsMethod(interfaze);

        List<CompilationUnit> answer = new ArrayList<CompilationUnit>();
        if (context.getPlugins().clientGenerated(interfaze, null, introspectedTable))
        {
            answer.add(interfaze);
        }

        List<CompilationUnit> extraCompilationUnits = getExtraCompilationUnits();
        if (extraCompilationUnits != null)
        {
            answer.addAll(extraCompilationUnits);
        }

        return answer;
    }

    protected void addCountByExampleMethod(Interface interfaze)
    {
        if (introspectedTable.getRules().generateCountByExample())
        {
            AbstractJavaMapperMethodGenerator methodGenerator = new CountByExampleMethodGenerator();
            initializeAndExecuteGenerator(methodGenerator, interfaze);
        }
    }

    protected void addDeleteByExampleMethod(Interface interfaze)
    {
        if (introspectedTable.getRules().generateDeleteByExample())
        {
            AbstractJavaMapperMethodGenerator methodGenerator = new DeleteByExampleMethodGenerator();
            initializeAndExecuteGenerator(methodGenerator, interfaze);
        }
    }

    protected void addDeleteByPrimaryKeyMethod(Interface interfaze)
    {
        if (introspectedTable.getRules().generateDeleteByPrimaryKey())
        {
            AbstractJavaMapperMethodGenerator methodGenerator = new DeleteByPrimaryKeyMethodGenerator(false);
            initializeAndExecuteGenerator(methodGenerator, interfaze);
        }
    }

    protected void addInsertMethod(Interface interfaze)
    {
        if (introspectedTable.getRules().generateInsert())
        {
            AbstractJavaMapperMethodGenerator methodGenerator = new InsertMethodGenerator(false);
            initializeAndExecuteGenerator(methodGenerator, interfaze);
        }
    }

    protected void addInsertSelectiveMethod(Interface interfaze)
    {
        if (introspectedTable.getRules().generateInsertSelective())
        {
            AbstractJavaMapperMethodGenerator methodGenerator = new InsertSelectiveMethodGenerator();
            initializeAndExecuteGenerator(methodGenerator, interfaze);
        }
    }

    protected void addSelectByExampleWithBLOBsMethod(Interface interfaze)
    {
        if (introspectedTable.getRules().generateSelectByExampleWithBLOBs())
        {
            AbstractJavaMapperMethodGenerator methodGenerator = new SelectByExampleWithBLOBsMethodGenerator();
            initializeAndExecuteGenerator(methodGenerator, interfaze);
        }
    }

    protected void addSelectByExampleWithoutBLOBsMethod(Interface interfaze)
    {
        if (introspectedTable.getRules().generateSelectByExampleWithoutBLOBs())
        {
            AbstractJavaMapperMethodGenerator methodGenerator = new SelectByExampleWithoutBLOBsMethodGenerator();
            initializeAndExecuteGenerator(methodGenerator, interfaze);
        }
    }

    protected void addSelectByPrimaryKeyMethod(Interface interfaze)
    {
        if (introspectedTable.getRules().generateSelectByPrimaryKey())
        {
            AbstractJavaMapperMethodGenerator methodGenerator = new SelectByPrimaryKeyMethodGenerator(false);
            initializeAndExecuteGenerator(methodGenerator, interfaze);
        }
    }

    protected void addUpdateByExampleSelectiveMethod(Interface interfaze)
    {
        if (introspectedTable.getRules().generateUpdateByExampleSelective())
        {
            AbstractJavaMapperMethodGenerator methodGenerator = new UpdateByExampleSelectiveMethodGenerator();
            initializeAndExecuteGenerator(methodGenerator, interfaze);
        }
    }

    protected void addUpdateByExampleWithBLOBsMethod(Interface interfaze)
    {
        if (introspectedTable.getRules().generateUpdateByExampleWithBLOBs())
        {
            AbstractJavaMapperMethodGenerator methodGenerator = new UpdateByExampleWithBLOBsMethodGenerator();
            initializeAndExecuteGenerator(methodGenerator, interfaze);
        }
    }

    protected void addUpdateByExampleWithoutBLOBsMethod(Interface interfaze)
    {
        if (introspectedTable.getRules().generateUpdateByExampleWithoutBLOBs())
        {
            AbstractJavaMapperMethodGenerator methodGenerator = new UpdateByExampleWithoutBLOBsMethodGenerator();
            initializeAndExecuteGenerator(methodGenerator, interfaze);
        }
    }

    protected void addUpdateByPrimaryKeySelectiveMethod(Interface interfaze)
    {
        if (introspectedTable.getRules().generateUpdateByPrimaryKeySelective())
        {
            AbstractJavaMapperMethodGenerator methodGenerator = new UpdateByPrimaryKeySelectiveMethodGenerator();
            initializeAndExecuteGenerator(methodGenerator, interfaze);
        }
    }

    protected void addUpdateByPrimaryKeyWithBLOBsMethod(Interface interfaze)
    {
        if (introspectedTable.getRules().generateUpdateByPrimaryKeyWithBLOBs())
        {
            AbstractJavaMapperMethodGenerator methodGenerator = new UpdateByPrimaryKeyWithBLOBsMethodGenerator();
            initializeAndExecuteGenerator(methodGenerator, interfaze);
        }
    }

    protected void addUpdateByPrimaryKeyWithoutBLOBsMethod(Interface interfaze)
    {
        if (introspectedTable.getRules().generateUpdateByPrimaryKeyWithoutBLOBs())
        {
            AbstractJavaMapperMethodGenerator methodGenerator = new UpdateByPrimaryKeyWithoutBLOBsMethodGenerator();
            initializeAndExecuteGenerator(methodGenerator, interfaze);
        }
    }

    protected void initializeAndExecuteGenerator(AbstractJavaMapperMethodGenerator methodGenerator, Interface interfaze)
    {
        methodGenerator.setContext(context);
        methodGenerator.setIntrospectedTable(introspectedTable);
        methodGenerator.setProgressCallback(progressCallback);
        methodGenerator.setWarnings(warnings);
        methodGenerator.addInterfaceElements(interfaze);
    }

    public List<CompilationUnit> getExtraCompilationUnits()
    {
        return null;
    }

    @Override
    public AbstractXmlGenerator getMatchedXMLGenerator()
    {
        return new XMLMapperGenerator();
    }
}
