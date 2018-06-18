/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : Configuration.java</p>
 * <p>Package     : pers.linhai.nature.esaccessor.model.client</p>
 * @Creator lilinhai 2018年6月17日 下午11:49:25
 * @Version  V1.0  
 */

package pers.linhai.nature.esmapper.model.client;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import pers.linhai.nature.utils.IOUtils;
import pers.linhai.nature.utils.ResourceUtils;

/**
 * es配置
 * <p>ClassName      : Configuration</p>
 * @author lilinhai 2018年6月17日 下午11:49:25
 * @version 1.0
 */
public abstract class Configuration
{
    private static final Configuration INSTANCE = new Configuration()
    {
    };
    
    private Configuration()
    {
        
    }
    
    /**
     * Comma-separated list of cluster node addresses.
     */
    private String clusterNodes;
    
    /**
     * Additional properties used to configure the client.
     */
    private Map<String, String> settings = new HashMap<String, String>();
    
    /**
     * 是否初始化
     */
    private boolean isInited;

    public String getClusterNodes()
    {
        return this.clusterNodes;
    }
    
    public void setClusterNodes(String clusterNodes)
    {
        this.clusterNodes = clusterNodes;
    }
    
    /**
     * <p>Get Method   :   settings Map<String,String></p>
     * @return settings
     */
    public Map<String, String> getSettings()
    {
        return settings;
    }

    /**
     * <p>Set Method   :   settings Map<String,String></p>
     * @param settings
     */
    public void setSettings(Map<String, String> settings)
    {
        this.settings = settings;
    }

    public static Configuration getInstance()
    {
        return INSTANCE;
    }
    
    public static void main(String[] args)
    {
        getInstance().load();
    }
    
    public void load()
    {
        try
        {
            if (isInited)
            {
                return;
            }
            load(ResourceUtils.getURL("classpath:esmapper.yml").openStream());
        }
        catch (Throwable e)
        {
            e.printStackTrace();
        }
    }
    
    @SuppressWarnings("unchecked")
    public void load(InputStream in)
    {
        try
        {
            if (isInited)
            {
                return;
            }
            Yaml yaml = new Yaml();
            Map<?, ?> mapConf = yaml.loadAs(in, Map.class);
            this.clusterNodes = ((Map<?, ?>)mapConf.get("esmapper")).get("clusterNodes").toString();
            this.settings = (Map<String, String>)((Map<?, ?>)mapConf.get("esmapper")).get("settings");
            this.isInited = true;
        }
        catch (Throwable e)
        {
            e.printStackTrace();
        }
        finally
        {
            IOUtils.close(in);
        }
    }
}
