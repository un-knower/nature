/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : DataSourceMonitorServlet.java</p>
 * <p>Package     : pers.linhai.nature.j2ee.core.dao.datasource.monitor</p>
 * @Creator lilinhai 2018年4月20日 下午11:07:05
 * @Version  V1.0  
 */ 

package pers.linhai.nature.j2ee.core.dao.datasource.monitor;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.stereotype.Component;

import com.alibaba.druid.support.http.StatViewServlet;

/**
 * <p>Description    : <pre>TODO(这里用一句话描述这个类的作用)</pre></p>
 * <p>ClassName      : DataSourceMonitorServlet</p>
 * @author lilinhai 2018年4月20日 下午11:07:05
 * @version 1.0
 */
@Component
public class DataSourceMonitorServlet extends ServletRegistrationBean
{

    /**
     * <p>Title        : DataSourceMonitorServlet lilinhai 2018年4月20日 下午11:07:17</p>
     * @param servlet
     * @param urlMappings 
     */ 
    public DataSourceMonitorServlet()
    {
        super(new StatViewServlet(), "/datasource-monitor/*");
        
        addInitParameter("allow", "127.0.0.1");

        // IP黑名单 (存在共同时，deny优先于allow) : 如果满足deny的话提示:Sorry, you are not permitted to view this
        // page.
        //servletRegistrationBean.addInitParameter("deny", "192.168.1.73");

        // 登录查看信息的账号密码.
        addInitParameter("loginUsername", "admin");
        addInitParameter("loginPassword", "123456");

        // 是否能够重置数据.
        addInitParameter("resetEnable", "false");
    }

    
}
