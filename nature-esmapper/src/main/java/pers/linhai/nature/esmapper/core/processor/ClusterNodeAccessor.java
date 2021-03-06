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
package pers.linhai.nature.esmapper.core.processor;

import org.elasticsearch.Version;
import org.elasticsearch.client.AdminClient;
import org.elasticsearch.client.ClusterAdminClient;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.client.transport.TransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pers.linhai.nature.esmapper.exception.EsClientInitializationException;
import pers.linhai.nature.esmapper.model.client.Configuration;
import pers.linhai.nature.esmapper.model.client.TransportClientBuilder;
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
    private final Logger logger = LoggerFactory.getLogger(ClusterNodeAccessor.class);
    
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
            logger.info("Elasticsearch-ClusterAccessor is in initializing, elasticsearch-version: " + bigVersion + "." + middleVersion + "." + smallVersion);
            
            //Create the TransportClient Object
            transportClient = TransportClientBuilder.build(Configuration.getInstance().getClusterNodes(), Configuration.getInstance().getSettings());
            
            adminClient = transportClient.admin();
            
            indicesAdminClient = adminClient.indices();
            
            clusterAdminClient = adminClient.cluster();
            
            logger.info("Elasticsearch-transportClient created successfully in " + ( System.currentTimeMillis() - start ) + " ms.");
            
            logger.info(IOUtils.inputStreamToString(ResourceUtils.getURL("classpath:banner").openStream()));
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
     * 销毁客户端
     * void
     */
    public void destroy()
    {
        transportClient.close();
        try
        {
            logger.info("Closing elasticSearch  client");
            if (transportClient != null)
            {
                transportClient.close();
            }
        }
        catch (final Exception e)
        {
            logger.error("Error closing ElasticSearch client: ", e);
        }
    }
}
