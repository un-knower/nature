package pers.linhai.nature.j2ee.generator.utils;

/**
 * 字段名，索引表名转换工具
 * @ClassName: NamingUtil 
 * @author forestsea 
 * @date 2015-10-18 上午11:15:45 
 * @version 1.0
 */
public abstract class NamingUtils
{

    /**
     * 转换成数据库或索引哭需要的名字格式:驼峰形式小写，用下划线连接
     * 
     * @param name
     * @return
     */
    public static final String storeFieldName(String name)
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
    
    public static String getCamelCaseString(String inputString, boolean firstCharacterUppercase)
    {
        StringBuilder sb = new StringBuilder();

        boolean nextUpperCase = false;
        for (int i = 0; i < inputString.length(); i++ )
        {
            char c = inputString.charAt(i);

            switch (c)
            {
                case '_':
                case '-':
                case '@':
                case '$':
                case '#':
                case ' ':
                case '/':
                case '&':
                    if (sb.length() > 0)
                    {
                        nextUpperCase = true;
                    }
                    break;

                default:
                    if (nextUpperCase)
                    {
                        sb.append(Character.toUpperCase(c));
                        nextUpperCase = false;
                    }
                    else
                    {
                        sb.append(Character.toLowerCase(c));
                    }
                    break;
            }
        }

        if (firstCharacterUppercase)
        {
            sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
        }

        return sb.toString();
    }
    
	/**
	 * 转换成需要的名字格式:驼峰形式小写，用下划线连接
	 * 
	 * @param name
	 * @return
	 */
	public static final String controllerName(String name)
	{
		StringBuffer s = new StringBuffer();
		for (int i = 0; i < name.length(); i++)
		{
			char c = name.charAt(i);
			if (Character.isUpperCase(c))
			{
				s.append(i == 0 ? "" : '-').append(Character.toLowerCase(c));
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
     * 将变量名转换成get方法名
     * 
     * @param name
     * @return
     */
    public static final String getMethodName(String variableName)
    {
        StringBuffer s = new StringBuffer();
        if (variableName.length() > 1)
        {
            if (Character.isLowerCase(variableName.charAt(0)) && Character.isLowerCase(variableName.charAt(1)))
            {
                s.append("get").append(Character.toUpperCase(variableName.charAt(0))).append(variableName.substring(1));
            }
            else
            {
                s.append("get").append(variableName);
            }
        }
        else
        {
            s.append("get").append(Character.toUpperCase(variableName.charAt(0)));
        }
        return s.toString();
    }
    
    /**
     * 将变量名转换成set方法名
     * 
     * @param name
     * @return
     */
    public static final String setMethodName(String variableName)
    {
        StringBuffer s = new StringBuffer();
        if (variableName.length() > 1)
        {
            if (Character.isLowerCase(variableName.charAt(0)) && Character.isLowerCase(variableName.charAt(1)))
            {
                s.append("set").append(Character.toUpperCase(variableName.charAt(0))).append(variableName.substring(1));
            }
            else
            {
                s.append("set").append(variableName);
            }
        }
        else
        {
            s.append("set").append(Character.toUpperCase(variableName.charAt(0)));
        }
        return s.toString();
    }
	
	/**
     * 将类名转换成变量名：把首字母大写转换成小写
     * 
     * @param name
     * @return
     */
    public static final String variableName(String name)
    {
        StringBuffer s = new StringBuffer();
        s.append(Character.toLowerCase(name.charAt(0))).append(name.substring(1));
        return s.toString();
    }
}