/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : Swagger2Controller.java</p>
 * <p>Package     : pers.linhai.nature.j2ee.core.web.api</p>
 * @Creator lilinhai 2018年6月9日 下午1:50:27
 * @Version  V1.0  
 */

package pers.linhai.nature.j2ee.core.web.api;

import static com.google.common.base.Strings.isNullOrEmpty;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;

import com.google.common.base.Optional;
import com.google.common.base.Strings;

import io.swagger.models.Swagger;
import springfox.documentation.service.Documentation;
import springfox.documentation.spring.web.DocumentationCache;
import springfox.documentation.spring.web.json.Json;
import springfox.documentation.spring.web.json.JsonSerializer;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.mappers.ServiceModelToSwagger2Mapper;

/**
 * <p>ClassName      : Swagger2Controller</p>
 * @author lilinhai 2018年6月9日 下午1:50:27
 * @version 1.0
 */
@RestController
@RequestMapping("/api-docs")
public class Swagger2Controller
{
    private final String hostNameOverride;
    
    private final DocumentationCache documentationCache;
    
    private final ServiceModelToSwagger2Mapper mapper;
    
    private final JsonSerializer jsonSerializer;
    
    @Autowired
    public Swagger2Controller(Environment environment, DocumentationCache documentationCache, ServiceModelToSwagger2Mapper mapper, JsonSerializer jsonSerializer)
    {
        
        this.hostNameOverride = environment.getProperty("springfox.documentation.swagger.v2.host", "DEFAULT");
        this.documentationCache = documentationCache;
        this.mapper = mapper;
        this.jsonSerializer = jsonSerializer;
    }
    
    @GetMapping("/")
    public ResponseEntity<Json> getDocumentation(@RequestParam(value = "group", required = false) String swaggerGroup, HttpServletRequest servletRequest)
    {
        String groupName = Optional.fromNullable(swaggerGroup).or(Docket.DEFAULT_GROUP_NAME);
        Documentation documentation = documentationCache.documentationByGroup(groupName);
        if (documentation == null)
        {
            return new ResponseEntity<Json>(HttpStatus.NOT_FOUND);
        }
        Swagger swagger = mapper.mapDocumentation(documentation);
        UriComponents uriComponents = HostNameProvider.componentsFrom(servletRequest, swagger.getBasePath());
        swagger.basePath(Strings.isNullOrEmpty(uriComponents.getPath()) ? "/" : uriComponents.getPath());
        if (isNullOrEmpty(swagger.getHost()))
        {
            swagger.host(hostName(uriComponents));
        }
        return new ResponseEntity<Json>(jsonSerializer.toJson(swagger), HttpStatus.OK);
    }
    
    private String hostName(UriComponents uriComponents)
    {
        if ("DEFAULT".equals(hostNameOverride))
        {
            String host = uriComponents.getHost();
            int port = uriComponents.getPort();
            if (port > -1)
            {
                return String.format("%s:%d", host, port);
            }
            return host;
        }
        return hostNameOverride;
    }
}
