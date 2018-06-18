/*
 * 项 目 名:  Storage Tool Service V100R001C00
 * 文 件 名:  esdao.esdao.SettingsTest.java
 * 版       权:  XXX Technologies Co., Ltd. Copyright 2017,  All rights reserved.
 * 描       述:  XXX PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * 修 改 人:  shinelon
 * 修改时间:  2017年6月10日
 * 修改内容:  创建
 */
package pers.linhai.nature.esaccessor.bak;

import java.io.IOException;

import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;

/**
 * 
 * 
 * @author  shinelon
 * @version  V100R001C00
 */
public class SettingsTest
{
    public static void main(String[] args) throws IOException
    {
        String json = "{\"a\":1,\"b\":{\"c\":true}}";
        Settings s =  Settings.builder().loadFromSource(json, XContentType.JSON).build();
        System.out.println(s);
    }
}
