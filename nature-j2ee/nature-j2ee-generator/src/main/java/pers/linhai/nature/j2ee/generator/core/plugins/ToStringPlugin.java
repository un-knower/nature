/**
 * Copyright 2006-2017 the original author or authors. Licensed under the Apache License, Version
 * 2.0 (the "License"); you may not use this file except in compliance with the License. You may
 * obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by
 * applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See
 * the License for the specific language governing permissions and limitations under the License.
 */
package pers.linhai.nature.j2ee.generator.core.plugins;


import static pers.linhai.nature.j2ee.generator.core.internal.util.StringUtility.isTrue;

import java.util.List;
import java.util.Properties;

import pers.linhai.nature.j2ee.generator.core.api.IntrospectedTable;
import pers.linhai.nature.j2ee.generator.core.api.PluginAdapter;
import pers.linhai.nature.j2ee.generator.core.api.dom.java.TopLevelClass;
import pers.linhai.nature.j2ee.generator.utils.CodeGeneratorUtils;


public class ToStringPlugin extends PluginAdapter
{

    @SuppressWarnings("unused")
    private boolean useToStringFromRoot;

    @Override
    public void setProperties(Properties properties)
    {
        super.setProperties(properties);
        useToStringFromRoot = isTrue(properties.getProperty("useToStringFromRoot")); //$NON-NLS-1$
    }

    @Override
    public boolean validate(List<String> warnings)
    {
        return true;
    }

    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable)
    {
        generateToString(introspectedTable, topLevelClass);
        return true;
    }

    @Override
    public boolean modelRecordWithBLOBsClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable)
    {
        generateToString(introspectedTable, topLevelClass);
        return true;
    }

    @Override
    public boolean modelPrimaryKeyClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable)
    {
        generateToString(introspectedTable, topLevelClass);
        return true;
    }

    private void generateToString(IntrospectedTable introspectedTable, TopLevelClass topLevelClass)
    {
        CodeGeneratorUtils.generateToString(introspectedTable, topLevelClass);
    }
}
