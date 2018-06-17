/*
 * 文 件 名: pers.linhai.esdao.configuration.model.Settings.java
 * 版    权: XXX Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述: <描述>
 * 修 改 人: shinelon
 * 修改时间: 2017年3月4日 下午1:31:25
 * 跟踪单号: <跟踪单号>
 * 修改单号: <修改单号>
 * 修改内容: <修改内容>
 */
package pers.linhai.nature.esaccessor.model.core;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.common.settings.Settings.Builder;

/**
 * @Description: <一句话功能简述>
 * @author: shinelon
 * @date: 2017年3月4日 下午1:31:25
 *
 * @ClassName: 	[Settings]
 * @version: [版本号]
 * @since: [产品/模块版本]
 */
public class ClientSettings
{

    /**
     * ES客户端setting集合
     */
    private List<Parameter> list = new ArrayList<ClientSettings.Parameter>();
    
    /**
     * 添加一个setting配置参数
     * @author: shinelon
     * @date: 2017年3月4日 下午1:44:42 
     * @Title: add
     *
     * @param parameter
     * @return:	void
     */
    public void add(Parameter parameter)
    {
        list.add(parameter);
    }
    
    /**
     * 配置参数
     * @author: shinelon
     * @date: 2017年3月4日 下午1:46:19 
     * @Title: configure
     *
     * @param builder
     * @return:	void
     */
    public void configure(Builder builder)
    {
        for (Parameter parameter : list)
        {
            builder.put(parameter.name, parameter.value);
        }
    }
    
    /**
     * 参数
     * @author: shinelon
     * @date: 2017年3月4日 下午1:34:05
     *
     * @ClassName: 	[Parameter]
     * @version: [版本号]
     * @since: [产品/模块版本]
     */
    public static final class Parameter
    {
        /**
         * 参数名
         */
        private String name;
        
        /**
         * 参数值
         */
        private String value;

        /** 
         * <构造函数>
         * @Description: <构造函数简要描述>
         * @author: shinelon
         * @date: 2017年3月4日 下午1:43:47 
         *
         * @param name
         * @param value
         */
        public Parameter(String name, String value)
        {
            super();
            this.name = name;
            this.value = value;
        }
        
        /** 
         * <重载方法>
         * @author: shinelon
         * @date: 2017年3月4日 下午1:43:52 
         * @Title: toString
         *
         * @return
         */
        public String toString()
        {
            return "Parameter [name=" + name + ", value=" + value + "]";
        }
        
    }
}
