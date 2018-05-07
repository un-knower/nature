/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : YamlPropertySourceFactory.java</p>
 * <p>Package     : pers.linhai.nature.j2ee.core.spring</p>
 * @Creator lilinhai 2018年5月6日 下午2:57:09
 * @Version  V1.0  
 */

package pers.linhai.nature.j2ee.core.spring;

import java.io.IOException;

import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;
import org.springframework.lang.Nullable;

/**
 * yaml文件解析工厂
 * <p>ClassName      : YamlPropertySourceFactory</p>
 * @author lilinhai 2018年5月6日 下午2:57:09
 * @version 1.0
 */
public class YamlPropertySourceFactory implements PropertySourceFactory
{
    
    /** 
     * <p>Overriding Method: lilinhai 2018年5月6日 下午2:57:09</p>
     * <p>Title: createPropertySource</p>
     * @param name
     * @param resource
     * @return
     * @throws IOException 
     * @see org.springframework.core.io.support.PropertySourceFactory#createPropertySource(java.lang.String, org.springframework.core.io.support.EncodedResource)
     */
    @Override
    public PropertySource< ? > createPropertySource(@Nullable String name, EncodedResource resource) throws IOException
    {
        return new YamlPropertySourceLoader().load(resource.getResource().getFilename(), resource.getResource()).get(0);
    }
    
}
