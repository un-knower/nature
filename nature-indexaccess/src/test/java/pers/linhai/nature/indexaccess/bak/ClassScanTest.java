/*
 * 项 目 名:  Storage Tool Service V100R001C00
 * 文 件 名:  pers.linhai.nature.indexaccess.ClassScanTest.java
 * 版       权:  XXX Technologies Co., Ltd. Copyright 2017,  All rights reserved.
 * 描       述:  XXX PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * 修 改 人:  shinelon
 * 修改时间:  2017年7月15日
 * 修改内容:  创建
 */
package pers.linhai.nature.indexaccess.bak;

import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;

/**
 * 
 * 
 * @author  shinelon
 * @version  V100R001C00
 */
public class ClassScanTest
{
    
    public static void main(String[] args) throws Exception
    {
        String pckName = "pers.linhai.nature.indexaccess.log4j2.plugins".replace('.', '/');
        Enumeration<URL> dirs = ClassScanTest.class.getClassLoader().getResources(pckName);
        while (dirs.hasMoreElements()) 
        {
            URL url = dirs.nextElement();
            String protocol = url.getProtocol();
            System.out.println(protocol);
            String filePath = URLDecoder.decode(url.getFile(), "UTF-8"); 
            System.out.println(filePath);
        }
        System.out.println(ClassScanTest.class.getResource(""));
    }
}
