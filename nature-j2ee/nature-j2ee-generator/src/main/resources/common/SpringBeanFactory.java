/*
 * <p>Copyright : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p> <p>FileName : SpringBeanFactory.java</p>
 * <p>Package : com.leloven.wanka.common.core</p>
 * @Creator lilinhai 2018年1月18日 下午7:25:22
 * @Version V1.0
 */

package ${groupId}.${artifactId}.common.core;


import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


/**
 * <p>Description    : <pre>TODO(这里用一句话描述这个类的作用)</pre></p>
 * <p>ClassName      : SpringBeanFactory</p>
 * @author lilinhai 2018年1月18日 下午7:25:22
 * @version 1.0
 */
@Component
public class SpringBeanFactory implements ApplicationContextAware
{

    /**
     * Spring容器
     */
    private static ApplicationContext applicationContext = null;

    /**
     * <p>Overriding Method: lilinhai 2018年1月18日 下午7:28:17</p>
     * <p>Title: setApplicationContext</p>
     * @param applicationContext
     * @throws BeansException 
     * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
     */
    public void setApplicationContext(ApplicationContext applicationContext)
        throws BeansException
    {
        if (SpringBeanFactory.applicationContext == null)
        {
            SpringBeanFactory.applicationContext = applicationContext;
        }
    }

    // 获取applicationContext
    public static ApplicationContext getApplicationContext()
    {
        return applicationContext;
    }

    // 通过name获取 Bean.
    public static Object getBean(String name)
    {
        if (applicationContext == null)
        {
            return null;
        }
        return getApplicationContext().getBean(name);
    }

    // 通过class获取Bean.
    public static <T> T getBean(Class<T> clazz)
    {
        if (applicationContext == null)
        {
            return null;
        }
        return getApplicationContext().getBean(clazz);
    }

    // 通过name,以及Clazz返回指定的Bean
    public static <T> T getBean(String name, Class<T> clazz)
    {
        if (applicationContext == null)
        {
            return null;
        }
        return getApplicationContext().getBean(name, clazz);
    }
}
