/*
 * 文 件 名: pers.linhai.esdao.datatype.StringType.java
 * 版    权: XXX Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述: <描述>
 * 修 改 人: shinelon
 * 修改时间: 2017年3月5日 下午2:24:59
 * 跟踪单号: <跟踪单号>
 * 修改单号: <修改单号>
 * 修改内容: <修改内容>
 */
package pers.linhai.nature.indexaccess.model.datatypes.quote;

import java.io.IOException;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;

import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.settings.Settings.Builder;
import org.elasticsearch.common.xcontent.XContentBuilder;

import pers.linhai.nature.indexaccess.core.DataTypeParser;
import pers.linhai.nature.indexaccess.core.impls.ObjectFieldConverterImpl;
import pers.linhai.nature.indexaccess.interfaces.ObjectFieldConverter;
import pers.linhai.nature.indexaccess.model.core.DataTypeCollection;
import pers.linhai.nature.indexaccess.model.datatypes.DataType;
import pers.linhai.nature.indexaccess.model.datatypes.quote.ObjectType.ObjectField;
import pers.linhai.nature.indexaccess.model.enumer.Dynamic;
import pers.linhai.nature.indexaccess.model.enumer.Enabled;
import pers.linhai.nature.reflect.ConstructorAccess;

/**
 * @Description: 对象类型，该对象里面的属性是任意字段类型的组合。对象里面的字段也可以是对象类型
 * @author: shinelon
 * @date: 2017年3月5日 下午2:24:59
 *
 * @ClassName: 	[StringType]
 * @version: [版本号]
 * @since: [产品/模块版本]
 */
public class ObjectType<OF extends ObjectField> extends DataType
{
    
    /**
     * 将实体转换成XContentBuilder对象，es能直接使用该对象
     */
    private ObjectFieldConverter<OF> objectFieldConverter;
    
    private DataTypeCollection dataTypeCollection;
    
    /**
     * Whether or not new properties should be added dynamically to an existing object. Accepts true (default), false and strict. 
     */
    private Dynamic dynamic = Dynamic.DEFAULT;
    
    /**
     * Whether the JSON value given for the object field should be parsed and indexed (true, default) or completely ignored (false). 
     * @return Enabled
     */
    private Enabled enabled = Enabled.DEFAULT;

    /**
     * <默认构造函数>
     * @param clazz
     * @param annoList
     */
    public ObjectType(Class<OF> clazz, List<Annotation> annoList)
    {
        type = "object";
        
        pers.linhai.nature.indexaccess.annotation.datatypes.ObjectField objectField
            = getAnnotation(annoList, pers.linhai.nature.indexaccess.annotation.datatypes.ObjectField.class);
        if(objectField != null)
        {
            dynamic = objectField.dynamic();
            enabled = objectField.enabled();
        }
        
        
        //数据entity的构造器，以便用于查询的时候，直接返回该类型的数据
        ConstructorAccess<OF> entityConstructor = ConstructorAccess.get(clazz);
        dataTypeCollection = DataTypeParser.parse(clazz);
        objectFieldConverter = new ObjectFieldConverterImpl<OF>(dataTypeCollection, entityConstructor);
    }
    
    /**
     * 
     *
     * @param t
     * @param object
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T> void setEntityFieldValue(T t, Object object)
    {
        Map<String, Object> objMap = (Map<String, Object>)object;
        ObjectField of = objectFieldConverter.convert(objMap);
        invoke(t, of);
    }

    /**
     * 
     *
     * @param xContentBuilder
     * @param pojo
     * @throws IOException
     */
    @Override
    public <T> void setJsonFieldValue(XContentBuilder xContentBuilder, T pojo) throws IOException
    {
        Object obj = invoke(pojo);
        if (obj == null) return;
        
        setJsonFieldValue0(xContentBuilder, obj);
    }

    /** 
     * @param xContentBuilder
     * @param obj
     * @throws IOException void
     */
    void setJsonFieldValue0(XContentBuilder xContentBuilder, Object obj) throws IOException
    {
        xContentBuilder.startObject(getName());
        objectFieldConverter.convert(xContentBuilder, obj);
        xContentBuilder.endObject();
    }
    
    /**
     * 索引表映射
     * @param mappingStructure void
     * @throws IOException 
     */
    public void buildMappingStructure(XContentBuilder mappingStructure) throws IOException
    {
        //定义一个新的字段类型
        mappingStructure.startObject(getName());
        
        Builder builder = getMappingParams();
        for (String key : builder.keys())
        {
            mappingStructure.field(key, builder.get(key));
        }
        
        mappingStructure.startObject("properties");
        for (DataType dt : dataTypeCollection.getDataTypeList())
        {
            dt.buildMappingStructure(mappingStructure);
        }
        mappingStructure.endObject();
        mappingStructure.endObject();
    }

    /**
     *
     * @return
     */
    @Override
    public Builder getMappingParams()
    {
        Settings.Builder builder = Settings.builder();
        builder.put("type", type);
        
        dynamic.set(builder);
        enabled.set(builder);
        
        return builder;
    }


    /**
     * 对象字段抽象
     * 
     * @author  shinelon
     * @version  V100R001C00
     */
    public static abstract class ObjectField implements Serializable
    {

        /**
         *
         */
        private static final long serialVersionUID = 1L;
        
    }
}
