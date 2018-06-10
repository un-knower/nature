package pers.linhai.nature.j2ee.generator;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.mysql.cj.jdbc.Driver;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;
import pers.linhai.nature.j2ee.generator.restapi.EntityRestApiCache;
import pers.linhai.nature.utils.ClassUtils;
import pers.linhai.nature.utils.FileUtils;


/*
 * <p>Copyright : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p> <p>FileName : Test.java</p> <p>Package : </p>
 * @Creator lilinhai 2018年2月22日 下午1:07:16
 * @Version V1.0
 */

/**
 * <p>Description    : <pre>TODO(这里用一句话描述这个类的作用)</pre></p>
 * <p>ClassName      : Test</p>
 * @author lilinhai 2018年2月22日 下午1:07:16
 * @version 1.0
 */
public class Main
{

    /**
     * <p>Title         : main lilinhai 2018年2月22日 下午1:07:16</p>
     * @param args 
     * void 
     */
    public static void main(String[] args) throws Exception
    {
        String outPutPath = "C:\\Users\\lilinhai\\Desktop";

        String groupId = "com.meme";
        String artifactId = "crm11";
        String dbIp = "localhost";
        String dbPort = "3306";
        String dbUsername = "root";
        String dbPassword = "LinHai_548";
        String dbName = "crm";
        String dbDriver = Driver.class.getName();
        

        Map<String, String> params = new HashMap<String, String>();
        params.put("groupId", groupId);
        params.put("artifactId", artifactId);
        params.put("projectPackageName", artifactId.replaceAll("\\-", "."));
        params.put("dbName", dbName);
        params.put("dbDriver", dbDriver);
        params.put("dbIp", dbIp);
        params.put("dbPort", dbPort);
        params.put("dbUsername", dbUsername);
        params.put("dbPassword", dbPassword);
        
        // 是否rest接口默认可用，默认不可用，全部抛出异常，根据需要一个个打开
        params.put("restApiDefaultEnabled", "false");

        Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);

        // 设置模板文件的目录
        cfg.setClassForTemplateLoading(Main.class, "/");
        cfg.setDefaultEncoding("UTF-8");
        cfg.setClassicCompatible(true);

        // 父pom
        File outPutDir = new File(outPutPath);
        File artifactDir = new File(outPutDir, artifactId);

