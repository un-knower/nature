package pers.linhai.nature.ui;

import org.springframework.boot.Banner.Mode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class App 
{
    public static void main( String[] args )
    {
        SpringApplication springApplication = new SpringApplication(App.class);
        
        //关闭spring boot自身横幅打印
        springApplication.setBannerMode(Mode.OFF);
        springApplication.run(args);
    }
}
