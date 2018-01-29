/*
 * 项 目 名:  Storage Tool Service V100R001C00
 * 文 件 名:  pers.linhai.esdao.core.ElasticsearchClient.java
 * 版       权:  XXX Technologies Co., Ltd. Copyright 2017,  All rights reserved.
 * 描       述:  XXX PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * 修 改 人:  shinelon
 * 修改时间:  2017年4月19日
 * 修改内容:  创建
 */
package pers.linhai.nature.indexaccess.core.processor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.Version;
import org.elasticsearch.client.AdminClient;
import org.elasticsearch.client.ClusterAdminClient;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.settings.Settings.Builder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import pers.linhai.nature.indexaccess.core.ConfigurationParser;
import pers.linhai.nature.indexaccess.exception.EsClientInitializationException;
import pers.linhai.nature.indexaccess.model.core.ClientConfiguration;
import pers.linhai.nature.utils.IOUtils;
import pers.linhai.nature.utils.ResourceUtils;

/**
 * ES client link single object
 * 
 * @author  shinelon
 * @version  V100R001C00
 */
public abstract class ClusterNodeAccessor
{
    /**
     * java logger
     */
    private static final Logger LOGGER = LogManager.getLogger(ClusterNodeAccessor.class);
    
    /**
     * ES client link single object
     */
    private static final ClusterNodeAccessor esTransporter = new ClusterNodeAccessor()
    {
        
    };
    
    /**
     * es连接相关对象
     */
    private TransportClient transportClient;
    
    /**
     * adminClient
     */
    private AdminClient adminClient;
    
    /**
     * indicesAdminClient
     */
    private IndicesAdminClient indicesAdminClient;
    
    /**
     * clusterAdminClient
     */
    private ClusterAdminClient clusterAdminClient;
    
    /**
     * <默认构造函数>
     */
    private ClusterNodeAccessor()
    {
        try
        {
            long start = System.currentTimeMillis();
            
            String esVersion = String.valueOf(Version.CURRENT.id);
            esVersion = esVersion.substring(0, esVersion.length() - 2);
            String bigVersion = esVersion.substring(0, 1);
            String middleVersion = esVersion.substring(2, 3);
            String smallVersion = esVersion.substring(4, 5);
            LOGGER.info("Elasticsearch-ClusterAccessor is in initializing, elasticsearch-version: " + bigVersion + "." + middleVersion + "." + smallVersion);
            
            //Parse the elastic-search-client configuration
            new ConfigurationParser().parse();
            
            Builder builder = Settings.builder();
            ClientConfiguration.getSettings().configure(builder);
            
            //Create the TransportClient Object
            transportClient = new PreBuiltTransportClient(builder.build());

            //init the client single-object
            ClientConfiguration.getAddresses().configure(transportClient);
            
            adminClient = transportClient.admin();
            
            indicesAdminClient = adminClient.indices();
            
            clusterAdminClient = adminClient.cluster();
            
            LOGGER.info("Elasticsearch-ClusterConnector created successfully in " + ( System.currentTimeMillis() - start ) + " ms.");
            
            LOGGER.info(IOUtils.inputStreamToString(ResourceUtils.getURL("classpath:banner").openStream()));
        }
        catch (Throwable e)
        {
            throw new EsClientInitializationException("The TransportClient Inited error: ", e);
        }
    }
    
    /**
     * get ES client link single object
     * 
     * @return ElasticsearchClient
     */
    public static ClusterNodeAccessor instance()
    {
        return esTransporter;
    }

    /**
     * 返回 adminClient
     *
     * @return adminClient
     */
    AdminClient getAdminClient()
    {
        return adminClient;
    }

    /**
     * 返回 transportClient
     *
     * @return transportClient
     */
    TransportClient getTransportClient()
    {
        return transportClient;
    }

    /**
     * 返回 indicesAdminClient
     *
     * @return indicesAdminClient
     */
    IndicesAdminClient getIndicesAdminClient()
    {
        return indicesAdminClient;
    }

    /**
     * 返回 clusterAdminClient
     *
     * @return clusterAdminClient
     */
    ClusterAdminClient getClusterAdminClient()
    {
        return clusterAdminClient;
    }
    
    /**
     * 关闭客户端
     * void
     */
    public void shutdown()
    {
        transportClient.close();
    }
}
