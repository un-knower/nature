/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : ApplicationOnStartupCompletion.java</p>
 * <p>Package     : pers.linhai.nature.j2ee.core.spring</p>
 * @Creator lilinhai 2018年6月8日 下午11:12:49
 * @Version  V1.0  
 */

package pers.linhai.nature.j2ee.core.spring;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import pers.linhai.nature.utils.BrowseUtils;

/**
 * spring-boot初始化完成后回调
 * <p>ClassName      : ApplicationOnStartupCompletion</p>
 * @author lilinhai 2018年6月8日 下午11:12:49
 * @version 1.0
 */
@Component
public class ApplicationOnStartupCompletion implements ApplicationListener<ContextRefreshedEvent>
{
    
    @Value("${nature.restApi.enabled}")
    private boolean isRestApiEnabled;
    
    @Value("${server.servlet.context-path}")
    private String contextPath;
    
    @Value("${server.port}")
    private int port;
    
    /** 
     * <p>Overriding Method: lilinhai 2018年6月8日 下午11:13:25</p>
     * <p>Title: onApplicationEvent</p>
     * @param event 
     * @see org.springframework.context.ApplicationListener#onApplicationEvent(org.springframework.context.ApplicationEvent)
     */
    public void onApplicationEvent(ContextRefreshedEvent event)
    {
        if (isRestApiEnabled)
        {
            // 如果rest-api开关打开，则启动完成后启动打开浏览器并浏览该rest-api主页
            String baseUrl = "http://localhost:" + port;
            if (!baseUrl.endsWith("/") && !contextPath.startsWith("/"))
            {
                baseUrl += "/";
            }
            baseUrl += contextPath;
            if (!baseUrl.endsWith("/"))
            {
                baseUrl += "/";
            }
            // 打开rest-api网址
            String restApiUrl = baseUrl + "nature-rest-api/swagger-ui.html";
            BrowseUtils.browse(restApiUrl);
            
            // 打开数据源监控网址
            String datasourceMonitorUrl = baseUrl + "/datasource-monitor/admin.html";
            BrowseUtils.browse(datasourceMonitorUrl);
        }
    }
    
}
