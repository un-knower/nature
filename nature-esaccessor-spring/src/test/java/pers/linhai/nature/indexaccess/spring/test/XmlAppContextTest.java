/*
 * 项 目 名:  Storage Tool Service V100R001C00
 * 文 件 名:  pers.linhai.nature.indexdao.springintegration.IndexRepositoryDefinitionRegistryTest.java
 * 版       权:  XXX Technologies Co., Ltd. Copyright 2017,  All rights reserved.
 * 描       述:  XXX PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * 修 改 人:  shinelon
 * 修改时间:  2017年7月2日
 * 修改内容:  创建
 */
package pers.linhai.nature.indexaccess.spring.test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import pers.linhai.nature.indexaccess.spring.test.services.LogIndexService;
import pers.linhai.nature.indexaccess.spring.test.services.LogInfoService;
import pers.linhai.nature.indexaccess.spring.test.services.UserIndexService;
import pers.linhai.nature.indexaccess.spring.test.services.UserInfoService;

/**
 * 
 * 
 * @author  shinelon
 * @version  V100R001C00
 */
public class XmlAppContextTest
{
    public static void main(String[] args)
    {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        System.out.println(context.getBean(UserIndexService.class).getUserIndexAccessor().index());
        System.out.println(context.getBean(LogIndexService.class).getLogIndexAccessor().index());
        System.out.println(context.getBean(UserInfoService.class).getUserInfoAccessor().typeName());
        System.out.println(context.getBean(LogInfoService.class).getLogInfoAccessor().typeName());
        context.close();
    }
}
