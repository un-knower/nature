/*
 * 项 目 名:  Storage Tool Service V100R001C00
 * 文 件 名:  esdao.esdao.Test.java
 * 版       权:  XXX Technologies Co., Ltd. Copyright 2017,  All rights reserved.
 * 描       述:  XXX PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * 修 改 人:  shinelon
 * 修改时间:  2017年4月22日
 * 修改内容:  创建
 */
package pers.linhai.nature.indexaccess.bak;

import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;

/**
 * 
 * 
 * @author  shinelon
 * @version  V100R001C00
 */
public class Test2
{
    
    public static void main(String[] args) throws Exception
    {
        XContentBuilder jsonBuild = XContentFactory.jsonBuilder();
        jsonBuild.startObject().startObject("ssss");
        jsonBuild.field("q1", "qqqqq11111").endObject();
        jsonBuild.field("a", "gfdkdjgk d gdfg jdk jgdfs jgd gdfs gdfs jgd");
        jsonBuild.field("b", true);
        jsonBuild.field("c", 3232);
        jsonBuild.field("d", 3.3);
        jsonBuild.endObject();
        System.out.println(jsonBuild.string());
        Settings s =  Settings.builder().loadFromSource(jsonBuild.string(), XContentType.JSON).build();
        System.out.println(s);
    }
}
