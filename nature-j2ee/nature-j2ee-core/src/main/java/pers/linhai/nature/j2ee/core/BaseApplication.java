/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2017, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : BaseApplication.java</p>
 * <p>Package     : com.leloven.wanka.common.core</p>
 * @Creator lilinhai 2017年12月28日 下午8:37:25
 * @Version  V1.0  
 */ 

package pers.linhai.nature.j2ee.core;

import org.apache.logging.log4j.core.async.AsyncLoggerContextSelector;
import org.springframework.boot.Banner.Mode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

/**
 * <p>Description    : <pre>TODO(这里用一句话描述这个类的作用)</pre></p>
 * <p>ClassName      : BaseApplication</p>
 * @author lilinhai 2017年12月28日 下午8:37:25
 * @version 1.0
 */
@PropertySource("${datasource.config}")
@PropertySource("classpath:mybatis-springboot.properties")
@SpringBootApplication
public abstract class BaseApplication
{

    /**
     * spring boot web应用的基类启动函数
     * @param clazz
     * @param args 
     * void
     */
    protected static void start(Class<? extends BaseApplication> clazz, String[] args)
    {
        // 异步日志
        System.setProperty("Log4jContextSelector", AsyncLoggerContextSelector.class.getName());
        SpringApplication springApplication = new SpringApplication(clazz);
        
        //关闭spring boot自身横幅打印
        springApplication.setBannerMode(Mode.OFF);
        springApplication.run(args);
    }
}
