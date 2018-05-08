package ${groupId}.${artifactId};

import org.springframework.boot.autoconfigure.SpringBootApplication;

import pers.linhai.nature.j2ee.core.BaseApplication;

/**
 * Spring boot应用启动类
 */
@SpringBootApplication
public class Application extends BaseApplication
{
    public static void main( String[] args )
    {
        start(Application.class, args);
    }
}
