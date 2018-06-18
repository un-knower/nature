/*
 * 项 目 名:  Storage Tool Service V100R001C00
 * 文 件 名:  pers.linhai.nature.indexaccess.spring.test.SpringBootTest.java
 * 版       权:  XXX Technologies Co., Ltd. Copyright 2017,  All rights reserved.
 * 描       述:  XXX PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * 修 改 人:  lilinhai
 * 修改时间:  2017年10月6日
 * 修改内容:  创建
 */
package pers.linhai.nature.indexaccess.spring.test;

import org.springframework.boot.Banner.Mode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

/**
 * 
 * 
 * @author  lilinhai
 * @version  V100R001C00
 */
@SpringBootApplication
@PropertySource("classpath:app.properties")
@ImportResource("classpath:spring.xml")
public class SpringBootTest
{
    
    public static void main(String[] args)
    {
        SpringApplication springApplication = new SpringApplication(SpringBootTest.class);
        
        //关闭spring boot自身横幅打印
        springApplication.setBannerMode(Mode.OFF);
        springApplication.run(args);
    }
}
