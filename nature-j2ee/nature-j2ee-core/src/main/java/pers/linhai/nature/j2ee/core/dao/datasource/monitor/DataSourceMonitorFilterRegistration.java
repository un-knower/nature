/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : DataSourceMonitorFilterRegistration.java</p>
 * <p>Package     : pers.linhai.nature.j2ee.core.dao.datasource.monitor</p>
 * @Creator lilinhai 2018年4月20日 下午11:09:08
 * @Version  V1.0  
 */

package pers.linhai.nature.j2ee.core.dao.datasource.monitor;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.stereotype.Component;

import com.alibaba.druid.support.http.WebStatFilter;

/**
 * <p>ClassName      : DataSourceMonitorFilterRegistration</p>
 * @author lilinhai 2018年4月20日 下午11:09:08
 * @version 1.0
 */
@Component
@ConfigurationProperties("datasource.monitor")
public class DataSourceMonitorFilterRegistration extends FilterRegistrationBean<WebStatFilter>
{
    /**
     * <p>Title        : DataSourceMonitorFilterRegistration lilinhai 2018年4月20日 下午11:09:45</p>
     * @param filter
     * @param servletRegistrationBeans 
     */
    public DataSourceMonitorFilterRegistration()
    {
        setFilter(new WebStatFilter());
        
        addUrlPatterns("/*");
        
        // 添加不需要忽略的格式信息.
        addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/datasource-monitor/*");
    }
}
