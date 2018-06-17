/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pers.linhai.nature.indexaccess.model.client;

import static pers.linhai.nature.utils.StringUtils.split;
import static pers.linhai.nature.utils.StringUtils.substringAfterLast;
import static pers.linhai.nature.utils.StringUtils.substringBeforeLast;

import java.net.InetAddress;
import java.util.Map;
import java.util.Map.Entry;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pers.linhai.nature.utils.Assert;
import pers.linhai.nature.utils.StringUtils;

/**
 * <p>ClassName      : TransportClientFactoryBean</p>
 * @author lilinhai 2018年6月17日 下午5:31:20
 * @version 1.0
 */
public class TransportClientBuilder
{
    
    private static final Logger logger = LoggerFactory.getLogger(TransportClientBuilder.class);
    
    private String clusterNodes = "127.0.0.1:9300";
    
    private String clusterName = "elasticsearch";
    
    private Boolean clientTransportSniff = true;
    
    private Boolean clientIgnoreClusterName = Boolean.FALSE;
    
    private String clientPingTimeout = "5s";
    
    private String clientNodesSamplerInterval = "5s";
    
    private TransportClient client;
    
    private Map<String, String> properties;
    
    static final String COLON = ":";
    
    static final String COMMA = ",";
    
    public void destroy()
    {
        try
        {
            logger.info("Closing elasticSearch  client");
            if (client != null)
            {
                client.close();
            }
        }
        catch (final Exception e)
        {
            logger.error("Error closing ElasticSearch client: ", e);
        }
    }
    
    private void buildClient() throws Exception
    {
        client = new PreBuiltTransportClient(getSettings());
        Assert.hasText(clusterNodes, "[Assertion failed] clusterNodes settings missing.");
        for (String clusterNode : split(clusterNodes, COMMA))
        {
            String hostName = substringBeforeLast(clusterNode, COLON);
            String port = substringAfterLast(clusterNode, COLON);
            Assert.hasText(hostName, "[Assertion failed] missing host name in 'clusterNodes'");
            Assert.hasText(port, "[Assertion failed] missing port in 'clusterNodes'");
            logger.info("adding transport node : " + clusterNode);
            client.addTransportAddress(new TransportAddress(InetAddress.getByName(hostName), Integer.valueOf(port)));
        }
        client.connectedNodes();
    }
    
    private Settings getSettings()
    {
        Settings.Builder builder = Settings.builder();
        if (properties != null)
        {
            for (Entry<String, String> property : properties.entrySet())
            {
                builder.put(property.getKey(), property.getValue());
            }
            return builder.build();
        }
        return builder
                .put("cluster.name", clusterName)
                .put("client.transport.sniff", clientTransportSniff)
                .put("client.transport.ignore_cluster_name", clientIgnoreClusterName)
                .put("client.transport.ping_timeout", clientPingTimeout)
                .put("client.transport.nodes_sampler_interval", clientNodesSamplerInterval)
                .build();
    }
    
    /**
     * 生成一个TransportClient对象
     * <p>Title         : build Administrator 2018年6月17日 下午9:27:21</p>
     * @param clusterName
     * @param clusterNodes
     * @param properties
     * @return 
     * TransportClient
     */
    public static TransportClient build(String clusterName, String clusterNodes, Map<String, String> properties)
    {
        try
        {
            TransportClientBuilder factory = new TransportClientBuilder();
            factory.clusterName = StringUtils.isEmpty(clusterName) ? factory.clusterName : clusterName;
            factory.clusterNodes = clusterNodes;
            factory.properties = properties;
            factory.buildClient();
            return factory.client;
        }
        catch (Throwable e)
        {
            e.printStackTrace();
            return null;
        }
    }
    
    public static TransportClient build(String clusterNodes, Map<String, String> properties)
    {
        return build(null, clusterNodes, properties);
    }
}
