/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : DatabasePageQueryGenerator.java</p>
 * <p>Package     : codegenerator.sqlmapgenerator</p>
 * @Creator lilinhai 2018年2月7日 下午3:15:37
 * @Version  V1.0  
 */ 

package pers.linhai.nature.j2ee.generator.sqlmapgenerator;

import pers.linhai.nature.j2ee.generator.core.api.IntrospectedTable;
import pers.linhai.nature.j2ee.generator.core.api.dom.xml.Document;

/**
 * <p>Description    : <pre>TODO(这里用一句话描述这个类的作用)</pre></p>
 * <p>ClassName      : DatabasePageQueryGenerator</p>
 * @author lilinhai 2018年2月7日 下午3:15:37
 * @version 1.0
 */
public abstract class DatabasePageQueryGenerator
{

    protected String targetPackae;
    
    /**
     * <p>Title        : DatabasePageQueryGenerator lilinhai 2018年2月7日 下午3:23:28</p>
     * <p>Description  : <pre>TODO(这里用一句话描述这个方法的作用)</pre></p>
     * @param targetPackae 
     */ 
    public DatabasePageQueryGenerator(String targetPackae)
    {
        this.targetPackae = targetPackae;
    }

    /**
     * 根据数据库类型自动生成分页查询
     * <p>Title         : generator lilinhai 2018年2月7日 下午3:16:46</p>
     * @param document
     * @param introspectedTable 
     * void
     */
    public abstract void generator(Document document, IntrospectedTable introspectedTable);
}
