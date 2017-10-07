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
public class ClientConfiguration
{

    /**
     * 索引名前缀
     */
    private String indexNamePrefix;
    
    /**
     * Settings
     */
    private ClientSettings settings = new ClientSettings();
    
    /**
     * Addresses
     */
    private ClientAddresses addresses = new ClientAddresses();

    /**
     * 获取 settings
     * @return 返回 settings
     */
    public ClientSettings getSettings()
    {
        return settings;
    }

    /**
     * 获取 addresses
     * @return 返回 addresses
     */
    public ClientAddresses getAddresses()
    {
        return addresses;
    }

    /**
     * 获取 indexNamePrefix
     * @return 返回 indexNamePrefix
     */
    public String getIndexNamePrefix()
    {
        return indexNamePrefix;
    }

    /**
     * 设置 indexNamePrefix
     * @param 对indexNamePrefix进行赋值
     */
    public void setIndexNamePrefix(String indexNamePrefix)
    {
        this.indexNamePrefix = indexNamePrefix;
    }
    
    
}
