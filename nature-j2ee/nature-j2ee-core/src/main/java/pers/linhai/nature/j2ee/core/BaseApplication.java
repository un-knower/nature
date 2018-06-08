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
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

import pers.linhai.nature.j2ee.core.spring.YamlPropertySourceFactory;

/**
 * 所有应用启动类的基类
 * <p>ClassName      : BaseApplication</p>
 * @author lilinhai 2017年12月28日 下午8:37:25
 * @version 1.0
 */
@PropertySources({@PropertySource("classpath:mybatis-springboot.properties"),
        @PropertySource(value = "classpath:nature/nature-${spring.profiles.active}.yml", factory = YamlPropertySourceFactory.class, ignoreResourceNotFound = true),
        @PropertySource(value = "classpath:server/server-${spring.profiles.active}.yml", factory = YamlPropertySourceFactory.class, ignoreResourceNotFound = true),
        @PropertySource(value = "classpath:spring/spring-${spring.profiles.active}.yml", factory = YamlPropertySourceFactory.class, ignoreResourceNotFound = true),
        @PropertySource(value = "classpath:${app.name}/${app.name}-${spring.profiles.active}.yml", factory = YamlPropertySourceFactory.class, ignoreResourceNotFound = true)})
@SpringBootApplication(exclude = MybatisAutoConfiguration.class)
public abstract class BaseApplication
{
    
    /**
     * spring boot web应用的基类启动函数
     * @param clazz
     * @param args 
     * void
     */
    protected static void start(Class< ? extends BaseApplication> clazz, String[] args)
    {
        // 异步日志
        System.setProperty("Log4jContextSelector", AsyncLoggerContextSelector.class.getName());
        SpringApplication.run(clazz, args);
    }
}
