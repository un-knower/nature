/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : DataSourceMonitorServlet.java</p>
 * <p>Package     : pers.linhai.nature.j2ee.core.dao.datasource.monitor</p>
 * @Creator lilinhai 2018年4月20日 下午11:07:05
 * @Version  V1.0  
 */

package pers.linhai.nature.j2ee.core.dao.datasource.monitor;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.stereotype.Component;

import com.alibaba.druid.support.http.StatViewServlet;

/**
 * 数据源监控器
 * <p>ClassName      : DataSourceMonitorServlet</p>
 * @author lilinhai 2018年4月20日 下午11:07:05
 * @version 1.0
 */
@Component
@ConfigurationProperties("nature.datasource.monitor")
public class DataSourceMonitorServlet extends ServletRegistrationBean<StatViewServlet>
{
    
    /**
     * <p>Title        : DataSourceMonitorServlet lilinhai 2018年4月20日 下午11:07:17</p>
     * @param servlet
     * @param urlMappings 
     */
    public DataSourceMonitorServlet()
    {
        this.setServlet(new StatViewServlet());
        this.addUrlMappings("/datasource-monitor/*");
    }
    
    /**
     * <p>Set Method   :   username String</p>
     * @param username
     */
    public void setUsername(String username)
    {
        // 登录查看信息的账号密码.
        addInitParameter("loginUsername", username);
    }
    
    /**
     * <p>Set Method   :   password String</p>
     * @param password
     */
    public void setPassword(String password)
    {
        addInitParameter("loginPassword", password);
    }
    
    /**
     * <p>Set Method   :   resetEnable String</p>
     * @param resetEnable
     */
    public void setResetEnable(String resetEnable)
    {
        // 是否能够重置数据.
        addInitParameter("resetEnable", resetEnable);
    }
    
    /**
     * <p>Set Method   :   allow String</p>
     * @param allow
     */
    public void setAllow(String allow)
    {
        if (allow != null)
        {
            addInitParameter("allow", allow);
        }
    }
    
    /**
     * IP黑名单 (存在共同时，deny优先于allow) : 如果满足deny的话提示:Sorry, you are not permitted to view this
     * @param deny
     */
    public void setDeny(String deny)
    {
        if (deny != null)
        {
            addInitParameter("deny", deny);
        }
    }
}
