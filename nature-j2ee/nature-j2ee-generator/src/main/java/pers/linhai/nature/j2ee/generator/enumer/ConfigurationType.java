/*
 * <p>Copyright : LinHai Technologies Co., Ltd. Copyright 2017, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p> <p>FileName : UserEvent.java</p> <p>Package :
 * com.leloven.wanka.dao.sdk.model</p>
 * @Creator lilinhai 2017年12月28日 下午3:00:49
 * @Version V1.0
 */

package pers.linhai.nature.j2ee.generator.enumer;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>ClassName      : UserEvent</p>
 * @author lilinhai 2017年12月28日 下午3:00:49
 * @version 1.0
 */
public enum ConfigurationType 
{
    
    /**
     * ANNOTATEDMAPPER
     */
    ANNOTATEDMAPPER("ANNOTATEDMAPPER", "ANNOTATEDMAPPER"),
    
    /**
     * MIXEDMAPPER
     */
    MIXEDMAPPER("MIXEDMAPPER", "MIXEDMAPPER"),
    
    /**
     * XMLMAPPER
     */
    XMLMAPPER("XMLMAPPER", "XMLMAPPER");
    
    private static Map<String, ConfigurationType> MAP = new HashMap<String, ConfigurationType>();
    
    static
    {
        for (ConfigurationType ue : values())
        {
            MAP.put(ue.value, ue);
        }
    }
    
    /**
     * <p>Title        : UserEvent lilinhai 2017年12月28日 下午3:03:26</p>
     * <p>Description  : 构造函数</p>
     * @param value
     * @param name 
     */ 
    ConfigurationType(String value, String name)
    {
        this.value = value;
        this.name = name;
    }

    /**
     * 事件类型值
     */
    private String value;
    
    /**
     * 事件名
     */
    private String name;

    /**
     * <p>Get Method   :   value int</p>
     * @return value
     */
    public String getValue()
    {
        return value;
    }

    /**
     * <p>Get Method   :   name String</p>
     * @return name
     */
    public String getName()
    {
        return name;
    }

    /**
     * 转换成枚举
     * @param value
     * @return 
     * UserEvent
     */
    public static ConfigurationType transfer(String value)
    {
        return MAP.get(value);
    }
}
