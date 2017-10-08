/*
 * 文 件 名: pers.linhai.esdao.datatype.DataType.java
 * 版    权: XXX Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述: <描述>
 * 修 改 人: shinelon
 * 修改时间: 2017年3月5日 下午1:55:42
 * 跟踪单号: <跟踪单号>
 * 修改单号: <修改单号>
 * 修改内容: <修改内容>
 */
package pers.linhai.nature.indexaccess.model.datatypes;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import org.elasticsearch.common.settings.Settings.Builder;
import org.elasticsearch.common.xcontent.XContentBuilder;

import pers.linhai.nature.core.reflect.MethodAccess;
import pers.linhai.nature.indexaccess.exception.DataTypeNonsupportException;
import pers.linhai.nature.indexaccess.exception.DataTypeParseException;
import pers.linhai.nature.indexaccess.model.datatypes.quote.ObjectType.ObjectField;

/**
 * <pre>
 * 抽象数据类型
 * 
 * 数据类型，仅支持以下几种
 * 
 *   String.class
 *   Integer.class
 *   Integer.TYPE
 *   Long.class
 *   Long.TYPE
 *   Float.class
 *   Float.TYPE
 *   Double.class
 *   Double.TYPE
 * </pre>
 * 
 * @author: shinelon
 * @date: 2017年3月5日 下午1:55:42
 *        
 * @ClassName: [DataType]
 * @version: [版本号]
 * @since: [产品/模块版本]
 */
public abstract class DataType
{

    /**
     * 数据类型
     */
    protected String type;
    
    /**
     * 数据pojo的成员变量名
     */
    private String name;
    
    /**
     * 方法访问者
     */
    private MethodAccess access;

    /**
     * 数据pojo对应成员的get方法
     */
    private int getterIndex;

    /**
     * 数据pojo对应成员的set方法
     */
    private int setterIndex;
    
    /** 
     * <默认构造函数>
     *
     * @param indexTypeBuilder
     */
    protected DataType()
    {
        
    }

    /**
     * 获取 name
     * @return 返回 name
     */
    public String getName()
    {
        return name;
    }

    /**
     * 设置 name
     * @param 对name进行赋值
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * 获取 access
     * @return 返回 access
     */
    public MethodAccess getAccess()
    {
        return access;
    }

    /**
     * 设置 access
     * @param 对access进行赋值
     */
    public void setAccess(MethodAccess access)
    {
        this.access = access;
    }

    /**
     * 获取 getterIndex
     * @return 返回 getterIndex
     */
    public int getGetterIndex()
    {
        return getterIndex;
    }

    /**
     * 设置 getterIndex
     * @param 对getterIndex进行赋值
     */
    public void setGetterIndex(int getterIndex)
    {
        this.getterIndex = getterIndex;
    }

    /**
     * 获取 setterIndex
     * @return 返回 setterIndex
     */
    public int getSetterIndex()
    {
        return setterIndex;
    }

    /**
     * 设置 setterIndex
     * @param 对setterIndex进行赋值
     */
    public void setSetterIndex(int setterIndex)
    {
        this.setterIndex = setterIndex;
    }
    
    /**
     * 获取指定的注解
     *
     * @param as
     * @param ac
     * @return T
     */
    protected <T extends Annotation> T getAnnotation(List<Annotation> annoList, Class<T> ac)
    {
        for (Annotation annotation : annoList)
        {
            if(annotation.annotationType() == ac)
            {
                return ac.cast(annotation);
            }
        }
        return null;
    }

    /**
     * 反射给实体对象设置值
     * @param t
     * @param object void
     */
    public abstract <T> void setEntityFieldValue(T t, Object object);

    /**
     * 转换成可被索引的数据
     * @param xContentBuilder
     * @param pojo void
     */
    public abstract <T> void setJsonFieldValue(XContentBuilder xContentBuilder, T pojo) throws IOException;

    /**
     * 获取该字段的索引配置参数
     *
     * @return Builder
     */
    public abstract Builder getMappingParams();
    
    /**
     * 反射set方法
     * @param t
     * @param args void
     */
    protected <T> void invoke(T t, Object... args)
    {
        access.invoke(t, setterIndex, args);
    }
    
    /**
     * 反射get方法
     * @param t
     * @return Object
     */
    protected <T> Object invoke(T t)
    {
        return access.invoke(t, getterIndex);
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
        
        for (Entry<String, String> e : getMappingParams().internalMap().entrySet())
        {
            mappingStructure.field(e.getKey(), e.getValue());
        }
        
        mappingStructure.endObject();
    }

    /**
     * 将java-field对象转换成es-type-field对象
     * @author: shinelon
     * @date: 2017年3月5日 下午4:52:32 
     * @Title: transfer
     *
     * @param field
     * @return
     * @return:	TypeField
     */
    public static final DataType transfer(Field field)
    {
        try
        {
            Class<?> fieldType = field.getType();
            
            //获取数据类型处理器构造函数
            Constructor<? extends DataType> dataTypeConstructor = DataTypeMap.get(fieldType);
            if (ObjectField.class.isAssignableFrom(fieldType))
            {
                if (dataTypeConstructor == null)
                {
                    dataTypeConstructor = DataTypeMap.get(ObjectField.class);
                }
                return dataTypeConstructor.newInstance(fieldType, Arrays.asList(field.getDeclaredAnnotations()));
            }
            else
            {
                if(dataTypeConstructor == null)
                {
                    throw new DataTypeNonsupportException(field.getName() + ": " + field.getType().getName());
                }
                
                return dataTypeConstructor.newInstance(Arrays.asList(field.getDeclaredAnnotations()));
            }
        }
        catch (Throwable e)
        {
            throw new DataTypeParseException("transfer", e);
        }
    }
}
