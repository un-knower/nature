/*
 * <p>Copyright : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p> <p>FileName : MapperPlugin.java</p>
 * <p>Package : codegenerator.plugins</p>
 * @Creator lilinhai 2018年2月4日 下午3:36:17
 * @Version V1.0
 */

package pers.linhai.nature.j2ee.generator.plugins;


import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mysql.jdbc.Driver;

import pers.linhai.nature.j2ee.generator.core.api.IntrospectedTable;
import pers.linhai.nature.j2ee.generator.core.api.dom.xml.Document;
import pers.linhai.nature.j2ee.generator.exception.GeneratorException;
import pers.linhai.nature.j2ee.generator.sqlmapgenerator.DatabasePageQueryGenerator;
import pers.linhai.nature.j2ee.generator.sqlmapgenerator.MySQLPageQueryGenerator;


/**
 * <p>Description    : <pre>TODO(这里用一句话描述这个类的作用)</pre></p>
 * <p>ClassName      : MapperPlugin</p>
 * @author lilinhai 2018年2月4日 下午3:36:17
 * @version 1.0
 */
public class SqlMapPlugin extends BasePlugin
{
    
    private static final Map<String, Constructor<? extends DatabasePageQueryGenerator>> DB_PAGE_QUERY_GENERATOR_MAP = new HashMap<String, Constructor<? extends DatabasePageQueryGenerator>>();
    
    static
    {
        try
        {
            List<Class<? extends DatabasePageQueryGenerator>> databasePageQueryGeneratorClassList = new ArrayList<Class<? extends DatabasePageQueryGenerator>>();
            databasePageQueryGeneratorClassList.add(MySQLPageQueryGenerator.class);
            for (Class<? extends DatabasePageQueryGenerator> class1 : databasePageQueryGeneratorClassList)
            {
                Constructor<? extends DatabasePageQueryGenerator> constructor = class1.getConstructor(String.class);
                constructor.setAccessible(true);
                DB_PAGE_QUERY_GENERATOR_MAP.put(Driver.class.getName(), constructor);
            }
        }
        catch (Throwable e)
        {
            e.printStackTrace();
        }
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
     * <p>Overriding Method: lilinhai 2018年2月4日 下午5:27:38</p>
     * <p>Title: sqlMapDocumentGenerated</p>
     * @param document
     * @param introspectedTable
     * @return 
     * @see pers.linhai.nature.j2ee.generator.core.api.PluginAdapter#sqlMapDocumentGenerated(pers.linhai.nature.j2ee.generator.core.api.dom.xml.Document, pers.linhai.nature.j2ee.generator.core.api.IntrospectedTable)
     */
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable)
    {
        String driverClass = introspectedTable.getContext().getJdbcConnectionConfiguration().getDriverClass();
        if (driverClass == null)
        {
            throw new GeneratorException("sqlMapDocumentGenerated找不到数据库驱动");
        }
        
        Constructor<? extends DatabasePageQueryGenerator> databasePageQueryGeneratorConstructor = DB_PAGE_QUERY_GENERATOR_MAP.get(driverClass);
        if (databasePageQueryGeneratorConstructor == null)
        {
            throw new GeneratorException("无法为该数据库生成分页功能：" + driverClass);
        }
        
        try
        {
            //databasePageQueryGeneratorConstructor.newInstance(getTargetPackae("model", "query")).generator(document, introspectedTable);
            return super.sqlMapDocumentGenerated(document, introspectedTable);
        }
        catch (Throwable e)
        {
            e.printStackTrace();
            return false;
        }
    }
}
