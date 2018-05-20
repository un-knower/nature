/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : LogicalOperator.java</p>
 * <p>Package     : pers.linhai.nature.j2ee.core.model.enumer</p>
 * @Creator lilinhai 2018年5月20日 下午10:34:55
 * @Version  V1.0  
 */ 

package pers.linhai.nature.j2ee.core.model.enumer;

import java.util.Locale;

/**
 * <p>Description    : <pre>TODO(这里用一句话描述这个类的作用)</pre></p>
 * <p>ClassName      : LogicalOperator</p>
 * @author lilinhai 2018年5月20日 下午10:34:55
 * @version 1.0
 */
public enum LogicalOperator
{

    AND("and"),
    
    OR("or");
    
    /**
     * <p>Title        : LogicalOperator lilinhai 2018年2月10日 下午2:29:51</p>
     * @param value 
     */
    private LogicalOperator(String value)
    {
        this.value = value;
    }
    
    /**
     * 值
     */
    private String value;
    
    /**
     * <p>Get Method   :   value String</p>
     * @return value
     */
    public String getValue()
    {
        return value;
    }
    
    public static LogicalOperator transfer(String value)
    {
        return value.toLowerCase(Locale.ENGLISH).equals("and") ? AND : value.toLowerCase(Locale.ENGLISH).equals("or") ? OR : null;
    }

}
