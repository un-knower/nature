/* 
 * <p>Copyright   : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p>
 * <p>FileName    : Direction.java</p>
 * <p>Package     : pers.linhai.nature.j2ee.core.model.enumer</p>
 * @Creator lilinhai 2018年5月20日 下午8:05:32
 * @Version  V1.0  
 */ 

package pers.linhai.nature.j2ee.core.model.enumer;

import java.util.Locale;

/**
 * 排序方向
 * <p>ClassName      : Direction</p>
 * @author lilinhai 2018年5月20日 下午8:05:32
 * @version 1.0
 */
public enum Direction
{
    ASC, DESC;
    
    /**
     * Returns whether the direction is ascending.
     * 
     * @return
     * @since 1.13
     */
    public boolean isAscending()
    {
        return this.equals(ASC);
    }
    
    /**
     * Returns whether the direction is descending.
     * 
     * @return
     * @since 1.13
     */
    public boolean isDescending()
    {
        return this.equals(DESC);
    }
    
    /**
     * Returns the {@link Direction} enum for the given {@link String} value.
     * 
     * @param value
     * @throws IllegalArgumentException in case the given value cannot be parsed into an enum value.
     * @return
     */
    public static Direction fromString(String value)
    {
        
        try
        {
            return Direction.valueOf(value.toUpperCase(Locale.US));
        }
        catch (Exception e)
        {
            throw new IllegalArgumentException(String.format("Invalid value '%s' for orders given! Has to be either 'desc' or 'asc' (case insensitive).", value), e);
        }
    }
    
    /**
     * Returns the {@link Direction} enum for the given {@link String} or null if it cannot be parsed into an enum
     * value.
     * 
     * @param value
     * @return
     */
    public static Direction fromStringOrNull(String value)
    {
        
        try
        {
            return fromString(value);
        }
        catch (IllegalArgumentException e)
        {
            return null;
        }
    }

}
