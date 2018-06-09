/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : HostNameProvider.java</p>
 * <p>Package     : pers.linhai.nature.j2ee.core.web.api</p>
 * @Creator lilinhai 2018年6月9日 下午1:52:17
 * @Version  V1.0  
 */

package pers.linhai.nature.j2ee.core.web.api;

import static org.springframework.util.StringUtils.hasText;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromContextPath;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UrlPathHelper;

/**
 * <p>Description    : <pre>TODO(这里用一句话描述这个类的作用)</pre></p>
 * <p>ClassName      : HostNameProvider</p>
 * @author lilinhai 2018年6月9日 下午1:52:17
 * @version 1.0
 */
public class HostNameProvider
{
    
    public HostNameProvider()
    {
        throw new UnsupportedOperationException();
    }
    
    public static UriComponents componentsFrom(HttpServletRequest request, String basePath)
    {
        
        ServletUriComponentsBuilder builder = fromServletMapping(request, basePath);
        
        UriComponents components = UriComponentsBuilder.fromHttpRequest(new ServletServerHttpRequest(request)).build();
        
        String host = components.getHost();
        if (!hasText(host))
        {
            return builder.build();
        }
        
        builder.host(host);
        builder.port(components.getPort());
        
        return builder.build();
    }
    
    private static ServletUriComponentsBuilder fromServletMapping(HttpServletRequest request, String basePath)
    {
        
        ServletUriComponentsBuilder builder = fromContextPath(request);
        
        builder.replacePath(prependForwardedPrefix(request, basePath));
        if (hasText(new UrlPathHelper().getPathWithinServletMapping(request)))
        {
            builder.path(request.getServletPath());
        }
        
        return builder;
    }
    
    private static String prependForwardedPrefix(HttpServletRequest request, String path)
    {
        
        String prefix = request.getHeader("X-Forwarded-Prefix");
        if (prefix != null)
        {
            return prefix + path;
        }
        else
        {
            return path;
        }
    }
}
