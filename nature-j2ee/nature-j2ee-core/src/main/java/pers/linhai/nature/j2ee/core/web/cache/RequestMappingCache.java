/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : RequestMappingCache.java</p>
 * <p>Package     : com.meme.crm.web.cache</p>
 * @Creator lilinhai 2018年2月26日 下午2:05:57
 * @Version  V1.0  
 */

package pers.linhai.nature.j2ee.core.web.cache;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.web.method.HandlerMethod;

/**
 * <p>Description    : <pre>TODO(这里用一句话描述这个类的作用)</pre></p>
 * <p>ClassName      : RequestMappingCache</p>
 * @author lilinhai 2018年2月26日 下午2:05:57
 * @version 1.0
 */
public abstract class RequestMappingCache
{
    
    private Map<HandlerMethod, Set<String>> map = new HashMap<HandlerMethod, Set<String>>();
    
    private static final RequestMappingCache CACHE = new RequestMappingCache()
    {
    };
    
    private RequestMappingCache()
    {
        
    }
    
    public static void put(HandlerMethod hm, String path)
    {
        Set<String> pathSet = CACHE.map.get(hm);
        if (pathSet == null)
        {
            pathSet = new HashSet<String>();
            CACHE.map.put(hm, pathSet);
        }
        pathSet.add(path);
    }
    
    public static Set<String> get(HandlerMethod hm)
    {
        return CACHE.map.get(hm);
    }
}
