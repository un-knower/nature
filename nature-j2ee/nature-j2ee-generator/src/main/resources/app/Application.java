package ${groupId}.${artifactId};

import org.apache.logging.log4j.core.async.AsyncLoggerContextSelector;
import org.springframework.boot.Banner.Mode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring boot应用启动类
 */
@EnableAutoConfiguration
@SpringBootApplication
public class Application 
{
    public static void main( String[] args )
    {
        // 异步日志
        System.setProperty("Log4jContextSelector", AsyncLoggerContextSelector.class.getName());
        
        SpringApplication springApplication = new SpringApplication(Application.class);
        
        //关闭spring boot自身横幅打印
        springApplication.setBannerMode(Mode.OFF);
        springApplication.run(args);
    }
}
