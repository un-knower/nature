/*
 * 文 件 名: pers.linhai.esdao.core.TypeConfiguration.java
 * 版    权: XXX Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述: <描述>
 * 修 改 人: shinelon
 * 修改时间: 2017年3月4日 下午3:03:00
 * 跟踪单号: <跟踪单号>
 * 修改单号: <修改单号>
 * 修改内容: <修改内容>
 */
package pers.linhai.nature.indexaccess.model.type;

import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import pers.linhai.nature.indexaccess.annotation.Mapping;
import pers.linhai.nature.indexaccess.core.DataTypeParser;
import pers.linhai.nature.indexaccess.exception.XContentBuilderException;
import pers.linhai.nature.indexaccess.model.core.DataTypeCollection;
import pers.linhai.nature.indexaccess.model.datatypes.DataType;
import pers.linhai.nature.indexaccess.model.index.Index;
import pers.linhai.nature.indexaccess.utils.NamingUtils;
import pers.linhai.nature.utils.StringUtils;

/**
 * 根据实体类对象初始化实体配置信息
 * @author: shinelon
 * @date: 2017年3月4日 下午3:03:00
 *
 * @ClassName: 	[TypeClass]
 * @version: [版本号]
 * @since: [产品/模块版本]
 */
public class MappingConfiguration<T extends Type>
{
    
    /**
     * Type实体类
     */
    private Class<T> typeClass;
    
    /**
     * java-field对象转换成的es-DataType集合
     */
    private DataTypeCollection dataTypeCollection;
    
    /**
     * ES表结构配置信息
     */
    private XContentBuilder mappingStructure;

    /**
     * 索引表的名字
     */
    private String typeName;
    
    /**
     * 所属index
     */
    private Index index;
    
    /**
     * <pre>
     *      The _all field is a special catch-all field which concatenates the values of all of the other fields into one big string,
     *   using space as a delimiter, which is then analyzed and indexed, but not stored. This means that it can be searched, but not retrieved.
     *
     *      The _all field allows you to search for values in documents without knowing which field contains the value. 
     *   This makes it a useful option when getting started with a new dataset.
     *      
     *      Note All values treated as strings
     * </pre>
     * @return boolean
     */
    private boolean enabledAll;
    
    /** 
     * <构造函数>
     * @author: shinelon
     * @date: 2017年3月4日 下午3:52:39 
     *
     * @param typeClass
     * @param index 
     */
    public MappingConfiguration(Class<T> typeClass, Index index)
    {
        this.typeClass = typeClass;
        this.index = index;
        
        // 如果类上声明有该注解，则解析该注解
        if (typeClass.isAnnotationPresent(Mapping.class))
        {
            Mapping t = typeClass.getAnnotation(Mapping.class);
            typeName = t.name();
            enabledAll = t.enabledAll();
        }
        
        typeName = StringUtils.isEmpty(typeName) ? NamingUtils.name(typeClass.getSimpleName()) : typeName;
        
        // 解析该type实体的注解字段
        parse();
    }

    /** 
     * 解析该type实体的注解字段
     * void
     */
    private void parse()
    {
        try
        {
            dataTypeCollection = DataTypeParser.parse(typeClass);
            
            //实体对应的es索引表结构定义
            mappingStructure = XContentFactory.jsonBuilder();
            mappingStructure.startObject().startObject(this.typeName).startObject("_all");
            mappingStructure.field("enabled", enabledAll);
            mappingStructure.endObject();
            mappingStructure.startObject("properties");
            for (DataType dt : dataTypeCollection.getDataTypeList())
            {
                dt.buildMappingStructure(mappingStructure);
            }
            
            //实体对应的es索引表结构定义结束
            mappingStructure.endObject().endObject().endObject();
        }
        catch (Throwable e)
        {
            throw new XContentBuilderException("TypeEntityConfiguration.parse", e);
        }
    }

    /**
     * 返回 实体对应的es索引表结构定义
     *
     * @return typeMappingTableStructure
     */
    public XContentBuilder getMappingStructure()
    {
        return mappingStructure;
    }

    /**
     * 返回 typeFieldMap
     *
     * @return typeFieldMap
     */
    public DataTypeCollection getDataTypeCollection()
    {
        return dataTypeCollection;
    }

    /**
     * 返回 typeName
     *
     * @return typeName
     */
    public String typeName()
    {
        return typeName;
    }
    
    /**
     * 返回 index
     *
     * @return index
     */
    public Index index()
    {
        return index;
    }

    /**
     * 返回 typeClass
     *
     * @return typeClass
     */
    public Class<T> getTypeClass()
    {
        return typeClass;
    }
    
}
