/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : RestApiServletRegistration.java</p>
 * <p>Package     : pers.linhai.nature.j2ee.core.web.api</p>
 * @Creator lilinhai 2018年6月8日 下午11:42:22
 * @Version  V1.0  
 */ 

package pers.linhai.nature.j2ee.core.web.api;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import springfox.documentation.spring.web.DocumentationCache;
import springfox.documentation.spring.web.json.JsonSerializer;
import springfox.documentation.swagger2.mappers.ServiceModelToSwagger2Mapper;

/**
 * <p>Description    : <pre>TODO(这里用一句话描述这个类的作用)</pre></p>
 * <p>ClassName      : RestApiServletRegistration</p>
 * @author lilinhai 2018年6月8日 下午11:42:22
 * @version 1.0
 */
@Component
@ConfigurationProperties("nature.rest-api")
public class RestApiServletRegistration extends ServletRegistrationBean<RestApiServlet> implements InitializingBean
{

    @Autowired
    private ResourceLoader resourceLoader;
    
    @Autowired
    private DocumentationCache documentationCache;
    
    @Autowired
    private ServiceModelToSwagger2Mapper mapper;
    
    @Autowired
    private JsonSerializer jsonSerializer;
    
    /** 
     * <p>Overriding Method: lilinhai 2018年6月9日 上午9:09:32</p>
     * <p>Title: afterPropertiesSet</p>
     * @throws Exception 
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */ 
    public void afterPropertiesSet() throws Exception
    {
        this.setServlet(new RestApiServlet(resourceLoader, documentationCache, mapper, jsonSerializer));
        this.addUrlMappings("/nature-rest-api/*");  
    }
   
    
}
