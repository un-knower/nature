/*
 * <p>Copyright : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p> <p>FileName : Test.java</p> <p>Package :
 * com.leloven.wanka.dao</p>
 * @Creator lilinhai 2018年2月4日 上午10:05:30
 * @Version V1.0
 */

package pers.linhai.nature.j2ee.generator;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.mysql.jdbc.Driver;

import pers.linhai.nature.j2ee.generator.configuration.CodeConfigurationBuilder;
import pers.linhai.nature.j2ee.generator.configuration.CommentGeneratorConfigurationBuilder;
import pers.linhai.nature.j2ee.generator.configuration.ContextBuilder;
import pers.linhai.nature.j2ee.generator.configuration.JDBCConnectionConfigurationBuilder;
import pers.linhai.nature.j2ee.generator.configuration.JavaClientGeneratorConfigurationBuilder;
import pers.linhai.nature.j2ee.generator.configuration.JavaModelGeneratorConfigurationBuilder;
import pers.linhai.nature.j2ee.generator.configuration.SqlMapGeneratorConfigurationBuilder;
import pers.linhai.nature.j2ee.generator.configuration.TableConfigurationBuilder;
import pers.linhai.nature.j2ee.generator.core.api.MyBatisGenerator;
import pers.linhai.nature.j2ee.generator.core.api.PluginAdapter;
import pers.linhai.nature.j2ee.generator.core.api.ProgressCallback;
import pers.linhai.nature.j2ee.generator.core.config.ModelType;
import pers.linhai.nature.j2ee.generator.core.config.PluginConfiguration;
import pers.linhai.nature.j2ee.generator.core.internal.DefaultShellCallback;
import pers.linhai.nature.j2ee.generator.core.plugins.ToStringPlugin;
import pers.linhai.nature.j2ee.generator.enumer.ConfigurationType;
import pers.linhai.nature.j2ee.generator.enumer.TargetRuntime;
import pers.linhai.nature.j2ee.generator.generator.CodeCommentGenerator;
import pers.linhai.nature.j2ee.generator.plugins.ControllerPlugin;
import pers.linhai.nature.j2ee.generator.plugins.MapperPlugin;
import pers.linhai.nature.j2ee.generator.plugins.ModelPlugin;
import pers.linhai.nature.j2ee.generator.plugins.ServicePlugin;


/**
 * <p>Description    : <pre>TODO(这里用一句话描述这个类的作用)</pre></p>
 * <p>ClassName      : Test</p>
 * @author lilinhai 2018年2月4日 上午10:05:30
 * @version 1.0
 */
public class CodeGenerator
{

    private static final Logger LOGGER = Logger.getLogger(CodeGenerator.class.getName());

    public static void generate(String outPutPath, Map<String, String> params)
    {
        try
        {
            System.setErr(System.out);
            ContextBuilder contextBuilder = new ContextBuilder("MySQL", ModelType.FLAT, TargetRuntime.MY_BATIS3);

            // 添加各种插件
            List<Class<? extends PluginAdapter>> pluginClassList = new ArrayList<Class<? extends PluginAdapter>>();
            pluginClassList.add(ToStringPlugin.class);
            pluginClassList.add(ModelPlugin.class);
            pluginClassList.add(MapperPlugin.class);
            // pluginClassList.add(SqlMapPlugin.class);
            pluginClassList.add(ServicePlugin.class);
            pluginClassList.add(ControllerPlugin.class);
            for (Class<? extends PluginAdapter> pluginClass : pluginClassList)
            {
                PluginConfiguration pluginConfiguration = new PluginConfiguration();
                pluginConfiguration.setConfigurationType(pluginClass.getName());
                pluginConfiguration.addProperty("groupId", params.get("groupId"));
                pluginConfiguration.addProperty("projectName", params.get("artifactId"));
                pluginConfiguration.addProperty("outPutPath", outPutPath);
                contextBuilder.addPluginConfiguration(pluginConfiguration);
            }

            // 添加注释插件
            contextBuilder.commentGeneratorConfiguration(new CommentGeneratorConfigurationBuilder().type(CodeCommentGenerator.class.getName()).build());

            // JDBC连接参数配置
            JDBCConnectionConfigurationBuilder jdbcConnectionConfigurationBuilder = new JDBCConnectionConfigurationBuilder();
            jdbcConnectionConfigurationBuilder.driverClass(Driver.class.getName());
            jdbcConnectionConfigurationBuilder.connectionURL("jdbc:mysql://" + params.get("dbIp") + ":" + params.get("dbPort") + "/" + params.get("dbName") + "?useUnicode=true&characterEncoding=utf8&useSSL=false");
            jdbcConnectionConfigurationBuilder.userId(params.get("dbUsername")).password(params.get("dbPassword"));
            contextBuilder.jdbcConnectionConfiguration(jdbcConnectionConfigurationBuilder.build());

            // 模型生成位置
            JavaModelGeneratorConfigurationBuilder javaModelGeneratorConfigurationBuilder = new JavaModelGeneratorConfigurationBuilder(params.get("groupId"), params.get("artifactId"), outPutPath);
            contextBuilder.javaModelGeneratorConfiguration(javaModelGeneratorConfigurationBuilder.build());

            // sqlmap 映射文件生成
            SqlMapGeneratorConfigurationBuilder sqlMapGeneratorConfigurationBuilder = new SqlMapGeneratorConfigurationBuilder(params.get("groupId"), params.get("artifactId"), outPutPath);
            contextBuilder.sqlMapGeneratorConfiguration(sqlMapGeneratorConfigurationBuilder.build());

            // mapper生成
            JavaClientGeneratorConfigurationBuilder javaClientGeneratorConfigurationBuilder = new JavaClientGeneratorConfigurationBuilder(params.get("groupId"), params.get("artifactId"), outPutPath);
            javaClientGeneratorConfigurationBuilder.type(ConfigurationType.XMLMAPPER);
            contextBuilder.javaClientGeneratorConfiguration(javaClientGeneratorConfigurationBuilder.build());

            // 数据库表配置，默认为所有表生成
            TableConfigurationBuilder TableConfigurationBuilder = new TableConfigurationBuilder(contextBuilder.build());
            contextBuilder.addTableConfiguration(TableConfigurationBuilder.build());

            CodeConfigurationBuilder codeConfigurationBuilder = new CodeConfigurationBuilder(contextBuilder.build());

            // 是否覆盖式
            boolean overwrite = true;
            DefaultShellCallback callback = new DefaultShellCallback(overwrite);

            List<String> warnings = new ArrayList<String>();
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(codeConfigurationBuilder.build(), callback, warnings);

            myBatisGenerator.generate(new ProgressCallback()
            {
                public void startTask(String taskName)
                {
                    LOGGER.info("----startTask--------------------taskName: " + taskName);
                }

                public void saveStarted(int totalTasks)
                {
                    LOGGER.info("----saveStarted--------------------totalTasks: " + totalTasks);
                }

                public void introspectionStarted(int totalTasks)
                {
                    LOGGER.info("----introspectionStarted--------------------totalTasks: " + totalTasks);
                }

                public void generationStarted(int totalTasks)
                {
                    LOGGER.info("----generationStarted--------------------totalTasks: " + totalTasks);
                }

                public void done()
                {
                    for (String warning : warnings)
                    {
                        LOGGER.warning(warning);
                    }
                    LOGGER.info("----done--------------------恭喜你，MyBatis代码生成器已为你成功生成dao, model, mapper xml文件~~~~~");
                }

                public void checkCancel()
                    throws InterruptedException
                {

                }
            });
        }
        catch (Throwable e)
        {
            e.printStackTrace();
        }
    }
}
