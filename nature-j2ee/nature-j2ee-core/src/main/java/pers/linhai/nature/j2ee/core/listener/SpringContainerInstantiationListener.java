/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : InstantiationTracingBeanPostProcessor.java</p>
 * <p>Package     : com.leloven.wanka.gw.listener</p>
 * @Creator lilinhai 2018年1月24日 上午10:05:41
 * @Version  V1.0  
 */ 

package pers.linhai.nature.j2ee.core.listener;

import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import pers.linhai.nature.j2ee.core.cache.RequestMappingCache;
import pers.linhai.nature.j2ee.core.model.JdbcModel;

/**
 * spring容器初始化完成监听器
 * <p>ClassName      : InstantiationTracingBeanPostProcessor</p>
 * @author lilinhai 2018年1月24日 上午10:05:41
 * @version 1.0
 */
@Component
public class SpringContainerInstantiationListener implements ApplicationListener<ContextRefreshedEvent>
{

    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;
    
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;
    
    /** 
     * <p>Overriding Method: lilinhai 2018年1月24日 上午10:06:03</p>
     * <p>Title: onApplicationEvent</p>
     * @param event 
     * @see org.springframework.context.ApplicationListener#onApplicationEvent(org.springframework.context.ApplicationEvent)
     */ 
    public void onApplicationEvent(ContextRefreshedEvent event)
    {
        JdbcModel.setDbDriverClass(driverClassName);
        Map<RequestMappingInfo, HandlerMethod> map = requestMappingHandlerMapping.getHandlerMethods();
        for (Entry<RequestMappingInfo, HandlerMethod> e : map.entrySet())
        {
            for (RequestMethod rm : e.getKey().getMethodsCondition().getMethods())
            {
                for (String path : e.getKey().getPatternsCondition().getPatterns())
                {
                    RequestMappingCache.put(e.getValue(), rm.name() + "_" +  path);
                }
            }
        }
    }
    
}