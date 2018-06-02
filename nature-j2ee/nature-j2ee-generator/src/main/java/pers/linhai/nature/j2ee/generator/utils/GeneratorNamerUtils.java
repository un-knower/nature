package pers.linhai.nature.j2ee.generator.utils;

/**
 * 代码生成器命名工具
 * @ClassName: NamingUtil 
 * @author forestsea 
 * @date 2015-10-18 上午11:15:45 
 * @version 1.0
 */
public abstract class GeneratorNamerUtils
{

    /**
     * 将控制器类名转换为映射名
     * 
     * @param name
     * @return
     */
    public static final String controllerMappingName(String name)
    {
        StringBuffer s = new StringBuffer();
        for (int i = 0; i < name.length(); i++)
        {
            char c = name.charAt(i);
            if (Character.isUpperCase(c))
            {
                s.append(i == 0 ? "" : '-').append(Character.toLowerCase(c));
                while (i + 1 < name.length() && Character.isUpperCase(name.charAt(i + 1)))
                {
                    i++;
                    c = name.charAt(i);
                    s.append('-').append(Character.toLowerCase(c));
                }
            }
            else
            {
                s.append(c);
            }
        }
        return s.toString();
    }
}