        FileUtils.createDir(outPutDir);
        FileUtils.createDir(artifactDir);
        Template temp = cfg.getTemplate("pom.xml"); // 在模板文件目录中寻找名为"name"的模板文件
        Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(artifactDir, "pom.xml")), "UTF-8"));
        temp.process(params, out);
        out.close();
        
        temp = cfg.getTemplate("git.ignore"); // 在模板文件目录中寻找名为"name"的模板文件
        out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(artifactDir, ".gitignore")), "UTF-8"));
        temp.process(params, out);
        out.close();
        
        temp = cfg.getTemplate("README.md"); // 在模板文件目录中寻找名为"name"的模板文件
        out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(artifactDir, "README.md")), "UTF-8"));
        temp.process(params, out);
        out.close();

        // APP模块
        buildApp(params, cfg, artifactDir);
        
        // common 模块
        buildCommon(params, cfg, artifactDir);
        
        // component 模块
        buildComponent(params, cfg, artifactDir);
        
        // dao模块
        buildDao(params, cfg, artifactDir);
        
        // model模块
        buildModel(params, cfg, artifactDir);
        
        // service模块
        buildService(params, cfg, artifactDir);
        
        // web模块
        buildWeb(params, cfg, artifactDir);
        
        // 生成业务代码
        CodeGenerator.generate(artifactDir.getAbsolutePath(), params);
        
        // 代码生成完后，生成对应的rest-api在线文档
        File appArtifactDir = new File(artifactDir, artifactId + "-application");
        File appResourcesDoc = new File(appArtifactDir, "doc/api");
        FileUtils.createDir(appResourcesDoc);
        Collections.sort(EntityRestApiCache.getInstance().getEntityRestApiList());
        params.put("apiJsonData", JSON.toJSONString(EntityRestApiCache.getInstance().getEntityRestApiList()));
        build(cfg, params, new File(ClassUtils.getDefaultClassLoader().getResource("app/doc").getPath()), appResourcesDoc, "app/doc/");
    }

    /**
     * <p>Title         : buildWeb lilinhai 2018年2月22日 下午6:16:59</p>
     * @param artifactId
     * @param params
     * @param cfg
     * @param artifactDir
     * @throws Exception 
     * void 
     */ 
    private static void buildWeb(Map<String, String> params, Configuration cfg, File artifactDir)
        throws Exception
    {
        Template temp;
        Writer out;
        File daoArtifactDir = new File(artifactDir, params.get("artifactId") + "-web");
        FileUtils.createDir(daoArtifactDir);
        
        temp = cfg.getTemplate("web/pom.xml");
        out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(daoArtifactDir, "pom.xml")), "UTF-8"));
        temp.process(params, out);
        out.close();
        
        File resources = new File(daoArtifactDir, "src/main/resources");
        FileUtils.createDir(resources);
        File java = new File(daoArtifactDir, "src/main/java");
        FileUtils.createDir(java);
        FileUtils.createDir(new File(daoArtifactDir, "src/test/java"));
    }

    /**
     * <p>Title         : buildService lilinhai 2018年2月22日 下午6:14:05</p>
     * @param artifactId
     * @param params
     * @param cfg
     * @param artifactDir
     * @throws Exception 
     * void 
     */ 
    private static void buildService(Map<String, String> params, Configuration cfg, File artifactDir)
        throws Exception
    {
        Template temp;
        Writer out;
        File daoArtifactDir = new File(artifactDir, params.get("artifactId") + "-service");
        FileUtils.createDir(daoArtifactDir);
        
        temp = cfg.getTemplate("service/pom.xml");
        out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(daoArtifactDir, "pom.xml")), "UTF-8"));
        temp.process(params, out);
        out.close();
        
        File resources = new File(daoArtifactDir, "src/main/resources");
        FileUtils.createDir(resources);
        File java = new File(daoArtifactDir, "src/main/java");
        FileUtils.createDir(java);
        FileUtils.createDir(new File(daoArtifactDir, "src/test/java"));
    }

    /**
     * <p>Title         : buildModel lilinhai 2018年2月22日 下午6:12:08</p>
     * <p>Description   : <pre>TODO(这里用一句话描述这个方法的作用)</pre></p>
     * @param artifactId
     * @param params
     * @param cfg
     * @param artifactDir
     * @throws TemplateNotFoundException
     * @throws MalformedTemplateNameException
     * @throws ParseException
     * @throws IOException
     * @throws UnsupportedEncodingException
     * @throws FileNotFoundException
     * @throws TemplateException 
     * void 
     */ 
    private static void buildModel(Map<String, String> params, Configuration cfg, File artifactDir)
        throws TemplateNotFoundException,
        MalformedTemplateNameException,
        ParseException,
        IOException,
        UnsupportedEncodingException,
        FileNotFoundException,
        TemplateException
    {
        Template temp;
        Writer out;
        File daoArtifactDir = new File(artifactDir, params.get("artifactId") + "-model");
        FileUtils.createDir(daoArtifactDir);
        
        temp = cfg.getTemplate("model/pom.xml");
        out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(daoArtifactDir, "pom.xml")), "UTF-8"));
        temp.process(params, out);
        out.close();
        
        File resources = new File(daoArtifactDir, "src/main/resources");
        FileUtils.createDir(resources);
        File java = new File(daoArtifactDir, "src/main/java");
        FileUtils.createDir(java);
        FileUtils.createDir(new File(daoArtifactDir, "src/test/java"));
    }

    /**
     * <p>Title         : buildDao lilinhai 2018年2月22日 下午6:08:00</p>
     * @param artifactId
     * @param params
     * @param cfg
     * @param artifactDir
     * @throws Exception
     * void 
     */ 
    private static void buildDao(Map<String, String> params, Configuration cfg, File artifactDir)
        throws Exception
    {
        Template temp;
        Writer out;
        File daoArtifactDir = new File(artifactDir, params.get("artifactId") + "-dao");
        FileUtils.createDir(daoArtifactDir);
        
        temp = cfg.getTemplate("dao/pom.xml");
        out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(daoArtifactDir, "pom.xml")), "UTF-8"));
        temp.process(params, out);
        out.close();
        
        File resources = new File(daoArtifactDir, "src/main/resources");
        FileUtils.createDir(resources);
        File java = new File(daoArtifactDir, "src/main/java");
        FileUtils.createDir(java);
        FileUtils.createDir(new File(daoArtifactDir, "src/test/java"));
    }

    /**
     * <p>Title         : buildCommon lilinhai 2018年2月22日 下午5:56:44</p>
     * <p>Description   : <pre>TODO(这里用一句话描述这个方法的作用)</pre></p>
     * @param params
     * @param cfg
     * @param artifactDir
     * void 
     */ 
    private static void buildCommon(Map<String, String> params, Configuration cfg, File artifactDir)
        throws Exception
    {
        Template temp;
        Writer out;
        File commonArtifactDir = new File(artifactDir, params.get("artifactId") + "-common");
        FileUtils.createDir(commonArtifactDir);
        
        temp = cfg.getTemplate("common/pom.xml");
        out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(commonArtifactDir, "pom.xml")), "UTF-8"));
        temp.process(params, out);
        out.close();
        
        File resources = new File(commonArtifactDir, "src/main/resources");
        FileUtils.createDir(resources);
        File java = new File(commonArtifactDir, "src/main/java");
        FileUtils.createDir(java);
        FileUtils.createDir(new File(commonArtifactDir, "src/test/java"));
    }
    
    /**
     * <p>Title         : buildCommon lilinhai 2018年2月22日 下午5:56:44</p>
     * <p>Description   : <pre>TODO(这里用一句话描述这个方法的作用)</pre></p>
     * @param params
     * @param cfg
     * @param artifactDir
     * void 
     */ 
    private static void buildComponent(Map<String, String> params, Configuration cfg, File artifactDir)
        throws Exception
    {
        Template temp;
        Writer out;
        File componentArtifactDir = new File(artifactDir, params.get("artifactId") + "-component");
        FileUtils.createDir(componentArtifactDir);
        
        temp = cfg.getTemplate("component/pom.xml");
        out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(componentArtifactDir, "pom.xml")), "UTF-8"));
        temp.process(params, out);
        out.close();
        
        File resources = new File(componentArtifactDir, "src/main/resources");
        FileUtils.createDir(resources);
        File java = new File(componentArtifactDir, "src/main/java");
        FileUtils.createDir(java);
        FileUtils.createDir(new File(componentArtifactDir, "src/test/java"));
    }

    /**
     * <p>Title         : buildApp lilinhai 2018年2月22日 下午5:27:35</p>
     * @param params
     * @param cfg
     * @param artifactDir
     * @throws Exception 
     * void 
     */ 
    private static void buildApp(Map<String, String> params, Configuration cfg, File artifactDir)
        throws Exception
    {
        Template temp;
        Writer out;
        File appArtifactDir = new File(artifactDir, params.get("artifactId") + "-application");
        FileUtils.createDir(appArtifactDir);
        temp = cfg.getTemplate("app/pom.xml");
        out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(appArtifactDir, "pom.xml")), "UTF-8"));
        temp.process(params, out);
        out.close();
        
        File appResources = new File(appArtifactDir, "src/main/resources");
        FileUtils.createDir(appResources);
        temp = cfg.getTemplate("app/application.yml");
        out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(appResources, "application.yml")), "UTF-8"));
        temp.process(params, out);
        out.close();
        
        File appResourcesConfig = new File(appResources, params.get("artifactId"));
        FileUtils.createDir(appResourcesConfig);
        build0(cfg, params, new File(ClassUtils.getDefaultClassLoader().getResource("app/config").getPath()), appResourcesConfig, "app/config/");
        
        File appResourcesServer = new File(appResources, "server");
        FileUtils.createDir(appResourcesServer);
        build(cfg, params, new File(ClassUtils.getDefaultClassLoader().getResource("app/server").getPath()), appResourcesServer, "app/server/");
        
        File appResourcesSpring = new File(appResources, "spring");
        FileUtils.createDir(appResourcesSpring);
        build(cfg, params, new File(ClassUtils.getDefaultClassLoader().getResource("app/spring").getPath()), appResourcesSpring, "app/spring/");
        
        File appResourcesDatasource = new File(appResources, "nature");
        FileUtils.createDir(appResourcesDatasource);
        build(cfg, params, new File(ClassUtils.getDefaultClassLoader().getResource("app/nature").getPath()), appResourcesDatasource, "app/nature/");
        
        File appResourcesLog4j2 = new File(appResources, "log4j2");
        FileUtils.createDir(appResourcesLog4j2);
        build(cfg, params, new File(ClassUtils.getDefaultClassLoader().getResource("app/log4j2").getPath()), appResourcesLog4j2, "app/log4j2/");
        
        File appJava = new File(appArtifactDir, "src/main/java");
        FileUtils.createDir(appJava);
        FileUtils.createDir(new File(appArtifactDir, "src/test/java"));
        File appJavaPackage = new File(appJava, params.get("groupId").replaceAll("\\.", "/") + "/" + params.get("projectPackageName").replaceAll("\\.", "/"));
        FileUtils.createDir(appJavaPackage);
        temp = cfg.getTemplate("app/Application.java");
        out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(appJavaPackage, "Application.java")), "UTF-8"));
        temp.process(params, out);
        out.close();
    }
    
    private static void build(Configuration cfg, Object params, File sourceDir, File destDir, String templatePrefix) throws Exception
    {
        Writer out = null;
        Template temp = null;
        for (File f : sourceDir.listFiles())
        {
            temp = cfg.getTemplate(templatePrefix + f.getName());
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(destDir, f.getName())), "UTF-8"));
            temp.process(params, out);
            out.close();
        }
    }
    
    private static void build0(Configuration cfg, Map<String, String> params, File sourceDir, File destDir, String templatePrefix) throws Exception
    {
        Writer out = null;
        Template temp = null;
        for (File f : sourceDir.listFiles())
        {
            temp = cfg.getTemplate(templatePrefix + f.getName());
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(destDir, f.getName().replace("application", params.get("artifactId")))), "UTF-8"));
            temp.process(params, out);
            out.close();
        }
    }
}
