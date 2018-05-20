/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : SortField.java</p>
 * <p>Package     : pers.linhai.nature.j2ee.core.model</p>
 * @Creator lilinhai 2018年5月20日 下午8:06:53
 * @Version  V1.0  
 */ 

package pers.linhai.nature.j2ee.core.model;

import java.util.Locale;

import pers.linhai.nature.j2ee.core.model.enumer.Direction;

/**
 * 排序字段对象
 * <p>ClassName      : SortField</p>
 * @author lilinhai 2018年5月20日 下午8:06:53
 * @version 1.0
 */
public class SortField
{

    
    /**
     * 排序字段名
     */
    private String fieldName;
    
    /**
     * 排序方向
     */
    private String direction = Direction.ASC.name().toLowerCase(Locale.ENGLISH);
    
    /**
     * <p>Get Method   :   fieldName String</p>
     * @return fieldName
     */
    public String getFieldName()
    {
        return fieldName;
    }
    
    /**
     * <p>Set Method   :   fieldName String</p>
     * @param fieldName
     */
    public void setFieldName(String fieldName)
    {
        this.fieldName = fieldName;
    }
    
    /**
     * <p>Get Method   :   direction Direction</p>
     * @return direction
     */
    public String getDirection()
    {
        return direction;
    }
    
    /**
     * <p>Set Method   :   direction Direction</p>
     * @param direction
     */
    public void setDirection(String direction)
    {
        this.direction = Direction.fromString(direction).name().toLowerCase(Locale.ENGLISH);
    }
    
    /** 
     * <p>Overriding Method: lilinhai 2018年2月8日 下午3:08:05</p>
     * <p>Title: toString</p>
     * @return 
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        return "SortField [fieldName=" + fieldName + ", direction=" + direction + "]";
    }

}
