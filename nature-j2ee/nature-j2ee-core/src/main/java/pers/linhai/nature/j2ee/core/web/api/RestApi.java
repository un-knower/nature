/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : SwaggerComponent.java</p>
 * <p>Package     : pers.linhai.nature.j2ee.core.swagger</p>
 * @Creator lilinhai 2018年6月8日 下午5:14:27
 * @Version  V1.0  
 */

package pers.linhai.nature.j2ee.core.web.api;

import java.util.List;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger-api组件
 * <p>ClassName      : SwaggerComponent</p>
 * @author lilinhai 2018年6月8日 下午5:14:27
 * @version 1.0
 */
@Component
@EnableSwagger2
@ConfigurationProperties("nature.rest-api")
public class RestApi extends Docket implements InitializingBean
{
    
    @Autowired
    private BeanFactory beanFactory;
    
    /**
     * <p>Title        : SwaggerComponent lilinhai 2018年6月8日 下午5:14:30</p>
     * @param documentationType 
     */
    public RestApi()
    {
        super(DocumentationType.SWAGGER_2);
    }
    
    /** 
     * <p>Overriding Method: lilinhai 2018年6月8日 下午5:19:15</p>
     * <p>Title: afterPropertiesSet</p>
     * @throws Exception 
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    public void afterPropertiesSet() throws Exception
    {
        List<String> packages = AutoConfigurationPackages.get(this.beanFactory);
        for (String pkg : packages)
        {
            // 如果扫描包不以框架开头
            if (getClass().getPackage().getName().startsWith(pkg))
            {
                continue;
            }
            
            this.apiInfo(apiInfo())
            .select()
            .apis(RequestHandlerSelectors.basePackage(pkg))
            .paths(PathSelectors.any())
            .build();
            break;
        }
        
    }
    
    /**
     * 是否开启rest-api功能
     * <p>Title         : setEnabled lilinhai 2018年6月8日 下午11:05:29</p>
     * @param enabled 
     * void
     */
    public void setEnabled(boolean enabled)
    {
        this.enable(enabled);
    }
    
    private ApiInfo apiInfo()
    {
        return new ApiInfoBuilder().title("Nature J2EE Rest Api Docs")
                .description("Nature J2EE框架Rest Api文档")
                .termsOfServiceUrl("http://blog.csdn.net/saytime")
                .version("2.0")
                .build();
    }
}
