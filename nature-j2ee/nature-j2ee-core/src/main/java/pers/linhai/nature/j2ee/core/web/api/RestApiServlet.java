/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : RestApiServlet.java</p>
 * <p>Package     : pers.linhai.nature.j2ee.core.web.api</p>
 * @Creator lilinhai 2018年6月8日 下午11:38:20
 * @Version  V1.0  
 */

package pers.linhai.nature.j2ee.core.web.api;

import static com.google.common.base.Strings.isNullOrEmpty;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.ResourceLoader;
import org.springframework.web.util.UriComponents;

import com.alibaba.druid.util.Utils;

import io.swagger.models.Swagger;
import pers.linhai.nature.utils.IOUtils;
import springfox.documentation.service.Documentation;
import springfox.documentation.spring.web.DocumentationCache;
import springfox.documentation.spring.web.json.JsonSerializer;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.mappers.ServiceModelToSwagger2Mapper;

/**
 * <p>ClassName      : RestApiServlet</p>
 * @author lilinhai 2018年6月8日 下午11:38:20
 * @version 1.0
 */
public class RestApiServlet extends HttpServlet
{
    
    /**
     * <p>Info          : long serialVersionUID lilinhai 2018年6月8日 下午11:38:24</p>
     */
    private static final long serialVersionUID = 1L;
    
    private ResourceLoader resourceLoader;
    
    private DocumentationCache documentationCache;
    
    private ServiceModelToSwagger2Mapper mapper;
    
    private JsonSerializer jsonSerializer;
    
    /**
     * <p>Title        : RestApiServlet lilinhai 2018年6月9日 上午9:08:30</p>
     * @param resourceLoader 
     * @param mapper 
     * @param documentationCache 
     * @param objectMapper 
     */
    public RestApiServlet(ResourceLoader resourceLoader, DocumentationCache documentationCache, ServiceModelToSwagger2Mapper mapper, JsonSerializer jsonSerializer)
    {
        this.resourceLoader = resourceLoader;
        this.documentationCache = documentationCache;
        this.mapper = mapper;
        this.jsonSerializer = jsonSerializer;
    }
    
    /** 
     * <p>Overriding Method: lilinhai 2018年6月9日 上午12:02:50</p>
     * <p>Title: init</p>
     * <p>Description: TODO</p>
     * @throws ServletException 
     * @see javax.servlet.GenericServlet#init()
     */
    @Override
    public void init() throws ServletException
    {
    }
    
    /** 
     * <p>Overriding Method: lilinhai 2018年6月8日 下午11:49:11</p>
     * <p>Title: service</p>
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     * @see javax.servlet.http.HttpServlet#service(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String contextPath = request.getContextPath();
        String servletPath = request.getServletPath();
        String requestURI = request.getRequestURI();
        
        response.setCharacterEncoding("utf-8");
        
        if (contextPath == null)
        { // root context
            contextPath = "";
        }
        String uri = contextPath + servletPath;
        String path = requestURI.substring(contextPath.length() + servletPath.length());
        
        if ("/".equals(path) || "".equals(path))
        {
            response.sendRedirect(contextPath + servletPath + "/swagger-ui.html");
            return;
        }
        
        if (requestURI.endsWith("api-docs"))
        {
            response.setContentType("application/json;charset=utf-8");
            String groupName = Docket.DEFAULT_GROUP_NAME;
            Documentation documentation = documentationCache.documentationByGroup(groupName);
            if (documentation == null)
            {
                response.getWriter().write("{}");
                return;
            }
            Swagger swagger = mapper.mapDocumentation(documentation);
            swagger.setBasePath(contextPath);
            UriComponents uriComponents = HostNameProvider.componentsFrom(request, swagger.getBasePath());
            if (isNullOrEmpty(swagger.getHost()))
            {
                swagger.host(hostName(uriComponents));
            }
            response.getWriter().write(jsonSerializer.toJson(swagger).value());
            return;
        }
        
        // find file in resources path
        returnResourceFile(path, uri, response);
    }
    
    protected void returnResourceFile(String fileName, String uri, HttpServletResponse response) throws ServletException, IOException
    {
        
        String filePath = getFilePath(fileName);
        InputStream in = null;
        try
        {
            in = resourceLoader.getResource(filePath).getInputStream();
        }
        catch (Exception e)
        {
            response.setStatus(404);
            response.getWriter().write("not found");
            return;
        }
        
        if (fileName.endsWith(".woff"))
        {
            response.setContentType("application/font-woff;charset=utf-8");
            byte[] bytes = Utils.readByteArray(in);
            if (bytes != null) {
                response.setContentLength(bytes.length);
                response.getOutputStream().write(bytes);
            }
            return;
        }
        else if (fileName.endsWith(".woff2"))
        {
            response.setContentType("application/font-woff2;charset=utf-8");
            byte[] bytes = Utils.readByteArray(in);
            if (bytes != null) {
                response.setContentLength(bytes.length);
                response.getOutputStream().write(bytes);
            }
            return;
        }
        else if (fileName.endsWith(".jpg") || fileName.endsWith(".png") || fileName.endsWith(".svg"))
        {
            byte[] bytes = Utils.readByteArray(in);
            if (bytes != null)
            {
                response.setContentLength(bytes.length);
                response.getOutputStream().write(bytes);
            }
            return;
        }
        
        String text = IOUtils.inputStreamToString(in);
        if (text == null)
        {
            response.sendRedirect(uri + "/index.html");
            return;
        }
        if (filePath.endsWith(".html"))
        {
            response.setContentType("text/html; charset=utf-8");
        }
        else if (fileName.endsWith(".css"))
        {
            response.setContentType("text/css;charset=utf-8");
        }
        else if (fileName.endsWith(".js"))
        {
            response.setContentType("text/javascript;charset=utf-8");
            
        }
        response.getWriter().write(text);
    }
    
    protected String getFilePath(String fileName) {
        return "" + fileName;
    }

    private String hostName(UriComponents uriComponents)
    {
        String host = uriComponents.getHost();
        int port = uriComponents.getPort();
        if (port > -1)
        {
            return String.format("%s:%d", host, port);
        }
        return host;
    }
}
