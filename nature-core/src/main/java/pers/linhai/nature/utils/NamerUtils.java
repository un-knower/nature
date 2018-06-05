package pers.linhai.nature.utils;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import pers.linhai.nature.exception.ReflectionException;

/**
 * 字段名，索引表名转换工具
 * @ClassName: NamingUtil 
 * @author forestsea 
 * @date 2015-10-18 上午11:15:45 
 * @version 1.0
 */
public abstract class NamerUtils
{
    private static final Set<Character> SEPARATOR_CHAR_SET = new HashSet<Character>();
    static
    {
        SEPARATOR_CHAR_SET.add('-');
        SEPARATOR_CHAR_SET.add('@');
        SEPARATOR_CHAR_SET.add('$');
        SEPARATOR_CHAR_SET.add('#');
        SEPARATOR_CHAR_SET.add(' ');
        SEPARATOR_CHAR_SET.add('/');
        SEPARATOR_CHAR_SET.add('&');
    }
    
    /**
     * 转换成数据库或索引哭需要的名字格式:驼峰形式小写，用下划线连接
     * 
     * @param name
     * @return
     */
    public static final String propertyToColumn(String name)
    {
        StringBuffer s = new StringBuffer();
        for (int i = 0; i < name.length(); i++)
        {
            char c = name.charAt(i);
            if (Character.isUpperCase(c))
            {
                s.append(i == 0 ? "" : '_').append(Character.toLowerCase(c));
                c = name.charAt(i + 1);
                while (i + 1 < name.length() && Character.isUpperCase(c))
                {
                    i++;
                    s.append(Character.toLowerCase(c));
                }
            }
            else
            {
                s.append(c);
            }
        }
        return s.toString();
    }
    
    /**
     * 列名转换为java属性名
     * <p>Title         : columnToProterty lilinhai 2018年6月2日 下午1:33:21</p>
     * @param column
     * @return 
     * String
     */
    public static String columnToProterty(String column)
    {
        return getCamelCaseString(column, false);
    }
    
    /**
     * 将数据库中带下划线的字段名计算成java字段名（驼峰命名）
     * <p>Title         : getCamelCaseString lilinhai 2018年3月14日 上午10:55:59</p>
     * @param inputString
     * @param firstCharacterUppercase
     * @return 
     * String
     */
    public static String getCamelCaseString(String inputString, boolean firstCharacterUppercase)
    {
        StringBuilder sb = new StringBuilder();
        boolean isNextCharToUpperCase = false;
        for (int i = 0; i < inputString.length(); i++)
        {
            char c = inputString.charAt(i);
            if ('_' ==  c || SEPARATOR_CHAR_SET.contains(c))
            {
                if (sb.length() > 0)
                {
                    isNextCharToUpperCase = true;
                }
            }
            else if (isNextCharToUpperCase)
            {
                sb.append(Character.toUpperCase(c));
                isNextCharToUpperCase = false;
            }
            else
            {
                sb.append(Character.toLowerCase(c));
            }
        }
        
        if (firstCharacterUppercase)
        {
            sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
        }
        
        return sb.toString();
    }
    
    /**
     * 将变量名转换成get方法名
     * 
     * @param name
     * @return
     */
    public static final String toGetMethodName(String property)
    {
        StringBuffer s = new StringBuffer();
        if (property.length() > 1)
        {
            if (Character.isLowerCase(property.charAt(0)) && Character.isLowerCase(property.charAt(1)))
            {
                s.append("get").append(Character.toUpperCase(property.charAt(0))).append(property.substring(1));
            }
            else
            {
                s.append("get").append(property);
            }
        }
        else
        {
            s.append("get").append(Character.toUpperCase(property.charAt(0)));
        }
        return s.toString();
    }
    
    /**
     * 将变量名转换成set方法名
     * 
     * @param name
     * @return
     */
    public static final String toSetMethodName(String property)
    {
        StringBuffer s = new StringBuffer();
        if (property.length() > 1)
        {
            if (Character.isLowerCase(property.charAt(0)) && Character.isLowerCase(property.charAt(1)))
            {
                s.append("set").append(Character.toUpperCase(property.charAt(0))).append(property.substring(1));
            }
            else
            {
                s.append("set").append(property);
            }
        }
        else
        {
            s.append("set").append(Character.toUpperCase(property.charAt(0)));
        }
        return s.toString();
    }
    
    /**
     * 将类名转换成变量名：把首字母大写转换成小写
     * 
     * @param name
     * @return
     */
    public static final String classToProperty(String name)
    {
        StringBuffer s = new StringBuffer();
        s.append(name).setCharAt(0, Character.toLowerCase(name.charAt(0)));
        return s.toString();
    }
    
    public static String methodToProperty(String name)
    {
        if (name.startsWith("is"))
        {
            name = name.substring(2);
        }
        else if (name.startsWith("get") || name.startsWith("set"))
        {
            name = name.substring(3);
        }
        else
        {
            throw new ReflectionException("Error parsing property name '" + name + "'.  Didn't start with 'is', 'get' or 'set'.");
        }
        
        if (name.length() == 1 || (name.length() > 1 && !Character.isUpperCase(name.charAt(1))))
        {
            name = name.substring(0, 1).toLowerCase(Locale.ENGLISH) + name.substring(1);
        }
        
        return name;
    }
    
    public static boolean isProperty(String name)
    {
        return name.startsWith("get") || name.startsWith("set") || name.startsWith("is");
    }
    
    public static boolean isGetter(String name)
    {
        return name.startsWith("get") || name.startsWith("is");
    }
    
    public static boolean isSetter(String name)
    {
        return name.startsWith("set");
    }
}
