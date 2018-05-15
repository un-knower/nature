package ${groupId}.${artifactId};

import pers.linhai.nature.j2ee.core.BaseApplication;

/**
 * Spring boot应用启动类
 */
public class Application extends BaseApplication
{
    public static void main( String[] args )
    {
        start(Application.class, args);
    }
}
