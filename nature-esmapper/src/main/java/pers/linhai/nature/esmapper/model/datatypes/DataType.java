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
package pers.linhai.nature.esmapper.model.datatypes;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.elasticsearch.common.settings.Settings.Builder;
import org.elasticsearch.common.xcontent.XContentBuilder;

import pers.linhai.nature.esmapper.exception.DataTypeNonsupportException;
import pers.linhai.nature.esmapper.exception.DataTypeParseException;
import pers.linhai.nature.esmapper.model.datatypes.quote.ObjectType.ObjectField;

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
     * get方法反射调用
     */
    private Method getMethod;
    
    /**
     * set方法反射调用
     */
    private Method setMethod;

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
     * <p>Set Method   :   getMethod Method</p>
     * @param getMethod
     */
    public void setGetMethod(Method getMethod)
    {
        this.getMethod = getMethod;
    }

    /**
     * <p>Set Method   :   setMethod Method</p>
     * @param setMethod
     */
    public void setSetMethod(Method setMethod)
    {
        this.setMethod = setMethod;
    }

    /**
     * 反射set方法
     * @param t
     * @param args void
     */
    protected <T> void invoke(T t, Object... args)
    {
        try
        {
            setMethod.invoke(t, args);
        }
        catch (Throwable e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * 反射get方法
     * @param t
     * @return Object
     */
    protected <T> Object invoke(T t)
    {
        try
        {
            return getMethod.invoke(t);
        }
        catch (Throwable e)
        {
            e.printStackTrace();
            return null;
        }
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
