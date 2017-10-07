/*
 * 文 件 名: pers.linhai.esdao.core.TypeFieldParser.java
 * 版    权: XXX Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述: <描述>
 * 修 改 人: shinelon
 * 修改时间: 2017年3月4日 下午3:03:36
 * 跟踪单号: <跟踪单号>
 * 修改单号: <修改单号>
 * 修改内容: <修改内容>
 */
package pers.linhai.nature.indexaccess.core;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.esotericsoftware.reflectasm.MethodAccess;

import pers.linhai.nature.indexaccess.exception.FieldParseException;
import pers.linhai.nature.indexaccess.model.core.DataTypeCollection;
import pers.linhai.nature.indexaccess.model.datatypes.DataType;
import pers.linhai.nature.indexaccess.model.datatypes.DataTypeMap;
import pers.linhai.nature.indexaccess.model.datatypes.quote.ObjectType.ObjectField;
import pers.linhai.nature.indexaccess.utils.NamingUtils;

/**
 * mapping映射解析器
 * 
 * @author: shinelon
 * @date: 2017年3月4日 下午3:03:36
 *       
 * @ClassName: [TypeFieldParser]
 * @version: [版本号]
 * @since: [产品/模块版本]
 */
public final class DataTypeParser
{

    /**
     * 解析ES索引表实体对象
     *
     * @param clazz
     * @return
     * @throws FieldParseException Map<String,TypeField>
     */
    public static final DataTypeCollection parse(Class<?> clazz) throws FieldParseException
    {
        MethodAccess access = MethodAccess.get(clazz);
        
        // 获取MethodMap集合
        Map<String, Method> methodMap = getMethodMap(clazz);
        
        // 获取fieldList集合
        List<Field> fieldList = getFieldList(clazz);
        
        DataTypeCollection dataTypeCollection = new DataTypeCollection(fieldList.size());
        for (Field field : fieldList)
        {
            // 静态成员跳过处理
            if (Modifier.isStatic(field.getModifiers()))
            {
                continue;
            }
            
            // 校验字段的合法性
            valid(field, clazz);
            
            String str = Character.toUpperCase(field.getName().charAt(0)) + field.getName().substring(1);
            
            //校验该字段是否存在对应的get和set方法
            Method getMethod = methodMap.get("get".concat(str));
            Method setMethod = methodMap.get("set".concat(str));
            if (getMethod == null)
            {
                throw new FieldParseException("Can't find the get method named ".concat("get".concat(str)).concat(" related to the field ").concat(field.getName()));
            }
            
            if (setMethod == null)
            {
                throw new FieldParseException("Can't find the set method named ".concat("set".concat(str)).concat(" related to the field ").concat(field.getName()));
            }
            
            DataType dt = DataType.transfer(field);
            dt.setName(NamingUtils.name(field.getName()));
            dt.setAccess(access);
            dt.setGetterIndex(access.getIndex(getMethod.getName()));
            dt.setSetterIndex(access.getIndex(setMethod.getName()));
            dataTypeCollection.add(dt);
        }
        
        return dataTypeCollection;
    }

    /** 
     * 获取成员变量集合
     *
     * @param clazz
     * @return List<Field>
     */
    private static List<Field> getFieldList(Class<?> clazz)
    {
        List<Field> fieldList = new ArrayList<Field>();
        for (Field field : clazz.getDeclaredFields())
        {
            fieldList.add(field);
        }
        return fieldList;
    }
    
    /**
     * 校验字段的合法性
     * 
     * @author: shinelon
     * @date: 2017年3月5日 上午11:53:02
     * @Title: valid
     *        
     * @param field
     * @param clazz
     * @return: void
     */
    private static void valid(Field field, Class<?> clazz)
    {
        String fieldName = field.getName();
        char firstChar = fieldName.charAt(0);
        
        // 校验成员变量名首字母是否大写，大写则异常跑出
        if (Character.isUpperCase(firstChar))
        {
            throw new FieldParseException("Es-dao frame init failed, The field Naming '".concat(fieldName).
                concat("' in the class'".concat(clazz.getName()).concat("'").concat(" is invalid, it must be Camel naming, and first letter is lowercase.")));
        }
        
        // 校验成员变量的类型是否是指定类型之一
        if (!DataTypeMap.dataTypeSupports().contains(field.getType()) && !ObjectField.class.isAssignableFrom(field.getType()))
        {
            throw new FieldParseException("Es-dao frame init failed, the type of the field'".concat(fieldName).concat("' in the class'").
                concat(clazz.getName()).concat("' is invalid, it must be one of the type set").concat(DataTypeMap.dataTypeSupports().toString()));
        }
    }
    
    /**
     * 获得MethodMap
     * 该map集合封装了class及该class父类下所有get和set方法
     * @param <T>
     * @param clazz
     * @return
     */
    private static final Map<String, Method> getMethodMap(Class<?> clazz)
    {
        Map<String, Method> methodMap = new HashMap<String, Method>();
        List<Method> methodList = new ArrayList<Method>();
        for (Method method : clazz.getDeclaredMethods())
        {
            methodList.add(method);
        }
        
        for (Method method : methodList)
        {
            // 静态成员跳过处理
            if (Modifier.isStatic(method.getModifiers()))
            {
                continue;
            }
            String name = method.getName();
            if (name.startsWith("get") || name.startsWith("set"))
            {
                methodMap.put(method.getName(), method);
            }
        }
        return methodMap;
    }
}
