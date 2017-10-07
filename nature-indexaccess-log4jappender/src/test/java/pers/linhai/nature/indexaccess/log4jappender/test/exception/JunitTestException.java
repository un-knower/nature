/*
 * 项 目 名:  Storage Tool Service V100R001C00
 * 文 件 名:  pers.linhai.nature.indexaccess.log4jappender.test.exception.JunitTestException.java
 * 版       权:  XXX Technologies Co., Ltd. Copyright 2017,  All rights reserved.
 * 描       述:  XXX PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * 修 改 人:  shinelon
 * 修改时间:  2017年8月20日
 * 修改内容:  创建
 */
package pers.linhai.nature.indexaccess.log4jappender.test.exception;

/**
 * 
 * 
 * @author  shinelon
 * @version  V100R001C00
 */
public class JunitTestException extends RuntimeException
{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /** 
     * <默认构造函数>
     *
     */
    public JunitTestException()
    {
        super();
        // TODO shinelon 简要描述
    }

    /** 
     * <默认构造函数>
     *
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public JunitTestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO shinelon 简要描述
    }

    /** 
     * <默认构造函数>
     *
     * @param message
     * @param cause
     */
    public JunitTestException(String message, Throwable cause)
    {
        super(message, cause);
        // TODO shinelon 简要描述
    }

    /** 
     * <默认构造函数>
     *
     * @param message
     */
    public JunitTestException(String message)
    {
        super(message);
        // TODO shinelon 简要描述
    }

    /** 
     * <默认构造函数>
     *
     * @param cause
     */
    public JunitTestException(Throwable cause)
    {
        super(cause);
        // TODO shinelon 简要描述
    }
    
}
