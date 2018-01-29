/*
 * 文 件 名: pers.linhai.esdao.configuration.ClientConfiguration.java
 * 版    权: XXX Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述: <描述>
 * 修 改 人: shinelon
 * 修改时间: 2017年3月4日 下午12:51:01
 * 跟踪单号: <跟踪单号>
 * 修改单号: <修改单号>
 * 修改内容: <修改内容>
 */
package pers.linhai.nature.indexaccess.model.core;

/**
 * @Description: <一句话功能简述>
 * @author: shinelon
 * @date: 2017年3月4日 下午12:51:01
 *
 * @ClassName: 	[ClientConfiguration]
 * @version: [版本号]
 * @since: [产品/模块版本]
 */
public abstract class ClientConfiguration
{
    
    private static final ClientConfiguration CLIENT_CONFIGURATION = new ClientConfiguration()
    {};
    
    private ClientConfiguration()
    {
        
    }
    
    private String configFilePath;
    
    /**
     * Settings
     */
    private ClientSettings settings = new ClientSettings();
    
    /**
     * Addresses
     */
    private ClientTransportAddress addresses = new ClientTransportAddress();

    /**
     * 获取 settings
     * @return 返回 settings
     */
    public static ClientSettings getSettings()
    {
        return CLIENT_CONFIGURATION.settings;
    }

    /**
     * 获取 addresses
     * @return 返回 addresses
     */
    public static ClientTransportAddress getAddresses()
    {
        return CLIENT_CONFIGURATION.addresses;
    }
    
    /**
     * <p>Get Method   :   configFilePath String</p>
     * @return configFilePath
     */
    public static String getConfigFilePath()
    {
        return CLIENT_CONFIGURATION.configFilePath;
    }

    /**
     * <p>Set Method   :   configFilePath String</p>
     * @param configFilePath
     */
    public static void setConfigFilePath(String configFilePath)
    {
        CLIENT_CONFIGURATION.configFilePath = configFilePath;
    }

    public static ClientConfiguration getInstance()
    {
        return CLIENT_CONFIGURATION;
    }
}
