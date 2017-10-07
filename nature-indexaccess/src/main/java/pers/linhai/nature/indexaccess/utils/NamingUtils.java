package pers.linhai.nature.indexaccess.utils;

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
	 * 转换成需要的名字格式:驼峰形式小写，用下划线连接
	 * 
	 * @param name
	 * @return
	 */
	public static final String name(String name)
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
	
	
	public static final String accessorName(Class<?> c)
	{
	    return name(c.getSimpleName()) + "_accessor";
	}
}
