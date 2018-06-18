/*
 * 文 件 名: pers.linhai.esdao.configuration.ClientConfigParser.java
 * 版    权: XXX Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述: <描述>
 * 修 改 人: shinelon
 * 修改时间: 2017年3月4日 下午12:50:10
 * 跟踪单号: <跟踪单号>
 * 修改单号: <修改单号>
 * 修改内容: <修改内容>
 */
package pers.linhai.nature.esmapper.core;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import pers.linhai.nature.esmapper.exception.EsConfigLoadedException;
import pers.linhai.nature.esmapper.exception.EsConfigParseException;
import pers.linhai.nature.esmapper.model.core.ClientConfiguration;
import pers.linhai.nature.esmapper.model.core.ClientSettings.Parameter;
import pers.linhai.nature.esmapper.model.core.ClientTransportAddress.Address;
import pers.linhai.nature.utils.IOUtils;
import pers.linhai.nature.utils.ResourceUtils;
import pers.linhai.nature.utils.StringUtils;

/**
 * es客戶端配置解析器
 * 
 * @author: shinelon
 * @date: 2017年3月4日 下午12:50:10
 *        
 * @ClassName: [ClientConfigParser]
 * @version: [版本号]
 * @since: [产品/模块版本]
 */
public class ConfigurationParser
{
    
    /**
     * xml config file document object
     */
    private Document document;
    
    /**
     * Whether the elastic-search configuration file has been loaded
     */
    private static boolean isInited;
    
    /**
     * <构造函数>
     * 
     * @author: shinelon
     * @date: 2017年3月4日 下午12:58:18
     *        
     * @param clientConfiguration
     */
    public ConfigurationParser()
    {
        if (!isInited)
        {
            InputStream configInput = null;
            try
            {
                configInput = ResourceUtils.getURL(StringUtils.isEmpty(ClientConfiguration.getConfigFilePath()) ? "classpath:transport-client.xml" : ClientConfiguration.getConfigFilePath()).openStream();
                this.document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(configInput);
                isInited = true;
            }
            catch (Throwable e)
            {
                throw new EsConfigParseException(e);
            }
            finally
            {
                IOUtils.close(configInput);
            }
        }
        else
        {
            throw new EsConfigLoadedException("The elastic-search configuration file has been loaded, can't be loaded again.");
        }
    }
    
    /**
     * parse the es client config file
     * 
     * @author: shinelon
     * @date: 2017年3月4日 下午1:15:06
     * @Title: parse
     *        
     * @return: void
     */
    public void parse()
    {
        Element root = document.getDocumentElement();
        if (root == null)
        {
            throw new EsConfigParseException("Can't find the root element from the es client config file.");
        }
        
        NodeList nodes = root.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++)
        {
            Node node = nodes.item(i);
            if (node.getNodeType() == Element.ELEMENT_NODE)
            {
                if (node.getNodeName().equals("Settings"))
                {
                    parseSettings(node);
                }
                else if (node.getNodeName().equals("TransportAddresses"))
                {
                    parseAddresses(node);
                }
            }
        }
        
        this.document = null;
    }

    /**
     * 解析客户端链接地址
     * 
     * @author: shinelon
     * @date: 2017年3月4日 下午1:25:22
     * @Title: parseAddresses
     *        
     * @param node
     * @return: void
     */
    private void parseAddresses(Node addressesNode)
    {
        NodeList nodeList = addressesNode.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++)
        {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Element.ELEMENT_NODE && node.getNodeName().equals("TransportAddress"))
            {
                Node ipAttr = node.getAttributes().getNamedItem("ip");
                Node portAttr = node.getAttributes().getNamedItem("port");
                if(ipAttr == null || portAttr == null)
                {
                    throw new EsConfigParseException("Addresses parsed error: ip or port attribute is null.");
                }
                
                ClientConfiguration.getAddresses().add(new Address(ipAttr.getNodeValue(), Integer.parseInt(portAttr.getNodeValue())));
            }
        }
    }
    
    /**
     * 解析设置
     * 
     * @author: shinelon
     * @date: 2017年3月4日 下午1:24:49
     * @Title: parseSettings
     *        
     * @param node
     * @return: void
     */
    private void parseSettings(Node settingsNode)
    {
        NodeList nodeList = settingsNode.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++)
        {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Element.ELEMENT_NODE && node.getNodeName().equals("Entry"))
            {
                Node nameAttr = node.getAttributes().getNamedItem("name");
                Node valueAttr = node.getAttributes().getNamedItem("value");
                if(nameAttr == null || valueAttr == null)
                {
                    throw new EsConfigParseException("Settings parsed error: name or value attribute is null.");
                }
                ClientConfiguration.getSettings().add(new Parameter(nameAttr.getNodeValue(), valueAttr.getNodeValue()));
            }
        }
    }
}
