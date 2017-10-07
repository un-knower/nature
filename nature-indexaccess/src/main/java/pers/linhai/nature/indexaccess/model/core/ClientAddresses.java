/*
 * 文 件 名: pers.linhai.esdao.configuration.model.Addresses.java
 * 版    权: XXX Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述: <描述>
 * 修 改 人: shinelon
 * 修改时间: 2017年3月4日 下午1:34:25
 * 跟踪单号: <跟踪单号>
 * 修改单号: <修改单号>
 * 修改内容: <修改内容>
 */
package pers.linhai.nature.indexaccess.model.core;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import pers.linhai.nature.indexaccess.exception.EsClientInitializationException;

/**
 * @Description: <一句话功能简述>
 * @author: shinelon
 * @date: 2017年3月4日 下午1:34:25
 *
 * @ClassName: 	[Addresses]
 * @version: [版本号]
 * @since: [产品/模块版本]
 */
public class ClientAddresses
{

    /**
     * 客户端地址集合
     */
    private List<Address> list = new ArrayList<ClientAddresses.Address>();
    
    /**
     * 添加一个客户端地址配置信息
     * @author: shinelon
     * @date: 2017年3月4日 下午1:37:04 
     * @Title: add
     *
     * @param address
     * @return:	void
     */
    public void add(Address address)
    {
        list.add(address);
    }
    
    /**
     * 对TransportClient进行客户端地址配置
     * @author: shinelon
     * @date: 2017年3月4日 下午1:42:02 
     * @Title: configure
     *
     * @param client
     * @return:	void
     */
    public void configure(TransportClient client)
    {
        for (Address address : list)
        {
            try
            {
                client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(address.getIp()), address.getPort()));
            }
            catch (Throwable e)
            {
                throw new EsClientInitializationException(address.toString(), e);
            }
        }
    }

    /**
     * 参数
     * @author: shinelon
     * @date: 2017年3月4日 下午1:34:05
     *
     * @ClassName:  [Parameter]
     * @version: [版本号]
     * @since: [产品/模块版本]
     */
    public static final class Address
    {
        /**
         * ip地址
         */
        private String ip;
        
        /**
         * 端口
         */
        private int port;

        /** 
         * <构造函数>
         * @author: shinelon
         * @date: 2017年3月4日 下午1:35:34 
         *
         * @param ip
         * @param port
         */
        public Address(String ip, int port)
        {
            super();
            this.ip = ip;
            this.port = port;
        }

        /**
         * 获取 ip
         * @return 返回 ip
         */
        public String getIp()
        {
            return ip;
        }

        /**
         * 获取 port
         * @return 返回 port
         */
        public int getPort()
        {
            return port;
        }

        /** 
         * <重载方法>
         * @author: shinelon
         * @date: 2017年3月4日 下午1:41:12 
         * @Title: toString
         *
         * @return
         */
        public String toString()
        {
            return "Address [ip=" + ip + ", port=" + port + "]";
        }
    }
}
